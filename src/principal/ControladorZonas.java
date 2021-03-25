package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import clases.sucursal;
import clases.zona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;


public class ControladorZonas implements Initializable{
	private ModeloPrincipal modelo;
	private ModeloSucursal modeloSucursal;
	@FXML ListView zona;
	@FXML ListView sucursal;
	private ArrayList<zona> zonasArray;
	private ArrayList<sucursal> sucursalArray;
	
	public void initialize(URL location, ResourceBundle resources) {
		modelo = new ModeloPrincipal();
		modeloSucursal = new ModeloSucursal();

		try {
			zona.setItems(listarZonas());
		} catch (ClassNotFoundException | SQLException | IOException e) {
			/*GENERAR EXCEPCION CONTROLADA */
		}

	}
	
	public ObservableList<String> listarZonas() throws ClassNotFoundException, SQLException, IOException {
		/// Listar todas las zonas
		zonasArray = modelo.listaZonas();
		ObservableList<String> lista = FXCollections.observableArrayList();

		/// Uso for each mejorado, expresión Lambda.
		zonasArray.forEach(n -> lista.add(n.getNombre()));
		return lista;

	}
	
	@SuppressWarnings("unchecked")
	public void seleccionZonas() throws ClassNotFoundException, IOException, SQLException {
		sucursalArray = modelo.listaSucursales(zonasArray.get(zona.getSelectionModel().getSelectedIndex()).getId());
		ObservableList<String> lista = FXCollections.observableArrayList();
		sucursalArray.forEach(n -> lista.add(n.getNombre()));
		sucursal.setItems(lista);
	}
	
	
    public void agregarSucursal(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("nuevaSucursal.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Nueva Sucursal");
            stage.setScene(new Scene(root, 300, 300));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    

}