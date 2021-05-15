package modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import clases.zona;

public class ListarZonas extends DBTask<ArrayList<zona>>{

	private Connection miConexion = null;
	private Conexion conectar;

	public ListarZonas() {
		conectar = new Conexion();
	}
	
	protected ArrayList<zona> call() throws Exception {
		return listaZonas();
	}
	
	public ArrayList<zona> listaZonas() throws SQLException, ClassNotFoundException, IOException {
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

}
