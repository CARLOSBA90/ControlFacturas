package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.nuevaZona;



public class ControladorNuevaZona implements Initializable{

	@FXML TextField nombre;
	@FXML Label etiqueta;
	ControladorZonas controlador;
	@FXML private ProgressIndicator indicador;
	@FXML private Button boton;
	private ExecutorService databaseExecutor;

	public void initialize(URL location, ResourceBundle resources) {
		indicador.setVisible(false);
		
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
			
			final nuevaZona modelo = new nuevaZona(nombre.getText());
			indicador.visibleProperty().bind(modelo.runningProperty());
			indicador.progressProperty().bind(modelo.progressProperty());
			boton.setVisible(false);
			
			modelo.setOnSucceeded(e ->{
				int insercion = modelo.getValue().getEntero();
				if(insercion==1) {
					//
					Stage stage = (Stage) etiqueta.getScene().getWindow();
				  	  stage.close();
				  	Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("Nueva Zona");
					alert.setContentText("Creación exitosa! nueva Zona agregada");
					alert.showAndWait();
					try {
						controlador.actualizarZona();
					} catch (ClassNotFoundException | SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
		
		
		} else { etiqueta.setText(" DEBES INSERTAR NOMBRE");
			
		}
		
		
		
		
	}

	public void setClaseZona(ControladorZonas controladorZonas, ExecutorService databaseExecutor) {
		controlador = controladorZonas;
		this.databaseExecutor = databaseExecutor;
		
	}
	

}
