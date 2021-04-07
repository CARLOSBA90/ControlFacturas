package principal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;

public class ControladorEditarSucursal implements Initializable{
	@FXML Label nombre;
	@FXML TextField user;
	@FXML TextField pass;
	@FXML ListView<String> zona;
	@FXML private javafx.scene.control.Button botonSalir;
	private ObservableList<String> lista;
	private ModeloSucursal modeloSucursal;
	private ArrayList<zona> zonasArray;
	private int idSucursal;
	private String nombreSucursal;
	private int index;
	private int idZona;

	public void initialize(URL location, ResourceBundle resources) {
		modeloSucursal = new ModeloSucursal();
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

	public void objetos(ArrayList<clases.zona> zonasArray, String nombre, int id, int index, int idZona) {
		this.zonasArray = zonasArray;
		lista = FXCollections.observableArrayList();
		/// Uso for each mejorado, expresión Lambda.
		zonasArray.forEach(n -> lista.add(n.getNombre()));
		this.idSucursal = id;
		this.nombreSucursal = nombre;
		this.index = index;
		this.idZona = idZona;
	}
	
	public void cambiar() throws SQLException {
		
		/* Envio de metodos al metodo editar Sucursal: 
		 * - id sucursal
		 * - seleccion de lista zona, si este no cambia se deja el ultimo,
		 * - nombre sucursal(campo usuario.usuario de tabla), si no se cambia, el parametro sera null
		 * - contraseña(campo usuario.contrasena de tabla), si no se cambia, el parametro sera null
		 * - id zona anterior, este valor se envia para el query de actualizacion de zona
		 */
		if (!zona.getSelectionModel().isEmpty()) {
			int edicion = modeloSucursal.editarSucursal(idSucursal,
					(zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId() == idZona) ? -2
							: zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId(),
					(nombreSucursal.equals(user.getText())) ? null : user.getText(),
					(pass.getText().equals("") ? null : pass.getText()),
					idZona);
			if(edicion==1) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Edicion Exitosa");
				alert.setContentText("Los datos han sido correctamente actualizado!");
				alert.showAndWait();
				//FIXME ARREGLAR PROBLEMA DE DATOS ANTERIORES EN LA VISTA CONTROLADOR ZONA
				this.salir(null); 
			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error");
				alert.setContentText("Intento de actualizar datos: fallido!");
				alert.showAndWait();
			}
		}
	}

}
