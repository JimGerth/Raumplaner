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
            } else {
                JSONString += serializeUnknown(alleMoebel.get(i));
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
            + "\t\t\"art\": \"" + moebel.art + "\",\n"
            + "\t\t\"xPosition\": \"" + moebel.xPosition + "\",\n"
            + "\t\t\"yPosition\": \"" + moebel.yPosition + "\",\n"
            + "\t\t\"farbe\": \"" + moebel.farbe + "\",\n";
    }
    
    private String serializeHocker(Hocker hocker) {
        return ""
            + "\t{\n"
            + serializeMoebel(hocker)
            + "\t\t\"Durchmesser\": \"" + hocker.durchmesser + "\"\n"
            + "\t}";
            // optionen array nutzen um zu serializen und serialize funktion generic machen? 
    }
    
    private String serializeUnknown(Moebel moebel) {
        return ""
            + "\t{\n"
            + "\t\tunknown Moebel!\n"
            + "\t}";
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