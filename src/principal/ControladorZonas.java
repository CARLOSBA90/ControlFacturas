package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import clases.acceso;
import clases.sucursal;
import clases.ventana;
import clases.zona;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;
//FIXME OPTIMIZACION DE CODIGO, RENDIMIENTO?

public class ControladorZonas implements Initializable{
	private ModeloPrincipal modelo;
	private ModeloSucursal modeloSucursal;
	@FXML ListView zona;
	@FXML ListView sucursal;
	private ArrayList<zona> zonasArray;
	private ArrayList<sucursal> sucursalArray;
	private acceso access;
	
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
        try {
        	ventana ventana = new ventana("/principal/nuevaSucursal.fxml");
            ControladorAgregarSucursal controlador = ventana.getLoader().getController();
            controlador.objetos(zonasArray,this);
            ventana.getStage().show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
   
	public void eliminarSucursal() throws SQLException {

		if (sucursal.getSelectionModel().getSelectedIndex() != -1 && !sucursal.getSelectionModel().getSelectedItem().equals("Sin datos")) {
			try {
				ventana ventana = new ventana("/principal/eliminarSucursal.fxml");
				ControladorEliminarSucursal controlador = ventana.getLoader().getController();
				controlador.objetos((String)sucursal.getSelectionModel().getSelectedItem(),sucursalArray.get(sucursal.getSelectionModel().getSelectedIndex()).getId(),access,this);
				ventana.getStage().show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Debes seleccionar una Sucursal!");
			alert.showAndWait();
		}
		
	}
	
	public void editarSucursal() throws SQLException, ClassNotFoundException {
		
		if (sucursal.getSelectionModel().getSelectedIndex() != -1 && !sucursal.getSelectionModel().getSelectedItem().equals("Sin datos")) {
			try {
				ventana ventana = new ventana("/principal/editarSucursal.fxml");
				ControladorEditarSucursal controlador = ventana.getLoader().getController();
				controlador.objetos(zonasArray,(String)sucursal.getSelectionModel().getSelectedItem(),
						sucursalArray.get(sucursal.getSelectionModel().getSelectedIndex()).getId(), zona.getSelectionModel().getSelectedIndex(),
						zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId(),this);
				ventana.getStage().show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Debes seleccionar una Sucursal!");
			alert.showAndWait();
		}
	}
	
	public void agregarZona() throws IOException, ClassNotFoundException, SQLException {
		ventana ventana = new ventana("/principal/nuevaZona.fxml");
		ControladorNuevaZona controlador = ventana.getLoader().getController();
		controlador.setClaseZona(this);
		ventana.getStage().show();
	}
	

	
	public void editarZona() throws IOException {
		ventana ventana = new ventana("/principal/editarZona.fxml");
		ControladorEditarZona controlador = ventana.getLoader().getController();
		controlador.objetos((String) zona.getSelectionModel().getSelectedItem(),
				zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId(),this);
		ventana.getStage().show();
	}
	
	public void eliminarZona() throws IOException {
		ventana ventana = new ventana("/principal/eliminarZona.fxml");
		ControladorEliminarZona controlador = ventana.getLoader().getController();
		controlador.objetos((String) zona.getSelectionModel().getSelectedItem(),
				zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId(),access,this);
		ventana.getStage().show();
	}

	public void setAcc(acceso access) {
		this.access = access;
	}
    
	@SuppressWarnings("unchecked")
	public void actualizarZona() throws ClassNotFoundException, SQLException, IOException {
		zona.setItems(listarZonas());
		sucursal.getItems().clear();
	};
	

}