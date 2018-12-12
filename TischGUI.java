import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class TischGUI extends JFrame implements KeyListener {
    
    private JButton jbErstellen = new JButton();
    
    
    TischGUI() {
        super("Raumplaner");

        // Fenstergröße
        int frameWidth = 280;
        int frameHeight = (Tisch.wichtigeOptionen.length * 35) + 70;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        setLocation(x, y);
        // Fläche für Bedienungs-Elemente (Buttons usw.):
        Container cp = getContentPane();
        cp.setLayout(null);
        // Anfang Komponenten
        komponentenEinfuegen(cp);
        // Ende Komponenten
        setResizable(false);
        setVisible(true);
        setTitle("Tisch erstellen");
    }

    private void komponentenEinfuegen(Container cp) {
        for (int i = 0; i < Tisch.wichtigeOptionen.length; i++) {
            Tisch.wichtigeOptionen[i].label.setBounds(10, (i * 35) + 10, 150, 25);
            cp.add(Tisch.wichtigeOptionen[i].label);
            
            Tisch.wichtigeOptionen[i].textField.setBounds(170, (i * 35) + 10, 100, 25);
            Tisch.wichtigeOptionen[i].textField.addKeyListener(this);
            cp.add(Tisch.wichtigeOptionen[i].textField);
        }
        
        jbErstellen.setBounds(170, (Tisch.wichtigeOptionen.length * 35) + 10, 100, 25);
        jbErstellen.setText("Erstellen");
        cp.add(jbErstellen);
        jbErstellen.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbErstellenActionPerformed(evt);
                }
            }
        );
    }
    
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER) erstellen(); 
        else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) dispose();
    }
    public void keyReleased(KeyEvent ke) {} // not needed
    public void keyTyped(KeyEvent ke) {} // not needed
    
    private void jbErstellenActionPerformed(ActionEvent evt) {
        erstellen();
    }

    private void erstellen() {
        Moebel tisch;
        try {
            tisch = new Tisch(Integer.parseInt(Tisch.wichtigeOptionen[0].textField.getText()),
                              Integer.parseInt(Tisch.wichtigeOptionen[1].textField.getText()));
        } catch (Exception e) {
            new FehlerSplashScreen(FehlerSplashScreen.FehlerArt.ERSTELLEN_FEHLER);
            return;
        }
        if (Leinwand.alleMoebel.size() > 0) {
            Leinwand.alleMoebel.get(Leinwand.moebelNummer).istAusgewaehlt = false;
            Leinwand.alleMoebel.get(Leinwand.moebelNummer).zeichne();
        }
        Leinwand.alleMoebel.add(tisch);
        Leinwand.moebelNummer = Leinwand.alleMoebel.size() - 1;
        Leinwand.alleMoebel.get(Leinwand.moebelNummer).istAusgewaehlt = true;
        tisch.zeige();
        Leinwand.gibLeinwand().istGespeichert = false;
        this.dispose();
    }
}
