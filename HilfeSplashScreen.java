import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class HilfeSplashScreen extends JFrame implements KeyListener {
    
    HilfeSplashScreen() {
        super("Über Raumplaner");
        
        // Fensterposition
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 5;
        int y = (d.height - getSize().height) / 5;
        setLocation(x, y);
        
        // Fläche für Bedienungs-Elemente (Buttons usw.):
        Container cp = getContentPane();
        cp.setLayout(null);
        
        // Komponenten einfuegen und sonstiges setup
        komponentenEinfuegen(cp);
        setResizable(false);
        setVisible(true);
        setTitle("");
        addKeyListener(this);
    }

    private void komponentenEinfuegen(Container cp) {
        JLabel titel = new JLabel("Hilfe");
        titel.setBounds(20, 15, 200, 40);
        titel.setFont(new Font("Arial", Font.BOLD, 32));
        cp.add(titel);
        
        JLabel untertitel1 = new JLabel("Tastatur-Kürzel:");
        untertitel1.setBounds(25, 55, 250, 25);
        untertitel1.setFont(new Font("Arial", Font.BOLD, 13));
        cp.add(untertitel1);
        
        JLabel untertitel2 = new JLabel("Speichern (unter): (shift+) ctrl+s");
        untertitel2.setBounds(30, 75, 450, 25);
        untertitel2.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel2);
        
        JLabel untertitel3 = new JLabel("Möbel erstellen: shift+a");
        untertitel3.setBounds(30, 100, 450, 25);
        untertitel3.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel3);
        
        JLabel untertitel4 = new JLabel("Möbel löschen: x / d / backspace / delete");
        untertitel4.setBounds(30, 125, 450, 25);
        untertitel4.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel4);
        
        JLabel untertitel5 = new JLabel("Möbel ausschneiden: shift+x");
        untertitel5.setBounds(30, 150, 450, 25);
        untertitel5.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel5);
        
        JLabel untertitel6 = new JLabel("Möbel kopieren: shift+c");
        untertitel6.setBounds(30, 175, 450, 25);
        untertitel6.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel6);
        
        JLabel untertitel7 = new JLabel("Möbel einfuegen: shift+v");
        untertitel7.setBounds(30, 200, 450, 25);
        untertitel7.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel7);
        
        JLabel untertitel8 = new JLabel("Möbel duplizieren: shift+d");
        untertitel8.setBounds(30, 225, 450, 25);
        untertitel8.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel8);
        /*
         * short cuts:
         *  ctrl+s speichern
         *  shift+ctrl+s speichern unter
         *  ctrl+f ctrl+o oeffnen
         *  
         *  shift+a moebel erstellen
         *  x d backspace delete moebel loeschen
         *  shift+x ausschneiden
         *  shift+c kopieren
         *  shift+v einfuegen
         *  shift+d duplizieren
         *  pfeiltasten bewegen ( durche shift genauere abstufung )
         *  r und l in entsprechende richtung drehen ( durche shift genauere abstufung )
         *  
         * maus steuerung:
         *  auf moebel klicken um auszuwahlen
         *  ueberall anderes klicken um auswahl zu entfernen
         *  druecken und ziehen um zu bewegen
         *  alt + ziehen um zu duplizieren
         *  shift + ziehen um zu drehen
         *  ctrl + ziehen um zu skalieren
         */
        
        setSize(500, 500);
    }
    
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER || ke.getKeyCode() == KeyEvent.VK_ESCAPE) dispose();
    }
    public void keyReleased(KeyEvent ke) {} // not needed
    public void keyTyped(KeyEvent ke) {} // not needed
}