package editeur;

import org.xml.sax.*; 
import org.xml.sax.helpers.*; 

import javax.xml.parsers.*;  

import java.io.*;  
import java.util.LinkedList;
import java.util.List;

public class XMLReader { //a pour vocation de comprendre le Parseur SAX

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// cr�ation d'une fabrique de parseurs SAX 
			SAXParserFactory fabrique = SAXParserFactory.newInstance(); 

			// cr�ation d'un parseur SAX 
			SAXParser parseur = fabrique.newSAXParser(); 
			XMLReader main = new XMLReader();
			File fichier = new File("TwoParagraphUnzip/content.xml"); 
			DefaultHandler gestionnaire = main.new TextHandler(); 
			parseur.parse(fichier, gestionnaire); 
			/*	Gestion des diff�rentes erreurs susceptibles de survenir.
			 */
		} catch(ParserConfigurationException pce){ 
			System.out.println("Erreur de configuration du parseur"); 
			System.out.println("Lors de l'appel � newSAXParser()"); 
		}catch(SAXException se){ 
			System.out.println("Erreur de parsing"); 
			System.out.println("Lors de l'appel � parse()"); 
			System.out.println(se.getMessage());
		}catch(IOException ioe){ 
			System.out.println("Erreur d'entr�e/sortie"); 
			System.out.println("Lors de l'appel � parse()"); 
		} 

	}
	
	public class Paragraphe{ 
		private int id; 
		private String paragraphe; 
	  
		public Paragraphe(){} 
		public Paragraphe(int id, String paragraphe){
			this.id=id;
			this.paragraphe=paragraphe;
		}
		public Paragraphe(int id){
			this.id=id;
		}
	  
		public int getId(){return id;} 
		public String getParagraphe(){return this.paragraphe;} 		
	  
		public void setId(int id){this.id = id;} 
		public void setParagraphe(String paragraphe){this.paragraphe = paragraphe;} 		
	  
		public String toString(){ 
			return new StringBuffer("Id : ").append(id).append(", ") 
				.append("Paragraphe : ").append(this.paragraphe)				
				.toString(); 
		} 
	}
	
	public class TextHandler extends DefaultHandler{ 
		//r�sultats de notre parsing 
		private List<Paragraphe> paragraphes; 
		private int currentID; //permet de garder en m�moire le num�ro d'apparition du paragraphe entrain d'�tre parser.
		private Paragraphe currentParagraphe;
		private boolean inParagraphe;
		private boolean inBody;
		//buffer nous permettant de r�cup�rer les donn�es  
		private StringBuffer buffer; 
	  
		// simple constructeur qui provient de DefaultHandler
		public TextHandler(){ 
			super(); //constructeur h�rit� de DefaultHandler
			this.currentID=0;
		} 
		//d�tection d'ouverture de balise 
		public void startElement(String uri, String localName, 
				String qName, Attributes attributes) throws SAXException{ 
			if(qName.equals("office:text")){ 
				this.paragraphes = new LinkedList<Paragraphe>(); 
				this.inBody =true;
				//inParagraphe = true; 
			}else if(qName.equals("text:p")){ 
				this.currentParagraphe = new Paragraphe(this.currentID++); 
				this.inParagraphe=true;
				this.buffer = new StringBuffer(); 
			}
		} 
	
		
		//d�tection fin de balise 
		public void endElement(String uri, String localName, String qName) 
				throws SAXException{ 
			if(qName.equals("office:text")){ 
				this.inBody = false; 
			}else if(qName.equals("text:p")){ 
				this.currentParagraphe.setParagraphe(this.buffer.toString());
				this.paragraphes.add(this.currentParagraphe); 
				this.currentParagraphe = null; //on remet � null la m�moire du paragraphe
				this.inParagraphe = false; 
				this.buffer=null;
			}/* else {
				// erreur, on peut lever une exception
				throw new SAXException("Balise " + qName + " inconnue.");
			}*/       
		} 
		//d�tection de caract�res 
		public void characters(char[] ch,int start, int length) 
				throws SAXException{ 
			String lecture = new String(ch,start,length); 
			if(buffer != null) buffer.append(lecture);        
		} 
		//d�but du parsing 
		public void startDocument() throws SAXException { 
			System.out.println("D�but du parsing"); 
		} 
		//fin du parsing 
		public void endDocument() throws SAXException { 
			System.out.println("Fin du parsing"); 
			System.out.println("Resultats du parsing"); 
			for(Paragraphe p : this.paragraphes){ 
				System.out.println(p); 
			} 
		} 
	}
}



