package editeur;


import java.awt.Font;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;



public class RTFReader { //classe utilisée pour retenir toutes les informations utiles lors de la lecture de la source
	LinkedList<Paragraphe> paragraphes ; //paragraphes présents dans la source.
	String path; //chemin de la source.
	HashMap<Integer,String> fontnames; //polices déclarées au début de la source
	double paperh,paperw,marginr,marginl,margint,marginb; //hauteur(h),largeur(w) et marges(right,left,top,bottom) 
	//ATTENTION : hauteur,largeur et marges sont en TWIP, unitée utilisée par le format RTF.
	int fontdefault;
	//indice de la police déclarée comme police par défaut par la source.
	
	
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
		this.fontdefault=0;
	}
		
	
	//la methode run() parse le fichier source et sauvegarde toutes les informations utiles
	public	void run(){
		try{
			BufferedReader buff = new BufferedReader(new FileReader(this.path));
			 
			try {
				String line;
				boolean onParagraph = false; //le BufferedReader est-t'il actuellement en train de lire un paragraphe ?
				String currentParagraph=""; //si oui, on enregistre au fur et au mesure les lignes dans currentParagraph.
				double currentfontsize = 12; int currentfontnum =-1; // si on est dans un paragraphe, quelle est la police et la taille de la police utilisï¿½e?
				line=buff.readLine();
				try{ //Normalement, la police par dï¿½faut est dï¿½clarï¿½e sur la premiï¿½re ligne.
					this.fontdefault=Integer.parseInt(Character.toString(line.split("deff")[1].charAt(0)));
				}
				catch(NumberFormatException nfe){
					System.out.println("pas de police par dï¿½fault dï¿½clarï¿½e");
				}
				System.out.println("Police par dï¿½fault : "+this.fontdefault);
			while ((line = buff.readLine()) != null) {
				//System.out.println(line);
				if(onParagraph){ //Si on se trouve dans un paragraphe.
					if(line.contains("}")){ //si cette ligne est la fin d'un paragraphe
						currentParagraph+=line.split("}")[0].split(Pattern.quote("\\par"))[0]; //Corrige le bug qui arrivait lorsque l'utilisateur laissait un paragraphe vide
						Font f;
						if(currentfontnum==-1) f = new Font("Liberation Serif",Font.PLAIN,(int) currentfontsize);
						else f = new Font(this.fontnames.get(currentfontnum),Font.PLAIN,(int) currentfontsize);
						this.paragraphes.add(new Paragraphe(currentParagraph,f,currentfontnum)); //on rajoute le paragraphe ï¿½ la liste paragraphes.
						currentfontsize=12;  // on remet ï¿½ zï¿½ro les variables currentfontsize,currentfontnum,currentParagraph et onParagraph
						currentfontnum=-1;
						currentParagraph="";
						onParagraph=false;
					}
					else{
						currentParagraph+=line;
					}
				}
				if(line.contains("{\\fonttbl")){ //cas oï¿½ on est en train de lire la ligne qui dï¿½clare toutes les polices.
					this.searchFonts(line); //fonction traitant la ligne pour y trouver le nom et les indices des polices
					for(String s : this.fontnames.values())System.out.println(s);
				}
				if(line.contains("\\paper")){ //cas oï¿½ on est en train de lire la ligne qui dï¿½clare les dimensions.
					this.searchSize(line); //fonction traitant la ligne pour y trouver la largeur et la hauteur de la page
					System.out.println("paperh: "+this.paperh+" paperw: "+this.paperw);
				}
				if(line.contains("\\marg")){ //cas oï¿½ on est en train de lire la ligne qui dï¿½clare toutes les marges.
					this.searchMargins(line); //fonction traitant la ligne pour y trouver les marges utilsiï¿½es
					System.out.println(" margint :"+this.margint+" marginb :"+this.marginb+" marginr :"+this.marginr+" marginl :"+this.marginl);
				}
				if(line.contains("\\pard")){ ////cas où on est en train de lire la ligne qui dï¿½clare un nouveau paragraphe
					String[] lines= line.split("ltrch");
					String info[] = lines[lines.length-1].split("loch");
					System.out.println(info.length);
					if(info.length>1){
						for(int k=1;k<info.length;k++){
							if(info[k].contains("\\fs")){ //regarde si une taille de police est dï¿½clarï¿½e pour ce paragraphe
								String temp =info[k].substring(3); String size ="";
								int i =0;
								while(temp.charAt(i)!='\\'){
									size+=temp.charAt(i);
									i++;
								}
								currentfontsize=Double.parseDouble(size)/2;
							}
							boolean isNextInteger =true;

							try{//permet de voir si une police a ï¿½tï¿½ dï¿½clarï¿½e pour ce paragraphe.
								System.out.println(k + " et  "+info[k]);
								Integer.parseInt(Character.toString(info[k].charAt(2)));
							}
							catch (NumberFormatException | StringIndexOutOfBoundsException nfe  ) { //I add the stringindexoutofboundsexception to deal with the case where there are multiple /loch and no /fs (between the two loch)
						        isNextInteger=false;
						    }
			
							if(isNextInteger&&info[k].contains("\\f")){ // ATTENTION IL NE FAUT POUR L'INSTANT PAS PLUS DE 10 POLICES DECLAREES
								currentfontnum = Integer.parseInt(Character.toString(info[k].charAt(2)));
							}
						}
					}
					//System.out.println("fontnum: "+fontnum+"fontsize: "+fontsize);
					onParagraph=true; //la balise \\pard dï¿½clare un nouveau paragraphe donc les prochaines lignes feront partie du paragraphe.
				}
				
			}
			} finally {
				buff.close();
			}
			} catch (IOException ioe) {
			System.out.println("Erreur --" + ioe.toString());
			}
	}
	
	public void searchFonts(String line){ //traite la ligne line pour y trouver toutes les polices dï¿½clarï¿½es
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
	
	public void searchSize(String line){ //traite la ligne line pour y trouver largeur et hauteur
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
	
	public void searchMargins(String line){ //traite la ligne line pour y trouver les dimensions des marges
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




