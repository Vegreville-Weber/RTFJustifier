package editeur;

/**
 * La classe mots est utilis�e dans l'algorithme avec hyphenation
 * Elle permet pour chaque mot du paragraphe de se souvenir de differentes informations utiles lors de la c�sure
 *
 */
public class Mots {
	public Mots next; //Le mot suivant
	public String content; //Le mot � proprement parl�
	public boolean isHyph; //true si l'algorithme a coup� le mot ici
	public boolean isEnd ; //true si l'algorithme passe � la ligne suivante apr�s ce mot
	public String left; // partie gauche du mot si il a �t� coup�
	public String right; // partie droite du mot si il a �t� coup�
	public int place; //place dans le paragraphe
	public int cut; //endroit o� couper si la taille du mot est suffisante.
	
	public Mots(String content,int place){
		this.next = null;
		this.content = content;
		this.isHyph = false;
		this.isEnd = false;
		this.left=null;
		this.right=null;
		this.place=place;
		if (this.content.length()>4){ //On ne coupe pas des mots dont la longueur est trop faible
			this.cut = HyphenationAlgorithme.coupure(this.content);
		}
		else this.cut = -1;
		
	}
	
	public void setNext(Mots next){
		this.next = next;
		
	}
}
