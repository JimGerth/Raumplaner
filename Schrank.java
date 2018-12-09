import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

class Schrank extends Moebel {
    
    int breite;
    int tiefe;
    static GUIOption[] optionen = {
        new GUIOption("X-Position"),
        new GUIOption("Y-Position"),
        new GUIOption("X-Scale"),
        new GUIOption("Y-Scale"),
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

    Schrank(int xPosition, int yPosition, int xScale, int yScale, String farbe, int orientierung, int breite, int tiefe) {
        super(xPosition, yPosition, xScale, yScale, farbe, orientierung);
        this.breite = breite;
        this.tiefe  = tiefe;
        this.art = "Schrank";
    }
    
    Schrank(int breite, int tiefe) {
        this(0, 0, 1, 1, "schwarz", 0, breite, tiefe);
    }
    
    protected Shape getFigur() {
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
        return schrank;
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
                        return Integer.toString(xScale);
                    case 3:
                        return Integer.toString(yScale);
                    case 4:
                        return farbe;
                    case 5:
                        return Integer.toString(orientierung);
                    case 6:
                        return Integer.toString(breite);
                    case 7:
                        return Integer.toString(tiefe);
                    case 8:
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
            new GUIOption("X-Scale"),
            new GUIOption("Y-Scale"),
            new GUIOption("Farbe"),
            new GUIOption("Orientierung"),
            new GUIOption("Breite"),
            new GUIOption("Tiefe"),
            new GUIOption("Art")
        };
        return optionen;
    }
}
