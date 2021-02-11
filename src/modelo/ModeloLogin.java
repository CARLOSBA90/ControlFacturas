package modelo;

import clases.usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModeloLogin {
	
	private Connection miConexion=null;
	
	private Conexion conectar;
	
	public ModeloLogin() {
		
		conectar = new Conexion();
		
	}
	
	public int autenticacion(String usuario, String contrasena) throws SQLException, ClassNotFoundException, IOException {
		
		int acceso = 0;
		
		List<usuario> usuarios = new ArrayList<>();

		Statement miStatement=null;

		ResultSet miResulset=null;
		
		try {
			// ESTABLECER CONEXION Y USAR SENTENCIA SQL

			miConexion = conectar.conectar();

			String instruccion="SELECT * FROM usuario";

			miStatement=miConexion.createStatement();

			/// EJECUTAR SQL

			miResulset=miStatement.executeQuery(instruccion);


			/// RECORRER EL RESULSET

			while(miResulset.next()) {
				
				if(usuario.toLowerCase().equals(miResulset.getString(2)) && contrasena.equals(miResulset.getString(3)))
				acceso=miResulset.getInt(1);

			}}catch(SQLException e) {

				e.printStackTrace();
			}finally {
				miStatement.close();
				miConexion.close();
			}
		
		
		return acceso;
	}

}
