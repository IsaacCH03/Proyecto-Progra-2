package business;

import java.io.*;
import java.net.Socket;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.DataClient;
import data.DataProduct;
import domain.Client;
import domain.Product;

public class ClientHandler implements Runnable {
	private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private LaunchServer server;
    private DataClient dataClient;

    public ClientHandler(Socket socket, LaunchServer server) {
        this.clientSocket = socket;
        this.server = server;
        this.dataClient = new DataClient();
        try {
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error configurando el cliente: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) { // Reemplazo de readUTF()
                System.out.println("Mensaje recibido: " + message);

                String[] parts = message.split(";");
                String command = parts[0];

                switch (command) {
                    case "REGISTER":
                        handleRegister(parts);
                        break;
                    case "LOGIN":
                        handleLogin(parts);
                        break;
                        
                    case "UPDATE_PROFILE":
                        handleUpdateProfile(parts);
                        break;
                        
                    case "GET_PRODUCTS":// Agregar case para el comando GET_PRODUCTS
                        handleGetProducts();
                        break;
                        
                    default:
                        out.println("ERROR: Comando no reconocido");
                }
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado: " + clientSocket.getInetAddress());
        } finally {
            server.removeClient(clientSocket);
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error cerrando socket: " + e.getMessage());
            }
        }
    }
    
    public void sendMessage(String message) {
        try {
            out.println(message);
        } catch (Exception e) {
            System.out.println("Error enviando mensaje al cliente: " + e.getMessage());
        }
    }


    private void handleRegister(String[] parts) {
        try {
            if (parts.length < 7) {
                out.println("ERROR: Datos incompletos");
                return;
            }

            String fullName = parts[1];
            String id = parts[2];
            String email = parts[3];
            String password = parts[4];
            String address = parts[5];
            String phone = parts[6];

            if (dataClient.find(email) != null) {
                out.println("ERROR: Usuario ya registrado");
                return;
            }

            Client newClient = new Client(fullName, id, email, password, address, phone);
            dataClient.save(newClient);
            out.println("REGISTER: Usuario registrado correctamente");
        } catch (Exception e) {
            out.println("REGISTERERROR: No se pudo registrar el usuario");
        }
    }
    
    private void handleLogin(String[] parts) {
    	try {
    		 if (parts.length < 3) {
                 out.println("ERROR: Datos incompletos");
                 return;
                }
    		 
    		 String email = parts[1];
    		 String password = parts[2];
    		 
    		 Client client = dataClient.find(email);
    		 client.setPass(password);
    		 if (client != null && client.validatePassword(password)) {
    			    out.println("SUCCESS:" + client.toString2()); // Enviar el objeto serializado
    			} else {
    			    out.println("ERROR: Credenciales incorrectas");
    			}

		} catch (Exception e) {
			out.println("ERROR: No se pudo autenticar el usuario");
		}
    	
    	
    	
    }
    private void handleUpdateProfile(String[] parts) {
        try {
            if (parts.length < 6) { // Verificamos que vengan todos los datos necesarios
                out.println("ERROR: Datos incompletos");
                return;
            }
            String email = parts[1];  // Email del usuario a actualizar (identificador único)
            String fullName = parts[2];
            String address = parts[3];
            String phone = parts[4];
            String newPassword = parts.length > 5 ? parts[5] : ""; // La contraseña es opcional
            Client client = dataClient.find(email); // Buscar usuario en JSON
            if (client == null) {
                out.println("ERROR: Usuario no encontrado");
                return;
            }
        
            
            Client temp = new Client(fullName, client.getId(), client.getEmail(), newPassword, address, phone);
            
            dataClient.update(temp); // Guardar cambios en JSON
            out.println("SUCCESS: Perfil actualizado correctamente");
        } catch (Exception e) {
            out.println("ERROR: No se pudo actualizar el perfil");
        }
    }
    
    private void handleGetProducts() {// Obtiene la lista de productos y la envía al cliente en formato JSON
        try {
            DataProduct dataProduct = new DataProduct();
            List<Product> products = dataProduct.getList();
            String jsonResponse = new ObjectMapper().writeValueAsString(products); // Convertimos la lista a JSON
            out.println("PRODUCT_LIST;" + jsonResponse);
        } catch (Exception e) {
            out.println("ERROR: No se pudieron obtener los productos");
        }
    }
    

    
    
    

}