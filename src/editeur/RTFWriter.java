package editeur;


import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import com.tutego.jrtf.*;


public class RTFWriter { //une fois connues gr�ce � RTFReader les caract�ristiques du fichier source, RTFWriter �crit les nouveaux paragraphes dans le fichier cible.

	/** Fonction d'�criture qui utilise le package com.tutego.jrtf
	 * @param r
	 * @param destination
	 */
	public static void writeRTF(RTFReader r,String destination){ 
		FileWriter temp = null;
		try {
			temp = new FileWriter(destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Rtf output = Rtf.rtf();
		
		if(r.paperw!=0){output=output.documentFormatting(RtfDocfmt.paperWidth(r.paperw, RtfUnit.TWIPS));}
		if(r.paperh!=0){output=output.documentFormatting(RtfDocfmt.paperHeight(r.paperh,RtfUnit.TWIPS));}
		if(r.marginl!=0){output=output.documentFormatting(RtfDocfmt.leftMargin(r.marginl, RtfUnit.TWIPS));}
		if(r.marginr!=0){output=output.documentFormatting(RtfDocfmt.rightMargin(r.marginr, RtfUnit.TWIPS));}
		if(r.margint!=0){output=output.documentFormatting(RtfDocfmt.topMargin(r.margint, RtfUnit.TWIPS));}
		if(r.marginb!=0){output=output.documentFormatting(RtfDocfmt.bottomMargin(r.marginb, RtfUnit.TWIPS));}
		for(int i : r.fontnames.keySet()){ //On d�clare toutes les fonctions d�clar�es dans le fichier source
			output=output.header(RtfHeader.font(r.fontnames.get(i)).at( i ) );
		}
		LinkedList<RtfPara> paras = new LinkedList<RtfPara> ();
		if(Main.justificationLogicielle){ //Si l'utilisateur a demand� la justification logicielle. Cette derni�re est effectu� via la fonction .alignJustified()
			for(Paragraphe pa : r.paragraphes){ //pa.fontnum==-1 signifie que la police n'est pas pr�cis�e pour ce paragraphe
				if(pa.fontnum!=-1) paras.add(RtfPara.p(RtfText.font(pa.fontnum,RtfText.fontSize(2*pa.getFontSize(),pa.texte))).alignJustified()); 
				else paras.add(RtfPara.p(RtfText.font(r.fontdefault,RtfText.fontSize(2*pa.getFontSize(),pa.texte))).alignJustified());
			}
		}
		else{
			for(Paragraphe pa : r.paragraphes){
				if(pa.fontnum!=-1) paras.add(RtfPara.p(RtfText.font(pa.fontnum,RtfText.fontSize(2*pa.getFontSize(),pa.texte))));
				else paras.add(RtfPara.p(RtfText.font(r.fontdefault,RtfText.fontSize(2*pa.getFontSize(),pa.texte))));
			}
		}
		output=output.section(paras);
		output.out(temp);
		
	}
	
	
}
