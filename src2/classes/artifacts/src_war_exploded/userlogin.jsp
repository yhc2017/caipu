<%--
  Created by IntelliJ IDEA.
  User: sunyang
  Date: 2016/12/8
  Time: 下午1:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <% String message = (String)(request.getAttribute("message"));%>
    <% if(message==null)
    {message=" ";}%>
<form id = "form1" action = "/Userlogin" method = "post">
        <table align = "center" border="1" style="border-collapse:collapse;">
            <tr align = "center">
                <td colspan="2">用户登录</td>
            </tr>
            <tr>
                <td>用户名：</td>
                <td>
                    <input type = "text" name = "userName"/>
                </td>

            </tr>
            <tr>
                <td>密码：</td>
                <td>
                    <input type = "password" name = "userPassword"/>
                    <span style = "color:red; font-size:13px;" id = "td2"><%=message %></span>
                </td>
            </tr>
            <tr align = "center">
                <td colspan = "2">
                    <input type = "button" name="register" value ="注册" onclick="location.href='./userregister.jsp'" />
                    <input type = "submit" name="login" value ="登录"/>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
