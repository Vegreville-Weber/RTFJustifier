package editeur;

public class OptimisationAlgorithme {

	private static final double INFINI = Double.MAX_VALUE;

	public static void main(String[] args) {
		String p = "aaaaaaa aaaaaaa bbbbbbbb bb ccccc aa bbb aaaaaaa bbcc cccc";
		double[] cara = new double[500];
		cara['a']=1;cara['b']=1;cara['c']=1;cara[' ']=1;
		double largeurBloc = 10 ;
		System.out.println(niceParagraph(p,cara,largeurBloc));
		/*String[] chaine = chainesdeMots(p);
		for(int k =0;k<chaine.length;k++) System.out.println(chaine[k]);*/

	}
	
	/**
	 * @param paragraphe - Paragraphe à mettre en page EN CONSIDERANT UN SEUL ESPACE ENTRE CHAQUE MOT
	 * @param largeur - Tableau donnant pour chaque caractère (ESPACE BLANC inclus) la largeur du caractère
	 * @param largeurBloc - Largeur du bloc où on écrit le paragraphe.
	 * @return - Renvoie le paragraphe mis en page
	 */
	
	//LARGEUR DOIT ETRE PLUS GRAND QUE LE PLUS GRAND DES MOTS DU PARAGRAPHE
	public static String niceParagraph(String paragraphe,double[] largeur,double largeurBloc){
		String[] chaine = chainesdeMots(paragraphe);
		int nombreDeMots = chaine.length;
		double[][] costs = new double[nombreDeMots + 1][nombreDeMots + 1];
		double[] costFinal = new double[nombreDeMots + 1];
		double[][] espaces = new double[nombreDeMots + 1][nombreDeMots + 1];
		int[] p = new int[nombreDeMots + 1];

		for (int i = 1; i <= nombreDeMots; i++) {
			espaces[i][i] = largeurBloc - chaine[i - 1].length();
			for (int j = i + 1; j <= nombreDeMots; j++) {
				espaces[i][j] = espaces[i][j - 1] - chaine[j - 1].length() - 1;
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
			String temp = "\n";;
			System.out.println(temp);
			for (int k = p[pointeur]; k < pointeur; k++) {
				temp+=(chaine[k-1]+" ");
			}
			temp+=chaine[pointeur-1];
			resultat = temp+=resultat;
			pointeur = p[pointeur]-1;
		}
		return resultat;
	}
	
	/**
	 * @param paragraphe - Paragraphe à traiter
	 * @return - Renvoie un tableau de String qui correspond à chaque mots présents dans le paragraphe. ATTENTION les espaces comptent comme des mots.
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

}
