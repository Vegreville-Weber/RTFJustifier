package editeur;

import org.xml.sax.*; 
import org.xml.sax.helpers.*; 
import javax.xml.parsers.*;  
import java.io.*;  

public class XMLReader { 
 /* Deux Parsers sont disponibles : DOM et SAX. Je pars ici sur SAX car il est à priori
  *  bien moins gourmand en mémoire que DOM qui garde en mémoire tout le fichier XML*/

	public static void main(String argv[]) {
		 
		try {
			// création d'une fabrique de parseurs SAX 
			SAXParserFactory fabrique = SAXParserFactory.newInstance(); 

			// création d'un parseur SAX 
			SAXParser parseur = fabrique.newSAXParser(); 
			File fichier = new File("./exempleXML.xml"); 
			DefaultHandler gestionnaire = new DefaultHandler(); 
			parseur.parse(fichier, gestionnaire); 
		/*	Gestion des différentes erreurs susceptibles de survenir.
		*/
		} catch(ParserConfigurationException pce){ 
			System.out.println("Erreur de configuration du parseur"); 
			System.out.println("Lors de l'appel à newSAXParser()"); 
		}catch(SAXException se){ 
			System.out.println("Erreur de parsing"); 
			System.out.println("Lors de l'appel à parse()"); 
		}catch(IOException ioe){ 
			System.out.println("Erreur d'entrée/sortie"); 
			System.out.println("Lors de l'appel à parse()"); 
		} 

	}

}
