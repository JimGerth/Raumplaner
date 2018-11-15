import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class StuhlGUI extends JFrame {
    
    private JButton jbErstellen = new JButton();
    
    StuhlGUI()
    {
        super("Raumplaner");

        // Fenster schließen -> Programmende
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) { System.exit(0); }
            });

        // Fenstergröße
        int frameWidth = 280;
        int frameHeight = (Stuhl.wichtigeOptionen.length * 35) + 70;
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
        setTitle("Stuhl erstellen");
    }

    private void komponentenEinfuegen(Container cp) {
        for (int i = 0; i < Stuhl.wichtigeOptionen.length; i++) {
            Stuhl.wichtigeOptionen[i].label.setBounds(10, (i * 35) + 10, 150, 25);
            cp.add(Stuhl.wichtigeOptionen[i].label);
            
            Stuhl.wichtigeOptionen[i].textField.setBounds(170, (i * 35) + 10, 100, 25);
            cp.add(Stuhl.wichtigeOptionen[i].textField);
        }
        
        jbErstellen.setBounds(170, (Stuhl.wichtigeOptionen.length * 35) + 10, 100, 25);
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
        Moebel stuhl = new Stuhl(Integer.parseInt(Stuhl.optionen[0].textField.getText()),
                                 Integer.parseInt(Stuhl.optionen[1].textField.getText()));
        if (GUI.alleMoebel.size() > 0) {
            GUI.alleMoebel.get(GUI.moebelNummer).aendereFarbe(GUI.alleMoebel.get(GUI.moebelNummer).letzteFarbe);
        }
        GUI.alleMoebel.add(stuhl);
        GUI.moebelNummer = GUI.alleMoebel.size() - 1;
        stuhl.zeige();
        this.dispose();
    }
}
