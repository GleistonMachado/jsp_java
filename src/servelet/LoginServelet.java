package servelet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.LoginDAO;

@WebServlet("/LoginServelet")
public class LoginServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private LoginDAO daoLogin = new LoginDAO();

	public LoginServelet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			
			if(login != null && !login.isEmpty() && senha != null && !senha.isEmpty() ) {
				
				if(daoLogin.validarLoginSenha(login, senha)) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("acessoLiberado.jsp");
					dispatcher.forward(request, response);
					
				} else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("acessoNegado.jsp");
					dispatcher.forward(request, response);
				}
				
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}