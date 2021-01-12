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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
	@FXML private TableColumn<factura, Double> subtotal, iva, iva2,iva3, otros, total;
	

	
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
	
	}
	
	
	@FXML 
	private void historial(MouseEvent event) {
		
		central.getChildren().clear();
	
		tableview.setItems(lista.getData());
		
		tableview.setPrefHeight(524);
		
		central.getChildren().add(tableview);
		
		cabecera.getChildren().clear();
		
		centralInferior.getChildren().clear();
		
		Label tituloCabecera = new Label ("Ultimas facturas ingresadas");
		
		tituloCabecera.setFont(new Font("Calibri", 34));
		
		tituloCabecera.setTextFill(Color.web("#868686"));
		
		tituloCabecera.setTranslateX(23.0);
		
		tituloCabecera.setTranslateY(24.0);	
		
		cabecera.setStyle("-fx-background-color: #F8F8FF;");
		
		cabecera.getChildren().add(tituloCabecera);
		
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
		
		centralInferior.getChildren().clear();
		
		Label tituloCabecera = new Label ("Ingreso de nueva factura");
		
		tituloCabecera.setFont(new Font("Calibri", 34));
		
		tituloCabecera.setTextFill(Color.web("#868686"));
		
		tituloCabecera.setTranslateX(23.0);
		
		tituloCabecera.setTranslateY(24.0);	
		
		cabecera.getChildren().add(tituloCabecera);
		
		cabecera.setStyle("-fx-background-color: #F8F8FF;");
		
		central.getChildren().add(root);
		
		tableview.setPrefHeight(322);
	
		tableview.setItems(lista.getData());
		
		centralInferior.getChildren().add(tableview);
		
		
	}
	
}
