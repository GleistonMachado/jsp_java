package servelet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.UsuarioBean;
import daos.TelefoneDAO;
import daos.UsuarioDAO;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class UsuarioServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDAO daoUsuario = new UsuarioDAO();
	private TelefoneDAO daoTelefone = new TelefoneDAO();
  
    public UsuarioServelet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		String user = request.getParameter("user");
		
		try {
			
			if(acao != null && acao.equalsIgnoreCase("delete")) {
				daoUsuario.delete(user);
				RequestDispatcher view = request.getRequestDispatcher("cadastro.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
				
			} else if(acao != null && acao.equalsIgnoreCase("editar")) {
				UsuarioBean usuario = daoUsuario.consultar(user);
				RequestDispatcher view = request.getRequestDispatcher("cadastro.jsp");
				request.setAttribute("user", usuario);
				view.forward(request, response);
				
			} else if(acao != null && acao.equalsIgnoreCase("listartodos")) {
				RequestDispatcher view = request.getRequestDispatcher("cadastro.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
				
			} else if(acao != null && acao.equalsIgnoreCase("download")){
				
				UsuarioBean usuario = daoUsuario.consultar(user);
				
				if(usuario != null) {
					
					String contentType = "";
					byte[] arquivoBytes = null;
					
					String tipo = request.getParameter("tipo");
					
					if(tipo.equalsIgnoreCase("imagem")) {
						contentType = usuario.getContentType();
						arquivoBytes = new Base64().decodeBase64(usuario.getFotoBase64()); // Converte a imagem base64 do banco para um array de byte[]
						
					} else if(tipo.equalsIgnoreCase("pdf")) {
						contentType = usuario.getContentTypeArquivo();
						arquivoBytes = new Base64().decodeBase64(usuario.getArquivoBase64()); // Converte a imagem base64 do banco para um array de byte[] 
					
					}
					
					// Criando o nome do aquivo a ser baixado 
					response.setHeader("Content-Disposition", "attachment;filename=arquivo."+ contentType.split("\\/")[1]); 
					
					
					InputStream inputStream = new ByteArrayInputStream(arquivoBytes); /* Coloca os bytes em um objeto de entrada para serem processado */
					
					/* Inicio da resposta para o navegador */
					
					byte[] arrayBytes = new byte[1024];                      // Array que recebera os bytes do arquivo
					int read = 0;
					OutputStream outPutStream = response.getOutputStream(); // Responsavel por pegar os bytes de saida
					
					// Enquanto a variavel read, que recebe os bytes do inputStream for menor que -1 o codigo sera executado
					while ((read = inputStream.read(arrayBytes)) != -1) { 
						outPutStream.write(arrayBytes, 0, read);           // gravando os bytes do arquivo no array arrayBytes
					}
					
					outPutStream.flush();
					outPutStream.close();
					
				}
				
			} else {
				
				RequestDispatcher view = request.getRequestDispatcher("cadastro.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String rua = request.getParameter("rua");
		String bairro = request.getParameter("bairro");
		String cidade = request.getParameter("cidade");
		String estado = request.getParameter("estado");
		String ibge = request.getParameter("ibge");
		String acao = request.getParameter("acao");
		
		if(acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher rd = request.getRequestDispatcher("cadastro.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				rd.forward(request, response);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else {
			
			UsuarioBean usuario = new UsuarioBean();
			usuario.setId(!id.isEmpty() ? Integer.parseInt(id) : 0);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setEmail(email);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setEstado(estado);
			usuario.setIbge(ibge);
			
			String cep = request.getParameter("cep");
			
			try {
	
				/* Codigo usado para formularios com textos e imagens */
				if(ServletFileUpload.isMultipartContent(request)) {
					
					Part imagemFoto = request.getPart("foto");
					
					/* Salva as imagens */
					if(imagemFoto != null && imagemFoto.getInputStream().available() > 0) {
						
						@SuppressWarnings("static-access")
						String fotoBase64 = new Base64().encodeBase64String(converteStreamParaByte(imagemFoto.getInputStream()));
						
						usuario.setFotoBase64(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());
						
						/* Miniatura imagem */
						byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
						
						// Transfomando a imagem em bufferedImagem
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
						
						// Pegando o tipo da imagem
						int tipo = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
						
						// Cria a imagem em miniatura
						BufferedImage resizedImage = new BufferedImage(100, 100, tipo);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null );
						g.dispose();
						
						// Escrever a imagem novamente
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", byteArrayOutputStream);
						
						String miniaturaBase64 = "data:image/png;base64,"+ DatatypeConverter.printBase64Binary(byteArrayOutputStream.toByteArray());
						
						usuario.setMiniaturaBase64(miniaturaBase64);
					
					} else {
						usuario.setAtualizarImagem(false);
					
					}
					
					Part arquivo = request.getPart("arquivo");
					
					/* Salva os arquivos */
					if (arquivo != null && arquivo.getInputStream().available() > 0){
						
						@SuppressWarnings("static-access")
						String arquivoBase64 = new Base64().encodeBase64String(converteStreamParaByte(arquivo.getInputStream()));
						
						usuario.setArquivoBase64(arquivoBase64);
						usuario.setContentTypeArquivo(arquivo.getContentType());
						
					} else {
						usuario.setAtualizarPdf(false);
						
					}
					
				}	
				
				/* Codigo usado somente para formularios de imagens
				  
				if(ServletFileUpload.isMultipartContent(request)) {
					List<FileItem> fileItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
					
					for (FileItem fileItem : fileItems) {
						if(fileItem.getFieldName().equals("foto")) {
							@SuppressWarnings("static-access")
							String fotoBase64 = new Base64().encodeBase64String(fileItem.get());
							String contentType = fileItem.getContentType();
							
							usuario.setFotoBase64(fotoBase64);
							usuario.setContentType(contentType);
							
							
						}
					}
				}
				*/
				
				/* Fim Upload de imagens e pdf */
				
				
				String mensagem = null;
				boolean podeInserir = true;
				
				if(login == null || login.isEmpty()) {
					mensagem = "Login deve ser informado";
					podeInserir = false;
					
				} else if (email == null || email.isEmpty()){
					mensagem = "E-mail deve ser informada";
					podeInserir = false;
					
				}  else if (senha == null || senha.isEmpty()){
					mensagem = "Senha deve ser informada";
					podeInserir = false;
					
				} else if (cep == null || cep.isEmpty()){
					mensagem = "Cep deve ser informado";
					podeInserir = false;
					
				} else if(id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) {
					mensagem = "Usuário já existe!";
					podeInserir = false;
				}
				
				if(mensagem != null) {
					request.setAttribute("mensagem", mensagem);
					
				} else if(id == null || id.isEmpty() && daoUsuario.validarLogin(login) && podeInserir)  {
					daoUsuario.salvar(usuario);
					
				} else if(id != null && !id.isEmpty() && podeInserir) {
					daoUsuario.atualizar(usuario);
					
				}
				
				if(!podeInserir) {
					request.setAttribute("user", usuario);
				}
				
				RequestDispatcher view = request.getRequestDispatcher("cadastro.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				request.setAttribute("mensagem", "Salvo com sucesso!");
				view.forward(request, response);
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	/* Converte a entrada de fluxo de dados da imagem para um array de byte[] */
	private byte[] converteStreamParaByte(InputStream imagem) throws IOException, InterruptedException {
		
 		ByteArrayOutputStream arrayBytesImagem = new ByteArrayOutputStream();
 		int sizeImagem = imagem.read();
 		
 		// Enquanto existir dados na imagem
 		while(sizeImagem != -1) {
 			arrayBytesImagem.write(sizeImagem);
 			sizeImagem = imagem.read();
 		}
 		
 		return arrayBytesImagem.toByteArray();
	}
	

}