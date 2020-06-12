<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.dao.CateDao" %>
<%@ page import="com.beans.CateInfo" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

    int id = Integer.parseInt(request.getParameter("id"));
    CateDao cateDao = new CateDao();
    CateInfo cate = cateDao.getCateById(id);
    request.setAttribute("cate",cate);
    request.setAttribute("id",id);
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>商品维护->一级分类修改</title>

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
            document.getElementById(item.id+"_msg").innerHTML = "";
            document.getElementById(item.id+"_msg").className="validate_info";
        }
        //验证一级分类名是否合法
        function validateFirstClassName(item) {
            var result = true;
            var reg = /^\S{2,20}$/;
            if(item.value ==""){
                result = false;
                document.getElementById(item.id+"_msg").innerHTML = "一级分类名不能为空";
                document.getElementById(item.id+"_msg").className="validate_error";
            }else{
                if(!reg.test(item.value)){
                    result = false;
                    document.getElementById(item.id+"_msg").innerHTML = "一级分类名非法(2-20位非空白字符)";
                    document.getElementById(item.id+"_msg").className="validate_error";
                }else {
                    onload =$.ajax({
                        url: "CateServlet.do",
                        type: "POST",
                        async: false,
                        data: {classId:"${id}",newclassName:$("#firstClassName").val(),flag:"validate"},
                        success: function (data) {
                            if(data=="-1") {
                                result = false;
                                document.getElementById(item.id + "_msg").innerText = "该一级分类已存在";
                                document.getElementById(item.id + "_msg").className = "validate_error";
                            }
                        }
                    });
                    if(result){
                        document.getElementById(item.id + "_msg").innerText = "√";
                        document.getElementById(item.id + "_msg").className = "validate_ok";
                    }
                }
            }
            return result;
        }
        function validate() {
            var result = true;
            if(validateFirstClassName(document.getElementById("firstClassName"))==false){
                result = false;
            }
            if(result == false){
                return false;
            }else{
                return confirm("确定保存吗?");
            }
        }
    </script>
</head>

<body>
<div class ="div_title">
    <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>商品管理 一级分类修改</span></div>
</div>


<form action="CateServlet.do" method="post" onsubmit="return validate()">
    <input type="hidden" name="flag" value="updateFir">
    <input type="hidden" name="id" value="${id}">
    <table class="edit_table" >
        <tr>
            <td class="td_info">分类名称:</td>
            <td class="td_input_short">
                <input type="text" class="txtbox" id="firstClassName" value="${cate.cateName}" name="firstClassName" onclick="showInfo(this)" onblur="validateFirstClassName(this)"  />&nbsp;&nbsp;&nbsp;
                <label class="validate_info" id="firstClassName_msg" >长度2-20位非空白字符</label>
            </td>
        </tr>
        <tr>
            <td class="td_info">分类描述:</td>
            <td><textarea rows="4" cols="27" name="des" class="txtarea">${cate.des}</textarea> </td>
        </tr>
        <tr>
            <td class="td_info"> </td>
            <td>
                <input class="form_btn" type="submit" value="保存修改" />
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
