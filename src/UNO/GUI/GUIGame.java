package UNO.GUI;

import UNO.Control;
import UNO.Kartenlogik.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUIGame extends JFrame {
    Control control;

    private JPanel basePanel;

    private JPanel playersPanel;
    private JPanel cardsOnhandPanel;

    private JButton btn_Stapel;
    private ArrayList<JButton> btn_Cards = new ArrayList<>();
    private JLabel[] jl_CardsOtherPlayer;

    public GUIGame(Control pControl) {
        control = pControl;
        jl_CardsOtherPlayer = new JLabel[control.getAnzSpieler()];
        setTitle("UNO - Gamescreen");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        basePanel = new JPanel();
        basePanel.setLayout(null);
        basePanel.setBackground(Color.green);
        cp.add(basePanel, BorderLayout.CENTER);

        playersPanel = new JPanel();
        playersPanel.setBounds(10,187,965,180);
        basePanel.add(playersPanel);

        cardsOnhandPanel = new JPanel();
        cardsOnhandPanel.setBounds(10, 375,965,180);
        cardsOnhandPanel.setLayout(new BorderLayout(0,5));
        basePanel.add(cardsOnhandPanel);

        designCards();

        setSize(1000, 563);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void designCards(){
        ArrayList<Card> holdCards = control.getCardsOnHand();
        for (int i = 0; i <holdCards.size() ; i++) {
            btn_Cards.add(new JButton());
        }


    }

}
