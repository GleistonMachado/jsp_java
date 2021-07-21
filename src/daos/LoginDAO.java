package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;

public class LoginDAO {

	private Connection connection;
	
	public LoginDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public boolean validarLoginSenha(String login, String senha) throws Exception {
		
		String sql = "SELECT * FROM usuario WHERE login = ? and senha = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, login);
		pstmt.setString(2, senha);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			return true;
		} else {
			return false;
		}
	}
		
	
}
