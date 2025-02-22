package business;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import data.DataBill;
import data.DataCart;
import data.DataClient;
import data.DataFavProduct;
import data.DataProduct;
import domain.Bill;
import domain.Cart;
import domain.Client;
import domain.FavProduct;
import domain.Product;

public class ClientHandler implements Runnable {
	private Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;
	private LaunchServer server;
	private DataClient dataClient;
	private DataCart cartData = new DataCart();
	private DataProduct dataProduct = new DataProduct();

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

				case "GET_PRODUCTS":
					handleGetProducts();
					break;

				case "SAVECART":
					handleSaveCart(parts);
					break;
				case "EDITCART":
					handleEditCart(parts);
					break;
				case "REMOVECART":
					handleRemoveCart(parts);
					break;
				case "GETCART":
					handleSetCart(parts);
					break;

				case "UPDATE_PRODUCT":
					handleUpdateProduct(parts);
					break;

				case "SAVE_FAV_PRODUCTS":
					handleSaveFavProducts(parts);
					break;

				case "GET_FAV_PRODUCTS":
					handleGetFavProducts(parts);
					break;

				case "REMOVE_FAV_PRODUCT":
					handleRemoveFavProduct(parts);
					break;

				case "SAVE_COMMENT":
					handleSaveComment(parts);
					break;

				case "SAVE_BILL":
					handleSaveBill(parts);
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
				System.out.println(client.toString2());
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
			String email = parts[1]; // Email del usuario a actualizar (identificador único)
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

