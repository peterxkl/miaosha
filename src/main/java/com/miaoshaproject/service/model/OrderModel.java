package com.miaoshaproject.service.model;

import java.math.BigDecimal;

/**
 * @author hongjun500
 * @date 2019/2/5 16:32
 */
//用户下单的交易模型
public class OrderModel {
    private String id;               //订单id;
    private Integer userId;          //购买的用户id;
    private Integer itemId;          //购买的商品id;
    private Integer promoId;         //若非空，则表示是以秒杀方式下单
    private BigDecimal itemPrice;    //商品价格;若promoId非空，则是秒杀商品价格
    private Integer amount;          //购买数量;
    private BigDecimal orderPrice;    //订单价格;若promoId非空，则是秒杀商品价格




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
