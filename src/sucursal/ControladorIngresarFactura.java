package sucursal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import modelo.ModeloSucursal;
import clases.proveedor;

public class ControladorIngresarFactura implements Initializable {
	
	@FXML private DatePicker fecha;
	@FXML private ComboBox<String> listaDeTipos, listaProveedor, listaCuit;
	
   ObservableList<String> tipo = FXCollections.observableArrayList("A", "B", "C");
   ObservableList<String> proveedor;
   ObservableList<String> cuit;
	
	public void initialize(URL location, ResourceBundle resources) {
		
		ModeloSucursal modelo =	 new ModeloSucursal();
		
		ArrayList<proveedor> listaProveedores = new ArrayList<proveedor>();
		
		try {
			listaProveedores = modelo.listaProveedores();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		generarListas(listaProveedores);
		
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
	


	private void generarListas(ArrayList<clases.proveedor> listaProveedores) {
		// TODO Auto-generated method stub
		
		ArrayList<String> listaNombre = new ArrayList<String>();
		
		ArrayList<String> listaCuit = new ArrayList<String>();
		
		for(proveedor temp: listaProveedores) {
			
			listaNombre.add(temp.getNombre());
			listaCuit.add(temp.getCuit());
		}
		
		proveedor = FXCollections.observableArrayList(listaNombre);
		cuit = FXCollections.observableArrayList(listaCuit);
		
	}



	@FXML 
	private void ingresarFactura(MouseEvent event) {
    	
    	
    	System.out.println("Ingresaste factura");
    	
    } 
	
	@FXML
	private void seleccionPro(ActionEvent e) {
		
		//System.out.println(listaProveedor.getValue());
	}

}
