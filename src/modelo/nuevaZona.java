package modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import clases.booleano;
import clases.entero;


public class nuevaZona extends DBTask<entero> {
    private Connection miConexion=null;
	private Conexion conectar;
	private String nombre;
	
	public nuevaZona(String nombre) {
		conectar = new Conexion();
		this.nombre = nombre;
	}
	protected entero call() throws Exception {
		return nueva();
	}
	
	public entero nueva() throws SQLException, ClassNotFoundException, IOException {
		String sql = "INSERT INTO zonas (nombre) VALUES (?)";
		miConexion = conectar.conectar();
		java.sql.PreparedStatement statement = miConexion.prepareStatement(sql);
		statement.setString(1, nombre);
		statement.execute();
		statement.close();
		miConexion.close();
		return new entero(1);
	}
}
