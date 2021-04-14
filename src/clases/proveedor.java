package clases;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/*
 * Clase que describe los atributos comunes de un Proveedor
 */

public class proveedor {
	public proveedor(int id, String nombre, String cuit) {
		this.id = new SimpleIntegerProperty(id);
		this.nombre =  new SimpleStringProperty(nombre);
		this.cuit =  new SimpleStringProperty(cuit);		
	}
	public proveedor(int id, String nombre) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.nombre =  new SimpleStringProperty(nombre);
	}
	
	public String getNombre() {
		return nombre.get();
	}
	public void setNombre(SimpleStringProperty nombre) {
		this.nombre = nombre;
	}
	public String getCuit() {
		return cuit.get();
	}
	public void setCuit(SimpleStringProperty cuit) {
		this.cuit = cuit;
	}
	public int getId() {
		return id.get();
	}
	public void setId(SimpleIntegerProperty id) {
		this.id = id;
	}


	private SimpleStringProperty  nombre, cuit;
	private SimpleIntegerProperty id;
}
