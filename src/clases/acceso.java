package clases;

public class acceso {
	private int nivel;
	private int id;
	private String nombre;
	public acceso(int id, String nombre, int nivel) {
		this.id = id;
		this.nombre= nombre;
		this.nivel = nivel;
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


	@Override
	public String toString() {
		return "acceso [nivel=" + nivel + ", id=" + id + ", nombre=" + nombre + "]";
	}
	
	

}
