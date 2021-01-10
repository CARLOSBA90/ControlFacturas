package sucursal;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

import clases.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ControladorSucursal implements Initializable {
	
	ListaSucursal lista = new ListaSucursal();
	
	@FXML private AnchorPane pane;
	
	@FXML private Pane central,centralInferior,cabecera;
	
	
	// Configurar tabla
	
	@FXML private TableView<factura> tableview;
	@FXML private TableColumn<factura, LocalDate> fecha;
	@FXML private TableColumn<factura, String> tipo, proveedor, cuit;
	@FXML private TableColumn<factura, Integer> prefijo, nrofactura;
	@FXML private TableColumn<factura, Double> subtotal, iva, total;
	

	
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
	
	}
	
	
	@FXML 
	private void historial(MouseEvent event) {
		
		central.getChildren().clear();
	
		tableview.setItems(lista.getData());
		
		central.getChildren().add(tableview);
		
	}
	
	
	@FXML 
	private void salir(MouseEvent event) {
		
		Stage stage = (Stage) pane.getScene().getWindow();
		stage.close();
		
	}
	
	@FXML 
	private void ingresar(MouseEvent event) throws IOException {
	
		cargarUI("ingresar");
	}
	
	
	private void cargarUI(String ui) throws IOException {
		
		Parent root = null;
	
	     root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
		 
		central.getChildren().clear();
		
		cabecera.getChildren().clear();
		
		cabecera.getChildren().add(new Label("Ingreso de nueva factura"));
		
		central.getChildren().add(root);
	
		tableview.setItems(lista.getData());
		
		centralInferior.getChildren().add(tableview);
		
		
	}
	
}
