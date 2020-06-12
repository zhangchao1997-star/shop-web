package com.dao;

import com.beans.GoodsInfo;
import com.beans.PageInfo;
import com.jdbc.DBUtil;
import com.utils.StrUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * 商品-dao层类
 */
public class GoodsDao {
    /**
     * 检查商品名是否重复
     * @param goodsName 商品名
     * @return 商品实体类对象
     */
    public GoodsInfo checkGoodsName(String goodsName) {
        String sql = "select * from goodsInfo where goodsName=?";
        return DBUtil.getSingleObject(sql,GoodsInfo.class,goodsName);
    }

    /**
     * 根据商品id查询商品信息
     * @param goodsId 商品id
     * @return 商品实体类对象
     */
    public GoodsInfo getGoodsById(int goodsId) {
        String sql = "select * from goodsInfo where id=?";
        return DBUtil.getSingleObject(sql,GoodsInfo.class,goodsId);
    }

    /**
     * 添加商品
     * @param goods 商品对象
     * @return 返回1成功，否则失败
     */
    public int addGoods(GoodsInfo goods) {
        Object[] o = {
          goods.getGoodsName(),
          goods.getBigCateId(),
          goods.getSmallCateId(),
          goods.getPrice(),
          goods.getDes(),
          goods.getUnit(),
          goods.getProducter(),
          goods.getPictureData(),
        };
        String sql = "insert into goodsInfo(goodsName,bigCateId,smallCateId,price,des,unit,producter,pictureData) values(?,?,?,?,?,?,?,?)";
        return DBUtil.addAndReturnId(sql,o);
    }

    /**
     * 删除某个商品
     * @param id 商品id
     * @return 如果为1，则删除成功
     */
    public int delGoods(int id) {
        String sql = "delete from goodsInfo where id=?";
        return DBUtil.update(sql,id);
    }

    /**
     * 修改商品信息
     * @param goods 商品实体类对象
     * @return 返回1修改成功，否则失败
     */
    public int updateGoods(GoodsInfo goods){
        String sql="update goodsInfo  set goodsName=? ,unit=? ,price=?,des=?,producter=?,bigCateId=?, smallCateId=? ,pictureData=? where id=?";
        Object [] params={
                goods.getGoodsName(),
                goods.getUnit(),
                goods.getPrice(),
                goods.getDes(),
                goods.getProducter(),
                goods.getBigCateId(),
                goods.getSmallCateId(),
                goods.getPictureData(),
                goods.getId()
        };
        return DBUtil.update(sql, params);
    }

    /**
     * 根据条件查询商品列表(带有分页)
     * @param select1 大分类id
     * @param select2 小分类id
     * @param goodsName 商品名称
     * @return 装有商品实体类对象的列表
     */
    public List<GoodsInfo> getGoods(String select1, String select2, String goodsName,PageInfo page) {
        List<GoodsInfo> goodsList = new ArrayList<>();
        String sql = "select * from goodsInfo where 1=1 ";
        //如果是开始点击商品维护，select1为null
         if(!"-1".equals(select1)&&select1!=null){
             sql+=" and bigcateId = "+select1;
         }
        if(!"-1".equals(select1)&&select2!=null){
            sql+=" and smallcateId = "+select2;
        }
        if(!StrUtil.isNullOrEmpty(goodsName)){
            sql+=" and goodsName = '"+goodsName+"'";
        }
        sql+=" limit "+page.getBeginRow()+","+page.getPageSize();
        String sql1 = "select cateName from cateInfo where id=?";
        Connection conn=null;
        ResultSet rs;
        rs = null;
        Statement stm=null;
        try {
            conn=DBUtil.getConn();
            stm=conn.createStatement();
            rs=stm.executeQuery(sql);
            while(rs.next()){
                GoodsInfo goods=new GoodsInfo();
                goods.setId(rs.getInt("id"));
                goods.setGoodsName(rs.getString("goodsName"));
                goods.setUnit(rs.getString("unit"));
                goods.setPrice(rs.getDouble("price"));
                goods.setBigCateId(rs.getInt("bigCateId"));
                goods.setSmallCateId(rs.getInt("SmallCateId"));
                goods.setDes(rs.getString("des"));
                goods.setProducter(rs.getString("producter"));
                goods.setPictureData(rs.getBytes("pictureData"));
                goodsList.add(goods);
            }
            for (GoodsInfo goods:goodsList){
                goods.setBigCateName(DBUtil.getScalar(sql1,goods.getBigCateId()));
                goods.setSmallCateName(DBUtil.getScalar(sql1,goods.getSmallCateId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            DBUtil.close(rs, stm, conn);
        }
        return goodsList;
    }
    /**
     * 获取商品的总数
     * @return 返回商品数
     */
    public int getGoodsCountPage(String select1, String select2, String goodsName) {
        String sql="select count(*) from goodsInfo";
        Connection conn=null;
        ResultSet rs;
        rs = null;
        Statement stm=null;
        int result = 0;
        //如果首次点击商品维护，select1为null
        if(!"-1".equals(select1)&&select1!=null){
            sql+=" where bigcateId = "+select1;
        }
        if(!"-1".equals(select1)&&select2!=null){
            sql+=" and smallcateId = "+select2;
        }
        if(!StrUtil.isNullOrEmpty(goodsName)){
            sql+=" and goodsName = '"+goodsName+"'";
        }
        try {
            conn=DBUtil.getConn();
            stm=conn.createStatement();
            rs=stm.executeQuery(sql);
            if(rs.next()){
              result = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            DBUtil.close(rs, stm, conn);
        }
        return result;
    }

    /**
     * 删除多个商品
     * @param ck_id 每个商品id
     */
    public void delMoreGoods(String[] ck_id) {
        String sql = "delete from goodsInfo where id=?";
        for (int i = 0; i < ck_id.length; i++) {
            DBUtil.update(sql,ck_id[i]);
        }
    }
}
