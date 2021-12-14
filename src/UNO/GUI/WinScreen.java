package UNO.GUI;

import UNO.Control;
import util.ImagePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WinScreen extends JFrame implements ActionListener {
    private Control control;
    private JLabel lb_winner;
    private JPanel jp_Hintergrund;
    private JButton restart;

    public WinScreen(Control pControl) {
        control = pControl;
        Container cp = getContentPane();
        cp.setBackground(Color.BLACK);
        cp.setLayout(null);


        ImagePanel myPicture = new ImagePanel("src/img/confetti-Hintergrund.png");
        myPicture.setBounds(0, 0, 500, 400);
        cp.add(myPicture);

        jp_Hintergrund = new JPanel();
        jp_Hintergrund.setBounds(150, 150, 200, 50);
        jp_Hintergrund.setLayout(new BorderLayout());
        jp_Hintergrund.setBackground(Color.black);
        myPicture.add(jp_Hintergrund);

        lb_winner = new JLabel(control.getGewinner().getName() + " hat gewonnen");
        lb_winner.setForeground(Color.white);
        lb_winner.setBackground(Color.white);
        jp_Hintergrund.add(lb_winner, BorderLayout.CENTER);


        restart = new JButton("Neues Spiel");
        restart.setBounds(175, 300, 150, 50);
        restart.addActionListener(this);
        cp.add(restart);

        setTitle("Winning - Screen");
        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        new Control();
        dispose();
    }
    

}
