package UNO.GUI;

import UNO.Control;
import UNO.Kartenlogik.Card;
import UNO.Kartenlogik.Tabletop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIGame extends JFrame implements ActionListener {
    private Control control;

    private JPanel basePanel;

    private JPanel playersPanel;
    private JPanel cardsOnHandPanel;

    private JButton btn_Stapel;
    private JPanel cardOnTablePanel;
    private JLabel cardOnTable;
    private ArrayList<JButton> btn_Cards = new ArrayList<>();
    private JLabel[] jl_CardsOtherPlayer;

    private GridLayout grid = new GridLayout(1, 7, 0, 5);

    public GUIGame(Control pControl) {
        control = pControl;
        jl_CardsOtherPlayer = new JLabel[control.getAnzSpieler()];
        setTitle("UNO - Gamescreen");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        basePanel = new JPanel();
        basePanel.setLayout(null);
        //basePanel.setBackground(Color.green);
        cp.add(basePanel, BorderLayout.CENTER);

        btn_Stapel = new JButton("Aufn. Stapel");
        btn_Stapel.setBounds(390, 20, 100, 150);
        btn_Stapel.addActionListener(this);
        basePanel.add(btn_Stapel);

        cardOnTablePanel = new JPanel();
        cardOnTablePanel.setBounds(500, 20, 100, 150);
        cardOnTablePanel.setLayout(new BorderLayout());
        basePanel.add(cardOnTablePanel);

        cardOnTable = new JLabel(control.getCardOnTable().getName(), SwingConstants.CENTER);
        cardOnTable.setForeground(Color.white);
        cardOnTablePanel.add(cardOnTable, BorderLayout.CENTER);

        designCOT();


        playersPanel = new JPanel();
        playersPanel.setBounds(10, 187, 965, 180);
        basePanel.add(playersPanel);

        cardsOnHandPanel = new JPanel();
        cardsOnHandPanel.setBounds(10, 375, 965, 180);
        cardsOnHandPanel.setLayout(grid);
        basePanel.add(cardsOnHandPanel);

        designCards();

        setSize(1000, 563);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void designCards() {
        ArrayList<Card> holdCards = control.getCardsOnHand();
        updateGrid();
        for (int i = 0; i < holdCards.size(); i++) {
            btn_Cards.add(new JButton());
        }
        for (int i = 0; i < btn_Cards.size(); i++) {
            btn_Cards.get(i).setText(holdCards.get(i).getName());
            btn_Cards.get(i).setForeground(Color.white);
            btn_Cards.get(i).addActionListener(this);
            switch (holdCards.get(i).getColorValue()) {
                case 0 -> btn_Cards.get(i).setBackground(Color.red);
                case 1 -> btn_Cards.get(i).setBackground(Color.green);
                case 2 -> btn_Cards.get(i).setBackground(Color.blue);
                case 3 -> {
                    btn_Cards.get(i).setBackground(Color.yellow);
                    btn_Cards.get(i).setForeground(Color.black);
                }
                case 4 -> btn_Cards.get(i).setBackground(Color.black);
            }
            cardsOnHandPanel.add(btn_Cards.get(i));

        }
    }

    private void updateGrid() {
        grid.setColumns(control.getCardsOnHand().size());
        this.revalidate();
    }

    private void designCOT() {
        cardOnTable.setText(control.getCardOnTable().getName());
        switch (control.getCardOnTable().getColorValue()) {
            case 0 -> cardOnTablePanel.setBackground(Color.red);
            case 1 -> cardOnTablePanel.setBackground(Color.green);
            case 2 -> cardOnTablePanel.setBackground(Color.blue);
            case 3 -> {
                cardOnTablePanel.setBackground(Color.yellow);
                cardOnTable.setForeground(Color.black);
            }
            case 4 -> cardOnTablePanel.setBackground(Color.black);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < btn_Cards.size(); i++) {
            if (e.getSource().equals(btn_Cards.get(i))) {
                if (control.überpruefenCarte(control.getCardsOnHand().get(i))) {
                    control.layDownCard(control.getCardsOnHand().get(i), 0);
                    cardsOnHandPanel.remove(btn_Cards.get(i));
                    btn_Cards.remove(i);
                    designCOT();
                    updateGrid();
                    this.repaint();
                }
            }
        }

    }
}
