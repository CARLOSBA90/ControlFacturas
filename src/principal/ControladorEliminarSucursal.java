package principal;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import clases.acceso;
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
	private acceso access;

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

	public void objetos(String string, int i, acceso access) {
		this.sucursal = string;
		this.id = i;
		this.access=access;
	}
	
	public void eliminar() throws SQLException {

		try {
			if(!pass.getText().equals("")) {
			   boolean query = modelo.eliminarSucursal(id, access.getId(), pass.getText());
			   if(query) {
			       nombre.setText("Eliminado con exito");
			       msjpass.setText("");
			       pass.clear();  
			   }
			
			}
			else { msjpass.setText(" CAMPO VACIO, INGRESE CONTRASEÑA");
				
			}
		} catch (SQLException e) {
			// TODO controlar excepcion
		}
	}
	
	

}
