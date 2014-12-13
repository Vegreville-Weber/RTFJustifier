package editeur;

public class OptimisationAlgorithme {

	public static void main(String[] args) {
		String p = "aaaaaaa aaaaaaa bbbbbbbb bb ccccc aa bbb aaaaaaa bbcc cccc";
		double[] cara = new double[500];
		cara['a']=1;cara['b']=1;cara['c']=1;cara[' ']=1;
		double largeurBloc = 10 ;
		//System.out.println(niceParagraph(p,cara,largeurBloc));
		String[] chaine = chainesdeMots(p);
		for(int k =0;k<chaine.length;k++) System.out.println(chaine[k]);

	}
	
	/**
	 * @param paragraphe - Paragraphe à mettre en page
	 * @param largeur - Tableau donnant pour chaque caractère (ESPACE BLANC inclus) la largeur du caractère
	 * @param largeurBloc - Largeur du bloc où on écrit le paragraphe.
	 * @return - Renvoie le paragraphe mis en page
	 */
	public static String niceParagraph(String paragraphe,double[] largeur,double largeurBloc){
		String[] chaine = chainesdeMots(paragraphe);
		return null;
	}
	
	/**
	 * @param paragraphe - Paragraphe à traiter
	 * @return - Renvoie un tableau de String qui correspond à chaque mots présents dans le paragraphe. ATTENTION les espaces comptent comme des mots.
	 */
	public static String[] chainesdeMots(String paragraphe){
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
