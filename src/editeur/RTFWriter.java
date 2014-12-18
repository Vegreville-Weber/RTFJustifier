package editeur;

import static com.tutego.jrtf.Rtf.rtf;
import static com.tutego.jrtf.RtfHeader.font;
import static com.tutego.jrtf.RtfPara.p;
import static com.tutego.jrtf.RtfText.font;
import static com.tutego.jrtf.RtfText.fontSize;

import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import com.tutego.jrtf.Rtf;
import com.tutego.jrtf.RtfDocfmt;
import com.tutego.jrtf.RtfPara;
import com.tutego.jrtf.RtfUnit;

public class RTFWriter {

	public static void main(String[] args) {
		String lorem ="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse semper consequat elementum. Donec ultricies cursus justo, a eleifend mauris ultrices a. Nam elementum sapien id sodales viverra. Morbi vestibulum odio ut purus placerat tristique. Duis sed rhoncus tortor. Vestibulum in semper purus. Nam eget auctor augue. Sed ut volutpat nibh, eget mollis diam. Ut tincidunt ex et dui semper mattis. Nulla non lacus iaculis justo pretium hendrerit vitae eu odio. Sed sagittis aliquam tellus quis mollis. Ut id eleifend enim. Aliquam mollis vitae eros a dignissim. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer dapibus, ligula eget finibus hendrerit, metus ante aliquam quam, aliquet laoreet ligula odio at dolor. Nulla viverra, enim ut imperdiet venenatis, eros odio porttitor neque, quis aliquet mauris turpis et sem.";
		String loremcor = OptimisationAlgorithme.niceParagraph(new Font("Liberation Serif", Font.PLAIN, 12),lorem,481.88976378);
		System.out.println(loremcor);
		writeRTF("Liberation Serif",12,loremcor,21,"test.rtf");

	}
	
	public static void writeRTF(RTFReader r,String path){
		FileWriter temp = null;
		try {
			temp = new FileWriter(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Rtf output = rtf();
		if(r.paperw!=0){output=output.documentFormatting(RtfDocfmt.paperWidth(r.paperw, RtfUnit.POINT));}
		if(r.paperh!=0){output=output.documentFormatting(RtfDocfmt.paperHeight(r.paperh,RtfUnit.POINT));}
		if(r.marginl!=0){output=output.documentFormatting(RtfDocfmt.leftMargin(r.marginl, RtfUnit.POINT));}
		if(r.marginr!=0){output=output.documentFormatting(RtfDocfmt.rightMargin(r.marginr, RtfUnit.POINT));}
		if(r.margint!=0){output=output.documentFormatting(RtfDocfmt.topMargin(r.margint, RtfUnit.POINT));}
		if(r.marginb!=0){output=output.documentFormatting(RtfDocfmt.bottomMargin(r.marginb, RtfUnit.POINT));}
		for(int i : r.fontnames.keySet()){
			output=output.header(font(r.fontnames.get(i)).at( i ) );
		}
		LinkedList<RtfPara> paras = new LinkedList<RtfPara> ();
		for(Paragraphe pa : r.paragraphes){
			if(pa.fontnum!=-1) paras.add(p(font(pa.fontnum,fontSize(2*pa.getFontSize(),pa.texte))));
			else paras.add(p(fontSize(2*pa.getFontSize(),pa.texte)));
		}
		output=output.section(paras);
		output.out(temp);
		//.out(temp);
	}
	
	public static void writeRTF(String fontName,int fontSize,String paragraphe,double largeurFeuille,String path){
		double margin = 56.692913386; //2cm
		FileWriter temp = null;
		try {
			temp = new FileWriter(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		rtf()
		  .documentFormatting(RtfDocfmt.paperWidth(largeurFeuille, RtfUnit.POINT))
		  .documentFormatting(RtfDocfmt.leftMargin(margin, RtfUnit.POINT))
		  .documentFormatting(RtfDocfmt.rightMargin(margin, RtfUnit.POINT))
		  .documentFormatting(RtfDocfmt.topMargin(margin, RtfUnit.POINT))
		  .documentFormatting(RtfDocfmt.bottomMargin(margin, RtfUnit.POINT))
		  .header(
		    font(fontName).at( 0 ) )
		  .section(
		    p( font( 0,fontSize(2*fontSize,paragraphe) ) )
		  )
		.out(temp);		
		
	}

}
