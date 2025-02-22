package domain;

import java.util.Date;

public class FavProduct {
    private int idClient;
    private Product product;
    private Date date;

    public FavProduct() {
    }

    public FavProduct(int idClient, Product product, Date date) {
		super();
		this.idClient = idClient;
		this.product = product;
		this.date = new Date();
	}



	public int getIdClient() {
		return idClient;
	}



	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}



	@Override
	public String toString() {
		return "FavProduct [idClient=" + idClient + ", product=" + product + ", date=" + date + "]";
	}



}
