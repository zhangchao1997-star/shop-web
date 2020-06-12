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
    <title>角色管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="js/jquery-1.8.0.js"></script>
    <link rel="stylesheet" type="text/css" href="css/maintable.css" >

    <script type="text/javascript">
      $(function () {
          $("table tr").mouseover(function(){
              $(this).css("background","#D3EAEF");
              $(this).siblings().css("background","white");
          });
      });
      function del(id) {
          if(confirm("确定要删除吗?")==true){
              window.location.href="RoleServlet.do?flag=del&id="+id;
          }
      }
    </script>
    <style type="text/css">
        /**
          备注特别长,进行相应的处理
         */
        .desc { width:200px;
            word-break:break-all;
            display:-webkit-box;
            -webkit-line-clamp:1;
            -webkit-box-orient:vertical;
            overflow:hidden;}
    </style>
</head>

<body>

    <div class ="div_title">
        <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>系统中的角色列表</span></div>
    </div>
    <table class="main_table" >
        <tr>
            <th>角色名称</th> <th>角色描述</th> <th>操作</th>
        </tr>
        <c:forEach var="role" items="${roleList}">
            <tr>
                <td>${role.roleName}</td>
                <td>
                     <div class="desc">
                         <a title="${role.des}">${role.des}</a>
                     </div>
                </td>
                <td>
                    <a href="RoleServlet.do?flag=forUpdate&id=${role.id}">修改</a> |&nbsp;
                    <a href="javascript:del(${role.id})">删除</a> |&nbsp;
                    <a href="RoleServlet.do?flag=rolePowerAssignment&roleId=${role.id}">角色权限分配</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <script type="text/javascript">
        var msg = "${msg}";
        if(msg!=""){
            alert(msg);
        }
    </script>
</body>
</html>
