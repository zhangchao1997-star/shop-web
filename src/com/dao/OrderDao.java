package com.dao;

import com.beans.OrderGoods;
import com.beans.OrderInfo;
import com.beans.PageInfo;
import com.commons.Constant;
import com.jdbc.DBUtil;
import com.utils.StrUtil;

import java.util.List;
import java.util.Set;
/**
 * 订单-dao层类
 */
public class OrderDao {
    /**
     * 获取订单总数
     * @param orderNo 订单编号
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param select1 订单状态
     * @return 订单总数
     */
    public int getOrderCount(String orderNo,String beginTime,String endTime,String select1){
        String sql = "select count(*) from orderInfo where 1=1";
        if(!StrUtil.isNullOrEmpty(orderNo)){
            sql+=" and orderNo='"+orderNo+"'";
        }
        if(!StrUtil.isNullOrEmpty(beginTime)){
            sql+=" and orderDate>='"+beginTime+"'";
        }
        if(!StrUtil.isNullOrEmpty(endTime)){
            sql+=" and orderDate<='"+endTime+"'";
        }
        if(!"-1".equals(select1)&&select1!=null){
            sql+=" and orderState='"+select1+"'";
        }
        long l = DBUtil.getScalar(sql);
        return (int)l;
    }

    /**
     * 带有分页的全部订单信息
     * @param page 页实体类
     * @return 装有订单实体类对象的List集合
     */
    public List<OrderInfo> getOrders(PageInfo page) {
        String sql = "select * from orderInfo limit ?,?";
        return DBUtil.getList(sql,OrderInfo.class,page.getBeginRow(),page.getPageSize());
    }

    public List<OrderInfo> getOrders(String orderNo,String beginTime,String endTime,String select1,PageInfo page) {
        String sql = "select * from orderInfo where 1=1";
        if(!StrUtil.isNullOrEmpty(orderNo)){
            sql+=" and orderNo='"+orderNo+"'";
        }
        if(!StrUtil.isNullOrEmpty(beginTime)){
            sql+=" and orderDate>='"+beginTime+"'";
        }
        if(!StrUtil.isNullOrEmpty(endTime)){
            sql+=" and orderDate<='"+endTime+"'";
        }
        if(!"-1".equals(select1)&&select1!=null){
            sql+=" and orderState='"+select1+"'";
        }
        sql+=" order by orderDate desc limit "+page.getBeginRow()+", "+page.getPageSize();
        return DBUtil.getList(sql,OrderInfo.class);
    }

    /**
     * 根据订单id删除订单
     * @param id 订单id
     * @return 如果为1，则成功
     */
    public int delOrder(int id) {
        String sql = "delete from orderInfo where id=?";
        return DBUtil.update(sql,id);
    }

    /**
     * 删除多个订单
     * @param memberIds 每个订单id
     * @return
     */
    public int delMembers(String[] memberIds) {
        int result = 0;
        String sql = "delete from orderInfo where id=?";
        for (String id:memberIds){
            result+=DBUtil.update(sql,id);
        }
        return result;
    }

    /**
     * 根据订单id查找订单信息
     * @param id 订单id
     * @return 订单实体类对象
     */
    public OrderInfo getOrderInfo(int id) {
       String sql="select orderInfo.*,memberInfo.memberLevel from orderInfo left join memberInfo on orderInfo.memberId=memberInfo.id where orderInfo.id=?";
       return DBUtil.getSingleObject(sql,OrderInfo.class,id);
    }

    /**
     * 根据订单id查询订单商品信息
     * @param id 订单id
     * @return 订单商品实体类对象
     */
    public List<OrderGoods> getOrderGoodsInfo(int id) {
        String sql = "select * from orderGoods where orderId=?";
        return DBUtil.getList(sql,OrderGoods.class,id);
    }

    /**
     * 发货,根据订单id修改订单状态
     * @param id 订单id
     * @return 返回1成功
     */
    public int updateGoodsState(int id) {
        String sql = "update orderInfo set orderState=? where id=?";
        return DBUtil.update(sql, Constant.EnumOrderState.已发货.getValue(),id);
    }

    /**
     * 找寻订单表中所有订单的状态(已发货，未支付，已支付)
     * @return
     */
    public Set<String> getOrderState() {
        String sql = "select orderState from orderInfo";
        return DBUtil.getScalarColSet(sql);
    }
}
