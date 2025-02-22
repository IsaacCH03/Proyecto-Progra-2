package business;

import java.util.List;

import domain.Cart;

public class LogicCart {
	
	
	public static boolean exitsCart(List<Cart> list, Cart cart) {
		for(Cart myCart : list) {
			if(myCart.getIdUser() == cart.getIdUser() && myCart.getProduct().getIdProduct().equals(cart.getProduct().getIdProduct())) {
				return true;
			}
		}
		return false;
	}

}
