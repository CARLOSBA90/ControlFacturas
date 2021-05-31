package sucursal;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import modelo.ModeloSucursal;
import modelo.ModeloSucursalFactura;
import modelo.ModeloSucursalIngreso;
import clases.factura;
import clases.proveedor;

public class ControladorIngresarFactura implements Initializable {
	@FXML private DatePicker fecha;
	@FXML private ComboBox<String> listaDeTipos;
	@FXML private ComboBox<String> listaProveedor;
	@FXML private ComboBox<String> listaCuit;
	@FXML private ComboBox<String> listaPago;
	@FXML private TextField subtotal;
	@FXML private TextField iva1;
	@FXML private TextField iva2;
	@FXML private TextField iva3;
	@FXML private TextField ivaotros;
	@FXML private TextField total;
	@FXML private TextField nrofactura;
	@FXML private TextField prefijo;
	@FXML ProgressIndicator indicador;
	private ObservableList<String> tipo = FXCollections.observableArrayList("A", "B", "C");
	private ObservableList<String> pago = FXCollections.observableArrayList("corriente", "contado");
	private ObservableList<String> proveedor;
	private ObservableList<String> cuit;
	private ArrayList<String> listaNombreArray;
	private ArrayList<String> listaCuitArray;
	private ControladorSucursal superior;
	private ModeloSucursal modelo;
	private int id;
	private ExecutorService databaseExecutor;
	private ArrayList<proveedor> listaProveedores;
	

	// Configurar tabla de un modelo de factura

	@FXML private TableView<factura> tableview;
	@FXML private TableColumn<factura, LocalDate> fechaT;
	@FXML private TableColumn<factura, String> tipoT;
	@FXML private TableColumn<factura, String> proveedorT;
	@FXML private TableColumn<factura, String> cuitT;
	@FXML private TableColumn<factura, String> formaT;
	@FXML private TableColumn<factura, Integer> prefijoT;
	@FXML private TableColumn<factura, Integer> nrofacturaT;
	@FXML private TableColumn<factura, Double> subtotalT;
	@FXML private TableColumn<factura, Double> ivaT;
	@FXML private TableColumn<factura, Double> iva2T;
	@FXML private TableColumn<factura, Double> iva3T;
	@FXML private TableColumn<factura, Double> otrosT;
	@FXML private TableColumn<factura, Double> totalT;
	
