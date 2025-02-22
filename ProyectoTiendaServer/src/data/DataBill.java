package data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import domain.Bill;

public class DataBill {
	private final String fileName = "bills.json";
	private final JsonUtils<Bill> jsonUtils = new JsonUtils<>(fileName);
	
	public List<Bill> getList() {
        try {
            return jsonUtils.getElements(Bill.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
	}
	
	public boolean save(Bill newBill) {
		try {
			List<Bill> bills = getList();
			bills.add(newBill);
			jsonUtils.saveList(bills);

			// Verificar la ruta donde se está guardando
			File file = new File(fileName);
			System.out.println("Factura guardada en: " + file.getAbsolutePath());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al guardar la factura en JSON.");
		}
		return false;
	}
	
	public boolean updateBill(int billId, Bill updatedBill) {
		try {
			List<Bill> bills = getList();
			boolean found = false;

			for (Bill bill : bills) {
				if (bill.getIdBill() == billId) {
					bill = updatedBill;
					found = true;
					break;
				}
			}

			if (found) {
				jsonUtils.saveList(bills);
				System.out.println("Factura actualizada correctamente.");
				return true;
			} else {
				System.out.println("Factura no encontrada.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al actualizar la factura.");
		}
		return false;
	}
	
	public ArrayList<Bill> getBillsByUser(int userId) {
		List<Bill> bills = getList();
		ArrayList<Bill> billsByUser = new ArrayList<>();

		for (Bill bill : bills) {
			if (bill.getIdBill() == userId) {
				billsByUser.add(bill);
			}
		}

		return billsByUser;
	}

	public void deleteBill(int idBill) {
		try {
			List<Bill> bills = getList();// Se obtiene la lista de facturas
			Bill billToDelete = null;// Se inicializa la factura a eliminar
			for (Bill bill : bills) {// Se recorre la lista de facturas
				if (bill.getIdBill() == idBill) {// Si se encuentra la factura a eliminar
					billToDelete = bill;
					break;
				}
			}

			if (billToDelete != null) {// Si se encontró la factura a eliminar
				bills.remove(billToDelete);// Se elimina la factura de la lista
				jsonUtils.saveList(bills);// Se guarda la lista actualizada
				System.out.println("Factura eliminada correctamente.");
			} else {
				System.out.println("Factura no encontrada.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al eliminar la factura.");
		}
		
	}
	
	
	

}
