package editeur;

import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		if(args.length==2){
			String source="";
			String dest="";
			int coupureMots=0;
			int justificationManuelle=-1; //justification par ajout d'espaces
			int justificationLogicielle=-1; // demande au logiciel de justifier chaque ligne
			for (int i = 0; i<args.length; i++){
				System.out.println("Le premier argument est : " + args[i]);
				if(args[i].equals("-mj")) justificationManuelle=1;
				else if(args[i].equals("-sj")) justificationLogicielle=1;
				else if(args[i].equals("-cw")) coupureMots=1;
				else if(args[i].startsWith("source=")) source=args[i].substring(7);
				else if(args[i].startsWith("dest=")) dest=args[i].substring(5);
			}
			
			if (justificationManuelle==1 && justificationLogicielle==1) throw new Error("-mj and -sj are not compatible");
			if (justificationManuelle!=1 && justificationLogicielle!=1) justificationManuelle=1;//Par defaut c'est la justification manuelle qui est choisie
			if (justificationManuelle==-1)justificationManuelle=0;
			if (justificationLogicielle==-1) justificationLogicielle=0;
			if (dest.equals("")) dest=source.replaceAll(".rtf", "-justified.rtf");
			
			run(source,dest);
			
		}
		else{
			GUI gui= new GUI();
		}
		
	}
	
	public static void run(String source, String cible){
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
