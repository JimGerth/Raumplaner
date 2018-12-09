import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class AllgemeineGUI<T extends Moebel> extends JFrame {
    
    private JButton jbErstellen = new JButton();
    
    AllgemeineGUI() {
        super("Raumplaner");
        
        // eventuell zuerst ein moebel jeder art erstellen und dann sind die static variables initialized???

        // Fenstergröße
        int frameWidth = 280;
        int frameHeight = T.wichtigeOptionen.length * 35 + 70;
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
        setTitle(T.art + " erstellen");
    }

    private void komponentenEinfuegen(Container cp) {
        for (int i = 0; i < T.wichtigeOptionen.length; i++) {
            T.wichtigeOptionen[i].label.setBounds(10, (i * 35) + 10, 150, 25);
            cp.add(T.wichtigeOptionen[i].label);
            
            T.wichtigeOptionen[i].textField.setBounds(170, (i * 35) + 10, 100, 25);
            cp.add(T.wichtigeOptionen[i].textField);
        }
        
        jbErstellen.setBounds(170, (T.wichtigeOptionen.length * 35) + 10, 100, 25);
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
        switch (T.art) {
            case "Hocker":
                Moebel hocker = new Hocker(Integer.parseInt(Hocker.wichtigeOptionen[0].textField.getText()));
                GUI.alleMoebel.add(hocker);
                hocker.zeige();
                break;
            case "Stuhl":
                Moebel stuhl = new Stuhl(Integer.parseInt(Stuhl.wichtigeOptionen[0].textField.getText()),
                                         Integer.parseInt(Stuhl.wichtigeOptionen[1].textField.getText()));
                GUI.alleMoebel.add(stuhl);
                stuhl.zeige();
                break;
            case "Tisch":
                Moebel tisch = new Tisch(Integer.parseInt(Tisch.wichtigeOptionen[0].textField.getText()),
                                         Integer.parseInt(Tisch.wichtigeOptionen[0].textField.getText()));
                GUI.alleMoebel.add(tisch);
                tisch.zeige();
                break;
            case "Schrank":
                Moebel schrank = new Schrank(Integer.parseInt(Schrank.wichtigeOptionen[0].textField.getText()),
                                             Integer.parseInt(Schrank.wichtigeOptionen[1].textField.getText()));
                GUI.alleMoebel.add(schrank);
                schrank.zeige();
                break;
            case "Schrankwand":
                Moebel schrankwand = new Schrankwand(Integer.parseInt(Schrankwand.wichtigeOptionen[0].textField.getText()),
                                                     Integer.parseInt(Schrankwand.wichtigeOptionen[1].textField.getText()),
                                                     Integer.parseInt(Schrankwand.wichtigeOptionen[2].textField.getText()));
                GUI.alleMoebel.add(schrankwand);
                schrankwand.zeige();
                break;
        }
        if (GUI.alleMoebel.size() > 0) {
            GUI.alleMoebel.get(GUI.moebelNummer).istAusgewaehlt = false;
            GUI.alleMoebel.get(GUI.moebelNummer).zeichne();
        }
        GUI.moebelNummer = GUI.alleMoebel.size() - 1;
        this.dispose();
    }
}