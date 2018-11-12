import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/************************************************************************************\
* Fenster zum erstellen von spezialisierten Klavieren.                               *
* Um weitere Klavier.optionen einzufügen einfach ein weiteres GUIOption mit Beschreibung     *
* in das Klavier.optionen Array einfügen und in der Methode jbErstellenActionPerformed()     *
* die gewünschte Option im Format Klavier.optionen[0].textField.getText() dem Initializer    *
* übergeben. { Integer.parseInt(Klavier.optionen[0].textField.getText()) für eine Zahl }.    *
\************************************************************************************/
class KlavierGUI extends JFrame {
    
    private JButton jbErstellen = new JButton();
    
    KlavierGUI()
    {
        super("Raumplaner");

        // Fenster schließen -> Programmende
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) { System.exit(0); }
            });

        // Fenstergröße
        int frameWidth = 280;
        int frameHeight = (Klavier.optionen.length * 35) + 70;
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
        setTitle("Klavier erstellen");
    }

    private void komponentenEinfuegen(Container cp) {
                for (int i = 0; i < Klavier.optionen.length; i++) {
            Klavier.optionen[i].label.setBounds(10, (i * 35) + 10, 150, 25);
            cp.add(Klavier.optionen[i].label);
            
            Klavier.optionen[i].textField.setBounds(170, (i * 35) + 10, 100, 25);
            cp.add(Klavier.optionen[i].textField);
        }
        
        jbErstellen.setBounds(170, (Klavier.optionen.length * 35) + 10, 100, 25);
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
        Moebel klavier = new Klavier(Integer.parseInt(Klavier.optionen[0].textField.getText()),
                                     Integer.parseInt(Klavier.optionen[1].textField.getText()),
                                     Integer.parseInt(Klavier.optionen[2].textField.getText()),
                                     Integer.parseInt(Klavier.optionen[3].textField.getText()));
        if (GUI.alleMoebel.size() > 0) {
            GUI.alleMoebel.get(GUI.moebelNummer).aendereFarbe(GUI.alleMoebel.get(GUI.moebelNummer).letzteFarbe);
        }
        GUI.alleMoebel.add(klavier);
        GUI.moebelNummer = GUI.alleMoebel.size() - 1;
        klavier.zeige();
        this.dispose();
    }
}
