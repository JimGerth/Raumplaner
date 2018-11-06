import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIOption {
    
    JLabel label = new JLabel();
    JTextField textField = new JTextField();
    String beschreibung;
    
    public GUIOption(String beschreibung) {
        this.beschreibung = beschreibung;
        label.setText(this.beschreibung);
        label.setHorizontalAlignment(JLabel.RIGHT);
    }
}