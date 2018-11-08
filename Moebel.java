import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

abstract public class Moebel {
    
    public String art;
    protected int xPosition;
    protected int yPosition;
    protected int orientierung;
    protected String farbe;
    public String letzteFarbe = "schwarz";
    protected boolean istSichtbar = false;
    static GUIOption[] optionen;

    public Moebel(int xPosition, int yPosition, String farbe, int orientierung) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.farbe = farbe;
        this.orientierung = orientierung;
    }
    
    abstract protected Shape gibAktuelleFigur();
    
    public void zeige() {
        if (!istSichtbar) {
            istSichtbar = true;
            zeichne();
        }
    }
    
    public void verberge() {
        loesche(); // "tue nichts" wird in loesche() abgefangen.
        istSichtbar = false;
    }

    public void dreheAuf(int neuerWinkel) {
        loesche();
        orientierung = neuerWinkel;
        zeichne();
    }
    
    public void dreheUm(int winkel) {
        loesche();
        orientierung += winkel;
        zeichne();
    }

    public void bewegeHorizontal(int entfernung) {
        loesche();
        xPosition += entfernung;
        zeichne();
    }

    public void bewegeVertikal(int entfernung) {
        loesche();
        yPosition += entfernung;
        zeichne();
    }
    
    public void aendereFarbe(String neueFarbe) {
        loesche();
        farbe = neueFarbe;
        letzteFarbe = neueFarbe;
        zeichne();
    }
    
    public void aendereFarbe(String neueFarbe, boolean istAuswahl) {
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
            leinwand.zeichne (
              this,           // leinwand kennt das Objekt
              farbe,          // definiert seine Zeichenfarbe
              figur);         // definiert seinen grafischen Aspekt
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
