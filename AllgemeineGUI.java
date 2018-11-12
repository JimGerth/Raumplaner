import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/******************\
* WORK IN PROGRESS *
\******************/
class AllgemeineGUI<T extends Moebel> extends JFrame {
    
    private JButton jbErstellen = new JButton();
    
    AllgemeineGUI()
    {
        super("Raumplaner");

        // Fenster schließen -> Programmende
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) { System.exit(0); }
            });

        // Fenstergröße
        int frameWidth = 280;
        int frameHeight = (T.optionen.length * 35) + 70;
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
                for (int i = 0; i < T.optionen.length; i++) {
            T.optionen[i].label.setBounds(10, (i * 35) + 10, 150, 25);
            cp.add(T.optionen[i].label);
            
            T.optionen[i].textField.setBounds(170, (i * 35) + 10, 100, 25);
            cp.add(T.optionen[i].textField);
        }
        
        jbErstellen.setBounds(170, (T.optionen.length * 35) + 10, 100, 25);
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
                Moebel hocker = new Hocker(Integer.parseInt(Hocker.optionen[0].textField.getText()));
                GUI.alleMoebel.add(hocker);
                hocker.zeige();
                break;
            case "Stuhl":
                Moebel stuhl = new Stuhl(Integer.parseInt(Stuhl.optionen[0].textField.getText()),
                                         Integer.parseInt(Stuhl.optionen[1].textField.getText()));
                GUI.alleMoebel.add(stuhl);
                stuhl.zeige();
                break;
            case "Tisch":
                Moebel tisch = new Tisch(Integer.parseInt(Tisch.optionen[0].textField.getText()),
                                         Integer.parseInt(Tisch.optionen[1].textField.getText()));
                GUI.alleMoebel.add(tisch);
                tisch.zeige();
                break;
            case "Schrank":
                Moebel schrank = new Schrank(Integer.parseInt(Schrank.optionen[0].textField.getText()),
                                             Integer.parseInt(Schrank.optionen[1].textField.getText()));
                GUI.alleMoebel.add(schrank);
                schrank.zeige();
                break;
            case "Schrankwand":
                Moebel schrankwand = new Schrankwand(Integer.parseInt(Schrankwand.optionen[0].textField.getText()),
                                                     Integer.parseInt(Schrankwand.optionen[1].textField.getText()),
                                                     Integer.parseInt(Schrankwand.optionen[2].textField.getText()));
                GUI.alleMoebel.add(schrankwand);
                schrankwand.zeige();
                break;
            case "Badewanne":
                Moebel badewanne = new Badewanne(Integer.parseInt(Badewanne.optionen[0].textField.getText()),
                                                 Integer.parseInt(Badewanne.optionen[1].textField.getText()));
                GUI.alleMoebel.add(badewanne);
                badewanne.zeige();
                break;
            case "Klavier":
                Moebel klavier = new Klavier(Integer.parseInt(Klavier.optionen[0].textField.getText()),
                                             Integer.parseInt(Klavier.optionen[1].textField.getText()),
                                             Integer.parseInt(Klavier.optionen[2].textField.getText()),
                                             Integer.parseInt(Klavier.optionen[3].textField.getText()));
                GUI.alleMoebel.add(klavier);
                klavier.zeige();
                break;
        }
        if (GUI.alleMoebel.size() > 0) {
            GUI.alleMoebel.get(GUI.moebelNummer).aendereFarbe(GUI.alleMoebel.get(GUI.moebelNummer).letzteFarbe);
        }
        GUI.moebelNummer = GUI.alleMoebel.size() - 1;
        this.dispose();
    }
}
