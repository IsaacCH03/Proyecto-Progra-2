package business;

import java.util.List;

import domain.Client;

public class LogicClient {
	
	public static boolean isSetClientIntoList(List<Client> list, Client client) {
		for (Client c : list) {
			if (c.equals(client)) {
				return true;
			}
		}
		return false;
	}

}
