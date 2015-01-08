package editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JWindow;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class GUI extends JFrame{
	static File source = null;
	static File cible = null;
	static JFrame reglagesAvances = null;
	static Color backgroundColor = new Color(245, 245, 245);
	
	public static void main(String[] args) {
		GUI gui= new GUI();
	}
	public GUI(){
		super();
 
		build();//On initialise notre fenêtre
		this.setContentPane(buildContentPane());
		this.setVisible(true);
	}
 
	private void build(){
		setTitle("RTF Justifier"); //Titre de l'application
		setSize(600,400); //Taille de la fenêtre
		setLocationRelativeTo(null); //centrage de la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //l'application se ferme lorsqu'on ferme la fenêtre
	}
	
	private JPanel buildContentPane(){
		JPanel backPanel = new JPanel();
		backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));
		backPanel.setSize(600,400);
		backPanel.setBackground(backgroundColor);
		backPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel topPanel = new JPanel();
		topPanel.setMinimumSize(new Dimension(560,60));
		topPanel.setOpaque(false);
		
		JLabel titre = new JLabel("RTF Justifier");
		Font policeTitre =  new Font("helvetica", Font.ITALIC, 20);
		titre.setFont(policeTitre);
		topPanel.add(titre);
		
		backPanel.add(topPanel);
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridBagLayout());
		panel.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel panelLabelSource = new JPanel();
		panelLabelSource.setOpaque(false);
		panelLabelSource.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelLabelSource.setBorder(BorderFactory.createTitledBorder("Fichier Source"));
		final JLabel labelSource  = new JLabel("Pas de fichier source");
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		c.weightx=1;
		c.insets= new Insets(0, 0, 30, 20);
		c.gridwidth=2;		
		panelLabelSource.add(labelSource);
		panel.add(panelLabelSource,c);
		
		JButton boutonSource = new JButton("Choisir le fichier source");
		
		c.weightx=0;
		c.insets= new Insets(0, 0, 30, 0);
		c.gridwidth=1;
		c.gridx=2;
		panel.add(boutonSource,c);
		
		
		JPanel panelLabelCible = new JPanel();
		panelLabelCible.setOpaque(false);
		panelLabelCible.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelLabelCible.setBorder(BorderFactory.createTitledBorder("Fichier Cible"));
		
		final JLabel labelCible  = new JLabel("Pas de fichier cible");
		panelLabelCible.add(labelCible);
		c.gridx=0;
		c.gridy=1;
		c.insets= new Insets(0, 0, 0, 20);
		c.gridwidth=2;		
		panel.add(panelLabelCible, c);
		
		JButton boutonCible = new JButton("Choisir le fichier cible");
		boutonSource.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	JFileChooser chooser = new JFileChooser();
            	chooser.setFileFilter(new FileNameExtensionFilter(".rtf", "rtf")); 
            	chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            	int option = chooser.showOpenDialog(new JFrame());
            	if (option == JFileChooser.APPROVE_OPTION) {
            	   source = chooser.getSelectedFile();
            	   setRightPath(labelSource, source.getAbsolutePath());  
            	   
            	   //On réinitialise la cible
            	   cible=null;
            	   labelCible.setText("Pas de fichier cible");
            
            	}
            	
            }
        });
		boutonCible.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	JFileChooser chooser = new JFileChooser();
            	chooser.setFileFilter(new FileNameExtensionFilter(".rtf", "rtf")); 
            	chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            	int option = chooser.showOpenDialog(new JFrame());
            	if (option == JFileChooser.APPROVE_OPTION) {
            	   cible = chooser.getSelectedFile();
            	   setRightPath(labelCible, cible.getAbsolutePath());                	
            	}
            	
            }
        });
		c.gridx=2;
		c.insets= new Insets(0, 0, 0, 0);
		c.gridwidth=1;		
		panel.add(boutonCible,c);
		

		JButton boutonLancement = new JButton("Justifier !");
		boutonLancement.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	if (source==null){
            		JOptionPane.showMessageDialog(null, "Vous devez choisir un fichier source!");
            		return;
            	}
            	if(cible==null){
            		String separator  = "\\"; //la variable separator sert à ne pas avoir une boucle propre à un système d'exploitation - Par défaut cas windows.
             	   	if(source.getAbsolutePath().contains("/")) separator = "/"; //cas Unix
             	   	String names[]=source.getAbsolutePath().split(separator);
            		JOptionPane.showMessageDialog(null, "Vous n'avez pas choisi de fichier cible!\nLe fichier écrit sera " +names[names.length-1].replaceAll(".rtf", "-justified.rtf") );
            		cible = new File(source.getAbsolutePath().replaceAll(".rtf", "-justified.rtf"));
            		setRightPath(labelCible, cible.getAbsolutePath());
            	}
 
            	try {
					Main.run(source.getAbsolutePath(), cible.getAbsolutePath());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            	//Ouvre une fenetre pour indiquer la fin du programme et proposer d'ouvrir le RTF
            	Object[] options = {"Oui","Non"};
            	int choixOuvrir = JOptionPane.showOptionDialog(null,
            		 	"Opération terminée!\n Voulez-vous ouvrir le document RTF produit?",
            			"Opération terminée",
            			JOptionPane.YES_NO_OPTION,
            		    JOptionPane.QUESTION_MESSAGE,
            		    null,     //do not use a custom Icon
            		    options,  //the titles of buttons
            		    options[0]); //default button title
            	if (choixOuvrir==0) open(cible);
            	
            	
            }
        });
		c.insets = new Insets(40, 0, 0, 0);
		c.gridx=1;
		c.gridy=2;
		c.gridwidth=3;
		c.fill=0;
		panel.add(boutonLancement,c);
		 
		backPanel.add(panel);
		
		JPanel bottomPanel1 = new JPanel();
		bottomPanel1.setLayout( new FlowLayout(FlowLayout.RIGHT));
		bottomPanel1.setMinimumSize(new Dimension(560,100));
		bottomPanel1.setOpaque(false);
		
		JButton boutonAvance = new JButton("Réglages Avancés");
		boutonAvance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "FenÃªtre en construction");
				reglagesAvances.setVisible(true);
			}
			
			
		});
		
		bottomPanel1.add(boutonAvance);
		
		JPanel bottomPanel2 = new JPanel();
		bottomPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		bottomPanel2.setOpaque(false);
		JLabel labelCredits = new JLabel("by Bruno Vegreville and Côme Weber");
		Font policeCredits =  new Font("helvetica", Font.ITALIC, 10);
		labelCredits.setFont(policeCredits);
		
		bottomPanel2.add(labelCredits, BorderLayout.SOUTH);
		
		
		backPanel.add(bottomPanel1);
		backPanel.add(bottomPanel2);
		
		initReglagesAvances();
		
		return backPanel;
	}
	
	private void initReglagesAvances(){
		reglagesAvances = new JFrame();
		JPanel backPanel2 = new JPanel();
		backPanel2.setBackground(backgroundColor);
		reglagesAvances.setTitle("Réglages Avancés");
		reglagesAvances.setSize(300, 130);
		reglagesAvances.setLocationRelativeTo(null);
		reglagesAvances.setResizable(false);
		backPanel2.setMinimumSize(reglagesAvances.getSize());
		backPanel2.setLayout(new BoxLayout(backPanel2, BoxLayout.Y_AXIS));
		backPanel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		JCheckBox boxCoupure = new JCheckBox("Autoriser la coupure de mots");
		boxCoupure.setSelected(Main.coupureMots);
		boxCoupure.setOpaque(false);
		boxCoupure.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.coupureMots = !Main.coupureMots;
			}
		});
		final JCheckBox boxJustificationManuelle = new JCheckBox("Justifier en ajoutant des espaces");
		boxJustificationManuelle.setSelected(Main.justificationManuelle);
		boxJustificationManuelle.setOpaque(false);
		final JCheckBox boxJustificationLogicielle = new JCheckBox("Justification logicielle des lignes");
		boxJustificationLogicielle.setSelected(Main.justificationLogicielle);
		boxJustificationLogicielle.setOpaque(false);
		boxJustificationManuelle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.justificationManuelle = !Main.justificationManuelle;
				if (boxJustificationLogicielle.isSelected()) {
					Main.justificationLogicielle = false;
					boxJustificationLogicielle.setSelected(false);
				}
			}
		});
		boxJustificationLogicielle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.justificationLogicielle = !Main.justificationLogicielle;
				if (boxJustificationManuelle.isSelected()) {
					Main.justificationManuelle = false;
					boxJustificationManuelle.setSelected(false);
				}
			}
		});
		
		
		
		backPanel2.add(boxCoupure);
		backPanel2.add(boxJustificationManuelle);
		backPanel2.add(boxJustificationLogicielle);
		
		
		reglagesAvances.setContentPane(backPanel2);
	}
	
	public static void open(File document){
	   try{
		Desktop dt = Desktop.getDesktop();
	    dt.open(document);}
	   catch(IOException e1){}
	}
	/**
	 * Modifier le chemin d'accès affiché dans le label pour qu'il ne soit pas coupé
	 * dans la fenêtre
	 * @param label Le Jlabel sur lequel on souhaite appliquer le chemin
	 * @param path le chemin complet qui sera aménagé
	 */
	private static void setRightPath(JLabel label, String path){

 	   label.setText(path);
 	   int taille = label.getFontMetrics(label.getFont()).stringWidth(path);
 	   boolean alreadyCut = false; //savoir si on est deja rentré dans la boucle pour savoir si on enleve de suite le ...\
 	   String separator  = "\\"; //la variable separator sert à ne pas avoir une boucle propre à un système d'exploitation - Par défaut cas windows.
 	   if(path.contains("/")) separator = "/"; //cas Unix
 	   while (taille > 325){ 
 		   String[] paths;
 		   if(alreadyCut){ //si on a deja ajouté .../ on l'enleve.
 			   path = path.split(Pattern.quote(separator),2)[1];
 		   }
 		   if(path.contains(separator)) paths= path.split(Pattern.quote(separator), 2); //Le Pattern.quote sert à corriger un bug du au fait qu'on peut pas spliter une string selon un backslash
 		   else break;  // si il reste rien à couper, tant pis.
 		   path = "..."+separator + paths[1];
 		   label.setText(path);
 		   taille = label.getFontMetrics(label.getFont()).stringWidth(path);            		   
 		   alreadyCut=true;
 		   
 	   }
	}
}
