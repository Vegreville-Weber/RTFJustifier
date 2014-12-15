package editeur;
import static com.tutego.jrtf.Rtf.rtf;
import static com.tutego.jrtf.RtfDocfmt.*;
import static com.tutego.jrtf.RtfHeader.*;
import static com.tutego.jrtf.RtfInfo.*;
import static com.tutego.jrtf.RtfFields.*;
import static com.tutego.jrtf.RtfPara.*;
import static com.tutego.jrtf.RtfSectionFormatAndHeaderFooter.*;
import static com.tutego.jrtf.RtfText.*;
import static com.tutego.jrtf.RtfUnit.*;

import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;

import com.tutego.jrtf.RtfDocfmt;
import com.tutego.jrtf.RtfText;
import com.tutego.jrtf.RtfUnit;

public class RTFReader {

	public static void main(String[] args) {
		String lorem ="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse semper consequat elementum. Donec ultricies cursus justo, a eleifend mauris ultrices a. Nam elementum sapien id sodales viverra. Morbi vestibulum odio ut purus placerat tristique. Duis sed rhoncus tortor. Vestibulum in semper purus. Nam eget auctor augue. Sed ut volutpat nibh, eget mollis diam. Ut tincidunt ex et dui semper mattis. Nulla non lacus iaculis justo pretium hendrerit vitae eu odio. Sed sagittis aliquam tellus quis mollis. Ut id eleifend enim. Aliquam mollis vitae eros a dignissim. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer dapibus, ligula eget finibus hendrerit, metus ante aliquam quam, aliquet laoreet ligula odio at dolor. Nulla viverra, enim ut imperdiet venenatis, eros odio porttitor neque, quis aliquet mauris turpis et sem.";
		String loremcor = OptimisationAlgorithme.niceParagraph(new Font("Liberation Serif", Font.PLAIN, 12),lorem,481.88976378);
		System.out.println(loremcor);
		RTFWriter.writeRTF("Liberation Serif",12,loremcor,21);

	}
}
