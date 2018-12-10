import java.util.ArrayList;

interface SpeicherProtokoll {
    void speicher(ArrayList<Moebel> alleMoebel, String location);
    void lade(String location) throws Exception;
}   