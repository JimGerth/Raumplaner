import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/************************************************************************************\
* Fenster zum kontrollieren des Programms.                                           *
* Der mittlere Knopf rotiert durch die Erstell/Lösch/Auswähl Seite, die              *
* weit-verschieben Seite, die genau-Verschieben Seite, die schnell-Rotieren Seite    *
* und die genau-Rotieren Seite.                                                      *
\************************************************************************************/
public class GUI extends JFrame
{
    
    static ArrayList<Moebel> alleMoebel = new ArrayList<Moebel>();
    static int moebelNummer = -1;
    private int controllerPageNumber = 0;
    private int buttonBreite = 25;
    private SpeicherProtokoll speicherDelegate = new JSONSpeicherDelegate();
    private JButton jbRechts = new JButton();
    private JButton jbLinks = new JButton();
    private JButton jbHoch = new JButton();
    private JButton jbRunter = new JButton();
    private JButton jbRechtsHoch = new JButton();
    private JButton jbLinksHoch = new JButton();
    private JButton jbRechtsRunter = new JButton();
    private JButton jbLinksRunter = new JButton();
    private JButton jbShift = new JButton();
    private JButton jbWeiter = new JButton();
    private JButton jbZuruek = new JButton();
    private JButton[] controllerPageButtons = {jbLinksHoch, jbHoch, jbRechtsHoch, jbLinks, jbShift, jbRechts, jbLinksRunter, jbRunter, jbRechtsRunter};
    private String[][] controllerPageSymbols = {
        {" ", "+", " ", "<", "⋆", ">", "^", "x", "*"},
        {"⇖", "⇑", "⇗", "⇐", "⋆", "⇒", "⇙", "⇓", "⇘"},
        {"↖", "↑", "↗", "←", "⋆", "→", "↙", "↓", "↘"},
        {"\\", "|", "/", "—", "⋆", "—", "/", "|", "\\"},
        {" ", " ", " ", "⟲", "⋆", "⟳", "⁢⁢ ", " ", " "},
        {"r", "b", "g", " ", "⋆", " ", "l", "g", "s"}
    };
    
