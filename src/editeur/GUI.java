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
	static File source = null;
	static File cible = null;
	static JFrame reglagesAvances = null;
	static JFrame informationsUtiles = null;
	static Color backgroundColor = new Color(245, 245, 245);
	static JFrame waitWindow;

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
		setIconImage(new ImageIcon(getClass().getResource("images/RTF.png")).getImage()); //On defini l'icone de l'application
	}
	
	/**
	 * Construit toute la fenêtre principale et ce qui en dépend
	 * @return Le JPanel
	 */
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
		panelLabelCible.setBorder(BorderFactory.createTitledBorder("Fichier Cible (facultatif)"));
		
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
             	   	String names[]=source.getAbsolutePath().split(Pattern.quote(File.separator));
            		JOptionPane.showMessageDialog(null, "Vous n'avez pas choisi de fichier cible!\nLe fichier écrit sera " +names[names.length-1].replaceAll(".rtf", "-justified.rtf") );
            		cible = new File(source.getAbsolutePath().replaceAll(".rtf", "-justified.rtf"));
            		setRightPath(labelCible, cible.getAbsolutePath());
            	}
    	
                Thread t = new Thread(){
                	public void run(){
                		try {
        					Main.run(source.getAbsolutePath(), cible.getAbsolutePath());
        				} catch (IOException e1) {
        					e1.printStackTrace();
        				}
                		
                		SwingUtilities.invokeLater(new Runnable(){
                			public void run(){
                				if (GUI.waitWindow!=null) GUI.waitWindow.setVisible(false);
                			}
                		});
            	    	
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
                };
                
            	SwingUtilities.invokeLater(new Runnable() {
            	    @Override
            	    public void run() {
            	    	JFrame waitingWindow = new JFrame();
                    	waitingWindow.setSize(300, 120);
                    	waitingWindow.setLocationRelativeTo(null);
                    	waitingWindow.setResizable(true);
                    	waitingWindow.setName("Travail en cours");
                    	waitingWindow.setIconImage(new ImageIcon(getClass().getResource("images/RTF.png")).getImage());
                    	JPanel backPanelWaiting = new JPanel();
                    	backPanelWaiting.setMaximumSize(new Dimension(300,120));
                    	backPanelWaiting.setLayout(new BoxLayout(backPanelWaiting, BoxLayout.PAGE_AXIS));
                    	backPanelWaiting.setBackground(backgroundColor);
                    	backPanelWaiting.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    	JLabel labelWaiting = new JLabel("<html><center>Votre fichier est en cours de préparation.</br>Veuillez patientez...</center></html>");
                    	labelWaiting.setOpaque(false);
                		backPanelWaiting.setVisible(true);
                    		
                    	JProgressBar progressBarWaiting = new JProgressBar(0,100);
                    	progressBarWaiting.setOpaque(false);
                    	progressBarWaiting.setIndeterminate(true);
                    	backPanelWaiting.add(labelWaiting);
                    	backPanelWaiting.add(Box.createVerticalGlue());
                    	backPanelWaiting.add(progressBarWaiting);
                    	waitingWindow.setContentPane(backPanelWaiting);
                    	waitingWindow.setVisible(true);
                    	GUI.waitWindow = waitingWindow;
            	    }
            	});   
            	
            	t.start();
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
		bottomPanel1.setLayout( new BoxLayout(bottomPanel1, BoxLayout.LINE_AXIS));
		bottomPanel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		bottomPanel1.setMinimumSize(new Dimension(560,100));
		bottomPanel1.setOpaque(false);
		
		JButton boutonAvance = new JButton("Réglages Avancés");
		boutonAvance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reglagesAvances.setVisible(true);
			}
		});
		
		JButton boutonInfos = new JButton("Informations Utiles");
		boutonInfos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				informationsUtiles.setVisible(true);
			}
		});
		
		
		
		
		bottomPanel1.add(boutonInfos);
		bottomPanel1.add(Box.createHorizontalGlue());
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
		initInformationsUtiles();
		
		return backPanel;
	}

	/**
	 * Définit la composition de la fenetre des réglages avancés
	 */
	private void initReglagesAvances(){
		reglagesAvances = new JFrame();
		JPanel backPanel2 = new JPanel();
		backPanel2.setBackground(backgroundColor);
		reglagesAvances.setTitle("Réglages Avancés");
		reglagesAvances.setSize(340, 190);
		reglagesAvances.setLocationRelativeTo(null);
		reglagesAvances.setResizable(false);
		backPanel2.setMaximumSize(new Dimension(340,180));
		backPanel2.setLayout(new BoxLayout(backPanel2, BoxLayout.Y_AXIS));
		backPanel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		JCheckBox boxCoupure = new JCheckBox("Autoriser la coupure de mots");
		boxCoupure.setSelected(Main.coupureMots);
		boxCoupure.setOpaque(false);
		
		final JSlider sliderPenalites = new JSlider(JSlider.HORIZONTAL, 0, 10,10-Main.penalite);
		if (!boxCoupure.isSelected()) sliderPenalites.setEnabled(false);
		sliderPenalites.setPreferredSize(new Dimension(280, 60));
		sliderPenalites.setMaximumSize(new Dimension(280,60));
		sliderPenalites.setMinorTickSpacing(1);
		sliderPenalites.setMajorTickSpacing(5);
		sliderPenalites.setPaintLabels(true);
		sliderPenalites.setPaintTicks(true);
		sliderPenalites.setOpaque(false);

		//Create the label table
		Hashtable<Integer, JLabel> sliderLabelTable = new Hashtable<Integer, JLabel>();
		sliderLabelTable.put(10, new JLabel("<html><center>Nombreuses<br>césures</center></html>") );
		sliderLabelTable.put(0, new JLabel("<html><center>Aucune<br>césure</center></html>") );
		sliderLabelTable.put(5, new JLabel("<html><center>Césures<br>modérées</center></html>") );
		sliderPenalites.setLabelTable( sliderLabelTable );

		
		boxCoupure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.coupureMots = !Main.coupureMots;
				sliderPenalites.setEnabled(Main.coupureMots);
			}
		});
		
		sliderPenalites.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Main.penalite=10-sliderPenalites.getValue();
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
		
		JPanel panelSlider = new JPanel();
		panelSlider.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelSlider.add(sliderPenalites);
		panelSlider.setSize(new Dimension(250,50));
		panelSlider.setOpaque(false);

		backPanel2.add(boxCoupure);
		backPanel2.add(panelSlider);
		backPanel2.add(boxJustificationManuelle);
		backPanel2.add(boxJustificationLogicielle);
		
		
		
		reglagesAvances.setContentPane(backPanel2);
		reglagesAvances.setIconImage(new ImageIcon(getClass().getResource("images/RTF.png").toExternalForm()).getImage());
	}

	/**
	 * Définit la composition de la fenetre des informations utiles
	 */
	private void initInformationsUtiles(){
		informationsUtiles = new JFrame();
		informationsUtiles.setTitle("Informations Utiles");
		informationsUtiles.setSize(500,300);
		informationsUtiles.setResizable(false);
		informationsUtiles.setLocationRelativeTo(null);
		informationsUtiles.setIconImage(new ImageIcon(getClass().getResource("images/RTF.png")).getImage());
		JPanel backPanelInfos = new JPanel();
		backPanelInfos.setBackground(backgroundColor);
		backPanelInfos.setLayout(new BoxLayout(backPanelInfos, BoxLayout.Y_AXIS));
		backPanelInfos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JEditorPane display = new JEditorPane ();
		display.setContentType("text/rtf; charset=EUC-JP");

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
	    
		display.setEditable ( false ); // set textArea non-editable
		JScrollPane scroll = new JScrollPane ( display );
	 	scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	 	
	 	backPanelInfos.add(scroll);
	 	informationsUtiles.setContentPane(backPanelInfos);
	}
	
	/**
	 * Ouvre un document avec l'application associée par défaut.
	 * Présent ici surtout pour la clarté du code (pas de try/catch..)
	 * @param document Le document à ouvrir
	 */
	public static void open(File document){
	   try{
		Desktop dt = Desktop.getDesktop();
	    dt.open(document);}
	   catch(IOException e1){}
	}
	
	/**
	 * Modifie le chemin d'accès affiché dans le label pour que la fin du chemin
	 * d'accès ne soit pas coupé.
	 * @param label Le Jlabel sur lequel on souhaite appliquer le chemin
	 * @param path le chemin complet qui sera aménagé
	 */
	private static void setRightPath(JLabel label, String path){

 	   label.setText(path);
 	   int taille = label.getFontMetrics(label.getFont()).stringWidth(path);
 	   boolean alreadyCut = false; //savoir si on est deja rentré dans la boucle pour savoir si on enleve de suite le ...\
 	   String separator  = File.separator; //la variable separator sert à ne pas avoir une boucle propre à un système d'exploitation - Par défaut cas windows.
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
