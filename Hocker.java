import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.Rectangle;


class Hocker extends Moebel {

    int durchmesser;
    static GUIOption[] optionen = {
        new GUIOption("X-Position"),
        new GUIOption("Y-Position"),
        new GUIOption("Scale"),
        new GUIOption("Farbe"),
        new GUIOption("Orientierung"),
        new GUIOption("Durchmesser"),
        new GUIOption("Art")
    };
    static GUIOption[] wichtigeOptionen = {
        new GUIOption("Durchmesser")
    };
    
    
    Hocker(int xPosition, int yPosition, double scale, String farbe, int orientierung, int durchmesser)  {
        super(xPosition, yPosition, scale, farbe, orientierung);
        this.durchmesser = durchmesser;
        this.art = "Hocker";
    }
    
    Hocker(int durchmesser) {
        this(0, 0, 1, "schwarz", 0, durchmesser);
    }
    
    protected Shape getFigur() {
        Shape hocker = new Ellipse2D.Double(0, 0, durchmesser, durchmesser);
        return  hocker;
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
                        return Integer.toString(durchmesser);
                    case 6:
                        return art;
                }
            }
        }
        return ""; // bzw throw error, that attribut doesnt exist 
    }
    
    GUIOption[] getOptionen() { // nicht mehr noetig wenn null problem geloest ist...
        GUIOption[] optionen = {
            new GUIOption("X-Position"),
            new GUIOption("Y-Position"),
            new GUIOption("Scale"),
            new GUIOption("Farbe"),
            new GUIOption("Orientierung"),
            new GUIOption("Durchmesser"),
            new GUIOption("Art")
        };
        return optionen;
    }
}
