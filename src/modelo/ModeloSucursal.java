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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModeloSucursal {
	private Connection miConexion = null;
	private Conexion conectar;
	
	public ModeloSucursal() {
		conectar = new Conexion();
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
				lista.add(new factura(LocalDate.now(), "-", "SIN DATOS", "SIN DATOS", 0, 0, 0, 0, 0, 0, 0, 0));
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
			String sqlAU = "SELECT AUTO_INCREMENT FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'controlfacturas' "
					+ "AND   TABLE_NAME   = 'facturas'";
			statementAU = miConexion.createStatement();
			rs_AU = statementAU.executeQuery(sqlAU);
			if (rs_AU.next()) {
				proximo = rs_AU.getInt(1);
			}
			rs_AU.close();

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

}
