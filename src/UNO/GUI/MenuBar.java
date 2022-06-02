package UNO.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {
    private JMenu menu, submenu;

    private JMenuItem menuItem;

    private JRadioButtonMenuItem rbMenuItem;

    private JCheckBoxMenuItem cbMenuItem;

    public MenuBar() {

        //Build the first menu.
        menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        this.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Spielregeln",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);

        //a submenu
        menu.addSeparator();
        submenu = new JMenu("Cheat Options");
        submenu.setMnemonic(KeyEvent.VK_S);

        menuItem = new JMenuItem("Hand von Bot 1");
        submenu.add(menuItem);

        menuItem = new JMenuItem("Hand von Bot 2");
        submenu.add(menuItem);

        menuItem = new JMenuItem("Hand von bot 3");
        submenu.add(menuItem);


        menu.add(submenu);
    }
}
