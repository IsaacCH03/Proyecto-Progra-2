package business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import domain.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ClientFunction {
    private String host;
    private int port;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private User user;

    public ClientFunction(String host, int port) {
        this.host = host;
        this.port = port;
        initClient();
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
        handleServerResponse(lblMessage);
    }

    public void login(String mail, String password, Label lblMessage) {
        this.printWriter.println("LOGIN;" + mail + ";" + password);
        handleServerResponse(lblMessage);
    }

    private void handleServerResponse(Label lblMessage) {
        new Thread(() -> {
            try {
                String response = bufferedReader.readLine();
                if (response.startsWith("SUCCESS:")) {
                	notify("Inicio de sesion exitoso", lblMessage);
                    String[] userData = response.substring(8).split(";"); 
                    try {
                        int userId = Integer.parseInt(userData[1]);  // Convertir ID a entero
                        int userPhone = Integer.parseInt(userData[5]);  // Convertir Tel�fono a entero
                        this.user = new User(userData[0], userId, userData[2], userData[3], userData[4], userPhone);
                        System.out.println("Usuario autenticado: " + user.toString());
                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir ID o Tel�fono: " + e.getMessage());
                    }

                    } else {
                    	notify(response, lblMessage);
                        System.out.println("Error al iniciar sesi�n: " + response);
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
}
