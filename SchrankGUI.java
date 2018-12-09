import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class SchrankGUI extends JFrame implements KeyListener {
    
    private JButton jbErstellen = new JButton();
    
    SchrankGUI()
    {
        super("Raumplaner");

        // Fenstergröße
        int frameWidth = 280;
        int frameHeight = (Schrank.wichtigeOptionen.length * 35) + 70;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2 ;
        setLocation(x, y);

        // Fläche für Bedienungs-Elemente (Buttons usw.):
        Container cp = getContentPane();
        cp.setLayout(null);
        // Anfang Komponenten
        komponentenEinfuegen(cp);
        // Ende Komponenten
        setResizable(false);
        setVisible(true);
        setTitle("Schrank erstellen");
    }

    private void komponentenEinfuegen(Container cp) {
        for (int i = 0; i < Schrank.wichtigeOptionen.length; i++) {
            Schrank.wichtigeOptionen[i].label.setBounds(10, (i * 35) + 10, 150, 25);
            cp.add(Schrank.wichtigeOptionen[i].label);
            
            Schrank.wichtigeOptionen[i].textField.setBounds(170, (i * 35) + 10, 100, 25);
            Schrank.wichtigeOptionen[i].textField.addKeyListener(this);
            cp.add(Schrank.wichtigeOptionen[i].textField);
        }
        
        jbErstellen.setBounds(170, (Schrank.wichtigeOptionen.length * 35) + 10, 100, 25);
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
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            erstellen();
        }
    }
    
    public void keyReleased(KeyEvent e) {
        // not needed (yet maybe?)
    }
    
    public void keyTyped(KeyEvent e) {
        // not needed (yet maybe?)        
    }
    
    private void jbErstellenActionPerformed(ActionEvent evt) {
        erstellen();
    }
    
    private void erstellen() {
        Moebel schrank;
        try {
            schrank = new Schrank(Integer.parseInt(Schrank.wichtigeOptionen[0].textField.getText()),
                                  Integer.parseInt(Schrank.wichtigeOptionen[1].textField.getText()));
        } catch (Exception e) {
            System.out.println("Error: Problem beim Erstellen des Moebels mit den gegebenen Werten.");
            return;
        }
        if (Leinwand.alleMoebel.size() > 0) {
            Leinwand.alleMoebel.get(Leinwand.moebelNummer).istAusgewaehlt = false;
            Leinwand.alleMoebel.get(Leinwand.moebelNummer).zeichne();
        }
        Leinwand.alleMoebel.add(schrank);
        Leinwand.moebelNummer = Leinwand.alleMoebel.size() - 1;
        Leinwand.alleMoebel.get(Leinwand.moebelNummer).istAusgewaehlt = true;
        schrank.zeige();
        this.dispose();
    }
}
