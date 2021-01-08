package sucursal;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

import clases.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ControladorSucursal implements Initializable {
	
	
	// Configurar tabla
	
	@FXML private TableView<factura> tableview;
	@FXML private TableColumn<factura, LocalDate> fecha;
	@FXML private TableColumn<factura, String> tipo;
	@FXML private TableColumn<factura, String> proveedor;
	@FXML private TableColumn<factura, String> cuit;
	@FXML private TableColumn<factura, Integer> prefijo;
	@FXML private TableColumn<factura, Integer> nrofactura;
	@FXML private TableColumn<factura, Double> subtotal;
	@FXML private TableColumn<factura, Double> iva;
	@FXML private TableColumn<factura, Double> total;
	
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
	
		tableview.setItems(getData());
	
	}
	
	
	public ObservableList<factura> getData(){
		
		ObservableList<factura> facturas = FXCollections.observableArrayList();
		
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 25),"A","BEBIDAS","20-1021",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 29),"C","GALLETAS","20-1331",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 18),"A","SNACKS","10-1221",2,1453,666.33,333.99,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 27),"A","BEBIDAS","20-100",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 26),"C","GALLETAS","20-121",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 19),"A","SNACKS","10-1231",2,1453,666.33,333.99,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 28),"A","BEBIDAS","20-121",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 30),"C","BEBIDAS","20-131",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 31),"A","HELADOS","10-111",2,1453,666.33,333.99,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 21),"A","GALLETAS","20-1021",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 21),"C","GASEOSAS","20-1231",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 21),"A","ALIMENTOS","10-1",2,1453,666.33,333.99,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 25),"A","BEBIDAS","20-1021",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 29),"C","GALLETAS","20-1331",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 18),"A","SNACKS","10-1221",2,1453,666.33,333.99,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 27),"A","BEBIDAS","20-100",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 26),"C","GALLETAS","20-121",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 19),"A","SNACKS","10-1231",2,1453,666.33,333.99,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 28),"A","BEBIDAS","20-121",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 30),"C","BEBIDAS","20-131",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 31),"A","HELADOS","10-111",2,1453,666.33,333.99,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 21),"A","GALLETAS","20-1021",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 21),"C","GASEOSAS","20-1231",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 21),"A","ALIMENTOS","10-1",2,1453,666.33,333.99,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 25),"A","BEBIDAS","20-1021",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 29),"C","GALLETAS","20-1331",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 18),"A","SNACKS","10-1221",2,1453,666.33,333.99,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 27),"A","BEBIDAS","20-100",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 26),"C","GALLETAS","20-121",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 19),"A","SNACKS","10-1231",2,1453,666.33,333.99,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 28),"A","BEBIDAS","20-121",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 30),"C","BEBIDAS","20-131",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 31),"A","HELADOS","10-111",2,1453,666.33,333.99,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 21),"A","GALLETAS","20-1021",1001,2004232,1002.44,102.5,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 21),"C","GASEOSAS","20-1231",22,123553,1666.44,155.5,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 21),"A","ALIMENTOS","10-1",2,1453,666.33,333.99,999.99));
		
		
		return facturas;
	}
	
	
}
