package editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class GUI extends JFrame{
	static File source = null;
	static File cible = null;
	
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
		setTitle("RTF Justifier"); //On donne un titre à l'application
		setSize(600,400); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
	}
	
	private JPanel buildContentPane(){
		JPanel backPanel = new JPanel();
		backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));
		backPanel.setSize(600,400);
		backPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel topPanel = new JPanel();
		topPanel.setMinimumSize(new Dimension(560,60));
		//topPanel.setBackground(Color.cyan);
		
		JLabel titre = new JLabel("RTF Justifier Pro");
		Font policeTitre =  new Font("helvetica", Font.ITALIC, 20);
		titre.setFont(policeTitre);
		topPanel.add(titre);
		
		backPanel.add(topPanel);
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridBagLayout());
		//panel.setSize(600, 400);
		
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel panelLabelSource = new JPanel();
		panelLabelSource.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelLabelSource.setBorder(BorderFactory.createTitledBorder("Fichier Source"));
		JLabel labelSource  = new JLabel("Pas de fichier source");
		//labelSource.setBackground(Color.pink);
		//labelSource.setOpaque(true);
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		c.weightx=1;
		c.insets= new Insets(0, 0, 30, 20);
		c.gridwidth=2;		
		panelLabelSource.add(labelSource);
		panel.add(panelLabelSource,c);
		
		JButton boutonSource = new JButton("Choisir le fichier source");
		boutonSource.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	JFileChooser chooser = new JFileChooser();
            	chooser.setFileFilter(new FileNameExtensionFilter(".rtf", "rtf")); 
            	chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            	int option = chooser.showOpenDialog(new JFrame());
            	if (option == JFileChooser.APPROVE_OPTION) {
            	   source = chooser.getSelectedFile();
            	   String path = source.getAbsolutePath();
            	   System.out.println(path);
            	   labelSource.setText(path);
            
            	}
            	
            }
        });
		c.weightx=0;
		c.insets= new Insets(0, 0, 30, 0);
		c.gridwidth=1;
		c.gridx=2;
		panel.add(boutonSource,c);
		
		
		JPanel panelLabelCible = new JPanel();
		panelLabelCible.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelLabelCible.setBorder(BorderFactory.createTitledBorder("Fichier Cible"));
		
		JLabel labelCible  = new JLabel("Pas de fichier cible");
		panelLabelCible.add(labelCible);
		c.gridx=0;
		c.gridy=1;
		c.insets= new Insets(0, 0, 0, 20);
		c.gridwidth=2;		
		panel.add(panelLabelCible, c);
		
		JButton boutonCible = new JButton("Choisir le fichier cible");
		boutonCible.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	JFileChooser chooser = new JFileChooser();
            	chooser.setFileFilter(new FileNameExtensionFilter(".rtf", "rtf")); 
            	chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            	int option = chooser.showOpenDialog(new JFrame());
            	if (option == JFileChooser.APPROVE_OPTION) {
            	   cible = chooser.getSelectedFile();
            	   String path = cible.getAbsolutePath();
            	   System.out.println(path);
            	   labelCible.setText(path);
            	
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
            	else if(cible==null){
            		JOptionPane.showMessageDialog(null, "Vous devez choisir un fichier cible!");
            		return;
            	}
            	
            	
            }
        });
		c.insets = new Insets(40, 0, 0, 0);
		c.gridx=1;
		c.gridy=2;
		c.gridwidth=3;
		c.fill=0;
		panel.add(boutonLancement,c);
		 
		backPanel.add(panel);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout( new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.setMinimumSize(new Dimension(560,100));
		//bottomPanel.setBackground(Color.cyan);
		
		JButton boutonAvance = new JButton("Réglages avancés");
		boutonAvance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Fenêtre en construction");
				
			}
		});
		
		bottomPanel.add(boutonAvance);
		backPanel.add(bottomPanel);
		
		return backPanel;
	}
}