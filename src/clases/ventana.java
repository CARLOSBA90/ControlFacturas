package clases;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ventana {
	private Stage stage;
	private FXMLLoader loader;
	
	public ventana(String recurso) throws IOException {
		this.stage = new Stage();
	    this.loader = new FXMLLoader(getClass().getResource(recurso));
		Parent root = (Parent) loader.load();
		stage.setScene(new Scene(root));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public void setLoader(FXMLLoader loader) {
		this.loader = loader;
	}
	
	 

}
