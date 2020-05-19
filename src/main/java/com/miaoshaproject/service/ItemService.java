package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.ItemModel;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

/**
 * @author hongjun500
 * @date 2019/2/4 11:29
 */
public interface ItemService {
    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;
    //商品列表浏览
    List<ItemModel> listItem();
    //商品详情浏览
    ItemModel getItemById(Integer id);

    //库存扣减
    boolean decreaseStock(Integer itemId,Integer amount)throws BusinessException;

//    //异步更新库存
//    boolean asyncDecreaseStock(Integer itemId,Integer amount);
//
//    //库存回滚
//    boolean increaseStock(Integer itemId,Integer amount)throws BusinessException;

    //商品销量增加
    void increaseSales(Integer itemId,Integer amount)throws BusinessException;

    //
    ItemModel getItemModelByIdInCache(Integer id);

    //初始化库存流水
    String initStockLog(Integer itemId, Integer amount);
}
