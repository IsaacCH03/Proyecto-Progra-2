package domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client {
    private String fullName;
    private String id;
    private String email;
    private String passwordHash;
    private String address;
    private String phone;
    
    
    public Client() {
    	
    }

    public Client(String fullName, String id, String email, String password, String address, String phone) {
        this.fullName = fullName;
        this.id = id;
        this.email = email;
        this.passwordHash = hashPassword(password);
        this.address = address;
        this.phone = phone;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean validatePassword(String inputPassword) {
        return this.passwordHash.equals(hashPassword(inputPassword));
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    
    

    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
    public String toString() {
        return email; // Se usa email como identificador único
    }
	
	public String toString2() {
	    return fullName + ";" + id + ";" + email + ";" + passwordHash + ";" + address + ";" + phone;
	}
}
