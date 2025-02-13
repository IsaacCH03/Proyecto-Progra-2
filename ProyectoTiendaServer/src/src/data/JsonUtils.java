package data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils<T> {//clase generica
	private String filePath;//ruta del Json a procesar
	private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());//objeto para leer/escribir Json)
	
	public JsonUtils(String filePath) {
		this.filePath = filePath;
	}
	
	
	
	public void save(T t) throws Exception {
		
		List<T> list = getElements((Class<T>)t.getClass());
		list.add(t);
		mapper.writeValue(new File(this.filePath), list);
		
	}
	
	public List<T> getElements(Class<T> temp) throws Exception {//metodo para leer Json y convertirlo a una lista de objetos
		
		File file = new File(this.filePath);
		if(!file.exists()) {
			return new ArrayList<T>();
		}
		
		return mapper.readValue(file, 
				mapper.getTypeFactory()
				.constructCollectionType(List.class, temp));// 
		
		
		
	}
	
	//metodo para buscar una pelicula en la lista por el nombre
	public T findElement(String name, Class<T> temp) throws Exception {
		List<T> list = getElements(temp);
		for (T t : list) {
			if (t.toString().equals(name)) {
				return t;
			}
		}
		return null;
	}
	
	public T removeElement(String name, Class<T> temp) throws Exception {
		List<T> list = getElements(temp);
		for (T t : list) {
			if (t.toString().equals(name)) {
				list.remove(t);
				mapper.writeValue(new File(this.filePath), list);
				return t;
			}
		}
		return null;
	}
	
	
	public T readElement(String name, Class<T> temp) throws Exception {
		List<T> list = getElements(temp);
		for (T t : list) {
			if (t.toString().equals(name)) {
				return t;
			}
		}
		return null;
	}
	
	
	//metodo para guardar una lista de objetos en un archivo Json
		public void saveList(List<T> list) throws Exception {
		    mapper.writeValue(new File(this.filePath), list);
		}
	

}
