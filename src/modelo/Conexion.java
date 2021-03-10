package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	public Conexion() {}

	String host = "localhost";
	String db = "controlfacturas";
	String user = "root";
	String password = "root";
	int port = 3306;
	String url = String.format("jdbc:mysql://%s:%d/%s?useSSL=false", host, port, db);

	

	public Connection conectar() throws SQLException {

		return DriverManager.getConnection(url, user, password);
	}

}
