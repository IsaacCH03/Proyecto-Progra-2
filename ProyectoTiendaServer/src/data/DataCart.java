package data;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import domain.Cart;
import domain.Product;

public class DataCart {
    private final String fileName = "cart.json";
    private final JsonUtils<Cart> jsonUtils = new JsonUtils<>(fileName);

    public List<Cart> getList() {
        try {
            return jsonUtils.getElements(Cart.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public boolean save(Cart newCart) {
        try {
            List<Cart> carts = getList();
            carts.add(newCart);
            jsonUtils.saveList(carts);

            // Verificar la ruta donde se está guardando
            File file = new File(fileName);
            System.out.println("Carrito guardado en: " + file.getAbsolutePath());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al guardar el carrito en JSON.");
        }
        return false;
    }

    public boolean updateCart(int userId, Product updatedProduct) {
        try {
            List<Cart> carts = getList();
            boolean found = false;

            for (Cart cart : carts) {
                if (cart.getIdUser() == userId && cart.getProduct().getIdProduct().equals(updatedProduct.getIdProduct())) {
                    cart.setProduct(updatedProduct);
                    found = true;
                    break;
                }
            }

            if (found) {
                jsonUtils.saveList(carts);
                System.out.println("Producto en carrito actualizado correctamente.");
                return true;
            } else {
                System.out.println("Producto no encontrado en el carrito.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el carrito.");
        }
        return false;
    }

    public ArrayList<Product> getCart(int idUser) {
        ArrayList<Product> list = new ArrayList<>();
        for (Cart myCart : getList()) {
            if (myCart.getIdUser() == idUser) {
                list.add(myCart.getProduct());
            }
        }
        return list;
    }

    public boolean removeFromCart(int userId, String productId) {
        try {
            List<Cart> carts = getList();
            boolean removed = carts.removeIf(cart -> cart.getIdUser() == userId && cart.getProduct().getIdProduct().equals(productId));

            if (removed) {
                jsonUtils.saveList(carts);
                System.out.println("Producto eliminado del carrito.");
                return true;
            } else {
                System.out.println("Producto no encontrado en el carrito.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
