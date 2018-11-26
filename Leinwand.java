import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Leinwand ist eine Klasse, die einfache Zeichenoperationen auf einer
 * leinwandartigen Zeichenfl�che erm�glicht.
 * Sie ist eine vereinfachte Version der Klasse Canvas (englisch f�r 
 * Leinwand) des JDK und wurde speziell f�r das Projekt "Figuren"
 * geschrieben.
 * 
 *
 * @author: Bruce Quig
 * @author: Michael K�lling (mik)
 * @author: Axel Schmolitzky
 * 
 * @author: �nderungen von
 * @Java-MS Groupies
 * @hier: Uwe Debacher
 *
 * @version: 1.7 (5.12.2003)
 */
public class Leinwand {
    // Hinweis: Die Implementierung dieser Klasse (insbesondere die
    // Verwaltung der Farben und Identit�ten der Figuren) ist etwas
    // komplizierter als notwendig. Dies ist absichtlich so, weil damit 
    // die Schnittstellen und Exemplarvariablen der Figuren-Klassen
    // f�r den Lernanspruch dieses Projekts einfacher und klarer
    // sein k�nnen.

    private static Leinwand leinwandSingleton;

    /**
     * Fabrikmethode, die eine Referenz auf das einzige Exemplar
     * dieser Klasse zur�ckliefert. Wenn es von einer Klasse nur
     * genau ein Exemplar gibt, wird dieses als 'Singleton'
     * bezeichnet.
     */
    public static Leinwand gibLeinwand() {
        if (leinwandSingleton == null) {
            leinwandSingleton = new Leinwand("Raumplaner - Jim Gerth", 500, 500, Color.white);
        }
        leinwandSingleton.setzeSichtbarkeit(true);
        return leinwandSingleton;
    }

    //  ----- Exemplarvariablen -----

    private JFrame fenster;
    private Zeichenflaeche zeichenflaeche;
    private Graphics2D graphic;
    private Color hintergrundfarbe;
    private Image leinwandImage;
    private List figuren;
    private Map figurZuShape; // Abbildung von Figuren zu Shapes
    private SpeicherProtokoll speicherDelegate = new JSONSpeicherDelegate();
    private JMenuBar menuBar; 
    private JMenu[] menus = {new JMenu("Raumplaner"), new JMenu("Ablage"), new JMenu("Bearbeiten"), new JMenu("Einfuegen")}; 
    private JMenuItem[][] menuItems = {
        {
            new JMenuItem(new AbstractAction("Einstellungen") {
                public void actionPerformed(ActionEvent ae) {
                    // oeffne einstellungen
                }
            }),
            new JMenuItem(new AbstractAction("Beenden") {
                public void actionPerformed(ActionEvent ae) {
                    Leinwand.gibLeinwand().fenster.dispose();
                }
            })
        },
        {
            new JMenuItem(new AbstractAction("Speichern") {
                public void actionPerformed(ActionEvent ae) {
                    GUI.gibGUI().jbSpeicher();
                }
            }),
            new JMenuItem(new AbstractAction("Speichern unter...") {
                public void actionPerformed(ActionEvent ae) {
                    GUI.gibGUI().jbSpeicher();
                }
            }),
            new JMenuItem(new AbstractAction("Öffnen...") {
                public void actionPerformed(ActionEvent ae) {
                    GUI.gibGUI().jbLade();
                }
            })
        },
        {
            new JMenuItem("Farbe...")
        },
        {
            new JMenuItem(new AbstractAction("neues Moebel...") {
                public void actionPerformed(ActionEvent ae) {
                    new MoebelGUI();
                }
            })
        }
    }; // Abbildung von Figuren zu Shapes // maybe make menu or menu bar class to take load off leinwand (refactor)

    /**
     * Erzeuge eine Leinwand.
     * @param titel  Titel, der im Rahmen der Leinwand angezeigt wird
     * @param breite  die gew�nschte Breite der Leinwand
     * @param hoehe  die gew�nschte H�he der Leinwand
     * @param grundfarbe die Hintergrundfarbe der Leinwand
     */
    private Leinwand(String titel, int breite, int hoehe, Color grundfarbe) {
        fenster = new JFrame();
        zeichenflaeche = new Zeichenflaeche();
        
        fenster = new JFrame();
        zeichenflaeche = new Zeichenflaeche();
    
        menuBar = new JMenuBar();
        for (int i = 0; i < menus.length; i++) { // loop through menu items and add each one to menu bar
            for (int j = 0; j < menuItems[i].length; j++) { // loop through menu items respectively and add each one to correlating menu
                menus[i].add(menuItems[i][j]);
            }
            menuBar.add(menus[i]);
        }
        fenster.setJMenuBar(menuBar);
    
        fenster.setContentPane(zeichenflaeche);
        fenster.setTitle(titel);
        zeichenflaeche.setPreferredSize(new Dimension(breite, hoehe));
        hintergrundfarbe = grundfarbe;
        fenster.pack();
        figuren = new ArrayList();
        figurZuShape = new HashMap();
    }


