import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class MoebelGUI extends JFrame implements ActionListener
{
    
    private JButton jbHocker = new JButton();
    private JButton jbStuhl = new JButton();
    private JButton jbTisch = new JButton();
    private JButton jbSchrank = new JButton();
    private JButton jbSchrankwand = new JButton();
    private JButton jbBadewanne = new JButton();
    private JButton jbKlavier = new JButton();
    private JButton[] alleMoebelKnoepfe = {jbHocker, jbStuhl, jbTisch, jbSchrank, jbSchrankwand, jbBadewanne, jbKlavier};
    private String[] alleMoebelNamen = {"Hocker" , "Stuhl", "Tisch", "Schrank", "Schrankwand", "Badewanne", "Klavier"};
    
    public MoebelGUI()
    {
        super("Raumplaner");

        // Fenster schließen -> Programmende
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) { System.exit(0); }
            });

        // Fenstergröße
        int frameWidth = 145;
        int frameHeight = (alleMoebelKnoepfe.length * 35) + 30;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2 ;
        setLocation(x, y);
        setTitle("Möbel");

        // Fläche für Bedienungs-Elemente (Buttons usw.):
        Container cp = getContentPane();
        cp.setLayout(null);
        // Anfang Komponenten
        komponentenEinfuegen(cp);
        // Ende Komponenten
        setResizable(false);
        setVisible(true);
    }
    
    private void komponentenEinfuegen(Container cp) {
        for (int i = 0; i < alleMoebelKnoepfe.length; i++) {
            alleMoebelKnoepfe[i].setBounds(10, (i * 35) + 10, 125, 25);
            alleMoebelKnoepfe[i].setText(alleMoebelNamen[i]);
            cp.add(alleMoebelKnoepfe[i]);
            alleMoebelKnoepfe[i].addActionListener(this);
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == jbHocker) {
            new AllgemeineGUI<Hocker>();
            setVisible(false);
            dispose();
        } else if (ae.getSource() == jbStuhl) {
            new StuhlGUI();
            setVisible(false);
            dispose();
        } else if (ae.getSource() == jbTisch) {
            new TischGUI();
            setVisible(false);
            dispose();
        } else if (ae.getSource() == jbSchrank) {
            new SchrankGUI();
            setVisible(false);
            dispose();
        } else if (ae.getSource() == jbSchrankwand) {
            new SchrankwandGUI();
            setVisible(false);
            dispose();
        } else if (ae.getSource() == jbBadewanne) {
            new BadewanneGUI();
            setVisible(false);
            dispose();
        } else if (ae.getSource() == jbKlavier) {
            new KlavierGUI();
            setVisible(false);
            dispose();
        }
    }
}
