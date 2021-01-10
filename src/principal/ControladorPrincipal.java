package principal;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import clases.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ControladorPrincipal implements Initializable {
	
	ListaSucursal lista = new ListaSucursal();
	
	// Configurar tabla
	
	@FXML private TableView<factura> tableview;
	@FXML private TableColumn<factura, LocalDate> fecha;
	@FXML private TableColumn<factura, String> tipo, proveedor, cuit;
	@FXML private TableColumn<factura, Integer> prefijo, nrofactura;
	@FXML private TableColumn<factura, Double> subtotal, iva, total;

	
	@FXML private ComboBox<String> ListaSucursales;
	
	@FXML private ComboBox<String> ListaZona;
	
	ObservableList<String> listaSuc = FXCollections.observableArrayList("Sucursal A", "Sucursal B", "Sucursal C");
	
	ObservableList<String> listaZo = FXCollections.observableArrayList("Zona Norte", "Zona Sur");
	
	public void initialize(URL location, ResourceBundle resources) {
		
		fecha.setCellValueFactory(new PropertyValueFactory<factura, LocalDate>("fecha"));
		tipo.setCellValueFactory(new PropertyValueFactory<factura, String>("tipo"));
		proveedor.setCellValueFactory(new PropertyValueFactory<factura, String>("proveedor"));
		cuit.setCellValueFactory(new PropertyValueFactory<factura, String>("cuit"));
		prefijo.setCellValueFactory(new PropertyValueFactory<factura, Integer>("prefijo"));
		nrofactura.setCellValueFactory(new PropertyValueFactory<factura, Integer>("nrofactura"));
		subtotal.setCellValueFactory(new PropertyValueFactory<factura, Double>("subtotal"));
		iva.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva"));
		total.setCellValueFactory(new PropertyValueFactory<factura, Double>("total"));
		tableview.setItems(lista.getData());
		
		ListaSucursales.setItems(listaSuc);
		
		ListaZona.setItems(listaZo);
	
	}
	
	
	
	public void ListaSucursalesCambia(ActionEvent event) {
		
		
	}
	
	
}
