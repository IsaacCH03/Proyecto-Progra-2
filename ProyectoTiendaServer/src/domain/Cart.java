package domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	
	private int idUser;
	private Product product;
	
	public Cart(int idUser, Product product) {
		this.idUser = idUser;
		this.product = product;
	}
	public Cart() {}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	

}
