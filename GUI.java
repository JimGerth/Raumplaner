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
class GUI extends JFrame { // maybe spawn a new GUI when right click on leinwand (at that position)
    
    private static GUI GUISingleton;
    
    static GUI gibGUI() {
        if (GUISingleton == null) {
            GUISingleton = new GUI();
        }
        return GUISingleton;
    }
    
    static ArrayList<Moebel> alleMoebel = new ArrayList<Moebel>();
    static int moebelNummer = -1;
    private int controllerPageNumber = 0;
    private int buttonBreite = 40;
    private int buttonAbstand = 5;
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
    
    private GUI()
    {
        super("Raumplaner");

        // Fenster schließen -> Programmende
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) { System.exit(0); }
        });

        // Fenstergröße
        int frameWidth = buttonAbstand + 3 * (buttonBreite + buttonAbstand);
        int frameHeight = buttonAbstand + 3 * (buttonBreite + buttonAbstand) + 20;
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
    
    void ladeMoebel(ArrayList<Moebel> neueMoebel) {
        alleMoebel = new ArrayList();
        for (int i = 0; i < neueMoebel.size(); i++) {
            alleMoebel.add(neueMoebel.get(i));
            if (i == neueMoebel.size() - 1) {
                neueMoebel.get(i).istAusgewaehlt = true;
            }
            neueMoebel.get(i).zeige();
        }
        moebelNummer = alleMoebel.size() - 1; // selects last Moebel and covers case of nonew Moebel -> moebelNummer = -1 -> add check for moebelNummer >= 0 in every action!
    }

    private void komponentenEinfuegen(Container cp) {
        int buttonNummer = 0;
        for (int y = 0; y < 3; y++) { // TODO: komponenten semi automatisch einfuegen
            for (int x = 0; x < 3; x++) {
                 controllerPageButtons[buttonNummer].setBounds(buttonAbstand + x * (buttonBreite + buttonAbstand),
                                                               buttonAbstand + y * (buttonBreite + buttonAbstand),
                                                               buttonBreite,
                                                               buttonBreite);
                 cp.add(controllerPageButtons[buttonNummer]);
                 buttonNummer++;
             }
        }
        jbRechts.setText(">");
        jbRechts.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbRechtsActionPerformed(evt);
                }
            }
        );
        
        jbLinks.setText("<");
        jbLinks.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbLinksActionPerformed(evt);
                }
            }
        );
        
        jbHoch.setText("+");
        jbHoch.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbHochActionPerformed(evt);
                }
            }
        );
        
        jbRunter.setText("x");
        jbRunter.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbRunterActionPerformed(evt);
                }
            }
        );
        
        jbRechtsHoch.setText(" ");
        jbRechtsHoch.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbRechtsHochActionPerformed(evt);
                }
            }
        );
        
        jbLinksHoch.setText(" ");
        jbLinksHoch.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbLinksHochActionPerformed(evt);
                }
            }
        );
        
        jbRechtsRunter.setText("*");
        jbRechtsRunter.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbRechtsRunterActionPerformed(evt);
                }
            }
        );
        
        jbLinksRunter.setText("^");
        jbLinksRunter.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbLinksRunterActionPerformed(evt);
                }
            }
        );
        
        jbShift.setText("⋆");
        jbShift.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jbShiftActionPerformed();
                }
            }
        );
    }
    
    private void jbRechtsActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                if (moebel == null) {break;};
                jbWeiter();
                break;
            case 1:
                if (moebel == null) {break;};
                moebel.bewegeHorizontal(10);
                break;
            case 2:
                if (moebel == null) {break;};
                moebel.bewegeHorizontal(2);
                break;
            case 3:
                if (moebel == null) {break;};
                moebel.dreheAuf(90);
                jbShiftActionPerformed();
                break;
            case 4:
                if (moebel == null) {break;};
                moebel.dreheUm(5);
                break;
        }
    }
    
    private void jbLinksActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                if (moebel == null) {break;};
                jbZurueck();
                break;
            case 1:
                if (moebel == null) {break;};
                moebel.bewegeHorizontal(-10);
                break;
            case 2:
                if (moebel == null) {break;};
                moebel.bewegeHorizontal(-2);
                break;
            case 3:
                if (moebel == null) {break;};
                moebel.dreheAuf(270);
                jbShiftActionPerformed();
                break;
            case 4:
                if (moebel == null) {break;};
                moebel.dreheUm(-5);
                break;
        }
    }
    
    private void jbHochActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                new MoebelGUI();
                jbShiftActionPerformed();
                break;
            case 1:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(-10);
                break;
            case 2:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(-2);
                break;
            case 3:
                if (moebel == null) {break;};
                moebel.dreheAuf(0);
                jbShiftActionPerformed();
                break;
            case 5:
                if (moebel == null) {break;};
                moebel.aendereFarbe("blau");
                break;
        } 
    }
    
    private void jbRunterActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                // delete moebel -> REFACTOR!
                if (moebel == null) {break;};
                moebel.verberge();
                if (moebelNummer == 0) {
                    alleMoebel.remove(moebelNummer);
                    moebelNummer = alleMoebel.size() - 1;
                    if (alleMoebel.size() > 0) {
                        alleMoebel.get(moebelNummer).istAusgewaehlt = true;
                        alleMoebel.get(moebelNummer).zeichne();
                    }
                } else {
                    jbZurueck();
                    alleMoebel.remove(moebelNummer + 1);
                }
                break;
            case 1:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(10);
                break;
            case 2:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(2);
                break;
            case 3:
                if (moebel == null) {break;};
                moebel.dreheAuf(180);
                jbShiftActionPerformed();
                break;
        }
    }
    
    private void jbRechtsHochActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 1:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(-7);
                moebel.bewegeHorizontal(7);
                break;
            case 2:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(-2);
                moebel.bewegeHorizontal(2);
                break;
            case 3:
                if (moebel == null) {break;};
                moebel.dreheAuf(45);
                jbShiftActionPerformed();
                break;
            case 5:
                if (moebel == null) {break;};
                moebel.aendereFarbe("gruen");
                break;
        }
    }
    
    private void jbLinksHochActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 1:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(-7);
                moebel.bewegeHorizontal(-7);
                break;
            case 2:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(-2);
                moebel.bewegeHorizontal(-2);
                break;
            case 3:
                if (moebel == null) {break;};
                moebel.dreheAuf(315);
                jbShiftActionPerformed();
                break;
            case 5:
                if (moebel == null) {break;};
                moebel.aendereFarbe("rot");
                break;
        }
    }
    
    private void jbRechtsRunterActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                speicherDelegate.speicher(alleMoebel);
                break;
            case 1:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(7);
                moebel.bewegeHorizontal(7);
                break;
            case 2:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(2);
                moebel.bewegeHorizontal(2);
                break;
            case 3:
                if (moebel == null) {break;};
                moebel.dreheAuf(135);
                jbShiftActionPerformed();
                break;
        }
    }
    
    private void jbLinksRunterActionPerformed(ActionEvent evt) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        switch (controllerPageNumber) {
            case 0:
                speicherDelegate.lade();
                break;
            case 1:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(7);
                moebel.bewegeHorizontal(-7);
                break;
            case 2:
                if (moebel == null) {break;};
                moebel.bewegeVertikal(2);
                moebel.bewegeHorizontal(-2);
                break;
            case 3:
                if (moebel == null) {break;};
                moebel.dreheAuf(225);
                jbShiftActionPerformed();
                break;
        }
    }
    
    private void jbShiftActionPerformed() {
        if (controllerPageNumber + 1 <= controllerPageSymbols.length - 1) {
            controllerPageNumber ++;
        } else {
            controllerPageNumber = 0;
        }
        for (int i = 0; i < controllerPageButtons.length; i++) {
                controllerPageButtons[i].setText(controllerPageSymbols[controllerPageNumber][i]);
        }
    }
    
    private void jbWeiter() {
        if (moebelNummer + 1 <= alleMoebel.size() - 1) {
            alleMoebel.get(moebelNummer).istAusgewaehlt = false;
            alleMoebel.get(moebelNummer).zeichne();
            moebelNummer ++;
            alleMoebel.get(moebelNummer).istAusgewaehlt = true;
            alleMoebel.get(moebelNummer).zeichne();
        } else {
            alleMoebel.get(moebelNummer).istAusgewaehlt = false;
            alleMoebel.get(moebelNummer).zeichne();
            moebelNummer = 0;
            alleMoebel.get(moebelNummer).istAusgewaehlt = true;
            alleMoebel.get(moebelNummer).zeichne();
        }
    }
    
    private void jbZurueck() {
        if (moebelNummer - 1 >= 0) {
            alleMoebel.get(moebelNummer).istAusgewaehlt = false;
            alleMoebel.get(moebelNummer).zeichne();
            moebelNummer --;
            alleMoebel.get(moebelNummer).istAusgewaehlt = true;
            alleMoebel.get(moebelNummer).zeichne();
        } else {
            alleMoebel.get(moebelNummer).istAusgewaehlt = false;
            alleMoebel.get(moebelNummer).zeichne();
            moebelNummer = alleMoebel.size() - 1;
            alleMoebel.get(moebelNummer).istAusgewaehlt = true;
            alleMoebel.get(moebelNummer).zeichne();
        }
    }
}
