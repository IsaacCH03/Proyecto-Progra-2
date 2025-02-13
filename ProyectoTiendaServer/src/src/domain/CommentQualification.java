package domain;

import java.time.LocalDate;

public class CommentQualification {
	
	private int idCommentQualification;
	private Client idUser;
	private LocalDate dateHoursComment;
	private String comment;
	private int qualification;
	
	public CommentQualification(int idCommentQualification, Client idUser, LocalDate dateHoursComment, String comment,
			int qualification) {
		super();
		this.idCommentQualification = idCommentQualification;
		this.idUser = idUser;
		this.dateHoursComment = LocalDate.now();
		this.comment = comment;
		this.qualification = qualification;
	}

	public int getIdCommentQualification() {
		return idCommentQualification;
	}

	public void setIdCommentQualification(int idCommentQualification) {
		this.idCommentQualification = idCommentQualification;
	}

	public Client getIdUser() {
		return idUser;
	}

	public void setIdUser(Client idUser) {
		this.idUser = idUser;
	}

	public LocalDate getDateHoursComment() {
		return dateHoursComment;
	}

	public void setDateHoursComment(LocalDate dateHoursComment) {
		this.dateHoursComment = dateHoursComment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getQualification() {
		return qualification;
	}

	public void setQualification(int qualification) {
		this.qualification = qualification;
	}

	@Override
	public String toString() {
		return "CommentQualification [idCommentQualification=" + idCommentQualification + ", idUser=" + idUser
				+ ", dateHoursComment=" + dateHoursComment + ", comment=" + comment + ", qualification=" + qualification
				+ "]";
	}
	
	
	
	
	
	
	
	
	

}
