package domain;

public class User {
	
	private String fullName;
	private int id;
	private String mail;
	private String password;
	private String address;
	private int phone;
	
	public User(String fullName, int id, String mail, String password, String address, int phone) {
		this.fullName = fullName;
		this.id = id;
		this.mail = mail;
		this.password = password;
		this.address = address;
		this.phone = phone;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return fullName + ";" + id + ";" + mail + ";" + password + ";"
				+ address + ";" + phone;
	}
	
	

}
