package editeur;

import java.awt.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.xml.parsers.SAXParserFactory;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class JDOMXMLReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
	
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new File("TwoParagraphTest.xml"));
		
		Collection<Element> blocs = document.getRootElement() //renvoie tout les paragraphes
				.getChild("body",Namespace.getNamespace("http://schemas.microsoft.com/office/word/2003/wordml"))
				.getChildren("p",Namespace.getNamespace("http://schemas.microsoft.com/office/word/2003/wordml"));
				
		Collection<Element> paragraphes = new LinkedList<Element>();
		//afficher(document);
		for(Element e : blocs){
			paragraphes.add(e.getChild("r",Namespace.getNamespace("http://schemas.microsoft.com/office/word/2003/wordml"))
				.getChild("t",Namespace.getNamespace("http://schemas.microsoft.com/office/word/2003/wordml")));
		}
		
		for(Element e : paragraphes){
			e.addContent("tets"); //test d'ajout de texte à chaque paragraphe.
			System.out.println(e.getText());
		}		
		afficher(document);
		} //gestion des erreurs.
		catch (JDOMException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	protected static void afficher(Document document)
	{
	try
	{
	XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	//sortie.output(document, System.out); // décommenter pour voir le fichier xml dans la console Eclipse
	sortie.output(document, new FileOutputStream(new File("resultat.xml"))); //décommenter pour écrire le résultat dans resultat.xml
	}
	catch (java.io.IOException e){}
	}

}
