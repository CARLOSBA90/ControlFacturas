package principal;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import clases.factura;
import clases.sucursal;
import clases.zona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;

public class ControladorHistorial implements Initializable {

	// Variables

	ModeloPrincipal modelo;

	ModeloSucursal modeloSucursal;

	ArrayList<zona> zonas;

	ArrayList<sucursal> sucursales;

	// Configurar tabla de un modelo de factura

	@FXML
	private TableView<factura> tableview;
	@FXML
	private TableColumn<factura, LocalDate> fecha;
	@FXML
	private TableColumn<factura, String> tipo;
	@FXML
	private TableColumn<factura, String> proveedor;
	@FXML
	private TableColumn<factura, String> cuit;
	@FXML
	private TableColumn<factura, Integer> prefijo;
	@FXML
	private TableColumn<factura, Integer> nrofactura;
	@FXML
	private TableColumn<factura, Double> subtotal;
	@FXML
	private TableColumn<factura, Double> iva;
	@FXML
	private TableColumn<factura, Double> iva2;
	@FXML
	private TableColumn<factura, Double> iva3;
	@FXML
	private TableColumn<factura, Double> otros;
	@FXML
	private TableColumn<factura, Double> total;
	@FXML
	private ComboBox<String> ListaSucursales;
	@FXML
	private ComboBox<String> ListaZona;

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
			/* En caso de excepciones */
		}

		ListaSucursales.setItems(null);

	}

	private ObservableList<String> listarZonas() throws ClassNotFoundException, SQLException, IOException {
		/// Listar todas las zonas

		zonas = modelo.listaZonas();

		ObservableList<String> lista = FXCollections.observableArrayList();

		/// Uso for each mejorado, expresión Lambda.

		zonas.forEach((n) -> lista.add(n.getNombre()));

		return lista;
	}

	public void seleccionZonas(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {

		// Listar las sucursales de la zona seleccionada

		sucursales = modelo.listaSucursales(zonas.get(ListaZona.getSelectionModel().getSelectedIndex()).getId());
		ObservableList<String> lista = FXCollections.observableArrayList();
		sucursales.forEach((n) -> lista.add(n.getNombre()));
		ListaSucursales.setItems(lista);

	}

	public void seleccionSucursal(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {

		if (ListaSucursales.getSelectionModel().getSelectedIndex() >= 0) 
			tableview.setItems(modeloSucursal.cargarData(sucursales.get(ListaSucursales.getSelectionModel().getSelectedIndex()).getId()));

	}

}