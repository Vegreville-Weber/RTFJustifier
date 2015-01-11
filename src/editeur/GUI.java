package editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class GUI extends JFrame{
	
	//VARIABLES DE LA CLASSE
	static File source = null;
	static File cible = null;
	static Color backgroundColor = new Color(245, 245, 245); //couleur de fond des fen�tres
	static JFrame reglagesAvances = null;//fen�tre des r�glages avanc�s
	static JFrame informationsUtiles = null;//fen�tres des informations utiles
	static JFrame waitWindow = null;//fen�tre pour indiquer que l'op�ration de justification est en cours
	
	

	public GUI(){
		super();
		buildMainFrame();//On initialise la fen�tre principale
		this.setContentPane(buildMainContentPane());
		this.setVisible(true);
	}
 
	private void buildMainFrame(){
		setTitle("RTF Justifier"); //Titre de l'application
		setSize(600,400); //Taille de la fen�tre
		setLocationRelativeTo(null); //centrage de la fen�tre sur l'�cran
		setResizable(false); //On interdit la redimensionnement de la fen�tre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //l'application se ferme lorsqu'on ferme la fen�tre
		setIconImage(new ImageIcon(getClass().getResource("images/RTF.png")).getImage()); //On defini l'icone de l'application
	}
	
	/**
	 * Construit toute la fen�tre principale et ce qui en d�pend
	 * @return Le JPanel
	 */
	private JPanel buildMainContentPane(){
		
		//On d�finit le fond de la fen�tre
		JPanel backPanel = new JPanel();
		backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));
		backPanel.setSize(600,400);
		backPanel.setBackground(backgroundColor);
		backPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		//On d�finit le panneau en haut de la fen�tre (le header)
		JPanel topPanel = new JPanel();
		topPanel.setMinimumSize(new Dimension(560,60));
		topPanel.setOpaque(false);
		
		//On d�finit et on ajoute le titre du programme au header
		JLabel titre = new JLabel("RTF Justifier");
		Font policeTitre =  new Font("helvetica", Font.ITALIC, 20);
		titre.setFont(policeTitre);
		topPanel.add(titre);
		
		//On ajoute le header au fond de la fen�tre
		backPanel.add(topPanel);
		
		//On d�finit le panneau principal (le body) et son layout
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		
		//On d�finit la zone de texte du fichier source et son placement
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
		
		//On d�finit le bouton du fichier source et son placement
		JButton boutonSource = new JButton("Choisir le fichier source");
		c.weightx=0;
		c.insets= new Insets(0, 0, 30, 0);
		c.gridwidth=1;
		c.gridx=2;
		panel.add(boutonSource,c);
		
		//On d�finit la zone de texte du fichier cible et son placement
		JPanel panelLabelCible = new JPanel();
		panelLabelCible.setOpaque(false);
		panelLabelCible.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelLabelCible.setBorder(BorderFactory.createTitledBorder("Fichier Cible (facultatif)"));
		final JLabel labelCible  = new JLabel("Pas de fichier cible");
		panelLabelCible.add(labelCible);
		c.gridx=0;
		c.gridy=1;
		c.insets= new Insets(0, 0, 0, 20);
		c.gridwidth=2;		
		panel.add(panelLabelCible, c);
		
		//On d�finit le bouton cible et son placement
		JButton boutonCible = new JButton("Choisir le fichier cible");
		c.gridx=2;
		c.insets= new Insets(0, 0, 0, 0);
		c.gridwidth=1;		
		panel.add(boutonCible,c);
		
		//On d�finit les actions associ�es aux boutons source et cible
		boutonSource.addActionListener(actionBoutonSource(labelSource, labelCible));
		boutonCible.addActionListener(actionBoutonCible(labelCible));
		
		//On d�finit le bouton de justification et son placement 
		JButton boutonLancement = new JButton("Justifier !");
		c.insets = new Insets(40, 0, 0, 0);
		c.gridx=1;
		c.gridy=2;
		c.gridwidth=3;
		c.fill=0;
		panel.add(boutonLancement,c);
		
		//On d�finit l'action associ�e au bouton de justification
		boutonLancement.addActionListener(actionBoutonJustifier(labelCible));
		 
		//On ajoute le body au fond de la fen�tre
		backPanel.add(panel);
		
		//On d�finit le premier footer qui contiendra les deux boutons
		//de r�glages et d'informations
		JPanel bottomPanel1 = new JPanel();
		bottomPanel1.setLayout( new BoxLayout(bottomPanel1, BoxLayout.LINE_AXIS));
		bottomPanel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		bottomPanel1.setMinimumSize(new Dimension(560,100));
		bottomPanel1.setOpaque(false);
		
		//On d�finit le bouton des r�glages avanc�s et son action
		JButton boutonAvance = new JButton("R�glages Avanc�s");
		boutonAvance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reglagesAvances.setVisible(true);
			}
		});
		
		//On d�finit le bouton d'information et son action
		JButton boutonInfos = new JButton("Informations Utiles");
		boutonInfos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				informationsUtiles.setVisible(true);
			}
		});
		
		//On ajoute et on place les deux boutons dans le premier footer
		bottomPanel1.add(boutonInfos);
		bottomPanel1.add(Box.createHorizontalGlue());
		bottomPanel1.add(boutonAvance);
		
		//On d�finit le deuxi�me footer et on y place sur la gauche
		//le nom des auteurs
		JPanel bottomPanel2 = new JPanel();
		bottomPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		bottomPanel2.setOpaque(false);
		JLabel labelCredits = new JLabel("by Bruno Vegreville and C�me Weber");
		Font policeCredits =  new Font("helvetica", Font.ITALIC, 10);
		labelCredits.setFont(policeCredits);
		bottomPanel2.add(labelCredits, BorderLayout.SOUTH);
		
		
		//On ajoute les deux footers au fond de la fen�tre
		backPanel.add(bottomPanel1);
		backPanel.add(bottomPanel2);
		
		//On lance la cr�ation (mais pas l'affichage) des fen�tres
		//d'informations utiles et de r�glages avanc�s
		initReglagesAvances();
		initInformationsUtiles();
		
		//On renvoit le fond de la fen�tre
		return backPanel;
	}

	/**
	 * D�finit la composition de la fenetre des r�glages avanc�s
	 */
	private void initReglagesAvances(){
		//On initialise la fen�tre
		reglagesAvances = new JFrame();
		reglagesAvances.setTitle("R�glages Avanc�s");
		reglagesAvances.setSize(340, 190);
		reglagesAvances.setLocationRelativeTo(null);
		reglagesAvances.setResizable(false);
		reglagesAvances.setIconImage(new ImageIcon(getClass().getResource("images/RTF.png")).getImage());
		
		//On initialise le JPanel de fond de la fen�tre
		JPanel backPanel2 = new JPanel();
		backPanel2.setBackground(backgroundColor);
		backPanel2.setMaximumSize(new Dimension(340,180));
		backPanel2.setLayout(new BoxLayout(backPanel2, BoxLayout.Y_AXIS));
		backPanel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		//On cr�e une case � cocher pour activer/desactiver la coupure de mots
		JCheckBox boxCoupure = new JCheckBox("Autoriser la coupure de mots");
		boxCoupure.setSelected(Main.coupureMots);
		boxCoupure.setOpaque(false);
		
		//On cr�e le slider de r�glage de la p�nalit� des mots coup�s
		final JSlider sliderPenalites = new JSlider(JSlider.HORIZONTAL, 0, 10,10-Main.penalite);
		if (!boxCoupure.isSelected()) sliderPenalites.setEnabled(false);
		sliderPenalites.setPreferredSize(new Dimension(280, 60));
		sliderPenalites.setMaximumSize(new Dimension(280,60));
		sliderPenalites.setMinorTickSpacing(1);
		sliderPenalites.setMajorTickSpacing(5);
		sliderPenalites.setPaintLabels(true);
		sliderPenalites.setPaintTicks(true);
		sliderPenalites.setOpaque(false);

		//On personnalise les labels du slider
		Hashtable<Integer, JLabel> sliderLabelTable = new Hashtable<Integer, JLabel>();
		sliderLabelTable.put(10, new JLabel("<html><center>Nombreuses<br>c�sures</center></html>") );
		sliderLabelTable.put(0, new JLabel("<html><center>Aucune<br>c�sure</center></html>") );
		sliderLabelTable.put(5, new JLabel("<html><center>C�sures<br>mod�r�es</center></html>") );
		sliderPenalites.setLabelTable( sliderLabelTable );

		//On fait en sorte que si l'on coche/d�coche la case de la coupure de mots
		//elle soit en effet desactiv�e dans l'algorithme. De plus on emp�che la 
		//modification du slider en cas de d�sactivation
		boxCoupure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.coupureMots = !Main.coupureMots;
				sliderPenalites.setEnabled(Main.coupureMots);
			}
		});
		
		//On met � jour la valeur de la penalit� en cas de 
		// d�placement du slider
		sliderPenalites.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Main.penalite=10-sliderPenalites.getValue();
			}
		});
		
		//On cr�e une case � (d�)cocher pour activer/desactiver la justification manuelle
		final JCheckBox boxJustificationManuelle = new JCheckBox("Justifier en ajoutant des espaces");
		boxJustificationManuelle.setSelected(Main.justificationManuelle);
		boxJustificationManuelle.setOpaque(false);
		
		//Idem pour la justification logicielle
		final JCheckBox boxJustificationLogicielle = new JCheckBox("Justification logicielle des lignes");
		boxJustificationLogicielle.setSelected(Main.justificationLogicielle);
		boxJustificationLogicielle.setOpaque(false);
		
		//On fait en sorte que l'on ne puisse pas cocher en m�me temps les
		//deux cases de justification. Et on met � jour les variables globales
		//utilis�es dans l'algorithme.
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
		
		//idem
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
		
		//On cr�e un nouveau Panel dans lequel on place le slider
		// afin de pouvoir le positionner � droite
		JPanel panelSlider = new JPanel();
		panelSlider.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelSlider.add(sliderPenalites);
		panelSlider.setSize(new Dimension(250,50));
		panelSlider.setOpaque(false);
		
		//On ajoute tout les composants au JPanel repr�sentant 
		//le fond de la fen�tre
		backPanel2.add(boxCoupure);
		backPanel2.add(panelSlider);
		backPanel2.add(boxJustificationManuelle);
		backPanel2.add(boxJustificationLogicielle);
		
		
		//On associe le JPanel ainsi cr�e � la fen�tre
		reglagesAvances.setContentPane(backPanel2);
		
	}

	/**
	 * D�finit la composition de la fenetre des informations utiles
	 */
	private void initInformationsUtiles(){
		
		//On d�finit les r�glages de la fen�tre
		informationsUtiles = new JFrame();
		informationsUtiles.setTitle("Informations Utiles");
		informationsUtiles.setSize(500,300);
		informationsUtiles.setResizable(false);
		informationsUtiles.setLocationRelativeTo(null);
		informationsUtiles.setIconImage(new ImageIcon(getClass().getResource("images/RTF.png")).getImage());
		
		//On d�finit le JPanel du fond de la fen�tre
		JPanel backPanelInfos = new JPanel();
		backPanelInfos.setBackground(backgroundColor);
		backPanelInfos.setLayout(new BoxLayout(backPanelInfos, BoxLayout.Y_AXIS));
		backPanelInfos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		//On d�finit un panneau destin� � afficher un fichier RTF
		//expliquant comment utiliser le logiciel
		JEditorPane display = new JEditorPane ();
		display.setContentType("text/rtf; charset=EUC-JP");
		display.setEditable (false);
		
		//On lit le fichier RTF en question
	    try {
	    	BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Informations.rtf")));
	        StringBuilder sb = new StringBuilder(); 
	        String line = br.readLine();
	    
	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        br.close();
	        
	        display.setText(sb.toString());
	    } catch (IOException e) {
			e.printStackTrace();
		} 
	    
	    //On encapsule texte dans un panneau � glissi�res
	    //que l'on ajoute au fond de la fen�tre
		JScrollPane scroll = new JScrollPane (display);
	 	scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	 	backPanelInfos.add(scroll);
	 	
	 	//On associe le fond de la fen�tre � la fen�tre
	 	informationsUtiles.setContentPane(backPanelInfos);
	}
	
	/**
	 * Ouvre un document avec l'application associ�e par d�faut.
	 * Pr�sent ici surtout pour la clart� du code (pas de try/catch..)
	 * @param document Le document � ouvrir
	 */
	public static void open(File document){
	   try{
		Desktop dt = Desktop.getDesktop();
	    dt.open(document);}
	   catch(IOException e1){}
	}
	
	
	/**
	 * Modifie le chemin d'acc�s affich� dans le label pour que la fin du chemin
	 * d'acc�s ne soit pas coup�.
	 * @param label Le Jlabel sur lequel on souhaite appliquer le chemin
	 * @param path le chemin complet qui sera am�nag�
	 */
	private static void setRightPath(JLabel label, String path){

 	   label.setText(path);
 	   int taille = label.getFontMetrics(label.getFont()).stringWidth(path);
 	   boolean alreadyCut = false; //savoir si on est deja rentr� dans la boucle pour savoir si on enleve de suite le ...\
 	   String separator  = File.separator; //la variable separator sert � ne pas avoir une boucle propre � un syst�me d'exploitation - Par d�faut cas windows.
 	   while (taille > 325){ 
 		   String[] paths;
 		   if(alreadyCut){ //si on a deja ajout� .../ on l'enleve.
 			   path = path.split(Pattern.quote(separator),2)[1];
 		   }
 		   if(path.contains(separator)) paths= path.split(Pattern.quote(separator), 2); //Le Pattern.quote sert � corriger un bug du au fait qu'on peut pas spliter une string selon un backslash
 		   else break;  // si il reste rien � couper, tant pis.
 		   path = "..."+separator + paths[1];
 		   label.setText(path);
 		   taille = label.getFontMetrics(label.getFont()).stringWidth(path);            		   
 		   alreadyCut=true;
 		   
 	   }
	}
	
	
	/**
	 * D�finit l'action associ�e au bouton "Choisir le fichier source"
	 * @return un ActionListener repr�sentant cette action
	 */
	private static ActionListener actionBoutonSource(JLabel labelSource, JLabel labelCible){
		ActionListener action = new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	JFileChooser chooser = new JFileChooser();
            	chooser.setFileFilter(new FileNameExtensionFilter(".rtf", "rtf")); 
            	chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            	int option = chooser.showOpenDialog(new JFrame());
            	if (option == JFileChooser.APPROVE_OPTION) {
            	   source = chooser.getSelectedFile();
            	   setRightPath(labelSource, source.getAbsolutePath());  
            	   
            	   //On r�initialise la cible
            	   cible=null;
            	   labelCible.setText("Pas de fichier cible");
            
            	}
            	
            }
            
        };
        return action;
	}
	
	
	/**
	 * D�finit l'action associ�e au bouton "Choisir le fichier cible"
	 * @return un ActionListener repr�sentant cette action
	 */
	private static ActionListener actionBoutonCible(JLabel labelCible){
		ActionListener action = new ActionListener() {
			 
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
        };
        
        return action;
	}

	
	/**
	 * D�finit l'action associ�e au bouton "Justifier"
	 * @return un ActionListener repr�sentant cette action
	 */
	private static ActionListener actionBoutonJustifier(JLabel labelCible){
		ActionListener action = new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	//On v�rifie qu'une source ait bien �t� d�finie
            	if (source==null){
            		JOptionPane.showMessageDialog(null, "Vous devez choisir un fichier source!");
            		return;
            	}
            	
            	//On regarde si une cible a �t� d�finie, et sinon on indique celle par d�faut
            	if(cible==null){
             	   	String names[]=source.getAbsolutePath().split(Pattern.quote(File.separator));
            		JOptionPane.showMessageDialog(null, "Vous n'avez pas choisi de fichier cible!\nLe fichier �crit sera " +names[names.length-1].replaceAll(".rtf", "-justified.rtf") );
            		cible = new File(source.getAbsolutePath().replaceAll(".rtf", "-justified.rtf"));
            		setRightPath(labelCible, cible.getAbsolutePath());
            	}
            	
            	//On d�finit un nouveau Thread pour la cr�ation du fichier justifi�.
            	//Ainsi l'interface graphique continue � r�pondre
                Thread t = new Thread(){
                	public void run(){
                		try {
        					Main.run(source.getAbsolutePath(), cible.getAbsolutePath());
        				} catch (IOException e1) {
        					e1.printStackTrace();
        				}
                		
                		//On demande � Swing de fermer la fen�tre d'attente
                		SwingUtilities.invokeLater(new Runnable(){
                			public void run(){
                				if (GUI.waitWindow!=null) GUI.waitWindow.setVisible(false);
                			}
                		});
            	    	
            	    	//Ouvre une fenetre pour indiquer la fin du programme et proposer d'ouvrir le RTF
                    	Object[] options = {"Oui","Non"};
                    	int choixOuvrir = JOptionPane.showOptionDialog(null,
                    		 	"Op�ration termin�e!\n Voulez-vous ouvrir le document RTF produit?",
                    			"Op�ration termin�e",
                    			JOptionPane.YES_NO_OPTION,
                    		    JOptionPane.QUESTION_MESSAGE,
                    		    null,     //do not use a custom Icon
                    		    options,  //the titles of buttons
                    		    options[0]); //default button title
                    	if (choixOuvrir==0) open(cible);
            	    }
                };
                
                //On demande � Swing d'ouvrir la fen�tre d'attente d�s qu'il en aura le temps.
            	SwingUtilities.invokeLater(new Runnable() {
            	    @Override
            	    public void run() {
            	    	waitWindow = new JFrame();
                    	waitWindow.setSize(300, 120);
                    	waitWindow.setLocationRelativeTo(null);
                    	waitWindow.setResizable(true);
                    	waitWindow.setName("Travail en cours");
                    	waitWindow.setIconImage(new ImageIcon(getClass().getResource("images/RTF.png")).getImage());
                    	JPanel backPanelWaiting = new JPanel();
                    	backPanelWaiting.setMaximumSize(new Dimension(300,120));
                    	backPanelWaiting.setLayout(new BoxLayout(backPanelWaiting, BoxLayout.PAGE_AXIS));
                    	backPanelWaiting.setBackground(backgroundColor);
                    	backPanelWaiting.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    	JLabel labelWaiting = new JLabel("<html><center>Votre fichier est en cours de pr�paration.</br>Veuillez patientez...</center></html>");
                    	labelWaiting.setOpaque(false);
                		backPanelWaiting.setVisible(true);
                    		
                    	JProgressBar progressBarWaiting = new JProgressBar(0,100);
                    	progressBarWaiting.setOpaque(false);
                    	progressBarWaiting.setIndeterminate(true);
                    	backPanelWaiting.add(labelWaiting);
                    	backPanelWaiting.add(Box.createVerticalGlue());
                    	backPanelWaiting.add(progressBarWaiting);
                    	waitWindow.setContentPane(backPanelWaiting);
                    	waitWindow.setVisible(true);
            	    }
            	});   
            	
            	//On d�marre le thread contenant l'action principale de justification
            	t.start();
            }
        };
        
        return action;
	}
}



