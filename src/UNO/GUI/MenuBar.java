package UNO.GUI;

import UNO.Control.GUIGameControl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {
    private JMenu menu, submenu;

    private JMenuItem menuItem;

    private JRadioButtonMenuItem rbMenuItem;

    private JCheckBoxMenuItem cbMenuItem;

    public MenuBar(GUIGameControl guiGameControl) {

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

        //items in submenu
        for (int i = 0; i < 3; i++) {
            menuItem = new JMenuItem("Hand von Bot " + (i + 1));
            int finalI = i;
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Action listener ist da");
                    guiGameControl.clickMenuItem(finalI);
                }
            });
            submenu.add(menuItem);
        }

        menu.add(submenu);
    }
}
