package domain;

import java.util.ArrayList;

import business.ClientFunction;

public class Utils {
	
	private String host;
    private int port;
	
	public static ClientFunction clientF;
	public static ArrayList<Product> listProducts;
	
	public Utils(String host, int port) {
		clientF = new ClientFunction(host,port);
	}

}
