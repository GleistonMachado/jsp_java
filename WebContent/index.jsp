<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet"  href="resources/css/style.css">
<title>Login</title>
</head>
<body>
	
	<div class="login-page">
  		<div class="form">
		    <form action="LoginServelet" method="post" class="login-form">
		      <input type="text" id="login" name="login" placeholder="Seu login">
		      <input type="password" id="senha" name="senha" placeholder="Sua senha">
		      <button type="submit" class="btn btn-primary">Enviar</button>
		      <p class="message">Not registered? <a href="#">Create an account</a></p>
		    </form>
	    </div>
	</div>
    
</body>
</html>