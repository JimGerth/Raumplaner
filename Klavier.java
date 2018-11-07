import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

public class Klavier extends Moebel {
    
    private int breite;
    private int tiefe;
    private int anzahlTasten;
    private int hoeheTasten;
    
    public Klavier(int xPosition, int yPosition, String farbe, int orientierung) {
        super(xPosition, yPosition, farbe, orientierung);
        this.breite = 60;
        this.tiefe  = 40;
        this.anzahlTasten = 12;
        this.hoeheTasten = 15;
    }

    public Klavier(int xPosition, int yPosition, String farbe, int orientierung, int breite, int tiefe, int anzahlTasten, int hoeheTasten) {
        super(xPosition, yPosition, farbe, orientierung);
        this.breite = breite;
        this.tiefe  = tiefe;
        this.anzahlTasten = anzahlTasten;
        this.hoeheTasten = hoeheTasten;
        this.art = "Klavier";
    }

    protected Shape gibAktuelleFigur() {
        // einen GeneralPath definieren
        GeneralPath klavier = new GeneralPath();
        klavier.moveTo(0, 0);
        klavier.lineTo(breite, 0);
        klavier.lineTo(breite, tiefe);
        klavier.lineTo(0, tiefe);
        klavier.lineTo(0, 0);
        //Tasten
        klavier.moveTo(breite / (anzahlTasten + 2) , tiefe);
        int currentX = breite / (anzahlTasten + 2);
        int currentY = tiefe;
        for(int i = 0; i <= anzahlTasten; i++) {
            klavier.lineTo(currentX, currentY - hoeheTasten);
            klavier.lineTo(currentX + (breite / (anzahlTasten + 2)),currentY - hoeheTasten);
            klavier.lineTo(currentX + (breite / (anzahlTasten + 2)), currentY);
            currentX += breite / (anzahlTasten + 2);
        }
        // transformieren:
        AffineTransform t = new AffineTransform();
        t.translate(xPosition, yPosition);
        Rectangle2D umriss = klavier.getBounds2D();
        t.rotate(Math.toRadians(orientierung),umriss.getX()+umriss.getWidth()/2,umriss.getY()+umriss.getHeight()/2);
        return  t.createTransformedShape(klavier);
    }
}
