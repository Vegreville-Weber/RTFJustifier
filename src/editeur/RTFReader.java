package editeur;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;


public class RTFReader {

	public static void main(String[] args) {
		RTFEditorKit editor = new RTFEditorKit();
		InputStream inputstream = null;
		try {
			inputstream = new FileInputStream("OneParagraph.rtf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DefaultStyledDocument styledDoc=new DefaultStyledDocument();
		try {
			editor.read(inputstream, styledDoc, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str="str";
		try {
			str = styledDoc.getText(0, styledDoc.getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(str);

	}
}
