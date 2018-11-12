import java.util.ArrayList;

class JSONSpeicherDelegate implements SpeicherProtokoll {
    public void speicher(ArrayList<Moebel> alleMoebel) {
        String outputString = toJSON(alleMoebel);
        System.out.println(outputString);
    }
    
    private String toJSON(ArrayList<Moebel> alleMoebel) {
        // switch die moebel art mit instanceof und dann passenden Serializer callen
        String JSONString = "[\n";
        for (int i = 0; i < alleMoebel.size(); i++) {
            if (alleMoebel.get(i) instanceof Hocker) {
                JSONString += serializeHocker( (Hocker) alleMoebel.get(i));
            }
            if (i < alleMoebel.size() - 1) {
                JSONString += ",\n";
            }
        }
        JSONString += "\n]";
        return JSONString;
    }
    
    private String serializeMoebel(Moebel moebel) {
        return ""
            + "\"art\": \"" + moebel.art + "\",\n"
            + "\"xPosition\": \"" + moebel.xPosition + "\",\n"
            + "\"yPosition\": \"" + moebel.yPosition + "\",\n"
            + "\"farbe\": \"" + moebel.farbe + "\",\n";
    }
    
    private String serializeHocker(Hocker hocker) {
        return ""
            + "{\n"
            + serializeMoebel(hocker)
            + "\"Durchmesser\": \"" + hocker.durchmesser + "\"\n"
            + "}";
            // optionen array nutzen um zu serializen und serialize funktion generic machen? 
    }
    
    /*
    private String serialize<T extends Moebel>(T moebel) {
        String output;
        output += "{\n";
        for (int i = 0; i < T.optionen.length; i++) {
            output += "\"" + T.optionen[i].beschreibung + "\": \"" + moebel.??? + "\"\n";
        }
        output += "}";
        return output;
    }
    Eventuell braucht man noch ein weiteres array mit ALLEN attributen!?
     */
}