package modelo;
import clases.acceso;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeloLogin extends DBTask<acceso>{
	
	private Connection miConexion=null;
	private Conexion conectar;
	private acceso acc;
	private String usuario;
	private String contra;
	
	@Override
	protected acceso call() throws Exception {
		return autenticacion(usuario,contra);
	}
	public ModeloLogin() {
		
		conectar = new Conexion();
		
	}
	public ModeloLogin(String usuario, String contra) throws ClassNotFoundException, SQLException, IOException {
		conectar = new Conexion();
		this.usuario = usuario;
		this.contra = contra;
	
	   
	}

	@SuppressWarnings("resource")
	public acceso autenticacion(String usuario, String contrasena)  throws SQLException, ClassNotFoundException, IOException {
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
				int id = miResulset.getInt(1);
				int nivel = miResulset.getInt(2);
					access = new acceso(id, usuario, nivel);
				
			}}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				miResulset.close();
				statement.close();
				miConexion.close();
			}
		setAcc(access);
		return access;
	}


	public acceso getAcc() {
		return acc;
	}

	public void setAcc(acceso acc) {
		this.acc = acc;
	}



	

}
