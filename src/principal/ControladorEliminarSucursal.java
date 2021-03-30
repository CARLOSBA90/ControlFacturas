package principal;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.ModeloSucursal;

public class ControladorEliminarSucursal implements Initializable{
	@FXML Label nombre;
	@FXML TextField pass;
	@FXML Label msjpass;
	private String sucursal;
	private int id;
	private ModeloSucursal modelo;

	public void initialize(URL location, ResourceBundle resources) {
		modelo = new ModeloSucursal();
		
		Platform.runLater(() -> {
			nombre.setText("" + sucursal);
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

	public void objetos(String string, int i) {
		this.sucursal = string;
		this.id = i;
	}
	
	public void eliminar() {
		if(!pass.getText().equals("")) {
		     modelo.eliminarSucursal(id, pass.getText());
		}
		else { msjpass.setText(" CAMPO VACIO, INGRESE CONTRASEÑA");
			
		}
	}
	
	

}
