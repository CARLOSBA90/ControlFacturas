package clases;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class zona {
	/*
	 * Define los atributos de una zona
	 */
	private SimpleStringProperty nombre;
	private SimpleIntegerProperty id;
	
	public zona(int id, String nombre) {
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
}
