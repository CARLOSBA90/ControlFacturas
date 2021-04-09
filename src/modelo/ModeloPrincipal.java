package modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import clases.factura;
import clases.proveedor;
import clases.sucursal;
import clases.zona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModeloPrincipal {
    private Connection miConexion=null;
	private Conexion conectar;
	
	public ModeloPrincipal() {
		conectar = new Conexion();
	}

	

	public ArrayList<zona> listaZonas() throws SQLException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		// ObservableList<String> listaZo = FXCollections.observableArrayList("Zona Norte", "Zona Sur");
		ArrayList<zona> listaZo = new ArrayList<zona>();
		Statement miStatement = null;
		ResultSet miResulset = null;

		try {
			// ESTABLECER CONEXION Y USAR SENTENCIA SQL
			miConexion = conectar.conectar();
			String instruccion = "SELECT * FROM zonas";//
			miStatement = miConexion.createStatement();

			/// EJECUTAR SQL
			miResulset = miStatement.executeQuery(instruccion);

			/// RECORRER EL RESULSET
			if (miResulset.next() == false)
				listaZo.add(new zona(0, "Sin datos"));
			else {
				do {
					listaZo.add(new zona(miResulset.getInt(1), miResulset.getString(2)));
				} while (miResulset.next());
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			miStatement.close();
			miConexion.close();
		}

		return listaZo;
	}



	public ArrayList<sucursal> listaSucursales(int i) throws ClassNotFoundException, IOException, SQLException {	
		ArrayList<sucursal> lista = new ArrayList<>();
		Statement miStatement = null;
		ResultSet miResulset = null;

		try {
			// ESTABLECER CONEXION Y USAR SENTENCIA SQL
			miConexion = conectar.conectar();
			String instruccion = "SELECT usuario.id, usuario.usuario from usuario inner join sucursal_zona where "
					+ "usuario.id=sucursal_zona.idsucursal and sucursal_zona.idzonas="+i;//
			miStatement = miConexion.createStatement();

			/// EJECUTAR SQL
			miResulset = miStatement.executeQuery(instruccion);

			/// RECORRER EL RESULSET
			if (miResulset.next() == false)
				lista.add(new sucursal(0, "Sin datos"));
			else {
				do {
					lista.add(new sucursal(miResulset.getInt(1), miResulset.getString(2)));
				} while (miResulset.next());
			}
		} catch (SQLException e) {
			/* GENERAR EXCEPCION CONTROLADA */
		} finally {
			miStatement.close();
			miConexion.close();
		}

		return lista;
	}



	public ArrayList<proveedor> cargarListaProve() throws ClassNotFoundException, IOException, SQLException {
		
		ArrayList<proveedor> lista = new ArrayList<>();
		Statement miStatement = null;
		ResultSet miResulset = null;

		try {
			// ESTABLECER CONEXION Y USAR SENTENCIA SQL
			miConexion = conectar.conectar();
			String instruccion = "SELECT * FROM proveedores";//
			miStatement = miConexion.createStatement();

			/// EJECUTAR SQL
			miResulset = miStatement.executeQuery(instruccion);

			/// RECORRER EL RESULSET
			if (miResulset.next() == false)
				return null;
			else {
				do {
					lista.add(new proveedor(miResulset.getInt(1), miResulset.getString(2)));

				} while (miResulset.next());
			}
		} catch (SQLException e) {
               /* GENERAR EXCEPCION CONTROLADA*/
		} finally {
			miStatement.close();
			miConexion.close();
		}

		
		return lista;
	}



	public ObservableList<String> cargarListaPre() throws ClassNotFoundException, IOException, SQLException {
		ObservableList<String> lista = FXCollections.observableArrayList();
		Statement miStatement = null;
		ResultSet miResulset = null;

		try {
			// ESTABLECER CONEXION Y USAR SENTENCIA SQL
			miConexion = conectar.conectar();
			String instruccion = "SELECT DISTINCT prefijo FROM facturas";//
			miStatement = miConexion.createStatement();
			
			/// EJECUTAR SQL
			miResulset = miStatement.executeQuery(instruccion);

			/// RECORRER EL RESULSET

			if (miResulset.next() == false)
				return null;
			else {
				do {
					lista.add(miResulset.getString(1));
				} while (miResulset.next());
			}
		} catch (SQLException e) {
          /* GENERAR EXCEPCION CONTROLADA*/
		} finally {
			miStatement.close();
			miConexion.close();
		}

		
		return lista;
	}



	public int nuevaZona(String nombre) throws SQLException {
		String sql = "INSERT INTO zonas (nombre) VALUES (?)";
		miConexion = conectar.conectar();
		java.sql.PreparedStatement statement = miConexion.prepareStatement(sql);
		statement.setString(1, nombre);
		statement.execute();
		return 1;
	}



	public boolean eliminarZona(int id, int idAdmin, String pass) throws SQLException {
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
			 sql = "DELETE FROM zonas WHERE zonas.id=?";
			 statement = null;
			 statement = miConexion.prepareStatement(sql);
			 statement.setInt(1, id);
			 statement.execute();
			 statement.close();
			 eliminado=true;
		}
		
		
		return eliminado;
	}



	public boolean editarZona(int id, String nombre) {
		boolean query = false;
			try {
				miConexion = conectar.conectar();
				String sql = "UPDATE zonas SET zonas.nombre=? WHERE zonas.id=?";
				PreparedStatement statement = null;
				statement = miConexion.prepareStatement(sql);
				statement.setString(1, nombre);
				statement.setInt(2, id);
				statement.execute();
				statement.close();
				query=true;
			} catch (Exception e) {
				/* GENERAR EXCEPCION CONTROLADA */
				query=false;
			} finally {
				try {
					miConexion.close();
				} catch (Exception e) {
					/* GENERAR EXCEPCION CONTROLADA */
				}
			}
		return query;
	}

}
