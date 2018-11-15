import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

public class Schrankwand extends Moebel {
    
    private int anzahlDerEinheiten;
    private int breite;
    private int tiefe;
    static GUIOption[] optionen = {
        new GUIOption("X-Position:"),
        new GUIOption("Y-Position:"),
        new GUIOption("Orientierung:"),
        new GUIOption("Farbe:"),
        new GUIOption("Anzahl der Einheiten:", true),
        new GUIOption("Breite:", true),
        new GUIOption("Tiefe:", true)
    };
    
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
            + "\t\t\"Art:\": \"" + art + "\",\n"
            + "\t\t\"X-Position:\": " + xPosition + ",\n"
            + "\t\t\"Y-Position:\": " + yPosition + ",\n"
            + "\t\t\"Farbe:\": \"" + farbe + "\",\n"
            + "\t\t\"Orientierung:\": " + orientierung + ",\n"
            + "\t\t\"Anzahl der Einheiten:\": " + anzahlDerEinheiten + ",\n"
            + "\t\t\"Breite:\": " + breite + ",\n"
            + "\t\t\"Tiefe:\": " + tiefe + ",\n"
            + "\t}";
    }
}
