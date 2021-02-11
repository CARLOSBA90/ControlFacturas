package sucursal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;

public class ControladorIngresarFactura implements Initializable {
	
	@FXML private DatePicker fecha;
	@FXML private ComboBox<String> listaDeTipos, listaProveedor, listaCuit;
	
   ObservableList<String> tipo = FXCollections.observableArrayList("A", "B", "C");
   ObservableList<String> proveedor = FXCollections.observableArrayList("A", "B", "C");
   ObservableList<String> cuit = FXCollections.observableArrayList("A", "B", "C");
	
	public void initialize(URL location, ResourceBundle resources) {
		
		listaProveedor.setItems(proveedor);
		
		listaCuit.setItems(cuit);
		
		listaDeTipos.setItems(tipo);
		
		fecha.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate today = LocalDate.now();

	            setDisable(empty || date.compareTo(today) > 0 );
	        }
	    });
	}
	
    @FXML 
	private void ingresarFactura(MouseEvent event) {
    	
    	
    	System.out.println("Ingresaste factura");
    	
    } 

}
