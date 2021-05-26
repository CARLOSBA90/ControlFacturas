package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import clases.factura;
import clases.proveedor;
import clases.sucursal;
import clases.zona;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.ListarPrefijo;
import modelo.ListarZonas;
import modelo.ModeloBusquedaPrincipal;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;

public class ControladorBusqueda implements Initializable {
	
	// Variables
	private ModeloPrincipal modelo;
	private ModeloSucursal modeloSucursal;
	private ModeloBusquedaPrincipal modeloBusqueda;
	private ArrayList<zona> zonas;
	private ArrayList<sucursal> sucursales;
	private ObservableList<String> listaCondicion1 = FXCollections.observableArrayList("PROVEEDOR", "TIPO", "PREFIJO", "FECHA");
	private ObservableList<String> listaFecha = FXCollections.observableArrayList("ULTIMO DIA", "ULTIMA SEMANA", "ULTIMO MES",
			                                                              "ULTIMO AÑO");
	private ObservableList<String> listaTipo = FXCollections.observableArrayList("A", "B", "C");
	private ObservableList<String> listaProve;
	private ObservableList<String> listaPrefijo;
	private ArrayList<proveedor> proveedores;
	private ObservableList<factura> lista = FXCollections.observableArrayList();
	private boolean fechaBloqueada = false;
	private ExecutorService databaseExecutor;

	// Configurar tabla de un modelo de factura
	@FXML private TableView<factura> tableview;
	@FXML private TableColumn<factura, LocalDate> fecha;
	@FXML private ComboBox<String> ListaSucursales;
	@FXML private ComboBox<String> ListaZona;
	@FXML private ComboBox<String> condicion1;
	@FXML private ComboBox<String> condicion2;
	@FXML private DatePicker fechaDesde;
	@FXML private DatePicker fechaHasta;
	@FXML private ToggleGroup IMPUESTOS;
	@FXML private ToggleGroup FORMAP;
	@FXML private Label labelNroFactura;
	@FXML private Label labelSubtotalAcumu;
	@FXML private Label labelTotalAcumu;
	@FXML private ProgressIndicator indicador;

