<%@ page import="com.sun.vo.RecipeVo" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.sun.dao.DisDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: sunyang
  Date: 2016/12/8
  Time: 下午5:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%  List<Map.Entry<Integer,Double>> top =(List<Map.Entry<Integer,Double>>)(request.getAttribute("list"));
    String id=request.getAttribute("id").toString();
    int topNum=20;//指定显示前多少条
    int[] list=new int[topNum];
    double[] list1=new double[topNum];
    for (int j = 0; j < topNum; j++) {
        list[j]=top.get(j).getKey();
        list1[j]=top.get(j).getValue();
    }
    DisDAO disdao=new DisDAO();
    ArrayList<RecipeVo> l=new ArrayList<RecipeVo>();
    l=disdao.display(list);
%>
<head>
    <title>推荐结果</title>
</head>
<script type="text/javascript">
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
                <td>菜谱ID</td>
                <td>菜谱名称</td>
                <td width="400">做法</td>
                <td>特性</td>
                <td>提示</td>
                <td>调料</td>
                <td>原料</td>
                <td>推荐度</td>
            </tr>

            <%for(int i = 0 ; i<topNum;i++) {

                RecipeVo re = l.get(i);
                double simDegree=list1[i];%>
            <tr align="center" >
                <td><%=re.getId() %></td>
                <td><%=re.getName() %></td>
                <td width="400"><%=re.getZuofa() %></td>
                <td><%=re.getTexing() %></td>
                <td><%=re.getTishi() %></td>
                <td><%=re.getTiaoliao() %></td>
                <td><%=re.getYuanliao() %></td>
                <td><%=simDegree%>></td>
            </tr>
            <%
                }
            %>

</div>
<div align="center">
    <input type="button" value="返回" id="return" onclick="back()" >
</div>
</body>
</html>
