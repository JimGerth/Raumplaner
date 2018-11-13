import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

public class Hocker extends Moebel {

    int durchmesser;
    static GUIOption[] optionen = {
        new GUIOption("Durchmesser:")
    };
    
    // jedes moebel soll ein optionen array haben, fuer die optionen, die bei der inizialisierung wichtig sind.
    // außerdem muss jedes moebel einen convienience initializer haben, der genau diese optionen als parameter
    // hat und allen anderen fields einen default wert zuschreibt.
    
    public Hocker(int xPosition, int yPosition, String farbe, int orientierung, int durchmesser)  {
        super(xPosition, yPosition, farbe, orientierung);
        this.durchmesser = durchmesser;
        this.art = "Hocker";
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
    
    String toJSON() {
        return ""
            + "\t{\n"
            + "\t\t\"art\": \"" + art + "\",\n"
            + "\t\t\"xPosition\": \"" + xPosition + "\",\n"
            + "\t\t\"yPosition\": \"" + yPosition + "\",\n"
            + "\t\t\"farbe\": \"" + farbe + "\",\n"
            + "\t\t\"durchmesser\": \"" + durchmesser + "\"\n"
            + "\t}";
    }
}