import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import JAVASON.*;


class FehlerSplashScreen extends JFrame implements KeyListener {
    
    FehlerSplashScreen() {
        this("unbekannter Fehler");
    }
    
    FehlerSplashScreen(String fehlerArt) {
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
            case "Lade-Fehler":
                ladeFehlerKomponentenEinfuegen(cp);
                break;
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
    
    private void defaultKomponentenEinfuegen(Container cp) {
        JLabel titel = new JLabel("Fehler");
        titel.setBounds(25, 15, 200, 40);
        titel.setFont(new Font("Arial", Font.BOLD, 32));
        cp.add(titel);
        
        setSize(475, 70);
    }
    
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER || ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }
    public void keyReleased(KeyEvent ke) {} // not needed
    public void keyTyped(KeyEvent ke) {} // not needed
}