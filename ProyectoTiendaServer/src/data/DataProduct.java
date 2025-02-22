package data;

import java.util.ArrayList;
import java.util.List;

import business.LogicProduct;
import domain.Product;

public class DataProduct {

	private final String fileName = "product.json";
	private final JsonUtils<Product> jsonUtils = new JsonUtils<>(fileName);

	public List<Product> getList() {
		try {
			return jsonUtils.getElements(Product.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Product>();
	}

	public void save(Product product, Product editProduct) {
		try {
			if (editProduct != null) {
				remove(editProduct.toString());
			}
			if (!LogicProduct.isSetProductIntoList(getList(), product)) {
				jsonUtils.save(product);
			} else {
				System.out.println("El producto ya existe");// aqui se envia el componente de la vista
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void remove(String name) {
		try {
			jsonUtils.removeElement(name, Product.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Product find(String name) {
		try {
			return jsonUtils.findElement(name, Product.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("El producto no existe");// aqui se envia el
		return null;
	}
	
	public Product searchProduct(String idProduct) {
		for(Product myProduct : getList()) {
			if(myProduct.getIdProduct().equals(idProduct)) {
				return myProduct;
			}
		}
		return null;
	}

}
