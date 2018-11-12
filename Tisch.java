import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

class Tisch extends Moebel {
    
    private int breite;
    private int tiefe;
    static String art = "Tisch";
    static GUIOption[] optionen = {
        new GUIOption("Breite:"),
        new GUIOption("Tiefe:")
    };

    Tisch(int xPosition, int yPosition, String farbe, int orientierung, int breite, int tiefe)  {
        super(xPosition, yPosition, farbe, orientierung);
        this.breite = breite;
        this.tiefe  = tiefe;
    }
    
    Tisch(int breite, int tiefe) {
        this(0, 0, "schwarz", 0, breite, tiefe);
    }

    protected Shape gibAktuelleFigur()
    {
        // definieren
        Shape tisch = new Ellipse2D.Double(0 , 0, breite, tiefe);
        
        // transformieren
        AffineTransform t = new AffineTransform();
        t.translate(xPosition, yPosition);
        Rectangle2D umriss = tisch.getBounds2D();
        t.rotate(Math.toRadians(orientierung),umriss.getX()+umriss.getWidth()/2,umriss.getY()+umriss.getHeight()/2);
        return  t.createTransformedShape(tisch);
    }
}
