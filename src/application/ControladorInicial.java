package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import clases.acceso;
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
	ModeloLogin modelo = new ModeloLogin();
	
	public void initialize(URL location, ResourceBundle resources) {
		/*Oyente para controlar la cantidad de caracteres permitidos en los Textfield
		** En este caso, maximo 20 caracteres*/
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
		/// Validación, si los campos estan vacios no se permite comprobar el acceso
		acceso acc = null;
		if (txtUsuario.getText().equals("") || txtContra.getText().equals("")) {
			status.setText("ACCESO INCORRECTO");
		} else {
			acc = modelo.autenticacion(txtUsuario.getText(), txtContra.getText());
			if (acc != null) {
				if (acc.getNivel() == 1)
					OfPrincipal(acc);
				else if (acc.getNivel() == 2)
					Sucursal(acc);
			} else
				status.setText("DENEGADO!");
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
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sucursal/VistaSucursal.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		ControladorSucursal controlador = loader.getController();
		controlador.setUsuario(2, "Sucursal2");
		primaryStage.show();
		status.getScene().getWindow().hide();
	
	}
	
	
	//Acceso comun, en caso de validarse el acceso
	public void Sucursal(acceso acc) throws IOException {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sucursal/VistaSucursal.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        ControladorSucursal controlador = loader.getController();
		controlador.setUsuario(acc.getId(),acc.getNombre());
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
