import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/************************************************************************************\
* Fenster zum erstellen von spezialisierten Tischen.                                 *
* Um weitere Tisch.optionen einzufügen einfach ein weiteres GUIOption mit Beschreibung     *
* in das Tisch.optionen Array einfügen und in der Methode jbErstellenActionPerformed()     *
* die gewünschte Option im Format Tisch.optionen[0].textField.getText() dem Initializer    *
* übergeben. { Integer.parseInt(Tisch.optionen[0].textField.getText()) für eine Zahl }.    *
\************************************************************************************/
class TischGUI extends JFrame {
    
    private JButton jbErstellen = new JButton();
    
    TischGUI()
    {
        super("Raumplaner");

        // Fenster schließen -> Programmende
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) { System.exit(0); }
            });

        // Fenstergröße
        int frameWidth = 280;
        int frameHeight = (Tisch.optionen.length * 35) + 70;
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
        setTitle("Tisch erstellen");
    }

    private void komponentenEinfuegen(Container cp) {
                for (int i = 0; i < Tisch.optionen.length; i++) {
            Tisch.optionen[i].label.setBounds(10, (i * 35) + 10, 150, 25);
            cp.add(Tisch.optionen[i].label);
            
            Tisch.optionen[i].textField.setBounds(170, (i * 35) + 10, 100, 25);
            cp.add(Tisch.optionen[i].textField);
        }
        
        jbErstellen.setBounds(170, (Tisch.optionen.length * 35) + 10, 100, 25);
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
        Moebel tisch = new Tisch(Integer.parseInt(Tisch.optionen[0].textField.getText()),
                                 Integer.parseInt(Tisch.optionen[1].textField.getText()));
        if (GUI.alleMoebel.size() > 0) {
            GUI.alleMoebel.get(GUI.moebelNummer).aendereFarbe(GUI.alleMoebel.get(GUI.moebelNummer).letzteFarbe);
        }
        GUI.alleMoebel.add(tisch);
        GUI.moebelNummer = GUI.alleMoebel.size() - 1;
        tisch.zeige();
        this.dispose();
    }
}
