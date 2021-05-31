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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.eliminarZona;

public class ControladorEliminarZona implements Initializable {
	@FXML Label nombre;
	@FXML TextField pass;
	@FXML Label msjpass;
	private String zona;
	private int id;
	private acceso access;
	ControladorZonas controlador;
	private ExecutorService databaseExecutor;
	@FXML private ProgressIndicator indicador;
	@FXML private Button boton;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		controlador = new ControladorZonas();
		indicador.setVisible(false);
		
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
	
	public void objetos(String string, int i, acceso access, ControladorZonas controladorZonas, ExecutorService databaseExecutor) {
		this.zona = string;
		this.id = i;
		this.access = access;
		this.controlador = controladorZonas;
		this.databaseExecutor = databaseExecutor;
	}
	
	public void eliminar() throws SQLException, ClassNotFoundException, IOException {
			if(!pass.getText().equals("")) {
				
				/// Listar todas las zonas
				final eliminarZona modelo = new eliminarZona(id, access.getId(), pass.getText());
				indicador.visibleProperty().bind(modelo.runningProperty());
				indicador.progressProperty().bind(modelo.progressProperty());
				boton.setVisible(false);
				
				modelo.setOnSucceeded(e ->{
					   boolean query = modelo.getValue().isBooleano();
					   if(query) {
						   Stage stage = (Stage) nombre.getScene().getWindow();
						  	stage.close();
						    try {
								controlador.actualizarZona();
							} catch (ClassNotFoundException | SQLException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setHeaderText(null);
							alert.setTitle("ZONA ELIMINADA");
							alert.setContentText("Eliminado con exito!");
							alert.showAndWait();
					   }
				   });
				
				modelo.setOnFailed(e->{
				   // conexionFallida();
					});
				databaseExecutor.submit(modelo);
			
			}
			else { msjpass.setText(" CAMPO VACIO, INGRESE CONTRASEÑA");
				
			}

	}
	
	

}
