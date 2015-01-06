package editeur;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.HashMap;

/*	La classe Polices sert � encapsuler une Font et � y ajouter une Hashmap largeur
 *  qui renvoie pour tout les caract�res pr�sents dans str leur largeur respectives * 
 * 
 */
public class Polices{

    public static FontRenderContext frc = new FontRenderContext(null, true, true); // R�gler � true, true, �tait le secret... (Presque deux jours de boulot pour trouver �a) 
    final private Font font;
 

    public Polices(Font font) {
    	this.font = font; 
    }
    
    /**
	 * @param mot - Mot dont on d�sire la largeur
	 * @param cara - Tableau donnant pour chaque caract�re sa largeur
	 * @return - Renvoie la largeur du mot
	 */
	public double largeurMot(String mot){
		return (new TextLayout(mot, this.getFont(), frc)).getAdvance();
	}

    
    public Font getFont(){return this.font;}

}
