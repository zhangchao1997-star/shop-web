package com.servlet;

import com.beans.OrderGoods;
import com.beans.OrderInfo;
import com.beans.PageInfo;
import com.dao.OrderDao;
import com.utils.PageUtil;
import com.utils.StrUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet("/OrderServlet.do")
public class OrderServlet extends HttpServlet {
    OrderDao orderDao = new OrderDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flag = request.getParameter("flag");
        if("manage".equals(flag)){
            manage(request,response);
        }
        else if("del".equals(flag)){
            del(request,response);
        }
        else if("delMore".equals(flag)){
            delMore(request,response);
        }
        else if("lookUp".equals(flag)){
            lookUp(request,response);
        }
        else if("sendGoods".equals(flag)){
            sendGoods(request,response);
        }
    }
    //发货
    private void sendGoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到前台发过来的订单id
        int id= Integer.parseInt(request.getParameter("id"));
        int result = orderDao.updateGoodsState(id);
        if(result == 1){
            request.setAttribute("msg","发货成功");
            manage(request, response);
        }
    }

    //查看订单详细信息
    private void lookUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        OrderInfo orderInfo = orderDao.getOrderInfo(id);
        if(orderInfo.getMemberLevel()==null){
            orderInfo.setMemberLevel("非会员(普通用户)");
        }
        List<OrderGoods> orderGoodsList = orderDao.getOrderGoodsInfo(id);
        request.setAttribute("orderInfo",orderInfo);
        request.setAttribute("orderGoodsList",orderGoodsList);
        request.getRequestDispatcher("/order/order_goods.jsp").forward(request,response);
    }

    //删除多个订单
    private void delMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] memberIds = request.getParameterValues("ck_id");
        int result = 0;
        result = orderDao.delMembers(memberIds);
        if(result!=0){
            request.setAttribute("msg","删除成功");
            manage(request, response);
        }
    }

    //根据订单id删除订单
    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int result = orderDao.delOrder(id);
        if(result == 1){
            request.setAttribute("msg","删除成功");
            manage(request, response);
        }
    }

    //订单管理
    private void manage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String order = request.getParameter("orderNo");
        String orderNo = (order==null)?null:order.trim(); //去掉可能存在的空格
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String select1 = request.getParameter("select1");
        int pageSize = 5;
        int rowCount = orderDao.getOrderCount(orderNo,beginTime,endTime,select1);
        int pageIndex = 1;
        String pageIndexStr = request.getParameter("pageIndex");
        if(!StrUtil.isNullOrEmpty(pageIndexStr)){
            pageIndex = Integer.parseInt(pageIndexStr);
        }
        PageInfo page = PageUtil.getPageInfo(pageSize,rowCount,pageIndex);
        //在数据库中查询订单列表
        List<OrderInfo> orderList = orderDao.getOrders(orderNo,beginTime,endTime,select1,page);
        //在数据库里找到订单列表所有的状态，并做回显
        Set<String> orderStateSet = orderDao.getOrderState();
        request.setAttribute("orderStateSet",orderStateSet);
        request.setAttribute("orderList",orderList);
        request.setAttribute("page",page);
        request.getRequestDispatcher("/order/order_manage.jsp").forward(request,response);
    }
}
