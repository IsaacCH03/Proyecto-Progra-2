package domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	
	private ArrayList<Product> list;
	
	public Cart() {
		list = new ArrayList<>();
	}

	public ArrayList<Product> getList() {
		return list;
	}

	public void setList(List<Product> cartProducts) {
		this.list = (ArrayList<Product>) cartProducts;
	}
	

}
