package sucursal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	@FXML private TextField subtotal, iva1, iva2, iva3, ivaotros, total = new TextField();
	
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
		
		
		///Oyente para colocar solo numeros dentro de los montos subtotal, impuestos y total
		
		ChangeListener<String> soloNumeros = (observable, valorViejo, valorNuevo) -> {
		    if (!valorNuevo.matches("\\d*(\\.\\d*)?"))
		      ((StringProperty) observable).set(valorViejo);
		};
		
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
    	
    	
    	System.out.println("Ingresaste factura");
    /*	double valor =Double.parseDouble(subtotal.getText())+
    			Double.parseDouble(iva1.getText())+
    			Double.parseDouble(iva2.getText())+
    			Double.parseDouble(iva3.getText())+
    			Double.parseDouble(ivaotros.getText());
    	total.setText(""+valor);*/
    	
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
