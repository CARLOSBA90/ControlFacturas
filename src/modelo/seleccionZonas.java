package modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import clases.sucursal;

public class seleccionZonas extends DBTask<ArrayList<sucursal>> {
	
	private Connection miConexion = null;
	private Conexion conectar;
	private int id;
	
	public seleccionZonas(int id) {
		this.id = id;
		conectar = new Conexion();
	}

	@Override
	protected ArrayList<sucursal> call() throws Exception {
		return listaSucursales(id);
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
			miResulset.close();
			miStatement.close();
			miConexion.close();
		}

		return lista;
	}

}
