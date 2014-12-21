package editeur;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
/*	La classe Polices sert à encapsuler une Font et à y ajouter une Hashmap largeur
 *  qui renvoie pour tout les caractères présents dans str leur largeur respectives * 
 * 
 */
public class Polices{

    final private static FontRenderContext frc = new FontRenderContext(null, false, false);
    final private Font font;
    final private HashMap<Character,Double> largeur;
    final private String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.?!:;%)( ,&"; // tout les caractères pris en compte pour l'instant.
    
    public static void main(String[] args) {
    	Font f = new Font("Liberation Sans Serif", Font.PLAIN, 12);
    	Polices p = new Polices(f);
    	System.out.println(p);
    	System.out.println(f.getSize());
    	

	}

    public Polices(Font font) {
    	this.font = font; 
    	this.largeur = new HashMap<Character,Double>();
    	for(int k=0;k<str.length();k++){ //Remplissage de la HashMap
    		char temp = str.charAt(k);
    		TextLayout layout = new TextLayout(""+temp, this.font, frc);
    		this.largeur.put(temp, layout.getBounds().getWidth());
    	}
    	this.largeur.put(' ',this.largeur.get('A')); //ARBITRAIRE A CHANGER A TERME
   
    }

    public HashMap<Character,Double> getLargeurs(){
    	return this.largeur;
    }

    public String toString() {
    	String temp ="";
    	for(int k =0;k<str.length();k++){
    		char tmp = str.charAt(k);
    		temp+= tmp+" : " +largeur.get(tmp)+"\n";
    	}
    	return temp;    	
    }

}
