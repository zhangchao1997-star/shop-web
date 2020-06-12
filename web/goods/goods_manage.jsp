<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*,com.beans.*,com.dao.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    CateDao cateDao = new CateDao();
    List<CateInfo> cateList = cateDao.getAllCate(0);
    request.setAttribute("cateList",cateList);
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>商品维护</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="js/jquery-1.8.0.js"></script>
    <link rel="stylesheet" type="text/css" href="css/maintable.css" ></link>
    <style type="text/css">

        /*显示图片*/
        #div_goods_detail{
            display:none;
            position: absolute;
        }
         /*表格上面的查询*/
        .select{
            border: 1px solid #A8C7CE;
            border-collapse:collapse;
            background:#D3EAEF;
            font-size:12px;
            font-weight:normal;
            height:25px;
            margin-top: 5px;
        }

    </style>
    <script type="text/javascript">
        $(function(){
            $("#ch_checkall,#top_ch_checkall").click(function(){
                if(this.checked){
                    $("input[name=ck_id]").attr("checked","checked");
                }else{
                    $("input[name=ck_id]").removeAttr("checked");
                }
            });

            $("table tr").mouseover(function(){
                $(this).css("background","#D3EAEF");
                $(this).siblings().css("background","white");
            });

            $("#select1").change(function(){
                $("#select2").empty();
                var pId=this.value;
                if(pId=="-1"){
                    $("#select2").append("<option>小分类</option>");
                }
                else {
                    $.ajax({
                        url: "CateServlet.do",
                        type: "post",
                        async: false,
                        dataType:"json",
                        data: {flag: "selSon", parentId: this.value},
                        success: function (subCateList) {
                            $.each(subCateList, function (k, cate) {
                                var str = "<option  value="+cate.id+">"+ cate.cateName + "</option>";
                                $("#select2").append(str);
                            });
                        }
                    });
                }
            });
            $("#select1").val("${param.select1}");
            $("#select1").change();
            $("#select2").val("${param.select2}");
        });

        function delMore() {
            //判断用户至少选了一项
            if($("input[name=ck_id]:checked").size()==0){
                alert("请至少选一项!");
            }
            else{
                if(confirm('确定要删除所选项吗')){
            //如果是进行多选删除的话就把flag的值置为delMore,在Servlet中就执行多选删除操作
                    $("#flag").val("delMore");
                    $("#pageIndex").val(${page.pageIndex}); //将本页所在的页数传给Servlet
                    document.form1.submit();
                }
            }
        }
        function subForm(pageIndex) {
            $("#pageIndex").val(pageIndex);
            document.form1.submit();
        }
        function getPageIndex() {
            var v =document.getElementById("zhuandao");
            if(v.value<=0){
                return 1;
            }
            else
                return v.value;
        }
        function delGoods(item) {
                if(confirm("确定要删除吗?"))
                {   //将该商品的id赋值给隐藏域里的id
                    $("#id").val(item);
                    //将隐藏域里的flag赋值为del，表示删除单个商品
                    $("#flag").val("del");
                    $("#pageIndex").val(${page.pageIndex}); //将本页所在的页数传给Servlet
                    //最后提交整个表单，连同可能有的查询条件
                    document.form1.submit();
                }
        }

        //根据已有元素,取得它的坐标
        function getElemPos(obj) {
            var pos = {
                "top": 0,
                "left": 0
            };
            if (obj.offsetParent) {
                while (obj.offsetParent) {
                    pos.top += obj.offsetTop;
                    pos.left += obj.offsetLeft;
                    obj = obj.offsetParent;
                }
            } else if (obj.x) {
                pos.left += obj.x;
            } else if (obj.x) {
                pos.top += obj.y;
            }

            return {
                x: pos.left,
                y: pos.top
            };
        }

        function showDetails(item, goodsId, desc) {
            var position = getElemPos(item);
            $("#div_goods_detail").show();
            $("#div_goods_detail").css("left", position.x - 150).css("top",
                position.y);

           	$("#goods_picture").attr("src","${pageContext.request.contextPath}/goods/picture.jsp?goodsId="+goodsId);
            $("#div_imgdesc").html("<center>" + desc + "</center>");
        }

        //隐藏商品图片div
        function hideDetails() {
            $("#div_goods_detail").hide();
        }
    </script>
