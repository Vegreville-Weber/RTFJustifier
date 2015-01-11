package editeur;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.HashMap;

/*	La classe Polices sert � encapsuler une Font et � calculer 
 * 	la taille d'une chaine de caract�re avec celle-ci.
 */
public class Polices{

    public static FontRenderContext frc = new FontRenderContext(null, true, true); // R�gler � true, true, �tait le secret... (Presque deux jours de boulot pour trouver �a) 
    final private Font font;
 

    public Polices(Font font) {
    	this.font = font; 
    }
    
  
	/** Renvoie la largeur du mot dans la police consid�r�e.
	 * @param mot
	 * @return
	 */
	public double largeurMot(String mot){
		return (new TextLayout(mot, this.getFont(), frc)).getAdvance();
	}

    
    public Font getFont(){return this.font;}

}
