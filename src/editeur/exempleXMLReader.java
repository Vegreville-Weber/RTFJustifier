package editeur;

import org.xml.sax.*; 
import org.xml.sax.helpers.*; 

import javax.xml.parsers.*;  

import java.io.*;  
import java.util.LinkedList;
import java.util.List;

public class exempleXMLReader { //a pour vocation de comprendre le Parseur SAX

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// création d'une fabrique de parseurs SAX 
			SAXParserFactory fabrique = SAXParserFactory.newInstance(); 

			// création d'un parseur SAX 
			SAXParser parseur = fabrique.newSAXParser(); 
			exempleXMLReader main = new exempleXMLReader();
			File fichier = new File("./exempleXML.xml"); 
			DefaultHandler gestionnaire = main.new PersonneHandler(); 
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
	
	public class Personne{ 
		private int id; 
		private String nom, prenom, adresse,information; 
	  
		public Personne(){} 
	  
		public int getId(){return id;} 
		public String getNom(){return nom;} 
		public String getPrenom(){return prenom;} 
		public String getAdresse(){return adresse;} 
		public String getInformation(){return information;}
	  
		public void setId(int id){this.id = id;} 
		public void setNom(String nom){this.nom = nom;} 
		public void setPrenom(String prenom){this.prenom = prenom;} 
		public void setAdresse(String adresse){this.adresse = adresse;} 
		public void setInformation(String information){this.information = information;} 
	  
		public String toString(){ 
			return new StringBuffer("Nom : ").append(nom).append(", ") 
				.append("Prenom : ").append(prenom).append(", ") 
				.append("Adresse : ").append(adresse).append(", ")
				.append("Information : ").append(information)
				.toString(); 
		} 
	}
	
	public class PersonneHandler extends DefaultHandler{ 
		//résultats de notre parsing 
		private List<Personne> annuaire; 
		private Personne personne; //permet de garder en mémoire la personne en train de se faire parser
		//flags nous indiquant la position du parseur 
		private boolean inAnnuaire, inPersonne, inNom, inPrenom, inAdresse, inInformation; 
		//buffer nous permettant de récupérer les données  
		private StringBuffer buffer; 
	  
		// simple constructeur qui provient de DefaultHandler
		public PersonneHandler(){ 
			super(); 
		} 
		//détection d'ouverture de balise 
		public void startElement(String uri, String localName, 
				String qName, Attributes attributes) throws SAXException{ 
			if(qName.equals("annuaire")){ 
				annuaire = new LinkedList<Personne>(); 
				inAnnuaire = true; 
			}else if(qName.equals("personne")){ 
				personne = new Personne(); 
				try{ 
					int id = Integer.parseInt(attributes.getValue("id")); 
					personne.setId(id); 
				}catch(Exception e){ 
					//erreur, le contenu de id n'est pas un entier 
					throw new SAXException(e); 
				} 
				inPersonne = true; 
			}else { 
				buffer = new StringBuffer(); 
				if(qName.equals("nom")){ 
					inNom = true; 
				}else if(qName.equals("prenom")){ 
					inPrenom = true; 
				}else if(qName.equals("adresse")){ 
					inAdresse = true;
				}else if(qName.equals("information")){ 
					inInformation = true; 
				}else{ 
					//erreur, on peut lever une exception 
					throw new SAXException("Balise "+qName+" inconnue."); 
				} 
			} 
		} 
		//détection fin de balise 
		public void endElement(String uri, String localName, String qName) 
				throws SAXException{ 
			if(qName.equals("annuaire")){ 
				inAnnuaire = false; 
			}else if(qName.equals("personne")){ 
				annuaire.add(personne); 
				personne = null; //on remet à null la mémoire de la personne
				inPersonne = false; 
			}else if(qName.equals("nom")){ 
				personne.setNom(buffer.toString()); 
				buffer = null; 
				inNom = false; 
			}else if(qName.equals("prenom")){ 
				personne.setPrenom(buffer.toString()); 
				buffer = null; 
				inPrenom = false; 
			}else if(qName.equals("adresse")){ 
				personne.setAdresse(buffer.toString()); 
				buffer = null; 
				inAdresse = false;
			}else if(qName.equals("information")){ 
				personne.setInformation(buffer.toString()); 
				buffer = null; 
				inInformation = false; 
			}else{ 
				//erreur, on peut lever une exception 
				throw new SAXException("Balise "+qName+" inconnue."); 
			}           
		} 
		//détection de caractères 
		public void characters(char[] ch,int start, int length) 
				throws SAXException{ 
			String lecture = new String(ch,start,length); 
			if(buffer != null) buffer.append(lecture);        
		} 
		//début du parsing 
		public void startDocument() throws SAXException { 
			System.out.println("Début du parsing"); 
		} 
		//fin du parsing 
		public void endDocument() throws SAXException { 
			System.out.println("Fin du parsing"); 
			System.out.println("Resultats du parsing"); 
			for(Personne p : annuaire){ 
				System.out.println(p); 
			} 
		} 
	}
}



