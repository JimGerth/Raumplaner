import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javafx.scene.input.KeyCode;


class HockerGUI extends JFrame implements KeyListener {
    
    private JButton jbErstellen = new JButton();
    
    HockerGUI()
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
            Hocker.wichtigeOptionen[i].textField.addKeyListener(this);
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
    
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed");
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("bitch wtf");
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
        Moebel hocker = new Hocker(Integer.parseInt(Hocker.wichtigeOptionen[0].textField.getText())); // erzeugt Hocker mit eingegebenen werten
        if (Leinwand.alleMoebel.size() > 0) {
            Leinwand.alleMoebel.get(Leinwand.moebelNummer).istAusgewaehlt = false;
            Leinwand.alleMoebel.get(Leinwand.moebelNummer).zeichne(); // wenn es bereits ein anderes Moebel gibt wird dies hiermit nicht mehr ausgewaehlt
        }
        Leinwand.alleMoebel.add(hocker);
        Leinwand.moebelNummer = Leinwand.alleMoebel.size() - 1;
        Leinwand.alleMoebel.get(Leinwand.moebelNummer).istAusgewaehlt = true; // waehlt das neue Moebel aus
        hocker.zeige();
        this.dispose();
    }
}
