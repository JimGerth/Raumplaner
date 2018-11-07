import java.util.ArrayList;
import JAVASON.*;

class JSONSpeicherDelegate extends SpeicherProtokoll {
    void speicher(ArrayList<Moebel> alleMoebel) {
        String outputString = toJSON(alleMoebel);
        System.out.println(outputString);
    }
    
    private String toJSON(ArrayList<Moebel> alleMoebel) {
        String JSONString = "{";
        for (int i = 0; i < alleMoebel.size(); i++) {
            JSONString += "\"" + alleMoebel.get(i).art + "\": {}";
            if (!(i == alleMoebel.size() - 1)) {
                JSONString += ", ";
            }
        }
        JSONString += "}";
        return JSONString;
    }
}