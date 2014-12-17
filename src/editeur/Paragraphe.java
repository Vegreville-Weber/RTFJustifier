package editeur;

import java.awt.Font;

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
