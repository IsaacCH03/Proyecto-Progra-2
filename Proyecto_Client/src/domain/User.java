package domain;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private String fullName;
	private int id;
	private String mail;
	private String password;
	private String address;
	private int phone;
	private Cart cart;
	private List<FavProduct> favProducts;
	
	public User(String fullName, int id, String mail, String password, String address, int phone, Cart cart) {
		this.fullName = fullName;
		this.id = id;
		this.mail = mail;
		this.password = password;
		this.address = address;
		this.phone = phone;
		this.cart = cart;
		this.favProducts = new ArrayList<>();
	}
	
	


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}
	public List<FavProduct> getFavProducts() {
	    return favProducts;
	}

	@Override
	public String toString() {
		return fullName + ";" + id + ";" + mail + ";" + password + ";"
				+ address + ";" + phone;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}




	public void setFavProducts(List<FavProduct> favProducts) {
		this.favProducts = favProducts;
	}
	




	

}
