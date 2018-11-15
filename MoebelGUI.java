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
    private JButton[] alleMoebelKnoepfe = {jbHocker, jbStuhl, jbTisch, jbSchrank, jbSchrankwand};
    private String[] alleMoebelNamen = {"Hocker" , "Stuhl", "Tisch", "Schrank", "Schrankwand"};
    
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
        // new AllgemeineGUI<ae.getSource().getClass()>();
        if (ae.getSource() == jbHocker) {
            new HockerGUI();
            this.dispose();
        } else if (ae.getSource() == jbStuhl) {
            new AllgemeineGUI<Stuhl>();
            this.dispose();
        } else if (ae.getSource() == jbTisch) {
            new AllgemeineGUI<Tisch>();
            this.dispose();
        } else if (ae.getSource() == jbSchrank) {
            new AllgemeineGUI<Schrank>();
            this.dispose();
        } else if (ae.getSource() == jbSchrankwand) {
<<<<<<< HEAD
            new SchrankwandGUI();
            setVisible(false);
            dispose();
=======
            new AllgemeineGUI<Schrankwand>();
            this.dispose();
        } else if (ae.getSource() == jbBadewanne) {
            new AllgemeineGUI<Badewanne>();
            this.dispose();
        } else if (ae.getSource() == jbKlavier) {
            new AllgemeineGUI<Klavier>();
            this.dispose();
>>>>>>> genericGUIFeature
        }
    }
}
