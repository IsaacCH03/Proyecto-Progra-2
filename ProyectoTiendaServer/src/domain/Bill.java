package domain;

import java.util.Date;
import java.util.List;

public class Bill {
	
	private int idBill;
	private String nameBuyer;
	private String addressBuyer;
	private int phoneBuyer;
	private List<Product> products;
	private double desc;
	private double TAX;
	private double total;
	private Date dateTransaction;
	
	
	public Bill() {
	}
	
	public Bill(int idBill, String nameBuyer, String addressBuyer, int phoneBuyer, List<Product> products, double desc,double TAX,
			double total, Date dateTransaction) {
		this.idBill = idBill;
		this.nameBuyer = nameBuyer;
		this.addressBuyer = addressBuyer;
		this.phoneBuyer = phoneBuyer;
		this.products = products;
		this.desc = desc;
		this.TAX = TAX;
		this.total = total;
		this.dateTransaction = dateTransaction;
	}
	
	public double getTAX() {
		return TAX;
	}
	
	public void setTAX(double TAX) {
		this.TAX = TAX;
	}

	public int getIdBill() {
		return idBill;
	}

	public void setIdBill(int idBill) {
		this.idBill = idBill;
	}

	public String getNameBuyer() {
		return nameBuyer;
	}

	public void setNameBuyer(String nameBuyer) {
		this.nameBuyer = nameBuyer;
	}

	public String getAddressBuyer() {
		return addressBuyer;
	}

	public void setAddressBuyer(String addressBuyer) {
		this.addressBuyer = addressBuyer;
	}

	public int getPhoneBuyer() {
		return phoneBuyer;
	}

	public void setPhoneBuyer(int phoneBuyer) {
		this.phoneBuyer = phoneBuyer;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public double getDesc() {
		return desc;
	}

	public void setDesc(double desc) {
		this.desc = desc;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getDateTransaction() {
		return dateTransaction;
	}

	public void setDateTransaction(Date dateTransaction) {
		this.dateTransaction = dateTransaction;
	}
	
	@Override
	public String toString() {
	    return "Factura {" +
	            "\n  ID de factura: " + idBill +
	            "\n  Nombre del comprador: " + nameBuyer +
	            "\n  Dirección del comprador: " + addressBuyer +
	            "\n  Teléfono del comprador: " + phoneBuyer +
	            "\n  Productos: " + products +
	            "\n  Descuento: " + desc +
	            "\n  Impuesto: " + TAX +
	            "\n  Total: " + total +
	            "\n  Fecha de la transacción: " + dateTransaction +
	            "\n}";
	}

	
	
	

}
