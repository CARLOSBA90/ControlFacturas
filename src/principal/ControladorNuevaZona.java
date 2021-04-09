package principal;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ModeloPrincipal;


public class ControladorNuevaZona implements Initializable{

	@FXML TextField nombre;
	@FXML Label etiqueta;
	private ModeloPrincipal modelo;
	ControladorZonas controlador;

	public void initialize(URL location, ResourceBundle resources) {
		
		modelo = new ModeloPrincipal();
		
		ChangeListener<String> soloLetras = (observable, valorViejo, valorNuevo) -> {
			 if (!valorNuevo.matches("\\sa-zA-Z*")) {
				 nombre.setText(valorNuevo.replaceAll("[^\\sa-zA-Z]", ""));
			 }
			/// Maximo digitos permitidos por cada textfield
			if (nombre.getText().length() > 15) {
				String s = nombre.getText().substring(0, 15);
				nombre.setText(s);
			}

		};
		
		nombre.textProperty().addListener(soloLetras);
		
	}
	
	public void nuevo() throws SQLException, ClassNotFoundException, IOException {
		
		if(!nombre.getText().isEmpty()) {
		int insercion = modelo.nuevaZona(nombre.getText());
		if(insercion==1) {
			//
			Stage stage = (Stage) etiqueta.getScene().getWindow();
		  	  stage.close();
		  	Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setTitle("Nueva Zona");
			alert.setContentText("Creación exitosa! nueva Zona agregada");
			alert.showAndWait();
			controlador.actualizarZona();
		}
		} else { etiqueta.setText(" DEBES INSERTAR NOMBRE");
			
		}
	}

	public void setClaseZona(ControladorZonas controladorZonas) {
		controlador = controladorZonas;
		
	}
	

}
