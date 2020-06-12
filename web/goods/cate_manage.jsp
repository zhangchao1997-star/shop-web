<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.beans.CateInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>商品分类维护</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="js/jquery-1.8.0.js"></script>
    <link rel="stylesheet" type="text/css" href="css/maintable.css" >
    <link rel="stylesheet" type="text/css" href="css/validate.css" >
    <link rel="stylesheet" type="text/css" href="css/edittable.css"  >
    <script type="text/javascript">
        $(function () {
            $("table tr").mouseover(function(){
                $(this).css("background","#D3EAEF");
                $(this).siblings().css("background","white");
            });
        });
        //删除二级分类
        function delSec(item) {
            if(confirm("确定删除吗?"))
            document.location.href = "CateServlet.do?flag=delSec&id="+item;
        }
        function delFir(item) {
            if(confirm("确定删除吗?"))
                document.location.href = "CateServlet.do?flag=delFir&id="+item;
        }
    </script>
</head>

<body>

<div class ="div_title">
    <div class="div_titlename">
        <img src="images/san_jiao.gif" >
        <span>商品分类维护</span>
    </div>
</div>
    <table class="main_table" >
        <tr>
            <th colspan="2">一级分类</th> <th colspan="2">二级分类</th>
        </tr>
        <c:forEach var="cate" items="${cateList}">
            <tr>
                <td>
                    ${cate.cateName}
                </td>
                <td>
                    <a href="goods/bigcate_update.jsp?id=${cate.id}">修改</a>|&nbsp;
                    <a href="javascript:delFir('${cate.id}')">删除</a>
                </td>
                <td>
                    <c:forEach var="sub_cate" items="${cate.subCateInfo}">
                        ${sub_cate.cateName}<br>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach var="sub_cate" items="${cate.subCateInfo}">
                        <a href="goods/smallcate_update.jsp?id=${sub_cate.id}">修改</a>|&nbsp;
                        <a href="javascript:delSec('${sub_cate.id}')">删除</a><br/>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>
<script type="text/javascript">
    var msg = "${msg}";
    if(msg!=""){
        alert(msg);
    }
</script>
</body>
</html>
