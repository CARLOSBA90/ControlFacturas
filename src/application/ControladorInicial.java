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
		
		if(txtUsuario.getText().equals("")||txtContra.getText().equals(""))
		   {
			status.setText("ACCESO INCORRECTO");
		   }
		else {
		int id = modelo.autenticacion(txtUsuario.getText(), txtContra.getText());
		
		if(id!=0)
			//status.setText("PERMITIDO");
		{
			if(id==1) OfPrincipal(id);
			
			else  Sucursal(id);
			
		}	
			
		else status.setText("DENEGADO!");
	
		}
		
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
	//	Parent root = FXMLLoader.load(getClass().getResource("/sucursal/VistaSucursal.fxml"));
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sucursal/VistaSucursal.fxml"));
		
		Parent root = (Parent) loader.load();
		
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
		Parent root = (AnchorPane)FXMLLoader.load(getClass().getResource("/principal/VistaPrincipal.fxml"));
		
		
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		status.getScene().getWindow().hide();
		
	}
	

}
