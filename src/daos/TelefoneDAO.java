package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.TelefoneBean;
import connection.SingleConnection;

public class TelefoneDAO {

	private Connection connection;
	
	public TelefoneDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(TelefoneBean telefone) {
		
		try {
			String sql = "INSERT INTO telefone (numero, tipo, usuario_id) VALUES (?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, telefone.getNumero());
			pstmt.setString(2, telefone.getTipo());
			pstmt.setInt(3, telefone.getUsuarioId());
			pstmt.execute();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public List<TelefoneBean> listar(int user_id) throws SQLException {
		
		List<TelefoneBean> lista = new ArrayList<TelefoneBean>();
		String sql = "SELECT * FROM telefone WHERE usuario_id = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setLong(1, user_id);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			TelefoneBean telefone = new TelefoneBean();
			telefone.setId(rs.getInt("id"));
			telefone.setNumero(rs.getString("numero"));
			telefone.setTipo(rs.getString("tipo"));
			telefone.setUsuarioId(rs.getInt("usuario_id"));
			
			lista.add(telefone);
		}
		
		return lista;
	}
	
	public void delete(String id) {
		
		try {
			String sql = "DELETE FROM telefone WHERE id = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.execute();
			connection.commit();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
}
