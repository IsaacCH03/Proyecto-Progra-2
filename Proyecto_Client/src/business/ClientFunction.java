package business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import domain.Bill;
import domain.Cart;
import domain.Comment;
import domain.FavProduct;
import domain.Product;
import domain.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ClientFunction {
    private String host;
    private int port;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private User user;
    private GUIMarketController GUIMarketController;
    private GUIFavController guiFavController;

    public ClientFunction(String host, int port) {
        this.host = host;
        this.port = port;
        this.guiFavController = guiFavController;
        initClient();
//        listenForUpdates();
    }

    private void initClient() {
        try {
            this.socket = new Socket(host, port);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.printWriter = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error inicializando el cliente: " + e.getMessage());
        }
    }

    public void sendRecord(User user, Label lblMessage) {
        this.printWriter.println("REGISTER;" + user.toString());
        handleServerResponse(lblMessage,null);
    }

    public void login(String mail, String password, Label lblMessage, Button btn) {
        this.printWriter.println("LOGIN;" + mail + ";" + password);
        handleServerResponse(lblMessage,btn);
    }

    private void handleServerResponse(Label lblMessage, Button btn) {
        new Thread(() -> {
            try {
                String response = bufferedReader.readLine();
                if (response.startsWith("SUCCESS:")) {
                	notify("Inicio de sesion exitoso", lblMessage);
                	System.out.println(response + "--------------");
                    String[] userData = response.substring(8).split(";"); 
                    System.out.println(userData[0]);
                    try {
                        int userId = Integer.parseInt(userData[1]);  // Convertir ID a entero
                        int userPhone = Integer.parseInt(userData[5]);  // Convertir Teléfono a entero
                        this.user = new User(userData[0], userId, userData[2], userData[3], userData[4], userPhone,null);
                        btn.setDisable(false);        
                        System.out.println("Usuario autenticado: " + user.toString());
                      
                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir ID o Teléfono: " + e.getMessage());
                    }

                    }else if (response.startsWith("ERROR:")) {
                    	notify(response, lblMessage);
                        System.out.println("Error al iniciar sesión: " + response);
                        btn.setDisable(false);
                    }
                
                else {
                
                    	notify(response, lblMessage);
                       
             
                    }
              
                System.out.println("Respuesta del servidor: " + response);
            } catch (IOException e) {
                System.out.println("Error al recibir la respuesta del servidor.");
            }
        }).start();
    }

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	  private void notify(String message, Label lblMessage) {
		  Platform.runLater(() -> {
	        lblMessage.getStyleClass().clear();
	        lblMessage.setText(message);
	        lblMessage.getStyleClass().add("lblNotification");
	        
	        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> lblMessage.setText("")));
	        timeline.setCycleCount(1);
	        timeline.play();
		  });
	    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public String updateProfile(String fullName, String address, String phone, String newPassword) {
	    String message = "UPDATE_PROFILE;" + user.getMail() + ";" + fullName + ";" + address + ";" + phone;
	    if (!newPassword.isEmpty()) {
	        message += ";" + newPassword;
	    }

	    printWriter.println(message);

	    try {
	        return bufferedReader.readLine(); // Esperar respuesta del servidor
	    } catch (IOException e) {
	        return "ERROR: No se pudo actualizar el perfil";
	    }
	}
	
	//nuevo
	public List<Product> getProducts() {
	    List<Product> products = new ArrayList<>();
	    printWriter.println("GET_PRODUCTS");

	    try {
	        String response = bufferedReader.readLine();
	        System.out.println("---------------" + response);
	        if (response.startsWith("PRODUCT_LIST;")) {
	            String jsonData = response.substring(13); // Removemos "PRODUCT_LIST;"
	            ObjectMapper objectMapper = new ObjectMapper();
	            objectMapper.registerModule(new JavaTimeModule()); // 🔹 REGISTRA EL MÓDULO PARA LocalDate
	            products = objectMapper.readValue(jsonData, 
	                        objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
	        } else {
	            System.out.println("Error al recibir la lista de productos");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return products; // Retorna la lista de productos
	}

	public void addProductToCart(int idUser, Product product) {
	    try {
	        String productText = product.toString2(); // Convertimos el producto a texto

	        // Verificar que los datos a enviar son correctos
	        System.out.println("Enviando producto al servidor: " + productText);

	        this.printWriter.println("SAVECART;" + idUser + ";" + productText);
	        
	        System.out.println(bufferedReader.readLine());  //colocar un componente para pasar el mensaje
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public List<Product> openCart() {
	    this.printWriter.println("GETCART;" + user.getId());

	    List<Product> cartProducts = new ArrayList<>();
	    try {
	        String response = bufferedReader.readLine();
	        System.out.println(response);
	        if (response.startsWith("CART_LIST;")) {
	            String jsonData = response.substring(10); // Eliminar el prefijo "CART_LIST;"
	            ObjectMapper objectMapper = new ObjectMapper();
	            cartProducts = objectMapper.readValue(jsonData, 
	                objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
	            System.out.println("Carrito recibido: " + cartProducts);
	            Cart cart = new Cart();
	            if(!cartProducts.isEmpty()) {
	                cart.setList(cartProducts);
	   	            user.setCart(cart);
	            }
	         
	        } else {
	            System.out.println("Error al recibir el carrito");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return cartProducts;
	} 
	public void editProductInCart(int idUser, Product product, Label lblMessage) {
        try {
            String productText = product.toString2();
            System.out.println("Editando producto en carrito: " + productText);
            this.printWriter.println("EDITCART;" + idUser + ";" + productText);

            String response = bufferedReader.readLine();
            if (response.startsWith("EDITCART:")) {
                System.out.println("Producto editado correctamente.");
                notify("Producto editado correctamente", lblMessage);
            } else {
                System.out.println("Error al editar el producto: " + response);
                notify("Error al editar", lblMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeProductFromCart(int idUser, String productId, Label lblMessage) {
        try {
            System.out.println("Eliminando producto con ID: " + productId);
            this.printWriter.println("REMOVECART;" + idUser + ";" + productId);

            String response = bufferedReader.readLine();
            if (response.startsWith("REMOVECART:")) {
                System.out.println("Producto eliminado correctamente.");
                notify("Producto eliminado correctamente", lblMessage);
            } else {
                System.out.println("Error al eliminar el producto: " + response);
                notify("Error al eliminar el producto", lblMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Product findProductById(String productId) {
        for (Product product : getProducts()) {
            if (product.getIdProduct().equals(productId)) {
                return product;
            }
        }
        return null;  // Si no se encuentra el producto, retorna null
    }

    public void updateProductInServer(Product product) {
        try {
            String productText = product.toString2(); // Convertimos el producto a formato de texto
            this.printWriter.println("UPDATE_PRODUCT;" + productText);

            String response = bufferedReader.readLine();
            if (response.startsWith("UPDATE_PRODUCT:")) {
                System.out.println("Producto actualizado correctamente en el servidor.");
            } else {
                System.out.println("Error al actualizar el producto: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void sendFavProduct(int clientId, FavProduct favProduct) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonFavProduct = objectMapper.writeValueAsString(favProduct);
            printWriter.println("SAVE_FAV_PRODUCTS;" + clientId + ";" + jsonFavProduct);

            System.out.println("Enviando producto favorito al servidor: " + jsonFavProduct);
        } catch (Exception e) {
            System.out.println("Error al enviar favorito: " + e.getMessage());
        }
    }


    public List<FavProduct> getFavProducts(int clientId) {
        List<FavProduct> favProducts = new ArrayList<>();
        printWriter.println("GET_FAV_PRODUCTS;" + clientId);

        try {
            String response = bufferedReader.readLine();
            if (response.startsWith("FAV_PRODUCTS_LIST;")) {
                String jsonData = response.substring(18); // Remover "FAV_PRODUCTS_LIST;"
                ObjectMapper objectMapper = new ObjectMapper();
                favProducts = objectMapper.readValue(jsonData, objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, FavProduct.class));
                
                user.setFavProducts(favProducts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return favProducts;
    }
    
    
	public void deleteFavProduct(int clientId, String productId) {
		try {
			printWriter.println("REMOVE_FAV_PRODUCT;" + clientId + ";" + productId);
			System.out.println("Enviando solicitud para eliminar producto favorito con ID: " + productId);
		} catch (Exception e) {
			System.out.println("Error al enviar solicitud para eliminar producto favorito: " + e.getMessage());
		}
	}
    
    
    public void sendComment(Product product) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // REGISTRAR EL MÓDULO PARA LocalDate
            
            String jsonProduct = objectMapper.writeValueAsString(product);
            printWriter.println("SAVE_COMMENT;" + jsonProduct);

            String response = bufferedReader.readLine();
            if (response.startsWith("SAVE_COMMENT:")) {
                System.out.println("Comentario guardado correctamente en el servidor.");
            } else {
                System.out.println("Error al guardar el comentario: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
	public void sendBill(Bill bill) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule()); // REGISTRAR EL MÓDULO PARA LocalDate

			String jsonBill = objectMapper.writeValueAsString(bill);
			printWriter.println("SAVE_BILL;" + jsonBill);

			String response = bufferedReader.readLine();
			if (response.startsWith("SAVE_BILL:")) {
				System.out.println("Factura guardada correctamente en el servidor.");
			} else {
				System.out.println("Error al guardar la factura: " + response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
    
    
    }

    

