package com.beans;

import java.util.List;

/***
 * author zhangchao
 * 一二级分类实体类
 */
public class CateInfo {
     private Integer id; //分类id
     private String cateName; //分类名称
     private String des; //分类描述
     private int parentId; //父级分类id
     private List<CateInfo> subCateInfo; //子分类

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<CateInfo> getSubCateInfo() {
        return subCateInfo;
    }

    public void setSubCateInfo(List<CateInfo> subCateInfo) {
        this.subCateInfo = subCateInfo;
    }
}
