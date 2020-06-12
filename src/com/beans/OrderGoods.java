package com.beans;
/***
 * author zhangchao
 * 订单商品类
 */
public class OrderGoods {
    private Integer id;//订单商品id
    private Integer orderId;//订单id
    private Integer goodsId;//商品id
    private String  goodsNumber;
    private String  goodsName;//商品名
    private String  unit; //单位
    private Double  price;//单价
    private String  saleCount;//购买的数量

     public Integer getId() {
         return id;
     }

     public void setId(Integer id) {
         this.id = id;
     }

     public Integer getOrderId() {
         return orderId;
     }

     public void setOrderId(Integer orderId) {
         this.orderId = orderId;
     }

     public Integer getGoodsId() {
         return goodsId;
     }

     public void setGoodsId(Integer goodsId) {
         this.goodsId = goodsId;
     }

     public String getGoodsNumber() {
         return goodsNumber;
     }

     public void setGoodsNumber(String goodsNumber) {
         this.goodsNumber = goodsNumber;
     }

     public String getGoodsName() {
         return goodsName;
     }

     public void setGoodsName(String goodsName) {
         this.goodsName = goodsName;
     }

     public String getUnit() {
         return unit;
     }

     public void setUnit(String unit) {
         this.unit = unit;
     }

     public Double getPrice() {
         return price;
     }

     public void setPrice(Double price) {
         this.price = price;
     }

     public String getSaleCount() {
         return saleCount;
     }

     public void setSaleCount(String saleCount) {
         this.saleCount = saleCount;
     }
 }
