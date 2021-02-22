package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import clases.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;


public class ControladorPrincipal implements Initializable {
	
	// Variables
	
	@FXML private AnchorPane pane;
	
	@FXML private Pane central,centralInferior,cabecera, menu;
	
    Label labelZona = new Label("ZONA");
	
	Label labelSucursal = new Label("SUCURSAL");

    Label tituloCabecera = new Label(" "); 
    
    ModeloPrincipal modelo;
    
    ModeloSucursal modeloSucursal;
    
    ArrayList<zona>  zonas;
    
    ArrayList<sucursal> sucursales;
		
	
	
	// Configurar tabla de un modelo de factura
	
	@FXML private TableView<factura> tableview;
	@FXML private TableColumn<factura, LocalDate> fecha;
	@FXML private TableColumn<factura, String> tipo, proveedor, cuit;
	@FXML private TableColumn<factura, Integer> prefijo, nrofactura;
	@FXML private TableColumn<factura, Double> subtotal, iva, iva2, iva3, otros, total;
	@FXML private ComboBox<String> ListaSucursales, ListaZona;
	
	
	
	 //// Inicializa la vista con las propiedades y atributos de la tabla de un modelo de factura
	
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
		
		modelo = new ModeloPrincipal();
		
		modeloSucursal = new ModeloSucursal();
		
		try {
			ListaZona.setItems(listarZonas());
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListaSucursales.setItems(null);
		
		
		
	
	}
	

	public ObservableList<String> listarZonas() throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		
		/// Listar todas las zonas
		
		zonas = modelo.listaZonas();
		
		ObservableList<String> lista = FXCollections.observableArrayList();
		
				/// Uso for each mejorado, expresi�n Lambda.
		
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
	
	public void seleccionSucursal(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		
		if(ListaSucursales.getSelectionModel().getSelectedIndex()>=0)
		
		tableview.setItems(modeloSucursal.cargarData(sucursales.get(ListaSucursales.getSelectionModel().getSelectedIndex()).getId()));
		
		
	}
	
	
	
	/// Metodo de salida del programa	
	
	@FXML 
	private void salir(MouseEvent event) {
		
		Stage stage = (Stage) pane.getScene().getWindow();
		stage.close();
		
	}
	
	@FXML 
	private void historial(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
	
		cargarUI("historial");
	}
	
	
	@FXML 
	private void busqueda(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
	
		cargarUI("busqueda");
	}
	
	@FXML 
	private void resumen(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
	
		cargarUI("resumen");
	}
	
	@FXML 
	private void exportar(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
	
		cargarUI("exportar");
	}
	@FXML 
	private void proveedores(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
	
		cargarUI("proveedores");
	}
	
	@FXML 
	private void zonas(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
	
		cargarUI("zonas");
	}
	
	
	
	
	
	/// Metodo para cargar diferentes vistas, mediante la estructura de control SWITCH
	// Se compara con el string en el argumento para generar la determinada vista
	
