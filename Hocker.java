import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

public class Hocker extends Moebel {

    int durchmesser;
    static GUIOption[] optionen = {
        new GUIOption("X-Position:"),
        new GUIOption("Y-Position:"),
        new GUIOption("Farbe:"),
        new GUIOption("Orientierung:"),
        new GUIOption("Durchmesser:")
    };
    static GUIOption[] wichtigeOptionen = {
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
            + "\t\t\"Art:\": \"" + art + "\",\n"
            + "\t\t\"X-Position:\": " + xPosition + ",\n"
            + "\t\t\"Y-Position:\": " + yPosition + ",\n"
            + "\t\t\"Farbe:\": \"" + farbe + "\",\n"
            + "\t\t\"Orientierung:\": " + orientierung + ",\n"
            + "\t\t\"Durchmesser:\": " + durchmesser + ",\n"
            + "\t}";
    }
 
    int gibInt(String attributName) {
        for (int i = 0; i < optionen.length; i++) {
            if (attributName == optionen[i].beschreibung) {
                switch (i) {
                    case 0:
                        return xPosition;
                    case 1:
                        return yPosition;
                    case 3:
                        return orientierung;
                    case 4:
                        return durchmesser;
                }
            }
        }
        return 0; //bzw throw error, that attribut doesnt exist
    }
    
    String gibString(String attributName) {
        for (int i = 0; i < optionen.length; i++) {
            if (attributName == optionen[i].beschreibung) {
                switch (i) {
                    case 2:
                        return farbe;
                }
            }
        }
        return ""; //bzw trow error
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