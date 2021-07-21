package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.ProdutoBean;
import connection.SingleConnection;

public class ProdutoDao {
	
	private Connection connection;
	
	public ProdutoDao() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(ProdutoBean produto) throws SQLException {
		
		String sql = "INSERT INTO produto (nome, quantidade, preco) VALUES (?, ?, ?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, produto.getNome());
		pstmt.setInt(2, produto.getQuantidade());
		pstmt.setFloat(3, produto.getPreco());
		pstmt.execute();
		connection.commit();
	}
	
	public List<ProdutoBean> listar() throws SQLException {
		
		List<ProdutoBean> lista = new ArrayList<>();
		
		String sql = "SELECT * FROM produto";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rset = pstmt.executeQuery();
		
		while (rset.next()) {
			ProdutoBean obj = new ProdutoBean();
			obj.setId(rset.getInt("id"));
			obj.setNome(rset.getString("nome"));
			obj.setQuantidade(rset.getInt("quantidade"));
			obj.setPreco(rset.getFloat("preco"));
			
			lista.add(obj);
		}
		
		return lista;
	}
	
	public void delete(String id) throws SQLException {
		String sql = "DELETE FROM produto WHERE id = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.execute();
		connection.commit();
	}
	
	public ProdutoBean consultar(String id) throws SQLException {
		
		String sql = "SELECT * FROM produto WHERE id = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rset = pstmt.executeQuery();
		
		if(rset.next()) {
			ProdutoBean obj = new ProdutoBean();
			obj.setId(rset.getInt("id"));
			obj.setNome(rset.getString("nome"));
			obj.setQuantidade(rset.getInt("quantidade"));
			obj.setPreco(rset.getFloat("preco"));
			
			return obj;
		}
		
		return null;
		
	}
	
	public void atualizar(ProdutoBean produto) throws SQLException {
		
		String sql = "UPDATE produto SET nome = ?, quantidade = ?, preco = ? WHERE id = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, produto.getNome());
		pstmt.setInt(2, produto.getQuantidade());
		pstmt.setFloat(3, produto.getPreco());
		pstmt.setInt(4, produto.getId());
		pstmt.executeUpdate();
		connection.commit();
		
	}
	
	
	public boolean validarProduto(String produto) {
		
		try {
			String sql = "SELECT COUNT(1) AS qtd FROM produto WHERE nome = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, produto);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("qtd") <= 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean validarProdutoUpdate(String produto, String id) {
		
		try {
			String sql = "SELECT COUNT(1) AS qtd FROM produto WHERE nome = ? and id <> ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, produto);
			pstmt.setString(2, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("qtd") <= 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
