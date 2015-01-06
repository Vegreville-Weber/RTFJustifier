package editeur;

import java.awt.Font;
import java.awt.font.TextLayout;
import java.util.HashMap;
import java.util.LinkedList;
import static com.tutego.jrtf.Rtf.rtf;

public class OptimisationAlgorithme {

	private static final double INFINI = Double.MAX_VALUE;

	public static void main(String[] args) {
		String p = "aaaaaaa aaaaaaa bbbbbbbb bb ccccc aa bbb aaaaaaa bbcc cccc cbebycbebceb cb cbeycbbcey bcecbeybcecyiyc cbdh dhcb cb hd cdh cbd hcbd cbdh cdhhccbdcdc cdcbdbcydcb cdbycbdcbydcbyd cbcd dd";
		/*double[] cara = new double[500];
		cara['a']=1;cara['b']=1;cara['c']=1;cara[' ']=1;
		double largeurBloc = 10 ;
		System.out.println(niceParagraph(p,cara,largeurBloc));*/
		String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer turpis mi, dignissim et dignissim vitae, auctor a lectus. Aenean cursus erat ut magna ultrices mollis. Cras turpis urna, tempus tincidunt dictum non, gravida posuere augue. Ut dapibus ante non sapien facilisis suscipit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed et felis ligula. Sed at mollis est. Cras vestibulum libero ac egestas iaculis. Sed congue nisl sit amet enim tempor sagittis. Donec rhoncus venenatis metus sollicitudin ultrices. Etiam sit amet tincidunt ligula. Proin euismod libero in lorem accumsan, eu lacinia tellus sagittis.";
		Font f = new Font("SansSerif", Font.PLAIN, 12);
    	double largeurBlocReel = 481.9;
    	System.out.println(niceParagraph(f,lorem,largeurBlocReel));

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
		HashMap<Character,Double> largeur = police.getLargeurs();
		if(paragraphe.trim().length()==0) return paragraphe; //paragraphe avec que des blancs.
		if(paragraphe.isEmpty()) return " ";
		String[] chaine = chainesdeMots(paragraphe); //chaine[k] : k-ieme mot du paragraphe
		
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
		
		double blank = largeur.get(' ');
		//largeur en point d'un espace.
		
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
		for (int j = 1; j <= nombreDeMots; j++) {
			costFinal[j] = INFINI;
			for (int i = 1; i <= j; i++) {
				if (costFinal[i-1] != INFINI && costs[i][j] != INFINI && (costFinal[i-1] + costs[i][j] < costFinal[j])) {
					costFinal[j] = costFinal[i - 1] + costs[i][j];
					p[j] = i; //on retient le saut de ligne.
				}
			}
		}
		String resultat = new String();
		int pointeur = nombreDeMots;
		boolean lastlign=true;
		while (pointeur >= 1) { //on construit le paragraphe final.
			String temp = new String();
			for (int k = p[pointeur]; k < pointeur; k++) {
				temp+=(chaine[k-1]+" ");
			}
			temp+=chaine[pointeur-1];
			if (!lastlign && Main.justificationManuelle) {
				String[] mots = chainesdeMots(temp);
				int nbrSpace = (int) Math.floor((largeurBloc - largeurMot(temp,
						police)) / blank);
				int nbrBoucle = nbrSpace / (mots.length - 1);
				int reste = nbrSpace % (mots.length - 1);
				temp = "";
				for (int i = 0; i < mots.length - 1; i++) {
					temp += mots[i];
					for (int j = 0; j <= nbrBoucle; j++)
						temp += " ";
					if (reste > 0) {
						temp += " ";
						reste--;
					}

				}
				temp += mots[mots.length - 1];
				
			}
			if(!lastlign)temp+="\\line ";
			else{
				temp+=System.lineSeparator();
				lastlign=false;}
			
			resultat = temp+=resultat;
			pointeur = p[pointeur]-1;
		}
		return resultat;
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
//		HashMap<Character, Double> cara = police.getLargeurs();
//			for(int j=0;j<mot.length();j++){
//				temp += cara.get(mot.charAt(j));
//			}
		
		temp = (new TextLayout(mot, police.getFont(), Polices.frc)).getAdvance();
		return temp;
	}
	

}
