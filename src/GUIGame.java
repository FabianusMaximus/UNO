import javax.swing.*;
import java.awt.*;

public class GUIGame extends JFrame {
    Control control;

    public GUIGame(Control pControl) {
        control = pControl;
        setTitle("UNO - Gamescreen");
        Container cp = getContentPane();
        cp.setLayout(null);


        setSize(1000, 563);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

}
