package clases;

import javafx.collections.ObservableList;

/*
 * Clase para confirmación de acciones importantes
 * Ejemplo: eliminación de sucursal o zona
 */
public class acceso {
	private int nivel;
	private int id;
	private String nombre;
	private ObservableList<factura> cargarData;
	public acceso(int id, String nombre, int nivel) {
		this.id = id;
		this.nombre= nombre;
		this.nivel = nivel;
	}
	public acceso(int id, String usuario, int nivel, ObservableList<factura> cargarData) {
		this.id = id;
		this.nombre= usuario;
		this.nivel = nivel;
		this.cargarData=cargarData;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public ObservableList<factura> getCargarData() {
		return cargarData;
	}
	public void setCargarData(ObservableList<factura> cargarData) {
		this.cargarData = cargarData;
	}
	@Override
	public String toString() {
		return "acceso [nivel=" + nivel + ", id=" + id + ", nombre=" + nombre + "]";
	}
	
	

}
