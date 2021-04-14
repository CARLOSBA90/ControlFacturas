package clases;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/*
 * Clase que describe un sub-usuario, en este caso
 * aquella persona o ente responsable de una sucursal
 */

public class sucursal {
	public sucursal(int id, String nombre) {
		this.nombre = new SimpleStringProperty(nombre);
		this.id = new SimpleIntegerProperty(id);
	}
	public String getNombre() {
		return nombre.get();
	}
	public void setNombre(SimpleStringProperty nombre) {
		this.nombre = nombre;
	}
	public int getId() {
		return id.get();
	}
	public void setId(SimpleIntegerProperty id) {
		this.id = id;
	}
	private SimpleStringProperty nombre;
	private SimpleIntegerProperty id;
	
	
}
