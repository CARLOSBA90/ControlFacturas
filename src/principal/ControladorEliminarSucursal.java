package principal;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import clases.acceso;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ModeloSucursal;

public class ControladorEliminarSucursal implements Initializable{
	@FXML Label nombre;
	@FXML TextField pass;
	@FXML Label msjpass;
	private String sucursal;
	private int id;
	private ModeloSucursal modelo;
	private acceso access;
	ControladorZonas controlador;

	public void initialize(URL location, ResourceBundle resources) {
		controlador = new ControladorZonas();
		modelo = new ModeloSucursal();
		
		Platform.runLater(() -> {
			nombre.setText("" + sucursal);
		});
		
		ChangeListener<String> formatopass = (observable, valorViejo, valorNuevo) -> {
			/// Maximo digitos permitidos por cada textfield
			if (pass.getText().length() > 15) {
				String s = pass.getText().substring(0, 15);
				pass.setText(s);
			}
		};
		
		pass.textProperty().addListener(formatopass);
		
	}

	public void objetos(String string, int i, acceso access, ControladorZonas controladorZonas) {
		this.sucursal = string;
		this.id = i;
		this.access = access;
		this.controlador = controladorZonas;
	}
	
	public void eliminar() throws SQLException, ClassNotFoundException, IOException {

		try {
			if(!pass.getText().equals("")) {
			   boolean query = modelo.eliminarSucursal(id, access.getId(), pass.getText());
			   if(query) {
				    Stage stage = (Stage) nombre.getScene().getWindow();
				  	stage.close();
				    controlador.actualizarZona();
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("SUCURSAL ELIMINADA");
					alert.setContentText("Eliminado con exito!");
					alert.showAndWait();
			   }
			
			}
			else { msjpass.setText(" CAMPO VACIO, INGRESE CONTRASEŅA");
				
			}
		} catch (SQLException e) {
			// TODO controlar excepcion
		}
	}
	
	

}