	private void handleGetProducts() {
		try {
			DataProduct dataProduct = new DataProduct();
			List<Product> products = dataProduct.getList();
			System.out.println(products.toString());

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule()); // 🔹 REGISTRA EL MÓDULO PARA LocalDate

			String jsonResponse = objectMapper.writeValueAsString(products);
			System.out.println(jsonResponse);
			out.println("PRODUCT_LIST;" + jsonResponse);
		} catch (Exception e) {
			out.println("ERROR: No se pudieron obtener los productos");
			e.printStackTrace();
		}
	}

	private void handleSaveCart(String[] parts) {
		try {
			int idUser = Integer.parseInt(parts[1]);
			String[] productData = parts[2].split(",");

			if (productData.length < 7) {
				out.println("ERROR: Formato de producto incorrecto");
				return;
			}

			String idProduct = productData[0];
			String name = productData[1];
			String description = productData[2];
			double price = Double.parseDouble(productData[3]);
			int amount = Integer.parseInt(productData[4]);
			String category = productData[5];
			String urlImage = productData[6];

			Product product = new Product(idProduct, name, description, price, amount, category, urlImage);
			Cart cart = new Cart(idUser, product);

			if (cartData.save(cart)) {
				out.println("SAVECART: Producto agregado");
			} else {
				out.println("SAVECARTERROR: Producto no agregado");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: No se pudo guardar el carrito");
		}
	}

	private void handleEditCart(String[] parts) {
		try {
			int idUser = Integer.parseInt(parts[1]);
			String[] productData = parts[2].split(",");

			if (productData.length < 7) {
				out.println("ERROR: Formato de producto incorrecto");
				return;
			}

			String idProduct = productData[0];
			String name = productData[1];
			String description = productData[2];
			double price = Double.parseDouble(productData[3]);
			int amount = Integer.parseInt(productData[4]);
			String category = productData[5];
			String urlImage = productData[6];

			Product product = new Product(idProduct, name, description, price, amount, category, urlImage);

			if (cartData.updateCart(idUser, product)) {
				out.println("EDITCART: Producto actualizado");
			} else {
				out.println("EDITCARTERROR: Producto no encontrado");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: No se pudo editar el carrito");
		}
	}

	private void handleRemoveCart(String[] parts) {
		try {
			int idUser = Integer.parseInt(parts[1]);
			String productId = parts[2];

			if (cartData.removeFromCart(idUser, productId)) {
				out.println("REMOVECART: Producto eliminado");
			} else {
				out.println("REMOVECARTERROR: Producto no encontrado");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: No se pudo eliminar el producto del carrito");
		}
	}

	private void handleSetCart(String[] parts) {
		try {
			int idUser = Integer.parseInt(parts[1]);
			ArrayList<Product> listP = cartData.getCart(idUser);

			if (!listP.isEmpty()) {
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonResponse = objectMapper.writeValueAsString(listP);
				out.println("CART_LIST;" + jsonResponse);
			} else {
				out.println("ERROR: El carrito está vacío o no se encontró.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			out.println("ERROR: No se pudo recuperar el carrito");
		}
	}

	private void handleUpdateProduct(String[] parts) {
		try {
			String[] productData = parts[1].split(",");

			if (productData.length < 7) {
				out.println("ERROR: Formato de producto incorrecto");
				return;
			}

			String idProduct = productData[0];
			String name = productData[1];
			String description = productData[2];
			double price = Double.parseDouble(productData[3]);
			int amount = Integer.parseInt(productData[4]);
			String category = productData[5];
			String urlImage = productData[6];

			Product existingProduct = dataProduct.searchProduct(idProduct);
			if (existingProduct != null) {
				// existingProduct.setStock(amount); // Actualiza el stock
				int stock = existingProduct.getStock();
				Product product = new Product(name, description, price, amount, category, urlImage);
				product.setIdProduct(idProduct);
				dataProduct.save(product, existingProduct);
				out.println("UPDATE_PRODUCT: Producto actualizado correctamente");
			} else {
				out.println("ERROR: Producto no encontrado en la base de datos");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: No se pudo actualizar el producto en el servidor");
		}
	}

	private void handleSaveFavProducts(String[] parts) {
		try {
			if (parts.length < 3) {
				out.println("ERROR: Datos incompletos");
				return;
			}

			int clientId = Integer.parseInt(parts[1]);
			String jsonFavProduct = parts[2];

			System.out.println("Recibiendo favorito del cliente ID: " + clientId);
			System.out.println("JSON recibido: " + jsonFavProduct);

			ObjectMapper objectMapper = new ObjectMapper();
			FavProduct favProduct = objectMapper.readValue(jsonFavProduct, FavProduct.class);

			DataFavProduct dataFavProduct = new DataFavProduct();
			dataFavProduct.saveFavProduct(clientId, favProduct);

			System.out.println("Favorito guardado en el servidor: " + favProduct);
			out.println("SUCCESS: Producto favorito guardado");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: No se pudo guardar el favorito");
		}
	}

	private void handleGetFavProducts(String[] parts) {
		try {
			if (parts.length < 2) {
				out.println("ERROR: Datos incompletos");
				return;
			}

			int clientId = Integer.parseInt(parts[1]);
			DataFavProduct dataFavProduct = new DataFavProduct();
			List<FavProduct> favProducts = dataFavProduct.getFavProductsByClientId(clientId);

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(favProducts);
			out.println("FAV_PRODUCTS_LIST;" + jsonResponse);
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: No se pudieron recuperar favoritos");
		}
	}

	private void handleSaveComment(String[] parts) {
		try {
			if (parts.length < 2) {
				out.println("ERROR: Datos incompletos");
				return;
			}

			String jsonProduct = parts[1];
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule()); // REGISTRAR EL MÓDULO PARA LocalDate

			Product updatedProduct = objectMapper.readValue(jsonProduct, Product.class);

			// Buscar el producto en la base de datos
			Product existingProduct = dataProduct.searchProduct(updatedProduct.getIdProduct());
			if (existingProduct != null) {
				// existingProduct.setListComments(updatedProduct.getListComments()); //
				// Actualiza los comentarios
				dataProduct.save(updatedProduct, existingProduct); // Guardar el producto con los comentarios
																	// actualizados
				out.println("SAVE_COMMENT: Comentario guardado correctamente");
			} else {
				out.println("ERROR: Producto no encontrado en la base de datos");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: No se pudo guardar el comentario");
		}
	}

	private void handleSaveBill(String[] parts) {
		try {
			if (parts.length < 2) {
				out.println("ERROR: Datos incompletos");
				return;
			}

			String jsonBill = parts[1];
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule()); // REGISTRAR EL MÓDULO PARA LocalDate

			Bill bill = objectMapper.readValue(jsonBill, Bill.class);
			DataBill dataBill = new DataBill();
			dataBill.save(bill);

			out.println("SAVE_BILL: Factura guardada correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: No se pudo guardar la factura");
		}
	}
	
	

	private void handleRemoveFavProduct(String[] parts) {
		try {
			if (parts.length < 3) {
				out.println("ERROR: Datos incompletos");
				return;
			}

			int clientId = Integer.parseInt(parts[1]);
			String productId = parts[2];
			DataFavProduct dataFavProduct = new DataFavProduct();
			dataFavProduct.removeFavProduct(clientId, productId);

			out.println("REMOVE_FAV_PRODUCT: Producto eliminado de favoritos");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: No se pudo eliminar el producto de favoritos");
		}
	}
	
	

}