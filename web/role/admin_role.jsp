<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="css/edittable.css"  ></link>
    <link rel="stylesheet" type="text/css" href="css/validate.css"  ></link>
    <title>角色分配</title>
    <script>
        $(function () {
            $("input[type=text],textarea").focus(function () {
                $(this).addClass("input_focus");
            }).blur(function () {
                $(this).removeClass("input_focus");
            });

            $(".form_btn").hover(function () {
                    $(this).css("color", "red").css("background", "#6FB2DB");
                },

                function () {
                    $(this).css("color", "#295568").css("background", "#BAD9E3");
                });
        });
    </script>

    <link rel="stylesheet" type="text/css" href="css/maintable.css"/>
</head>
<body>
<div class="div_title">
    <div class="div_titlename"><img src="images/san_jiao.gif">
        <span>角色分配</span>&nbsp;&nbsp;&nbsp;&nbsp;
        当前用户：${admin.adminName}
    </div>

</div>
<form method="post" name="form1" action="RoleServlet.do">
    <input type="hidden" name="id" value="${admin.id}">
    <input type="hidden" name="flag" value="updateAdminRole" >
    <table class="main_table">
        <c:forEach var="role" items="${roleList}">
            <tr>
                <td>
                    <input type="radio" name="roleId" value="${role.id}"
                           <c:if test="${admin.roleId==role.id}">checked="checked"</c:if>>${role.roleName}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td>
                <input class="form_btn" type="submit" value="提交" id="form_btn"/>
            </td>
        </tr>
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
