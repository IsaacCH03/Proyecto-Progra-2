package data;

import java.util.List;
import domain.Client;

public class DataClient {
    private final String fileName = "clients.json";
    private final JsonUtils<Client> jsonUtils = new JsonUtils<>(fileName);

    public List<Client> getList() {
        try {
            return jsonUtils.getElements(Client.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public void save(Client client) {
        try {
            if (find(client.getEmail()) == null) {
                jsonUtils.save(client);
                System.out.println("Usuario guardado correctamente.");
            } else {
                System.out.println("ERROR: El usuario ya existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(String email) {
        try {
            jsonUtils.removeElement(email, Client.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client find(String email) {
        try {
            List<Client> clients = getList();
            for (Client client : clients) {
                if (client.getEmail().equals(email)) {
                    return client;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean authenticate(String email, String password) {
        Client client = find(email);
        return client != null && client.validatePassword(password);
    }
    public void update(Client updatedClient) {
        try {
            List<Client> clients = getList(); // Cargar todos los clientes
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getEmail().equals(updatedClient.getEmail())) {
                    clients.set(i, updatedClient); // Reemplazar el cliente con los nuevos datos
                    jsonUtils.saveList(clients); // Guardar la lista actualizada en el JSON
                    System.out.println("Usuario actualizado correctamente.");
                    return;
                }
            }
            System.out.println("ERROR: Usuario no encontrado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
