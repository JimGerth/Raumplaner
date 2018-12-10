import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class HilfeSplashScreen extends JFrame implements KeyListener {
    
    HilfeSplashScreen() {
        super("Über Raumplaner");
        
        // Fenstergröße
        int frameWidth = 235;
        int frameHeight = 110;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 5;
        int y = (d.height - getSize().height) / 5;
        setLocation(x, y);
        
        // Fläche für Bedienungs-Elemente (Buttons usw.):
        Container cp = getContentPane();
        cp.setLayout(null);
        
        // Komponenten einfuegen und sonstiges setup
        komponentenEinfuegen(cp);
        setResizable(false);
        setVisible(true);
        setTitle("");
        addKeyListener(this);
    }

    private void komponentenEinfuegen(Container cp) {
        JLabel titel = new JLabel("Hilfe");
        titel.setBounds(20, 15, 200, 40);
        titel.setFont(new Font("Arial", Font.BOLD, 32));
        cp.add(titel);
        
        JLabel untertitel = new JLabel("von Jim Gerth");
        untertitel.setBounds(25, 45, 150, 25);
        untertitel.setFont(new Font("Arial", Font.PLAIN, 13));
        cp.add(untertitel);
    }
    
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER || ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }
    public void keyReleased(KeyEvent ke) {} // not needed
    public void keyTyped(KeyEvent ke) {} // not needed
}