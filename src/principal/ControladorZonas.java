package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import clases.sucursal;
import clases.zona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;


public class ControladorZonas implements Initializable{
	private ModeloPrincipal modelo;
	private ModeloSucursal modeloSucursal;
	@FXML ListView zona;
	@FXML ListView sucursal;
	private ArrayList<zona> zonasArray;
	private ArrayList<sucursal> sucursalArray;
	
	@SuppressWarnings("unchecked")
	public void initialize(URL location, ResourceBundle resources) {
		modelo = new ModeloPrincipal();
		modeloSucursal = new ModeloSucursal();

		try {
			zona.setItems(listarZonas());
		} catch (ClassNotFoundException | SQLException | IOException e) {
			/*GENERAR EXCEPCION CONTROLADA */
		}

	}
	
	public ObservableList<String> listarZonas() throws ClassNotFoundException, SQLException, IOException {
		/// Listar todas las zonas
		zonasArray = modelo.listaZonas();
		ObservableList<String> lista = FXCollections.observableArrayList();

		/// Uso for each mejorado, expresión Lambda.
		zonasArray.forEach(n -> lista.add(n.getNombre()));
		return lista;

	}
	
	@SuppressWarnings("unchecked")
	public void seleccionZonas() throws ClassNotFoundException, IOException, SQLException {
		sucursalArray = modelo.listaSucursales(zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId());
		ObservableList<String> lista = FXCollections.observableArrayList();
		sucursalArray.forEach(n -> lista.add(n.getNombre()));
		sucursal.setItems(lista);
	}
	
	
    public void agregarSucursal(MouseEvent event) {
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Nueva sucursal");
		alert.setContentText("Agregar nueva sucursal");
		alert.showAndWait();
    	
    }

}