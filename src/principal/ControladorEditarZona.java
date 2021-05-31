package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.editarZona;

public class ControladorEditarZona implements Initializable{
	@FXML Label label;
	@FXML TextField nombre;
	private String zona;
	private int id;
	ControladorZonas controlador;
	@FXML private ProgressIndicator indicador;
	private ExecutorService databaseExecutor;
	@FXML private Button editar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		controlador = new ControladorZonas();
		indicador.setVisible(false);
		
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
		
			if(!nombre.getText().equals("") && !nombre.getText().equals(zona)) {
				
				final editarZona modelo = new editarZona(id, nombre.getText());
				indicador.visibleProperty().bind(modelo.runningProperty());
				indicador.progressProperty().bind(modelo.progressProperty());
				editar.setVisible(false);
				
				modelo.setOnSucceeded(e ->{
					boolean edicion = modelo.getValue().isBooleano();
					if(edicion) {
					    Stage stage = (Stage) nombre.getScene().getWindow();
					  	stage.close();
					    try {
							controlador.actualizarZona();
						} catch (ClassNotFoundException | SQLException | IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setTitle("NOMBRE DE ZONA");
						alert.setContentText("Editado con exito!");
						alert.showAndWait();
					}
					}
				   );
				
				modelo.setOnFailed(e->{
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Error");
					alert.setContentText("Intento de actualizar datos: fallido!");
					alert.showAndWait();
					});
	  	
				databaseExecutor.submit(modelo);
				
	
			
		}}
			
		
	

	
	public void objetos(String string, int i, ControladorZonas controladorZonas, ExecutorService databaseExecutor) {
		this.zona = string;
		this.id = i;
		this.controlador = controladorZonas;
		this.databaseExecutor = databaseExecutor;
	}
	

}
