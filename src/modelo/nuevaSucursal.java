package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import clases.entero;

public class nuevaSucursal extends DBTask<entero>{
    private Connection miConexion=null;
	private Conexion conectar;
	private int idZona;
	private String user;
	private String pass;
	
	public nuevaSucursal(int idZona, String user, String pass) {
		conectar = new Conexion();
		this.idZona = idZona;
		this.user = user;
		this.pass = pass;
	}

	protected entero call() throws Exception {
		return nuevaSucursal();
	}

	
	public entero nuevaSucursal() throws SQLException {
		int insercion = -1;
		try {
			miConexion = conectar.conectar();
            /// Chequeo de usuario existente
			PreparedStatement statement=null;
			String sql = "SELECT id from usuario WHERE usuario=?";
			statement = miConexion.prepareStatement(sql);
			statement.setString(1,user);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) return new entero(1);
			rs.close();
			statement.close();
			
			/// Transaccion!
			miConexion.setAutoCommit(false);
			/// ------------------------------------------------------------------------------------------------
			/// OBTENER EL PROXIMO AUTO-INCREMENT DE LA TABLA PEDIDOS PARA UTILIZARLO EN LA
			///  TABLA SUCURSAL
			Statement statementAU = null;
			ResultSet rs_AU = null;
			int proximo = 0;
			String sqlAU = "SELECT AUTO_INCREMENT FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'heroku_52d8d334e25a240' "
					+ "AND   TABLE_NAME   = 'usuario'";
			statementAU = miConexion.createStatement();
			rs_AU = statementAU.executeQuery(sqlAU);
			if (rs_AU.next())  proximo = rs_AU.getInt(1);
			rs_AU.close();
			
			///TABLA SUCURSAL
			 sql = "INSERT INTO usuario(id,usuario,contrasena,nivel) values(?,?,?,2)";
			 statement = null;
			 statement = miConexion.prepareStatement(sql);
			 statement.setInt(1, proximo);
			 statement.setString(2, user.toLowerCase());
			 statement.setString(3, pass);
			 statement.execute();
			 
			/// TABLA ZONA SUCURSAL
			 sql = "INSERT INTO sucursal_zona(idsucursal,idzonas) values(?,?)";
			 statement = null;
			 statement = miConexion.prepareStatement(sql);
			 statement.setInt(1, proximo);
			 statement.setInt(2, idZona);
			 statement.execute();
			 statement.close();
			miConexion.commit();
			insercion = 0;
		} catch (Exception e) {
			miConexion.rollback();
			insercion = 2;
		} finally {
			try {
				miConexion.close();
			} catch (Exception e) {
				/* GENERAR EXCEPCION CONTROLADA*/
			}
		}
		
		return new entero(insercion);
	}


}
