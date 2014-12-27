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
    final private HashMap<Character,Double> largeur;
    final private static String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.?!:;%)( ,&"; // tout les caract�res pris en compte pour l'instant.
    
    public static void main(String[] args) {
    	Font f = new Font("Liberation Serif", Font.PLAIN, 16);
    	Polices p = new Polices(f);
    	System.out.println(p);
    	System.out.println(f.getSize());
    	
    	/**
    	 * Tout ce qui suit sont des mesures de lignes de texte faisant toutes une largeur de page dans LibreOffice.  
    	 * Le but �tait de voir les r�glages � faire pour que Java me dise bien que toutes ces lignes ont une longueur �quivalente,
    	 * puis de voir comment cette longueur est corr�l�e � largeurBloc
    	 * Le FRC est extremement important, et il fallait lui demander d'utiliser des FractionalMetrics!
    	 */
    	
    	System.out.println("Liberation Serif 12 :");
    	f = new Font("Liberation Serif", Font.PLAIN, 12);
    	frc = new FontRenderContext(f.getTransform(), true, true);
    	System.out.println( new TextLayout("Efneceinceifneonifnsncndlcln vnicencin cvnefeclnefciencez eicnenizncencnzcec dfidneceiienneieeii", f, frc).getBounds().getWidth());
    	System.out.println( new TextLayout("edzdi,die,enceinfoncinncdoiqsnoscononcoisnconoiinconzfonnNINDONSDONSONONOIodccneicn", f, frc).getBounds().getWidth());
    	System.out.println( new TextLayout("s,zasz,siz,iz,idzid,duedudneuduednqusndudndnusnudnusnklsndksndudnzuansnsnjjncneieindeiniecni", f, frc).getBounds().getWidth());
    	System.out.println();
    	System.out.println("Liberation Serif 14 : ");
    	f = new Font("Liberation Serif", Font.PLAIN, 14);
    	frc = new FontRenderContext(f.getTransform(), true, true);
    	System.out.println( new TextLayout("cldccdle,cemcmemc,me,,ecm,o,edm,dmsmqd,sqf,edeirfijzefoebfennuefuneonfofnedcl", f, frc).getBounds().getWidth());
    	System.out.println( new TextLayout("Aejejdjd endiediexiedidneid dendeidndeiniednidniede ediedien,iefnfienfnfnen en deie", f, frc).getBounds().getWidth());
    	
    	System.out.println();
    	System.out.println("Liberation Serif 16 : ");
    	f = new Font("Liberation Serif", Font.PLAIN, 16);
    	frc = new FontRenderContext(f.getTransform(), true, true);
    	System.out.println( new TextLayout("dd,ezdeiznfizennienidnendzidnzoieodndneiqoinqonxsonqoniqsxiqsiiqxiqiii", f, frc).getBounds().getWidth());
    	System.out.println( new TextLayout("A dkdkdz A d,edd,ozddoz,A odd,d,dozO W dzdizd,zdid,zod,zozdd iiizdzidi", f, frc).getBounds().getWidth());
    	System.out.println( new TextLayout("A S iiiiAI Kii,doe iiS Nde, S N N, K N I, N N, I I id sis, e de ie de de e, sns", f, frc).getBounds().getWidth());
    	
    	System.out.println();
    	System.out.println("Courier 10 Pitch 12 : ");
    	f = new Font("Courier 10 Pitch", Font.PLAIN, 12);
    	frc = new FontRenderContext(f.getTransform(), true, true);
    	System.out.println( new TextLayout("An,idxzidz,zx,zx,zozkszokzoszdcnendoznodozinnsdnskxnsxknnAIAISNINI", f, frc).getBounds().getWidth());
    	
    	System.out.println();
    	System.out.println("Courier 10 Pitch 16 : ");
    	f = new Font("Courier 10 Pitch", Font.PLAIN, 16);
    	frc = new FontRenderContext(f.getTransform(), true, true);

    	System.out.println( new TextLayout("c,ec,sc,smls,,coezoe,m,c,cqm,cm,dsmc,cmssoc,csocso", f, frc).getBounds().getWidth());

	}

    public Polices(Font font) {
    	this.font = font; 
    	this.largeur = new HashMap<Character,Double>();
    	frc = new FontRenderContext(font.getTransform(), true, true);
    	for(int k=0;k<str.length();k++){ //Remplissage de la HashMap
    		char temp = str.charAt(k);
    		TextLayout layout = new TextLayout(""+temp, this.font, frc);
    		this.largeur.put(temp, (double) layout.getAdvance());
    	}
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
    
    public Font getFont(){return this.font;}

}
