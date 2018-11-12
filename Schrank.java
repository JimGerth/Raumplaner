import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

class Schrank extends Moebel {
    
    private int breite;
    private int tiefe;
    static String art = "Schrank";
    static GUIOption[] optionen = {
        new GUIOption("Breite:"),
        new GUIOption("Tiefe:")
    };
    
    int getBreite() {
        return breite;
    }

    Schrank(int xPosition, int yPosition, String farbe, int orientierung, int breite, int tiefe) {
        super(xPosition, yPosition, farbe, orientierung);
        this.breite = breite;
        this.tiefe  = tiefe;
    }
    
    Schrank(int breite, int tiefe) {
        this(0, 0, "schwarz", 0, breite, tiefe);
    }
    
    protected Shape gibAktuelleFigur() {
        // einen GeneralPath definieren
        GeneralPath schrank = new GeneralPath();
        schrank.moveTo(0, 0);
        schrank.lineTo(breite, 0);
        schrank.lineTo(breite, tiefe);
        schrank.lineTo(0, tiefe);
        schrank.lineTo(0, 0);
        // Das ist die Umrandung. Der schrank bekommt noch ein Kreuz:
        schrank.moveTo(0, 0);
        schrank.lineTo(breite, tiefe);
        schrank.moveTo(0, tiefe);
        schrank.lineTo(breite, 0);
        // transformieren:
        AffineTransform t = new AffineTransform();
        t.translate(xPosition, yPosition);
        Rectangle2D umriss = schrank.getBounds2D();
        t.rotate(Math.toRadians(orientierung),umriss.getX()+umriss.getWidth()/2,umriss.getY()+umriss.getHeight()/2);
        return  t.createTransformedShape(schrank);
    }
}
