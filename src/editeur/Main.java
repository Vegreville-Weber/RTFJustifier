package editeur;

import java.io.IOException;
import java.util.LinkedList;

public class Main {

	public static boolean justificationManuelle = false; //justification par ajout d'espaces
	public static boolean justificationLogicielle = true; //demande au logiciel de justifier les lignes
	public static boolean coupureMots= true; //autorise la coupure de mots (l'hyphenation) ou pas
	public static int penalite = 1; //permet de doser la penalité infliger pour l'utilisation de coupures de mots
	
	public static void main(String[] args) {
		//Si le logiciel est executé en ligne de commande
		if(args.length>=1){
			String source="";
			String dest="";
			justificationLogicielle=false;
			justificationManuelle=false;
			coupureMots=false;
			boolean parametreJustification =false;
			for (int i = 0; i<args.length; i++){
				if(args[i].equals("-mj")) {
					if (!parametreJustification) justificationManuelle=true;
					else throw new Error("Vous ne pouvez pas avoir plusieurs paramètres de justifications");
					}
				else if(args[i].equals("-sj"))  {
					if (!parametreJustification) justificationLogicielle=true;
					else throw new Error("Vous ne pouvez pas avoir plusieurs paramètres de justifications");
					}
				else if(args[i].equals("-cw")) coupureMots=true;
				else if(args[i].startsWith("source=")) source=args[i].substring(7);
				else if(args[i].startsWith("dest=")) dest=args[i].substring(5);
				else if(args[i].startsWith("-p=")) {
					penalite=Integer.parseInt(args[i].substring(3));
					if (penalite>10) throw new Error("La penalité doit être comprise entre 0 et 10 inclus");
				}
			}
			
			//Si le fichier source n'est pas spécifié
			if (source.equals("")) throw new Error("Vous devez indiquer un fichier source");
			//Si le fichier de destination n'est pas spécifié
			if (dest.equals("")) dest=source.replaceAll(".rtf", "-justified.rtf");
			
			try {
				run(source,dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		//en l'absence d'arguments on démarre l'interface graphique
		else{
			GUI gui= new GUI();
		}
		
	}

	/** Fonction principale du programme qui lit le fichier source, passe l'algorithme choisi dessus
	 * et écrit le resultat sur le fichier cible.
	 * @param source Chemin vers le fichier source .rtf
	 * @param cible Chemin vers le fichier cible .rtf
	 * @throws IOException
	 */
	public static void run(String source, String cible) throws IOException{
		RTFReader r = new RTFReader(source);
		r.run(); //on lit le fichier source et on sauvegarde les informations utiles dans r.
		double largeurBloc = (r.paperw-r.marginl-r.marginr)*0.05; // On convertit le TWIP en POINT
		LinkedList<Paragraphe> newparagraphes = new LinkedList<Paragraphe>();
		for (Paragraphe p : r.paragraphes){
			String newpara;			
			double securite = new Polices(p.font).largeurMot("i");
			// On prend une sécurité sur la largeur maximale d'une ligne pour
			// compenser les erreurs d'evaluation entre Java et LibreOffice. 
			// On prend la taille du caractère "i" car ca correspond, en moyenne,
			// au plus petit caractère de chaque police, et qui suffit à se 
			// prémunir des erreurs d'évaluation.
			Polices pol = new Polices(p.font);
			if(Main.coupureMots)
				newpara = HyphenationAlgorithme.niceParagraph(pol, p.texte, largeurBloc-securite);
			else
				newpara = OptimisationAlgorithme.niceParagraph(pol, p.texte, largeurBloc-securite);	
			Paragraphe temp = new Paragraphe(newpara,p.font,p.fontnum);
			newparagraphes.add(temp);				
		}
		r.paragraphes=newparagraphes; //on met dans r les nouveaux paragraphes
		RTFWriter.writeRTF(r,cible); //on écrit le résultat dans le fichier cible.
	}

}
