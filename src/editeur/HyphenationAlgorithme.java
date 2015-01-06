package editeur;

import java.awt.Font;
import java.awt.font.TextLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;


public class HyphenationAlgorithme {

	private static final double INFINI = Double.MAX_VALUE;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    	Main.justificationManuelle=false;
		Main.justificationLogicielle=false;
		try {
			Main.run("TwoParagraph.rtf","test.rtf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

		
		
		/**
		 * @param paragraphe - Paragraphe à mettre en page EN CONSIDERANT UN SEUL ESPACE ENTRE CHAQUE MOT
		 * @param largeur - Tableau donnant pour chaque caractère (ESPACE BLANC inclus) la largeur du caractère
		 * @param largeurBloc - Largeur du bloc où on écrit le paragraphe. en point PS
		 * @return - Renvoie le paragraphe mis en page
		 */
		public static String niceParagraph(Font f, String paragraphe,double largeurBloc){
			Polices pol = new Polices(f);
	    	return niceParagraph(paragraphe,pol,largeurBloc);
		}
		
		/**
		 * @param paragraphe - Paragraphe à mettre en page EN CONSIDERANT UN SEUL ESPACE ENTRE CHAQUE MOT
		 * @param largeur - Tableau donnant pour chaque caractère (ESPACE BLANC inclus) la largeur du caractère
		 * @param largeurBloc - Largeur du bloc où on écrit le paragraphe.
		 * @return - Renvoie le paragraphe mis en page
		 */
		//LARGEURBLOC DOIT ETRE PLUS GRAND QUE LE PLUS GRAND DES MOTS DU PARAGRAPHE
		public static String niceParagraph(String paragraphe,Polices police,double largeurBloc){
			if(paragraphe.trim().length()==0) return paragraphe; //paragraphe avec que des blancs.
			if(paragraphe.isEmpty()) return " ";
			String[] chaine = chainesdeMots(paragraphe); //chaine[k] : k-ieme mot du paragraphe
			Mots init = new Mots(chaine[0],1);
			Mots temprun = init;
			for(int k = 1; k<chaine.length;k++){
				Mots temp = new Mots(chaine[k],k+1);
				temprun.setNext(temp);
				temprun = temp;
			}
			int nombreDeMots = chaine.length;
			
			double[][] espaces = new double[nombreDeMots + 1][nombreDeMots + 1];
			/*espaces[i][j] :  Nombre d'espaces blancs à la fin d'une ligne où on a mit les mots du i-eme mot au j-eme mot.
			Si cette  suite de mots dépasse la ligne, espaces[i][j] sera négatif */
			
			double[][] costs = new double[nombreDeMots + 1][nombreDeMots + 1];
			// costs[i][j] : Côut d'une ligne où on a mit les mots du i-eme mot au j-ieme mot : espaces[i][j]^3 si espaces[i][j]>=0 , INFINI sinon.
			
			double[] costFinal = new double[nombreDeMots + 1];
			// costFinal[i] : Côut optimal (sommes des costs sur chaque ligne) des lignes formées des mots du premier mot au i-eme mot.
			
			int[] p = new int[nombreDeMots + 1]; 
			//pointeur qui retient les positions des sauts de ligne dans la solution finale.
			
			
			
			
			for (int i = 1; i <= nombreDeMots; i++) { //on remplit le tableau espaces.
				espaces[i][i] = largeurBloc - largeurMot(chaine[i-1],police); //ligne ne comportant que le i-eme mot
				for (int j = i + 1; j <= nombreDeMots; j++) {
					espaces[i][j] = espaces[i][j - 1] - largeurMot(" " + chaine[j-1],police);
				}
			}

			for (int i = 1; i <= nombreDeMots; i++) { //on remplit le tableau costs.
				for (int j = i; j <= nombreDeMots; j++) {
					if (espaces[i][j] < 0)
						costs[i][j] = INFINI;
					else if (j == nombreDeMots && espaces[i][j] >= 0) //on ne prend pas en compte la dernière ligne.
						costs[i][j] = 0;
					else
						costs[i][j] = espaces[i][j] * espaces[i][j] * espaces[i][j];
				}
			}
			
			costFinal[0] = 0;
			Mots temp = init;
			for (int j = 1; j <= nombreDeMots; j++) {
				costFinal[j] = INFINI;
				for (int i = 1; i <= j; i++) {
					if (costFinal[i-1] != INFINI && costs[i][j] != INFINI && (costFinal[i-1] + costs[i][j] < costFinal[j])) {
						costFinal[j] = costFinal[i - 1] + costs[i][j];
						p[j] = i; //on retient le saut de ligne.
						temp.isEnd=true;
					}
					String content = temp.content;
					int size = content.length();
					if(size>4){
						String left = content.substring(0,size/2);
						String right = content.substring((size/2),size);
						double espace = espaces[i][j - 1] - largeurMot(" " + left+"-",police);
						double cost;
						if(espace <0 || j==nombreDeMots) cost=INFINI;
						else cost = espace*espace*espace +10000;//penalité
						if(costFinal[i-1]!=INFINI&&cost!=INFINI&&(costFinal[i-1]+cost<costFinal[j])){
							temp.isHyph=true;
							temp.left=left;
							temp.right=right;
							costFinal[j] = costFinal[i-1] +cost;
							espaces[j+1][j+1] = espaces[j+1][j+1]- largeurMot(right,police);
							for (int k = j + 2; k <= nombreDeMots; k++) {
								espaces[j+1][k] = espaces[j+1][k - 1] - largeurMot(" " + chaine[k-1],police);
							}
							p[j] = i;
							for (int s = 1; s <= nombreDeMots; s++) { //on remplit le tableau costs.
								for (int t = s; t <= nombreDeMots; t++) {
									if (espaces[s][t] < 0)
										costs[s][t] = INFINI;
									else if (t == nombreDeMots && espaces[s][t] >= 0) //on ne prend pas en compte la dernière ligne.
										costs[s][t] = 0;
									else
										costs[s][t] = espaces[s][t] * espaces[s][t] * espaces[s][t];
								}
							}
						}
						
					}
							
				}
				temp = temp.next;
			}
			String restemp = new String();
			Mots run = init;
			LinkedList<Integer> stop = new LinkedList<Integer>();
			int pointeur = nombreDeMots;
			while(pointeur!=0){
				stop.addFirst(pointeur);
				pointeur = p[pointeur]-1;
			}
			pointeur = stop.poll();
			if(Main.justificationLogicielle){
				while(run!= null&&(!stop.isEmpty()||pointeur==nombreDeMots)){
					if(run.place==pointeur&&!run.isHyph){
						if(pointeur!=nombreDeMots){
							pointeur = stop.poll();
							restemp=restemp+run.content+"\\line ";
						}
						else{
							pointeur=nombreDeMots+1;
							restemp=restemp+run.content+System.lineSeparator();
						}
						
					}
					else if(run.place==pointeur&&run.isHyph){
						restemp = restemp+run.left+"-"+"\\line "+run.right+" ";
						if(pointeur!=nombreDeMots)pointeur = stop.poll();
						else pointeur=nombreDeMots+1;
					}
					else restemp = restemp+run.content+" ";
					run = run.next;
				}

				return restemp;
			}
			else{
				return null ;
			}
		}
		
		/**
		 * @param paragraphe - Paragraphe à traiter
		 * @return - Renvoie un tableau de String qui correspond à chaque mots présents dans le paragraphe. ATTENTION : il n'y a plus les espaces.
		 */	
		
		public static String[] chainesdeMots(String paragraphe){
			String[] result = new String[paragraphe.length()];
			int j =0;
			for(int i = 0;i<paragraphe.length();i++){
				char temp = paragraphe.charAt(i);
				if(temp==' '){				
					j++;								
				}			
				else{
					if(result[j]==null) result[j] = new String();
					result[j]=result[j].concat(Character.toString(temp));
					}			
			}
			String[] resultFinal;
			if(result[j]!=null){
				resultFinal = new String[j+1];
				for(int k=0;k<resultFinal.length;k++) resultFinal[k]=result[k];}
			else{
				resultFinal = new String[j];
				for(int k=0;k<resultFinal.length;k++) resultFinal[k]=result[k];}
			return resultFinal;
		}
		
		/**
		 * @param chaine - Chaine de mots présents dans le paragraphe
		 * @param cara - Tableau donnant pour chaque caractère sa largeur
		 * @return - Renvoie la largeur totale du paragraphe.
		 */
		public static double largeurParagraphe(String[] chaine, double[] cara){
			double largeur = 0 ;
			for (int k =0;k<chaine.length;k++) {
				double temp=0;
				for(int j=0;j<chaine[k].length();j++) temp += cara[chaine[k].charAt(j)];
				largeur += temp;
			}
			return largeur;
		}
	
		/**
		 * @param mot - Mot dont on désire la largeur
		 * @param cara - Tableau donnant pour chaque caractère sa largeur
		 * @return - Renvoie la largeur du mot
		 */
		public static double largeurMot(String mot, Polices police){
			double temp = 0 ;
			
			/**
			 * On ne calcule plus la largeur des mots en demandant à java de sommer la largeur des caractères
			 * mais en lui demandant de donner directement la largeur du mot.
			 */
//			HashMap<Character, Double> cara = police.getLargeurs();
//				for(int j=0;j<mot.length();j++){
//					temp += cara.get(mot.charAt(j));
//				}
			
			temp = (new TextLayout(mot, police.getFont(), Polices.frc)).getAdvance();
			return temp;
		}


}
