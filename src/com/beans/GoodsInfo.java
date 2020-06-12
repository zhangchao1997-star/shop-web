package com.beans;

import java.util.Date;
/***
 * author zhangchao
 * 商品实体类
 */
public class GoodsInfo {
    private Integer id; //商品id
    private String goodsName;//商品名
    private double price; //商品价格
    private String des;//商品描述
    private String unit;//计量单位
    private String producter;//生产厂商
    private Date editDate;//商品最后修改日期
    private byte[] pictureData;//商品图片
    private Integer bigCateId;//商品所属一级分类
    private Integer smallCateId;//商品所属二级分类

    //以下两个字段在数据库中没有,是用于关联查询分类名称的
    private String bigCateName;  //大分类名称
    private String smallCateName; //小分类名称

    public String getBigCateName() {
        return bigCateName;
    }

    public void setBigCateName(String bigCateName) {
        this.bigCateName = bigCateName;
    }

    public String getSmallCateName() {
        return smallCateName;
    }

    public void setSmallCateName(String smallCateName) {
        this.smallCateName = smallCateName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProducter() {
        return producter;
    }

    public void setProducter(String producter) {
        this.producter = producter;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public byte[] getPictureData() {
        return pictureData;
    }

    public void setPictureData(byte[] pictureData) {
        this.pictureData = pictureData;
    }

    public Integer getBigCateId() {
        return bigCateId;
    }

    public void setBigCateId(Integer bigCateId) {
        this.bigCateId = bigCateId;
    }

    public Integer getSmallCateId() {
        return smallCateId;
    }

    public void setSmallCateId(Integer smallCateId) {
        this.smallCateId = smallCateId;
    }
}
