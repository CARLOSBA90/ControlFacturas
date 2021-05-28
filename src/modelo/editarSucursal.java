package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import clases.entero;
import javafx.collections.ObservableList;

public class editarSucursal extends DBTask<entero>{
    private Connection miConexion=null;
	private Conexion conectar;
	private int idSucursal;
	private int idZona;
	private String user;
	private String pass;
	private int idZonaAnterior;

	public editarSucursal(int idSucursal, int idZona, String user, String pass, int idZonaAnterior) {
		conectar = new Conexion();
		this.idSucursal = idSucursal;
		this.idZona = idZona;
		this.user = user;
		this.pass = pass;
		this.idZonaAnterior = idZonaAnterior;
	}

	protected entero call() throws Exception {
		return editarSucursal();
	}


	public entero editarSucursal() throws SQLException {
	    ///FIXME traer valor de id zona anterior, arreglar problemas en edicion de user y pass
		int insercion = 0;
		try {
			/// Transaccion!
			miConexion = conectar.conectar();
			miConexion.setAutoCommit(false);
			/// Actualizacion de dos tablas de ser necesario,tablas: sucursal y sucursal_zona
			/// tabla sucursal
			// ENSAMBLE
			if (user!=null || pass!=null) {
				String sql = "UPDATE usuario SET";
				sql += (user == null) ? "" : " usuario.usuario=?";
				sql += (user != null && pass != null) ? "," : "";
				sql += (pass == null) ? "" : " usuario.contrasena=?";
				sql += " WHERE usuario.id=?";
				PreparedStatement statement = null;
				statement = miConexion.prepareStatement(sql);
				if (user != null && pass != null) {
					statement.setString(1, user);
					statement.setString(2, pass);
					statement.setInt(3, idSucursal);
					

				} else if (user != null && pass == null) {
					statement.setString(1, user);
					statement.setInt(2, idSucursal);

				} else {
					statement.setString(1, pass);
					statement.setInt(2, idSucursal);
				}
				statement.execute();
				statement.close();
			}
			
			/// tabla sucursal_zona
			if (idZona != -2) {
				String sql = "UPDATE sucursal_zona SET idzonas=? WHERE idsucursal=? AND idzonas=?";
				PreparedStatement statement = null;
				statement = miConexion.prepareStatement(sql);
				statement.setInt(1, idZona);
				statement.setInt(2, idSucursal);
				statement.setInt(3, idZonaAnterior); 
				statement.execute();
				statement.close();
			}
			miConexion.commit();
			insercion = 1;
		} catch (Exception e) {
			miConexion.rollback();
		} finally {
			try {
				miConexion.close();
			} catch (Exception e) {
				/* GENERAR EXCEPCION CONTROLADA */
			}
		}
		
		return new entero(insercion);
	}

}
