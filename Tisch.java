import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

class Tisch extends Moebel {
    
    int breite;
    int tiefe;
    static GUIOption[] optionen = {
        new GUIOption("X-Position"),
        new GUIOption("Y-Position"),
        new GUIOption("Scale"),
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

    Tisch(int xPosition, int yPosition, double scale, String farbe, int orientierung, int breite, int tiefe)  {
        super(xPosition, yPosition, scale, farbe, orientierung);
        this.breite = breite;
        this.tiefe  = tiefe;
        this.art = "Tisch";
    }
    
    Tisch(int breite, int tiefe) {
        this(0, 0, 1, "schwarz", 0, breite, tiefe);
    }

    protected Shape getFigur() {
        Shape tisch = new Ellipse2D.Double(0 , 0, breite, tiefe);
        return tisch;
    }

    String getWert(String attributName) {
        for (int i = 0; i < optionen.length; i++) {
            if (attributName == optionen[i].name) {
                switch (i) {
                    case 0:
                        return Integer.toString(xPosition);
                    case 1:
                        return Integer.toString(yPosition);
                    case 2:
                        return Double.toString(scale);
                    case 3:
                        return farbe;
                    case 4:
                        return Integer.toString(orientierung);
                    case 5:
                        return Integer.toString(breite);
                    case 6:
                        return Integer.toString(tiefe);
                    case 7:
                        return art;
                }
            }
        }
        return ""; //bzw throw error, that attribut doesnt exist
    }
    
    GUIOption[] getOptionen() {
        GUIOption[] optionen = {
            new GUIOption("X-Position"),
            new GUIOption("Y-Position"),
            new GUIOption("Scale"),
            new GUIOption("Farbe"),
            new GUIOption("Orientierung"),
            new GUIOption("Breite"),
            new GUIOption("Tiefe"),
            new GUIOption("Art")
        };
        return optionen;
    }
}
