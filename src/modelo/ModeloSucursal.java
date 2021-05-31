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
import clases.listado;
import clases.proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModeloSucursal extends DBTask<listado>{
	private Connection miConexion = null;
	private Conexion conectar;
	private int id;
	
	public ModeloSucursal() {
		conectar = new Conexion();
	}
	
	public ModeloSucursal(int id) {
		conectar = new Conexion();
		this.id=id;
	}
	protected listado call() throws Exception {
		return cargarLista(id);
	}

	private listado cargarLista(int id) throws ClassNotFoundException, IOException, SQLException {
		ObservableList<factura> lista = FXCollections.observableArrayList();
		Statement miStatement = null;
		ResultSet miResulset = null;
		try {
			// ESTABLECER CONEXION Y USAR SENTENCIA SQL
			miConexion = conectar.conectar();
			String instruccion = "SELECT facturas.id, facturas.fecha, facturas.tipo, facturas.prefijo, facturas.nrofactura, facturas.proveedor,"
					+ " facturas.cuit, facturas.subtotal, facturas.iva1, facturas.iva2, facturas.iva3, "
					+ " facturas.otro, facturas.total, facturas.forma from facturas INNER JOIN sucursal_factura on"
					+ " facturas.id=sucursal_factura.idfactura where sucursal_factura.idsucursal=" + id;//
			miStatement = miConexion.createStatement();

			/// EJECUTAR SQL
			miResulset = miStatement.executeQuery(instruccion);

			/// RECORRER EL RESULSET
			if (miResulset.next() == false)
				lista.add(new factura(LocalDate.now(), "-", "SIN DATOS", "SIN DATOS", 0, 0, "SIN DATOS",0, 0, 0, 0, 0, 0));
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
		} catch (SQLException e) {
                     /* GENERAR EXCEPCION CONTROLADA */
		} finally {
			miResulset.close();
			miStatement.close();
			miConexion.close();
		}
		
		listado listado = new listado(lista);
		
		return listado;
	}

	public ObservableList<factura> cargarData(int id) throws ClassNotFoundException, IOException, SQLException {
		ObservableList<factura> lista = FXCollections.observableArrayList();
		Statement miStatement = null;
		ResultSet miResulset = null;
		try {
			// ESTABLECER CONEXION Y USAR SENTENCIA SQL
			miConexion = conectar.conectar();
			String instruccion = "SELECT facturas.id, facturas.fecha, facturas.tipo, facturas.prefijo, facturas.nrofactura, facturas.proveedor,"
					+ " facturas.cuit, facturas.subtotal, facturas.iva1, facturas.iva2, facturas.iva3, "
					+ " facturas.otro, facturas.total, facturas.forma from facturas INNER JOIN sucursal_factura on"
					+ " facturas.id=sucursal_factura.idfactura where sucursal_factura.idsucursal=" + id;//
			miStatement = miConexion.createStatement();

			/// EJECUTAR SQL
			miResulset = miStatement.executeQuery(instruccion);

			/// RECORRER EL RESULSET
			if (miResulset.next() == false)
				lista.add(new factura(LocalDate.now(), "-", "SIN DATOS", "SIN DATOS", 0, 0, "SIN DATOS",0, 0, 0, 0, 0, 0));
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
		} catch (SQLException e) {
                     /* GENERAR EXCEPCION CONTROLADA */
		} finally {
			miResulset.close();
			miStatement.close();
			miConexion.close();
		}

		return lista;
	}

	public ArrayList<proveedor> listaProveedores() throws SQLException, ClassNotFoundException, IOException {
		ArrayList<proveedor> lista = new ArrayList<proveedor>();
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

			       while (miResulset.next()) {
				          lista.add(new proveedor(miResulset.getInt(1), miResulset.getString(2)
						  , miResulset.getString(3)));};

		     } catch (SQLException e) {
                       /* GENERAR EXCEPCION CONTROLADA */
		} finally {
			miResulset.close();
			miStatement.close();
			miConexion.close();
		}
		return lista;
	}

	public boolean insertarFactura(factura factura, int id) throws SQLException {
		boolean insercion = false;
		try {
			/// Transaccion!
			miConexion = conectar.conectar();
			miConexion.setAutoCommit(false);

			/// ------------------------------------------------------------------------------------------------
			/// OBTENER EL PROXIMO AUTO-INCREMENT DE LA TABLA PEDIDOS PARA UTILIZARLO EN LA
			/// TABLA PEDIDO_PRODUCTO
			Statement statementAU = null;
			ResultSet rs_AU = null;
			int proximo = 0;
			String sqlAU = "SELECT AUTO_INCREMENT FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'heroku_52d8d334e25a240' "
					+ "AND   TABLE_NAME   = 'facturas'";
			statementAU = miConexion.createStatement();
			rs_AU = statementAU.executeQuery(sqlAU);
			if (rs_AU.next()) {
				proximo = rs_AU.getInt(1);
			}
			rs_AU.close();
			statementAU.close();

			/// ------------------------------------------------------------------------------------------------

			//// INSERCION RELACION TABLA FACTURA PROVEEDOR

			// PRIMERO BUSCA SI EL PROVEEDOR EXISTE DENTRO DE LA TABLA(SELECCIONA EL NUMERO
			// DE CODIGO DE PROVEEDOR)
			Statement statementProveedor = null;
			ResultSet rs_Proveedor = null;
			String sqlProveedor = "SELECT proveedores.id FROM proveedores WHERE proveedores.nombre = '"
					+ factura.getProveedor().toString() + "' AND proveedores.cuit = '" + factura.getCuit().toString()
					+ "'";
			int cod_proveedor = 0;
			statementProveedor = miConexion.createStatement();
			rs_Proveedor = statementProveedor.executeQuery(sqlProveedor);
			if (rs_Proveedor.next()) {
				cod_proveedor = rs_Proveedor.getInt(1);
			}
			rs_Proveedor.close();
			if (cod_proveedor == 0 || proximo == 0)
				throw new Exception("Valores no encontrado: proximo ID factura o codigo Proveedor");
			
			statementProveedor.close();
			rs_Proveedor.close();
			/// ------------------------------------------------------------------------------------------------
			/// INSERCION VALORES TABLA FACTURA
			String sql = "INSERT INTO facturas(fecha,tipo,prefijo,nrofactura,proveedor,cuit,"
					+ "subtotal,iva1,iva2,iva3,otro,total,id,forma" + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement miStatement = null;
			miStatement = miConexion.prepareStatement(sql);
			miStatement.setDate(1, Date.valueOf(factura.getFecha()));
			miStatement.setString(2, factura.getTipo());
			miStatement.setInt(3, factura.getPrefijo());
			miStatement.setInt(4, factura.getNrofactura());
			miStatement.setString(5, factura.getProveedor());
			miStatement.setString(6, factura.getCuit());
			miStatement.setDouble(7, factura.getSubtotal());
			miStatement.setDouble(8, factura.getIva());
			miStatement.setDouble(9, factura.getIva2());
			miStatement.setDouble(10, factura.getIva3());
			miStatement.setDouble(11, factura.getOtros());
			miStatement.setDouble(12, factura.getTotal());
			miStatement.setInt(13, proximo);
			miStatement.setString(14, factura.getForma());
			miStatement.execute();
			miStatement.close();

			/// ------------------------------------------------------------------------------------------------
			/// INSERCION VALORES FACTURA PROVEEDOR EN DICHA TABLA

			String sqlFP = "INSERT INTO factura_proveedor(idfactura,idproveedor) values(?,?)";
			PreparedStatement ST_FP = null;
			ST_FP = miConexion.prepareStatement(sqlFP);
			ST_FP.setInt(1, proximo);
			ST_FP.setInt(2, cod_proveedor);
			ST_FP.execute();
			ST_FP.close();

			/// ------------------------------------------------------------------------------------------------
			/// INSERCION VALORES SUCURSAL FACTURA EN DICHA TABLA

			String sqlSF = "INSERT INTO sucursal_factura(idsucursal,idfactura) values(?,?)";
			PreparedStatement ST_SF = null;
			ST_SF = miConexion.prepareStatement(sqlSF);
			ST_SF.setInt(1, id);
			ST_SF.setInt(2, proximo);
			ST_SF.execute();
			ST_SF.close();

			/// COMPLETAR TODOS LOS QUERYS O NO COMPLETAR NINGUNO, TRANSACCION
			miConexion.commit();
			insercion = true;

		} catch (Exception e) {
			miConexion.rollback();
			insercion = false;
		} finally {
			try {
				miConexion.close();
			} catch (Exception e) {
				/* GENERAR EXCEPCION CONTROLADA*/
			}
		}
		return insercion;
	}

	public int nuevaSucursal(int idZona, String user, String pass) throws SQLException {
		int insercion = -1;
		try {
			miConexion = conectar.conectar();
            /// Chequeo de usuario existente
			PreparedStatement statement=null;
			String sql = "SELECT id from usuario WHERE usuario=?";
			statement = miConexion.prepareStatement(sql);
			statement.setString(1,user);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) return 1;
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
			statementAU.close();
			
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
		
		return insercion;
	}

	public boolean eliminarSucursal(int id, int idAdmin, String pass) throws SQLException, ClassNotFoundException, IOException {
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
			 sql = "DELETE FROM usuario WHERE usuario.id=?";
			 statement = null;
			 statement = miConexion.prepareStatement(sql);
			 statement.setInt(1, id);
			 statement.execute();
			 statement.close();
			 eliminado=true;
		}
		
		rs.close();
		statement.close();
		miConexion.close();
		return eliminado;
	}

	public int editarSucursal(int idSucursal,int idZona, String user, String pass, int idZonaAnterior) throws SQLException {
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

		return insercion;
	}




}
