package sucursal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import modelo.ModeloSucursal;
import clases.proveedor;

public class ControladorIngresarFactura implements Initializable {
	
	@FXML private DatePicker fecha;
	
	@FXML private ComboBox<String> listaDeTipos, listaProveedor, listaCuit;
	
	@FXML private TextField subtotal, iva1, iva2, iva3, ivaotros, total, nrofactura, prefijo = new TextField();
	
   ObservableList<String> tipo = FXCollections.observableArrayList("A", "B", "C");
   
   ObservableList<String> proveedor,cuit;
   
   ArrayList<String> listaNombreArray, listaCuitArray;
   
   
	public void initialize(URL location, ResourceBundle resources) {
		
		
		/// Solicita los datos a la BBDD de nombre de proveedor y su CUIT
		
		ModeloSucursal modelo =	 new ModeloSucursal();
		
		ArrayList<proveedor> listaProveedores = new ArrayList<proveedor>();
		
		try {
			listaProveedores = modelo.listaProveedores();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Genera lista en ArrayList tipo String
		
		generarListas(listaProveedores);
		
		/// Inserta los datos en cada lista de la GUI
		
		listaProveedor.setItems(proveedor);
		
		listaCuit.setItems(cuit);
		
		listaDeTipos.setItems(tipo);
		
		///Desactiva el textField total
		
		total.setDisable(true);
		
		/// Oyente para colocar la fecha como tope hasta el dia actual
		
		fecha.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	        	
	            super.updateItem(date, empty);
	            
	            LocalDate today = LocalDate.now();
	            
	            
	            setDisable(empty || date.compareTo(today) > 0 );
	        }
	    });
		
		
		
		///Oyente solo para el prefijo y factura, solo se pueden incluir numeros en estos campos
		
		ChangeListener<String> soloNumerosFactura = (observable, valorViejo, valorNuevo) -> {
		    if (!valorNuevo.matches("\\d*"))
		      ((StringProperty) observable).set(valorViejo);
		     
		    
		    
		    /// Maximo digitos permitidos por cada textfield
		      if (prefijo.getText().length() > 5) {
		    	  
	                String s = prefijo.getText().substring(0, 5);
	                
	                prefijo.setText(s);
		      }
		      
		      if (nrofactura.getText().length() > 12) {
		    	  
	                String s = nrofactura.getText().substring(0, 12);
	                
	                nrofactura.setText(s);
		      }
		    
		};
		
		
		///Oyente para colocar solo numeros dentro de los montos subtotal, impuestos y total
		/// Tambien incluye la suma de subtotal y todos los impuestos
		ChangeListener<String> soloNumeros = (observable, valorViejo, valorNuevo) -> {
		    if (!valorNuevo.matches("\\d*(\\.\\d*)?"))
		      ((StringProperty) observable).set(valorViejo);
		    
		    
		    /// Maximo digitos permitidos por cada textfield
		      if (subtotal.getText().length() > 10) {
	                String s = subtotal.getText().substring(0, 10);
	                subtotal.setText(s);
		      }
		      
		      if (iva1.getText().length() > 10) {
	                String s = iva1.getText().substring(0, 10);
	                iva1.setText(s);
		      }
		      
		      if (iva2.getText().length() > 10) {
	                String s = iva2.getText().substring(0, 10);
	                iva2.setText(s);
		      }
		      
		      if (iva3.getText().length() > 10) {
	                String s = iva3.getText().substring(0, 10);
	                iva3.setText(s);
		      }
		      
		      if (ivaotros.getText().length() > 10) {
	                String s = ivaotros.getText().substring(0, 10);
	                ivaotros.setText(s);
		      }
		   
		    
		    ///Suma los valores cada vez que ingresan un digito
		    double valorSubtotal =(!subtotal.getText().trim().isEmpty())?Double.parseDouble(subtotal.getText()):0;
	    	double valorIva1 =(!iva1.getText().trim().isEmpty())?Double.parseDouble(iva1.getText()):0;
	    	double valorIva2 =(!iva2.getText().trim().isEmpty())?Double.parseDouble(iva2.getText()):0;
	    	double valorIva3 =(!iva3.getText().trim().isEmpty())?Double.parseDouble(iva3.getText()):0;
	    	double valorOtros =(!ivaotros.getText().trim().isEmpty())?Double.parseDouble(ivaotros.getText()):0;
	    	double valor = valorSubtotal+ valorIva1 + valorIva2 + valorIva3 + valorOtros;
	    	
	    	/// Formato de salida a textfield texto, maximo 2 decimales
	    	
	    	DecimalFormat df = new DecimalFormat("####0.00");
	    	
	    	total.setText(""+df.format(valor));
		    
		};
		
		//Agrega los listener a los textfields
		prefijo.textProperty().addListener(soloNumerosFactura);
		
		nrofactura.textProperty().addListener(soloNumerosFactura);
		
		subtotal.textProperty().addListener(soloNumeros);
		
		iva1.textProperty().addListener(soloNumeros);
		
		iva2.textProperty().addListener(soloNumeros);
		
		iva3.textProperty().addListener(soloNumeros);
		
		ivaotros.textProperty().addListener(soloNumeros);
	}
	


	private void generarListas(ArrayList<clases.proveedor> listaProveedores) {
		// TODO Auto-generated method stub
		
	     listaNombreArray = new ArrayList<String>();
		
		 listaCuitArray = new ArrayList<String>();
		
		for(proveedor temp: listaProveedores) {
			
			listaNombreArray.add(temp.getNombre());
			
			listaCuitArray.add(temp.getCuit());
		}
		
		proveedor = FXCollections.observableArrayList(listaNombreArray);
		
		cuit = FXCollections.observableArrayList(listaCuitArray);
		
	}



	@FXML 
	private void ingresarFactura(MouseEvent event) {
    	
		/// Validacion de datos entradas, verifica los campos minimos para realizar una entrada a la BBDD
		if(
		   (fecha.getValue()!=null)&&
		   !listaDeTipos.getSelectionModel().isEmpty()&&
		   !listaProveedor.getSelectionModel().isEmpty()&&
		   !prefijo.getText().trim().isEmpty()&&
		   !nrofactura.getText().trim().isEmpty()&&
		   !subtotal.getText().trim().isEmpty()
		   
		  ) {
			
			/// Confirmacion de datos de entrada
		ButtonType Si = new ButtonType("Si", ButtonData.OK_DONE);
		
		ButtonType Cancelar = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		Alert alert = new Alert(AlertType.INFORMATION,"¿Deseas ingresar la factura actual?",Si,Cancelar);
		
		alert.setTitle("Ingreso de nuevo factura");
		
		alert.setHeaderText(null);
		
		Optional<ButtonType> resultado = alert.showAndWait();
		
		if (resultado.orElse(Cancelar) == Si) {
            
			  facturaBBDD();
                
		}}
		else {
			
			 /// Alerta de datos faltantes para ingresar una nueva factura
			  Alert alert = new Alert(Alert.AlertType.ERROR);
			  
			    alert.setHeaderText(null);
			    
			    alert.setTitle("Error");
			    
			    alert.setContentText("Faltan datos por completar");
			    
			    alert.showAndWait();
		}
		
    } 
	
	private void facturaBBDD() {
		// TODO Auto-generated method stub
		
		ModeloSucursal modelo = new ModeloSucursal();
		
		LocalDate datoFecha = fecha.getValue();
		
		String datoTipo = listaDeTipos.getValue();
		
		String datoProveedor = listaProveedor.getValue();
		
		String datoCuit = listaCuit.getValue();
		
		/// continuacion ..
		
		
	}



	@FXML
	private void seleccionPro(ActionEvent e) {
		
		String texto =  listaProveedor.getValue();
		
		int c =0;
		
		for(int i=0;i<listaNombreArray.size();i++) {
			
			if(texto==listaNombreArray.get(i))
				{c=i;break;}
			
		}
		
		listaCuit.getSelectionModel().select(c);
		
	}
	
	@FXML
	private void seleccionCuit(ActionEvent e) {
		
		String texto =  listaCuit.getValue();
		
		int c =0;
		
		for(int i=0;i<listaCuitArray.size();i++) {
			
			if(texto==listaCuitArray.get(i))
				{c=i;break;}
			
		}
		
		listaProveedor.getSelectionModel().select(c);
		
	}
	
	@FXML
	private void nuevoMonto(ActionEvent e) {
		
		//total.setText(""+Double.parseDouble(subtotal.getText()));
		
	}

}
