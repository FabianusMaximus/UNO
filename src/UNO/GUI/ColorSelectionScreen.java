package UNO.GUI;

import UNO.Control.GUIGameControl;
import UNO.util.SwingCalculation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorSelectionScreen extends JFrame {
    private GUIGameControl guiGameControl;
    private JPanel farbauswahlPanel;

    private JButton[] btn_Farben = new JButton[4];

    public ColorSelectionScreen(GUIGameControl guiGameControl) {
        this.guiGameControl = guiGameControl;

        int width = 500;
        int height = 200;

        setTitle("Farbauswahl");
        setSize(width, height);

        farbauswahlPanel = new JPanel();
        farbauswahlPanel.setSize(width,height);
        farbauswahlPanel.setLocation(0, SwingCalculation.centerY(this, farbauswahlPanel));
        farbauswahlPanel.setLayout(new GridLayout(1, 4, 5, 5));
        this.add(farbauswahlPanel);

        for (int i = 0; i < btn_Farben.length; i++) {
            btn_Farben[i] = new JButton();
            farbauswahlPanel.add(btn_Farben[i]);
            int finalI = i;
            btn_Farben[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guiGameControl.clickColor(finalI);
                }
            });
        }
        btn_Farben[0].setBackground(Color.red);
        btn_Farben[1].setBackground(Color.green);
        btn_Farben[2].setBackground(Color.blue);
        btn_Farben[3].setBackground(Color.yellow);

        toFront();
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
