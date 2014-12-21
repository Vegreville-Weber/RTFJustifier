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
	
	public Paragraphe(String texte, String fontname, int fontsize){
		this.texte=texte;
		this.font=new Font(fontname,Font.PLAIN,fontsize);
	}
	
	public Paragraphe(String texte,Font f,int fontnum){
		this.texte=texte;
		this.font= f;
		this.fontnum=fontnum;
	}
	
	public Paragraphe(String texte){
		this.texte=texte;
		this.font = new Font("Liberation Serif",Font.PLAIN,12);
	}
	
	public String getTexte(){return this.texte;}
	public void setTexte(String newtexte){this.texte=newtexte;}
	public Font getFont(){return this.font;}
	public int getFontSize(){return this.font.getSize();}
	public void setFontNum(int fontnum){this.fontnum=fontnum;}
	
	
}
