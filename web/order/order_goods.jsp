<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*,com.beans.*,java.text.*,com.dao.AdminDao" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>订单管理 订单信息</title>
    <script type="text/javascript"  src="js/jquery-1.8.0.js"></script>
    <link rel="stylesheet" type="text/css" href="css/edittable.css"  >
    <style type="text/css">
        input[type=text]{
            color: red;
        }

        /* 呈现数据的主表 */
        #main_table{
            margin:0 auto;
            width:99.5%;
            border: 1px solid #A8C7CE;
            border-collapse:collapse;
            margin-top:5px;
        }

        #main_table td{
            border: 1px solid #A8C7CE;
            border-collapse:collapse;
            font-size:12px;
            color:#295568;
            height:20px;
            padding-left:20px;
        }

        #main_table th{
            border: 1px solid #A8C7CE;
            border-collapse:collapse;
            background:#D3EAEF;
            font-size:12px;
            font-weight:normal;
            height:20px;
        }

    </style>
    <script type="text/javascript">
        $(function () {
            $(".form_btn").click(function () {
                window.history.go(-1);  //返回
            });
            $("#main_table tr").mouseover(function () {
                $(this).css("background", "#D3EAEF");
                $(this).siblings().css("background", "white");
            });

            $(".form_btn").hover(function(){
                    $(this).css("color","red").css("background","#6FB2DB");
                },

                function(){
                    $(this).css("color","#295568").css("background","#BAD9E3");
                });
        });
    </script>
</head>
<body>
<div class ="div_title">
    <div class="div_titlename"> <img src="images/san_jiao.gif" >
        <span>订单商品列表</span>
    </div>
</div>
<table id="main_table" >
    <tr>
        <th>商品名称</th>  <th>单位</th>  <th>单价</th>  <th>购买数量</th>
    </tr>
    <c:forEach var="orderGoods" items="${orderGoodsList}">
        <tr>
            <td>${orderGoods.goodsName}</td>
            <td>${orderGoods.unit}</td>
            <td>${orderGoods.price}</td>
            <td>${orderGoods.saleCount}</td>
        </tr>
    </c:forEach>
</table>


<div class ="div_title">
    <div class="div_titlename"> <img src="images/san_jiao.gif" >
        <span>订单详情列表</span>
    </div>
</div>
<table class="edit_table" >
    <tr>
        <td class="td_info">订单号</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${orderInfo.orderNo}">
        </td>
        <td class="td_info">邮费</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${orderInfo.postage}">
        </td>
    </tr>
    <tr>
        <td class="td_info">付款方式</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly" value="${orderInfo.payMethod}">
        </td>
        <td class="td_info">邮递方式</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${orderInfo.postMethod}">
        </td>
    </tr>
    <tr>
        <td class="td_info">下单日期</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${orderInfo.orderDate}">
        </td>
        <td class="td_info">邮寄地址</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${orderInfo.address}">
        </td>
    </tr>
    <tr>
        <td class="td_info">订单状态</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${orderInfo.orderState}">
        </td>
        <td class="td_info">发货日期</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"   value="${orderInfo.sendDate}">
        </td>
    </tr>
    <tr>
        <td class="td_info">订单金额</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${orderInfo.amount}"><br>
        </td>
        <td class="td_info">会员ID</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${orderInfo.memberLevel}">
        </td>
    </tr>
    <tr>
        <td class="td_info"></td><td colspan="3"><input type="button"class="form_btn" value="返回"></td>
    </tr>
</table>
</body>
</html>
