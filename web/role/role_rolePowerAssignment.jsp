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
    <title>角色权限分配</title>
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
            $("tr td input[name=parentMenu]").click(function(){
                var arr=$(this).parent().next().children();
                for(var i=0;i<arr.length;i++){
                    arr[i].checked=this.checked;
                }
            });
        });
    </script>
</head>

<body>

<div class ="div_title">
    <div class="div_titlename">
        <img src="images/san_jiao.gif" >
        <span>角色权限管理</span>
    </div>
</div>
<form action="RoleServlet.do" method="post">
    <input type="hidden" name="flag" value="updateRoleMenu">
    <input type="hidden" name="roleId" value="${roleId}">
    <input type="hidden" name="roleId" value="${roleInfo.id}">
    <table class="main_table" >
        <tr>
            <th>一级菜单</th> <th>二级菜单</th>
        </tr>
        <c:forEach var="parentMenu" items="${menuList}">
            <tr>
                <td>
                    <input type="checkbox" name="menu" value="${parentMenu.id}" id="${parentMenu.id}" onclick="checkSubItem(this)">${parentMenu.menuName}
                </td>
                <td>
                    <c:forEach var="menu" items="${parentMenu.subMenuList}">
                        <input type="checkbox" name="menu" class="${parentMenu.id}" value="${menu.id}" onclick="checkParent(${parentMenu.id})">${menu.menuName}<br/>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
    </table>
    <input class="form_btn" style="margin-left: 150px;margin-top: 20px" type="submit" onclick="return check()" value="保存">
</form>
<script type="text/javascript">
    var msg = "${msg}";
    if(msg!=""){
        alert(msg);
    }
    Array.prototype.contains=function(e){
        for(var i=0;i<this.length;i++){
            if(this[i]==e){
                return true;
            }
        }
        return false;
    }
     $(function () {
         menuShow();
     });
    function menuShow() {
        var menuStr = "${menuStr}";
        if(menuStr!=""){
            menuStr = menuStr.substring(0,menuStr.length-1);
            var menu = menuStr.split(",");
            $("input[name=menu]").each(function () {
                for (var i=0;i<menu.length;i++){
                    if(menu.contains(this.value)){
                        this.checked = true;
                    }
                }
            });
        }
    }

    function check() {
         var v = document.getElementsByName("menu");
         var sum = 0;
         for (var i=0;i<v.length;i++){
             if(v[i].checked){
                 sum++;
             }
         }
         if(sum==0){
             alert("菜单框里的菜单至少选一项");
             menuShow();
             return false;
         }else{
             return confirm("确定保存吗?");
         }
    }

    //勾选父级复选框,让子项全选中或取消,注意,子项中加了一个class属性 ,值为父级checkbox的 id,用这样的方式建立起来的关联
    function checkSubItem(txtMenu){
        $("."+txtMenu.id).attr("checked",txtMenu.checked);
    }

    //勾选子级复选框后,处理父级复选框的状态 ,传过来的参数值是父级复选框的id
    function checkParent(parentId){
        $("#"+parentId).removeAttr("checked");

        $("."+parentId).each(function(){
            if(this.checked){
                $("#"+parentId).attr("checked","checked");
                return;
            }
        });
    }

</script>
</body>
</html>
