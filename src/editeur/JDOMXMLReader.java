package editeur;

import java.awt.List;
import java.io.File;
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
		Document document = builder.build(new File("TwoParagraphUnzip/content.xml"));
		
		Collection<Element> paragraphes = document.getRootElement() //renvoie tout les paragraphes
				.getChild("body",Namespace.getNamespace("urn:oasis:names:tc:opendocument:xmlns:office:1.0"))
				.getChild("text",Namespace.getNamespace("urn:oasis:names:tc:opendocument:xmlns:office:1.0"))
				.getChildren("p",Namespace.getNamespace("urn:oasis:names:tc:opendocument:xmlns:text:1.0"));
		
		//afficher(document);
		for(Element e : paragraphes){
			System.out.println(e.getText());
		}
		
		
		
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
	sortie.output(document, System.out);
	}
	catch (java.io.IOException e){}
	}

}
