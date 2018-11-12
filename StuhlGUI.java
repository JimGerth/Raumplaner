import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/************************************************************************************\
* Fenster zum erstellen von spezialisierten Stühlen.                                 *
* Um weitere Stuhl.optionen einzufügen einfach ein weiteres GUIOption mit Beschreibung     *
* in das Stuhl.optionen Array einfügen und in der Methode jbErstellenActionPerformed()     *
* die gewünschte Option im Format Stuhl.optionen[0].textField.getText() dem Initializer    *
* übergeben. { Integer.parseInt(Stuhl.optionen[0].textField.getText()) für eine Zahl }.    *
\************************************************************************************/
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
        int frameHeight = (Stuhl.optionen.length * 35) + 70;
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
                for (int i = 0; i < Stuhl.optionen.length; i++) {
            Stuhl.optionen[i].label.setBounds(10, (i * 35) + 10, 150, 25);
            cp.add(Stuhl.optionen[i].label);
            
            Stuhl.optionen[i].textField.setBounds(170, (i * 35) + 10, 100, 25);
            cp.add(Stuhl.optionen[i].textField);
        }
        
        jbErstellen.setBounds(170, (Stuhl.optionen.length * 35) + 10, 100, 25);
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
