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
		
		<h1 style="margin-top: 30px">Cadastro de Produtos</h1>
		
		
		<form id="fomrProduto" action="salvarProduto" method="post" style="margin-top: 30px" onsubmit="return validarCampos() ? true : false">
		
			<input type="hidden" name="id" value="${produto.id}">
			
			<div class="form-group">
				<label for="nome">Nome do produto</label> 
				<input type="text" class="form-control" id="nome" name="nome" value="${produto.nome}">
			</div>
			
			<p style="color:red">${mensagem}</p>
			
			<div class="form-group">
				<label for="quantidade">Quantidade</label> 
				<input type="text" class="form-control" id="quantidade" name="quantidade" value="${produto.quantidade}">
			</div>
			
			<div class="form-group">
				<label for="preco">Preço</label> 
				R$ <input type="text" class="form-control" id="preco" name="preco" value="${produto.preco}">
			</div>
	
			<button type="submit" class="btn btn-primary">Enviar</button>
			<button onclick="document.getElementById(fomrProduto).action = 'salvarProduto?acao=reset'" class="btn btn-primary">Cancelar</button>
		</form>
		<hr>
		
		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">Produto</th>
		      <th scope="col">Quantidade</th>
		      <th scope="col">Preço</th>
		    </tr>
		  </thead>
		  <tbody>
	  		<c:forEach items="${produtos}" var="produto">
			    <tr>
			    	<td><c:out value="${produto.id}"></c:out></td>
			      	<td><c:out value="${produto.nome}"></c:out></td>
			      	<td><c:out value="${produto.quantidade}"></c:out></td>
			      	<td><c:out value="R$ ${produto.preco}"></c:out></td>
			      	<td>
				      	<a href="salvarProduto?acao=editar&produto=${produto.id}">
							<svg xmlns="http://www.w3.org/2000/svg" 
								width="25" height="25" fill="currentColor" 
								class="bi bi-pencil-square" viewBox="0 0 16 16">
	  							<path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
  								<path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
							</svg>
						</a>
					</td>
			      	<td>
				      	<a href="salvarProduto?acao=delete&produto=${produto.id}" onclick="return confirm('Deseja excluir?')">
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
			if (document.getElementById("nome").value == '') {
				alert('Informe o nome do produto');
				return false;
			} else if (document.getElementById("quantidade").value == '') {
				alert('Informe a quantidade do produto');
				return false;
			} else if (document.getElementById("preco").value == '') {
				alert('Informe o preço do produto');
				return false;
			} 
			
			return true;
		}
	</script>
</body>
</html>