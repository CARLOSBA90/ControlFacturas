package clases;
import java.time.LocalDate;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class factura {
	
	
	
	
	public factura(LocalDate fecha, String tipo, String proveedor, String cuit, int prefijo, int nrofactura, double subtotal, double iva, double total) {
	
		super();
		this.fecha = fecha;
		this.tipo = new SimpleStringProperty(tipo);
		this.proveedor = new SimpleStringProperty(proveedor);
		this.cuit = new SimpleStringProperty(cuit);
		this.prefijo = new SimpleIntegerProperty(prefijo);
		this.nrofactura = new SimpleIntegerProperty(nrofactura);
		this.subtotal = new SimpleDoubleProperty(subtotal);
		this.iva = new SimpleDoubleProperty(iva);
		this.total = new SimpleDoubleProperty(total);
	}
	
	
	
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo.get();
	}
	public void setTipo(SimpleStringProperty tipo) {
		this.tipo = tipo;
	}
	public String getProveedor() {
		return proveedor.get();
	}
	public void setProveedor(SimpleStringProperty proveedor) {
		this.proveedor = proveedor;
	}
	public String getCuit() {
		return cuit.get();
	}
	public void setCuit(SimpleStringProperty cuit) {
		this.cuit = cuit;
	}
	public int getPrefijo() {
		return prefijo.get();
	}
	public void setPrefijo(SimpleIntegerProperty prefijo) {
		this.prefijo = prefijo;
	}
	public int getNrofactura() {
		return nrofactura.get();
	}
	public void setNrofactura(SimpleIntegerProperty nrofactura) {
		this.nrofactura = nrofactura;
	}
	public double getSubtotal() {
		return subtotal.get();
	}
	public void setSubtotal(SimpleDoubleProperty subtotal) {
		this.subtotal = subtotal;
	}
	public double getIva() {
		return iva.get();
	}
	public void setIva(SimpleDoubleProperty iva) {
		this.iva = iva;
	}
	public double getTotal() {
		return total.get();
	}
	public void setTotal(SimpleDoubleProperty total) {
		this.total = total;
	}






	private LocalDate fecha;
	private SimpleStringProperty tipo, proveedor, cuit;
	private SimpleIntegerProperty prefijo, nrofactura;
	private SimpleDoubleProperty subtotal, iva, total;

}
