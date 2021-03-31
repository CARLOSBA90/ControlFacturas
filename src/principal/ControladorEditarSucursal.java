package principal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import clases.acceso;
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

public class ControladorEditarSucursal implements Initializable{
	@FXML Label nombre;
	@FXML TextField user;
	@FXML TextField pass;
	@FXML ListView zona;
	@FXML private javafx.scene.control.Button botonSalir;
	private ObservableList<String> lista;
	private ModeloPrincipal modelo;
	private ModeloSucursal modeloSucursal;
	private ArrayList<zona> zonasArray;
	private int idSucursal;
	private String nombreSucursal;
	private int index;
	private int idZona;

	public void initialize(URL location, ResourceBundle resources) {
		modelo = new ModeloPrincipal();
		modeloSucursal = new ModeloSucursal();

		
		Platform.runLater(() -> {
			zona.setItems(lista);
			nombre.setText(nombreSucursal);
			user.setText(nombreSucursal);
			zona.getSelectionModel().select(index);
		});
		/// Algoritmo que solo permite caracteres alfabeticos en el Textfield user
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
		};
		
		user.textProperty().addListener(soloLetras);
	    pass.textProperty().addListener(formatopass);
	}

    public void salir(ActionEvent event) {
  	  Stage stage = (Stage) botonSalir.getScene().getWindow();
  	  stage.close();
  }

	public void objetos(ArrayList<clases.zona> zonasArray, String nombre, int id, int index, int idZona) {
		this.zonasArray = zonasArray;
		lista = FXCollections.observableArrayList();
		/// Uso for each mejorado, expresión Lambda.
		zonasArray.forEach(n -> lista.add(n.getNombre()));
		this.idSucursal = id;
		this.nombreSucursal = nombre;
		this.index = index;
		this.idZona = idZona;
	}
	
	public void cambiar() {
           
		//modelo.editarSucursal()
	}

}
