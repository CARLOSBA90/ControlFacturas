package principal;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import clases.factura;
import clases.proveedor;
import clases.sucursal;
import clases.zona;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import modelo.ModeloBusquedaPrincipal;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;

public class ControladorBusqueda  implements Initializable{

	// Variables
	
	    ModeloPrincipal modelo;
	    
	    ModeloSucursal modeloSucursal;
	    
	    ModeloBusquedaPrincipal modeloBusqueda;
	    
	    ArrayList<zona>  zonas;
	    
	    ArrayList<sucursal> sucursales;
	    
	    ObservableList<String> listaCondicion1 = FXCollections.observableArrayList("PROVEEDOR", "TIPO", "PREFIJO", "FECHA");
	
	    ObservableList<String> listaFecha = FXCollections.observableArrayList("ULTIMO DIA", "ULTIMA SEMANA", "ULTIMO MES", "ULTIMO AÑO");
	    
	    ObservableList<String> listaTipo = FXCollections.observableArrayList("A", "B", "C");
	    
	    ObservableList<String> listaProve, listaPrefijo;
	    
	    ArrayList<proveedor> proveedores;
	    
	    ObservableList<factura> lista = FXCollections.observableArrayList();
		
		// Configurar tabla de un modelo de factura
		
		@FXML private TableView<factura> tableview;
		
		@FXML private TableColumn<factura, LocalDate> fecha;
		
	/*	@FXML private TableColumn<factura, String> tipo, proveedor, cuit, sucursal;
		
		@FXML private TableColumn<factura, Integer> prefijo, nrofactura;
		
		@FXML private TableColumn<factura, Double> subtotal, iva, iva2, iva3, otros, total;*/
		
		@FXML private ComboBox<String> ListaSucursales, ListaZona, condicion1, condicion2;
		
		@FXML private DatePicker fechaDesde, fechaHasta;
		
		@FXML private ToggleGroup IMPUESTOS;
		
		@FXML private ToggleGroup FORMAP;
	
	public void initialize(URL location, ResourceBundle resources) {
		
		condicion1.setItems(listaCondicion1);
		
		modelo = new ModeloPrincipal();
		
		modeloSucursal = new ModeloSucursal();
		
		modeloBusqueda = new ModeloBusquedaPrincipal();
		
		/// Oyente para colocar la fecha como tope hasta el dia actual
		
				fechaDesde.setDayCellFactory(picker -> new DateCell() {
			        public void updateItem(LocalDate date, boolean empty) {
			        	
			            super.updateItem(date, empty);
			            
			            LocalDate today = LocalDate.now();
			            
			            
			            setDisable(empty || date.compareTo(today) > 0 );
			        }
			    });
				
				
				fechaHasta.setDayCellFactory(picker -> new DateCell() {
			        public void updateItem(LocalDate date, boolean empty) {
			        	
			            super.updateItem(date, empty);
			            
			            LocalDate today = LocalDate.now();
			            
			            
			            setDisable(empty || date.compareTo(today) > 0 );
			        }
			    });
		
		
		try {
			ListaZona.setItems(listarZonas());
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListaSucursales.setItems(null);
		
		
	}
	
	 private ObservableList<String> listarZonas() throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
			/// Listar todas las zonas
			
			zonas = modelo.listaZonas();
			
			ObservableList<String> lista = FXCollections.observableArrayList();
			
					/// Uso for each mejorado, expresión Lambda.
			
			zonas.forEach((n) -> lista.add(n.getNombre()));
			
			return lista;
	}

	public void seleccionZonas(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
			
		// System.out.println(""+ zonas.get(ListaZona.getSelectionModel().getSelectedIndex()).getId());
		
		// Listar las sucursales de la zona seleccionada
		
		sucursales = modelo.listaSucursales(zonas.get(ListaZona.getSelectionModel().getSelectedIndex()).getId());
		
		ObservableList<String> lista = FXCollections.observableArrayList();
		
		sucursales.forEach((n) -> lista.add(n.getNombre()));
		
		ListaSucursales.setItems(lista);
		
		
	}
	
