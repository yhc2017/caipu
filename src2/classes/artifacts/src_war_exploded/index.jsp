<%--
  Created by IntelliJ IDEA.
  User: sunyang
  Date: 2016/12/8
  Time: 上午11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <style type="text/css">
      body {color:red}
      h1 {text-align:center}
      p {color:rgb(0,0,255);text-align:center}
    </style>
    <title>主页面</title>
    <script type="text/javascript">
      function Clock() {
        var date = new Date();
        this.year = date.getFullYear();
        this.month = date.getMonth() + 1;
        this.date = date.getDate();
        this.day = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[date.getDay()];
        this.hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        this.minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        this.second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        this.toString = function() {
          return "现在是:" + this.year + "年" + this.month + "月" + this.date + "日 " + this.hour + ":" + this.minute + ":" + this.second + " " + this.day;
        };

        this.toSimpleDate = function() {
          return this.year + "-" + this.month + "-" + this.date;
        };

        this.toDetailDate = function() {
          return this.year + "-" + this.month + "-" + this.date + " " + this.hour + ":" + this.minute + ":" + this.second;
        };

        this.display = function(ele) {
          var clock = new Clock();
          ele.innerHTML = clock.toString();
          window.setTimeout(function() {clock.display(ele);}, 1000);
        };
      }
    </script>
  </head>
  <body>
  <h1>欢迎使用菜谱推荐系统</h1>
  <p><a href="./userregister.jsp">注册</a></p>
  <p><a href="./userlogin.jsp">登录</a></p>
  <div id="clock" align="center"></div>
  <script type="text/javascript">
    var clock = new Clock();
    clock.display(document.getElementById("clock"));
  </script>
  </body>
</html>
