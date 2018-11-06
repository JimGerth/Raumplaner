import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Arc2D;

public class Badewanne extends Moebel {
    
    private int breite;
    private int tiefe;

    public Badewanne(int xPosition, int yPosition, String farbe, int orientierung, int breite, int tiefe) {
        super(xPosition, yPosition, farbe, orientierung);
        this.breite = breite;
        this.tiefe  = tiefe;
    }

    protected Shape gibAktuelleFigur() {
        // einen GeneralPath definieren
        GeneralPath badewanne = new GeneralPath();
        Shape rectangle = new Rectangle2D.Double(0, 0, breite, tiefe);
        //innere ding da shiieeeet
        badewanne.moveTo(150, 10);
        badewanne.lineTo(10, 10);
        badewanne.lineTo(10, 70);
        badewanne.lineTo(150, 70);
        Shape arc = new Arc2D.Double();
        // transformieren:
        AffineTransform t = new AffineTransform();
        t.translate(xPosition, yPosition);
        Rectangle2D umriss = badewanne.getBounds2D();
        t.rotate(Math.toRadians(orientierung),umriss.getX()+umriss.getWidth()/2,umriss.getY()+umriss.getHeight()/2);
        return  t.createTransformedShape(badewanne);
    }
}
