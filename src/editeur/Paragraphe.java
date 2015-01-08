package editeur;

import java.awt.Font;
/* La classe Paragraphe est étroitement lié à la classe RTFReader.java et sert à ce dernier à sauvegarder en plus du texte du paragraphe
 * la police utilisée et l'indice de referencement de cette police dans le document* 
 * 
 * 
 */
public class Paragraphe {
	String texte;
	Font font;
	int fontnum;
	
	
	public Paragraphe(String texte,Font f,int fontnum){
		this.texte=texte;
		this.font= f;
		this.fontnum=fontnum;
	}
	
	public int getFontSize(){return this.font.getSize();}
	
	
}
