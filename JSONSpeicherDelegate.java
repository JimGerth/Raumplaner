import java.util.ArrayList;

class JSONSpeicherDelegate extends SpeicherProtokoll {
    void speicher(ArrayList<Moebel> alleMoebel) {
        String outputString = toJSON(alleMoebel);
        System.out.println(outputString);
    }
    
    private String toJSON(ArrayList<Moebel> alleMoebel) {
        String JSONString = "[\n";
        for (int i = 0; i < alleMoebel.size(); i++) {
            JSONString += "\t{\n\t\t\"art\": \"" + alleMoebel.get(i).art + "\"\n\t}";
            if (!(i == alleMoebel.size() - 1)) {
                JSONString += ",\n";
            } else {
                JSONString += "\n";
            }
        }
        JSONString += "]";
        return JSONString;
    }
}