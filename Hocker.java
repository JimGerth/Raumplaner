import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

class Hocker extends Moebel {

    private int durchmesser;
    static String art = "Hocker";
    static GUIOption[] optionen = {
        new GUIOption("Durchmesser:")
    };
    
    Hocker(int xPosition, int yPosition, String farbe, int orientierung, int durchmesser)  {
        super(xPosition, yPosition, farbe, orientierung);
        this.durchmesser = durchmesser;
    }
    
    Hocker(int durchmesser) {
        this(0, 0, "schwarz", 0, durchmesser);
    }
    
    protected Shape gibAktuelleFigur()
    {
        // definieren
        Shape Hocker = new Ellipse2D.Double(0 , 0, durchmesser, durchmesser);
        // transformieren
        AffineTransform t = new AffineTransform();
        t.translate(xPosition, yPosition);
        Rectangle2D umriss = Hocker.getBounds2D();
        t.rotate(Math.toRadians(orientierung),umriss.getX()+umriss.getWidth()/2,umriss.getY()+umriss.getHeight()/2);
        return  t.createTransformedShape(Hocker);
    }
}