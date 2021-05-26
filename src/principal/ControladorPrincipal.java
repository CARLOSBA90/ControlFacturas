package principal;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import clases.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.ListarZonas;
import modelo.ModeloSucursal;

public class ControladorPrincipal implements Initializable {

	// Variables
	@FXML private AnchorPane pane;
	@FXML private Pane central;
	@FXML private Pane centralInferior;
	@FXML private Pane cabecera;
	@FXML private Pane menu;
	@SuppressWarnings("unused")
	private Label labelZona = new Label("ZONA");
	@SuppressWarnings("unused")
	private Label labelSucursal = new Label("SUCURSAL");
	private Label tituloCabecera = new Label(" ");
	private ArrayList<zona> zonas;
	private ArrayList<sucursal> sucursales;

	// Configurar tabla de un modelo de factura

	@FXML private TableView<factura> tableview;
	@FXML private TableColumn<factura, LocalDate> fecha;
	@FXML private TableColumn<factura, String> tipo;
	@FXML private TableColumn<factura, String> proveedor;
	@FXML private TableColumn<factura, String> cuit;
	@FXML private TableColumn<factura, String> forma;
	@FXML private TableColumn<factura, Integer> prefijo;
	@FXML private TableColumn<factura, Integer> nrofactura;
	@FXML private TableColumn<factura, Double> subtotal;
	@FXML private TableColumn<factura, Double> iva;
	@FXML private TableColumn<factura, Double> iva2;
	@FXML private TableColumn<factura, Double> iva3;
	@FXML private TableColumn<factura, Double> otros;
	@FXML private TableColumn<factura, Double> total;
	@FXML private ComboBox<String> ListaSucursales; 
	@FXML private ComboBox<String> ListaZona;
	private Stage stage;
	private acceso access;
	private ExecutorService databaseExecutor;
	@FXML private ProgressIndicator indicador;

	//// Inicializa la vista con las propiedades y atributos de la tabla de un
	//// modelo de factura
	
	public ControladorPrincipal() {
		
	}

	public void initialize(URL location, ResourceBundle resources) {

		fecha.setCellValueFactory(new PropertyValueFactory<factura, LocalDate>("fecha"));
		tipo.setCellValueFactory(new PropertyValueFactory<factura, String>("tipo"));
		proveedor.setCellValueFactory(new PropertyValueFactory<factura, String>("proveedor"));
		cuit.setCellValueFactory(new PropertyValueFactory<factura, String>("cuit"));
		prefijo.setCellValueFactory(new PropertyValueFactory<factura, Integer>("prefijo"));
		nrofactura.setCellValueFactory(new PropertyValueFactory<factura, Integer>("nrofactura"));
		forma.setCellValueFactory(new PropertyValueFactory<factura, String>("forma"));
		subtotal.setCellValueFactory(new PropertyValueFactory<factura, Double>("subtotal"));
		iva.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva"));
		iva2.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva2"));
		iva3.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva3"));
		otros.setCellValueFactory(new PropertyValueFactory<factura, Double>("otros"));
		total.setCellValueFactory(new PropertyValueFactory<factura, Double>("total"));
		tableview.setItems(null);
		indicador.setVisible(false);
		ListaSucursales.setItems(null);
		/// Finalizacion total del sistema
		Platform.runLater(() -> {
			try {
				listarZonas();
			} catch (ClassNotFoundException | SQLException | IOException e) {
				/* controlar excepcion */
			}	
		
		stage.setOnHiding(new EventHandler<WindowEvent>() {

	         @Override
	         public void handle(WindowEvent event) {
	             Platform.runLater(new Runnable() {
	                 @Override
	                 public void run() {
	                     //System.out.println("Application Closed by click to Close Button(X)");
	                     System.exit(0);
	                 }
	             });
	         }
	     });
		});

	}

	public void listarZonas() throws ClassNotFoundException, SQLException, IOException {
		/// Listar todas las zonas
		final ListarZonas modeloZona = new ListarZonas();
		indicador.visibleProperty().bind(modeloZona.runningProperty());
		indicador.progressProperty().bind(modeloZona.progressProperty());
		
		modeloZona.setOnSucceeded(e ->{
			zonas = modeloZona.getValue();
			ObservableList<String> lista = FXCollections.observableArrayList();
			/// Uso for each mejorado, expresión Lambda.
			zonas.forEach(n -> lista.add(n.getNombre()));
			ListaZona.setItems(lista);
		   });
		
		modeloZona.setOnFailed(e->{
		    conexionFallida();
			});
		databaseExecutor.submit(modeloZona);

	}

