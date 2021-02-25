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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;

public class ControladorBusqueda  implements Initializable{

	// Variables
	
	    ModeloPrincipal modelo;
	    
	    ModeloSucursal modeloSucursal;
	    
	    ArrayList<zona>  zonas;
	    
	    ArrayList<sucursal> sucursales;
	    
	    ObservableList<String> listaCondicion1 = FXCollections.observableArrayList("PROVEEDOR", "TIPO", "PREFIJO", "FECHA");
	
	    ObservableList<String> listaFecha = FXCollections.observableArrayList("ULTIMO DIA", "ULTIMA SEMANA", "ULTIMO MES", "ULTIMO AÑO");
	    
	    ObservableList<String> listaTipo = FXCollections.observableArrayList("A", "B", "C");
	    
	    ObservableList<String> listaProve, listaPrefijo;
		
		// Configurar tabla de un modelo de factura
		
		@FXML private TableView<factura> tableview;
		
		@FXML private TableColumn<factura, LocalDate> fecha;
		
		@FXML private TableColumn<factura, String> tipo, proveedor, cuit;
		
		@FXML private TableColumn<factura, Integer> prefijo, nrofactura;
		
		@FXML private TableColumn<factura, Double> subtotal, iva, iva2, iva3, otros, total;
		
		@FXML private ComboBox<String> ListaSucursales, ListaZona, condicion1, condicion2;
		
		@FXML private DatePicker fechaDesde, fechaHasta;
		
		@FXML private CheckBox impuestoTodos, impuestoIva1, impuestoIva2, impuestoIva3, impuestoIvaOtros;
	
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
		
		tableview.setItems(null);
		
		condicion1.setItems(listaCondicion1);
		
		modelo = new ModeloPrincipal();
		
		modeloSucursal = new ModeloSucursal();
		
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
			
			ArrayList<proveedor> proveedores = modelo.cargarListaProve();
			
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
	
        	break;
			
			
			default:
				System.out.println("Entrada en default de switch combobox condicion 1");
		
		
		
		}
	}
	
	
	public void estadoImpuestos(ActionEvent event) {
		
		if(impuestoTodos.isSelected()) {
			
			impuestoIva1.setSelected(false);
			
			impuestoIva2.setSelected(false);
			
			impuestoIva3.setSelected(false);
			
			impuestoIvaOtros.setSelected(false);
		}
	}
	
	public void busqueda (ActionEvent event) {
		
		
		String zona = (ListaZona.getSelectionModel().getSelectedItem()==null)? "todos" : ListaZona.getSelectionModel().getSelectedItem();
	
		String sucursal = (ListaSucursales.getSelectionModel().getSelectedItem()==null)? "todos" : ListaSucursales.getSelectionModel().getSelectedItem();
		
		String condicional1 = (condicion1.getSelectionModel().getSelectedItem()==null)? "todos" : condicion1.getSelectionModel().getSelectedItem();
		
	    String condicional2 = (condicion2.getSelectionModel().getSelectedItem()==null)? "todos" : condicion2.getSelectionModel().getSelectedItem();
		
	    
		
   ///	System.out.println(zona+" : "+sucursal+ "Condicion 1: "+ condicional1+", Condicion 2: "+condicional2);
	
	
	}
	
	
}