<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*,com.dao.*,com.beans.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>商品添加->添加二级分类</title>

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
        //验证二级分类名是否合法
        function validateSecondClassName(item) {
            var result = true;
            var reg = /^\S{2,20}$/;
            if(item.value ==""){
                result = false;
                document.getElementById(item.id+"_msg").innerHTML = "二级分类名不能为空";
                document.getElementById(item.id+"_msg").className="validate_error";
            }else{
                if(!reg.test(item.value)){
                    result = false;
                    document.getElementById(item.id+"_msg").innerHTML = "二级分类名非法(2-20位非空白字符)";
                    document.getElementById(item.id+"_msg").className="validate_error";
                }else {
                    onload =  $.ajax({
                        url: "CateServlet.do",
                        type: "POST",
                        async: false,
                        data: {className: $("#secondClassName").val(),flag:"validateSecForAdd"},
                        success: function (data) {
                            if(data==-1) {
                                result = false;
                                document.getElementById(item.id + "_msg").innerText = "该二级分类已存在";
                                document.getElementById(item.id + "_msg").className = "validate_error";
                            }
                        }
                    });
                    if(result){
                        document.getElementById(item.id + "_msg").innerText = "验证通过";
                        $("#secondClassName_msg").removeClass("validate_error").addClass("validate_ok");
                    }
                }
            }
            return result;
        }
        function validate() {
            var result = true;
            if(validateSecondClassName(document.getElementById("secondClassName"))==false){
                result = false;
            }
            if(result == false){
                return false;
            }else{
                return confirm("确定提交吗?");
            }
        }
    </script>
</head>

<body>
<div class ="div_title">
    <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>商品管理 添加二级分类</span></div>
</div>

<form action="CateServlet.do" method="post" onsubmit="return validate()">
    <input type="hidden" name="flag" value="addSecond">
    <table class="edit_table" >
        <tr>
            <td class="td_info">父级分类:</td>
            <td class="td_input_short">
                <%
                    CateDao cateDao = new CateDao();
                    List<CateInfo> firCate = cateDao.getFirCate();
                    request.setAttribute("firCate",firCate);
                %>
                <select name="firName">
                    <option value="-1">--请选择所属一级分类--</option>
                    <c:forEach var="cate" items="${firCate}" >
                        <option <c:if test="${cate.id==param.firName}">selected</c:if> value="${cate.id}">${cate.cateName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_info">分类名称:</td>
            <td class="td_input_short">
                <input type="text" class="txtbox" id="secondClassName" value="${param.secondClassName}" name="secondClassName" onclick="showInfo(this)" onblur="validateSecondClassName(this)"  />&nbsp;&nbsp;&nbsp;
                <label class="validate_info" id="secondClassName_msg" >长度2-20位非空白字符</label>
            </td>
        </tr>
        <tr>
            <td class="td_info">分类描述:</td>
            <td><textarea rows="4" cols="27" name="note" class="txtarea">${param.note}</textarea> </td>
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
    var msg = "${msg}";
    if(msg!=""){
        alert(msg);
    }
</script>
</body>
</html>
