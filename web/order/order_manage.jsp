<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>订单列表</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="js/jquery-1.8.0.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="css/maintable.css" ></link>
    <style type="text/css">

        /*表格上面的查询*/
        .select{
            border: 1px solid #A8C7CE;
            border-collapse:collapse;
            background:#D3EAEF;
            font-size:12px;
            font-weight:normal;
            height:25px;
            margin-top: 5px;
        }

    </style>
    <script type="text/javascript">
        $(function() {
            $("#ch_checkall,#top_ch_checkall").click(function () {
                if (this.checked) {
                    $("input[name=ck_id]").attr("checked", "checked");
                } else {
                    $("input[name=ck_id]").removeAttr("checked");
                }
            });

            $("table tr").mouseover(function () {
                $(this).css("background", "#D3EAEF");
                $(this).siblings().css("background", "white");
            });

        });
        function delMore() {
            //判断用户至少选了一项
            if($("input[name=ck_id]:checked").size()==0){
                alert("请至少选一项!");
            }
            else{
                if(confirm('确定要删除所选项吗')){
                    //如果是进行多选删除的话就把flag的值置为delMore,在Servlet中就执行多选删除操作
                    $("#flag").val("delMore");
                    $("#pageIndex").val(${page.pageIndex}); //将本页所在的页数传给Servlet
                    document.form1.submit();
                }
            }
        }
        function subForm(pageIndex) {
                $("#pageIndex").val(pageIndex);
                document.form1.submit();
        }
        function getPageIndex() {
            var v =document.getElementById("zhuandao");
            if(v.value<=0){
                return 1;
            }
            else
                return v.value;
        }
        function send(item) {
            $("#flag").val("sendGoods");
            $("#id").val(item);
            $("#pageIndex").val(${page.pageIndex}); //将本页所在的页数传给Servlet
            document.form1.submit();
        }
    </script>
</head>

<body>

<div class ="div_title">
    <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>订单列表</span></div>
    <div class="div_titleoper">
        <input type="checkbox" id="top_ch_checkall"/> 全选 <a href="javascript:void(0)"> <img src="images/add.gif"/>添加 </a> <a href="javascript:delMore()"><img src="images/del.gif"/>删除</a> </div>
</div>

<form action="OrderServlet.do" id="form1" name="form1" method="post">
    <input type="hidden" name="flag" id="flag" value="manage">
    <input type="hidden" name="pageIndex" id="pageIndex">
    <input type="hidden" name="id" id="id">
    <table class="main_table" >
        <div class="select">
            &nbsp; &nbsp; &nbsp;
            订单号 <input type="text" name="orderNo" value="${param.orderNo}">
            下单日期从:<input name="beginTime"   onclick="WdatePicker()"  value="${param.beginTime}"> 到
            <input name="endTime"  onclick="WdatePicker()"  value="${param.endTime}">
            订单状态:
             <select name="select1" id="select1">
                 <option value="-1">请选择</option>
                 <c:forEach items="${orderStateSet}" var="orderState">
                     <option value="${orderState}" <c:if test="${param.select1.equals(orderState)}">selected</c:if>>${orderState}</option>
                 </c:forEach>
             </select>
            <input type="submit" value="查询">
        </div>
        <tr>
            <th><input type="checkbox" id="ch_checkall" /></th>	<th>订单号</th> 	<th>付款方式</th>	<th>订单金额</th>  <th>订单状态</th> 	<th>邮寄方式</th>  <th>生成日期</th> <th>发货地址</th> <th>操作</th>
        </tr>
        <c:forEach var="order" items="${orderList}">
            <tr>
                <td>
                    <input type="checkbox" name="ck_id" id="ch_check" value="${order.id}"/>
                </td>

                <td>
                        ${order.orderNo}
                </td>
                <td>
                        ${order.payMethod}
                </td>
                <td>
                        ${order.amount}
                </td>
                <td>
                        ${order.orderState}
                </td>
                <td>
                        ${order.postMethod}
                </td>
                <td>
                        ${order.orderDate}
                </td>
                <td>
                        ${order.address}
                </td>
                <td>
                    <a href="OrderServlet.do?flag=lookUp&id=${order.id}">查看</a> |
                    <c:if test="${order.orderState=='未支付'||order.orderState=='已发货'}"></c:if>
                    <c:if test="${order.orderState=='已支付'}">
                        <a href="javascript:send(${order.id})">发货</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div class="div_page" >
        <div class="div_page_left">    共有 <label>${page.rowCount}</label> 条记录，当前第 <label>${page.pageIndex}</label> 页，共 <label> ${page.pageCount }</label> 页	</div>
        <div class="div_page_right" >
            <c:choose>
                <c:when test="${page.hasPre }">
                    <button onclick="subForm(1)">首页</button>
                    <button onclick="subForm(${page.pageIndex-1 })">上一页</button>  &nbsp;  &nbsp;  &nbsp;  &nbsp;
                </c:when>
                <c:otherwise>
                    首页
                    上一页
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${page.hasNext }">
                    <button onclick="subForm(${page.pageIndex+1 })">下一页</button>
                    <button onclick="subForm(${page.pageCount })">尾页</button>
                </c:when>
                <c:otherwise>
                    下一页
                    尾页
                </c:otherwise>
            </c:choose>
            <button onclick="subForm(getPageIndex()<=${page.pageCount}?getPageIndex():${page.pageIndex})">转到</button>  第 <input name="pageIndex" id="zhuandao" class="page" value="${page.pageIndex }"> 页
        </div>
    </div>
</form>
<script type="text/javascript">
    var msg = '${msg}';
    if(msg != ""){
        alert(msg);
    }
</script>
</body>
</html>
