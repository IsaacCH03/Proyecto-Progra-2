package business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import domain.Product;
import domain.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientFunction {
    private String host;
    private int port;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private User user;
    private GUIMarketController GUIMarketController;

    public ClientFunction(String host, int port) {
        this.host = host;
        this.port = port;
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
                    String[] userData = response.substring(8).split(";"); 
                    try {
                        int userId = Integer.parseInt(userData[1]);  // Convertir ID a entero
                        int userPhone = Integer.parseInt(userData[5]);  // Convertir Teléfono a entero
                        this.user = new User(userData[0], userId, userData[2], userData[3], userData[4], userPhone);
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
	public List<Product> getProducts() {//metodo para obtener productos del servidor 
	    List<Product> products = new ArrayList<>();
	    printWriter.println("GET_PRODUCTS");

	    try {
	        String response = bufferedReader.readLine();
	        if (response.startsWith("PRODUCT_LIST;")) {
	            String jsonData = response.substring(13); // Removemos "PRODUCT_LIST;"
	            ObjectMapper objectMapper = new ObjectMapper();//se crea un objeto de la clase ObjectMapper para convertir el json a objetos
	            products = objectMapper.readValue(jsonData, 
	                        objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
	        } else {
	            System.out.println("Error al recibir la lista de productos");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return products;//retorna la lista de productos
	}
	
	// esto para hacer que el catologo se cargue en tiempo real pero no esta implementado 
//	private void listenForUpdates() {
//	    new Thread(() -> {
//	        try {
//	            while (true) {
//	                String message = bufferedReader.readLine();
//	                if (message.startsWith("NEW_PRODUCT;")) {
//	                    String jsonData = message.substring(12);
//	                    ObjectMapper objectMapper = new ObjectMapper();
//	                    Product newProduct = objectMapper.readValue(jsonData, Product.class);
//	                    
//	                    Platform.runLater(() -> {
//	                        System.out.println("Nuevo producto recibido: " + newProduct.getName());
//	                        GUIMarketController.addNewProductToCatalog(newProduct);
//	                    });
//	                }
//	            }
//	        } catch (IOException e) {
//	            System.out.println("Error escuchando actualizaciones: " + e.getMessage());
//	        }
//	    }).start();
//	}


	
}