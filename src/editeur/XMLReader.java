package editeur;

import org.xml.sax.*; 
import org.xml.sax.helpers.*; 
import javax.xml.parsers.*;  
import java.io.*;  

public class XMLReader { 
 /* Deux Parsers sont disponibles : DOM et SAX. Je pars ici sur SAX car il est � priori
  *  bien moins gourmand en m�moire que DOM qui garde en m�moire tout le fichier XML*/

	public static void main(String argv[]) {
		 
		try {
			// cr�ation d'une fabrique de parseurs SAX 
			SAXParserFactory fabrique = SAXParserFactory.newInstance(); 

			// cr�ation d'un parseur SAX 
			SAXParser parseur = fabrique.newSAXParser(); 
			File fichier = new File("./exempleXML.xml"); 
			DefaultHandler gestionnaire = new DefaultHandler(); 
			parseur.parse(fichier, gestionnaire); 
		/*	Gestion des diff�rentes erreurs susceptibles de survenir.
		*/
		} catch(ParserConfigurationException pce){ 
			System.out.println("Erreur de configuration du parseur"); 
			System.out.println("Lors de l'appel � newSAXParser()"); 
		}catch(SAXException se){ 
			System.out.println("Erreur de parsing"); 
			System.out.println("Lors de l'appel � parse()"); 
		}catch(IOException ioe){ 
			System.out.println("Erreur d'entr�e/sortie"); 
			System.out.println("Lors de l'appel � parse()"); 
		} 

	}

}
