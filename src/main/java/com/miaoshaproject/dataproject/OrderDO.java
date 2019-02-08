package com.miaoshaproject.dataproject;

public class OrderDO {

    private String id;


    private Integer userId;


    private Integer itemId;

    private Integer promo_id;

    private Double itemPrice;


    private Integer amount;


    private Double orderPrice;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
        return promo_id;
    }

    public void setPromoId(Integer promo_id) {
        this.promo_id = promo_id;
    }

    public Double getItemPrice() {
        return itemPrice;
    }


    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }


    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }
}