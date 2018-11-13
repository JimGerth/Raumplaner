import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

public class Schrankwand extends Moebel {
    
    private int anzahlDerEinheiten;
    private int breite;
    private int tiefe;
    
    public Schrankwand(int xPosition, int yPosition, String farbe, int orientierung, int anzahlDerEinheiten, int breite, int tiefe) {
        super(xPosition, yPosition, farbe, orientierung);
        this.anzahlDerEinheiten = anzahlDerEinheiten;
        this.breite = breite;
        this.tiefe = tiefe;
        this.art = "Schrankwand";
    }
    
    public Schrankwand(int xPosition, int yPosition, String farbe, int orientierung, int anzahlDerEinheiten) {
        super(xPosition, yPosition, farbe, orientierung);
        this.anzahlDerEinheiten = anzahlDerEinheiten;
        this.breite = 60;
        this.tiefe = 40;
    }
    
    protected Shape gibAktuelleFigur() {
        GeneralPath schrankwand = new GeneralPath();
        
        for(int i = 0; i < anzahlDerEinheiten; i++) {
            Schrank schrank = new Schrank(0, 0, "schwarz", 0, breite, tiefe);
            schrank.bewegeHorizontal(i * schrank.getBreite());
            schrankwand.append(schrank.gibAktuelleFigur(), false);
        }
        
        AffineTransform t = new AffineTransform();
        t.translate(xPosition, yPosition);
        Rectangle2D umriss = schrankwand.getBounds2D();
        t.rotate(Math.toRadians(orientierung),umriss.getX()+umriss.getWidth()/2,umriss.getY()+umriss.getHeight()/2);
        return  t.createTransformedShape(schrankwand);
    }
    
    String toJSON() {
        return ""
            + "\t{\n"
            + "\t\t\"art\": \"" + art + "\",\n"
            + "\t\t\"xPosition\": " + xPosition + ",\n"
            + "\t\t\"yPosition\": " + yPosition + ",\n"
            + "\t\t\"farbe\": \"" + farbe + "\",\n"
            + "\t\t\"orientierung\": " + orientierung + ",\n"
            + "\t\t\"anzahlDerEinheiten\": " + anzahlDerEinheiten + ",\n"
            + "\t\t\"breite\": " + breite + ",\n"
            + "\t\t\"tiefe\": " + tiefe + ",\n"
            + "\t}";
    }
}
