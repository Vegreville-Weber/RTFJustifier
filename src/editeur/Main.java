package editeur;

import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		String path="test.rtf";
		if(args.length==1){
			RTFReader r = new RTFReader(args[0]);
			r.run();
			double largeurBloc = (r.paperw-r.marginr-r.marginr)*0.05;
			LinkedList<Paragraphe> newparagraphes = new LinkedList<Paragraphe>();
			for (Paragraphe p : r.paragraphes){
				String newpara = OptimisationAlgorithme.niceParagraph(p.font, p.texte, largeurBloc);
				Paragraphe temp = new Paragraphe(newpara,p.font,p.fontnum);
				newparagraphes.add(temp);				
			}
			r.paragraphes=newparagraphes;
			RTFWriter.writeRTF(r,path);
		}
		else{
			RTFReader r = new RTFReader("TwoParagraph.rtf");
			r.run();
			LinkedList<Paragraphe> newparagraphes = new LinkedList<Paragraphe>();
			double largeurBloc = (r.paperw-r.marginr-r.marginr)*0.05; //On convertit l'unité (le twip)utilisé par .rtf en point.
			for (Paragraphe p : r.paragraphes){
				String newpara = OptimisationAlgorithme.niceParagraph(p.font, p.texte, largeurBloc);
				Paragraphe temp = new Paragraphe(newpara,p.font,p.fontnum);
				newparagraphes.add(temp);			
				System.out.println(newpara);
			}
			r.paragraphes=newparagraphes;
			RTFWriter.writeRTF(r,path);
		}

	}

}
