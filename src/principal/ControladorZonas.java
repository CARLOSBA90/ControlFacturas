package principal;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import clases.sucursal;
import clases.zona;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.ModeloPrincipal;
import modelo.ModeloSucursal;


public class ControladorZonas implements Initializable{
	private ModeloPrincipal modelo;
	private ModeloSucursal modeloSucursal;
	@FXML ListView zona;
	@FXML ListView sucursal;
	@FXML TextField user = new TextField();
	@FXML TextField pass = new TextField();
	@FXML TextField rpass = new TextField();
	@FXML Label mensaje;
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
		
		
		/// Algoritmo que solo permite caracteres alfabeticos en el Textfield user
		/*user.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (!newValue.matches("\\sa-zA-Z*")) {
	        	user.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
	        }
	    });*/
		
		Platform.runLater(() -> {

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
    
    public void salir(ActionEvent event) {
        System.exit(0);
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
    

}