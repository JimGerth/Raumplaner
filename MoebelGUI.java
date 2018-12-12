import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;


public class MoebelGUI extends JFrame implements ActionListener, KeyListener {
    
    private JButton jbHocker = new JButton();
    private JButton jbStuhl = new JButton();
    private JButton jbTisch = new JButton();
    private JButton jbSchrank = new JButton();
    private JButton jbSchrankwand = new JButton();
    private JButton[] alleMoebelKnoepfe = {jbHocker, jbStuhl, jbTisch, jbSchrank, jbSchrankwand};
    private String[] alleMoebelNamen = {"Hocker" , "Stuhl", "Tisch", "Schrank", "Schrankwand"};
    
    
    public MoebelGUI() {
        super("Raumplaner");

        // Fenstergröße / position
        int frameWidth = 145;
        int frameHeight = (alleMoebelKnoepfe.length * 35) + 30;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        setLocation(x, y);
        
        // komponenten einfuegen und allgemeines setup
        Container cp = getContentPane();
        cp.setLayout(null);
        komponentenEinfuegen(cp);
        setResizable(false);
        setVisible(true);
        setTitle("Möbel");
    }
    
    private void komponentenEinfuegen(Container cp) {
        for (int i = 0; i < alleMoebelKnoepfe.length; i++) {
            alleMoebelKnoepfe[i].setBounds(10, (i * 35) + 10, 125, 25);
            alleMoebelKnoepfe[i].setText(alleMoebelNamen[i]);
            cp.add(alleMoebelKnoepfe[i]);
            alleMoebelKnoepfe[i].addActionListener(this);
            alleMoebelKnoepfe[i].addKeyListener(this);
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        // new AllgemeineGUI<ae.getSource().getClass()>();
        if (ae.getSource() == jbHocker) {
            new HockerGUI(); // change to AllgemeineGUI<Hocker>() once "null optionen" is fixed
            // new AllgemeineGUI<Hocker>();
            this.dispose();
        } else if (ae.getSource() == jbStuhl) {
            new StuhlGUI();
            this.dispose();
        } else if (ae.getSource() == jbTisch) {
            new TischGUI();
            this.dispose();
        } else if (ae.getSource() == jbSchrank) {
            new SchrankGUI();
            this.dispose();
        } else if (ae.getSource() == jbSchrankwand) {
            new SchrankwandGUI();
            this.dispose();
        }
    }
    
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) dispose();
    }
    public void keyReleased(KeyEvent ke) {} // not needed
    public void keyTyped(KeyEvent ke) {} // not needed
}
