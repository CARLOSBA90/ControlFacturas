package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.ListarZonas;
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
	@FXML private ProgressIndicator indicador1;
	@FXML private ProgressIndicator indicador2;
	private ExecutorService databaseExecutor;
	
	public void initialize(URL location, ResourceBundle resources) {
		modelo = new ModeloPrincipal();
		modeloSucursal = new ModeloSucursal();
		indicador1.setVisible(false);
		indicador2.setVisible(false);
		Platform.runLater(() -> {
			try {
				listarZonas();
			} catch (ClassNotFoundException | SQLException | IOException e) {
				/* controlar excepcion */
			}	
		});
	}
	
	public void listarZonas() throws ClassNotFoundException, SQLException, IOException {
		/// Listar todas las zonas
		final ListarZonas modeloZona = new ListarZonas();
		indicador1.visibleProperty().bind(modeloZona.runningProperty());
		indicador1.progressProperty().bind(modeloZona.progressProperty());
		
		modeloZona.setOnSucceeded(e ->{
			zonasArray = modeloZona.getValue();
			ObservableList<String> lista = FXCollections.observableArrayList();
			/// Uso for each mejorado, expresión Lambda.
			zonasArray.forEach(n -> lista.add(n.getNombre()));
			zona.setItems(lista);
		   });
		
		modeloZona.setOnFailed(e->{
		    conexionFallida();
			});
		databaseExecutor.submit(modeloZona);

	}
	
	@SuppressWarnings("unchecked")
	public void seleccionZonas() throws ClassNotFoundException, IOException, SQLException {
		final modelo.seleccionZonas modelo = new modelo.seleccionZonas(zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId());
		indicador2.visibleProperty().bind(modelo.runningProperty());
		indicador2.progressProperty().bind(modelo.progressProperty());
		modelo.setOnSucceeded(e ->{
			sucursalArray = modelo.getValue();
			ObservableList<String> lista = FXCollections.observableArrayList();
			sucursalArray.forEach(n -> lista.add(n.getNombre()));
			sucursal.setItems(lista);
			//ListaSucursales.getSelectionModel().selectFirst();	
		   });
		modelo.setOnFailed(e->{
		    conexionFallida();
			});
		databaseExecutor.submit(modelo);
	}
	
	
    public void agregarSucursal(MouseEvent event) {
        try {
        	ventana ventana = new ventana("/principal/nuevaSucursal.fxml");
            ControladorAgregarSucursal controlador = ventana.getLoader().getController();
            controlador.objetos(zonasArray,this, databaseExecutor);
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
				controlador.objetos((String)sucursal.getSelectionModel().getSelectedItem(),sucursalArray.get(sucursal.getSelectionModel().getSelectedIndex()).getId(),access, this, databaseExecutor);
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
						zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId(), this, databaseExecutor);
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
		controlador.setClaseZona(this, databaseExecutor);
		ventana.getStage().show();
	}
	

	
	public void editarZona() throws IOException {
		ventana ventana = new ventana("/principal/editarZona.fxml");
		ControladorEditarZona controlador = ventana.getLoader().getController();
		controlador.objetos((String) zona.getSelectionModel().getSelectedItem(),
				zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId(), this, databaseExecutor);
		ventana.getStage().show();
	}
	
	public void eliminarZona() throws IOException {
		ventana ventana = new ventana("/principal/eliminarZona.fxml");
		ControladorEliminarZona controlador = ventana.getLoader().getController();
		controlador.objetos((String) zona.getSelectionModel().getSelectedItem(),
				zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId(),access,this, databaseExecutor);
		ventana.getStage().show();
	}

	public void setAcc(acceso access, ExecutorService databaseExecutor) {
		this.access = access;
		this.databaseExecutor = databaseExecutor;
	}
    
	@SuppressWarnings("unchecked")
	public void actualizarZona() throws ClassNotFoundException, SQLException, IOException {
		listarZonas();
		sucursal.getItems().clear();
	};
	public void conexionFallida() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setTitle("Error");
		alert.setContentText("Conexion a base de datos: fallido!");
		alert.showAndWait();
	}
	

}