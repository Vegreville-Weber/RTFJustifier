package editeur;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
		setSize(800,400); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
	}
	
	private JPanel buildContentPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		
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
            	}
            	
            }
        });
		panel.add(boutonSource);
 
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
            	}
            	
            }
        });
		panel.add(boutonCible);
		
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
		panel.add(boutonLancement);
 
		return panel;
	}
}