package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ModeloLogin;
import principal.ControladorPrincipal;
import sucursal.ControladorSucursal;

public class ControladorInicial implements Initializable{
	@FXML private Label status;
	@FXML private TextField txtUsuario;
	@FXML private TextField txtContra;
	ModeloLogin modelo = new ModeloLogin();
	
	public void initialize(URL location, ResourceBundle resources) {
		
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
		
	}
	
	public void Login() throws ClassNotFoundException, SQLException, IOException {

		if (txtUsuario.getText().equals("") || txtContra.getText().equals("")) {
			status.setText("ACCESO INCORRECTO");
		} else {
			int id = modelo.autenticacion(txtUsuario.getText(), txtContra.getText());

			if (id != 0) {
				if (id == 1)
					OfPrincipal(id);
				else
					Sucursal(id);
			}
		else status.setText("DENEGADO!");
		}
		
	}
	
	public void OfPrincipal(ActionEvent event) throws IOException {
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/principal/VistaPrincipal.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		ControladorPrincipal controlador = loader.getController();
		controlador.setStage(primaryStage);
		primaryStage.show();
		status.getScene().getWindow().hide();
		
	}
	
	
	//-----------------------------------------------------------------------------------------------------/
	
	
	public void Sucursal(int id) throws IOException {
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sucursal/VistaSucursal.fxml"));
		Parent root = (Parent)loader.load();
        ControladorSucursal controlador = loader.getController();
		controlador.setUsuario(id);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		status.getScene().getWindow().hide();
		
	}
	
	public void OfPrincipal(int id) throws IOException {
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/principal/VistaPrincipal.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		ControladorPrincipal controlador = loader.getController();
		controlador.setStage(primaryStage);
		primaryStage.show();
		status.getScene().getWindow().hide();
	}
	
	public void accesoSucursal() throws IOException {
		
		Stage primaryStage = new Stage();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/sucursal/VistaSucursal.fxml"));
			
			Parent root = (Parent) loader.load();
			
	        ControladorSucursal controlador = loader.getController();
	        
			controlador.setUsuario(2);
			
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			
			primaryStage.setResizable(false);
			
			primaryStage.show();
			
			status.getScene().getWindow().hide();
		
		
	}
	
	
	

}