	public void initialize(URL location, ResourceBundle resources) {

		fechaT.setCellValueFactory(new PropertyValueFactory<factura, LocalDate>("fecha"));
		tipoT.setCellValueFactory(new PropertyValueFactory<factura, String>("tipo"));
		proveedorT.setCellValueFactory(new PropertyValueFactory<factura, String>("proveedor"));
		cuitT.setCellValueFactory(new PropertyValueFactory<factura, String>("cuit"));
		prefijoT.setCellValueFactory(new PropertyValueFactory<factura, Integer>("prefijo"));
		nrofacturaT.setCellValueFactory(new PropertyValueFactory<factura, Integer>("nrofactura"));
		subtotalT.setCellValueFactory(new PropertyValueFactory<factura, Double>("subtotal"));
		formaT.setCellValueFactory(new PropertyValueFactory<factura, String>("forma"));
		ivaT.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva"));
		iva2T.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva2"));
		iva3T.setCellValueFactory(new PropertyValueFactory<factura, Double>("iva3"));
		otrosT.setCellValueFactory(new PropertyValueFactory<factura, Double>("otros"));
		totalT.setCellValueFactory(new PropertyValueFactory<factura, Double>("total"));

		/// Carga de datos despues de generar las vista, para poder cargar el ID
		Platform.runLater(() -> {
			cargarLista(id);
		});
		/// Inserta los datos en cada lista de la GUI
		listaDeTipos.setItems(tipo);
		listaPago.setItems(pago);

		/// Desactiva el textField total
		total.setDisable(true);

		/// Oyente para colocar la fecha como tope hasta el dia actual

		fecha.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				setDisable(empty || date.compareTo(today) > 0);
			}
		});

		/// Oyente solo para el prefijo y factura, solo se pueden incluir numeros en
		/// estos campos

		ChangeListener<String> soloNumerosFactura = (observable, valorViejo, valorNuevo) -> {
			if (!valorNuevo.matches("\\d*"))
				((StringProperty) observable).set(valorViejo);
			
			/// Maximo digitos permitidos por cada textfield
			if (prefijo.getText().length() > 5) {
				String s = prefijo.getText().substring(0, 5);
				prefijo.setText(s);
			}

			if (nrofactura.getText().length() > 12) {
				String s = nrofactura.getText().substring(0, 12);
				nrofactura.setText(s);
			}

		};

		/// Oyente para colocar solo numeros dentro de los montos subtotal, impuestos y
		/// total
		/// Tambien incluye la suma de subtotal y todos los impuestos
		ChangeListener<String> soloNumeros = (observable, valorViejo, valorNuevo) -> {
			if (!valorNuevo.matches("\\d*(\\.\\d*)?"))
				((StringProperty) observable).set(valorViejo);

			/// Maximo digitos permitidos por cada textfield
			if (subtotal.getText().length() > 10) {
				String s = subtotal.getText().substring(0, 10);
				subtotal.setText(s);
			}

			if (iva1.getText().length() > 10) {
				String s = iva1.getText().substring(0, 10);
				iva1.setText(s);
			}

			if (iva2.getText().length() > 10) {
				String s = iva2.getText().substring(0, 10);
				iva2.setText(s);
			}

			if (iva3.getText().length() > 10) {
				String s = iva3.getText().substring(0, 10);
				iva3.setText(s);
			}

			if (ivaotros.getText().length() > 10) {
				String s = ivaotros.getText().substring(0, 10);
				ivaotros.setText(s);
			}

			/// Suma los valores cada vez que ingresan un digito
			double valorSubtotal = (!subtotal.getText().trim().isEmpty()) ? Double.parseDouble(subtotal.getText()) : 0;
			double valorIva1 = (!iva1.getText().trim().isEmpty()) ? Double.parseDouble(iva1.getText()) : 0;
			double valorIva2 = (!iva2.getText().trim().isEmpty()) ? Double.parseDouble(iva2.getText()) : 0;
			double valorIva3 = (!iva3.getText().trim().isEmpty()) ? Double.parseDouble(iva3.getText()) : 0;
			double valorOtros = (!ivaotros.getText().trim().isEmpty()) ? Double.parseDouble(ivaotros.getText()) : 0;
			double valor = valorSubtotal + valorIva1 + valorIva2 + valorIva3 + valorOtros;

			/// Formato de salida a textfield texto, maximo 2 decimales
			DecimalFormat df = new DecimalFormat("####0.00");
			total.setText("" + df.format(valor));
		};

		// Agrega los listener a los textfields
		prefijo.textProperty().addListener(soloNumerosFactura);
		nrofactura.textProperty().addListener(soloNumerosFactura);
		subtotal.textProperty().addListener(soloNumeros);
		iva1.textProperty().addListener(soloNumeros);
		iva2.textProperty().addListener(soloNumeros);
		iva3.textProperty().addListener(soloNumeros);
		ivaotros.textProperty().addListener(soloNumeros);
	}

	private void generarListas(ArrayList<clases.proveedor> listaProveedores) {
		listaNombreArray = new ArrayList<String>();
		listaCuitArray = new ArrayList<String>();
		
		for (proveedor temp : listaProveedores) {
			listaNombreArray.add(temp.getNombre());
			listaCuitArray.add(temp.getCuit());
		}

		proveedor = FXCollections.observableArrayList(listaNombreArray);
		cuit = FXCollections.observableArrayList(listaCuitArray);

	}

	@FXML
	private void ingresarFactura(MouseEvent event) throws SQLException {

		/// Validacion de datos entradas, verifica los campos minimos para realizar una
		/// entrada a la BBDD
		if ((fecha.getValue() != null) && !listaDeTipos.getSelectionModel().isEmpty()
				&& !listaProveedor.getSelectionModel().isEmpty() && !prefijo.getText().trim().isEmpty()
				&& !nrofactura.getText().trim().isEmpty() && !subtotal.getText().trim().isEmpty()

		) {

			/// Confirmacion de datos de entrada
			ButtonType Si = new ButtonType("Si", ButtonData.OK_DONE);
			ButtonType Cancelar = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
			Alert alert = new Alert(AlertType.INFORMATION, "¿Deseas ingresar la factura actual?", Si, Cancelar);
			alert.setTitle("Ingreso de nuevo factura");
			alert.setHeaderText(null);
			Optional<ButtonType> resultado = alert.showAndWait();
			
			if (resultado.orElse(Cancelar) == Si) {
				facturaBBDD();
			}
		} else {

			/// Alerta de datos faltantes para ingresar una nueva factura
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Faltan datos por completar");
			alert.showAndWait();
		}

	}

	@SuppressWarnings("unchecked")
	private void facturaBBDD() throws SQLException {

		LocalDate datoFecha = fecha.getValue();
		String datoTipo = listaDeTipos.getValue();
		int datoPrefijo = Integer.parseInt(prefijo.getText());
		int datoNroFactura = Integer.parseInt(nrofactura.getText());
		String datoProveedor = listaProveedor.getValue();
		String datoCuit = listaCuit.getValue();
		String datoPago = listaPago.getValue();
		double datoSubtotal = Double.parseDouble(subtotal.getText());
		double datoIva1 = (!iva1.getText().trim().isEmpty()) ? Double.parseDouble(iva1.getText()) : 0;
		double datoIva2 = (!iva2.getText().trim().isEmpty()) ? Double.parseDouble(iva2.getText()) : 0;
		double datoIva3 = (!iva3.getText().trim().isEmpty()) ? Double.parseDouble(iva3.getText()) : 0;
		double datoIvaOtros = (!ivaotros.getText().trim().isEmpty()) ? Double.parseDouble(ivaotros.getText()) : 0;
		double datoTotal = (!total.getText().trim().isEmpty())
				? Double.parseDouble(total.getText().replaceAll(",", "."))
				: 0;

		factura factura = new factura(datoFecha, datoTipo, datoProveedor, datoCuit, datoPrefijo, datoNroFactura,
				datoPago, datoSubtotal, datoIva1, datoIva2, datoIva3, datoIvaOtros, datoTotal);
		
		////
		final ModeloSucursalFactura modelo = new ModeloSucursalFactura(factura, id);
		indicador.visibleProperty().bind(modelo.runningProperty());
		indicador.progressProperty().bind(modelo.progressProperty());
		modelo.setOnSucceeded(e ->{
			boolean	insercion=(boolean) modelo.getValue();
			if (insercion) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setTitle("Factura insertada");
				alert.setContentText("Los datos han sido guardados correctamente");
				ButtonType boton = new ButtonType("Ok");
				ButtonType cancelar = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(boton, cancelar);
				alert.getDialogPane().lookupButton(cancelar).setVisible(false);
				alert.showAndWait();
				cargarLista(id);
				blanquear();

			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error");
				alert.setContentText("Intento de guardar datos: fallido!");
				alert.showAndWait();
			}
		   }
				);
		
		modelo.setOnFailed(e ->{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Conexion a base de datos: fallido!");
			alert.showAndWait();
			
		});
   
		databaseExecutor.submit(modelo);
	}

	@FXML
	private void seleccionPro(ActionEvent e) {
		String texto = listaProveedor.getValue();
		int c = 0;

		for (int i = 0; i < listaNombreArray.size(); i++) {

			if (texto == listaNombreArray.get(i)) {
				c = i;
				break;
			}
		}

		listaCuit.getSelectionModel().select(c);

	}

	@FXML
	private void seleccionCuit(ActionEvent e) {
		String texto = listaCuit.getValue();
		int c = 0;

		for (int i = 0; i < listaCuitArray.size(); i++) {

			if (texto == listaCuitArray.get(i)) {
				c = i;
				break;
			}

		}

		listaProveedor.getSelectionModel().select(c);

	}

	public void setUsuario(int id, ExecutorService databaseExecutor, ProgressIndicator indicador) {
		this.id = id;
		modelo = new ModeloSucursal();
		this.databaseExecutor = databaseExecutor;
		//this.indicador = indicador;
	}

	public void blanquear() {
		fecha.setValue(null);
		listaDeTipos.setValue(null);
		listaProveedor.setValue(null);
		listaCuit.setValue(null);
		prefijo.clear();
		nrofactura.clear();
		subtotal.clear();
		listaPago.setValue(null);
		iva1.clear();
		iva2.clear();
		iva3.clear();
		ivaotros.clear();
		total.clear();
	}

	public void cargarLista(int id) {
		if (id != 0) {
			tableview.getItems().clear();
		/*	try {tableview.setItems(modelo.cargarData(id));
			} catch (ClassNotFoundException | IOException | SQLException e) {
				/// GENERAR EXCEPCION CONTROLADA }*/
			
		    final ModeloSucursalIngreso modelo = new ModeloSucursalIngreso(id);
		    indicador.visibleProperty().bind(
		    		modelo.runningProperty()
		    		);
		    indicador.progressProperty().bind(
		            modelo.progressProperty()
		    );
		    
			modelo.setOnSucceeded(e ->{
				ObservableList<factura> lista = modelo.getValue().getLista();
				tableview.setItems(lista);
				listaProveedores = modelo.getValue().getProveedores();
				// Genera lista en ArrayList tipo String
				generarListas(listaProveedores);
				listaProveedor.setItems(proveedor);
				/// Inserta los datos en cada lista de la GUI
				listaCuit.setItems(cuit);
			   }
					);
			databaseExecutor.submit(modelo);
		} else {
			tableview.setItems(null);
		}
	}

	public void setClase(ControladorSucursal controladorSucursal) {
		superior = controladorSucursal;
	}

}
