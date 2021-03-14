package clases;

import java.time.LocalDate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class factura {
	
	private LocalDate fecha;
	private SimpleStringProperty tipo;
	private SimpleStringProperty proveedor; 
	private SimpleStringProperty cuit;
	private SimpleStringProperty forma; 
	private SimpleStringProperty sucursal;
	private SimpleIntegerProperty prefijo;
	private SimpleIntegerProperty nrofactura;
	private SimpleDoubleProperty subtotal; 
	private SimpleDoubleProperty iva;
	private SimpleDoubleProperty iva2;
	private SimpleDoubleProperty iva3; 
	private SimpleDoubleProperty otros;
	private SimpleDoubleProperty total;
	private boolean impuestos;
	

	public factura(LocalDate fecha, String tipo, String proveedor, String cuit, int prefijo, int nrofactura,
			double subtotal, double iva, double iva2, double iva3, double otros, double total) {

		super();
		this.fecha = fecha;
		this.tipo = new SimpleStringProperty(tipo);
		this.proveedor = new SimpleStringProperty(proveedor);
		this.cuit = new SimpleStringProperty(cuit);
		this.prefijo = new SimpleIntegerProperty(prefijo);
		this.nrofactura = new SimpleIntegerProperty(nrofactura);
		this.subtotal = new SimpleDoubleProperty(subtotal);
		this.iva = new SimpleDoubleProperty(iva);
		this.iva2 = new SimpleDoubleProperty(iva2);
		this.iva3 = new SimpleDoubleProperty(iva3);
		this.otros = new SimpleDoubleProperty(otros);
		this.total = new SimpleDoubleProperty(total);
		this.impuestos = true;
	}

	public factura(LocalDate fecha, String tipo, String proveedor, String cuit, int prefijo, int nrofactura,
			String forma, double subtotal, double iva, double iva2, double iva3, double otros, double total) {

		super();
		this.fecha = fecha;
		this.tipo = new SimpleStringProperty(tipo);
		this.proveedor = new SimpleStringProperty(proveedor);
		this.cuit = new SimpleStringProperty(cuit);
		this.prefijo = new SimpleIntegerProperty(prefijo);
		this.nrofactura = new SimpleIntegerProperty(nrofactura);
		this.forma = new SimpleStringProperty(forma);
		this.subtotal = new SimpleDoubleProperty(subtotal);
		this.iva = new SimpleDoubleProperty(iva);
		this.iva2 = new SimpleDoubleProperty(iva2);
		this.iva3 = new SimpleDoubleProperty(iva3);
		this.otros = new SimpleDoubleProperty(otros);
		this.total = new SimpleDoubleProperty(total);
		this.impuestos = true;
	}

	public factura(String sucursal, LocalDate fecha, String tipo, String proveedor, String cuit, int prefijo,
			int nrofactura, String forma, double subtotal, double iva, double iva2, double iva3, double otros,
			double total) {

		super();
		this.sucursal = new SimpleStringProperty(sucursal);
		this.fecha = fecha;
		this.tipo = new SimpleStringProperty(tipo);
		this.proveedor = new SimpleStringProperty(proveedor);
		this.cuit = new SimpleStringProperty(cuit);
		this.prefijo = new SimpleIntegerProperty(prefijo);
		this.nrofactura = new SimpleIntegerProperty(nrofactura);
		this.forma = new SimpleStringProperty(forma);
		this.subtotal = new SimpleDoubleProperty(subtotal);
		this.iva = new SimpleDoubleProperty(iva);
		this.iva2 = new SimpleDoubleProperty(iva2);
		this.iva3 = new SimpleDoubleProperty(iva3);
		this.otros = new SimpleDoubleProperty(otros);
		this.total = new SimpleDoubleProperty(total);
		this.impuestos = true;
	}

	public factura(String sucursal, LocalDate fecha, String tipo, String proveedor, int prefijo, int nrofactura,
			String forma, double subtotal, double total) {

		super();
		this.sucursal = new SimpleStringProperty(sucursal);
		this.fecha = fecha;
		this.tipo = new SimpleStringProperty(tipo);
		this.proveedor = new SimpleStringProperty(proveedor);
		this.prefijo = new SimpleIntegerProperty(prefijo);
		this.nrofactura = new SimpleIntegerProperty(nrofactura);
		this.forma = new SimpleStringProperty(forma);
		this.subtotal = new SimpleDoubleProperty(subtotal);
		this.total = new SimpleDoubleProperty(total);
		this.impuestos = false;
	}

	public factura(String sucursal, LocalDate fecha, String tipo, String proveedor, int prefijo, int nrofactura,
			String forma, double subtotal, double iva, double iva2, double iva3, double otros, double total) {

		super();
		this.sucursal = new SimpleStringProperty(sucursal);
		this.fecha = fecha;
		this.tipo = new SimpleStringProperty(tipo);
		this.proveedor = new SimpleStringProperty(proveedor);
		this.prefijo = new SimpleIntegerProperty(prefijo);
		this.nrofactura = new SimpleIntegerProperty(nrofactura);
		this.forma = new SimpleStringProperty(forma);
		this.iva = new SimpleDoubleProperty(iva);
		this.iva2 = new SimpleDoubleProperty(iva2);
		this.iva3 = new SimpleDoubleProperty(iva3);
		this.otros = new SimpleDoubleProperty(otros);
		this.subtotal = new SimpleDoubleProperty(subtotal);
		this.total = new SimpleDoubleProperty(total);
		this.impuestos = true;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getSucursal() {
		return sucursal.get();
	}

	public void setSucursal(SimpleStringProperty tipo) {
		this.sucursal = sucursal;
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

	public String getForma() {
		return forma.get();
	}

	public void setForma(SimpleStringProperty forma) {
		this.forma = forma;
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

	public double getIva2() {
		return iva2.get();
	}

	public void setIva2(SimpleDoubleProperty iva2) {
		this.iva2 = iva2;
	}

	public double getIva3() {
		return iva3.get();
	}

	public void setIva3(SimpleDoubleProperty iva3) {
		this.iva3 = iva3;
	}

	public double getOtros() {
		return otros.get();
	}

	public void setOtros(SimpleDoubleProperty otros) {
		this.otros = otros;
	}

	public double getTotal() {
		return total.get();
	}

	public void setTotal(SimpleDoubleProperty total) {
		this.total = total;
	}

	public boolean isImpuestos() {
		return impuestos;
	}

	public void setImpuestos(boolean impuestos) {
		this.impuestos = impuestos;
	}

	@Override
	public String toString() {
		return "factura [fecha=" + fecha + ", tipo=" + tipo + ", proveedor=" + proveedor + ", cuit=" + cuit + ", forma="
				+ forma + ", sucursal=" + sucursal + ", prefijo=" + prefijo + ", nrofactura=" + nrofactura
				+ ", subtotal=" + subtotal + ", iva=" + iva + ", iva2=" + iva2 + ", iva3=" + iva3 + ", otros=" + otros
				+ ", total=" + total + "]";
	}

}
