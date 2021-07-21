package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {

	private static String url = "jdbc:mysql://localhost/curso_jsp?verifyServerCertificate=false&useSSL=true";
	private static String user = "root";
	private static String password = "root";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnection() {
		conectar();
	}

	private static void conectar() {

		try {
			if(connection == null) {
				Class.forName("com.mysql.jdbc.Driver"); /* Aqui registra o mysql */
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);  /* Aqui desabilita a atualização automatica */
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao se conectar ao banco de dados!\n" + e.getMessage());
		}
		
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
}
