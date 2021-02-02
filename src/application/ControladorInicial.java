package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.ModeloLogin;
import sucursal.ControladorSucursal;

public class ControladorInicial {

	@FXML
	private Label status;
	
	@FXML
	private TextField txtUsuario;
	
	@FXML
	private TextField txtContra;
	
	ModeloLogin modelo = new ModeloLogin();
	
	public void Login(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		
		
		int nivel = modelo.autenticacion(txtUsuario.getText(), txtContra.getText());
		
		if(nivel!=0)
			//status.setText("PERMITIDO");
		{
			if(nivel==1) OfPrincipal(nivel);
			
			else  Sucursal(nivel);
			
		}	
			
		else status.setText("DENEGADO!");
	
		
	}
	
	public void Sucursal(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = (AnchorPane)FXMLLoader.load(getClass().getResource("/sucursal/VistaSucursal.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		status.getScene().getWindow().hide();
		
	}
	
	public void OfPrincipal(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = (AnchorPane)FXMLLoader.load(getClass().getResource("/principal/VistaPrincipal.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		status.getScene().getWindow().hide();
		
	}
	
	
	//-----------------------------------------------------------------------------------------------------/
	
	
	public void Sucursal(int id) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = (AnchorPane)FXMLLoader.load(getClass().getResource("/sucursal/VistaSucursal.fxml"));
		
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		status.getScene().getWindow().hide();
		
	}
	
	public void OfPrincipal(int id) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = (AnchorPane)FXMLLoader.load(getClass().getResource("/principal/VistaPrincipal.fxml"));
		
		
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		status.getScene().getWindow().hide();
		
	}
	

}
