import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class SchrankwandGUI extends JFrame {
    
    private JButton jbErstellen = new JButton();
    
    SchrankwandGUI()
    {
        super("Raumplaner");

        // Fenster schließen -> Programmende
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) { System.exit(0); }
            });

        // Fenstergröße
        int frameWidth = 280;
        int frameHeight = (Schrankwand.wichtigeOptionen.length * 35) + 70;
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
        setTitle("Schrankwand erstellen");
    }

    private void komponentenEinfuegen(Container cp) {
                for (int i = 0; i < Schrankwand.wichtigeOptionen.length; i++) {
            Schrankwand.wichtigeOptionen[i].label.setBounds(10, (i * 35) + 10, 150, 25);
            cp.add(Schrankwand.wichtigeOptionen[i].label);
            
            Schrankwand.wichtigeOptionen[i].textField.setBounds(170, (i * 35) + 10, 100, 25);
            cp.add(Schrankwand.wichtigeOptionen[i].textField);
        }
        
        jbErstellen.setBounds(170, (Schrankwand.wichtigeOptionen.length * 35) + 10, 100, 25);
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
    
    private void jbErstellenActionPerformed(ActionEvent evt) {
        // Handle errors if Values entered aren't legal
        Moebel schrankwand = new Schrankwand(Integer.parseInt(Schrankwand.wichtigeOptionen[0].textField.getText()),
                                             Integer.parseInt(Schrankwand.wichtigeOptionen[1].textField.getText()),
                                             Integer.parseInt(Schrankwand.wichtigeOptionen[2].textField.getText()));
        if (Leinwand.alleMoebel.size() > 0) {
            Leinwand.alleMoebel.get(Leinwand.moebelNummer).istAusgewaehlt = false;
            Leinwand.alleMoebel.get(Leinwand.moebelNummer).zeichne();
        }
        Leinwand.alleMoebel.add(schrankwand);
        Leinwand.moebelNummer = Leinwand.alleMoebel.size() - 1;
        Leinwand.alleMoebel.get(Leinwand.moebelNummer).istAusgewaehlt = true;
        schrankwand.zeige();
        this.dispose();
    }
}
