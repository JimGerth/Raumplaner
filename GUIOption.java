import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class GUIOption {
    
    JLabel label = new JLabel();
    JTextField textField = new JTextField();
    String name;
    
    
    GUIOption(String name) {
        this.name = name;
        label.setText(name + ":");
        label.setHorizontalAlignment(JLabel.RIGHT);
    }
}