package editeur;

import java.awt.Font;

public class OptimisationAlgorithme {

	private static final double INFINI = Double.MAX_VALUE;

	public static void main(String[] args) {
		String p = "aaaaaaa aaaaaaa bbbbbbbb bb ccccc aa bbb aaaaaaa bbcc cccc cbebycbebceb cb cbeycbbcey bcecbeybcecyiyc cbdh dhcb cb hd cdh cbd hcbd cbdh cdhhccbdcdc cdcbdbcydcb cdbycbdcbydcbyd cbcd dd";
		/*double[] cara = new double[500];
		cara['a']=1;cara['b']=1;cara['c']=1;cara[' ']=1;
		double largeurBloc = 10 ;
		System.out.println(niceParagraph(p,cara,largeurBloc));*/
		
		Font f = new Font("SansSerif", Font.PLAIN, 12);
    	Polices pol = new Polices(f);
    	double[] carareels = pol.getLargeurs();
    	double largeurBlocReel = 150;
    	System.out.println(niceParagraph(p,carareels,largeurBlocReel));
		

	}
	
	/**
	 * @param paragraphe - Paragraphe � mettre en page EN CONSIDERANT UN SEUL ESPACE ENTRE CHAQUE MOT
	 * @param largeur - Tableau donnant pour chaque caract�re (ESPACE BLANC inclus) la largeur du caract�re
	 * @param largeurBloc - Largeur du bloc o� on �crit le paragraphe.
	 * @return - Renvoie le paragraphe mis en page
	 */
	public static String niceParagraph(Font f, String paragraphe,double largeurBloc){
		Polices pol = new Polices(f);
    	double[] largeur = pol.getLargeurs();
    	return niceParagraph(paragraphe,largeur,largeurBloc);
	}
	
	/**
	 * @param paragraphe - Paragraphe � mettre en page EN CONSIDERANT UN SEUL ESPACE ENTRE CHAQUE MOT
	 * @param largeur - Tableau donnant pour chaque caract�re (ESPACE BLANC inclus) la largeur du caract�re
	 * @param largeurBloc - Largeur du bloc o� on �crit le paragraphe.
	 * @return - Renvoie le paragraphe mis en page
	 */
	//LARGEURBLOC DOIT ETRE PLUS GRAND QUE LE PLUS GRAND DES MOTS DU PARAGRAPHE
	public static String niceParagraph(String paragraphe,double[] largeur,double largeurBloc){
		String[] chaine = chainesdeMots(paragraphe);
		int nombreDeMots = chaine.length;
		double[][] costs = new double[nombreDeMots + 1][nombreDeMots + 1];
		double[] costFinal = new double[nombreDeMots + 1];
		double[][] espaces = new double[nombreDeMots + 1][nombreDeMots + 1];
		int[] p = new int[nombreDeMots + 1];
		
		double blank = largeur[' '];
		for (int i = 1; i <= nombreDeMots; i++) {
			espaces[i][i] = largeurBloc - largeurMot(chaine[i-1],largeur);
			for (int j = i + 1; j <= nombreDeMots; j++) {
				espaces[i][j] = espaces[i][j - 1] - largeurMot(chaine[j-1],largeur) - blank;
			}
		}

		for (int i = 1; i <= nombreDeMots; i++) {
			for (int j = i; j <= nombreDeMots; j++) {
				if (espaces[i][j] < 0)
					costs[i][j] = INFINI;
				else if (j == nombreDeMots && espaces[i][j] >= 0)
					costs[i][j] = 0;
				else
					costs[i][j] = espaces[i][j] * espaces[i][j] * espaces[i][j];
			}
		}
		
		costFinal[0] = 0;
		for (int j = 1; j <= nombreDeMots; j++) {
			costFinal[j] = INFINI;
			for (int i = 1; i <= j; i++) {
				if (costFinal[i-1] != INFINI && costs[i][j] != INFINI && (costFinal[i-1] + costs[i][j] < costFinal[j])) {
					costFinal[j] = costFinal[i - 1] + costs[i][j];
					p[j] = i;
				}
			}
		}
		String resultat = new String();
		int pointeur = nombreDeMots;
		while (pointeur >= 1) {
			String temp = new String();
			for (int k = p[pointeur]; k < pointeur; k++) {
				temp+=(chaine[k-1]+" ");
			}
			temp+=chaine[pointeur-1];
			temp+="\n";
			resultat = temp+=resultat;
			pointeur = p[pointeur]-1;
		}
		return resultat;
	}
	
	/**
	 * @param paragraphe - Paragraphe � traiter
	 * @return - Renvoie un tableau de String qui correspond � chaque mots pr�sents dans le paragraphe. ATTENTION les espaces comptent comme des mots.
	 */
	public static String[] chainesdeMotsAvecEspaces(String paragraphe){
		String[] result = new String[paragraphe.length()];
		int j =0; boolean isPreviousBlank = true;
		for(int i = 0;i<paragraphe.length();i++){
			char temp = paragraphe.charAt(i);
			if(temp!=' '){				
				if(isPreviousBlank&&j!=0) {
					isPreviousBlank = false;
					j++;
				}				
			}
			else{
				if(!isPreviousBlank||j==0){				
					isPreviousBlank =true;
					j++;}				
			}
			if(result[j]==null) result[j] = new String();
			result[j]=result[j].concat(Character.toString(temp));			
		}
		String[] resultFinal = new String[j+1];
		for(int k=0;k<resultFinal.length;k++) resultFinal[k]=result[k];
		return resultFinal;
	}
	
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
		String[] resultFinal = new String[j+1];
		for(int k=0;k<resultFinal.length;k++) resultFinal[k]=result[k];
		return resultFinal;
	}
	
	/**
	 * @param chaine - Chaine de mots pr�sents dans le paragraphe
	 * @param cara - Tableau donnant pour chaque caract�re sa largeur
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
	
	public static double largeurMot(String mot, double[] cara){
		double temp = 0 ;
			for(int j=0;j<mot.length();j++)	temp += cara[mot.charAt(j)];
		return temp;
	}
	

}
