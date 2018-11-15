import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/************************************************************************************\
* Fenster zum erstellen von spezialisierten Hockern.                                 *
* Um weitere Hocker.wichtigeOptionen einzufügen einfach ein weiteres GUIOption mit Beschreibung     *
* in das Hocker.wichtigeOptionen Array einfügen und in der Methode jbErstellenActionPerformed()     *
* die gewünschte Option im Format Hocker.wichtigeOptionen[0].textField.getText() dem Initializer    *
* übergeben. { Integer.parseInt(Hocker.wichtigeOptionen[0].textField.getText()) für eine Zahl }.    *
\************************************************************************************/
public class HockerGUI extends JFrame {
    
    private JButton jbErstellen = new JButton();
    
    public HockerGUI()
    {
        super("Raumplaner");

        // Fenster schließen -> Programmende
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) { System.exit(0); }
            });

        // Fenstergröße
        int frameWidth = 280;
        int frameHeight = (Hocker.wichtigeOptionen.length * 35) + 70;
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
        setTitle("Hocker erstellen");
    }

    private void komponentenEinfuegen(Container cp) {
                for (int i = 0; i < Hocker.wichtigeOptionen.length; i++) {
            Hocker.wichtigeOptionen[i].label.setBounds(10, (i * 35) + 10, 150, 25);
            cp.add(Hocker.wichtigeOptionen[i].label);
            
            Hocker.wichtigeOptionen[i].textField.setBounds(170, (i * 35) + 10, 100, 25);
            cp.add(Hocker.wichtigeOptionen[i].textField);
        }
        
        jbErstellen.setBounds(170, (Hocker.wichtigeOptionen.length * 35) + 10, 100, 25);
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
        Moebel hocker = new Hocker(0,
                                   0,
                                   "schwarz",
                                   0,
                                   Integer.parseInt(Hocker.wichtigeOptionen[0].textField.getText()));
        if (GUI.alleMoebel.size() > 0) {
            GUI.alleMoebel.get(GUI.moebelNummer).aendereFarbe(GUI.alleMoebel.get(GUI.moebelNummer).letzteFarbe);
        }
        GUI.alleMoebel.add(hocker);
        GUI.moebelNummer = GUI.alleMoebel.size() - 1;
        hocker.zeige();
        this.dispose();
    }
}
