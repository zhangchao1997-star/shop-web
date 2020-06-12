<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>用户修改</title>

    <link rel="stylesheet" type="text/css" href="css/edittable.css"  ></link>
    <link rel="stylesheet" type="text/css" href="css/validate.css"  ></link>
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
        function showInfo(item) {
            document.getElementById(item.id+"_msg").innerText ="";
            document.getElementById(item.id+"_msg").className="validate_info";
        }
        //验证用户名
        function validateAdminName(item) {
            var result=true;
            var reg=/^[a-zA-Z0-9]{4,15}$/;
            if(item.value==""){
                result=false;
                document.getElementById(item.id+"_msg").innerText = "用户名不能为空";
                document.getElementById(item.id+"_msg").className="validate_error";
            }
            else {
                if (!reg.test(item.value)) {
                    result = false;
                    document.getElementById(item.id+"_msg").innerText = "用户名格式非法(4-15位数字或大小写英文)";
                    document.getElementById(item.id+"_msg").className="validate_error";
                }else{
                    document.getElementById(item.id+"_msg").innerText = "验证通过";
                    document.getElementById(item.id+"_msg").className="validate_ok";
                }
            }
            return result;
        }
        //验证密码
        function validatePassword(item) {
            var result=true;
            var reg=/^[a-zA-Z0-9]{6,20}$/;
            if(item.value==""){
                result=false;
                document.getElementById(item.id+"_msg").innerText = "密码不能为空";
                document.getElementById(item.id+"_msg").className="validate_error";
            }
            else {
                if (!reg.test(item.value)) {
                    result = false;
                    document.getElementById(item.id+"_msg").innerText = "密码格式非法!(数字或英文,6-20位) ";
                    document.getElementById(item.id+"_msg").className="validate_error";
                } else {
                    document.getElementById(item.id+"_msg").innerText = "验证通过";
                    document.getElementById(item.id+"_msg").className="validate_ok";
                }
            }
            return result;
        }
        //验证重复密码
        function validateConfirm(item) {
            var result=true;
            if(item.value==""){
                result=false;
                document.getElementById(item.id+"_msg").innerText = "请输入重复密码";
                document.getElementById(item.id+"_msg").className="validate_error";
            }
            else{
                if(item.value!=document.getElementById("password").value){
                    result=false;
                    document.getElementById(item.id+"_msg").innerText = "两次输入的密码不一致";
                    document.getElementById(item.id+"_msg").className="validate_error";
                }
                else{
                    document.getElementById(item.id+"_msg").innerText = "验证通过";
                    document.getElementById(item.id+"_msg").className="validate_ok";
                }
            }
            return result;
        }
        function validate() {
            var result = true;
            //验证用户名
            if(validateAdminName(document.getElementById("adminName"))==false){
                result=false;
            }

            //验证密码
            if(validatePassword(document.getElementById("password"))==false){
                result=false;
            }

            //验证重复密码
            if(validateConfirm(document.getElementById("pwdconfirm"))==false){
                result=false;
            }

            if(result==true){
                return confirm("确定要提交吗");
            }

            return result;
        }
    </script>
</head>

<body>
<div class ="div_title">
    <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>管理员信息修改</span></div>
</div>

<form action="AdminServlet.do" method="post" onsubmit="return validate()">
    <input type="hidden" name="flag" value="update">
    <input type="hidden" name="id" value="${admin.id}">
    <table class="edit_table" >
        <tr>
            <td class="td_info">用户账号:</td>
            <td class="td_input_short">
                <input type="text" class="txtbox" id="adminName" value="${admin.adminName}" name="adminName" onclick="showInfo(this)" onblur="validateAdminName(this)"  />&nbsp;&nbsp;&nbsp;
                <label class="validate_info" id="adminName_msg" >4-15位；只限数字(0-9)和英文(a-z),不区分大小写</label>
            </td>
        </tr>
        <tr>
            <td class="td_info">用户密码:</td>
            <td>
                <input type="text"  class="txtbox"  name="password" value="${admin.password}" id="password" onclick="showInfo(this)" onblur="validatePassword(this)"/>&nbsp;&nbsp;&nbsp;
                <label  class="validate_info" id="password_msg" >数字或英文,6-20位</label>
            </td>
        </tr>
        <tr>
            <td class="td_info">确认密码:</td>
            <td><input type="text" class="txtbox" id="pwdconfirm"  name="password" value="${admin.password}" onclick="showInfo(this)" onblur="validateConfirm(this)" />&nbsp;&nbsp;&nbsp;
                <label  class="validate_info"  id="pwdconfirm_msg">请输入确认密码</label>
            </td>
        </tr>
        <tr>
            <td class="td_info">备注信息:</td>
            <td><textarea rows="4" cols="27" name="note" class="txtarea">${admin.note}</textarea> </td>
            <td><label></label></td>
        </tr>
        <tr>
            <td class="td_info"> </td>
            <td>
                <input class="form_btn" type="submit" value="提交" />
                <input type="reset"  class="form_btn" value="重置" /> &nbsp;&nbsp;&nbsp;
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript">
    var msg="${msg}";
    if(msg!=""){
        alert(msg);
    }
</script>
</body>
</html>
