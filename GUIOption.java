import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Klasse zum beschreiben einer Option bzw. eines Parameters, bei der Erstellung eines Moebels.                 *
 * Die Beschriebung sollte so aussehen: "<Name des Parameters, ausgeschrieben in Deutsch>:"                     *
 * Also z.B. wird aus anzahlDerEinheiten: "Anzahl der Einheiten:"                                               *
 *                                                                                                              *
 * Jede Option hat auch automatisch ein JLabel mit der Beschreibung,                                            *
 * welches bei z.B. einem Erstellungsdialog verwendet werden kann (siehe z.B. SchrankwandGUI)                   *
 *                                                                                                              *
 * Alle (zum speichern und laden) wichtigen Attribute eines Moebels sollten in seinem optionen-Array            *
 * gespeichert werden, wobei allerdings nur die Attribute, die wichtig zum Erstellen eines neuen                *
 * Moebels auch als "wichtig" gekennzeichnet werden sollten (wichtig = true).                                   *
 * Es ist letztendlich eine Design-Entscheidung, welche Attribute tatsaechlich wichtig sind, allerdings         *
 * rate ich dazu so wenig wie moeglich wichtige Attribute zu haben, um den Nutzer beim erstellen eines          *
 * neuen Moebels nicht zu ueberforden. Dinge, wie die Position sind z.B. nicht wichtig, da man sie beim         *
 * Erstellen sowieso noch nicht genau abschaetzen kann und spaeter oft noch aendert.                            *
 *                                                                                                              *
 * Jede Moebel-Klasse sollte moeglichst auch einen convienience Konstruktor haben, welcher nur die wichtigen    *
 * Attribute als Parameter annimmt und den designierten Konstruktor mit default-Werten fuer alle anderen        *
 * Attribute aufruft.                                                                                           *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
class GUIOption {
    
    JLabel label = new JLabel();
    JTextField textField = new JTextField();
    String beschreibung;
    boolean wichtig;
    
    GUIOption(String beschreibung, boolean wichtig) {
        this.wichtig = wichtig;
        this.beschreibung = beschreibung;
        label.setText(this.beschreibung);
        label.setHorizontalAlignment(JLabel.RIGHT);
    }
    
    GUIOption(String beschreibung) {
        this(beschreibung, false);
    }
}