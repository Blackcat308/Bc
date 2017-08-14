<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/12
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/index3.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery-1.8.2.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/fenye.css" type="text/css">
<head>
    <title>用户页面</title>
</head>
<body>
<table>
    <tr>
        <th colspan="10">用户管理系统</th>
    </tr>
    <tr>
        <td>BlackCat</td>
        <td>用户ID</td>
        <td>用户姓名</td>
        <td>用户年龄</td>
        <td>用户性别</td>
    </tr>
    <c:forEach items="${userList}" var="user">
        <tr>
            <td><input type="radio" value="${user.userId}" name="sid" class="ck"/></td>
            <td>${user.userId}</td>
            <td>${user.userName}</td>
            <td>${user.userAge}</td>
            <td>${user.userSex}</td>
        </tr>
    </c:forEach>
</table>
<div>
    <center>
        <div class="jogger">
            <span class="disabled"> &lt; </span>
            <a href="<%=request.getContextPath()%>/list?page=1" class="current">1</a>
            <a href="<%=request.getContextPath()%>/list?page=2">2</a>
            <a href="<%=request.getContextPath()%>/list?page=3">3</a>
            <a href="<%=request.getContextPath()%>/list?page=4">...</a>
            <a href="<%=request.getContextPath()%>/list?page=100">100</a>
                &gt; </div>
        当前为第${p.pageNum}页/共${p.pageSize}条数据
    </center>
</div>
</body>
</html>
