<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*,com.beans.*,com.dao.*" pageEncoding="UTF-8"%>
<%@ page import="com.utils.StrUtil" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    CateDao cateDao = new CateDao();
    List<CateInfo> cateList = cateDao.getAllCate(0);
    request.setAttribute("cateList",cateList);

    if(!StrUtil.isNullOrEmpty(request.getParameter("goodsId"))){
        int goodsId=Integer.parseInt(request.getParameter("goodsId"));
        GoodsInfo goods=new GoodsDao().getGoodsById(goodsId);
        request.setAttribute("goods", goods);
    }

%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>修改商品</title>
    <link rel="stylesheet" type="text/css" href="css/edittable.css"  ></link>
    <link rel="stylesheet" type="text/css" href="css/validate.css"  ></link>
    <script type="text/javascript"  src="js/jquery-1.8.0.js"></script>
    <style type="text/css">
        /*模拟文件控件的文本框*/
        .file-box{
            position:relative;width:320px
        }

        /*模拟文件控件的提交按钮*/
        .filebtn{
            background-color:#FFF;
            border:1px solid #CDCDCD;
            height:20px;
            width:70px;
        }
        /*把真的文件控件藏起来*/
        .file{
            position:absolute;
            top:0;
            right:40px;
            filter:alpha(opacity:0);opacity: 0;
            width:280px;
            height:30px;
        }

        label{
            margin-left:10px;
        }

    </style>
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

            $("#picture").change(function () {
                var img = $("#picture").val();
                // 1 校验格式
                var idx = img.lastIndexOf(".");
                var lastName = img.substring(idx, img.length);
                var name = lastName.toLowerCase();
                if (name != ".jpg" && name != ".jpeg" && name != ".png") {
                    document.getElementById("picture_msg").innerHTML="图片格式不支持,请选择JPG/JPEG/PNG格式的图片";
                    document.getElementById("picture_msg").className="validate_error";
                }
            });

            $(function(){
                //点击文件选择框后,把选中的文件路径,展示在假的文本框内
                $("#pic_instead").hide();
                $("#picture").change(function(){
                    $("#pic_instead").val(this.value);
                });


                $("#select1,#select2").change(
                    function () {
                        document.getElementById("password_msg").innerHTML="";
                    }
                );

                //点击重置后,把页面信息清除
                $("#btnreset").click(function(){
                    clearInfo();
                    return false;
                } );

                function clearInfo(){
                    window.document.location = "${pageContext.request.contextPath}/goods/goods_add.jsp?goodsId=${goods.id}";
                }
            });

            $("#select1").change(function(){
                $("#select2").empty();
                var pId=this.value;
                if(pId=="-1"){
                    $("#select2").append("<option>请选择</option>");
                }

                else {
                    $.ajax({
                        url: "CateServlet.do",
                        type: "post",
                        async: false,
                        dataType:"json",
                        data: {flag: "selSon", parentId: this.value},
                        success: function (cateList) {
                            $.each(cateList, function (k, cate) {
                                var str = "<option value='" + cate.id + "'>" + cate.cateName + "</option>";
                                $("#select2").append(str);
                            });
                            $("#select2").val("${goods.smallCateId}");
                        }

                    });
                }
            });
            $("#select1").change();
        });
        function showInfo(item) {
            document.getElementById(item.id+"_msg").innerHTML="";
        }
        //验证商品名称是否合法
        function validateGoods(item) {
            var reg = /^\S{2,20}$/;
            var result = true;
            if(item.value==""){
                result = false;
                document.getElementById(item.id+"_msg").innerHTML="商品名不能为空";
                document.getElementById(item.id+"_msg").className="validate_error";
            }
            else {
                if(!reg.test(item.value)){
                    result = false;
                    document.getElementById(item.id+"_msg").innerHTML="商品名非法(2-20位非空白字符)";
                    document.getElementById(item.id+"_msg").className="validate_error";
                }
                else{
                    $.ajax({
                        url:"GoodsServlet.do",
                        type:"post",
                        async:false,
                        data:{flag:'checkUpdate',goodsName:item.value,oldGoodsName:'${goods.goodsName}'},
                        success:function (data) {
                            if(data=="-1"){
                                result = false;
                                document.getElementById(item.id+"_msg").innerHTML="商品名已存在";
                                document.getElementById(item.id+"_msg").className="validate_error";
                            }else{
                                document.getElementById(item.id+"_msg").innerHTML="√";
                                document.getElementById(item.id+"_msg").className="validate_ok";
                            }
                        }
                    });
                }
            }
            return result;
        }
        //验证计量单位是否合法
        function validateUnit(item) {
            var reg=/^\S{1,10}$/;
            var result = true;
            if(item.value==""){
                result = false;
                document.getElementById(item.id+"_msg").innerHTML="计量单位不能为空";
                document.getElementById(item.id+"_msg").className="validate_error";
            }else{
                if(!reg.test(item.value)){
                    result = false;
                    document.getElementById(item.id+"_msg").innerHTML="计量单位非法(1-10位非空白字符)";
                    document.getElementById(item.id+"_msg").className="validate_error";
                }else{
                    document.getElementById(item.id+"_msg").innerHTML="√";
                    document.getElementById(item.id+"_msg").className="validate_ok";
                }
            }
            return result;
        }
        //验证商品价格
        function validatePrice(item) {
            var result = true;
            var reg = /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/;
            if(item.value==""){
                result = false;
                document.getElementById(item.id+"_msg").innerHTML="商品价格不能为空";
                document.getElementById(item.id+"_msg").className="validate_error";
            }else{
                if(!reg.test(item.value)){
                    result = false;
                    document.getElementById(item.id+"_msg").innerHTML="商品价格非法";
                    document.getElementById(item.id+"_msg").className="validate_error";
                }else{
                    document.getElementById(item.id+"_msg").innerHTML="√";
                    document.getElementById(item.id+"_msg").className="validate_ok";
                }
            }
            return result;
        }
        //验证生产厂商填写是否合法
        function validateProductor(item) {
            var result = true;
            if(item.value==""){
                result = false;
                document.getElementById(item.id+"_msg").innerHTML="生产厂商不能为空";
                document.getElementById(item.id+"_msg").className="validate_error";
            }else {
                document.getElementById(item.id+"_msg").innerHTML="√";
                document.getElementById(item.id+"_msg").className="validate_ok";
            }
            return result;
        }
        //总验证
        function validate() {
            var result = true;
            //验证商品名
            if(validateGoods(document.getElementById("goodsName"))==false){
                result = false;
            }
            //验证计量单位
            if(validateUnit(document.getElementById("unit"))==false){
                result = false;
            }
            //验证商品价格
            if(validatePrice(document.getElementById("price"))==false){
                result = false;
            }
            //验证生产厂商
            if(validateProductor(document.getElementById("productor"))==false){
                result = false;
            }
            if(result == false){
                return false;
            }else{
                return confirm("确定提交吗?");
            }
        }

        function setImagePreview(docObj, localImagId, imgObjPreview, width, height) {
            if (docObj.files && docObj.files[0]) { //火狐下，直接设img属性
                imgObjPreview.style.display = 'block';
                imgObjPreview.style.width = width;
                imgObjPreview.style.height = height;
                //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
                imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
            } else { //IE下，使用滤镜
                docObj.select();
                var imgSrc = document.selection.createRange().text;
                //必须设置初始大小
                localImagId.style.width = width;
                localImagId.style.height = height;
                //图片异常的捕捉，防止用户修改后缀来伪造图片
                try {
                    localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                    localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                } catch (e) {
                    alert("您上传的图片格式不正确，请重新选择!");
                    return false;
                }
                imgObjPreview.style.display = 'none';
                document.selection.empty();
            }
        }
    </script>
