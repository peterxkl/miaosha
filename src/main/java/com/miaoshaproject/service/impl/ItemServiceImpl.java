package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.ItemDOMapper;
import com.miaoshaproject.dao.ItemStockDOMapper;
import com.miaoshaproject.dao.StockLogDOMapper;
import com.miaoshaproject.dataproject.ItemDO;
import com.miaoshaproject.dataproject.ItemStockDO;
import com.miaoshaproject.dataproject.StockLogDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.mq.MqProducer;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.PromoService;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.PromoModel;
import com.miaoshaproject.utils.Utils;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author hongjun500
 * @date 2019/2/4 11:35
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private StockLogDOMapper stockLogDOMapper;

    @Autowired
    private PromoService promoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MqProducer mqProducer;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidationResult validationResult=validator.validate(itemModel);
        if(validationResult.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validationResult.getErrMsg());
        }
        //转化ItemModel->dataObject
        ItemDO itemDO=this.convertItemDOFromItemModel(itemModel);
        //写入数据库
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());

        ItemStockDO itemStockDO=this.convertItemStockDOFromItemModel(itemModel);

        itemStockDOMapper.insertSelective(itemStockDO);

        //返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    private ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if (itemModel==null){
            return null;
        }
        ItemDO itemDO=new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return  itemDO;
    }

    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel){
        if(itemModel==null){
            return null;
        }
        ItemStockDO itemStockDO=new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    @Override
    public List<ItemModel> listItem() {

        List<ItemDO> itemDOList=itemDOMapper.listItem();
        List<ItemModel> itemModelList=itemDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO=itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel=this.convertModelFromDataObject(itemDO,itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO=itemDOMapper.selectByPrimaryKey(id);
        if(itemDO==null){
            return null;
        }
        //操作获得库存的数量
        ItemStockDO itemStockDO=itemStockDOMapper.selectByItemId(itemDO.getId());

        //将dataobject->model
        ItemModel itemModel=convertModelFromDataObject(itemDO,itemStockDO);

        //获取活动商品信息
        PromoModel promoModel=promoService.getPromoByItemId(itemModel.getId());
        if(promoModel!=null&&promoModel.getStatus().intValue()!=3){
             itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        Long result = redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, amount * -1);
        if(result>0){
            //更新库存成功
//            boolean mqResult = mqProducer.asyncReduceStock(itemId, amount);
//            if (!mqResult) {
//                redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, amount * 1);
//                return false;
//            }
            return true;
        }else if(result == 0) {
            redisTemplate.opsForValue().set("promo_item_stock_invalid_"+itemId, "true");
            return true;
        }else {
            ////更新库存失败
            redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, amount * 1);
            return false;
        }
    }

//    @Override
//    public boolean asyncDecreaseStock(Integer itemId, Integer amount){
//        Map<String, Object> bodyMap = new HashMap<>();
//        bodyMap.put("itemId", itemId);
//        bodyMap.put("amount", amount);
//        producer.sendMsg(JSONUtils.toJSONString(bodyMap));
//        return true;
//    }

//    @Override
//    public boolean increaseStock(Integer itemId, Integer amount) throws BusinessException {
//        redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, amount * 1);
//        return true;
//    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
       itemDOMapper.increaseSales(itemId,amount);
    }

    @Override
    public ItemModel getItemModelByIdInCache(Integer id) {
        ItemModel itemModel = redisTemplate.opsForValue().get("item_validate_" + id) == null ? null : Utils.decodeRedisData((LinkedHashMap)redisTemplate.opsForValue().get("item_validate_" + id));
        if (itemModel == null) {
            itemModel = this.getItemById(id);
            redisTemplate.opsForValue().set("item_validate_"+id, itemModel);
            redisTemplate.expire("item_validate_"+id, 10, TimeUnit.MINUTES);
        }
        return itemModel;
    }

    //初始化库存流水
    @Override
    @Transactional
    public String initStockLog(Integer itemId, Integer amount) {
        StockLogDO stockLogDO = new StockLogDO();
        stockLogDO.setStockLogId(UUID.randomUUID().toString().replace("-", ""));
        stockLogDO.setItemId(itemId);
        stockLogDO.setAmount(amount);
        stockLogDO.setStatus(1);

        stockLogDOMapper.insertSelective(stockLogDO);

        return stockLogDO.getStockLogId();
    }

    private ItemModel convertModelFromDataObject(ItemDO itemDO,ItemStockDO itemStockDO){
        ItemModel itemModel=new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());

        return itemModel;
    }
}
