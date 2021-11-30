package UNO.GUI;

import UNO.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIStart extends JFrame implements ActionListener {
    private Control control;
    private JPanel basePanel;
    private JLabel jl_anzSpieler;
    private JLabel jl_anzDeck;
    private JLabel jl_name;
    private JTextField tf_Spieler;
    private JTextField tf_Deck;
    private JTextField tf_Name;
    private JButton btn_start;

    private JOptionPane dialog;

    private int width = 300;
    private int height = 320;
    private int x = 20;
    private int y = 20;

    public GUIStart(Control pControl) {
        control = pControl;

        setTitle("UNO - Gamescreen");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        basePanel = new JPanel();
        basePanel.setLayout(null);
        cp.add(basePanel, BorderLayout.CENTER);

        jl_anzSpieler = new JLabel("Anzahl Spieler:");
        jl_anzSpieler.setBounds(x,y,100,50);
        basePanel.add(jl_anzSpieler);

        tf_Spieler = new JTextField();
        tf_Spieler.setBounds(160,35,30,30);
        basePanel.add(tf_Spieler);

        jl_anzDeck = new JLabel("Anzahl der Decks:");
        jl_anzDeck.setBounds(x,75,120,50);
        basePanel.add(jl_anzDeck);

        tf_Deck = new JTextField();
        tf_Deck.setBounds(x+140,85,30,30);
        tf_Deck.addActionListener(this);
        basePanel.add(tf_Deck);

        jl_name = new JLabel("Name:");
        jl_name.setBounds(x,130,120,50);
        basePanel.add(jl_name);

        tf_Name = new JTextField();
        tf_Name.setBounds(x+140,130,70,30);
        basePanel.add(tf_Name);

        btn_start = new JButton("Start");
        btn_start.setBounds(40,180,150,40);
        btn_start.addActionListener(this);
        basePanel.add(btn_start);

        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tf_Deck || e.getSource() == btn_start) {
            try{
                control.setAnzSpieler(Integer.parseInt(tf_Spieler.getText()));
                control.setDeck(Integer.parseInt(tf_Deck.getText()));
                control.start();
            }catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(this,"Alle Felder müssen ausgefüllt sein");
            }


        }
    }
}
