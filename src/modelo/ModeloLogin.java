package modelo;

import clases.acceso;
import clases.usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
	
	public acceso autenticacion(String usuario, String contrasena) throws SQLException, ClassNotFoundException, IOException {
		ResultSet miResulset=null;
		acceso access=null;
		PreparedStatement statement=null;
		
		try {
			// ESTABLECER CONEXION Y USAR SENTENCIA SQL

			miConexion = conectar.conectar();
			String instruccion="SELECT id,nivel FROM usuario WHERE usuario=? AND contrasena=?";
			statement=miConexion.prepareStatement(instruccion);
			statement.setString(1, usuario);
			statement.setString(2, contrasena);
			/// EJECUTAR SQL
			miResulset= statement.executeQuery();	
			if(miResulset.next()) {
				access = new acceso(miResulset.getInt(1),usuario,miResulset.getInt(2)); 
			}}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				miResulset.close();
				statement.close();
				miConexion.close();
			}
		
		return access;
	}

}
