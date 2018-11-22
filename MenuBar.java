import javax.swing.*;

class MenuBar extends JMenuBar {
    JMenu[] menus = {};
    
    MenuBar(JMenu[] menus) {
        super();
        for (JMenu menu : menus) {
            this.add(menu);
        }
    }
}