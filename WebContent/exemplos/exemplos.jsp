<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Bem vindo!</h1>
	
	<!-- Código Java  -->
	<% out.print("Olá esse é um codigo Java"); %><br><br> 
	
	<%= "Olá esse é um codigo Java 2" %> <br><br> 
	
	<!-- Tag Declarativa  -->
	<%! 
		int count = 1; 
	
		public int soma() {
			return count + 1;
		}
	
	%>
	
	<%= "Usando a tag declarativa: "+ count +" + 1 = " + soma() %> <br><br> 
	
	<hr>
	
	<form action="exemploReceberDadosFormulario.jsp">
	
		<label>Nome:
		<input type="text" id="name" name="name">
		</label>
		
		<button>Enviar</button>
	
	</form>
</body>
</html>