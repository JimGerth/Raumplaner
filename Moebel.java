import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

abstract public class Moebel {
    
    protected int xPosition;
    protected int yPosition;
    protected int orientierung;
    protected String farbe;
    public boolean istAusgewaehlt;
    public String letzteFarbe = "schwarz";
    protected boolean istSichtbar = false;
    static String art;
    static ArrayList<GUIOption> optionen = new ArrayList<GUIOption>();

    Moebel(int xPosition, int yPosition, String farbe, int orientierung) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.farbe = farbe;
        this.orientierung = orientierung;
    }
    
    abstract protected Shape gibAktuelleFigur();
    
    public static ArrayList<GUIOption> getOptionen() {
        return optionen;
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
        letzteFarbe = neueFarbe;
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
                "rot",          // definiert seine Zeichenfarbe
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
