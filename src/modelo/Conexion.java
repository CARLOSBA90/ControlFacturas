package modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	String host = "localhost";
	String db = "controlfacturas";
	String user = "root";
	String password = "root";
	int port = 3306;
	String url = String.format("jdbc:mysql://%s:%d/%s?useSSL=false", host, port, db);
	
	
	public Conexion() {
		
	}
	
	public Connection conectar() throws IOException, ClassNotFoundException, SQLException {

		 return DriverManager.getConnection(url, user, password);
	}

}
