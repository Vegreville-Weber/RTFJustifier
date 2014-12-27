package editeur;

import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		/*if(args.length==2){
			run(args[0],args[1]);
		}
		else{
			run("TwoParagraph.rtf","test.rtf");
		}*/
		GUI gui= new GUI();
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
