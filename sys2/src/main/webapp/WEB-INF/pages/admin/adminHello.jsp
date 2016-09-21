<%@ page import="com.jelly.sso.module.User" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/include/topInc.jsp" %>
<html>
<body>
<h2>!!!
<%
    User user = (User)request.getSession().getAttribute("J_TOKEN");
    if(user != null){
        out.write(user.getName());
    }else{
        out.write("anonymous");
    }
%>,
    Welcome to access admin pages!!!</h2>
<ul>
    <li><a href="${host}?J_TOKEN=${J_TOKEN}">goSys2</a></li>
</ul>
</body>
</html>
