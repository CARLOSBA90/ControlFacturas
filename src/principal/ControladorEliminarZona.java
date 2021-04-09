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
import modelo.ModeloPrincipal;

public class ControladorEliminarZona implements Initializable {
	@FXML Label nombre;
	@FXML TextField pass;
	@FXML Label msjpass;
	private String zona;
	private int id;
	private ModeloPrincipal modelo;
	private acceso access;
	ControladorZonas controlador;
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		controlador = new ControladorZonas();
		modelo = new ModeloPrincipal();
		
		Platform.runLater(() -> {
			nombre.setText("" + zona);
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
		this.zona = string;
		this.id = i;
		this.access = access;
		this.controlador = controladorZonas;
	}
	
	public void eliminar() throws SQLException, ClassNotFoundException, IOException {

		try {
			if(!pass.getText().equals("")) {
			   boolean query = modelo.eliminarZona(id, access.getId(), pass.getText());
			   if(query) {
				    Stage stage = (Stage) nombre.getScene().getWindow();
				  	stage.close();
				    controlador.actualizarZona();
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("ZONA ELIMINADA");
					alert.setContentText("Eliminado con exito!");
					alert.showAndWait();
			   }
			
			}
			else { msjpass.setText(" CAMPO VACIO, INGRESE CONTRASEÑA");
				
			}
		} catch (SQLException e) {
			// TODO controlar excepcion
		}
	}
	
	

}
