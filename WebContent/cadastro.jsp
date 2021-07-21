<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<title>Cadastro</title>
</head>
<body style="background-color: #ddd; padding: 10px;">

	<p><a href="acessoLiberado.jsp">Início</a> | <a href="index.jsp">Sair</a></p>
	
	<div class="container">
	
		<h1 style="margin-top: 30px">Cadastro de Usuários</h1>
		
		
		<form id="fomrUser" action="salvarUsuario" method="post" style="margin-top: 30px" onsubmit="return validarCampos() ? true : false" enctype="multipart/form-data">
		
			<p style="color:red">${mensagem}</p>
		
			<input type="hidden" name="id" value="${user.id}">
			
			<div class="form-group">
				<label for="login">Login</label> 
				<input type="text" class="form-control" id="login" name="login" value="${user.login}" placeholder="Seu login">
			</div>
			
			<div class="form-group">
				<label for="nome">E-mail</label> 
				<input type="text" class="form-control" id="email" name="email" value="${user.email}" placeholder="Sua email">
			</div>
			
			<div class="form-group">
				<label for="senha">Senha</label> 
				<input type="password" class="form-control" id="senha" name="senha" value="${user.senha}" placeholder="Sua senha">
			</div>
			
			<div class="form-group">
				<label for="cep">Cep</label> 
				<input type="text" class="form-control" id="cep" name="cep" value="" placeholder="Seu cep">
			</div>
			
			<div class="form-group">
				<label for="rua">Rua</label> 
				<input type="text" class="form-control" id="rua" name="rua" value="${user.rua}">
			</div>
			
			<div class="form-group">
				<label for="bairro">Bairro</label> 
				<input type="text" class="form-control" id="bairro" name="bairro" value="${user.bairro}">
			</div>
			
			<div class="form-group">
				<label for="cidade">Cidade</label> 
				<input type="text" class="form-control" id="cidade" name="cidade" value="${user.cidade}">
			</div>
			
			<div class="form-group">
				<label for="uf">Estado</label> 
				<input type="text" class="form-control" id="uf" name="estado" value="${user.estado}">
			</div>
			
			<div class="form-group">
				<label for="ibge">IBGE</label> 
				<input type="text" class="form-control" id="ibge" name="ibge" value="${user.ibge}">
			</div>
			
			<div class="form-group">
				<label for="foto">Foto</label> 
				<input type="file" class="form-control" id="foto" name="foto" value="${user.fotoBase64}">
			</div>
			
			<div class="form-group">
				<label for="arquivo">Arquivo PDF</label> 
				<input type="file" class="form-control" id="arquivo" name="arquivo" value="${user.arquivoBase64}">
			</div>
	
			<button type="submit" class="btn btn-primary">Enviar</button>
			<button onclick="document.getElementById(fomrUser).action = 'salvarUsuario?acao=reset'" class="btn btn-primary">Cancelar</button>
		</form>
		<hr>
		
		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">Avatar</th>
		      <th scope="col">Login</th>
		      <th scope="col">E-mail</th>
		      <th scope="col">Cidade</th>
		      <th scope="col">Baixar Arquivo</th>
		      <th scope="col">Telefone</th>
		      <th scope="col">Editar</th>
		      <th scope="col">Excluir</th>
		    </tr>
		  </thead>
		  <tbody>
	  		<c:forEach items="${usuarios}" var="user">
			    <tr>
			    	<td><c:out value="${user.id}"></c:out></td>
			    	
			    	<c:if test="${ user.miniaturaBase64 != null }">
				      	<td>
				      		<a href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}">
				      			<img alt="Avatar" src='<c:out value="${ user.miniaturaBase64 }"></c:out>' width="25px" height="25px">
			      			</a>
				      	</td>
			      	</c:if>
			      	
			    	<c:if test="${ user.miniaturaBase64 == null}">
				      	<td><img alt="Avatar" src='resources/img/user-female-icon.png' onclick="alert('Não possui arquivos para serem baixados')" width="25px" height="25px"></td>
			      	</c:if>
			      	
			      	<td><c:out value="${user.login}"></c:out></td>
			      	<td><c:out value="${user.email}"></c:out></td>
			      	<td><c:out value="${user.cidade}"></c:out></td>
			      	
			      	<c:if test="${ user.arquivoBase64 != null }">
				      	<td>
				      		<a href="salvarUsuario?acao=download&tipo=pdf&user=${user.id}">
				      			<img alt="Avatar" src='resources/img/download.png' width="25px" height="25px" />
			      			</a>
				      	</td>
			      	</c:if>
			      	
			      	<c:if test="${ user.arquivoBase64 == null }">
			      		<td><img alt="Arquivo" src='resources/img/download.png' width="25px" height="25px" onclick="alert('Não possui arquivos para serem baixados')" /></td>
			      	</c:if>
			      	
					<td>
				      	<a href="salvarTelefone?acao=addTelefone&user=${user.id}">
							<img alt="" src="resources/img/phone_7135.ico" width="25px" height="25px">
						</a>
					</td>
			      		
			      	<td>
				      	<a href="salvarUsuario?acao=editar&user=${user.id}">
							<svg xmlns="http://www.w3.org/2000/svg" 
								width="25" height="25" fill="currentColor" 
								class="bi bi-pencil-square" viewBox="0 0 16 16">
	  							<path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
  								<path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
							</svg>
						</a>
					</td>
			      	<td>
				      	<a href="salvarUsuario?acao=delete&user=${user.id}" onclick="return confirm('Deseja excluir?')">
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
			if (document.getElementById("login").value == '') {
				alert('Informe o Login');
				return false;
			} else if (document.getElementById("email").value == '') {
				alert('Informe o E-mail');
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert('Informe o Senha');
				return false;
			} else if (document.getElementById("telefone").value == '') {
				alert('Informe o Telefone');
				return false;
			}
			
			return true;
		}
		
		$(document).ready(function() {

            function limpa_formulário_cep() {
                // Limpa valores do formulário de cep.
                $("#rua").val("");
                $("#bairro").val("");
                $("#cidade").val("");
                $("#uf").val("");
                $("#ibge").val("");
            }
            
            //Quando o campo cep perde o foco.
            $("#cep").blur(function() {

                //Nova variável "cep" somente com dígitos.
                var cep = $(this).val().replace(/\D/g, '');

                //Verifica se campo cep possui valor informado.
                if (cep != "") {

                    //Expressão regular para validar o CEP.
                    var validacep = /^[0-9]{8}$/;

                    //Valida o formato do CEP.
                    if(validacep.test(cep)) {

                        //Preenche os campos com "..." enquanto consulta webservice.
                        $("#rua").val("...");
                        $("#bairro").val("...");
                        $("#cidade").val("...");
                        $("#uf").val("...");
                        $("#ibge").val("...");

                        //Consulta o webservice viacep.com.br/
                        $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                            if (!("erro" in dados)) {
                                //Atualiza os campos com os valores da consulta.
                                $("#rua").val(dados.logradouro);
                                $("#bairro").val(dados.bairro);
                                $("#cidade").val(dados.localidade);
                                $("#uf").val(dados.uf);
                                $("#ibge").val(dados.ibge);
                            } 
                            else {
                                //CEP pesquisado não foi encontrado.
                                limpa_formulário_cep();
                                alert("CEP não encontrado.");
                            }
                        });
                    } 
                    else {
                        //cep é inválido.
                        limpa_formulário_cep();
                        alert("Formato de CEP inválido.");
                    }
                } 
                else {
                    //cep sem valor, limpa formulário.
                    limpa_formulário_cep();
                }
            });
        });
	</script>
</body>
</html>