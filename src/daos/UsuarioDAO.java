package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.UsuarioBean;
import connection.SingleConnection;

public class UsuarioDAO {

	private Connection connection;
	
	public UsuarioDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(UsuarioBean novoUsuario) {
		
		try {
			String sql = "INSERT INTO usuario ("
					+ "login, email, senha, rua, bairro, cidade, "
					+ "estado, ibge, foto, tipo_arquivo, arquivo, contentTypeArquivo, miniatura) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, novoUsuario.getLogin());
			pstmt.setString(2, novoUsuario.getEmail());
			pstmt.setString(3, novoUsuario.getSenha());
			pstmt.setString(4, novoUsuario.getRua());
			pstmt.setString(5, novoUsuario.getBairro());
			pstmt.setString(6, novoUsuario.getCidade());
			pstmt.setString(7, novoUsuario.getEstado());
			pstmt.setString(8, novoUsuario.getIbge());
			pstmt.setString(9, novoUsuario.getFotoBase64());
			pstmt.setString(10, novoUsuario.getContentType());
			pstmt.setString(11, novoUsuario.getArquivoBase64());
			pstmt.setString(12, novoUsuario.getContentTypeArquivo());
			pstmt.setString(13, novoUsuario.getMiniaturaBase64());
			
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
	
	public List<UsuarioBean> listar() throws SQLException {
		String sql = "SELECT * FROM usuario WHERE login <> 'admin'";
		return extracted(sql);
	}

	private List<UsuarioBean> extracted(String sql) throws SQLException {
		List<UsuarioBean> lista = new ArrayList<UsuarioBean>();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			UsuarioBean usuario = new UsuarioBean();
			usuario.setId(rs.getInt("id"));
			usuario.setLogin(rs.getString("login"));
			usuario.setEmail(rs.getString("email"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setRua(rs.getString("rua"));
			usuario.setBairro(rs.getString("bairro"));
			usuario.setCidade(rs.getString("cidade"));
			usuario.setEstado(rs.getString("estado"));
			usuario.setIbge(rs.getString("ibge"));
			//usuario.setFotoBase64(rs.getString("foto"));
			usuario.setContentType(rs.getString("tipo_arquivo"));
			usuario.setArquivoBase64(rs.getString("arquivo"));
			usuario.setContentTypeArquivo(rs.getString("contentTypeArquivo"));
			usuario.setMiniaturaBase64(rs.getString("miniatura"));
			
			lista.add(usuario);
		}
		
		return lista;
	}
	
	public void delete(String id) {
		
		try {
			String sql = "DELETE FROM usuario WHERE id = ? AND login <> 'admin'";
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
	
	public UsuarioBean consultar(String id) throws SQLException {
		
		String sql = "SELECT * FROM usuario WHERE id = ? AND login <> 'admin'";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			UsuarioBean usuario = new UsuarioBean();
			usuario.setId(rs.getInt("id"));
			usuario.setLogin(rs.getString("login"));
			usuario.setEmail(rs.getString("email"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setRua(rs.getString("rua"));
			usuario.setBairro(rs.getString("bairro"));
			usuario.setCidade(rs.getString("cidade"));
			usuario.setEstado(rs.getString("estado"));
			usuario.setIbge(rs.getString("ibge"));
			usuario.setFotoBase64(rs.getString("foto"));
			usuario.setContentType(rs.getString("tipo_arquivo"));
			usuario.setArquivoBase64(rs.getString("arquivo"));
			usuario.setContentTypeArquivo(rs.getString("contentTypeArquivo"));
			usuario.setMiniaturaBase64(rs.getString("miniatura"));
			
			return usuario;
		}
		
		return null;
				
	}
	
	public void atualizar(UsuarioBean usuario) {
		
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("UPDATE usuario SET login = ?, email = ?, senha = ?, rua = ?, bairro = ?, cidade = ?, estado = ?, ibge = ? ");
			
			if(usuario.isAtualizarImagem()) sql.append(", foto = ?, tipo_arquivo = ? ");
			
			if(usuario.isAtualizarPdf()) sql.append(", arquivo = ?, contentTypeArquivo = ? ");
			
			if(usuario.isAtualizarImagem()) sql.append(", miniatura = ? ");
			
			sql.append("WHERE id = ?");

			PreparedStatement pstmt = connection.prepareStatement(sql.toString());
			pstmt.setString(1, usuario.getLogin());
			pstmt.setString(2, usuario.getEmail());
			pstmt.setString(3, usuario.getSenha());
			pstmt.setString(4, usuario.getRua());
			pstmt.setString(5, usuario.getBairro());
			pstmt.setString(6, usuario.getCidade());
			pstmt.setString(7, usuario.getEstado());
			pstmt.setString(8, usuario.getIbge());
			
			if(!usuario.isAtualizarImagem() && !usuario.isAtualizarPdf()) {
				pstmt.setInt(9, usuario.getId());
				
			} else if(usuario.isAtualizarImagem() && !usuario.isAtualizarPdf()) {
				pstmt.setString(9, usuario.getFotoBase64());
				pstmt.setString(10, usuario.getContentType());
				pstmt.setString(11, usuario.getMiniaturaBase64());
				pstmt.setInt(12, usuario.getId());
				
			} else if(usuario.isAtualizarPdf() && !usuario.isAtualizarImagem()) {
				pstmt.setString(9, usuario.getArquivoBase64());
				pstmt.setString(10, usuario.getContentTypeArquivo());
				pstmt.setInt(11, usuario.getId());
				
			} else if(usuario.isAtualizarImagem() && usuario.isAtualizarPdf()) {
				pstmt.setString(9, usuario.getFotoBase64());
				pstmt.setString(10, usuario.getContentType());
				pstmt.setString(11, usuario.getArquivoBase64());
				pstmt.setString(12, usuario.getContentTypeArquivo());
				pstmt.setString(13, usuario.getMiniaturaBase64());
				pstmt.setInt(14, usuario.getId());
				
			} 
				
			pstmt.executeUpdate();
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
	
	public boolean validarLogin(String login) {
		
		try {
			String sql = "SELECT COUNT(1) AS qtd FROM usuario WHERE login = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("qtd") <= 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean validarLoginUpdate(String login, String id) {
		
		try {
			String sql = "SELECT COUNT(1) AS qtd FROM usuario WHERE login = ? and id <> ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, login);
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
	
	//public boolean validarSenha(String senha) {
		
		//try {
			//String sql = "SELECT COUNT(1) AS qtd FROM usuario WHERE senha = ? and id <> ?";
			//PreparedStatement pstmt = connection.prepareStatement(sql);
			//pstmt.setString(1, senha);
			//ResultSet rs = pstmt.executeQuery();
			
			//if(rs.next()) {
				//return rs.getInt("qtd") <= 0;
			//}
			
		//} catch (Exception e) {
			//e.printStackTrace();
		//}
		
		//return false;
	//}
	
}
