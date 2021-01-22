package principal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class ControladorResumen implements Initializable{
	
	@FXML private ComboBox<String> ListaSucursales;
	@FXML private ComboBox<String> ListaZona;
	
   ObservableList<String> listaSuc = FXCollections.observableArrayList("Sucursal A", "Sucursal B", "Sucursal C");
	
	ObservableList<String> listaZo = FXCollections.observableArrayList("Zona Norte", "Zona Sur");
	
	
	public void initialize(URL location, ResourceBundle resources) {
		
        ListaSucursales.getItems().addAll(listaSuc);
		
		ListaZona.setItems(listaZo);
		
		
	}
	
	
    public void evento(MouseEvent event) {
    	
    	System.out.println("TEST");
    }

}
