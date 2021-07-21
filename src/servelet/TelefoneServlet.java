package servelet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.TelefoneBean;
import beans.UsuarioBean;
import daos.TelefoneDAO;
import daos.UsuarioDAO;

@WebServlet("/salvarTelefone")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UsuarioDAO daoUsuario = new UsuarioDAO();
    private TelefoneDAO daoTelefone = new TelefoneDAO();
   
    public TelefoneServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");            // Esta pegando user.id da pagina cadastro.jsp
			
			if(user != null) {
				
				if(acao.equalsIgnoreCase("addTelefone")) {
					UsuarioBean usuario = daoUsuario.consultar(user);     // Criando um objeto usuario com os dados existentes no banco de dados
					
					request.getSession().setAttribute("user", usuario);  // Salvando os dados dos usuario na sessão
					request.setAttribute("user", usuario);              // Enviando os dados para a tela
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("/telefone.jsp");
					request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
					dispatcher.forward(request, response);
					
					
				} else if(acao.equalsIgnoreCase("delete")) {
					
					String telefone = request.getParameter("telefone");
					daoTelefone.delete(telefone);
					
					UsuarioBean usuario = (UsuarioBean) request.getSession().getAttribute("user");
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("/telefone.jsp");
					request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
					request.setAttribute("mensagem", "Removido com sucesso");
					dispatcher.forward(request, response);
				}
				
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastro.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				dispatcher.forward(request, response);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// recuperando o objeto guardado na sessão
			UsuarioBean usuario = (UsuarioBean) request.getSession().getAttribute("user"); 
			
			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");
			String acao = request.getParameter("acao");
			
			if(acao != null && !acao.equalsIgnoreCase("voltar")) {
				
				if(numero == null || numero != null && numero.isEmpty()) {
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("/telefone.jsp");
					request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
					request.setAttribute("mensagem", "Informe o número do telefone");
					dispatcher.forward(request, response);
					
				} else {
					
					// Criando um novo objeto telefone com os dados vindo do formulario
					TelefoneBean telefone = new TelefoneBean();
					telefone.setNumero(numero);
					telefone.setTipo(tipo);
					telefone.setUsuarioId(usuario.getId());
					
					daoTelefone.salvar(telefone);
					
					// Guardando os novos dados na Sessão
					request.getSession().setAttribute("user", usuario);
					request.setAttribute("user", usuario);
					
					// Redirecionando para a tela de telefone com os dados
					RequestDispatcher dispatcher = request.getRequestDispatcher("/telefone.jsp");
					request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
					request.setAttribute("mensagem", "Telefone salvo com sucesso");
					dispatcher.forward(request, response);
				}
				
			} else {
				
				RequestDispatcher view = request.getRequestDispatcher("cadastro.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
				
			}
			
			
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
