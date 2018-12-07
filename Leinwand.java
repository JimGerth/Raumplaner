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
            leinwandSingleton = new Leinwand("Raumplaner - Jim Gerth", 500, 500, Color.white);
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
    
    private boolean shiftGedrueckt = false;
    private int dragXOffset, dragYOffset = 0;
    //////////// END VARIABLEN ////////////

    
    /*********** KONSTRUKTOR ***********/
    
    private Leinwand(String titel, int breite, int hoehe, Color grundfarbe) {
        fenster = new JFrame();
        
        zeichenflaeche = new Zeichenflaeche();
        zeichenflaeche.setPreferredSize(new Dimension(breite, hoehe));
        
        fenster.setJMenuBar(setupMenuBar());
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
            case KeyEvent.VK_X:
                moebelLoeschen();
                break;
            case KeyEvent.VK_DELETE:
                moebelLoeschen();
                break;
            case KeyEvent.VK_BACK_SPACE:
                moebelLoeschen();
                break;
        }
    }
    
    public void keyReleased(KeyEvent ke) {
        // not needed (yet maybe?)
    }
    
    public void keyTyped(KeyEvent ke) {
        // not needed (yet maybe?)
    }
    //////////// END KEYBOARD EVENT HANDLING ////////////
    
    
    /*********** MOUSE EVENT HANDLING ***********/
    
    public void mousePressed(MouseEvent me) {
        for (Moebel moebel : alleMoebel) {
            if (moebel.gibAktuelleFigur().contains(me.getX(), me.getY())) {
                dragXOffset = me.getX() - moebel.xPosition;
                dragYOffset = me.getY() - moebel.yPosition;
                moebel.istSchwebend = true;
                break;
            }
        }
    }
    
    public void mouseDragged(MouseEvent me) {
        for (Moebel moebel : alleMoebel) {
            if (moebel.istSchwebend) {
                moebel.loesche();
                moebel.xPosition = me.getX() - dragXOffset;
                moebel.yPosition = me.getY() - dragYOffset;
                moebel.zeichne();
            }
        }
    }
    
    public void mouseMoved(MouseEvent me) {
        for (Moebel moebel : alleMoebel) {
            if (moebel.istSchwebend) {
                moebel.loesche();
                moebel.xPosition = me.getX();
                moebel.yPosition = me.getY();
                moebel.zeichne();
            }
        }
    }
    
    public void mouseReleased(MouseEvent me) {
        for (Moebel moebel : alleMoebel) {
            moebel.istSchwebend = false;
        }
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
    
    public void mouseExited(MouseEvent me) {
        // not needed yet
    }
    //////////// END MOUSE EVENT HANDLING ////////////
    
    
    /*********** MENU BAR ***********/
    
    private JMenuBar setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        
        // Menus
        
            // MenuItems / SubMenus
            
                // MenuItems of potential SubMenus
            
            
        JMenu raumplanerMenu = new JMenu("Raumplaner");
        
            JMenuItem einstellungenMenuItem = new JMenuItem(new AbstractAction("Einstellungen") {
                public void actionPerformed(ActionEvent ae) {
                    aendereGroesse(250, 250);
                }
            });
            raumplanerMenu.add(einstellungenMenuItem);
            
            JMenuItem beendenMenuItem = new JMenuItem(new AbstractAction("Beenden") {
                public void actionPerformed(ActionEvent ae) {
                    Leinwand.gibLeinwand().fenster.dispose();
                }
            });
            raumplanerMenu.add(beendenMenuItem);
            
        menuBar.add(raumplanerMenu);
        
        
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
        
        menuBar.add(ablageMenu);
              
        
        JMenu bearbeitenMenu = new JMenu("Bearbeiten");
        
            JMenu farbeMenu = new JMenu("Farbe...");
            
                JMenuItem blauMenuItem = new JMenuItem(new AbstractAction("Blau") {
                    public void actionPerformed(ActionEvent ae) {
                        moebelFarbeAendern("blau");
                    }
                });
                farbeMenu.add(blauMenuItem);
                
                JMenuItem rotMenuItem = new JMenuItem(new AbstractAction("Rot") {
                    public void actionPerformed(ActionEvent ae) {
                        moebelFarbeAendern("rot");
                    }
                });
                farbeMenu.add(rotMenuItem);
                
                JMenuItem schwarzMenuItem = new JMenuItem(new AbstractAction("Schwarz") {
                    public void actionPerformed(ActionEvent ae) {
                        moebelFarbeAendern("schwarz");
                    }
                });
                farbeMenu.add(schwarzMenuItem);
            
            bearbeitenMenu.add(farbeMenu);
        
        menuBar.add(bearbeitenMenu);
        
        
        JMenu einfuegenMenu = new JMenu("Einfuegen");
        
            JMenuItem neuesMoebelMenuItem = new JMenuItem(new AbstractAction("neues Moebel...") {
                public void actionPerformed(ActionEvent ae) {
                    moebelErstellen();
                }
            });
            einfuegenMenu.add(neuesMoebelMenuItem);
        
        menuBar.add(einfuegenMenu);
        
        
        JMenu ansichtMenu = new JMenu("Ansicht");
        
            JMenuItem GUIOeffnenMenuItem = new JMenuItem(new AbstractAction("GUI öffnen") {
                public void actionPerformed(ActionEvent ae) {
                    GUI.gibGUI(); // change alle moebel etc. in GUI to acces the ones in Leinwand
                }
            });
            ansichtMenu.add(GUIOeffnenMenuItem);
        
        menuBar.add(ansichtMenu);
        
        
        return menuBar;
    }
    //////////// END MENU BAR ////////////
    
    
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
    
    private void weiter() {
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
    
    private void zurueck() {
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
    //////////// END MOEBEL AUSWAHL ////////////
    
    
    
    /*********** MOEBEL MANIPULATIONS-FUNKTIONEN ***********/
    
    private void moebelErstellen() {
        new MoebelGUI();
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
    
    private void moebelLoeschen() {
        Moebel moebel = (moebelNummer >= 0) ? alleMoebel.get(moebelNummer) : null;
        if (moebel == null) return;
        entferne(moebel);
        // still need to remove moebel from alleMoebel!
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
    //////////// END INTERNE HELFER KLASSEN ////////////
}
