package UNO.GUI;

import UNO.Control;
import UNO.Kartenlogik.Card;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIGame extends JFrame implements ActionListener {
    private Control control;

    private JPanel basePanel, playersPanel, cardsOnHandPanel, cardOnTablePanel, farbauswahlPanel;

    private JButton btn_Stapel, btn_Uno, btn_Next;

    private JButton[] btn_Farben = new JButton[4];

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
        playersPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        basePanel.add(playersPanel);

        jl_CardsOtherPlayer = new JLabel[control.getAnzSpieler() - 1];
        for (int i = 0; i < jl_CardsOtherPlayer.length; i++) {
            jl_CardsOtherPlayer[i] = new JLabel();
            jl_CardsOtherPlayer[i].setText(control.getName(i + 1) + ": " +
                    control.getCardsOnHand(i + 1).size());
            jl_CardsOtherPlayer[i].setBounds(20 + (100), 200, 200, 200);
            playersPanel.add(jl_CardsOtherPlayer[i]);
        }

        btn_Next = new JButton("weiter");
        if (control.getActivePlayer() == 0) {
            btn_Next.setVisible(false);
        }
        btn_Next.addActionListener(this);
        playersPanel.add(btn_Next);

        btn_Uno = new JButton("UNO");
        btn_Uno.setBounds(10, 50, 100, 50);
        btn_Uno.setVisible(false);
        basePanel.add(btn_Uno);

        cardsOnHandPanel = new JPanel();
        cardsOnHandPanel.setBounds(10, 375, 965, 180);
        cardsOnHandPanel.setLayout(grid);
        basePanel.add(cardsOnHandPanel);

        farbauswahlPanel = new JPanel();
        farbauswahlPanel.setBounds(10, 375, 965, 180);
        farbauswahlPanel.setLayout(new GridLayout(1, 4));
        setVisible(false);
        basePanel.add(farbauswahlPanel);

        for (int i = 0; i < btn_Farben.length; i++) {
            btn_Farben[i] = new JButton();
            farbauswahlPanel.add(btn_Farben[i]);
            btn_Farben[i].addActionListener(this);
        }
        btn_Farben[0].setBackground(Color.red);
        btn_Farben[1].setBackground(Color.green);
        btn_Farben[2].setBackground(Color.blue);
        btn_Farben[3].setBackground(Color.yellow);


        designCards();

        setSize(1000, 563);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void designCards() {
        ArrayList<Card> holdCards = control.getCardsOnHand(0);
        updateGrid();
        while (holdCards.size() > btn_Cards.size()) {
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
        this.repaint();
    }

    private void updateGrid() {
        grid.setColumns(control.getCardsOnHand(0).size());
        cardsOnHandPanel.setLayout(grid);
        this.revalidate();
    }

    private void designCOT() {
        cardOnTable.setText(control.getCardOnTable().getName());
        cardOnTable.setForeground(Color.white);
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

    private void updateOtherPlayers() {
        for (int i = 0; i < jl_CardsOtherPlayer.length; i++) {
            jl_CardsOtherPlayer[i].setText(control.getName(i + 1) + ": " +
                    control.getCardsOnHand(i + 1).size());
        }
        btn_Next.setVisible(control.getActivePlayer() != 0);
        this.repaint();
    }

    public void auswahlFarbe(boolean auswahl) {
        if (auswahl) {
            cardsOnHandPanel.setVisible(false);
            farbauswahlPanel.setVisible(true);
        } else {
            cardsOnHandPanel.setVisible(true);
            farbauswahlPanel.setVisible(false);
        }
    }

    private void updateGui(){
        designCOT();
        updateGrid();
        designCards();
        updateOtherPlayers();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int ausgCardValue = 0;
        boolean zug = false;
        if (control.getActivePlayer() == 0) {
            for (int i = 0; i < btn_Cards.size(); i++) {
                if (e.getSource().equals(btn_Cards.get(i))) {
                    if (control.ueberpruefenKarte(control.getCardsOnHand(0).get(i))) {
                        ausgCardValue = control.getCardsOnHand(0).get(i).getColorValue();
                        control.layDownCard(control.getCardsOnHand(0).get(i), 0);
                        cardsOnHandPanel.remove(btn_Cards.get(i));
                        btn_Cards.remove(i);
                        zug = true;
                        break;
                    }
                }
            }
            for (int i = 0; i < btn_Farben.length; i++) {
                if (e.getSource() == btn_Farben[i]) {
                    String farbe;
                    switch (i) {
                        case 0:
                            farbe = "Red";
                            break;
                        case 1:
                            farbe = "Green";
                            break;
                        case 2:
                            farbe = "Blue";
                            break;
                        case 3:
                            farbe = "Yellow";
                            break;
                        default:
                            farbe = "undefiniert";
                            break;
                    }
                    ausgCardValue = 0;
                    control.layDownCard(new Card(farbe, 69), 0);
                    zug = true;
                    control.setActivePlayer();
                    break;
                }
            }
            if (e.getSource() == btn_Stapel) {
                control.aufnehmenKarte(0);
                ausgCardValue = 0;
            }
            if (ausgCardValue != 4 && zug) {
                control.setActivePlayer();
            }
            zug = true;

            updateGui();

        } else if (e.getSource() == btn_Next) {
            control.reactionBot();
            updateGui();
        } else {
            //JOptionPane.showMessageDialog(this, "DU bist nicht an der Reihe");
        }


    }
}


