import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

class Schrankwand extends Moebel {
    
    private int anzahlDerEinheiten;
    private int breite;
    private int tiefe;
    static String art = "Schrankwand";
    static GUIOption[] optionen = {
        new GUIOption("Anzahl der Einheiten:"),
        new GUIOption("Breite:"),
        new GUIOption("Tiefe:")
    };
    
    Schrankwand(int xPosition, int yPosition, String farbe, int orientierung, int anzahlDerEinheiten, int breite, int tiefe) {
        super(xPosition, yPosition, farbe, orientierung);
        this.anzahlDerEinheiten = anzahlDerEinheiten;
        this.breite = breite;
        this.tiefe = tiefe;
    }
    
    Schrankwand(int xPosition, int yPosition, String farbe, int orientierung, int anzahlDerEinheiten) {
        this(xPosition, yPosition, farbe, orientierung, anzahlDerEinheiten, 60, 40);
    }
    
    Schrankwand(int anzahlDerEinheiten, int breite, int tiefe) {
        this(0, 0, "schwarz", 0, anzahlDerEinheiten, breite, tiefe);
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
}