    public GUI()
    {
        super("Raumplaner");

        // Fenster schließen -> Programmende
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) { System.exit(0); }
        });

        // Fenstergröße
        int frameWidth = 115;
        int frameHeight = 135;
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
        setTitle("");
    }
    
    public void ladeMoebel(ArrayList<Moebel> neueMoebel) {
        alleMoebel = new ArrayList();
        for (int i = 0; i < neueMoebel.size(); i++) {
            alleMoebel.add(neueMoebel.get(i));
        }
        moebelNummer = neueMoebel.size() - 1; // selects last Moebel and covers case of nonew Moebel -> moebelNummer = -1 -> add check for moebelNummer >= 0 in every action!
    }

    private void komponentenEinfuegen(Container cp) {
        jbRechts.setBounds(80, 45, buttonBreite, buttonBreite);
        jbRechts.setText(">");
        cp.add(jbRechts);
        jbRechts.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbRechtsActionPerformed(evt);
                }
            }
        );
        
        jbLinks.setBounds(10, 45, buttonBreite, buttonBreite);
        jbLinks.setText("<");
        cp.add(jbLinks);
        jbLinks.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbLinksActionPerformed(evt);
                }
            }
        );
        
        jbHoch.setBounds(45, 10, buttonBreite, buttonBreite);
        jbHoch.setText("+");
        cp.add(jbHoch);
        jbHoch.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbHochActionPerformed(evt);
                }
            }
        );
        
        jbRunter.setBounds(45, 80, buttonBreite, buttonBreite);
        jbRunter.setText("x");
        cp.add(jbRunter);
        jbRunter.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbRunterActionPerformed(evt);
                }
            }
        );
        
        jbRechtsHoch.setBounds(80, 10, buttonBreite, buttonBreite);
        jbRechtsHoch.setText(" ");
        cp.add(jbRechtsHoch);
        jbRechtsHoch.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbRechtsHochActionPerformed(evt);
                }
            }
        );
        
        jbLinksHoch.setBounds(10, 10, buttonBreite, buttonBreite);
        jbLinksHoch.setText(" ");
        cp.add(jbLinksHoch);
        jbLinksHoch.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbLinksHochActionPerformed(evt);
                }
            }
        );
        
        jbRechtsRunter.setBounds(80, 80, buttonBreite, buttonBreite);
        jbRechtsRunter.setText(" ");
        cp.add(jbRechtsRunter);
        jbRechtsRunter.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbRechtsRunterActionPerformed(evt);
                }
            }
        );
        
        jbLinksRunter.setBounds(10, 80, buttonBreite, buttonBreite);
        jbLinksRunter.setText(" ");
        cp.add(jbLinksRunter);
        jbLinksRunter.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbLinksRunterActionPerformed(evt);
                }
            }
        );
        
        jbShift.setBounds(45, 45, buttonBreite, buttonBreite);
        jbShift.setText("⋆");
        cp.add(jbShift);
        jbShift.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbShiftActionPerformed();
                }
            }
        );
    }
    
    public void jbRechtsActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                if (alleMoebel.isEmpty()) {break;};
                jbWeiter();
                break;
            case 1:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeHorizontal(10);
                break;
            case 2:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeHorizontal(2);
                break;
            case 3:
                if (alleMoebel.isEmpty()) {break;};
                moebel.dreheAuf(90);
                jbShiftActionPerformed();
                break;
            case 4:
                if (alleMoebel.isEmpty()) {break;};
                moebel.dreheUm(5);
                break;
        }
    }
    
    public void jbLinksActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                if (alleMoebel.isEmpty()) {break;};
                jbZurueck();
                break;
            case 1:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeHorizontal(-10);
                break;
            case 2:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeHorizontal(-2);
                break;
            case 3:
                if (alleMoebel.isEmpty()) {break;};
                moebel.dreheAuf(270);
                jbShiftActionPerformed();
                break;
            case 4:
                if (alleMoebel.isEmpty()) {break;};
                moebel.dreheUm(-5);
                break;
        }
    }
    
    public void jbHochActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                new MoebelGUI();
                jbShiftActionPerformed();
                break;
            case 1:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(-10);
                break;
            case 2:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(-2);
                break;
            case 3:
                if (alleMoebel.isEmpty()) {break;};
                moebel.dreheAuf(0);
                jbShiftActionPerformed();
                break;
            case 5:
                moebel.aendereFarbe("blau");
                break;
        } 
    }
    
    public void jbRunterActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                if (alleMoebel.isEmpty()) {break;};
                alleMoebel.get(moebelNummer).verberge();
                if (moebelNummer == 0) {
                    alleMoebel.remove(moebelNummer);
                    moebelNummer = alleMoebel.size() - 1;
                    if (alleMoebel.size() > 0) {
                        alleMoebel.get(moebelNummer).aendereFarbe("schwarz", true);
                    }
                } else {
                    jbZurueck();
                    alleMoebel.remove(moebelNummer + 1);
                }
                break;
            case 1:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(10);
                break;
            case 2:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(2);
                break;
            case 3:
                if (alleMoebel.isEmpty()) {break;};
                moebel.dreheAuf(180);
                jbShiftActionPerformed();
                break;
        }
    }
    
    public void jbRechtsHochActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 1:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(-7);
                moebel.bewegeHorizontal(7);
                break;
            case 2:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(-2);
                moebel.bewegeHorizontal(2);
                break;
            case 3:
                if (alleMoebel.isEmpty()) {break;};
                moebel.dreheAuf(45);
                jbShiftActionPerformed();
                break;
            case 5:
                moebel.aendereFarbe("gruen");
                break;
        }
    }
    
    public void jbLinksHochActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 1:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(-7);
                moebel.bewegeHorizontal(-7);
                break;
            case 2:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(-2);
                moebel.bewegeHorizontal(-2);
                break;
            case 3:
                if (alleMoebel.isEmpty()) {break;};
                moebel.dreheAuf(315);
                jbShiftActionPerformed();
                break;
            case 5:
                moebel.aendereFarbe("rot");
                break;
        }
    }
    
    public void jbRechtsRunterActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                speicherDelegate.speicher(alleMoebel);
                break;
            case 1:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(7);
                moebel.bewegeHorizontal(7);
                break;
            case 2:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(2);
                moebel.bewegeHorizontal(2);
                break;
            case 3:
                if (alleMoebel.isEmpty()) {break;};
                moebel.dreheAuf(135);
                jbShiftActionPerformed();
                break;
        }
    }
    
    public void jbLinksRunterActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                speicherDelegate.lade();
                break;
            case 1:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(7);
                moebel.bewegeHorizontal(-7);
                break;
            case 2:
                if (alleMoebel.isEmpty()) {break;};
                moebel.bewegeVertikal(2);
                moebel.bewegeHorizontal(-2);
                break;
            case 3:
                if (alleMoebel.isEmpty()) {break;};
                moebel.dreheAuf(225);
                jbShiftActionPerformed();
                break;
        }
    }
    
    public void jbShiftActionPerformed() {
        if (controllerPageNumber + 1 <= controllerPageSymbols.length - 1) {
            controllerPageNumber ++;
        } else {
            controllerPageNumber = 0;
        }
        for (int i = 0; i < controllerPageButtons.length; i++) {
                controllerPageButtons[i].setText(controllerPageSymbols[controllerPageNumber][i]);
        }
    }
    
    public void jbWeiter() {
        if (moebelNummer + 1 <= alleMoebel.size() - 1) {
            alleMoebel.get(moebelNummer).aendereFarbe(alleMoebel.get(moebelNummer).letzteFarbe);
            moebelNummer ++;
            alleMoebel.get(moebelNummer).aendereFarbe("schwarz", true);
        } else {
            alleMoebel.get(moebelNummer).aendereFarbe(alleMoebel.get(moebelNummer).letzteFarbe);
            moebelNummer = 0;
            alleMoebel.get(moebelNummer).aendereFarbe("schwarz", true);
        }
    }
    
    public void jbZurueck() {
        if (moebelNummer - 1 >= 0) {
            alleMoebel.get(moebelNummer).aendereFarbe(alleMoebel.get(moebelNummer).letzteFarbe);
            moebelNummer --;
            alleMoebel.get(moebelNummer).aendereFarbe("schwarz", true);
        } else {
            alleMoebel.get(moebelNummer).aendereFarbe(alleMoebel.get(moebelNummer).letzteFarbe);
            moebelNummer = alleMoebel.size() - 1;
            alleMoebel.get(moebelNummer).aendereFarbe("schwarz", true);
        }
    }
}
