package editeur;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.HashMap;

/*	La classe Polices sert à encapsuler une Font et à y ajouter une Hashmap largeur
 *  qui renvoie pour tout les caractères présents dans str leur largeur respectives * 
 * 
 */
public class Polices{

    public static FontRenderContext frc = new FontRenderContext(null, true, true); // Régler à true, true, était le secret... (Presque deux jours de boulot pour trouver ça) 
    final private Font font;
 

    public Polices(Font font) {
    	this.font = font; 
    }
    
  
	/** Renvoie la largeur du mot dans la police considée.
	 * @param mot
	 * @return
	 */
	public double largeurMot(String mot){
		return (new TextLayout(mot, this.getFont(), frc)).getAdvance();
	}

    
    public Font getFont(){return this.font;}

}
