package clases;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public class listado {
	private ObservableList<factura> lista;
	private ArrayList<proveedor> proveedores;
	
	public listado(ObservableList<factura> lista) {
		this.lista=lista;
	}
	
	public listado(ObservableList<factura> lista, ArrayList<proveedor> proveedores) {
		this.lista=lista;
		this.proveedores=proveedores;
	}

	public ObservableList<factura> getLista() {
		return lista;
	}

	public void setLista(ObservableList<factura> lista) {
		this.lista = lista;
	}

	public ArrayList<proveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(ArrayList<proveedor> proveedores) {
		this.proveedores = proveedores;
	}
}
