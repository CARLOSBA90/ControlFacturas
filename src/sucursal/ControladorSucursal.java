package sucursal;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import clases.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import modelo.ModeloSucursal;

public class ControladorSucursal implements Initializable {

	// Variables
	private ModeloSucursal modelo;
	@FXML private AnchorPane pane;
	@FXML private Pane central, cabecera;
	@FXML private Label idLabel;
	private int id;
	private String nombre;
	private ExecutorService databaseExecutor;
	//private ObservableList<factura> cargarData;
	

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
	@FXML ProgressIndicator indicador;

	//// Inicializa la vista con las propiedades y atributos de la tabla de un
	//// modelo de factura
	public void initialize(URL location, ResourceBundle resources) {
		fecha.setCellValueFactory(new PropertyValueFactory<factura, LocalDate>("fecha"));
		tipo.setCellValueFactory(new PropertyValueFactory<factura, String>("tipo"));
		proveedor.setCellValueFactory(new PropertyValueFactory<factura, String>("proveedor"));
		cuit.setCellValueFactory(new PropertyValueFactory<factura, String>("cuit"));
		prefijo.setCellValueFactory(new PropertyValueFactory<factura, Integer>("prefijo"));
		nrofactura.setCellValueFactory(new PropertyValueFactory<factura, Integer>("nrofactura"));
		subtotal.setCellValueFactory(new PropertyValueFactory<factura, Double>("subtotal"));
		forma.setCellValueFactory(new PropertyValueFactory<factura, String>("forma"));
		iva.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva"));
		iva2.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva2"));
		iva3.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva3"));
		otros.setCellValueFactory(new PropertyValueFactory<factura, Double>("otros"));
		total.setCellValueFactory(new PropertyValueFactory<factura, Double>("total"));
		indicador.setVisible(false);

		/// Carga de datos despues de generar las vista, para poder cargar el ID
		Platform.runLater(() -> {
			cargarLista(id);
		});
	}

	/// Metodo para mostrar el historial de facturas en el sector derecho
	@FXML
	private void historial(MouseEvent event) {
		blanquear();
		cargarLista(id);
		tableview.setPrefHeight(524);
		central.getChildren().add(tableview);
		central.getChildren().add(indicador);
		Label tituloCabecera = new Label("Ultimas facturas ingresadas");
		tituloCabecera.setFont(new Font("Calibri", 34));
		tituloCabecera.setTextFill(Color.web("#868686"));
		tituloCabecera.setTranslateX(23.0);
		tituloCabecera.setTranslateY(24.0);
		cabecera.setStyle("-fx-background-color: #F8F8FF;");
		cabecera.getChildren().add(tituloCabecera);
	}

	/// Metodo de salida del programa
	@FXML private void salir(MouseEvent event) {
		Stage stage = (Stage) pane.getScene().getWindow();
		stage.close();
	}

	// Metodo para cargar en ventana el archivo FXML ingresar
	@FXML private void ingresar(MouseEvent event) throws IOException {
		cargarUI("ingresar");
	}

	/// Metodo para generar los nodos de la vista "Ingresar nueva factura"
	private void cargarUI(String ui) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(ui + ".fxml"));
		Parent root = (Parent) loader.load();
		ControladorIngresarFactura controlador = loader.getController();
		controlador.setUsuario(id,databaseExecutor,indicador);
		controlador.setClase(this);
		blanquear();
		Label tituloCabecera = new Label("Ingreso de nueva factura");
		tituloCabecera.setFont(new Font("Calibri", 34));
		tituloCabecera.setTextFill(Color.web("#868686"));
		tituloCabecera.setTranslateX(23.0);
		tituloCabecera.setTranslateY(24.0);
		cabecera.setStyle("-fx-background-color: #F8F8FF;");
		cabecera.getChildren().add(tituloCabecera);
		central.getChildren().add(root);
		tableview.setPrefHeight(392);
	}

	/// Metodo para limpiar el sector derecho de la vista para generar una nueva vista
	public void blanquear() {
		cabecera.getChildren().clear();
		central.getChildren().clear();
	}

	// Codigo para identificar de que sucursal se carga el ID
	public void setUsuario(int i, String string, ExecutorService databaseExecutor) {
		this.id = i;
		modelo = new ModeloSucursal();
		nombre = string;
		this.databaseExecutor = databaseExecutor;
		//cargarData=observableList;
	}

	/// Carga la listview desde la BBDD
	public void cargarLista(int id) {
		if (id != 0) {
			idLabel.setText("Sucursal : " + nombre);
			tableview.getItems().clear();
		/*	try {tableview.setItems(modelo.cargarData(id));
			} catch (ClassNotFoundException | IOException | SQLException e) {
				/// GENERAR EXCEPCION CONTROLADA }*/
			
		    final ModeloSucursal modelo = new ModeloSucursal(id);
			indicador.visibleProperty().bind(modelo.runningProperty());
			indicador.progressProperty().bind(modelo.progressProperty());
		    
			modelo.setOnSucceeded(e ->{
				ObservableList<factura> lista = modelo.getValue().getLista();
				tableview.setItems(lista);
			   });
			databaseExecutor.submit(modelo);
		} else {
			tableview.setItems(null);
		}

	}

	/// Refresca la vista despues de insertar una nueva factura, desde el controlador Ingresar Factura
	public void refrescarLista() {
		cargarLista(id);
	}

	public void setUsuario(int i, String string) {
		this.id = i;
		modelo = new ModeloSucursal();
		nombre = string;
	}


}
