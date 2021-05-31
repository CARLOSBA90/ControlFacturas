package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import clases.factura;
import clases.sucursal;
import clases.zona;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.ListarZonas;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;

public class ControladorHistorial implements Initializable {

	// Variables
	ModeloPrincipal modelo;
	ModeloSucursal modeloSucursal;
	ArrayList<zona> zonas;
	ArrayList<sucursal> sucursales;
	private ExecutorService databaseExecutor;
	@FXML ProgressIndicator indicador;

	// Configurar tabla de un modelo de factura

	@FXML private TableView<factura> tableview;
	@FXML private TableColumn<factura, LocalDate> fecha;
	@FXML private TableColumn<factura, String> tipo;
	@FXML private TableColumn<factura, String> proveedor;
	@FXML private TableColumn<factura, String> cuit;
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

	public void initialize(URL location, ResourceBundle resources) {
		indicador.setVisible(false);
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
		Platform.runLater(() -> {
			try {
				listarZonas();
			} catch (ClassNotFoundException | SQLException | IOException e) {
				/* controlar excepcion */
			}		
			});

		ListaSucursales.setItems(null);

	}

	private void listarZonas() throws ClassNotFoundException, SQLException, IOException {
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

	private void conexionFallida() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setTitle("Error");
		alert.setContentText("Conexion a base de datos: fallido!");
		alert.showAndWait();
	}

	public void seleccionZonas(ActionEvent event) throws ClassNotFoundException, IOException, SQLException{
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

	public void seleccionSucursal(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
		///////////////////////////////////
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

	public void setDatabaseExecutor(ExecutorService databaseExecutor) {
		this.databaseExecutor = databaseExecutor;
	}

}
