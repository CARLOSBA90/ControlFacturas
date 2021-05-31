package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import clases.factura;

public class ModeloSucursalFactura extends DBTask{
	private Connection miConexion = null;
	private Conexion conectar;
	private int id;
	private factura factura;
	
	protected Object call() throws Exception {
		// TODO Auto-generated method stub
		return insertarFactura(factura,id);
	}
	
	public ModeloSucursalFactura() {
		conectar = new Conexion();
	}
	
	public ModeloSucursalFactura(factura factura, int id) {
		conectar = new Conexion();
		this.factura = factura;
		this.id = id;
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
			statementProveedor.close();
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
