import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import JAVASON.*;


class JSONSpeicherDelegate implements SpeicherProtokoll {
    
    public void speicher(ArrayList<Moebel> alleMoebel, String location) {
        try {
            FileWriter fw = new FileWriter(location);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(toJSON(alleMoebel));
            bw.close();
        } catch (Exception e) {
            new FehlerSplashScreen("Speicher-Fehler");
        }
    }
    
    private String toJSON(ArrayList<Moebel> alleMoebel) {
        String JSONString = "[\n";
        for (int i = 0; i < alleMoebel.size(); i++) {
            JSONString += serialize(alleMoebel.get(i));
            if (i < alleMoebel.size() - 1) {
                JSONString += ",\n";
            }
        }
        JSONString += "\n]";
        return JSONString;
    }
    
    private <T extends Moebel> String serialize(T moebel) {
        String output = "\t{\n";
        for (int i = 0; i < moebel.getOptionen().length; i++) {
            output += "\t\t\"" + moebel.getOptionen()[i].name + "\": \"" + moebel.getWert(moebel.getOptionen()[i].name) + "\"";
            if (i < moebel.getOptionen().length - 1) {
                output += ",\n";
            }
        }
        output += "\n\t}";
        return output;
    }
    
    public void lade(String location) throws Exception {
        String JSONString = "";
        JSONString = new String(Files.readAllBytes(Paths.get(location)));
        JSONArray alleMoebel = new JSONArray(JSONString);
        Leinwand.gibLeinwand().ladeMoebel(JSONArrayToMoebelArray(alleMoebel));
    }
    
    private ArrayList<Moebel> JSONArrayToMoebelArray(JSONArray alleJSONMoebel) { // TODO: maybe new failable moebel initializor from JSONObject..?
        ArrayList<Moebel> alleMoebel = new ArrayList<Moebel>();
        for (int i = 0; i < alleJSONMoebel.length(); i++) {
            switch (alleJSONMoebel.getJSONObject(i).getString("Art")) {
                case "Hocker":
                    alleMoebel.add(new Hocker(
                        alleJSONMoebel.getJSONObject(i).getInt(Hocker.optionen[0].name), // xPosition
                        alleJSONMoebel.getJSONObject(i).getInt(Hocker.optionen[1].name), // yPosition
                        alleJSONMoebel.getJSONObject(i).getDouble(Hocker.optionen[2].name), // scale
                        alleJSONMoebel.getJSONObject(i).getString(Hocker.optionen[3].name), // farbe
                        alleJSONMoebel.getJSONObject(i).getInt(Hocker.optionen[4].name), // usw.
                        alleJSONMoebel.getJSONObject(i).getInt(Hocker.optionen[5].name)
                    ));
                    break;
                case "Stuhl":
                    alleMoebel.add(new Stuhl(
                        alleJSONMoebel.getJSONObject(i).getInt(Stuhl.optionen[0].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Stuhl.optionen[1].name),
                        alleJSONMoebel.getJSONObject(i).getDouble(Stuhl.optionen[2].name),
                        alleJSONMoebel.getJSONObject(i).getString(Stuhl.optionen[3].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Stuhl.optionen[4].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Stuhl.optionen[5].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Stuhl.optionen[6].name)
                    ));
                    break;
                case "Tisch":
                    alleMoebel.add(new Tisch(
                        alleJSONMoebel.getJSONObject(i).getInt(Tisch.optionen[0].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Tisch.optionen[1].name),
                        alleJSONMoebel.getJSONObject(i).getDouble(Tisch.optionen[2].name),
                        alleJSONMoebel.getJSONObject(i).getString(Tisch.optionen[3].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Tisch.optionen[4].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Tisch.optionen[5].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Tisch.optionen[6].name)
                    ));
                    break;
                case "Schrank":
                    alleMoebel.add(new Schrank(
                        alleJSONMoebel.getJSONObject(i).getInt(Schrank.optionen[0].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrank.optionen[1].name),
                        alleJSONMoebel.getJSONObject(i).getDouble(Schrank.optionen[2].name),
                        alleJSONMoebel.getJSONObject(i).getString(Schrank.optionen[3].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrank.optionen[4].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrank.optionen[5].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrank.optionen[6].name)
                    ));
                    break;
                case "Schrankwand":
                    alleMoebel.add(new Schrankwand(
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[0].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[1].name),
                        alleJSONMoebel.getJSONObject(i).getDouble(Schrankwand.optionen[2].name),
                        alleJSONMoebel.getJSONObject(i).getString(Schrankwand.optionen[3].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[4].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[5].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[6].name),
                        alleJSONMoebel.getJSONObject(i).getInt(Schrankwand.optionen[7].name)
                    ));
                    break;
            }
        }
        return alleMoebel;
    }
}