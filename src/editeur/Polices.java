package editeur;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/*	La classe Polices sert à encapsuler une Font et à y ajouter une Hashmap largeur
 *  qui renvoie pour tout les caractères présents dans str leur largeur respectives * 
 * 
 */
public class Polices{

    final public static FontRenderContext frc = new FontRenderContext(null, false, false);
    final private Font font;
    final private HashMap<Character,Double> largeur;
    final private static String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.?!:;%)( ,&"; // tout les caractères pris en compte pour l'instant.
    
    public static void main(String[] args) {
    	Font f = new Font("Gentium Basic", Font.PLAIN, 13);
    	Polices p = new Polices(f);
    	System.out.println(p);
    	System.out.println(f.getSize());
    	
    	System.out.println( new TextLayout("t", f, frc).getAdvance());
    	System.out.println( new TextLayout("e", f, frc).getAdvance());
    	System.out.println( new TextLayout("s", f, frc).getAdvance());
    	System.out.println( new TextLayout("test", f, frc).getAdvance());
    	System.out.println();
    	System.out.println( new TextLayout("t", f, frc).getVisibleAdvance());
    	System.out.println( new TextLayout("e", f, frc).getVisibleAdvance());
    	System.out.println( new TextLayout("s", f, frc).getVisibleAdvance());
    	int temp =0;
    	for(int i=0; i<52;i++) temp += (new TextLayout(""+str.charAt(i), f, frc)).getVisibleAdvance();
    	System.out.println();
    	System.out.println(temp);
    	System.out.println( new TextLayout("ABCDEFGHI JKLMNOPQRSTUVWXY Zabcdefghijklmno pqrstuvwxyz", f, frc).getVisibleAdvance());

	}

    public Polices(Font font) {
    	this.font = font; 
    	this.largeur = new HashMap<Character,Double>();
    	for(int k=0;k<str.length();k++){ //Remplissage de la HashMap
    		char temp = str.charAt(k);
//    		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
//    		int [] widths = fm.getWidths();
    		
    		TextLayout layout = new TextLayout(""+temp, this.font, frc);
//    		System.out.println(temp + " : advance " + layout.getAdvance() + ", visible advance " + layout.getVisibleAdvance());
    		this.largeur.put(temp, (double) layout.getAdvance());
    		if (temp ==' ') System.out.println("taille de l'espace avant modif : " + this.largeur.get(' ') + "pts"); 
    	}
//    	
//    	JLabel label = new JLabel("A");
//    	label.setFont(this.font);
//    	for(int k=0;k<str.length();k++){ //Remplissage de la HashMap
//    		String temp = ""+ str.charAt(k);
//    		//for(int i = 0; i<20; i++) // 1048576 fois le même caractère
//    		 //temp = temp+temp;
//    		//System.out.println(label.getFontMetrics(label.getFont()).stringWidth(temp));
//    		Double taille = ((double) label.getFontMetrics(label.getFont()).stringWidth(temp));
//    		this.largeur.put(str.charAt(k), taille);
//    	}
   	
//    	TextLayout layout = new TextLayout("A A", this.font, frc);
//    	this.largeur.put(' ',layout.getBounds().getWidth() - 2*this.largeur.get('A')); //ARBITRAIRE A CHANGER A TERME
//    	System.out.println("Pour la police " + this.font.getName());
//    	System.out.println("taille de l'espace : " + this.largeur.get(' ') + "pts");
//    	System.out.println("taille du A : " + this.largeur.get('A') + "pts");
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
    
    public Font getFont(){ return this.font;}

}
