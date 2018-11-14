import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

public class Tisch extends Moebel {
    
    private int breite;
    private int tiefe;
    static GUIOption[] optionen = {
        new GUIOption("X-Position:"),
        new GUIOption("Y-Position:"),
        new GUIOption("Orientierung:"),
        new GUIOption("Farbe:"),
        new GUIOption("Breite:", true),
        new GUIOption("Tiefe:", true)
    };

    public Tisch(int xPosition, int yPosition, String farbe, int orientierung, int breite, int tiefe)  {
        super(xPosition, yPosition, farbe, orientierung);
        this.breite = breite;
        this.tiefe  = tiefe;
        this.art = "Tisch";
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
    
    String toJSON() {
        return ""
            + "\t{\n"
            + "\t\t\"art\": \"" + art + "\",\n"
            + "\t\t\"xPosition\": " + xPosition + ",\n"
            + "\t\t\"yPosition\": " + yPosition + ",\n"
            + "\t\t\"farbe\": \"" + farbe + "\",\n"
            + "\t\t\"orientierung\": " + orientierung + ",\n"
            + "\t\t\"breite\": " + breite + ",\n"
            + "\t\t\"tiefe\": " + tiefe + ",\n"
            + "\t}";
    }
}
