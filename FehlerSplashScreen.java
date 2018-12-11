import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import JAVASON.*;


class FehlerSplashScreen extends JFrame implements KeyListener {
    
    FehlerSplashScreen() {
        this(FehlerArt.UNBEKANNTER_FEHLER);
    }
    
    FehlerSplashScreen(FehlerArt fehlerArt) {
        super("Fehler");
        
        // Lage auf dem Bildschirm
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 5;
        int y = (d.height - getSize().height) / 5;
        setLocation(x, y);
        
        // Fläche für Bedienungs-Elemente (Buttons usw.):
        Container cp = getContentPane();
        cp.setLayout(null);
        
        // Komponenten einfuegen und sonstiges setup
        switch (fehlerArt) {
            case LADE_FEHLER:
                ladeFehlerKomponentenEinfuegen(cp);
                break;
            case ERSTELLEN_FEHLER:
                moebelErstellenFehlerKomponentenEinfuegen(cp);
                break;
            case UNBEKANNTER_FEHLER:
            default:
                defaultKomponentenEinfuegen(cp);
                break;
        }
        setResizable(false);
        setVisible(true);
        setTitle("");
        addKeyListener(this);
    }

    private void ladeFehlerKomponentenEinfuegen(Container cp) {
        JLabel titel = new JLabel("Fehler");
        titel.setBounds(25, 15, 200, 40);
        titel.setFont(new Font("Arial", Font.BOLD, 32));
        cp.add(titel);
        
        JLabel untertitel = new JLabel("Problem beim Einlesen der Datei.");
        untertitel.setBounds(25, 55, 250, 25);
        untertitel.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel);
        
        JLabel untertitel2 = new JLabel("Stelle sicher, dass du eine .txt datei mit passendem Format auswählst.");
        untertitel2.setBounds(25, 70, 450, 25);
        untertitel2.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel2);
        
        setSize(475, 135);
    }
    
    private void moebelErstellenFehlerKomponentenEinfuegen(Container cp) {
        JLabel titel = new JLabel("Fehler");
        titel.setBounds(25, 15, 200, 40);
        titel.setFont(new Font("Arial", Font.BOLD, 32));
        cp.add(titel);
        
        JLabel untertitel = new JLabel("Problem beim Erstellen des Möbels.");
        untertitel.setBounds(25, 55, 250, 25);
        untertitel.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel);
        
        JLabel untertitel2 = new JLabel("Stelle sicher, dass du angemessene Werte eingegeben hast.");
        untertitel2.setBounds(25, 70, 450, 25);
        untertitel2.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel2);
        
        setSize(475, 135);
    }
    
    private void defaultKomponentenEinfuegen(Container cp) {
        JLabel titel = new JLabel("Fehler");
        titel.setBounds(25, 15, 200, 40);
        titel.setFont(new Font("Arial", Font.BOLD, 32));
        cp.add(titel);
        
        JLabel untertitel = new JLabel("Ein unbekannter Fehler ist aufgetreten.");
        untertitel.setBounds(25, 55, 250, 25);
        untertitel.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel);
        
        setSize(475, 115);
    }
    
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER || ke.getKeyCode() == KeyEvent.VK_ESCAPE) this.dispose();
    }
    public void keyReleased(KeyEvent ke) {} // not needed
    public void keyTyped(KeyEvent ke) {} // not needed
    
    enum FehlerArt { LADE_FEHLER, SPEICHER_FEHLER, ERSTELLEN_FEHLER, UNBEKANNTER_FEHLER }
}