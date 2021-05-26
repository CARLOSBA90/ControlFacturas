package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import clases.acceso;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ModeloPrincipal;

public class ControladorEditarZona implements Initializable{
	@FXML Label label;
	@FXML TextField nombre;
	private String zona;
	private int id;
	private ModeloPrincipal modelo;
	ControladorZonas controlador;
	@FXML private ProgressIndicator indicador;
	private ExecutorService databaseExecutor;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		controlador = new ControladorZonas();
		modelo = new ModeloPrincipal();
		
		Platform.runLater(() -> {
			nombre.setText(zona);
		});
		
		ChangeListener<String> formatopass = (observable, valorViejo, valorNuevo) -> {
			/// Maximo digitos permitidos por cada textfield
			if (nombre.getText().length() > 15) {
				String s = nombre.getText().substring(0, 15);
				nombre.setText(s);
			}
		};
		
		nombre.textProperty().addListener(formatopass);
	}
	
	public void editar() throws ClassNotFoundException, IOException {
		
		try {
			if(!nombre.getText().equals("") && !nombre.getText().equals(zona)) {
			   boolean query = modelo.editarZona(id, nombre.getText());
			   if(query) {
				    Stage stage = (Stage) nombre.getScene().getWindow();
				  	stage.close();
				    controlador.actualizarZona();
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("NOMBRE DE ZONA");
					alert.setContentText("Editado con exito!");
					alert.showAndWait();
			   }
			
			}
			else { label.setText("OPERACIÓN INVÁLIDA");
				
			}
		} catch (SQLException e) {
			// TODO controlar excepcion
		}
		
	}
	
	public void objetos(String string, int i, ControladorZonas controladorZonas, ExecutorService databaseExecutor) {
		this.zona = string;
		this.id = i;
		this.controlador = controladorZonas;
	}
	

}
