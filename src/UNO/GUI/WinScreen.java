package UNO.GUI;

import UNO.Control.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinScreen extends JFrame implements ActionListener {
    private Control control;
    private JLabel lb_winner, imgLabel;
    private JPanel jp_Hintergrund;
    private JButton restart;

    public WinScreen(Control pControl) {
        control = pControl;
        Container cp = getContentPane();
        cp.setBackground(Color.BLACK);
        cp.setLayout(null);


        imgLabel = new JLabel(resizeImage(new ImageIcon("src/img/confetti-Hintergrund.png"),
                500, 400));
        imgLabel.setBounds(0, 0, 500, 400);
        cp.add(imgLabel);

        jp_Hintergrund = new JPanel();
        jp_Hintergrund.setBounds(150, 150, 200, 50);
        jp_Hintergrund.setLayout(new BorderLayout());
        jp_Hintergrund.setBackground(Color.black);
        imgLabel.add(jp_Hintergrund);

        lb_winner = new JLabel(control.getGewinner().getName() + " hat gewonnen");
        lb_winner.setForeground(Color.white);
        lb_winner.setBackground(Color.white);
        lb_winner.setHorizontalAlignment(SwingConstants.CENTER);
        lb_winner.setVerticalAlignment(SwingConstants.CENTER);
        jp_Hintergrund.add(lb_winner);


        restart = new JButton("Neues Spiel");
        restart.setBounds(175, 300, 150, 50);
        restart.addActionListener(this);
        cp.add(restart);

        setTitle("Winning - Screen");
        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private ImageIcon resizeImage(ImageIcon originalImage, int targetWidth, int targetHeight) {
        return new ImageIcon(originalImage.getImage()
                .getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        new Control();
        dispose();
    }


}
