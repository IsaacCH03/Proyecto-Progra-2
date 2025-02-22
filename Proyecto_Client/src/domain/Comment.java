package domain;

import java.time.LocalDate;
import java.util.UUID;

public class Comment {
    
    private String id;  // Cambiar int a String para manejar UUID
    private int idUser;
    private LocalDate dateTime;
    private String text;
    private int qualification;
    
    public Comment(int idUser, LocalDate dateTime, String text, int qualification) {
        this.id = generateId();
        this.idUser = idUser;
        this.dateTime = dateTime;
        this.text = text;
        this.qualification = qualification;
    }
    public Comment() {}

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQualification() {
        return qualification;
    }

    public void setQualification(int qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return "Comment [id=" + id + ", idUser=" + idUser + ", dateTime=" + dateTime + ", text=" + text
                + ", qualification=" + qualification + "]";
    }
}
