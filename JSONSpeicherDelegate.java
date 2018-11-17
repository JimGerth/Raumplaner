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
    
    private String toJSON(ArrayList<Moebel> alleMoebel) {
        String JSONString = "[\n";
        for (int i = 0; i < alleMoebel.size(); i++) {
            // JSONString += alleMoebel.get(i).toJSON();
            JSONString += serialize(alleMoebel.get(i));
            if (i < alleMoebel.size() - 1) {
                JSONString += ",\n";
            }
        }
        JSONString += "\n]";
        return JSONString;
    }
    
    private <T extends Moebel> String serialize(T moebel) {
        String output = "{\n";
        for (int i = 0; i < moebel.getOptionen().length; i++) {
            output += "\"" + moebel.getOptionen()[i].name + "\": \"" + moebel.gibWert(moebel.getOptionen()[i].name) + "\"";
            if (i < moebel.getOptionen().length - 1) {
                output += ",\n";
            }
        }
        output += "\n}";
        return output;
    }
    
    public void lade() {
        String JSONString = "";
        try {
            JSONString = new String(Files.readAllBytes(Paths.get("/Users/jim/Desktop/JSON.txt")));
        } catch (IOException e) {
            System.out.println("error while trying to read file");
        } // handle JAVASONs errors -> no valid JSON...
        JSONArray alleMoebel = new JSONArray(JSONString);
        Leinwand.gibLeinwand().loescheMoebel();
        GUI.gibGUI().ladeMoebel(JSONArrayToMoebelArray(alleMoebel));
    }
    
    private ArrayList<Moebel> JSONArrayToMoebelArray(JSONArray alleJSONMoebel) { // TODO: maybe new failable moebel initializor from JSONObject..?
        ArrayList<Moebel> alleMoebel = new ArrayList<Moebel>();
        for (int i = 0; i < alleJSONMoebel.length(); i++) {
            switch (alleJSONMoebel.getJSONObject(i).getString("Art")) {
                case "Hocker":
                    alleMoebel.add(new Hocker(
                        alleJSONMoebel.getJSONObject(i).getInt(Hocker.optionen[0].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Hocker.optionen[1].name),
                        alleJSONMoebel.getJSONObject(i).getString(Hocker.optionen[2].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Hocker.optionen[3].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Hocker.optionen[4].name)
                    ));
                    break;
                case "Stuhl":
                    alleMoebel.add(new Stuhl(
                        alleJSONMoebel.getJSONObject(i).getInt(Stuhl.optionen[0].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Stuhl.optionen[1].name),
                        alleJSONMoebel.getJSONObject(i).getString(Stuhl.optionen[2].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Stuhl.optionen[3].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Stuhl.optionen[4].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Stuhl.optionen[5].name)
                    ));
                    break;
                case "Tisch":
                    alleMoebel.add(new Tisch(
                        alleJSONMoebel.getJSONObject(i).getInt(Tisch.optionen[0].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Tisch.optionen[1].name),
                        alleJSONMoebel.getJSONObject(i).getString(Tisch.optionen[2].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Tisch.optionen[3].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Tisch.optionen[4].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Tisch.optionen[5].name)
                    ));
                    break;
                case "Schrank":
                    alleMoebel.add(new Schrank(
                        alleJSONMoebel.getJSONObject(i).getInt(Schrank.optionen[0].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrank.optionen[1].name),
                        alleJSONMoebel.getJSONObject(i).getString(Schrank.optionen[2].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrank.optionen[3].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrank.optionen[4].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrank.optionen[5].name)
                    ));
                    break;
                case "Schrankwand":
                    alleMoebel.add(new Schrankwand(
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[0].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[1].name),
                        alleJSONMoebel.getJSONObject(i).getString(Schrankwand.optionen[2].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[3].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[4].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[5].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[6].name)
                    ));
                    break;
            }
        }
        return alleMoebel;
    }
}