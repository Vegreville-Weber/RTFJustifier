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
	double paperh,paperw,marginr,marginl,margint,marginb;
	
	
	public static void main(String[] args) {
		RTFReader r= new RTFReader("TwoParagraph.rtf");
		r.run();
		for(Paragraphe p : r.paragraphes){
			System.out.println("fontname:"+p.font.getFontName()+" fontSize:"+p.font.getSize()+" " +p.texte);
		}
	}
	
	public RTFReader(String path){
		this.paragraphes=new LinkedList<Paragraphe>();
		this.path=path;
		this.fontnames=new HashMap<Integer,String>();
		this.marginb=0;this.marginl=0;this.marginr=0;this.margint=0;this.paperh=0;this.paperw=0;
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
				boolean onParagraph = false;
				String currentParagraph="";
				double currentfontsize = 12; int currentfontnum =-1;
			while ((line = buff.readLine()) != null) {
				//System.out.println(line);
				if(onParagraph){
					if(line.contains("}")){
						currentParagraph+=line.split("}")[0];
						Font f;
						if(currentfontnum==-1) f = new Font("Liberation Serif",Font.PLAIN,(int) currentfontsize);
						else f = new Font(this.fontnames.get(currentfontnum),Font.PLAIN,(int) currentfontsize);
						this.paragraphes.add(new Paragraphe(currentParagraph,f));
						currentfontsize=12;
						currentfontnum=-1;
						currentParagraph="";
						onParagraph=false;
					}
				}
				if(line.contains("{\\fonttbl")){
					this.searchFonts(line);
					for(String s : this.fontnames.values())System.out.println(s);
				}
				if(line.contains("\\paper")){
					this.searchSize(line); System.out.println("paperh: "+this.paperh+" paperw: "+this.paperw);
				}
				if(line.contains("\\marg")){
					this.searchMargins(line); System.out.println(" margint :"+this.margint+" marginb :"+this.marginb+" marginr :"+this.marginr+" marginl :"+this.marginl);
				}
				if(line.contains("\\pard")){
					String paragraphe="";
					String[] lines= line.split("ltrch");
					String info[] = lines[lines.length-1].split("loch");
					if(info.length>1){
						for(int k=1;k<info.length;k++){
							if(info[k].contains("\\fs")){
								String temp =info[k].substring(3); String size ="";
								int i =0;
								while(temp.charAt(i)!='\\'){
									size+=temp.charAt(i);
									i++;
								}
								currentfontsize=Double.parseDouble(size)/2;
							}
							boolean isNextInteger =true;
							try{
								Integer.parseInt(Character.toString(info[k].charAt(2)));
							}
							catch (NumberFormatException nfe) {
						        isNextInteger=false;
						    }
							if(isNextInteger&&info[k].contains("\\f")){ // ATTENTION IL FAUT POUR L'INSTANT PAS PLUS DE 10 POLICES DECLAREES
								currentfontnum = Integer.parseInt(Character.toString(info[k].charAt(2)));
							}
						}
					}
					//System.out.println("fontnum: "+fontnum+"fontsize: "+fontsize);
					onParagraph=true;
				}
				
			}
			} finally {
				buff.close();
			}
			} catch (IOException ioe) {
			System.out.println("Erreur --" + ioe.toString());
			}
	}
	
	public void searchFonts(String line){
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
	}
	
	public void searchSize(String line){
		String[] size = line.split("paper",3);
		for(int k=0;k<size.length;k++){
			if(size[k].charAt(0)=='h'&&this.paperh==0){
				String paperh ="";
				int i =1;
				int n = size[k].length();
				while(i<n){
					char temp = size[k].charAt(i);
					if(temp=='\\')break;
					paperh+=temp;
					i++;
				}
				this.paperh=Double.parseDouble(paperh);
			}
			if(size[k].charAt(0)=='w'&&this.paperw==0){
				String paperh ="";
				int i =1;
				int n = size[k].length();
				while(i<n){
					char temp = size[k].charAt(i);
					if(temp=='\\')break;
					paperh+=temp;
					i++;
				}
				this.paperw=Double.parseDouble(paperh);
			}
		}
	}
	
	public void searchMargins(String line){
		String[] margins = line.split("marg",5);
		for(int k=0;k<margins.length;k++){
			char init = margins[k].charAt(0);
			boolean isNextDouble = true;
		    try {
		    	Double.parseDouble(Character.toString(margins[k].charAt(1)));
		    } catch (NumberFormatException nfe) {
		        isNextDouble=false;
		    }
			if(isNextDouble&&(init=='l'||init=='b'||init=='r'||init=='t')){
				String margin ="";
				int i =1;
				int n = margins[k].length();
				while(i<n){
					char temp = margins[k].charAt(i);
					if(temp=='\\')break;
					margin+=temp;
					i++;
				}
				double doublemargin = Double.parseDouble(margin);
				switch(init){
				case 'l':
					this.marginl=doublemargin;
					break;
				case 'r':
					this.marginr=doublemargin;
					break;
				case 'b':
					this.marginb=doublemargin;
					break;
				case 't':
					this.margint=doublemargin;
					break;
				default:break;
				}
			}
		}
	}
}




