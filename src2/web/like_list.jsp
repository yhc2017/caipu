<%--
  Created by IntelliJ IDEA.
  User: sunyang
  Date: 2017/4/21
  Time: 下午4:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sun.vo.RecipeVo" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.sun.dao.DisDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>收藏列表</title>
</head>

<%  ArrayList<Integer> list=(ArrayList<Integer>)request.getAttribute("list");
    String id=request.getAttribute("id").toString();

    DisDAO disdao=new DisDAO();
    ArrayList<RecipeVo> l=new ArrayList<RecipeVo>();
    l=disdao.display(list);
%>
<script src="http://libs.baidu.com/jquery/2.1.1/jquery.min.js"></script>

<script>
    function del(){
        var list =new Array();
        var check=document.getElementsByName("like");
        for(var i=0;i<check.length;i++){
            if(check[i].checked==true){//检查checkbox是否已选中
                list.push(check[i].value);
            }
        }
        $.ajax({//通过ajax传给一个servlet处理
            type:"POST", //请求方式
            url:"/likeList", //请求路径
            data:{"list":list,
                "id":<%=id
                %>},
            traditional: true,//加上这个就可以传数组
            dataType : 'json',
            success:function(result){
            }
        });
        window.location.href="likeList?id="+<%=id%>;
    }
    function back(){
        window.location.href="rec_sys.jsp?page=1&id="+<%=id%>;
    }
</script>
<body>
<div id = "main">
    <table width="1000" border ="1" align = "center" style="border-collapse:collapse;">
        <tr align="center">
            <td colspan="9">
                食谱大全
            </td>
        </tr>
        <tr align="center">
            <td>感兴趣</td>
            <td>菜谱ID</td>
            <td>菜谱名称</td>
            <td width="400">做法</td>
            <td>特性</td>
            <td>提示</td>
            <td>调料</td>
            <td>原料</td>
        </tr>

            <%for(int i = 0 ; i<l.size();i++) {

            RecipeVo re = l.get(i);%>
        <tr align="center" >
            <td><input type = "checkbox"  value ='<%=re.getId() %>' name="like" /></td>
            <td><%=re.getId() %></td>
            <td><%=re.getName() %></td>
            <td width="400"><div name="thediv0"><%=re.getZuofa() %></div></td>
            <td><div name="thediv1"><%=re.getTexing() %></div></td>
            <td><div name="thediv2"><%=re.getTishi() %></div></td>
            <td><div name="thediv3"><%=re.getTiaoliao() %></div></td>
            <td><div name="thediv4"><%=re.getYuanliao() %></div></td>
        </tr>
            <%
            }
        %>

</div>

<div>
    <table align="center">
    <tr>
        <td><input type ="button" value ="删除" onclick="del()"/></td>
        <td><input type ="button" value ="返回" onclick="back()"/></td>
    </tr>
</table>
</div>
</body>
</html>
