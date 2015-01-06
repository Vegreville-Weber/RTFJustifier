package editeur;

import java.io.IOException;
import java.util.LinkedList;

public class Main {

	public static boolean justificationManuelle = true; //justification par ajout d'espaces
	public static boolean justificationLogicielle = false; //demande au logiciel de justifier les lignes
	public static boolean coupureMots= false; //autorise la coupure de mots (l'hyphenation) ou pas
	
	public static void main(String[] args) {
		if(args.length>=1){
			String source="";
			String dest="";
			for (int i = 0; i<args.length; i++){
				if(args[i].equals("-mj")) {justificationManuelle=true; justificationLogicielle=false;}
				else if(args[i].equals("-sj"))  {justificationManuelle=false; justificationLogicielle=true;}
				else if(args[i].equals("-cw")) coupureMots=true;
				else if(args[i].startsWith("source=")) source=args[i].substring(7);
				else if(args[i].startsWith("dest=")) dest=args[i].substring(5);
			}
			
			//if (justificationManuelle==true && justificationLogicielle==true) throw new Error("-mj and -sj are not compatible");
			//if (justificationManuelle!=1 && justificationLogicielle!=1) justificationManuelle=1;//Par defaut c'est la justification manuelle qui est choisie
			//if (justificationManuelle==-1)justificationManuelle=0;
			//if (justificationLogicielle==-1) justificationLogicielle=0;
			if (dest.equals("")) dest=source.replaceAll(".rtf", "-justified.rtf");
			
			try {
				run(source,dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			GUI gui= new GUI();
		}
		
	}
	
	public static void run(String source, String cible) throws IOException{
		RTFReader r = new RTFReader(source);
		r.run();
		double largeurBloc = (r.paperw-r.marginl-r.marginr)*0.05; // On convertit le TWIP en POINT
		//System.out.println(largeurBloc);
		LinkedList<Paragraphe> newparagraphes = new LinkedList<Paragraphe>();
		for (Paragraphe p : r.paragraphes){
			String newpara = OptimisationAlgorithme.niceParagraph(p.font, p.texte, largeurBloc);
			Paragraphe temp = new Paragraphe(newpara,p.font,p.fontnum);
			newparagraphes.add(temp);				
		}
		r.paragraphes=newparagraphes;
		RTFWriter.writeRTF(r,cible);
	}

}
