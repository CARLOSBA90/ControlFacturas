package clases;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
public class usuario {
	/*
	 *  Describe los atributos comunes del usuario de la aplicación
	 *  permite la validación y acceso, el nivel asignado, existen 
	 *  dos tipos de usuario: los de oficina principal(usuario de primer nivel)
	 *  y los de sucursales(usuarios de segundo nivel)
	 */
	private SimpleStringProperty  usuario, contrasena;
	private SimpleIntegerProperty id, nivel;
	
	public usuario(int id, String usuario, String contrasena, int nivel) {
		this.id = new SimpleIntegerProperty(id);
		this.usuario = new SimpleStringProperty(usuario);
		this.contrasena = new SimpleStringProperty(contrasena);
		this.nivel = new SimpleIntegerProperty(nivel);
	}
	
	public String getUsuario() {
		return usuario.get();
	}
	public void setUsuario(SimpleStringProperty usuario) {
		this.usuario = usuario;
	}
	public String getContrasena() {
		return contrasena.get();
	}
	public void setContrasena(SimpleStringProperty contrasena) {
		this.contrasena = contrasena;
	}
	public int getId() {
		return id.get();
	}
	public void setId(SimpleIntegerProperty id) {
		this.id = id;
	}
	public int getNivel() {
		return nivel.get();
	}
	public void setNivel(SimpleIntegerProperty nivel) {
		this.nivel = nivel;
	}

}