    /**
     * Setze, ob diese Leinwand sichtbar sein soll oder nicht. Wenn die
     * Leinwand sichtbar gemacht wird, wird ihr Fenster in den
     * Vordergrund geholt. Diese Operation kann auch benutzt werden, um 
     * ein bereits sichtbares Leinwandfenster in den Vordergrund (vor
     * andere Fenster) zu holen.
     * @param sichtbar boolean f�r die gew�nschte Sichtbarkeit: 
     * true f�r sichtbar, false f�r nicht sichtbar.
     */
    public void setzeSichtbarkeit(boolean sichtbar) {
        if (graphic == null) {
            // erstmaliger Aufruf: erzeuge das Bildschirm-Image und f�lle
            // es mit der Hintergrundfarbe
            Dimension size = zeichenflaeche.getSize();
            leinwandImage = zeichenflaeche.createImage(size.width, size.height);
            graphic = (Graphics2D) leinwandImage.getGraphics();
            graphic.setColor(hintergrundfarbe);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        fenster.setVisible(sichtbar);
    }

    /**
     * Zeichne f�r das gegebene Figur-Objekt eine Java-Figur (einen Shape)
     * auf die Leinwand.
     * @param  figur  das Figur-Objekt, f�r das ein Shape gezeichnet
     *                 werden soll
     * @param  farbe  die Farbe der Figur
     * @param  shape  ein Objekt der Klasse Shape, das tats�chlich
     *                 gezeichnet wird
     */
    public void zeichne(Object figur, String farbe, Shape shape) {
        figuren.remove(figur); // entfernen, falls schon eingetragen
        figuren.add(figur); // am Ende hinzuf�gen
        figurZuShape.put(figur, new ShapeMitFarbe(shape, farbe));
        erneutZeichnen();
    }
    
    public void loescheMoebel() { // loescht alle figuren aus dem figuren array und zeichnet das leere array dann erneut -> loescht alle figuren
        figuren = new ArrayList();
        erneutZeichnen();
    }
    
    /**
     * Entferne die gegebene Figur von der Leinwand.
     * @param  figur  die Figur, deren Shape entfernt werden soll
     */
    public void entferne(Object figur) {
        figuren.remove(figur); // entfernen,falls schon eingetragen
        figurZuShape.remove(figur);
        erneutZeichnen();
    }

    /**
     * Setze die Zeichenfarbe der Leinwand.
     * @param  farbname der Name der neuen Zeichenfarbe.
     */
    public void setzeZeichenfarbe(String farbname) {
        if (farbname.equals("rot")) { graphic.setColor(Color.red); }
        else if (farbname.equals("schwarz")) { graphic.setColor(Color.black); }
        else if (farbname.equals("blau")) { graphic.setColor(Color.blue); }
        else if (farbname.equals("gelb")) { graphic.setColor(Color.yellow); }
        else if (farbname.equals("gruen")) { graphic.setColor(Color.green); }
        else if (farbname.equals("lila")) { graphic.setColor(Color.magenta); }
        else if (farbname.equals("weiss")) { graphic.setColor(Color.white); }
        else { graphic.setColor(Color.black); }
    }

    /**
     * Warte f�r die angegebenen Millisekunden.
     * Mit dieser Operation wird eine Verz�gerung definiert, die
     * f�r animierte Zeichnungen benutzt werden kann.
     * @param  millisekunden die zu wartenden Millisekunden
     */
    public void warte(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch (Exception e) {
            // Exception ignorieren
        }
    }

    /**
     * Zeichne erneut alle Figuren auf der Leinwand.
     */
    private void erneutZeichnen() {
        loeschen();
        for (Iterator i = figuren.iterator(); i.hasNext();) {
            ((ShapeMitFarbe) figurZuShape.get(i.next())).draw(graphic);
        }
        zeichenflaeche.repaint();
    }

    /**
     * L�sche die gesamte Leinwand.
     */
    private void loeschen() {
        Color original = graphic.getColor();
        graphic.setColor(hintergrundfarbe);
        Dimension size = zeichenflaeche.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }
    
    void jbSpeicher() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("~"));
        fc.setDialogTitle("choose directory to save file to");
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        if (fc.showOpenDialog(jbLinksRunter) == JFileChooser.APPROVE_OPTION) {
            // idk why but dont touch this
        }
        speicherDelegate.speicher(alleMoebel, fc.getSelectedFile().getAbsolutePath());
    }
    
    void jbLade() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("~"));
        fc.setDialogTitle("choose directory to load file from");
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        if (fc.showOpenDialog(jbLinksRunter) == JFileChooser.APPROVE_OPTION) {
            // idk why but dont touch this
        }
        speicherDelegate.lade(fc.getSelectedFile().getAbsolutePath());
    }

    /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Interne Klasse Zeichenflaeche - die Klasse f�r die GUI-Komponente, *
     * die tats�chlich im Leinwand-Fenster angezeigt wird. Diese Klasse   *
     * definiert ein JPanel mit der zus�tzlichen M�glichkeit, das auf ihm *
     * gezeichnet Image aufzufrischen (erneut zu zeichnen).               *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
     private class Zeichenflaeche extends JPanel {
         public void paint(Graphics g) {
             g.drawImage(leinwandImage, 0, 0, null);
         }
    }

    /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Interne Klasse ShapeMitFarbe - Da die Klasse Shape des JDK nicht auch      *
     * eine Farbe mitverwalten kann, muss mit dieser Klasse die Verkn�pfung       *
     * modelliert werden.                                                         *
     * graphic.fill() durch graphic.draw() ersetzt von Uwe Debacher am 5.12.2003  *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
     private class ShapeMitFarbe {
         private Shape shape;
         private String farbe;

         public ShapeMitFarbe(Shape shape, String farbe) {
             this.shape = shape;
             this.farbe = farbe;
         }

         public void draw(Graphics2D graphic) {
             setzeZeichenfarbe(farbe);
             graphic.draw(shape);
         }
    }
}
