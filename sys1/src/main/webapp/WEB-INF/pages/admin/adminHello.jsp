<%@ page import="com.jelly.sso.module.User" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/include/topInc.jsp" %>
<html>
<body>
<h2>!!!
<%
    User user = (User)request.getSession().getAttribute("J_TOKEN_USER");
    if(user != null){
        out.write(user.getName());
    }else{
        out.write("anonymous");
    }
%>, Welcome to access admin pages!!!</h2>
<ul>
    <li><a href="http://www.sys1.com:9091">goSys1</a></li>
    <li><a href="http://www.sys1.com:9091/?J_TOKEN=${J_TOKEN}">goSys1-${J_TOKEN}</a></li>
    <li><a href="http://www.sys1.com:9091/admin?J_TOKEN=${J_TOKEN}">goSys1/admin-${J_TOKEN}</a></li>
    <li><a href="http://www.sys2.com:9092">goSys2</a></li>
    <li><a href="http://www.sys2.com:9092/?J_TOKEN=${J_TOKEN}">goSys2-${J_TOKEN}</a></li>
    <li><a href="http://www.sys2.com:9092/admin?J_TOKEN=${J_TOKEN}">goSys2/admin-${J_TOKEN}</a></li>
</ul>
</body>
</html>
