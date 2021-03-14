package modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import clases.factura;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModeloBusquedaPrincipal {
	private Connection miConexion = null;
	private Conexion conectar;
	private boolean puerta = false;

	public ModeloBusquedaPrincipal() {
		conectar = new Conexion();
	}

	public ObservableList<factura> obtenerLista(int zona, int sucursal, String condicional1, String condicional2,
			String formaPago, String impuestos, LocalDate fecha1, LocalDate fecha2)
			throws ClassNotFoundException, IOException, SQLException {

		String Zona = (zona == -1) ? "*" : "" + zona;
		String Sucursal = (sucursal == -1) ? "*" : "" + sucursal;

		switch (condicional1) {

		/* Condicion de busqueda por Proveedor */
		case "PROVEEDOR":

			String condicional = (condicional2 == "todos") ? "-1" : condicional2;
			int proveedor = Integer.parseInt(condicional);
			/* colocar condicional2 como INT id de proveedor o prefijo
			
		     BUSQUEDA TIPO "BUS" 1 CAMPOS: (SUCURSAL, FECHA, TIPO, PREFIJO, NROFACTURA,
			 PROVEEDOR, FORMA, SUBTOTAL, TOTAL)
			
			 BUSQUEDA TIPO "BUS" 2 CAMPOS: (SUCURSAL, FECHA, TIPO, PREFIJO, NROFACTURA,
			 PROVEEDOR, FORMA, SUBTOTAL, IVA1, IVA2, IVA3, OTROS, TOTAL)*/

			/* CONDICION 2: ELECCION DE PROVEEDOR, BUSQUEDA TIPO 1 */
			String bus = "";
			String sql = "SELECT usuario.usuario, facturas.fecha, facturas.tipo, facturas.prefijo, facturas.nrofactura,"
						+ "facturas.proveedor, facturas.forma, facturas.subtotal, facturas.total";

				if (impuestos.equals("TODOS")) {

					sql += ", facturas.iva1, facturas.iva2, facturas.iva3, facturas.otro";

					bus = "2";

				} else
					bus = "1";

				sql += " FROM facturas INNER JOIN sucursal_factura ON"
						+ " sucursal_factura.idfactura = facturas.id INNER JOIN usuario ON usuario.id = sucursal_factura.idsucursal INNER JOIN factura_proveedor ON"
						+ " factura_proveedor.idfactura = facturas.id INNER JOIN proveedores ON factura_proveedor.idproveedor = proveedores.id";

				/* Aplicacion de query, modelo relacional entre tablas, verifica si tiene zona especificada y sucursal como todos*/

				if (Zona != "*" && Sucursal == "*") {
					sql += " INNER JOIN sucursal_zona ON sucursal_zona.idsucursal = usuario.id INNER JOIN  zonas ON zonas.id = sucursal_zona.idzonas WHERE zonas.id ="
							+ Zona;
					if (!puerta)
						puerta = true;
				} else if (Sucursal != "*") {
					sql = operador(sql, puerta);
					sql += "usuario.id=" + Integer.parseInt(Sucursal);
				}

				/* Verifica si tiene provedor como todos o uno en especifico*/
				if (proveedor != -1) {
					sql = operador(sql, puerta);
					sql += "proveedores.id=" + proveedor;

				}

				/* Forma de pago diferente de todos, proveedor igual a todos */
				if (!formaPago.equals("TODOS") && proveedor == -1) {
					sql = operador(sql, puerta);
					sql += "facturas.forma='" + formaPago.toLowerCase() + "'";
				}

				else if (!formaPago.equals("TODOS") && proveedor != -1) {
					sql = operador(sql, puerta);
					sql += "facturas.forma='" + formaPago.toLowerCase() + "'";
				}

				if (fecha1 != null && fecha2 != null) {
			           
					 if(fecha2.isBefore(fecha1)) {
						 LocalDate temp = fecha1;
						 fecha1 = fecha2;
						 fecha2 = temp;
					 }
					 sql = operador(sql, puerta);
					 sql += "fecha BETWEEN '"+fecha1+"' AND '"+fecha2+"'";
					
				} else if (fecha1 != null){
					sql = operador(sql, puerta);
					sql += " fecha >= '"+fecha1+"'";
					
				} else if (fecha2 != null){
					sql = operador(sql, puerta);
					sql += " fecha <= '"+fecha2+"'";
				}

			return busquedaBBDD(sql, bus);
		/// Condicion de busqueda por Tipo
		case "TIPO":

			break;

		/// Condicion de busqueda por Prefijo
		case "PREFIJO":

			break;

		/// Condicion de busqueda por FECHA
		case "FECHA":

			break;

		default:
			System.out.println("ENTRADA METODO DEFAULT, CLASE ModeloBusquedaPrincipal");
		}
		return null;
	}

	public String operador(String sql, boolean e) {
   /* Metodo para query en la que implementamos las palabras reservadas AND o WHERE, en determinados casos*/
		if (e)
			sql += " AND ";
		else
			sql += " WHERE ";
		if (!puerta)
			puerta = true;
		return sql;
	}

	private ObservableList<factura> busquedaBBDD(String sql, String bus) throws SQLException {
		ObservableList<factura> lista = FXCollections.observableArrayList();
		Statement miStatement = null;
		ResultSet miResulset = null;

		try {
			// ESTABLECER CONEXION Y USAR SENTENCIA SQL
			miConexion = conectar.conectar();
			miStatement = miConexion.createStatement();

			/// EJECUTAR SQL
			miResulset = miStatement.executeQuery(sql);

			/// RECORRER EL RESULSET

			if (miResulset.next() == false)
				lista.add(new factura("SIN DATOS", LocalDate.now(), "SIN DATOS", "SIN DATOS", 0, 0, "SIN DATOS", 0, 0,
						0, 0, 0, 0));
			else {
				switch (bus) {

				case "1":
					do {
						Date fechaSQL = miResulset.getDate(2);
						LocalDate fecha = fechaSQL.toLocalDate();
						lista.add(new factura(miResulset.getString(1), fecha, miResulset.getString(3),
								miResulset.getString(6), miResulset.getInt(4), miResulset.getInt(5),
								miResulset.getString(7), miResulset.getDouble(8), miResulset.getDouble(9)));
					} while (miResulset.next());

					break;

				case "2":

					do {
						Date fechaSQL = miResulset.getDate(2);
						LocalDate fecha = fechaSQL.toLocalDate();
						lista.add(new factura(miResulset.getString(1), fecha, miResulset.getString(3),
								miResulset.getString(6), miResulset.getInt(4), miResulset.getInt(5),
								miResulset.getString(7), miResulset.getDouble(8), miResulset.getDouble(10),
								miResulset.getDouble(11), miResulset.getDouble(12), miResulset.getDouble(13),
								miResulset.getDouble(9)));

					} while (miResulset.next());
					break;

				default:
					System.out.println("NO BUS, NO SENTENCIA");
				}
			}
		} catch (SQLException e) {
			/* Agregar excepcion al metodo*/
		} finally {
			miStatement.close();
			miConexion.close();
		}

		return lista;

	}

}
