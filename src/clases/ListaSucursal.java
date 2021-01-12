package clases;

import java.time.LocalDate;
import java.time.Month;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListaSucursal {
	
	public ListaSucursal() {
		
		
	}
	
public ObservableList<factura> getData(){
		
		ObservableList<factura> facturas = FXCollections.observableArrayList();
		
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 25),"A","BEBIDAS","20-1021",1001,2004232,1002.44,102.5,1.2,1.3,1.4,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 29),"C","GALLETAS","20-1331",22,123553,1666.44,155.5,0,0,0,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 18),"A","SNACKS","10-1221",2,1453,666.33,333.99,0,0,0,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 27),"A","BEBIDAS","20-100",1001,2004232,1002.44,102.5,0,0,0,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 26),"C","GALLETAS","20-121",22,123553,1666.44,155.5,0,0,0,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 19),"A","SNACKS","10-1231",2,1453,666.33,333.99,0,0,0,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 28),"A","BEBIDAS","20-121",1001,2004232,1002.44,102.5,0,0,0,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 25),"A","BEBIDAS","20-1021",1001,2004232,1002.44,102.5,1.2,1.3,1.4,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 29),"C","GALLETAS","20-1331",22,123553,1666.44,155.5,0,0,0,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 18),"A","SNACKS","10-1221",2,1453,666.33,333.99,0,0,0,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 27),"A","BEBIDAS","20-100",1001,2004232,1002.44,102.5,0,0,0,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 26),"C","GALLETAS","20-121",22,123553,1666.44,155.5,0,0,0,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 19),"A","SNACKS","10-1231",2,1453,666.33,333.99,0,0,0,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 28),"A","BEBIDAS","20-121",1001,2004232,1002.44,102.5,0,0,0,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 25),"A","BEBIDAS","20-1021",1001,2004232,1002.44,102.5,1.2,1.3,1.4,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 29),"C","GALLETAS","20-1331",22,123553,1666.44,155.5,0,0,0,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 18),"A","SNACKS","10-1221",2,1453,666.33,333.99,0,0,0,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 27),"A","BEBIDAS","20-100",1001,2004232,1002.44,102.5,0,0,0,1104.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 26),"C","GALLETAS","20-121",22,123553,1666.44,155.5,0,0,0,1821.94));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 19),"A","SNACKS","10-1231",2,1453,666.33,333.99,0,0,0,999.99));
		facturas.add(new factura(LocalDate.of(2020, Month.DECEMBER, 28),"A","BEBIDAS","20-121",1001,2004232,1002.44,102.5,0,0,0,1104.94));
		
		
		return facturas;
	}

}
