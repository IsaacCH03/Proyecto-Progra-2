package business;

import java.util.List;

import domain.Product;

public class LogicProduct {
	
	public static boolean isSetProductIntoList(List<Product> list, Product product) {
		for (Product pro : list) {
			if (pro.getName().equals(product.getName())) {
				return true;
			}
		}

		return false;
	}
	
	
	

}
