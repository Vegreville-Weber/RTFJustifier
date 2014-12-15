package editeur;


import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.rtf.RTFEditorKit;


public class RTFReader {
	LinkedList<Paragraphe> paragraphes ;
	String path;
	HashMap<Integer,String> fontnames;
	
	
	public static void main(String[] args) {
		RTFReader r= new RTFReader("TwoParagraph.rtf");
		r.run();

	}
	
	public RTFReader(String path){
		this.paragraphes=new LinkedList<Paragraphe>();
		this.path=path;
		this.fontnames=new HashMap<Integer,String>();
	}
	
	public void addParagraphe(Paragraphe p){
		this.paragraphes.add(p);
	}
	
	public void addFont(int key,String f){
		this.fontnames.put(key, f);
	}
	
	public	void run(){
		try{
			BufferedReader buff = new BufferedReader(new FileReader(this.path));
			 
			try {
				String line;
			while ((line = buff.readLine()) != null) {
				//System.out.println(line);
				if(line.startsWith("{\\fonttbl")){
					String font = new String(line.substring(9));
					String[] temp = font.split("\\{");
					for(int k=1;k<temp.length;k++){
						
						if(temp[k].startsWith("\\f")){
							String fontnum = temp[k].substring(2);
							int num = fontnum.charAt(0)-'0';
							char[] c = fontnum.toCharArray();
							String FontName = "";
							boolean isOn=false;
							for(int i=0;i<c.length;i++){
								if(isOn&&c[i]!=';'&& c[i]!='}') FontName+=c[i];
								else if(!isOn && c[i]==' ') isOn=true;
							}
							this.fontnames.put(num, FontName);
						}
						
						
					}
					for(String s : this.fontnames.values())System.out.println(s);
				}
			}
			} finally {
				buff.close();
			}
			} catch (IOException ioe) {
			System.out.println("Erreur --" + ioe.toString());
			}
	}
	
}




