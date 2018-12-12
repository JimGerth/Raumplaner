import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class HilfeSplashScreen extends JFrame implements KeyListener {
    
    HilfeSplashScreen() {
        super("Über Raumplaner");
        
        // Fensterposition
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
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
        untertitel1.setBounds(25, 75, 250, 25);
        untertitel1.setFont(new Font("Arial", Font.BOLD, 13));
        cp.add(untertitel1);
        
        JLabel untertitel2 = new JLabel("Speichern (unter): (shift+) ctrl+s");
        untertitel2.setBounds(30, 100, 450, 25);
        untertitel2.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel2);
        
        JLabel untertitel19 = new JLabel("Öffnen: ctrl+o / ctrl+f");
        untertitel19.setBounds(30, 125, 450, 25);
        untertitel19.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel19);
        
        JLabel untertitel3 = new JLabel("Möbel erstellen: shift+a");
        untertitel3.setBounds(30, 150, 450, 25);
        untertitel3.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel3);
        
        JLabel untertitel4 = new JLabel("Möbel löschen: x / d / backspace / delete");
        untertitel4.setBounds(30, 175, 450, 25);
        untertitel4.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel4);
        
        JLabel untertitel5 = new JLabel("Möbel ausschneiden: shift+x");
        untertitel5.setBounds(30, 200, 450, 25);
        untertitel5.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel5);
        
        JLabel untertitel6 = new JLabel("Möbel kopieren: shift+c");
        untertitel6.setBounds(30, 225, 450, 25);
        untertitel6.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel6);
        
        JLabel untertitel7 = new JLabel("Möbel einfuegen: shift+v");
        untertitel7.setBounds(30, 250, 450, 25);
        untertitel7.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel7);
        
        JLabel untertitel8 = new JLabel("Möbel duplizieren: shift+d");
        untertitel8.setBounds(30, 275, 450, 25);
        untertitel8.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel8);
        
        JLabel untertitel9 = new JLabel("Möbel bewegen: Pfeiltasten (durch shift genauere Abstufung)");
        untertitel9.setBounds(30, 300, 450, 25);
        untertitel9.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel9);
        
        JLabel untertitel10 = new JLabel("Möbel drehen: r & l (durch shift genauere Abstufung)");
        untertitel10.setBounds(30, 325, 450, 25);
        untertitel10.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel10);
        
        JLabel untertitel11 = new JLabel("Möbel auswählen: alt+← / alt+→");
        untertitel11.setBounds(30, 350, 450, 25);
        untertitel11.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel11);
        
        JLabel untertitel12 = new JLabel("Maus-Steuerung:");
        untertitel12.setBounds(25, 400, 250, 25);
        untertitel12.setFont(new Font("Arial", Font.BOLD, 13));
        cp.add(untertitel12);
        
        JLabel untertitel13 = new JLabel("auf Möbel klicken, um es auszuwahlen");
        untertitel13.setBounds(30, 425, 450, 25);
        untertitel13.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel13);
        
        JLabel untertitel14 = new JLabel("überall anderes klicken, um die Auswahl aufzuheben");
        untertitel14.setBounds(30, 450, 450, 25);
        untertitel14.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel14);
        
        JLabel untertitel15 = new JLabel("drücken und ziehen, um Möbel zu bewegen");
        untertitel15.setBounds(30, 475, 450, 25);
        untertitel15.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel15);
        
        JLabel untertitel16 = new JLabel("alt + ziehen, um Möbel zu duplizieren");
        untertitel16.setBounds(30, 500, 450, 25);
        untertitel16.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel16);
        
        JLabel untertitel17 = new JLabel("shift + ziehen, um Möbel zu drehen");
        untertitel17.setBounds(30, 525, 450, 25);
        untertitel17.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel17);
        
        JLabel untertitel18 = new JLabel("ctrl + ziehen, um Möbel zu skalieren");
        untertitel18.setBounds(30, 550, 450, 25);
        untertitel18.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel18);
        
        /*
         * short cuts:
         *  ctrl+s speichern
         *  shift+ctrl+s speichern unter
         *  ctrl+f ctrl+o oeffnen
         *  ctrl+q / ctrl+w schliessen
         *  
         *  shift+a moebel erstellen
         *  x d backspace delete moebel loeschen
         *  shift+x ausschneiden
         *  shift+c kopieren
         *  shift+v einfuegen
         *  shift+d duplizieren
         *  pfeiltasten bewegen ( durche shift genauere abstufung )
         *  r und l in entsprechende richtung drehen ( durche shift genauere abstufung )
         *  alt+Pfeiltasten Moebel auswaehlen
         *  
         * maus steuerung:
         *  auf moebel klicken um auszuwahlen
         *  ueberall anderes klicken um auswahl zu entfernen
         *  druecken und ziehen um zu bewegen
         *  alt + ziehen um zu duplizieren
         *  shift + ziehen um zu drehen
         *  ctrl + ziehen um zu skalieren
         */
        
        setSize(475, 615);
    }
    
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER || ke.getKeyCode() == KeyEvent.VK_ESCAPE) dispose();
    }
    public void keyReleased(KeyEvent ke) {} // not needed
    public void keyTyped(KeyEvent ke) {} // not needed
}