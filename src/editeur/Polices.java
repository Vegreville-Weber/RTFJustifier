package editeur;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

    public static FontRenderContext frc = new FontRenderContext(null, true, true);
    final private Font font;
    final private HashMap<Character,Double> largeur;
    final private static String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.?!:;%)( ,&"; // tout les caractères pris en compte pour l'instant.
    
    public static void main(String[] args) {
    	Font f = new Font("Liberation Serif", Font.PLAIN, 16);
    	Polices p = new Polices(f);
    	System.out.println(p);
    	System.out.println(f.getSize());
    	
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
    	
    	
//    	JFrame frame = new JFrame();
//    	frame.setTitle("RTF Justifier"); //On donne un titre Ã  l'application
//		frame.setSize(600,400); //On donne une taille Ã  notre fenÃªtre
//		frame.setLocationRelativeTo(null); //On centre la fenÃªtre sur l'Ã©cran
//		frame.setResizable(false); //On interdit la redimensionnement de la fenÃªtre
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit Ã  l'application de se fermer lors du clic sur la croix
//		frame.setUndecorated(true);
//		frame.setAlwaysOnTop(true);
//		frame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
//		JPanel panel = new JPanel();
//    	JLabel label=new JLabel("c,ec,sc,smls,,coezoe,m,c,cqm,cm,dsmc,cmssoc,csocso");
//    	label.setFont(f);
//    	//((Graphics2D) panel.getGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//    	label.setOpaque(false);
//    	panel.setOpaque(false);
//    	panel.add(label);
//    	frame.setContentPane(panel);
//    	frame.setVisible(true);

	}

    public Polices(Font font) {
    	this.font = font; 
    	this.largeur = new HashMap<Character,Double>();
    	frc = new FontRenderContext(font.getTransform(), true, true);
    	for(int k=0;k<str.length();k++){ //Remplissage de la HashMap
    		char temp = str.charAt(k);
//    		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
//    		int [] widths = fm.getWidths();
    		
    		TextLayout layout = new TextLayout(""+temp, this.font, frc);
    		TextLayout layout2 = new TextLayout(""+temp+temp, this.font, frc);
//    		System.out.println(temp + " : advance " + layout.getAdvance() + ", visible advance " + layout.getVisibleAdvance());
    		System.out.print(temp+ " : ");
    		System.out.print(layout.getBounds().getWidth()+ " ");
    		System.out.print(layout2.getBounds().getWidth()+ " ");
    		System.out.println(layout2.getBounds().getWidth()-layout.getBounds().getWidth());
    		this.largeur.put(temp, (double) layout2.getBounds().getWidth()-layout.getBounds().getWidth());
    		if (temp ==' ') System.out.println("taille de l'espace avant modif : " + this.largeur.get(' ') + "pts"); 
    	}
    	TextLayout layout = new TextLayout("A", this.font, frc);
    	TextLayout layout2 = new TextLayout("A A", this.font, frc);
    	this.largeur.put(' ',layout2.getBounds().getWidth() - layout.getBounds().getWidth()-this.largeur.get('A')); //ARBITRAIRE A CHANGER A TERME
    	System.out.println("taille de l'espace : " + this.largeur.get(' ') + "pts");
    	
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
