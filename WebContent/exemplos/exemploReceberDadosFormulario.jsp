<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<% 
	String str = "Olá " + request.getParameter("name");
	out.print(str); 
%>

<br>
<p>
URL = <%= request.getRequestURL() %>
</p>
</body>
</html>