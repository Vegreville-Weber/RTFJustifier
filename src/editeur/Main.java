package editeur;

public class Main {

	public static void main(String[] args) {
		if(args.length==1){
			RTFReader r = new RTFReader(args[0]);
			r.run();
			for (Paragraphe p : r.paragraphes){
				p.texte=OptimisationAlgorithme.niceParagraph(p.font, p.texte, r.paperw);
			}
			RTFWriter.writeRTF(r);
		}
		else{
			RTFReader r = new RTFReader("TwoParagraph.rtf");
			r.run();
			for (Paragraphe p : r.paragraphes){
				p.texte=OptimisationAlgorithme.niceParagraph(p.font, p.texte, r.paperw);
			}
			RTFWriter.writeRTF(r);
		}

	}

}
