package domain;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;


public class Product {
    private String idProduct;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;
    private int value; // 1 es malo, 2 es regular, 3 es bueno, 4 es muy bueno, 5 es excelente
    private String urlImage;
    private ArrayList<Comment> listComments;
    
    public Product() {
    	this.listComments = new ArrayList<>();
    }
    

    public Product(String idProduct, String name, String description, double price, int stock, String category,
			String urlImage) {
		this.idProduct = idProduct;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.category = category;
		this.urlImage = urlImage;
		this.listComments = new ArrayList<>();
	}


	public Product(String name, String description, double price, int stock, String category, String urlImage) {
		this.idProduct = generateUniqueID();
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.category = category;
		this.urlImage = urlImage;
		this.listComments = new ArrayList<>();
	}

	public Product(String name, String description, double price, int stock, String category,
                   int value, String urlImage) {
        this.idProduct = generateUniqueID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.value = value;
        this.urlImage = urlImage;
        this.listComments = new ArrayList<>();
    }

    private String generateUniqueID() {
        Random random = new Random();
        int uniqueID = 10000 + random.nextInt(90000); // Genera un número entre 10000 y 99999
        return String.valueOf(uniqueID);
    }


	public String getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    public int getValue() {
        return value;
    }
    
    
    public void setIdProduct() {
		this.idProduct = generateUniqueID();
	}




	public void setName(String name) {
		this.name = name;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public void setPrice(double price) {
		this.price = price;
	}




	public void setStock(int stock) {
		this.stock = stock;
	}




	public void setCategory(String category) {
		this.category = category;
	}




	public void setValue(int value) {
		this.value = value;
	}




	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}




	public String getUrlImage() {
        return urlImage;
    }
	
	

    public ArrayList<Comment> getListComments() {
		return listComments;
	}


	public void setListComments(ArrayList<Comment> listComments) {
		this.listComments = listComments;
	}


	@Override
    public String toString() {
        return name;
    }
public String toString2() {
	return idProduct + "," + name+ "," + description + "," + price + "," + stock + "," + category + "," + urlImage; 
}

}
