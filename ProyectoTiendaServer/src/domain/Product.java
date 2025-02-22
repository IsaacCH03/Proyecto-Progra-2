package domain;

import java.util.ArrayList;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    
    //Constructor para guardar un producto del carrito
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
		super();
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


    public String getUrlImage() {
        return urlImage;
    }
    
    
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	


	public void setValue(int value) {
		this.value = value;
	}
	
	
	


	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}
	public ArrayList<Comment> getListComments() {
	    return listComments;
	}

	public void setListComments(ArrayList<Comment> listComments) {
	    this.listComments = listComments;
	}



	@Override
    public String toString() {
        return "Product{" +
                "idProduct='" + idProduct + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category='" + category + '\'' +
                ", value=" + value +
                ", urlImage='" + urlImage + '\'' +
                ", listComments='" + listComments + '\'' +
                '}';
    }


}
