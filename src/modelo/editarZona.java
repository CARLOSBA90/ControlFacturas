package modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import clases.booleano;

public class editarZona extends DBTask<booleano> {
    private Connection miConexion=null;
	private Conexion conectar;
	private int id;
	private String nombre;
	
	public editarZona(int id, String nombre) {
		conectar = new Conexion();
	    this.id = id;
	    this.nombre = nombre;
	}
	
	protected booleano call() throws Exception {
		return editar();
	}


	public booleano editar() {
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
		return new booleano(query);
	}

}
