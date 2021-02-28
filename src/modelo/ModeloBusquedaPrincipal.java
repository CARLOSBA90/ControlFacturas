package modelo;

import java.sql.Connection;
import java.time.LocalDate;

import clases.factura;
import javafx.scene.control.TableView;

public class ModeloBusquedaPrincipal {

	
    private Connection miConexion=null;
	
	private Conexion conectar;
	
	public ModeloBusquedaPrincipal() {
		
		conectar = new Conexion();
	}

	public TableView<factura> obtenerLista(int zona, int sucursal, String condicional1, String condicional2,
			String formaPago, String impuestos, LocalDate fecha1, LocalDate fecha2) {
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
			
			
			
			// Todos excepto eleccion de proveedor
			if(Zona == "*" && Sucursal=="*" && formaPago.equals("TODOS") && impuestos.equals("todos") && fecha1==null && fecha2==null)
				{
				
				
				
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
			System.out.println("Entrada al default en metodo obtenerLista, clase ModeloBusquedaPrincipal");
		
		
		
		
		}
		
		
		
		
		
		
		
		return null;
	}



	
	
}
