package sucursal;

import clases.factura;
import javafx.collections.ObservableList;

public class listado {
	private ObservableList<factura> lista;
	
	public listado(ObservableList<factura> lista) {
		this.lista=lista;
	}

	public ObservableList<factura> getLista() {
		return lista;
	}

	public void setLista(ObservableList<factura> lista) {
		this.lista = lista;
	}
	
	

}
