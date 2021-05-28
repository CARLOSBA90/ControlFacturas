package modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import clases.booleano;

public class eliminarSucursal extends DBTask<booleano>{
    private Connection miConexion=null;
	private Conexion conectar;
	private int id;
	private int idAdmin;
	private String pass;

	public eliminarSucursal(int id, int idAdmin, String pass) {
		conectar = new Conexion();
		this.id = id;
		this.idAdmin = idAdmin;
		this.pass = pass;
	}


	@Override
	protected booleano call() throws Exception {
		return eliminar();
	}
	
	
	public booleano eliminar() throws SQLException, ClassNotFoundException, IOException {
		boolean validacion = false;
		boolean eliminado = false;
		miConexion = conectar.conectar();
		
        /// Validacion administrador y contraseña
		PreparedStatement statement=null;
		String sql = "SELECT usuario.id from usuario WHERE usuario.id=? and usuario.contrasena=?";
		statement = miConexion.prepareStatement(sql);
		statement.setInt(1,idAdmin);
		statement.setString(2,pass);
		ResultSet rs = statement.executeQuery();
		if(rs.next()) validacion = true;
		rs.close();
		statement.close();
		if(validacion) {
			 sql = "DELETE FROM usuario WHERE usuario.id=?";
			 statement = null;
			 statement = miConexion.prepareStatement(sql);
			 statement.setInt(1, id);
			 statement.execute();
			 statement.close();
			 eliminado=true;
		}
		
		miConexion.close();
		return new booleano(eliminado);
	}




}
