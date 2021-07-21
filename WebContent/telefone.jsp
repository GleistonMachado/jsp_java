<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<title>Produtos</title>
</head>
<body style="background-color: #ddd; padding: 10px;" >

	<p><a href="acessoLiberado.jsp">Início</a> | <a href="index.jsp">Sair</a></p>

	<div class="container">
		
		<h1 style="margin-top: 30px">Cadastro de telefones</h1>
	
		
		
		<form id="fomrTelefone" action="salvarTelefone" method="post" style="margin-top: 30px" onsubmit="return validarCampos() ? true : false">
		
			<p style="color:red">${mensagem}</p>
		
			<input type="hidden" name="id" value="${telefone.id}">
			
			<div class="form-group">
				<label for="nome">Usuário</label> 
				<input type="text" class="form-control" id="nome" name="nome" value="${user.login}" disabled="disabled">
			</div>
			
			<div class="form-group">
				<label for="numero">Número</label> 
				<input type="text" class="form-control" id="numero" name="numero" value="${telefone.numero}">
			</div>
			
			<div class="form-group">
				<label for="tipo">Tipo</label> 
				<select id="tipo" name="tipo">
					<option>Casa</option>
					<option>Trabalho</option>
					<option>Celular</option>
				</select>
			</div>

			<button type="submit" class="btn btn-primary">Salvar</button>
			<button type="submit" class="btn btn-primary" onsubmit="document.getElementById('fomrTelefone').action = salvarTelefone?acao=voltar">Voltar</button>
		</form>
		<hr>
		
		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">Número</th>
		      <th scope="col">Tipo</th>
		       <th scope="col">Excluir</th>
		    </tr>
		  </thead>
		  <tbody>
	  		<c:forEach items="${telefones}" var="telefone">
			    <tr>
			    	<td><c:out value="${telefone.id}"></c:out></td>
			      	<td><c:out value="${telefone.numero}"></c:out></td>
			      	<td><c:out value="${telefone.tipo}"></c:out></td>
			  
			      	<td>
				      	<a href="salvarTelefone?acao=delete&telefone=${telefone.id}" onclick="return confirm('Deseja excluir?')">
							<svg xmlns="http://www.w3.org/2000/svg" 
								width="25" height="25" fill="currentColor" 
								class="bi bi-person-x-fill" viewBox="0 0 16 16">
	  							<path fill-rule="evenodd" d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6.146-2.854a.5.5 0 0 1 .708 0L14 6.293l1.146-1.147a.5.5 0 0 1 .708.708L14.707 7l1.147 1.146a.5.5 0 0 1-.708.708L14 7.707l-1.146 1.147a.5.5 0 0 1-.708-.708L13.293 7l-1.147-1.146a.5.5 0 0 1 0-.708z"/>
							</svg>
						</a>
					</td>
			    </tr>
		   	</c:forEach>
		  </tbody>
		</table>
		
	</div>
	
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("numero").value == '') {
				alert('Informe o número do telefone');
				return false;
				
			} else if (document.getElementById("tipo").value == '') {
				alert('Informe o tipo do telefone');
				return false;
			} 
			
			return true;
		}
	</script>
</body>
</html>