<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>管理员基本信息列表</title>
    <script type="text/javascript" src="js/jquery-1.8.0.js"></script>
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
        });
        function subForm(pageIndex) {
            window.location.href = "RoleServlet.do?flag=listAdmin&pageIndex="+ pageIndex;
        }
        function getPageIndex() {
            var v =document.getElementById("zhuandao");
            if(v.value<=0){
                return 1;
            }
            else
                return v.value;
        }
    </script>

    <link rel="stylesheet" type="text/css" href="css/maintable.css" />
</head>
<body>
<div class ="div_title">
    <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>管理人员基本信息列表</span></div>
</div>
<form method="post" name="form1" action="RoleServlet.do">
    <table  class="main_table">
        <tr>
            <th>账号</th>
            <th>状态</th>
            <th>用户角色</th>
            <th>最后更新日期</th>
            <th>操作</th>
        </tr>

        <c:forEach var="admin"  items="${adminList}"    >
            <tr>
                <td>${admin.adminName }</td>
                <td>
                    <c:if test="${admin.state==0 }">已锁定</c:if>
                    <c:if test="${admin.state==1 }">正常</c:if>
                    <c:if test="${admin.state==2 }">已删除</c:if>
                </td>
                <td>${admin.roleName }</td>
                <td>${admin.editDate }</td>
                <td>
                    <c:if test="${admin.state==0 }"></c:if>
                    <c:if test="${admin.state==1 }"><a href="RoleServlet.do?flag=adminRole&id=${admin.id}" >角色分配</a></c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>
<div class="div_page">
    <div class="div_page_left"> 共有 <label>${pageInfo.rowCount }</label> 条记录，当前第 <label>${pageInfo.pageIndex }</label>
        页，共 <label>${pageInfo.pageCount }</label> 页
    </div>

    <div class="div_page_right">
        <c:choose>

            <c:when test="${pageInfo.hasPre }">
                <button onclick="subForm(1)">首页</button>
                <button onclick="subForm(${pageInfo.pageIndex-1})">上一页</button>
            </c:when>
            <c:otherwise>
                首页
                上一页
            </c:otherwise>

        </c:choose>

        <c:choose>

            <c:when test="${pageInfo.hasNext}">
                <button onclick="subForm(${pageInfo.pageIndex+1})">下一页</button>
                <button onclick="subForm(${pageInfo.pageCount})">尾页</button>
            </c:when>

            <c:otherwise>
                下一页
                尾页
            </c:otherwise>
        </c:choose>
        <button onclick="subForm(getPageIndex()<=${pageInfo.pageCount}?getPageIndex():${pageInfo.pageIndex})">转到</button>
        第 <input name="pageIndex" id="zhuandao" class="page" value="${pageInfo.pageIndex }"> 页
    </div>

</div>

</body>
</html>

