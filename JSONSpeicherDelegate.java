import java.io.*;
import java.util.ArrayList;

class JSONSpeicherDelegate implements SpeicherProtokoll {
    
    public void speicher(ArrayList<Moebel> alleMoebel) {
        try {
            FileWriter fw = new FileWriter("/Users/jim/Desktop/JSON.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(toJSON(alleMoebel));
            bw.close();
        } catch (IOException e) {
            System.out.println("error while trying to write file");
        }
    }
    
    private String toJSON(ArrayList<Moebel> alleMoebel) {
        String JSONString = "[\n";
        for (int i = 0; i < alleMoebel.size(); i++) {
            JSONString += alleMoebel.get(i).toJSON();
            if (i < alleMoebel.size() - 1) {
                JSONString += ",\n";
            }
        }
        JSONString += "\n]";
        return JSONString;
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