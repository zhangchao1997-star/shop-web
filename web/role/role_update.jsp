<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>角色修改</title>
    <link rel="stylesheet" type="text/css" href="css/edittable.css"  >
    <link rel="stylesheet" type="text/css" href="css/validate.css"  >
    <script type="text/javascript"  src="js/jquery-1.8.0.js"></script>
    <script>
        $(function () {
            $("input[type=text],textarea").focus(function(){
                $(this).addClass("input_focus");
            }).blur(function(){
                $(this).removeClass("input_focus");
            });

            $(".form_btn").hover(function(){
                    $(this).css("color","red").css("background","#6FB2DB");
                },

            function(){
                $(this).css("color","#295568").css("background","#BAD9E3");
            });
        });
        function showInfo() {
            document.getElementById("roleName_msg").innerText = "";
        }
        //验证用户名
        function validateConfirm(item) {
            var result = true;
            if(item.value==""){
                result=false;
                document.getElementById(item.id+"_msg").innerText = "角色名不能为空";
                document.getElementById(item.id+"_msg").className="validate_error";
            }
            else{
                onload =  $.ajax({
                    url: "RoleServlet.do",
                    type: "POST",
                    async: false,
                    data: {roleName: $("#roleName").val(),flag:"validate",id:${role.id}},
                    success: function (data) {
                        if(data==-1) {
                            result = false;
                            document.getElementById(item.id + "_msg").innerText = "该角色名已存在";
                            document.getElementById(item.id + "_msg").className = "validate_error";
                        }
                    }
                });
                if(result)
                {
                    document.getElementById(item.id + "_msg").innerText = "验证通过";
                    document.getElementById(item.id+"_msg").className="validate_ok";
                }
            }
            return result;
        }
        function validate() {
            var result = true;
            if(validateConfirm(document.getElementById("roleName")) == false){
                result = false;
            }
            if(result==true){
                return confirm("确定提交吗?");
            }
            return result;
        }
    </script>
</head>

<body>
<div class ="div_title">
    <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>角色修改</span></div>
</div>

<form action="RoleServlet.do" method="post" onsubmit="return validate()">
    <input type="hidden" name="flag" value="update">
    <input type="hidden" name="id" value="${role.id}">
    <table class="edit_table" >
        <tr>
            <td class="td_info">角色名称:</td>
            <td><input type="text" class="txtbox" id="roleName"  name="roleName" value="${role.roleName}" onclick="showInfo()" onblur="validateConfirm(this)" />&nbsp;&nbsp;&nbsp;
                <label  class="validate_info"  id="roleName_msg"></label>
            </td>
        </tr>
        <tr>
            <td class="td_info">角色描述:</td>
            <td><textarea rows="4" cols="27" name="des" class="txtarea">${role.des}</textarea> </td>
        </tr>
        <tr>
            <td class="td_info"> </td>
            <td>
                <input class="form_btn" type="submit" value="保存" />
                <input type="reset"  class="form_btn" value="重置" /> &nbsp;&nbsp;&nbsp;
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
