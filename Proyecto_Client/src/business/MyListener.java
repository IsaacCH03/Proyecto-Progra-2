package business;

import domain.Product;

public interface MyListener { // interface que se encarga de definir el metodo onClickListener que recibe un producto
	
	public void onClickListener(Product product);

}