	public void initialize(URL location, ResourceBundle resources) {
		condicion1.setItems(listaCondicion1);
		modelo = new ModeloPrincipal();
		modeloSucursal = new ModeloSucursal();
		modeloBusqueda = new ModeloBusquedaPrincipal();
		indicador.setVisible(false);

		/// Oyente para colocar la fecha como tope hasta el dia actual

		fechaDesde.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				setDisable(empty || date.compareTo(today) > 0);
			}
		});

		fechaHasta.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				setDisable(empty || date.compareTo(today) > 0);
			}
		});

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

	public void mostrarPor() throws ClassNotFoundException, IOException, SQLException {

		switch (condicion1.getSelectionModel().getSelectedItem()) {

		case "PROVEEDOR":
				/////
			proveedores = modelo.cargarListaProve();
			ObservableList<String> lista = FXCollections.observableArrayList();
			proveedores.forEach(n -> lista.add(n.getNombre()));
			condicion2.setItems(lista);
			if(fechaBloqueada) DesbloquearFecha();
			break;

		case "TIPO":
			condicion2.setItems(listaTipo);
			if(fechaBloqueada) DesbloquearFecha();
			break;

		case "PREFIJO":
		            /////
		   	final ListarPrefijo modelo = new ListarPrefijo();
			indicador.visibleProperty().bind(modelo.runningProperty());
			indicador.progressProperty().bind(modelo.progressProperty());
			modelo.setOnSucceeded(e ->{
				condicion2.setItems(modelo.getValue());
			   });
			modelo.setOnFailed(e->{
			    conexionFallida();
				});
			databaseExecutor.submit(modelo);
            if(fechaBloqueada) DesbloquearFecha();
			break;

		case "FECHA":
			condicion2.setItems(listaFecha);
			fechaDesde.getEditor().setDisable(true);
			fechaHasta.getEditor().setDisable(true);
            fechaBloqueada=true;
			break;

		default:

		}
	}

	private void DesbloquearFecha() {
		fechaDesde.getEditor().setDisable(false);
		fechaHasta.getEditor().setDisable(false);
		fechaBloqueada=false;
	}

	@SuppressWarnings("unchecked")
	public void busqueda() throws ClassNotFoundException, IOException, SQLException {

		int zona = (ListaZona.getSelectionModel().getSelectedItem() == null) ? -1
				: zonas.get(ListaZona.getSelectionModel().getSelectedIndex()).getId();

		int sucursal = (ListaSucursales.getSelectionModel().getSelectedItem() == null) ? -1
				: sucursales.get(ListaSucursales.getSelectionModel().getSelectedIndex()).getId();

		String condicional1 = (condicion1.getSelectionModel().getSelectedItem() == null) ? "todos"
				: condicion1.getSelectionModel().getSelectedItem();

		String condicional2;

		if (condicional1 == "PROVEEDOR")
			condicional2 = (condicion2.getSelectionModel().getSelectedItem() == null) ? "todos"
					: "" + proveedores.get(condicion2.getSelectionModel().getSelectedIndex()).getId();
		else

			condicional2 = (condicion2.getSelectionModel().getSelectedItem() == null) ? "todos"
					: condicion2.getSelectionModel().getSelectedItem();

		RadioButton botonSeleccionForma = (RadioButton) FORMAP.getSelectedToggle();
		String formaPago = botonSeleccionForma.getText();
		RadioButton botonSeleccionImpuestos = (RadioButton) IMPUESTOS.getSelectedToggle();
		String impuestos = botonSeleccionImpuestos.getText();
		LocalDate fecha1 = fechaDesde.getValue();
		LocalDate fecha2 = fechaHasta.getValue();
		////////
		final ModeloBusquedaPrincipal modelo = new ModeloBusquedaPrincipal(zona, sucursal, condicional1,
				condicional2, formaPago, impuestos, fecha1, fecha2);
		indicador.visibleProperty().bind(modelo.runningProperty());
		indicador.progressProperty().bind(modelo.progressProperty());
		modelo.setOnSucceeded(e ->{
			lista = modelo.getValue();
			if (Bindings.isEmpty(tableview.getItems()).get()) {
				// EMPTY
			} else {
				// FILLED
				tableview.getColumns().clear();
				tableview.getItems().clear();

			}
			
			TableColumn<factura, String> Sucursal = new TableColumn<factura, String>("Sucursal");
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

			if (lista.get(0).isImpuestos()) {
				TableColumn<factura, Double> Iva = new TableColumn<factura, Double>("Iva");
				TableColumn<factura, Double> Iva2 = new TableColumn<factura, Double>("Iva 2");
				TableColumn<factura, Double> Iva3 = new TableColumn<factura, Double>("Iva 3");
				TableColumn<factura, Double> Otros = new TableColumn<factura, Double>("Otros");
				Iva.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva"));
				Iva2.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva2"));
				Iva3.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva3"));
				Otros.setCellValueFactory(new PropertyValueFactory<factura, Double>("otros"));
				tableview.getColumns().addAll(Sucursal, Fecha, Tipo, Proveedor, Prefijo, NroFactura, FormaPago, Subtotal,
						Iva, Iva2, Iva3, Otros, Total);
			} else {
				tableview.getColumns().addAll(Sucursal, Fecha, Tipo, Proveedor, Prefijo, NroFactura, FormaPago, Subtotal,
						Total);
			}
			tableview.setItems(lista);
			
			double tempSub=0;
			double tempTotal=0;
			for(factura n: lista) {
			   tempSub+=n.getSubtotal();
			   tempTotal+=n.getTotal();
			}
			labelNroFactura.setText("Numero de facturas: "+lista.size());
			labelSubtotalAcumu.setText("Subtotal acumulado: "+String.format("%.00f", tempSub));
			labelTotalAcumu.setText("Total acumulado: "+String.format("%.00f", tempTotal));	
		   });
		modelo.setOnFailed(e->{
		    conexionFallida();
			});
		databaseExecutor.submit(modelo);
	

	}
	

	public void setDatabaseExecutor(ExecutorService databaseExecutor) {
		this.databaseExecutor=databaseExecutor;
	}
	
	public void conexionFallida() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setTitle("Error");
		alert.setContentText("Conexion a base de datos: fallido!");
		alert.showAndWait();
	}

}
