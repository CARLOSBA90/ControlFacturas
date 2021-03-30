package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import clases.zona;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;

public class ControladorAgregarSucursal implements Initializable {
	private ModeloPrincipal modelo;
	private ModeloSucursal modeloSucursal;
	private ArrayList<zona> zonasArray;
	@FXML ListView zona;
	@FXML TextField user = new TextField();
	@FXML TextField pass = new TextField();
	@FXML TextField rpass = new TextField();
	@FXML Label mensaje;
	@FXML private javafx.scene.control.Button botonSalir;
	private ObservableList<String> lista;
	
	public void initialize(URL location, ResourceBundle resources) {
		modelo = new ModeloPrincipal();
		modeloSucursal = new ModeloSucursal();

		/// Algoritmo que solo permite caracteres alfabeticos en el Textfield user
		Platform.runLater(() -> {
			zona.setItems(lista);
		});
		ChangeListener<String> soloLetras = (observable, valorViejo, valorNuevo) -> {
			 if (!valorNuevo.matches("\\sa-zA-Z*")) {
		            user.setText(valorNuevo.replaceAll("[^\\sa-zA-Z]", ""));
			 }
			/// Maximo digitos permitidos por cada textfield
			if (user.getText().length() > 15) {
				String s = user.getText().substring(0, 15);
				user.setText(s);
			}

		};
		
		ChangeListener<String> formatopass = (observable, valorViejo, valorNuevo) -> {
			/// Maximo digitos permitidos por cada textfield
			if (pass.getText().length() > 15) {
				String s = pass.getText().substring(0, 15);
				pass.setText(s);
			}
			
			if (rpass.getText().length() > 15) {
				String s = rpass.getText().substring(0, 15);
				rpass.setText(s);
			}

		};
		
		user.textProperty().addListener(soloLetras);
	    pass.textProperty().addListener(formatopass);
	    rpass.textProperty().addListener(formatopass);
		
		
	}

	public void ingresarSucursal() throws SQLException {
		if (zona.getSelectionModel().getSelectedItem() != null && !user.getText().equals("")
				&& !pass.getText().equals("") && !rpass.getText().equals("")) {
			try {
				if (pass.getText().contentEquals(rpass.getText())) {
					int insercion = modeloSucursal.nuevaSucursal(zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId(),user.getText(),pass.getText());
					switch(insercion) {
			          
					case 0:
						   mensaje.setText("Registrado con exito!");
						   user.clear();
						   pass.clear();
						   rpass.clear();
						   zona.getSelectionModel().select(0);
					
					break;
					
					case 1: mensaje.setText("Nombre duplicado, registre otro!"); break;
					
					case 2: mensaje.setText("Error en base datos, transaccion fallida!"); break;
			
					}
				} else
					mensaje.setText("Las contraseñas deben coincidir");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			mensaje.setText("Faltan campos por completar");
		}
	}
	
    public void salir(ActionEvent event) {
    	  Stage stage = (Stage) botonSalir.getScene().getWindow();
    	  stage.close();
    }

	public void objetos(ArrayList<clases.zona> zonasArray2) {
		zonasArray = zonasArray2;
		lista = FXCollections.observableArrayList();
		/// Uso for each mejorado, expresión Lambda.
		zonasArray.forEach(n -> lista.add(n.getNombre()));
		
	}
	
	
}
