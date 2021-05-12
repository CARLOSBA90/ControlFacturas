package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import clases.acceso;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelo.ModeloLogin;
import principal.ControladorPrincipal;
import sucursal.ControladorSucursal;
/*
 * Autor: Carlos Peña
 * El programa tiene como finalidad controlar y organizar las facturas de forma virtual
 * Toda la documentación se encuentra en el repositorio: github.com/CARLOSBA90/ControlFacturas
 * Software libre
 */

public class ControladorInicial implements Initializable{
	///Declaramos los elementos de la interfaz grafica, y la instancia de clase modeloLogin
	@FXML private Label status;
	@FXML private TextField txtUsuario;
	@FXML private TextField txtContra;
	@FXML private Button botonLogin;
	@FXML private Button boton1;
	@FXML private Button boton2;
	@FXML ProgressIndicator databaseActivityIndicator;
	acceso acc = null;
	private ExecutorService databaseExecutor;
	//private Future          databaseSetupFuture;
	//private static final Logger logger = Logger.getLogger(ControladorInicial.class.getName());
	
	public void initialize(URL location, ResourceBundle resources) {
		/*Oyente para controlar la cantidad de caracteres permitidos en los Textfield
		** En este caso, maximo 20 caracteres*/
		databaseActivityIndicator.setVisible(false);
		ChangeListener<String> formato = (observable, valorViejo, valorNuevo) -> {
			/// Maximo digitos permitidos por cada textfield
			if (txtUsuario.getText().length() > 20) {
				String s = txtUsuario.getText().substring(0, 20);
				txtUsuario.setText(s);
			}
			if (txtContra.getText().length() > 20) {
				String s = txtContra.getText().substring(0, 20);
				txtContra.setText(s);
			}

		};
		txtUsuario.textProperty().addListener(formato);
		txtContra.textProperty().addListener(formato);
		
		botonLogin.setOnAction(event -> {
			try {
				Login();
			} catch (ClassNotFoundException | SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		databaseExecutor = Executors.newFixedThreadPool(1, new DatabaseThreadFactory()); 
		
		botonLogin.setOnAction(event -> {
			try {
				Login();
			} catch (ClassNotFoundException | SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	    }
		
	public void Login() throws ClassNotFoundException, SQLException, IOException {
		/// Validación, si los campos estan vacios no se permite comprobar el acceso
		
		if (txtUsuario.getText().equals("") || txtContra.getText().equals("")) {
			status.setText("ACCESO INCORRECTO");
		} else {
			
			final ModeloLogin modelo = new ModeloLogin(txtUsuario.getText(), txtContra.getText());
			//botonLogin.disabledProperty().bind(modelo.runningProperty());
			botonLogin.setVisible(false);
			boton1.setVisible(false);
			boton2.setVisible(false);
			databaseActivityIndicator.visibleProperty().bind(
		            modelo.runningProperty()
				    );
			databaseActivityIndicator.progressProperty().bind(
		            modelo.progressProperty()
		    );

			modelo.setOnSucceeded(e ->{
				acc = modelo.getValue();
				if (acc != null) {
					if (acc.getNivel() == 1)
						try {
							OfPrincipal(acc);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					else if (acc.getNivel() == 2)
						try {
							Sucursal(acc, databaseExecutor);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				} else
					status.setText("DENEGADO!");
				    botonLogin.setVisible(true);
				    boton1.setVisible(true);
				    boton2.setVisible(true);  
			   }
			      
					);
			
			 databaseExecutor.submit(modelo);
			
		}

	}
	

	/// Acceso mediante atajos de boton, fines demostrativos
	public void OfPrincipal(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/principal/VistaPrincipal.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		ControladorPrincipal controlador = loader.getController();
		controlador.setAcc(new acceso (1,"principal",1));
		controlador.setStage(primaryStage);
		primaryStage.show();
		status.getScene().getWindow().hide();
		
	}
	
	public void accesoSucursal() throws IOException {
		databaseExecutor = Executors.newFixedThreadPool(1, new DatabaseThreadFactory()); 
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sucursal/VistaSucursal.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		ControladorSucursal controlador = loader.getController();
		controlador.setUsuario(2, "Palermo",databaseExecutor);
		primaryStage.show();
		status.getScene().getWindow().hide();
	
	}
	
	
	//Acceso comun, en caso de validarse el acceso
	public void Sucursal(acceso acc, ExecutorService databaseExecutor) throws IOException {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sucursal/VistaSucursal.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        ControladorSucursal controlador = loader.getController();
		controlador.setUsuario(acc.getId(),acc.getNombre(),databaseExecutor);
		primaryStage.show();
		status.getScene().getWindow().hide();
		
	}
	
	public void OfPrincipal(acceso acc) throws IOException {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/principal/VistaPrincipal.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		ControladorPrincipal controlador = loader.getController();
		controlador.setAcc(acc);
		controlador.setStage(primaryStage);
		primaryStage.show();
		status.getScene().getWindow().hide();
	}
	

	
	
	

}


