<%@ page language="java" import="java.util.*,com.dao.*,com.beans.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'picture.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">

</head>

<body>

<%
    int goodsId= Integer.parseInt(request.getParameter("goodsId"));
    GoodsDao dao=new GoodsDao();
    GoodsInfo goods=  dao.getGoodsById(goodsId);
    response.setContentType("image/jpg");

    ServletOutputStream outstream =response.getOutputStream();
    outstream.write(goods.getPictureData());
    out.clear();
    out=pageContext.pushBody();
%>

</body>
</html>