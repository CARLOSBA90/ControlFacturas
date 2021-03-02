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
import javafx.scene.control.TableView;

public class ModeloBusquedaPrincipal {

	
    private Connection miConexion=null;
	
	private Conexion conectar;
	
	public ModeloBusquedaPrincipal() {
		
		conectar = new Conexion();
	}

	public ObservableList<factura> obtenerLista(int zona, int sucursal, String condicional1, String condicional2,
			String formaPago, String impuestos, LocalDate fecha1, LocalDate fecha2) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		String Zona = (zona == -1)?  "*" : ""+zona;
		
		
		String Sucursal = (sucursal == -1)? "*" : ""+sucursal;
		
		
		switch(condicional1) {
		
		
		/// Condicion de busqueda por Proveedor
		case "PROVEEDOR":
			
			String condicional = (condicional2 == "todos")? "-1": condicional2;
			
			int proveedor = Integer.parseInt(condicional);
			
			/*
			 * 
			 * SELECT usuario.usuario, facturas.*, facturas.proveedor FROM facturas INNER
			 * JOIN sucursal_factura on sucursal_factura.idfactura = facturas.id INNER JOIN
			 * usuario on usuario.id = sucursal_factura.id WHERE
			 * facturas.proveedor="Aguas S.A";
			 * 
			 */
			/// colocar condicional2 como INT id de proveedor o prefijo
			
			
			///BUSQUEDA TIPO 1 CAMPOS: (SUCURSAL, FECHA, TIPO, PREFIJO, NROFACTURA, PROVEEDOR, FORMA, SUBTOTAL, TOTAL)
			
			
			// CONDICIONES: ZONAS -> TODOS, SUCURSAL -> TODOS, FORMA PAGO -> TODOS, IMPUESTOS -> NADA, FECHAS -> CAMPOS VACIOS
			// CONDICION 2: ELECCION DE PROVEEDOR BUSQUEDA TIPO 1 
			if(Zona == "*" && Sucursal=="*" && formaPago.equals("TODOS") && impuestos.equals("nada") && fecha1==null && fecha2==null)
				{
				 String bus = "1";
				String sql = "SELECT usuario.usuario, facturas.fecha, facturas.tipo, facturas.prefijo, facturas.nrofactura," + 
						"facturas.proveedor, facturas.forma, facturas.subtotal, facturas.total FROM facturas INNER JOIN sucursal_factura ON"
						+ " sucursal_factura.idfactura = facturas.id INNER JOIN usuario ON usuario.id = sucursal_factura.idsucursal INNER JOIN factura_proveedor ON"
						+ " factura_proveedor.idfactura = facturas.id INNER JOIN proveedores ON factura_proveedor.idproveedor = proveedores.id WHERE "
						+ "proveedores.id= "+proveedor;
				
				
				
				return busquedaBBDD(sql, bus);
				
				
				}
			
			
			break;
			
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

	private ObservableList<factura> busquedaBBDD(String sql, String bus) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		ObservableList<factura> lista = FXCollections.observableArrayList();
	       
		Statement miStatement=null;

		ResultSet miResulset=null;
		
		try {
			// ESTABLECER CONEXION Y USAR SENTENCIA SQL

				miConexion = conectar.conectar();
			
			  miStatement=miConexion.createStatement();

			/// EJECUTAR SQL

			miResulset=miStatement.executeQuery(sql);


			/// RECORRER EL RESULSET
			
			 if(miResulset.next() == false)
			       lista.add(new factura("SIN DATOS",LocalDate.now(),"SIN DATOS","SIN DATOS",0,0,"SIN DATOS",0,0));
			 else {
				 
				 switch (bus) {
				 
				 case "1":
	                do {
	                	
	    	   Date fechaSQL = miResulset.getDate(2);
	    	   
	    	   LocalDate fecha = fechaSQL.toLocalDate(); 
			
	   		lista.add(new factura(miResulset.getString(1), fecha ,miResulset.getString(3), miResulset.getString(6),
	   				 miResulset.getInt(4),miResulset.getInt(5),miResulset.getString(7),miResulset.getDouble(8),
	   				 miResulset.getDouble(9)));
				
			           }while(miResulset.next());
	                
	                break;
	                
	                
	             default:
	            	 System.out.println("NO BUS, NO SENTENCIA");
				             }
			            }
		}catch(SQLException e) {

				e.printStackTrace();
			}finally {
				miStatement.close();
				miConexion.close();
			}
		
		
		return lista;
		
	}



	
	
}
