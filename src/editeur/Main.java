package editeur;

import java.io.IOException;
import java.util.LinkedList;

public class Main {

	public static boolean justificationManuelle = false; //justification par ajout d'espaces
	public static boolean justificationLogicielle = true; //demande au logiciel de justifier les lignes
	public static boolean coupureMots= true; //autorise la coupure de mots (l'hyphenation) ou pas
	
	public static void main(String[] args) {
		//Si le logiciel est execut� en ligne de commande
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
					else throw new Error("Vous ne pouvez pas avoir plusieurs param�tres de justifications");
					}
				else if(args[i].equals("-sj"))  {
					if (!parametreJustification) justificationLogicielle=true;
					else throw new Error("Vous ne pouvez pas avoir plusieurs param�tres de justifications");
					}
				else if(args[i].equals("-cw")) coupureMots=true;
				else if(args[i].startsWith("source=")) source=args[i].substring(7);
				else if(args[i].startsWith("dest=")) dest=args[i].substring(5);
			}
			
			//Si le fichier source n'est pas sp�cifi�
			if (source.equals("")) throw new Error("Vous devez indiquer un fichier source");
			//Si le fichier de destination n'est pas sp�cifi�
			if (dest.equals("")) dest=source.replaceAll(".rtf", "-justified.rtf");
			
			try {
				run(source,dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		//en l'absence d'arguments on d�marre l'interface graphique
		else{
			GUI gui= new GUI();
		}
		
	}
	
	public static void run(String source, String cible) throws IOException{
		RTFReader r = new RTFReader(source);
		r.run();
		double largeurBloc = (r.paperw-r.marginl-r.marginr)*0.05; // On convertit le TWIP en POINT
		LinkedList<Paragraphe> newparagraphes = new LinkedList<Paragraphe>();
		for (Paragraphe p : r.paragraphes){
			String newpara;
			
			double securite = new Polices(p.font).largeurMot("i");
			// On prend une s�curit� sur la largeur maximale d'une ligne pour
			// compenser les erreurs d'evaluation entre Java et LibreOffice. 
			// On prend la taille du caract�re "i" car ca correspond, en moyenne,
			// au plus petit caract�re de chaque police, et qui suffit � se 
			// pr�munir des erreurs d'�valuation.
			
			if(Main.coupureMots)
				newpara = HyphenationAlgorithme.niceParagraph(p.font, p.texte, largeurBloc-securite);
			else
				newpara = OptimisationAlgorithme.niceParagraph(p.font, p.texte, largeurBloc-securite);	
			Paragraphe temp = new Paragraphe(newpara,p.font,p.fontnum);
			newparagraphes.add(temp);				
		}
		r.paragraphes=newparagraphes;
		RTFWriter.writeRTF(r,cible);
	}

}
