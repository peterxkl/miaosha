package com.miaoshaproject.service.impl;


import com.miaoshaproject.dao.PromoDOMapper;
import com.miaoshaproject.dataproject.PromoDO;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.PromoService;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author hongjun500
 * @date 2019/2/7 15:27
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RedisTemplate redisTemplate;



    @Override
    public PromoModel getPromoByItemId(Integer itemId) {

        //获取对应秒杀商品的活动信息
        PromoDO promoDO=promoDOMapper.selectByItemId(itemId);

        //dataobject->model
        PromoModel promoModel=convertFromDateObject(promoDO);
        if(promoModel==null){
            return null;
        }

        //判断当前时间是否有秒杀活动即将开始或者正在进行
        DateTime now = new DateTime();
        if(promoModel.getStartTime().isAfterNow()){
            promoModel.setStatus(1);
        }else if(promoModel.getEndTime().isBeforeNow()){
            promoModel.setStatus(3);
        }else {
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    @Override
    public void publishPromo(Integer promoId) {
        //通过活动id获取活动
        PromoDO promo =  promoDOMapper.selectByPrimaryKey(promoId);
        if (promo.getItemId() == null || promo.getItemId().intValue() ==0) {
            return;
        }
        ItemModel itemModel = itemService.getItemById(promo.getItemId());

        //将库存同步到redis中
        redisTemplate.opsForValue().set("promo_item_stock_"+itemModel.getId(), itemModel.getStock());
    }

    @Override
    public String generateSecondKillToken(Integer promoId) {

        return null;
    }

    private PromoModel convertFromDateObject(PromoDO promoDO){
        if(promoDO==null){
            return null;
        }
        PromoModel promoModel=new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);

        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartTime(new DateTime(promoDO.getStartTime()));
        promoModel.setEndTime(new DateTime(promoDO.getEndTime()));

        return promoModel;
    }
}
