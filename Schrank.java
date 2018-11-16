import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

class Schrank extends Moebel {
    
    private int breite;
    private int tiefe;
    static GUIOption[] optionen = {
        new GUIOption("X-Position"),
        new GUIOption("Y-Position"),
        new GUIOption("Farbe"),
        new GUIOption("Orientierung"),
        new GUIOption("Breite"),
        new GUIOption("Tiefe"),
        new GUIOption("Art")
    };
    static GUIOption[] wichtigeOptionen = {
        new GUIOption("Breite"),
        new GUIOption("Tiefe")
    };
    
    int getBreite() {
        return breite;
    }

    Schrank(int xPosition, int yPosition, String farbe, int orientierung, int breite, int tiefe) {
        super(xPosition, yPosition, farbe, orientierung);
        this.breite = breite;
        this.tiefe  = tiefe;
        this.art = "Schrank";
    }
    
    Schrank(int breite, int tiefe) {
        this(0, 0, "schwarz", 0, breite, tiefe);
    }
    
    GUIOption[] getOptionen() {
        GUIOption[] optionen = {
            new GUIOption("X-Position"),
            new GUIOption("Y-Position"),
            new GUIOption("Farbe"),
            new GUIOption("Orientierung"),
            new GUIOption("Breite"),
            new GUIOption("Tiefe"),
            new GUIOption("Art")
        };
        return optionen;
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
    /*
    String toJSON() {
        return ""
            + "\t{\n"
            + "\t\t\"Art:\": \"" + art + "\",\n"
            + "\t\t\"X-Position:\": " + xPosition + ",\n"
            + "\t\t\"Y-Position:\": " + yPosition + ",\n"
            + "\t\t\"Farbe:\": \"" + farbe + "\",\n"
            + "\t\t\"Orientierung:\": " + orientierung + ",\n"
            + "\t\t\"Breite:\": " + breite + ",\n"
            + "\t\t\"Tiefe:\": " + tiefe + ",\n"
            + "\t}";
    }
    */
    String gibWert(String attributName) {
        for (int i = 0; i < optionen.length; i++) {
            if (attributName == optionen[i].beschreibung) {
                switch (i) {
                    case 0:
                        return Integer.toString(xPosition);
                    case 1:
                        return Integer.toString(yPosition);
                    case 2:
                        return farbe;
                    case 3:
                        return Integer.toString(orientierung);
                    case 4:
                        return Integer.toString(breite);
                    case 5:
                        return Integer.toString(tiefe);
                    case 6:
                        return art;
                }
            }
        }
        return ""; //bzw throw error, that attribut doesnt exist
    }
}
