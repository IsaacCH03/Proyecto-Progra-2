package business;

import java.time.LocalDate;
import java.util.ArrayList;

import domain.Comment;
import domain.Product;

public class LogicComment {

	public static Comment getComment(ArrayList<Comment> list, int idUser) {
		for (Comment myComment : list) {
			if (myComment.getIdUser() == idUser) {
				return myComment;
			}
		}
		return null;
	}

	public static boolean changeComment(Product product, Comment newComment) {
		for (Comment commentTemp : product.getListComments()) {
			if (commentTemp.getIdUser() == newComment.getIdUser()) {
				commentTemp.setText(newComment.getText());
				commentTemp.setQualification(newComment.getQualification());
				commentTemp.setDateTime(newComment.getDateTime());
				return true;
			}
		}
		return false;
	}

	public static boolean deleteComment(Product product, int idUser) {
		for (Comment commentTemp : product.getListComments()) {
			if (commentTemp.getIdUser() == idUser) {
				product.getListComments().remove(commentTemp);
				return true;
			}
		}
		return false;
	}

	public static String filterCommentDate(Product product, LocalDate dateFilter) {
		String text = "Lista de comentarios del producto "+ product.getName() + " en  la fecha: "+dateFilter+" \n";
		for(Comment commentTemp : product.getListComments()) {
			if(commentTemp.getDateTime().equals(dateFilter)) {
				text += "Id del usuario: "+commentTemp.getIdUser() + "\n" 
			     + "Comentario: " + commentTemp.getText()+ "\n" 
				 +"Calificacion: "+ commentTemp.getQualification() + "\n"
				 +"-------------------------------------"+ "\n";
			}
		}
		return text;
	}
	public static String filterCommentValue(Product product, int value) {
		String text = "Lista de comentarios del producto "+ product.getName() + " con calificacion: "+value+" \n";
		for(Comment commentTemp : product.getListComments()) {
			if(commentTemp.getQualification() == value) {
				text += "Id del usuario: "+commentTemp.getIdUser() + "\n" 
			     + "Comentario: " + commentTemp.getText()+ "\n" 
				 +"Calificacion: "+ commentTemp.getQualification() + "\n"
				 +"-------------------------------------"+ "\n";
			}
		}
		return text;
	}
	public static String getComment(int idUser, Product product) {
		String text="";
		for(Comment commentTemp : product.getListComments()) {
			if(commentTemp.getIdUser() == idUser) {
				text += commentTemp.getText();
			}
		}
		return text;
	}

}
