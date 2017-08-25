<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/23
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>'
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>搜索页面</title>
</head>
<body>
    <form action="<%=request.getContextPath()%>/toSearch" method="post">
        <table>
            <tr>
                <td>
                    <input type="text" name="index"/>
                    <input type="submit" value="搜索">
                </td>
            </tr>
        </table>
    </form>
    <table border="1">
        <c:forEach var="user" items="${list}">
            <tr>
                <td>
                    用户id为:${user.userId}<br/>
                    用户姓名为:${user.userName},今年年龄:${user.userAge},性别为:${user.userSex}
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
