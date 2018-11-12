import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/************************************************************************************\
* Fenster zum erstellen von spezialisierten Schrankwänden.                           *
* Um weitere Schrankwand.optionen einzufügen einfach ein weiteres GUIOption mit Beschreibung     *
* in das Schrankwand.optionen Array einfügen und in der Methode jbErstellenActionPerformed()     *
* die gewünschte Option im Format Schrankwand.optionen[0].textField.getText() dem Initializer    *
* übergeben. { Integer.parseInt(Schrankwand.optionen[0].textField.getText()) für eine Zahl }.    *
\************************************************************************************/
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
        int frameHeight = (Schrankwand.optionen.length * 35) + 70;
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
                for (int i = 0; i < Schrankwand.optionen.length; i++) {
            Schrankwand.optionen[i].label.setBounds(10, (i * 35) + 10, 150, 25);
            cp.add(Schrankwand.optionen[i].label);
            
            Schrankwand.optionen[i].textField.setBounds(170, (i * 35) + 10, 100, 25);
            cp.add(Schrankwand.optionen[i].textField);
        }
        
        jbErstellen.setBounds(170, (Schrankwand.optionen.length * 35) + 10, 100, 25);
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
        Moebel schrankwand = new Schrankwand(Integer.parseInt(Schrankwand.optionen[0].textField.getText()),
                                             Integer.parseInt(Schrankwand.optionen[1].textField.getText()),
                                             Integer.parseInt(Schrankwand.optionen[2].textField.getText()));
        if (GUI.alleMoebel.size() > 0) {
            GUI.alleMoebel.get(GUI.moebelNummer).aendereFarbe(GUI.alleMoebel.get(GUI.moebelNummer).letzteFarbe);
        }
        GUI.alleMoebel.add(schrankwand);
        GUI.moebelNummer = GUI.alleMoebel.size() - 1;
        schrankwand.zeige();
        this.dispose();
    }
}
