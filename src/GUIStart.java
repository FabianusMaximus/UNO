import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIStart extends JFrame implements ActionListener {
    private Control control;
    private JLabel jl_anzSpieler;
    private JLabel jl_anzDeck;
    private JTextField tf_Spieler;
    private JTextField tf_Deck;
    private JButton btn_start;
    public GUIStart(Control pControl){
        control = pControl;

        setTitle("UNO - Gamescreen");
        Container cp = getContentPane();
        cp.setLayout(null);

        jl_anzSpieler = new JLabel("Anzahl Spieler:");
        jl_anzSpieler.setBounds(40,20,100,50);
        cp.add(jl_anzSpieler);

        tf_Spieler = new JTextField();
        tf_Spieler.setBounds(160,30,30,30);
        cp.add(tf_Spieler);

        jl_anzDeck = new JLabel("Anzahl der Decks:");
        jl_anzDeck.setBounds(40,80,120,50);
        cp.add(jl_anzDeck);

        tf_Deck = new JTextField();
        tf_Deck.setBounds(160,90,30,30);
        tf_Deck.addActionListener(this);
        cp.add(tf_Deck);

        btn_start = new JButton("Start");
        btn_start.setBounds(40,130,150,40);
        btn_start.addActionListener(this);
        cp.add(btn_start);

        setSize(250, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tf_Deck){
            control.start();
        }else if (e.getSource() == btn_start){
            control.start();
        }
    }
}
