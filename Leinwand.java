import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Leinwand extends MouseInputAdapter implements KeyListener {
    
    
    /*********** SINGLETON SETUP ***********/
    
    private static Leinwand leinwandSingleton;
    
    public static Leinwand gibLeinwand() {
        if (leinwandSingleton == null) {
            leinwandSingleton = new Leinwand("Raumplaner - Jim Gerth", 850, 550, Color.white);
        }
        leinwandSingleton.setzeSichtbarkeit(true);
        return leinwandSingleton;
    }
    //////////// END SINGLETON SETUP ////////////

    
    /*********** VARIABLEN ***********/
    
    private JFrame fenster;
    private Zeichenflaeche zeichenflaeche;
    private Graphics2D graphic;
   
    private Color hintergrundfarbe;
    private Image leinwandImage;
    
    private List figuren;
    private Map figurZuShape; // Abbildung von Figuren zu Shapes
    
    private SpeicherProtokoll speicherDelegate = new JSONSpeicherDelegate();
    private String letzterSpeicherPfad;
    
    static ArrayList<Moebel> alleMoebel = new ArrayList<Moebel>();
    static int moebelNummer = -1;

    private int dragXOffset, dragYOffset = 0;
    
    private int previousMouseX, previousMouseY, deltaMouseX, deltaMouseY = 0;
    private int originaleOrientierung;
    
    private int previousXScale, previousYScale;
    
    private boolean shiftGedrueckt = false;
    private boolean altGedrueckt = false;
    private boolean controlGedrueckt = false;
    
    private Moebel zwischenspeicher;
    //////////// END VARIABLEN ////////////

    
    /*********** KONSTRUKTOR ***********/
    
    private Leinwand(String titel, int breite, int hoehe, Color grundfarbe) {
        fenster = new JFrame();
        
        zeichenflaeche = new Zeichenflaeche();
        zeichenflaeche.setPreferredSize(new Dimension(breite, hoehe));
        
        fenster.setJMenuBar(new MenuBar());
        fenster.setContentPane(zeichenflaeche);
        fenster.setTitle(titel);
        fenster.addKeyListener(this);
        zeichenflaeche.addMouseListener(this);
        zeichenflaeche.addMouseMotionListener(this);
        
        hintergrundfarbe = grundfarbe;
        fenster.pack();
        
        figuren = new ArrayList();
        figurZuShape = new HashMap();
    }
    //////////// END KONSTRUKTOR ////////////
    
    
    private void aendereGroesse(int breite, int hoehe) {
        zeichenflaeche.setSize(breite, hoehe);
        // fenster.pack(); // bitch wtf wenn man das fenster resized wird die groesse wieder zurückgesetzt!?!?!? 
    }
    
    
    /*********** KEYBOARD EVENT HANDLING ***********/
    
    public void keyPressed(KeyEvent ke) { // maybe add a file to save these key bindings to (.config) and add ability to change them...
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_A:
                if (ke.isShiftDown()) moebelErstellen();
                break;
            case KeyEvent.VK_UP:
                if (ke.isShiftDown()) moebelBewegen(Richtung.HOCH, 1);
                else moebelBewegen(Richtung.HOCH, 25);
                break;
            case KeyEvent.VK_DOWN:
                if (ke.isShiftDown()) moebelBewegen(Richtung.RUNTER, 1);
                else moebelBewegen(Richtung.RUNTER, 25);
                break;
            case KeyEvent.VK_RIGHT:
                if (ke.isShiftDown()) moebelBewegen(Richtung.RECHTS, 1);
                else if (ke.isAltDown()) weiter();
                else moebelBewegen(Richtung.RECHTS, 25);
                break;
            case KeyEvent.VK_LEFT:
                if (ke.isShiftDown()) moebelBewegen(Richtung.LINKS, 1);
                else if (ke.isAltDown()) zurueck();
                else moebelBewegen(Richtung.LINKS, 25);
                break;
            case KeyEvent.VK_R:
                if (ke.isShiftDown()) moebelDrehen(Richtung.RECHTS, 1);
                else moebelDrehen(Richtung.RECHTS, 30);
                break;
            case KeyEvent.VK_L:
                if (ke.isShiftDown()) moebelDrehen(Richtung.LINKS, 1);
                else moebelDrehen(Richtung.LINKS, 30);
                break;
            case KeyEvent.VK_S:
                if (ke.isControlDown() && ke.isShiftDown()) speicherUnter();
                else if (ke.isControlDown()) speicher();
                break;
            case KeyEvent.VK_O:
                if (ke.isControlDown()) lade();
                break;
            case KeyEvent.VK_DELETE:
                moebelLoeschen();
                break;
            case KeyEvent.VK_BACK_SPACE:
                moebelLoeschen();
                break;
            case KeyEvent.VK_SHIFT:
                shiftGedrueckt = true;
                break;
            case KeyEvent.VK_ALT:
                altGedrueckt = true;
                break;
            case KeyEvent.VK_CONTROL:
                controlGedrueckt = true;
                break;
            case KeyEvent.VK_X:
                if (ke.isControlDown()) moebelAusschneiden();
                else if (!ke.isControlDown()) moebelLoeschen();
                break;
            case KeyEvent.VK_C:
                if (ke.isControlDown()) moebelKopieren();
                break;
            case KeyEvent.VK_V:
                if (ke.isControlDown()) moebelEinfuegen(true);
                break;
            case KeyEvent.VK_D:
                if (ke.isControlDown()) moebelDuplizieren(true);
                break;
            case KeyEvent.VK_Q:
                if (ke.isControlDown()) Leinwand.gibLeinwand().fenster.dispose();
                break;
            case KeyEvent.VK_W:
                if (ke.isControlDown()) Leinwand.gibLeinwand().fenster.dispose();
                break;
        }
    }
    
    public void keyReleased(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_SHIFT:
                shiftGedrueckt = false;
                break;
            case KeyEvent.VK_ALT:
                altGedrueckt = false;
                break;
            case KeyEvent.VK_CONTROL:
                controlGedrueckt = false;
                break;
        }
    }
    
    public void keyTyped(KeyEvent ke) {} // not needed
    //////////// END KEYBOARD EVENT HANDLING ////////////
    
    
    /*********** MOUSE EVENT HANDLING ***********/
    
    public void mousePressed(MouseEvent me) {
        auswahlAufheben();
        for (int i = 0; i < alleMoebel.size(); i++) {
            Moebel moebel = alleMoebel.get(i);
            if (moebel.getAktuelleFigur().contains(me.getX(), me.getY())) {
                // auswaehlen
                moebelAuswaehlen(i);
                
                // setup for rotateWithMouse
                originaleOrientierung = moebel.orientierung; 
                previousMouseX = me.getX();
                previousMouseY = me.getY();
                
                // setup for drag and drop
                dragXOffset = me.getX() - moebel.xPosition;
                dragYOffset = me.getY() - moebel.yPosition;
                moebel.istSchwebend = true;
                
                // setup for scaling
                previousXScale = moebel.xScale;
                previousYScale = moebel.yScale;
                
                if (altGedrueckt) moebelDuplizieren(false);
                
                // um nur das oberste moebel auszuwaehlen / draggen / rotieren
                return;
            }
        }
        //wenn kein moebel erkannt wurde, das gerade ausgewaehlte deselektieren
        auswahlAufheben();
    }
    
    public void mouseDragged(MouseEvent me) {
        for (Moebel moebel : alleMoebel) {
            if (moebel.istSchwebend && !shiftGedrueckt && !controlGedrueckt) { // drag
                moebel.xPosition = me.getX() - dragXOffset;
                moebel.yPosition = me.getY() - dragYOffset;
                moebel.zeichne();
                
                // um nur das oberste moebel auszuwaehlen / draggen / rotieren
                return;
            } else if (moebel.istSchwebend && shiftGedrueckt && !controlGedrueckt) { // rotate
                deltaMouseX = me.getX() - previousMouseX;
                deltaMouseY = me.getY() - previousMouseY;
                moebel.orientierung = originaleOrientierung + deltaMouseX + deltaMouseY;
                moebel.zeichne();
                
                // um nur das oberste moebel auszuwaehlen / draggen / rotieren
                return;
            } else if (moebel.istSchwebend && !shiftGedrueckt && controlGedrueckt) { // scale
                moebel.xScale = previousXScale + ( me.getX() - previousMouseX ) / 50;
                moebel.yScale = previousYScale + ( me.getY() - previousMouseY ) / 50;
                moebel.zeichne();
                
                // um nur das oberste moebel auszuwaehlen / draggen / rotieren
                return;
            }
        }
    }
    
    public void mouseMoved(MouseEvent me) {
        for (Moebel moebel : alleMoebel) {
            if (moebel.istSchwebend) {
                moebel.xPosition = me.getX() - (int) moebel.getAktuelleFigur().getBounds().getWidth() / 2; // make moebel hover centered under mouse
                moebel.yPosition = me.getY() - (int) moebel.getAktuelleFigur().getBounds().getHeight() / 2;
                moebel.zeichne();
            }
        }
    }
    
    public void mouseReleased(MouseEvent me) {
        for (Moebel moebel : alleMoebel) {
            moebel.istSchwebend = false;
        }
    }
    
    public void mouseExited(MouseEvent me) {
        shiftGedrueckt = false; // fixes bug where shiftGedrueckt isnt updated to false when spawning new moebelGUI (shift+a)
        altGedrueckt = false;
        controlGedrueckt = false;
    }
    
    public void mouseWheelMoved(MouseEvent me) {
        // drehen und wenn shift gedrueckt ist verkleinern / vergroessern...
    }
    
    public void mouseClicked(MouseEvent me) {
        // not needed yet
    }
    
    public void mouseEntered(MouseEvent me) {
        // not needed yet
    }
    //////////// END MOUSE EVENT HANDLING ////////////
    
    
    /*********** LEINWAND ZEICHNEN / VERWALTUNG ***********/
    
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

    public void zeichne(Object figur, String farbe, Shape shape) {
        figuren.remove(figur); // entfernen, falls schon eingetragen
        figuren.add(figur); // am Ende hinzuf�gen
        figurZuShape.put(figur, new ShapeMitFarbe(shape, farbe));
        erneutZeichnen();
    }

    public void entferne(Object figur) {
        figuren.remove(figur); // entfernen, falls schon eingetragen
        figurZuShape.remove(figur);
        erneutZeichnen();
    }

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

    public void warte(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch (Exception e) {
            // Exception ignorieren
        }
    }

    private void erneutZeichnen() {
        loeschen();
        for (Iterator i = figuren.iterator(); i.hasNext();) {
            ((ShapeMitFarbe) figurZuShape.get(i.next())).draw(graphic);
        }
        zeichenflaeche.repaint();
    }

    private void loeschen() {
        Color original = graphic.getColor();
        graphic.setColor(hintergrundfarbe);
        Dimension size = zeichenflaeche.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }
    //////////// END LEINWAND ZEICHNEN / VERWALTUNG ////////////
    
    
    /*********** SPEICHERN UND OEFFNEN ***********/
    
    private void speicherUnter() {
        letzterSpeicherPfad = null;
        speicher();
    }
    
    private void speicher() {
        if (letzterSpeicherPfad != null) {
            speicherDelegate.speicher(alleMoebel, letzterSpeicherPfad);
            return;
        } // else open file chooser:
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("~"));
        fc.setDialogTitle("choose file to save");
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        if (fc.showOpenDialog(fenster) == JFileChooser.APPROVE_OPTION) {
            // idk why but dont touch this
        }
        speicherDelegate.speicher(alleMoebel, fc.getSelectedFile().getAbsolutePath());
    }
    
    private void lade() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("~"));
        fc.setDialogTitle("choose file to open");
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        if (fc.showOpenDialog(fenster) == JFileChooser.APPROVE_OPTION) {
            // idk why but dont touch this
        }
        letzterSpeicherPfad = fc.getSelectedFile().getAbsolutePath();
        speicherDelegate.lade(fc.getSelectedFile().getAbsolutePath());
    }
    
    public void loescheMoebel() { // loescht alle figuren aus dem figuren array und zeichnet das leere array dann erneut -> loescht alle figuren
        figuren = new ArrayList();
        erneutZeichnen();
    }
    
    void ladeMoebel(ArrayList<Moebel> neueMoebel) { // gets called when loading a file, so all moebel have to be set and not floating!
        alleMoebel = new ArrayList();
        for (int i = 0; i < neueMoebel.size(); i++) {
            alleMoebel.add(neueMoebel.get(i));
            alleMoebel.get(i).istSchwebend = false;
            if (i == neueMoebel.size() - 1) {
                neueMoebel.get(i).istAusgewaehlt = true;
            }
            neueMoebel.get(i).zeige();
        }
        moebelNummer = alleMoebel.size() - 1; // selects last Moebel and covers case of nonew Moebel -> moebelNummer = -1 -> add check for moebelNummer >= 0 in every action!
    }
    //////////// END SPEICHERN UND OEFFNEN ////////////
    
    
    
    /*********** MOEBEL AUSWAHL ***********/
    
    private void moebelAuswaehlen(int neueMoebelNummer) {
        if (moebelNummer >= 0) {
            alleMoebel.get(moebelNummer).istAusgewaehlt = false;
            alleMoebel.get(moebelNummer).zeichne();
        }
        moebelNummer = neueMoebelNummer;
        alleMoebel.get(moebelNummer).istAusgewaehlt = true;
        alleMoebel.get(moebelNummer).zeichne();
    }
    
    private void auswahlAufheben() {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        if (moebel == null) return;
        // for (Moebel moebel : alleMoebel) { // TODO: schlechte performance da O(n)! schleife kann entfernt werden, wenn duplizieren mit maus besser geregelt wird...
            moebel.istAusgewaehlt = false;
            moebel.zeichne();
        // }
    }
    
    private void weiter() {
        if (moebelNummer + 1 <= alleMoebel.size() - 1) {
            if (moebelNummer >= 0) {
                alleMoebel.get(moebelNummer).istAusgewaehlt = false;
                alleMoebel.get(moebelNummer).zeichne();
            }
            moebelNummer ++;
            alleMoebel.get(moebelNummer).istAusgewaehlt = true;
            alleMoebel.get(moebelNummer).zeichne();
        } else {
            if (moebelNummer >= 0) {
                alleMoebel.get(moebelNummer).istAusgewaehlt = false;
                alleMoebel.get(moebelNummer).zeichne();
            }
            moebelNummer = 0;
            alleMoebel.get(moebelNummer).istAusgewaehlt = true;
            alleMoebel.get(moebelNummer).zeichne();
        }
    }
    
    private void zurueck() {
        if (moebelNummer - 1 >= 0) {
            if (moebelNummer >= 0) {
                alleMoebel.get(moebelNummer).istAusgewaehlt = false;
                alleMoebel.get(moebelNummer).zeichne();
            }
            moebelNummer --;
            alleMoebel.get(moebelNummer).istAusgewaehlt = true;
            alleMoebel.get(moebelNummer).zeichne();
        } else {
            if (moebelNummer >= 0) {
                alleMoebel.get(moebelNummer).istAusgewaehlt = false;
                alleMoebel.get(moebelNummer).zeichne();
            }
            moebelNummer = alleMoebel.size() - 1;
            alleMoebel.get(moebelNummer).istAusgewaehlt = true;
            alleMoebel.get(moebelNummer).zeichne();
        }
    }
    //////////// END MOEBEL AUSWAHL ////////////
    
    
    
    /*********** MOEBEL MANIPULATIONS-FUNKTIONEN ***********/
    
    private void moebelErstellen() {
        new MoebelGUI();
    }
    
    private void moebelAusschneiden() {
        moebelKopieren();
        moebelLoeschen();
    }
    
    private void moebelKopieren() {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        if (moebel == null) return;
        switch (moebel.art) {
            case "Hocker":
                Hocker alterHocker = (Hocker) moebel;
                Moebel neuerHocker = new Hocker(alterHocker.xPosition, alterHocker.yPosition, alterHocker.xScale, alterHocker.yScale, alterHocker.farbe, alterHocker.orientierung, alterHocker.durchmesser);
                zwischenspeicher = neuerHocker;
                break;
            case "Stuhl":
                Stuhl alterStuhl = (Stuhl) moebel;
                Moebel neuerStuhl = new Stuhl(alterStuhl.xPosition, alterStuhl.yPosition, alterStuhl.xScale, alterStuhl.yScale, alterStuhl.farbe, alterStuhl.orientierung, alterStuhl.breite, alterStuhl.tiefe);
                zwischenspeicher = neuerStuhl;
                break;
            case "Tisch":
                Tisch alterTisch = (Tisch) moebel;
                Moebel neuerTisch = new Tisch(alterTisch.xPosition, alterTisch.yPosition, alterTisch.xScale, alterTisch.yScale, alterTisch.farbe, alterTisch.orientierung, alterTisch.breite, alterTisch.tiefe);
                zwischenspeicher = neuerTisch;
                break;
            case "Schrank":
                Schrank alterSchrank = (Schrank) moebel;
                Moebel neuerSchrank = new Schrank(alterSchrank.xPosition, alterSchrank.yPosition, alterSchrank.xScale, alterSchrank.yScale, alterSchrank.farbe, alterSchrank.orientierung, alterSchrank.breite, alterSchrank.tiefe);
                zwischenspeicher = neuerSchrank;
                break;
            case "Schrankwand":
                Schrankwand alteSchrankwand = (Schrankwand) moebel;
                Moebel neueSchrankwand = new Schrankwand(alteSchrankwand.xPosition, alteSchrankwand.yPosition, alteSchrankwand.xScale, alteSchrankwand.yScale, alteSchrankwand.farbe, alteSchrankwand.orientierung, alteSchrankwand.anzahlDerEinheiten, alteSchrankwand.breite, alteSchrankwand.tiefe);
                zwischenspeicher = neueSchrankwand;
                break;
        }
    }
    
    private void moebelEinfuegen(boolean ausgewaehlt) {
        if (zwischenspeicher == null) return;
        if (ausgewaehlt) auswahlAufheben();
        alleMoebel.add(zwischenspeicher);
        moebelNummer = alleMoebel.size() - 1;
        alleMoebel.get(moebelNummer).istAusgewaehlt = ausgewaehlt;
        alleMoebel.get(moebelNummer).zeige();
    }
    
    private void moebelDuplizieren(boolean ohneMaus) { // geht auch mitMaus (alt gedrueckt und drag)
        moebelKopieren();
        if (ohneMaus) auswahlAufheben();
        moebelEinfuegen(ohneMaus);
    }
    
    private void moebelLoeschen() {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        if (moebel == null) return;
        entferne(moebel);
        alleMoebel.remove(moebel);
        moebelNummer = alleMoebel.size() - 1;
    }
    
    private void moebelBewegen(Richtung richtung, int entfernung) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        if (moebel == null) return;
        switch (richtung) {
            case HOCH:
                moebel.bewegeVertikal(-entfernung);
                break;
            case RUNTER:
                moebel.bewegeVertikal(entfernung);
                break;
            case RECHTS:
                moebel.bewegeHorizontal(entfernung);
                break;
            case LINKS:
                moebel.bewegeHorizontal(-entfernung);
                break;
        }
    }
    
    private void moebelDrehen(Richtung richtung, int grad) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        if (moebel == null) return;
        switch (richtung) {
            case RECHTS:
                moebel.dreheUm(grad);
                break;
            case LINKS:
                moebel.dreheUm(-grad);
        }
    }
    
    private void moebelFarbeAendern(String neueFarbe) {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        if (moebel == null) return;
        moebel.aendereFarbe(neueFarbe);
    }
    //////////// END MOEBEL MANIPULATIONS-FUNKTIONEN ////////////
    
    
    /*********** INTERNE HELFER KLASSEN ***********/
    
    private enum Richtung {
        RECHTS, LINKS, HOCH, RUNTER;
    }
    
    private class Zeichenflaeche extends JPanel {
         public void paint(Graphics g) {
             g.drawImage(leinwandImage, 0, 0, null);
         }
    }
    
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
    
    class MenuBar extends JMenuBar {
        MenuBar() {
            super();

        
            // Menus
        
                // MenuItems / SubMenus
            
                    // MenuItems of potential SubMenus
            
            
            JMenu raumplanerMenu = new JMenu("Raumplaner");
        
                JMenuItem ueberMenuItem = new JMenuItem(new AbstractAction("Über Raumplaner") {
                    public void actionPerformed(ActionEvent ae) {
                        // Ueber splash screen
                    }
                });
                raumplanerMenu.add(ueberMenuItem);
        
                JMenuItem hilfeMenuItem = new JMenuItem(new AbstractAction("Hilfe") {
                    public void actionPerformed(ActionEvent ae) {
                        // hilfe spash scren
                    }
                });
                raumplanerMenu.add(hilfeMenuItem);
                
                raumplanerMenu.addSeparator();
        
                JMenuItem einstellungenMenuItem = new JMenuItem(new AbstractAction("Einstellungen...") {
                    public void actionPerformed(ActionEvent ae) {
                        // einstellungen dialog
                    }
                });
                raumplanerMenu.add(einstellungenMenuItem);
                
                raumplanerMenu.addSeparator();
            
                JMenuItem beendenMenuItem = new JMenuItem(new AbstractAction("Beenden") {
                    public void actionPerformed(ActionEvent ae) {
                        Leinwand.gibLeinwand().fenster.dispose();
                    }
                });
                raumplanerMenu.add(beendenMenuItem);
            
            add(raumplanerMenu);
            
        
            JMenu ablageMenu = new JMenu("Ablage");
        
                JMenuItem speichernMenuItem = new JMenuItem(new AbstractAction("Speichern") {
                    public void actionPerformed(ActionEvent ae) {
                        speicher();
                    }
                });
                ablageMenu.add(speichernMenuItem);
            
                JMenuItem speichernUnterMenuItem = new JMenuItem(new AbstractAction("Speichern unter...") {
                    public void actionPerformed(ActionEvent ae) {
                        speicher();
                    }
                });
                ablageMenu.add(speichernUnterMenuItem);
            
                JMenuItem oeffnenMenuItem = new JMenuItem(new AbstractAction("Öffnen...") {
                    public void actionPerformed(ActionEvent ae) {
                        lade();
                    }
                });
                ablageMenu.add(oeffnenMenuItem);
        
            add(ablageMenu);
              
        
            JMenu bearbeitenMenu = new JMenu("Bearbeiten");
            
                JMenuItem ausschneidenMenuItem = new JMenuItem(new AbstractAction("Ausschneiden") {
                    public void actionPerformed(ActionEvent ae) {
                        moebelAusschneiden();
                    }
                });
                bearbeitenMenu.add(ausschneidenMenuItem);
            
                JMenuItem kopierenMenuItem = new JMenuItem(new AbstractAction("Kopieren") {
                    public void actionPerformed(ActionEvent ae) {
                        moebelKopieren();
                    }
                });
                bearbeitenMenu.add(kopierenMenuItem);
            
                JMenuItem einfuegenMenuItem = new JMenuItem(new AbstractAction("Einfügen") {
                    public void actionPerformed(ActionEvent ae) {
                        moebelEinfuegen(true);
                    }
                });
                bearbeitenMenu.add(einfuegenMenuItem);
            
                JMenuItem duplizierenMenuItem = new JMenuItem(new AbstractAction("Duplizieren") {
                    public void actionPerformed(ActionEvent ae) {
                        moebelDuplizieren(true);
                    }
                });
                bearbeitenMenu.add(duplizierenMenuItem);
                
                JMenuItem loeschenMenuItem = new JMenuItem(new AbstractAction("Löschen") {
                    public void actionPerformed(ActionEvent ae) {
                        moebelLoeschen();
                    }
                });
                bearbeitenMenu.add(loeschenMenuItem);
                
                bearbeitenMenu.addSeparator();
        
                JMenu farbeMenu = new JMenu("Farbe...");
            
                    JMenuItem rotMenuItem = new JMenuItem(new AbstractAction("Rot") {
                        public void actionPerformed(ActionEvent ae) {
                            moebelFarbeAendern("rot");
                        }
                    });
                    farbeMenu.add(rotMenuItem);
            
                    JMenuItem blauMenuItem = new JMenuItem(new AbstractAction("Blau") {
                        public void actionPerformed(ActionEvent ae) {
                            moebelFarbeAendern("blau");
                        }
                    });
                    farbeMenu.add(blauMenuItem);
                
                    JMenuItem gruenMenuItem = new JMenuItem(new AbstractAction("Grün") {
                        public void actionPerformed(ActionEvent ae) {
                            moebelFarbeAendern("gruen");
                        }
                    });
                    farbeMenu.add(gruenMenuItem);
                    
                    JMenuItem gelbMenuItem = new JMenuItem(new AbstractAction("Gelb") {
                        public void actionPerformed(ActionEvent ae) {
                            moebelFarbeAendern("gelb");
                        }
                    });
                    farbeMenu.add(gelbMenuItem);
            
                    JMenuItem schwarzMenuItem = new JMenuItem(new AbstractAction("Schwarz") {
                        public void actionPerformed(ActionEvent ae) {
                            moebelFarbeAendern("schwarz");
                        }
                    });
                    farbeMenu.add(schwarzMenuItem);
            
                bearbeitenMenu.add(farbeMenu);
                
                JMenu groesseMenu = new JMenu("Größe...");
            
                    JMenuItem viertelMenuItem = new JMenuItem(new AbstractAction("x0.25") {
                        public void actionPerformed(ActionEvent ae) {
                            moebelFarbeAendern("rot");
                        }
                    });
                    groesseMenu.add(viertelMenuItem);
            
                    JMenuItem halbMenuItem = new JMenuItem(new AbstractAction("x0.5") {
                        public void actionPerformed(ActionEvent ae) {
                            moebelFarbeAendern("blau");
                        }
                    });
                    groesseMenu.add(halbMenuItem);
                
                    JMenuItem einMenuItem = new JMenuItem(new AbstractAction("x1") {
                        public void actionPerformed(ActionEvent ae) {
                            moebelFarbeAendern("gruen");
                        }
                    });
                    groesseMenu.add(einMenuItem);
                    
                    JMenuItem doppelMenuItem = new JMenuItem(new AbstractAction("x2") {
                        public void actionPerformed(ActionEvent ae) {
                            moebelFarbeAendern("gelb");
                        }
                    });
                    groesseMenu.add(doppelMenuItem);
            
                    JMenuItem viermalMenuItem = new JMenuItem(new AbstractAction("x4") {
                        public void actionPerformed(ActionEvent ae) {
                            moebelFarbeAendern("schwarz");
                        }
                    });
                    groesseMenu.add(viermalMenuItem);
            
                bearbeitenMenu.add(groesseMenu);
        
            add(bearbeitenMenu);
        
        
            JMenu einfuegenMenu = new JMenu("Einfuegen");
        
                JMenuItem neuesMoebelMenuItem = new JMenuItem(new AbstractAction("neues Moebel...") {
                    public void actionPerformed(ActionEvent ae) {
                        moebelErstellen();
                    }
                });
                einfuegenMenu.add(neuesMoebelMenuItem);
        
            add(einfuegenMenu);
        
        
            JMenu ansichtMenu = new JMenu("Ansicht");
        
                JMenuItem GUIOeffnenMenuItem = new JMenuItem(new AbstractAction("GUI öffnen") {
                    public void actionPerformed(ActionEvent ae) {
                        GUI.gibGUI(); // change alle moebel etc. in GUI to acces the ones in Leinwand
                    }
                });
                ansichtMenu.add(GUIOeffnenMenuItem);
        
            add(ansichtMenu);
        }
    }
    //////////// END INTERNE HELFER KLASSEN ////////////
}
