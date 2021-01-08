package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControladorInicial {
	@FXML
	private Label status;
	
	@FXML
	private TextField txtUsuario;
	
	@FXML
	private TextField txtContra;
	
	public void Login(ActionEvent event) {
	
		if (txtUsuario.getText().equals("1") && txtContra.getText().equals("")){
			status.setText("Valido");
		}else {
			status.setText("Denegado");
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

}