	public void seleccionZonas() throws ClassNotFoundException, IOException, SQLException {
		final modelo.seleccionZonas modelo = new modelo.seleccionZonas(zonas.get(ListaZona.getSelectionModel().getSelectedIndex()).getId());
		indicador.visibleProperty().bind(modelo.runningProperty());
		indicador.progressProperty().bind(modelo.progressProperty());
		modelo.setOnSucceeded(e ->{
			sucursales = modelo.getValue();
			ObservableList<String> lista = FXCollections.observableArrayList();
			sucursales.forEach(n -> lista.add(n.getNombre()));
			ListaSucursales.setItems(lista);
			//ListaSucursales.getSelectionModel().selectFirst();	
		   });
		modelo.setOnFailed(e->{
		    conexionFallida();
			});
		databaseExecutor.submit(modelo);
	}

	public void seleccionSucursal() throws ClassNotFoundException, IOException, SQLException {
		if(ListaSucursales.getSelectionModel().getSelectedIndex()>=0) {
		 final ModeloSucursal modelo = new ModeloSucursal(sucursales.get(ListaSucursales.getSelectionModel().getSelectedIndex()).getId());
			indicador.visibleProperty().bind(modelo.runningProperty());
			indicador.progressProperty().bind(modelo.progressProperty());
			modelo.setOnSucceeded(e ->{
				ObservableList<factura> lista = modelo.getValue().getLista();
				tableview.setItems(lista);
			   });
			databaseExecutor.submit(modelo);
			
			modelo.setOnFailed(e->{
			    conexionFallida();
				});
			databaseExecutor.submit(modelo);
		}}
	
	public void conexionFallida() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setTitle("Error");
		alert.setContentText("Conexion a base de datos: fallido!");
		alert.showAndWait();
	}

	/// Metodo de salida del programa

	@FXML
	private void salir(MouseEvent event) {
		Stage stage = (Stage) pane.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void historial(MouseEvent event) throws IOException {
		cargarUI("historial");
	}

	@FXML
	private void busqueda(MouseEvent event) throws IOException {
		cargarUI("busqueda");
	}

	@FXML
	private void resumen(MouseEvent event) throws IOException {
		cargarUI("resumen");
	}

	@FXML
	private void exportar(MouseEvent event) throws IOException {
		cargarUI("exportar");
	}

	@FXML
	private void proveedores(MouseEvent event) throws IOException {
		cargarUI("proveedores");
	}

	@FXML
	private void zonas(MouseEvent event) throws IOException{
		cargarUI("zonas");
	}

	/// Metodo para cargar diferentes vistas, mediante la estructura de control
	/// SWITCH
	// Se compara con el string en el argumento para generar la determinada vista

	private void cargarUI(String ui) throws IOException {

		blanquear();

		FXMLLoader loader = new FXMLLoader(getClass().getResource(ui + ".fxml"));
		Parent root = (Parent)loader.load();

		//// Seleccion de vista segun argumento

		switch (ui) {

		case "historial":
			
			blanquear();
			cabecera.setPrefHeight(70);
			tituloCabecera = new Label("Historial de facturas");
			tituloCabecera.setFont(new Font("Calibri", 34));
			tituloCabecera.setTextFill(Color.web("#868686"));
			tituloCabecera.setTranslateX(23.0);
			tituloCabecera.setTranslateY(24.0);
			cabecera.setStyle("-fx-background-color: #F8F8FF;");
			cabecera.getChildren().add(tituloCabecera);
			ControladorHistorial controladorHistorial = loader.getController();
			controladorHistorial.setDatabaseExecutor(databaseExecutor);
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
			ControladorBusqueda ControladorBusqueda = loader.getController();
			ControladorBusqueda.setDatabaseExecutor(databaseExecutor);
			central.getChildren().add(root);
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
			ControladorZonas controlador = loader.getController();
			controlador.setAcc(access, databaseExecutor);
			central.getChildren().add(root);

			break;

		default:

		}

	}

	/// Meotdo para limpiar el sector derecho de la vista para generar una nueva
	/// vista

	public void blanquear() {
		menu.getChildren().clear();
		cabecera.getChildren().clear();
		central.getChildren().clear();
	}

	public void setStage(Stage primaryStage) {
		stage=primaryStage;
	}

	public void setAcc(acceso acc, ExecutorService databaseExecutor) {
	 this.access=acc;	
	 this.databaseExecutor=databaseExecutor;
	}

}
