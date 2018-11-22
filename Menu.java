import javax.swing.*;

class Menu extends JMenu {
    JMenuItem[] menuItems = {};
    
    Menu(JMenuItem[] menuItems) {
        super();
        for (JMenuItem menuItem : menuItems) {
            this.add(menuItem);
        }
    }
}