	public void mostrarPor(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {

		switch(condicion1.getSelectionModel().getSelectedItem()) {
		
		case "PROVEEDOR":
			
			proveedores = modelo.cargarListaProve();
			
			ObservableList<String> lista = FXCollections.observableArrayList();
			
			proveedores.forEach((n) -> lista.add(n.getNombre()));
			
			condicion2.setItems(lista);
			
			break;
			
        case "TIPO":
        	
        	condicion2.setItems(listaTipo);
			
			break;
		
        case "PREFIJO":
        	
        	condicion2.setItems(modelo.cargarListaPre());
			
			break;
			
        case "FECHA":
        	
        	condicion2.setItems(listaFecha);
        	
        	fechaDesde.getEditor().setDisable(true);
        	
        	fechaHasta.getEditor().setDisable(true);
	
        	break;
			
			
			default:
				System.out.println("Entrada en default de switch combobox condicion 1");
		
		
		
		}
	}
	
	
	public void busqueda (ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		
		
		int zona = (ListaZona.getSelectionModel().getSelectedItem()==null)? -1 : zonas.get(ListaZona.getSelectionModel().getSelectedIndex()).getId();
	
		int sucursal = (ListaSucursales.getSelectionModel().getSelectedItem()==null)? -1 : sucursales.get(ListaSucursales.getSelectionModel().getSelectedIndex()).getId();
		
		String condicional1 = (condicion1.getSelectionModel().getSelectedItem()==null)? "todos" : condicion1.getSelectionModel().getSelectedItem();
		
		String condicional2;
		
		if(condicional1=="PROVEEDOR") condicional2 = (condicion2.getSelectionModel().getSelectedItem()==null)? "todos" : ""+proveedores.get(condicion2.getSelectionModel().getSelectedIndex()).getId();
		else
		
	    condicional2 = (condicion2.getSelectionModel().getSelectedItem()==null)? "todos" : condicion2.getSelectionModel().getSelectedItem();

	 
	    RadioButton botonSeleccionForma = (RadioButton) FORMAP.getSelectedToggle();
	    
	     String formaPago = botonSeleccionForma.getText();
	    
	     RadioButton botonSeleccionImpuestos = (RadioButton) IMPUESTOS.getSelectedToggle();
	     
	    
	    String impuestos = botonSeleccionImpuestos.getText();
	    
	   //////////////////////////
	    
	    LocalDate fecha1 = fechaDesde.getValue();
	    
	    LocalDate fecha2 = fechaHasta.getValue();
	    
	   
	   lista = modeloBusqueda.obtenerLista(zona, sucursal, condicional1, condicional2, formaPago, impuestos, fecha1, fecha2);
	
	   
	   if(Bindings.isEmpty(tableview.getItems()).get()) {
	        //EMPTY


	    }else{
	        //FILLED
	    	 tableview.getColumns().clear();
	    	 tableview.getItems().clear();

	    }

	  
	   
	   TableColumn<factura,String> Sucursal = new TableColumn<factura,String>("Sucursal");
       TableColumn<factura, LocalDate> Fecha = new TableColumn<factura, LocalDate>("Fecha");
       TableColumn<factura, String> Tipo = new TableColumn<factura, String>("Tipo");
       TableColumn<factura, String> Proveedor = new TableColumn<factura, String>("Proveedor");
       TableColumn<factura, Integer> Prefijo = new TableColumn<factura, Integer>("Prefijo");
       TableColumn<factura, Integer> NroFactura = new TableColumn<factura, Integer>("NroFactura");
       TableColumn<factura, String> FormaPago = new TableColumn<factura, String>("FormaPago");
       TableColumn<factura, Double> Subtotal = new TableColumn<factura, Double>("Subtotal");
       TableColumn<factura, Double> Total = new TableColumn<factura, Double>("Total");
       
   	Sucursal.setCellValueFactory(new PropertyValueFactory<factura, String>("sucursal"));
	Fecha.setCellValueFactory(new PropertyValueFactory<factura, LocalDate>("fecha"));
	Tipo.setCellValueFactory(new PropertyValueFactory<factura, String>("tipo"));
	Proveedor.setCellValueFactory(new PropertyValueFactory<factura, String>("proveedor"));
	Prefijo.setCellValueFactory(new PropertyValueFactory<factura, Integer>("prefijo"));
	NroFactura.setCellValueFactory(new PropertyValueFactory<factura, Integer>("nrofactura"));
	FormaPago.setCellValueFactory(new PropertyValueFactory<factura, String>("forma"));
	Subtotal.setCellValueFactory(new PropertyValueFactory<factura, Double>("subtotal"));
	Total.setCellValueFactory(new PropertyValueFactory<factura, Double>("total"));
	
	//continuacion
	
	   
	 tableview.getColumns().addAll(Sucursal, Fecha, Tipo, Proveedor, Prefijo, NroFactura, FormaPago, Subtotal, Total);
	   
	   tableview.setItems(lista);
	   
	}
	
	
}