</head>

<body>

<div class ="div_title">
    <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>商品列表</span></div>
    <div class="div_titleoper">
        <input type="checkbox" id="top_ch_checkall"/> 全选 <a href="goods/goods_add.jsp"> <img src="images/add.gif"/>添加 </a> <a href="javascript:delMore()"><img src="images/del.gif"/>删除</a> </div>
</div>
<form action="GoodsServlet.do"  name="form1" method="post">
    <input type="hidden" name="flag" id="flag" value="manage">
    <input type="hidden"name="pageIndex" id="pageIndex">
    <input type="hidden" name="id" id="id">
    <table class="main_table" >
        <div class="select">
            &nbsp;&nbsp;&nbsp;&nbsp;
            <select id="select1" name="select1">
                <option value="-1">大分类</option>
                <c:forEach var="cate" items="${cateList }">
                    <option <c:if test="${cate.id==param.select1}">selected</c:if> value="${cate.id}">${ cate.cateName}</option>
                </c:forEach>
            </select>
             &nbsp; &nbsp;
            <select id="select2" name="select2">
                <option value="-1">小分类</option>
            </select>
            <script>
                $("#select1").change();
            </script>
            &nbsp;&nbsp;&nbsp;&nbsp;
            商品名称 <input type="text" name="goodsName" value="${param.goodsName}">
            <input type="submit" value="查询">
        </div>
        <tr>
            <th><input type="checkbox" id="ch_checkall" /></th>	<th>名称</th> 	<th>单位</th>	<th>单价</th>  <th>大分类</th> 	<th>小分类</th>  <th>操作</th>
        </tr>
        <c:forEach var="goods" items="${goodsList}">
            <tr>
                <td>
                    <input type="checkbox" name="ck_id" id="ch_check" value="${goods.id}"/>
                </td>

                <td>
                        ${goods.goodsName}
                </td>
                <td>
                       ${goods.unit}
                </td>
                <td>
                        ${goods.price}
                </td>
                <td>
                        ${goods.bigCateName}
                </td>
                <td>
                        ${goods.smallCateName}
                </td>
                <td>
                    <a href="javascript:void(0)"
                       onmouseover="showDetails(this,'${goods.id }','${goods.des }')"
                       onmouseout="hideDetails()"
                    >查看</a>|

                     <a href="GoodsServlet.do?flag=forUpdate&id=${goods.id}">修改</a> |

                     <a href="javascript:delGoods(${goods.id})">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
<div class="div_page" >
    <div class="div_page_left">    共有 <label>${page.rowCount}</label> 条记录，当前第 <label>${page.pageIndex}</label> 页，共 <label> ${page.pageCount }</label> 页	</div>
    <div class="div_page_right" >
        <c:choose>
            <c:when test="${page.hasPre }">
                <a href="javascript:subForm(1)">首页</a>
                <a href="javascript:subForm(${page.pageIndex-1 })">上一页</a>  &nbsp;  &nbsp;  &nbsp;  &nbsp;
            </c:when>
            <c:otherwise>
                首页
                上一页
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${page.hasNext }">
                <a href="javascript:subForm(${page.pageIndex+1 })">下一页</a>
                <a href="javascript:subForm(${page.pageCount })">尾页</a>
            </c:when>
            <c:otherwise>
                下一页
                尾页
            </c:otherwise>
        </c:choose>
        <button onclick="subForm(getPageIndex()<=${page.pageCount}?getPageIndex():${page.pageIndex})">转到</button>  第 <input name="pageIndex" id="zhuandao" class="page" value="${page.pageIndex }"> 页
    </div>
</div>
    <div id="div_goods_detail">
        <img id="goods_picture" width="140" height="190" />
        <div id="div_imgdesc"></div>
    </div>
</form>
<script type="text/javascript">
    var msg = '${msg}';
    if(msg != ""){
        alert(msg);
    }
</script>
</body>
</html>
