import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

abstract public class Moebel {
    
    int xPosition;
    int yPosition;
    String farbe;
    int orientierung;
    boolean istAusgewaehlt;

    static String art;
    static GUIOption[] optionen = {};
    static GUIOption[] wichtigeOptionen = {};

    protected boolean istSichtbar = false;


    Moebel(int xPosition, int yPosition, String farbe, int orientierung) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.farbe = farbe;
        this.orientierung = orientierung;
        // updateWichtigeOptionen();
    }
    
    abstract protected Shape gibAktuelleFigur();
    abstract String toJSON();
    abstract int gibInt(String attributName);
    
    static GUIOption[] gibOptionen() {
        return optionen;
    }
    
    static GUIOption[] gibWichtigeOptionen() {
        return wichtigeOptionen;
    }
    
    void zeige() {
        if (!istSichtbar) {
            istSichtbar = true;
            zeichne();
        }
    }
    
    void verberge() {
        loesche(); // "tue nichts" wird in loesche() abgefangen.
        istSichtbar = false;
    }

    void dreheAuf(int neuerWinkel) {
        loesche();
        orientierung = neuerWinkel;
        zeichne();
    }
    
    void dreheUm(int winkel) {
        loesche();
        orientierung += winkel;
        zeichne();
    }

    void bewegeHorizontal(int entfernung) {
        loesche();
        xPosition += entfernung;
        zeichne();
    }

    void bewegeVertikal(int entfernung) {
        loesche();
        yPosition += entfernung;
        zeichne();
    }
    
    void aendereFarbe(String neueFarbe) {
        loesche();
        farbe = neueFarbe;
        zeichne();
    }
    
    void aendereFarbe(String neueFarbe, boolean istAuswahl) {
        if (istAuswahl) {
            loesche();
            farbe = neueFarbe;
            zeichne();
        } else {
            aendereFarbe(neueFarbe);
        }
    }

    protected void zeichne() {
        if (istSichtbar) {
            Shape figur = gibAktuelleFigur();
            Leinwand leinwand = Leinwand.gibLeinwand();
            if (!istAusgewaehlt) {
                leinwand.zeichne (
                this,           // leinwand kennt das Objekt
                farbe,          // definiert seine Zeichenfarbe
                figur);         // definiert seinen grafischen Aspekt
            } else {
                leinwand.zeichne (
                this,           // leinwand kennt das Objekt
                "rot",          // Rot als Auswahl-Farbe
                figur);         // definiert seinen grafischen Aspekt
            }
            leinwand.warte(10);
        }
    }

    protected void loesche() {
        if (istSichtbar){
            Leinwand leinwand = Leinwand.gibLeinwand();
            leinwand.entferne(this);
        }
    }
}
