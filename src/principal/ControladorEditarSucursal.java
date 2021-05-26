package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import clases.acceso;
import clases.zona;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;
import modelo.editarSucursal;

public class ControladorEditarSucursal implements Initializable{
	@FXML Label nombre;
	@FXML TextField user;
	@FXML TextField pass;
	@FXML ListView<String> zona;
	@FXML private Button botonSalir;
	@FXML private Button botonCambiar;
	private ObservableList<String> lista;
	private ModeloSucursal modeloSucursal;
	private ArrayList<zona> zonasArray;
	private int idSucursal;
	private String nombreSucursal;
	private int index;
	private int idZona;
	private ControladorZonas controlador;
	@FXML private ProgressIndicator indicador;
	private ExecutorService databaseExecutor;
	

	public void initialize(URL location, ResourceBundle resources) {
		controlador = new ControladorZonas();
		modeloSucursal = new ModeloSucursal();
		indicador.setVisible(false);
		Platform.runLater(() -> {
			zona.setItems(lista);
			nombre.setText(nombreSucursal);
			user.setText(nombreSucursal);
			zona.getSelectionModel().select(index);
		});
		/// Algoritmo que solo permite caracteres alfabeticos en el Textfield user
		ChangeListener<String> soloLetras = (observable, valorViejo, valorNuevo) -> {
			 if (!valorNuevo.matches("\\sa-zA-Z*")) {
		            user.setText(valorNuevo.replaceAll("[^\\sa-zA-Z]", ""));
			 }
			/// Maximo digitos permitidos por cada textfield
			if (user.getText().length() > 15) {
				String s = user.getText().substring(0, 15);
				user.setText(s);
			}

		};
		
		ChangeListener<String> formatopass = (observable, valorViejo, valorNuevo) -> {
			/// Maximo digitos permitidos por cada textfield
			if (pass.getText().length() > 15) {
				String s = pass.getText().substring(0, 15);
				pass.setText(s);
			}
		};
		
		user.textProperty().addListener(soloLetras);
	    pass.textProperty().addListener(formatopass);
	}

    public void salir(ActionEvent event) {
  	  Stage stage = (Stage) botonSalir.getScene().getWindow();
  	  stage.close();
  }

	public void objetos(ArrayList<clases.zona> zonasArray, String nombre, int id, int index, int idZona, ControladorZonas controladorZonas, ExecutorService databaseExecutor) {
		this.zonasArray = zonasArray;
		lista = FXCollections.observableArrayList();
		/// Uso for each mejorado, expresión Lambda.
		zonasArray.forEach(n -> lista.add(n.getNombre()));
		this.idSucursal = id;
		this.nombreSucursal = nombre;
		this.index = index;
		this.idZona = idZona;
		this.controlador = controladorZonas;
		this.databaseExecutor = databaseExecutor;
	}
	
	public void cambiar() throws SQLException, ClassNotFoundException, IOException {
		
		/* Envio de metodos al metodo editar Sucursal: 
		 * - id sucursal
		 * - seleccion de lista zona, si este no cambia se deja el ultimo,
		 * - nombre sucursal(campo usuario.usuario de tabla), si no se cambia, el parametro sera null
		 * - contraseña(campo usuario.contrasena de tabla), si no se cambia, el parametro sera null
		 * - id zona anterior, este valor se envia para el query de actualizacion de zona
		 */
		if (!zona.getSelectionModel().isEmpty()) {
			
			final editarSucursal modelo = new editarSucursal(idSucursal,
					(zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId() == idZona) ? -2
							: zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId(),
					(nombreSucursal.equals(user.getText())) ? null : user.getText(),
					(pass.getText().equals("") ? null : pass.getText()),
					idZona);
			

			indicador.visibleProperty().bind(modelo.runningProperty());
			indicador.progressProperty().bind(modelo.progressProperty());
			botonCambiar.setVisible(false);
			botonSalir.setVisible(false);
			
			modelo.setOnSucceeded(e ->{
				int edicion = modelo.getValue().getEntero();
				if(edicion==1) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("Edicion Exitosa");
					alert.setContentText("Los datos han sido correctamente actualizado!");
					alert.showAndWait();
					try {
						controlador.actualizarZona();
					} catch (ClassNotFoundException | SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					this.salir(null); 
				}else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Error");
					alert.setContentText("Intento de actualizar datos: fallido!");
					alert.showAndWait();
				}
			   });
			
			modelo.setOnFailed(e->{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error");
				alert.setContentText("Intento de actualizar datos: fallido!");
				alert.showAndWait();
				});
			databaseExecutor.submit(modelo);

			
	
		}
	}

}
