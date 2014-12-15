package editeur;

import static com.tutego.jrtf.Rtf.rtf;
import static com.tutego.jrtf.RtfHeader.font;
import static com.tutego.jrtf.RtfPara.p;
import static com.tutego.jrtf.RtfText.font;
import static com.tutego.jrtf.RtfText.fontSize;

import java.io.FileWriter;
import java.io.IOException;

import com.tutego.jrtf.RtfDocfmt;
import com.tutego.jrtf.RtfUnit;

public class RTFWriter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static void writeRTF(String fontName,int fontSize,String paragraphe,double largeurFeuille){
		double margin = 2; //2cm
		FileWriter temp = null;
		try {
			temp = new FileWriter("test.rtf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		rtf()
		  .documentFormatting(RtfDocfmt.paperWidth(largeurFeuille, RtfUnit.CM))
		  .documentFormatting(RtfDocfmt.leftMargin(margin, RtfUnit.CM))
		  .documentFormatting(RtfDocfmt.rightMargin(margin, RtfUnit.CM))
		  .documentFormatting(RtfDocfmt.topMargin(margin, RtfUnit.CM))
		  .documentFormatting(RtfDocfmt.bottomMargin(margin, RtfUnit.CM))
		  .header(
		    font(fontName).at( 0 ) )
		  .section(
		    p( font( 0,fontSize(2*fontSize,paragraphe) ) )
		  )
		.out(temp);
		
		
	}

}
