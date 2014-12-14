package editeur;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class Polices{

    final private static FontRenderContext frc = new FontRenderContext(null, false, false);
    final private Font font;
    final private double[] largeur;
    final private String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.?!:;%)( ,&";
    
    public static void main(String[] args) {
    	Font f = new Font("SansSerif", Font.PLAIN, 70);
    	Polices p = new Polices(f);
    	System.out.println(p);

	}

    public Polices(Font font) {
    	this.font = font; 
    	this.largeur = new double[Character.MAX_VALUE];
    	for(int k=0;k<str.length();k++){
    		char temp = str.charAt(k);
    		TextLayout layout = new TextLayout(""+temp, this.font, frc);
    		this.largeur[temp]=layout.getBounds().getWidth();
    	}
    	this.largeur[' ']=this.largeur['A']; //ARBITRAIRE A CHANGER A TERME
    }

    public double[] getLargeurs(){
    	return this.largeur;
    }

    public String toString() {
    	String temp ="";
    	for(int k =0;k<str.length();k++){
    		char tmp = str.charAt(k);
    		temp+= tmp+" : " +largeur[tmp]+"\n";
    	}
    	return temp;    	
    }

}
