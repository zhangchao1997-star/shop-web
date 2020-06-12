<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*,com.beans.*,java.text.*,com.dao.AdminDao" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    MemberInfo member = (MemberInfo) request.getAttribute("member");
    String [] memberIp=member.getIp().split(",");
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>会员管理 会员信息</title>
    <script type="text/javascript"  src="js/jquery-1.8.0.js"></script>
    <link rel="stylesheet" type="text/css" href="css/edittable.css"  >
    <style type="text/css">
        input[type=text]{
           color: red;
        }
    </style>
    <script type="text/javascript">
        $(function () {
           $(".form_btn").click(function () {
               window.history.go(-1);
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
    <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>会员管理 会员信息</span>
    </div>
</div>
<table class="edit_table" >
    <tr>
        <td class="td_info">会员账号</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${member.memberNo}">
        </td>
        <td class="td_info">会员姓名</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${member.memberName}">
        </td>
    </tr>
    <tr>
        <td class="td_info">联系电话</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${member.phone}">
        </td>
        <td class="td_info">电子邮箱</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${member.email}">
        </td>
    </tr>
    <tr>
        <td class="td_info">注册日期</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${member.registerDate}">
        </td>
        <td class="td_info">身份证号</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${member.idCard}">
        </td>
    </tr>
    <tr>
        <td class="td_info">登录次数</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${member.loginCounts}">
        </td>
        <td class="td_info">最后登录时间</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"   value="${member.lastLoginDate}">
        </td>
    </tr>
    <tr>
        <td class="td_info">常用ip</td>
        <td style="font-size: 18px">
            <%
               for (int i = 0;i<memberIp.length;i++){
                   request.setAttribute("ip",memberIp[i]);
            %>
            <input type="text" readonly="readonly"  value="${ip}"><br>
            <%
                }
            %>
        </td>
        <td class="td_info">会员等级</td>
        <td style="font-size: 18px">
            <input type="text" readonly="readonly"  value="${member.memberLevel}">
        </td>
    </tr>
    <tr>
        <td></td><td colspan="3"><input type="button"class="form_btn" value="返回"></td>
    </tr>
</table>
</body>
</html>