    private void cargarUI(String ui) throws IOException, ClassNotFoundException, SQLException {
		
    	 blanquear();
    	
	  	Parent root = null;
	
	     root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
		 
	     
	       //// Seleccion de vista segun argumento
	     
	     switch(ui) {
	     
	     case "historial":
	    	 
	    	 blanquear();
	 		
			 cabecera.setPrefHeight(70);
			 
			 tituloCabecera = new Label ("Historial de facturas");

			 tituloCabecera.setFont(new Font("Calibri", 34));

			 tituloCabecera.setTextFill(Color.web("#868686"));

			 tituloCabecera.setTranslateX(23.0);

			 tituloCabecera.setTranslateY(24.0);	

			 cabecera.setStyle("-fx-background-color: #F8F8FF;");
			 
			 cabecera.getChildren().add(tituloCabecera);
			 
			 central.getChildren().add(root);
	    	 
	    	 
	    	 
	    	 break;
	    	 
	    	 
	     
	     case "busqueda":
	    	 
	    	 cabecera.setPrefHeight(75);

	    	 tituloCabecera.setText("Busqueda Avanzada");

	    	 tituloCabecera.setFont(new Font("Calibri", 34));

	    	 tituloCabecera.setTextFill(Color.web("#868686"));

	    	 tituloCabecera.setTranslateX(23.0);

	    	 tituloCabecera.setTranslateY(24.0);

	    	 cabecera.getChildren().add(tituloCabecera);

	    	 cabecera.setStyle("-fx-background-color: #F8F8FF;");

	    	 central.setPrefHeight(120);

	    	 central.getChildren().add(root);

	    	/* tableview.setPrefHeight(405);

	    	 tableview.setItems(null);

	    	 centralInferior.getChildren().add(tableview);*/
	    	 

	    	 break;
	    	 
	     case "resumen":
	    	 
	    	 cabecera.setPrefHeight(75);

	    	 tituloCabecera.setText("Resumen de Cuentas");

	    	 tituloCabecera.setFont(new Font("Calibri", 34));

	    	 tituloCabecera.setTextFill(Color.web("#868686"));

	    	 tituloCabecera.setTranslateX(23.0);

	    	 tituloCabecera.setTranslateY(24.0);	

	    	 cabecera.getChildren().add(tituloCabecera);

	    	 cabecera.setStyle("-fx-background-color: #F8F8FF;");

	    	 central.setPrefHeight(90);

	    	 central.getChildren().add(root);

	  //  	 tableview.setPrefHeight(440);

	    //	 tableview.setItems(null);

	    //	 centralInferior.getChildren().add(tableview);
	    	 
	    	 
	    	 
	    	 break;
	    	 
	     case "exportar":
	    	 
	    	 cabecera.setPrefHeight(75);

	    	 tituloCabecera.setText("Exportar Facturas");

	    	 tituloCabecera.setFont(new Font("Calibri", 34));

	    	 tituloCabecera.setTextFill(Color.web("#868686"));

	    	 tituloCabecera.setTranslateX(23.0);

	    	 tituloCabecera.setTranslateY(24.0);	

	    	 cabecera.getChildren().add(tituloCabecera);

	    	 cabecera.setStyle("-fx-background-color: #F8F8FF;");

	    	 central.setPrefHeight(600);

	    	 central.getChildren().add(root);

	    
	    	 
	    	 break;
	    	 
         case "proveedores":
	    	 
	    	 cabecera.setPrefHeight(75);

	    	 tituloCabecera.setText("Proveedores");

	    	 tituloCabecera.setFont(new Font("Calibri", 34));

	    	 tituloCabecera.setTextFill(Color.web("#868686"));

	    	 tituloCabecera.setTranslateX(23.0);

	    	 tituloCabecera.setTranslateY(24.0);	

	    	 cabecera.getChildren().add(tituloCabecera);

	    	 cabecera.setStyle("-fx-background-color: #F8F8FF;");

	    	 central.setPrefHeight(600);

	    	 central.getChildren().add(root);

	    
	    	 
	    	 break;
	    	 
           case "zonas":
	    	 
	    	 cabecera.setPrefHeight(75);

	    	 tituloCabecera.setText("Zonas");

	    	 tituloCabecera.setFont(new Font("Calibri", 34));

	    	 tituloCabecera.setTextFill(Color.web("#868686"));

	    	 tituloCabecera.setTranslateX(23.0);

	    	 tituloCabecera.setTranslateY(24.0);	

	    	 cabecera.getChildren().add(tituloCabecera);

	    	 cabecera.setStyle("-fx-background-color: #F8F8FF;");

	    	 central.setPrefHeight(600);

	    	 central.getChildren().add(root);

	    
	    	 
	    	 break;
		
		default:
			
	     }
		
		
	}
    
  /// Meotdo para limpiar el sector derecho de la vista para generar una nueva vista
    
    public void blanquear() {
    	
		menu.getChildren().clear();
	
		cabecera.getChildren().clear();
		
		central.getChildren().clear();
		
		//centralInferior.getChildren().clear();
    	
    }
    
    public void evento(MouseEvent event) {
    	
    	System.out.println("TEST");
    }
    
   
    

	
}
