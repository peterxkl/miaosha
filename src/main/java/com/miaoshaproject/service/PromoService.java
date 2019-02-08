package com.miaoshaproject.service;

import com.miaoshaproject.service.model.PromoModel;

/**
 * @author hongjun500
 * @date 2019/2/7 15:24
 */
public interface PromoService {

    //根据itemId获取即将进行或者正在进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);

    //
}
