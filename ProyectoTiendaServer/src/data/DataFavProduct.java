package data;

import java.util.ArrayList;
import java.util.List;

import domain.FavProduct;

public class DataFavProduct {
	private final String fileName = "favProducts.json";
	private final JsonUtils<FavProduct> jsonUtils = new JsonUtils<>(fileName);
	
	public List<FavProduct> getList() {
        try {
            return jsonUtils.getElements(FavProduct.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
	}
	
	public void saveFavProduct(int clientId, FavProduct newFav) {
	    try {
	        List<FavProduct> existingFavs = getList(); // Obtener la lista actual
	        System.out.println("Favoritos antes de guardar: " + existingFavs);

	        // Filtrar solo los favoritos del usuario
	        List<FavProduct> userFavs = new ArrayList<>();
	        for (FavProduct fav : existingFavs) {
	            if (fav.getIdClient() == clientId) {
	                userFavs.add(fav);
	            }
	        }

	        // Verificar si el producto ya está en la lista
	        boolean exists = false;
	        for (FavProduct existingFav : userFavs) {
	            if (existingFav.getProduct().getIdProduct().equals(newFav.getProduct().getIdProduct())) {
	                exists = true;
	                break;
	            }
	        }

	        // Agregar solo si no existe
	        if (!exists) {
	            existingFavs.add(newFav);
	            System.out.println("Nuevo favorito agregado: " + newFav);
	        } else {
	            System.out.println("El producto ya está en favoritos.");
	        }

	        // Guardar la lista actualizada en JSON
	        jsonUtils.saveList(existingFavs);
	        System.out.println("Favorito guardado correctamente en JSON.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}






	public List<FavProduct> getFavProductsByClientId(int clientId) {
	    List<FavProduct> favProducts = getList(); // Obtener todos los favoritos
	    List<FavProduct> clientFavProducts = new ArrayList<>();

	    System.out.println("Verificando favoritos guardados en el servidor para cliente ID: " + clientId);
	    System.out.println("Lista completa de favoritos: " + favProducts); // Imprime todos los favoritos guardados

	    for (FavProduct fav : favProducts) {
	        if (fav.getIdClient() == clientId) {
	            clientFavProducts.add(fav);
	        }
	    }

	    System.out.println("Favoritos encontrados para el cliente " + clientId + ": " + clientFavProducts);

	    return clientFavProducts;
	}
	
	public void removeFavProduct(int idClient, String idProduct ) {
		try {
			List<FavProduct> favProducts = getList(); // Obtener todos los favoritos
			List<FavProduct> updatedFavProducts = new ArrayList<>();

			for (FavProduct fav : favProducts) {
				if (fav.getIdClient() == idClient && fav.getProduct().getIdProduct().equals(idProduct)) {
					System.out.println("Eliminando favorito: " + fav);
				} else {
					updatedFavProducts.add(fav);
				}
			}

			jsonUtils.saveList(updatedFavProducts); // Guardar la lista actualizada
			System.out.println("Favorito eliminado correctamente.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		

	public void save(FavProduct favProduct) {
        try {
            if (find(favProduct.getIdClient()) == null) {
                jsonUtils.save(favProduct);
                System.out.println("Producto favorito guardado correctamente.");
            } else {
                System.out.println("ERROR: El producto favorito ya existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        }
	
	public void remove(int idClient) {
		        try {
            jsonUtils.removeElement(idClient, FavProduct.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
	
	public FavProduct find(int idClient) {
		try {
			List<FavProduct> favProducts = getList();
			for (FavProduct favProduct : favProducts) {
				if (favProduct.getIdClient() == idClient) {
					return favProduct;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void update(FavProduct updatedFavProduct) {
		try {
			List<FavProduct> favProducts = getList(); // Cargar todos los productos favoritos
			for (int i = 0; i < favProducts.size(); i++) {
				if (favProducts.get(i).getIdClient() == updatedFavProduct.getIdClient()) {
					favProducts.set(i, updatedFavProduct);
					jsonUtils.saveList(favProducts);
					System.out.println("Producto favorito actualizado correctamente.");
					return;
				}
			}
			System.out.println("ERROR: El producto favorito no existe.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Integer> getClientIdsWithProduct(String idProduct) {
		        List<FavProduct> favProducts = getList();
        List<Integer> clientIds = new ArrayList<>();

        for (FavProduct fav : favProducts) {
            if (fav.getProduct().getIdProduct().equals(idProduct)) {
                clientIds.add(fav.getIdClient());
            }
        }

        return clientIds;
    }
		
		
	
	
}

