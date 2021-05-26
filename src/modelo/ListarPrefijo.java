package modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import clases.factura;
import clases.proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListarPrefijo extends DBTask<ObservableList<String>>{
    private Connection miConexion=null;
	private Conexion conectar;
	
	public ListarPrefijo() {
		conectar = new Conexion();
	}
	
	@Override
	protected ObservableList<String> call() throws Exception {
		// TODO Auto-generated method stub
		return cargarListaPre();
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




}
