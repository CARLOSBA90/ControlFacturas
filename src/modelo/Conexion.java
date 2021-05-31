package modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
	
	public Conexion() {}

/*	String host = "localhost";
	String db = "controlfacturas";
	String user = "root";
	String password = "root";
	int port = 3306;
	String url = String.format("jdbc:mysql://%s:%d/%s?useSSL=false", host, port, db);*/

	public Connection conectar() throws SQLException, ClassNotFoundException, IOException {

		InputStream inputStream =null;
    	String url = "";
    	String user = "";
    	String pass = "";
   	 
		try {
			Properties prop = new Properties();
			String propFileName ="resources/conexion.properties";
			Class currentClass = new Object() { }.getClass().getEnclosingClass();
 
			inputStream = currentClass.getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			// get the property value and print it out
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			pass = prop.getProperty("pass");
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
    	
        Class.forName("com.mysql.jdbc.Driver"); 
          Connection connection = DriverManager.getConnection(url,user,pass); 
          return connection;  
    }  

}