</head>

<body>
<div class ="div_title">
    <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>商品管理 修改商品信息</span></div>
</div>

<form action="GoodsServlet.do" method="post" enctype="multipart/form-data" onsubmit="return validate()">
    <input type="hidden" name="flag" value="update">
    <input type="hidden" name="id" value="${goods.id}">
    <table class="edit_table" >
        <tr>
            <td class="td_info">商品名称:</td>
            <td class="td_input_short">
                <input type="text" class="txtbox" id="goodsName" value="${goods.goodsName}" name="goodsName" onclick="showInfo(this)" onblur="validateGoods(this)"  />&nbsp;&nbsp;&nbsp;
                <label class="validate_info" id="goodsName_msg" >2-20位非空白字符</label>
            </td>
        </tr>
        <tr>
            <td class="td_info">所属分类:</td>
            <td>
                <select id="select1" name="select1">
                    <option value="-1">请选择</option>
                    <c:forEach var="cate" items="${cateList }">
                        <option <c:if test="${cate.id==goods.bigCateId}">selected</c:if> value="${cate.id}">${ cate.cateName}</option>
                    </c:forEach>
                </select>

                <select id="select2" name="select2">
                    <option value="-1">请选择</option>
                </select>
                <script>
                    $("#select1").change();
                </script>
            </td>
        </tr>
        <tr>
            <td class="td_info">计量单位:</td>
            <td><input type="text" class="txtbox" id="unit"  name="unit" value="${goods.unit}" onclick="showInfo(this)" onblur="validateUnit(this)" />&nbsp;&nbsp;&nbsp;
                <label  class="validate_info"  id="unit_msg">1-10位非空字符</label>
            </td>
        </tr>
        <tr>
            <td class="td_info">商品价格:</td>
            <td><input type="text" class="txtbox" id="price"  name="price" value="${goods.price}" onclick="showInfo(this)" onblur="validatePrice(this)" />&nbsp;&nbsp;&nbsp;
                <label  class="validate_info"  id="price_msg">不能为空,以元为单位,可以是小数</label>
            </td>
        </tr>
        <tr>
            <td class="td_info">生产厂商:</td>
            <td><input type="text" class="txtbox" id="productor"  name="producter" value="${goods.producter}" onclick="showInfo(this)" onblur="validateProductor(this)" />&nbsp;&nbsp;&nbsp;
                <label  class="validate_info"  id="productor_msg">非空字符</label>
            </td>
        </tr>
        <tr>
            <td class="td_info">商品图片:</td>
            <td>
                <div class="file-box">
                    <input type='button' class='filebtn' value='找找...' />
                    <input type="file" name="picture" class="file" id="picture" size="28"
                           onchange="setImagePreview(this,localImag,preview,'100px','125px');" />
                </div>
                <div id="localImag" style="margin-left: 24px" ></div>
                <img id="preview" alt="请上传图片" style="width:121px;height: 75px" src="${pageContext.request.contextPath}/goods/picture.jsp?goodsId=${goods.id}"  />
                <label  class="validate_info"  id="picture_msg"></label>
            </td>
        </tr>
        <tr>
            <td class="td_info">备注信息:</td>
            <td>
                <textarea rows="4" cols="27" name="des" class="txtarea">${goods.des}</textarea>
            </td>
        </tr>
        <tr>
            <td class="td_info"> </td>
            <td>
                <input class="form_btn" type="submit" value="提交" />
                <input type="reset" id="btnreset" class="form_btn" value="重置" /> &nbsp;&nbsp;&nbsp;
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
