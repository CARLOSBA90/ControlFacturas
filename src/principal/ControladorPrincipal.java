package principal;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import clases.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class ControladorPrincipal implements Initializable {
	
	ListaSucursal lista = new ListaSucursal();
	
	@FXML private AnchorPane pane;
	
	@FXML private Pane central,centralInferior,cabecera, menu;
	
	// Configurar tabla
	
	@FXML private TableView<factura> tableview;
	@FXML private TableColumn<factura, LocalDate> fecha;
	@FXML private TableColumn<factura, String> tipo, proveedor, cuit;
	@FXML private TableColumn<factura, Integer> prefijo, nrofactura;
	@FXML private TableColumn<factura, Double> subtotal, iva, iva2, iva3, otros, total;
	@FXML private ComboBox<String> ListaSucursales, ListaZona;
	
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
		iva2.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva2"));
		iva3.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva3"));
		otros.setCellValueFactory(new PropertyValueFactory<factura, Double>("otros"));
		total.setCellValueFactory(new PropertyValueFactory<factura, Double>("total"));
		tableview.setItems(lista.getData());
		
		ListaSucursales.setItems(listaSuc);
		
		ListaZona.setItems(listaZo);
	
	}
	
	
	
	public void ListaSucursalesCambia(ActionEvent event) {
		
		
	}
	
	@FXML 
	private void salir(MouseEvent event) {
		
		Stage stage = (Stage) pane.getScene().getWindow();
		stage.close();
		
	}
	
	
	@FXML 
	private void busqueda(MouseEvent event) throws IOException {
	
		cargarUI("busqueda");
	}
	
    private void cargarUI(String ui) throws IOException {
		
		Parent root = null;
	
	     root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
		 
		central.getChildren().clear();
		
		menu.getChildren().clear();
	
		cabecera.getChildren().clear();
		
		cabecera.setPrefHeight(75);
		
		centralInferior.getChildren().clear();
		
		Label tituloCabecera = new Label ("Busqueda Avanzada");
		
		tituloCabecera.setFont(new Font("Calibri", 34));
		
		tituloCabecera.setTextFill(Color.web("#868686"));
		
		tituloCabecera.setTranslateX(23.0);
		
		tituloCabecera.setTranslateY(24.0);	
		
		cabecera.getChildren().add(tituloCabecera);
		
		cabecera.setStyle("-fx-background-color: #F8F8FF;");
		
		central.setPrefHeight(120);
		
		central.getChildren().add(root);
		
		tableview.setPrefHeight(405);
	
		tableview.setItems(lista.getData());
		
		centralInferior.getChildren().add(tableview);
		
		
	}
	
	
}
