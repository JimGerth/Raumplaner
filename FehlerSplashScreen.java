import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import JAVASON.*;


class FehlerSplashScreen extends JFrame implements KeyListener {
    
    private FehlerArt fehlerArt;
    
    FehlerSplashScreen() {
        this(FehlerArt.UNBEKANNTER_FEHLER);
    }
    
    FehlerSplashScreen(FehlerArt fehlerArt) {
        super("Fehler");
        this.fehlerArt = fehlerArt;
        
        // Lage auf dem Bildschirm
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 5;
        int y = (d.height - getSize().height) / 5;
        setLocation(x, y);
        
        // Fläche für Bedienungs-Elemente (Buttons usw.):
        Container cp = getContentPane();
        cp.setLayout(null);
        
        // Komponenten einfuegen und sonstiges setup
        JLabel titel = new JLabel("Achtung!"); // immer gleicher titel
        titel.setBounds(25, 15, 200, 40);
        titel.setFont(new Font("Arial", Font.BOLD, 32));
        cp.add(titel);
        switch (fehlerArt) {
            case LADE_FEHLER:
                ladeFehlerKomponentenEinfuegen(cp);
                break;
            case SPEICHER_FEHLER:
                speicherFehlerKomponentenEinfuegen(cp);
                break;
            case ERSTELLEN_FEHLER:
                moebelErstellenFehlerKomponentenEinfuegen(cp);
                break;
            case NICHT_GESPEICHERT_FEHLER:
                nichtGespeichertFehlerKomponentenEinfuegen(cp);
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
        JLabel untertitel = new JLabel("Problem beim Einlesen der Datei.");
        untertitel.setBounds(25, 55, 250, 25);
        untertitel.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel);
        
        JLabel untertitel2 = new JLabel("Stelle sicher, dass du eine .txt datei mit passendem Format auswählst.");
        untertitel2.setBounds(25, 75, 450, 25);
        untertitel2.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel2);
        
        setSize(475, 115);
    }
    
    private void speicherFehlerKomponentenEinfuegen(Container cp) {
        JLabel untertitel = new JLabel("Problem beim Speichern der Datei.");
        untertitel.setBounds(25, 55, 250, 25);
        untertitel.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel);
        
        setSize(475, 135);
    }
    
    private void moebelErstellenFehlerKomponentenEinfuegen(Container cp) {
        JLabel untertitel = new JLabel("Problem beim Erstellen des Möbels.");
        untertitel.setBounds(25, 55, 250, 25);
        untertitel.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel);
        
        JLabel untertitel2 = new JLabel("Stelle sicher, dass du angemessene Werte eingegeben hast.");
        untertitel2.setBounds(25, 75, 450, 25);
        untertitel2.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel2);
        
        setSize(475, 135);
    }
    
    private void nichtGespeichertFehlerKomponentenEinfuegen(Container cp) {
        JLabel untertitel = new JLabel("Der Raum ist noch nicht gespeichert!");
        untertitel.setBounds(25, 55, 250, 25);
        untertitel.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel);
        
        JButton loeschen = new JButton("Löschen"); // schliessen ohne zu speichern
        loeschen.setBounds(260, 85, 100, 25);
        loeschen.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) { loescheUndSchliesse(); }
            }
        );
        cp.add(loeschen);
        
        JButton speichern = new JButton("Speichern"); // erst speichern, dann schliessen
        speichern.setBounds(360, 85, 100, 25);
        speichern.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) { speicherUndSchliesse(); }
            }
        );
        cp.add(speichern);
        
        loeschen.addKeyListener(this);
        speichern.addKeyListener(this);
        setSize(475, 150);
    }
    
    private void defaultKomponentenEinfuegen(Container cp) {
        JLabel untertitel = new JLabel("Ein unbekannter Fehler ist aufgetreten.");
        untertitel.setBounds(25, 55, 250, 25);
        untertitel.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel);
        
        setSize(475, 115);
    }
    
    private void loescheUndSchliesse() { Leinwand.gibLeinwand().schliessen(true); }
    private void speicherUndSchliesse() { Leinwand.gibLeinwand().speicher(); Leinwand.gibLeinwand().schliessen(true); }
    
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) dispose();
        if (ke.getKeyCode() == KeyEvent.VK_ENTER && fehlerArt == FehlerArt.NICHT_GESPEICHERT_FEHLER) speicherUndSchliesse();
        else if (ke.getKeyCode() == KeyEvent.VK_ENTER) dispose();
    }
    public void keyReleased(KeyEvent ke) {} // not needed
    public void keyTyped(KeyEvent ke) {} // not needed
    
    enum FehlerArt { LADE_FEHLER, SPEICHER_FEHLER, ERSTELLEN_FEHLER, NICHT_GESPEICHERT_FEHLER, UNBEKANNTER_FEHLER }
}