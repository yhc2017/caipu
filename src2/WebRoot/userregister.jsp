<%--
  Created by IntelliJ IDEA.
  User: sunyang
  Date: 2016/12/8
  Time: 下午12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
<form action="/UserRegister" method="post">
    <table align = "center" border="1" style="border-collapse: collapse;">
        <tr>
            <td colspan="2">用户注册</td>
        </tr>
        <tr>
            <td>用户名：</td>
            <td><input type="text" name="userName" /></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="userPassword" /></td>
        </tr>
        <tr>
            <td class="tdstyle" colspan="2">
                <input type="submit" value="注册" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>
