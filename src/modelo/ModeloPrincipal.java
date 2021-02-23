package modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import clases.factura;
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
		// TODO Auto-generated method stub
		
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

			e.printStackTrace();
		} finally {
			miStatement.close();
			miConexion.close();
		}

		return lista;
	}



	public ObservableList<String> cargarListaProve() {
		// TODO Auto-generated method stub
		return null;
	}



	public ObservableList<String> cargarListaPre() {
		// TODO Auto-generated method stub
		return null;
	}

}
