import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

class Hocker extends Moebel {

    int durchmesser;
    static GUIOption[] optionen = {
        new GUIOption("X-Position"),
        new GUIOption("Y-Position"),
        new GUIOption("Farbe"),
        new GUIOption("Orientierung"),
        new GUIOption("Durchmesser"),
        new GUIOption("Art")
    };
    static GUIOption[] wichtigeOptionen = {
        new GUIOption("Durchmesser")
    };
    
    // jedes moebel soll ein optionen array haben, fuer die optionen, die bei der inizialisierung wichtig sind.
    // außerdem muss jedes moebel einen convienience initializer haben, der genau diese optionen als parameter
    // hat und allen anderen fields einen default wert zuschreibt.
    
    Hocker(int xPosition, int yPosition, String farbe, int orientierung, int durchmesser)  {
        super(xPosition, yPosition, farbe, orientierung);
        this.durchmesser = durchmesser;
        this.art = "Hocker";
    }
    
    Hocker(int durchmesser) {
        this(0, 0, "schwarz", 0, durchmesser);
    }
    
    GUIOption[] getOptionen() {
        GUIOption[] optionen = {
            new GUIOption("X-Position"),
            new GUIOption("Y-Position"),
            new GUIOption("Farbe"),
            new GUIOption("Orientierung"),
            new GUIOption("Durchmesser"),
            new GUIOption("Art")
        };
        return optionen;
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
    /*
    String toJSON() {
        return ""
            + "\t{\n"
            + "\t\t\"Art:\": \"" + art + "\",\n"
            + "\t\t\"X-Position:\": " + xPosition + ",\n"
            + "\t\t\"Y-Position:\": " + yPosition + ",\n"
            + "\t\t\"Farbe:\": \"" + farbe + "\",\n"
            + "\t\t\"Orientierung:\": " + orientierung + ",\n"
            + "\t\t\"Durchmesser:\": " + durchmesser + ",\n"
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
                        return Integer.toString(durchmesser);
                    case 5:
                        return art;
                }
            }
        }
        return ""; //bzw throw error, that attribut doesnt exist
    }
    
    /* failed attempt at generics.... maybe try later...
       <T> T gibWert(String attributName) {
        switch (attributName) {
            case optionen[0].beschreibung:
                return xPosition;
            case 
        }
    }*/
}
