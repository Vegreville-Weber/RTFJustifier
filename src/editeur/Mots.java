package editeur;

public class Mots {
	public Mots next;
	public String content;
	public boolean isHyph;
	public boolean isEnd ;
	public String left;
	public String right;
	public int place;
	
	public Mots(String content,int place){
		this.next = null;
		this.content = content;
		this.isHyph = false;
		this.isEnd = false;
		this.left=null;
		this.right=null;
		this.place=place;
		
	}
	
	public void setNext(Mots next){
		this.next = next;
		
	}
}
