package modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;

import clases.factura;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModeloSucursal {
	
    private Connection miConexion=null;
	
	private Conexion conectar;
	
	public ModeloSucursal() {
		
		conectar = new Conexion();
	}

	public ObservableList<factura> cargarData(int id) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		ObservableList<factura> lista = FXCollections.observableArrayList();
       
		Statement miStatement=null;

		ResultSet miResulset=null;
		
		try {
			// ESTABLECER CONEXION Y USAR SENTENCIA SQL

				miConexion = conectar.conectar();
			
				String instruccion="SELECT facturas.id, facturas.fecha, facturas.tipo, facturas.prefijo, facturas.nrofactura, facturas.proveedor,"
						+ " facturas.cuit, facturas.subtotal, facturas.iva1, facturas.iva2, facturas.iva3, "
						+ " facturas.otro, facturas.total from facturas INNER JOIN sucursal_factura on"
                        + " facturas.id=sucursal_factura.idfactura where sucursal_factura.idsucursal="+Integer.toString(id);// 

			  miStatement=miConexion.createStatement();

			/// EJECUTAR SQL

			miResulset=miStatement.executeQuery(instruccion);


			/// RECORRER EL RESULSET

	       while(miResulset.next()) {
	    	   
	    	   Date fechaSQL = miResulset.getDate(2);
	    	   
	    	   LocalDate fecha = fechaSQL.toLocalDate(); 
			
	   		lista.add(new factura(fecha ,miResulset.getString(3),miResulset.getString(6),
	   				miResulset.getString(7),miResulset.getInt(4),miResulset.getInt(5), miResulset.getDouble(8),
	   				miResulset.getDouble(9), miResulset.getDouble(10), miResulset.getDouble(11), miResulset.getDouble(12),
	   				miResulset.getDouble(13)));
				
			}}catch(SQLException e) {

				e.printStackTrace();
			}finally {
				miStatement.close();
				miConexion.close();
			}
		
		
		
		
		
		return lista;
	}


	
	
	

}
