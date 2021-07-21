package servelet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ProdutoBean;
import daos.ProdutoDao;

@WebServlet("/salvarProduto")
public class ProdutoServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	private ProdutoDao dao = new ProdutoDao();
 
    public ProdutoServelet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listartodos";
		String produto = request.getParameter("produto");
		
		try {
			
			if(acao.equalsIgnoreCase("delete")) {
				dao.delete(produto);
				RequestDispatcher rdispatcher = request.getRequestDispatcher("produto.jsp");
				request.setAttribute("produtos", dao.listar());
				rdispatcher.forward(request, response);
				
			} else if(acao.equalsIgnoreCase("editar")) {
				ProdutoBean prod = dao.consultar(produto);
				RequestDispatcher rdispatcher = request.getRequestDispatcher("produto.jsp");
				request.setAttribute("produto", prod);
				rdispatcher.forward(request, response);
				
			} else if(acao.equalsIgnoreCase("listartodos")) {
				RequestDispatcher rdispatcher = request.getRequestDispatcher("produto.jsp");
				request.setAttribute("produtos", dao.listar());
				rdispatcher.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");

		if(acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher rdispatcher = request.getRequestDispatcher("produto.jsp");
				request.setAttribute("produtos", dao.listar());
				rdispatcher.forward(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String preco = request.getParameter("preco");
			// preco.replace(".", "").replace(",", ".");
			
			ProdutoBean produto = new ProdutoBean();
			produto.setId(!id.isEmpty() ? Integer.parseInt(id) : 0);
			produto.setNome(nome);
			produto.setQuantidade(Integer.parseInt(quantidade));
			produto.setPreco(Float.parseFloat(preco));
			
			try {
				
				boolean podeInserir = true;
				
				if(id == null || id.isEmpty() && !dao.validarProduto(nome)) {
					request.setAttribute("mensagem", "Produto ja existe!");
					podeInserir = false;
				}
				
				if(id == null || id.isEmpty() && dao.validarProdutoUpdate(nome, id) && podeInserir)  {
					dao.salvar(produto);
					
				} else if(id != null && !id.isEmpty() && podeInserir) {
					if(!dao.validarProdutoUpdate(nome, id)) {
						request.setAttribute("mensagem", "Produto ja existe!");
					} else {
						dao.atualizar(produto);
					}
				}
				
				if(!podeInserir) {
					request.setAttribute("produto", produto);
				}
				
				RequestDispatcher rdispatcher = request.getRequestDispatcher("produto.jsp");
				request.setAttribute("produtos", dao.listar());
				rdispatcher.forward(request, response);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
