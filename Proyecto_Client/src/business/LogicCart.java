package business;

import java.util.ArrayList;
import java.util.List;

import domain.Cart;
import domain.Product;

public class LogicCart {
	
	public static double price(Cart cart) {
		double finalPrice = 0;
		if(cart.getList() == null) {
			return 0;
		}
		for(Product myProduct : cart.getList()) {
			finalPrice += myProduct.getPrice() * myProduct.getStock();
		}
		return finalPrice;
	}
	
	public static boolean exitsCart(List<Product> list, Product product) {
		for(Product myProduct : list) {
			if(myProduct.getIdProduct().equals(product.getIdProduct())) {
				return true;
			}
		}
		return false;
	}
	
    public static double calculateDiscount(double total, double discountPercentage) {//
        if (discountPercentage < 0 || discountPercentage > 100) {//si el descuento es menor a 0 o mayor a 100
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100.");
        }
        double discountAmount = total * (discountPercentage / 100);//descuento es igual al total por el porcentaje de descuento
        return total - discountAmount;//me retorna el total menos el descuento
    }
    


}
