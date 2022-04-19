package UNO.GUI;

import UNO.Control;

import javax.swing.*;
import java.awt.*;

public class BotHand extends JFrame {
    private Control control;
    private GridLayout gridLayout;

    private JPanel[] Cards;

    public BotHand(Control control) {
        this.control = control;
        this.gridLayout = new GridLayout(1, control.getPlayer(1).getAnzCards());

        Cards = new JPanel[control.getPlayer(1).getHand().size()];

        setTitle("Hand von Bot " + 1);
        setSize(2000, 500);
        setLayout(gridLayout);
        setVisible(true);
    }

    public void updateGrid(int anzCards) {
        this.gridLayout.setColumns(anzCards);
    }
}
