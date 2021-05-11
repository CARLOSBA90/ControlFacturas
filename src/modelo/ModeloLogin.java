package modelo;
import clases.acceso;
import clases.factura;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
				if (nivel == 2) {
					ObservableList<factura> lista = FXCollections.observableArrayList();
					instruccion = "SELECT facturas.id, facturas.fecha, facturas.tipo, facturas.prefijo, facturas.nrofactura, facturas.proveedor,"
							+ " facturas.cuit, facturas.subtotal, facturas.iva1, facturas.iva2, facturas.iva3, "
							+ " facturas.otro, facturas.total, facturas.forma from facturas INNER JOIN sucursal_factura on"
							+ " facturas.id=sucursal_factura.idfactura where sucursal_factura.idsucursal=?";
					statement = miConexion.prepareStatement(instruccion);
					statement.setInt(1, miResulset.getInt(1));
					miResulset = statement.executeQuery();
					if (miResulset.next() == false)
						lista.add(new factura(LocalDate.now(), "-", "SIN DATOS", "SIN DATOS", 0, 0, "SIN DATOS", 0, 0,
								0, 0, 0, 0));
					else {
						do {
							Date fechaSQL = miResulset.getDate(2);
							LocalDate fecha = fechaSQL.toLocalDate();
							lista.add(new factura(fecha, miResulset.getString(3), miResulset.getString(6),
									miResulset.getString(7), miResulset.getInt(4), miResulset.getInt(5),
									miResulset.getString(14), miResulset.getDouble(8), miResulset.getDouble(9),
									miResulset.getDouble(10), miResulset.getDouble(11), miResulset.getDouble(12),
									miResulset.getDouble(13)));
						} while (miResulset.next());
					}
					
					access = new acceso(id, usuario, nivel, lista);
				} else if (nivel == 1) {
					
					access = new acceso(id, usuario, nivel);
				}
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
