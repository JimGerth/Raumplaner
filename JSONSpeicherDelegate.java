import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import JAVASON.*;

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
    
    public void lade() {
        String JSONString = "";
        try {
            JSONString = new String(Files.readAllBytes(Paths.get("/Users/jim/Desktop/JSON.txt")));
        } catch (IOException e) {
            System.out.println("error while trying to read file");
        } // handle JAVASONs errors -> no valid JSON...
        JSONArray alleMoebel = new JSONArray(JSONString);
        Leinwand.gibLeinwand().ladeMoebel(JSONArrayToMoebelArray(alleMoebel));
        // still need to change alleMoebel in GUI!
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
    
    private ArrayList<Moebel> JSONArrayToMoebelArray(JSONArray alleJSONMoebel) {
        ArrayList<Moebel> alleMoebel = new ArrayList<Moebel>();
        for (int i = 0; i < alleJSONMoebel.length(); i++) {
            switch (alleJSONMoebel.getJSONObject(i).getString("art")) {
                case "Hocker":
                    alleMoebel.add(new Hocker(
                        alleJSONMoebel.getJSONObject(i).getInt("xPosition"),
                        alleJSONMoebel.getJSONObject(i).getInt("yPosition"),
                        alleJSONMoebel.getJSONObject(i).getString("farbe"),
                        alleJSONMoebel.getJSONObject(i).getInt("orientierung"),
                        alleJSONMoebel.getJSONObject(i).getInt("durchmesser")
                    ));
                    break;
                case "Stuhl":
                    alleMoebel.add(new Stuhl(
                        alleJSONMoebel.getJSONObject(i).getInt("xPosition"),
                        alleJSONMoebel.getJSONObject(i).getInt("yPosition"),
                        alleJSONMoebel.getJSONObject(i).getString("farbe"),
                        alleJSONMoebel.getJSONObject(i).getInt("orientierung"),
                        alleJSONMoebel.getJSONObject(i).getInt("breite"),
                        alleJSONMoebel.getJSONObject(i).getInt("tiefe")
                    ));
                    break;
                case "Tisch":
                    alleMoebel.add(new Tisch(
                        alleJSONMoebel.getJSONObject(i).getInt("xPosition"),
                        alleJSONMoebel.getJSONObject(i).getInt("yPosition"),
                        alleJSONMoebel.getJSONObject(i).getString("farbe"),
                        alleJSONMoebel.getJSONObject(i).getInt("orientierung"),
                        alleJSONMoebel.getJSONObject(i).getInt("breite"),
                        alleJSONMoebel.getJSONObject(i).getInt("tiefe")
                    ));
                    break;
                case "Schrank":
                    alleMoebel.add(new Schrank(
                        alleJSONMoebel.getJSONObject(i).getInt("xPosition"),
                        alleJSONMoebel.getJSONObject(i).getInt("yPosition"),
                        alleJSONMoebel.getJSONObject(i).getString("farbe"),
                        alleJSONMoebel.getJSONObject(i).getInt("orientierung"),
                        alleJSONMoebel.getJSONObject(i).getInt("breite"),
                        alleJSONMoebel.getJSONObject(i).getInt("tiefe")
                    ));
                    break;
                case "Schrankwand":
                    alleMoebel.add(new Schrankwand(
                        alleJSONMoebel.getJSONObject(i).getInt("xPosition"),
                        alleJSONMoebel.getJSONObject(i).getInt("yPosition"),
                        alleJSONMoebel.getJSONObject(i).getString("farbe"),
                        alleJSONMoebel.getJSONObject(i).getInt("orientierung"),
                        alleJSONMoebel.getJSONObject(i).getInt("anzahlDerEinheiten"),
                        alleJSONMoebel.getJSONObject(i).getInt("breite"),
                        alleJSONMoebel.getJSONObject(i).getInt("tiefe")
                    ));
                    break;
            }
        }
        return alleMoebel;
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