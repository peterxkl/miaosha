package com.miaoshaproject.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.PromoModel;

import java.util.LinkedHashMap;

public class Utils {

    public static ItemModel decodeRedisData(LinkedHashMap linkedHashMap) {
        ItemModel itemModel = new ItemModel();
        linkedHashMap.forEach((key, value) -> {
            if (key.equals("promoModel")) {
                PromoModel promoModel = JSONObject.parseObject(JSON.toJSONString(value), PromoModel.class);
                itemModel.setPromoModel(promoModel);
                linkedHashMap.put(key, null);
            }
        });
        ItemModel itemModel1 = JSON.parseObject(JSON.toJSONString(linkedHashMap), ItemModel.class);
        itemModel1.setPromoModel(itemModel.getPromoModel());
        return itemModel1;
    }
}
