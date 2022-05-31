package UNO.GUI;

import UNO.Components.CardButton;
import UNO.GUIGameControl;
import util.SwingCalculation;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIGame extends JFrame {
    ColorSelectionScreen css;
    private GUIGameControl guiGameControl;

    private JPanel basePanel, playersPanel, otherPlayerPanel, cardsOnHandPanel, cardOnTablePanel;

    private JButton btn_Stapel, btn_Uno, btn_Next;


    private JLabel cardOnTable;

    private ArrayList<CardButton> btn_Cards = new ArrayList<>();

    private JLabel[] jl_CardsOtherPlayer;

    private JTextArea verlauf;

    private GridLayout grid = new GridLayout(1, 7, 0, 5);

    private int ausgCardValue = 0;

    public GUIGame(GUIGameControl pControl) {
        guiGameControl = pControl;
        jl_CardsOtherPlayer = new JLabel[guiGameControl.getAnzSpieler()];
        setTitle("UNO - Gamescreen");
        setSize(1000, 563);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        basePanel = new JPanel();
        basePanel.setLayout(null);
        cp.add(basePanel, BorderLayout.CENTER);

        btn_Stapel = new JButton("Aufn. Stapel");
        btn_Stapel.setBounds(390, 20, 100, 150);
        btn_Stapel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiGameControl.clickStapel();
            }
        });
        basePanel.add(btn_Stapel);

        cardOnTablePanel = new JPanel();
        cardOnTablePanel.setBounds(500, 20, 100, 150);
        cardOnTablePanel.setLayout(new BorderLayout());
        basePanel.add(cardOnTablePanel);

        cardOnTable = new JLabel(guiGameControl.getCardOnTable().getName(), SwingConstants.CENTER);
        cardOnTable.setForeground(Color.white);
        cardOnTablePanel.add(cardOnTable, BorderLayout.CENTER);

        designCOT();

        verlauf = new JTextArea();
        verlauf.setText("--------------------Verlauf--------------------" + "\n");
        verlauf.setBounds(620, 20, 200, 150);
        verlauf.setEditable(false);
        basePanel.add(verlauf);

        playersPanel = new JPanel();
        playersPanel.setBounds(10, 187, 965, 180);
        playersPanel.setLayout(new GridLayout(1, 2));
        basePanel.add(playersPanel);

        otherPlayerPanel = new JPanel();
        otherPlayerPanel.setLayout(new GridLayout(guiGameControl.getAnzSpieler() - 1, 1));
        playersPanel.add(otherPlayerPanel);

        jl_CardsOtherPlayer = new JLabel[guiGameControl.getAnzSpieler() - 1];
        for (int i = 0; i < jl_CardsOtherPlayer.length; i++) {
            jl_CardsOtherPlayer[i] = new JLabel();
            jl_CardsOtherPlayer[i].setText(guiGameControl.getName(i + 1) + ": " +
                    guiGameControl.getCardsOnHand(i + 1).size());
            jl_CardsOtherPlayer[i].setFont(new Font("Arial", Font.PLAIN, 20));
            jl_CardsOtherPlayer[i].setVerticalAlignment(SwingConstants.CENTER);
            jl_CardsOtherPlayer[i].setHorizontalAlignment(SwingConstants.CENTER);
            otherPlayerPanel.add(jl_CardsOtherPlayer[i]);
        }

        btn_Next = new JButton("weiter");
        btn_Next.setFont(new Font("Arial", Font.PLAIN, 20));
        if (guiGameControl.getActivePlayer() == 0) {
            btn_Next.setVisible(false);
        }
        btn_Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiGameControl.clickNext();
            }
        });
        playersPanel.add(btn_Next);

        btn_Uno = new JButton("UNO");
        btn_Uno.setBounds(10, 50, 100, 50);
        btn_Uno.setVisible(false);
        basePanel.add(btn_Uno);

        cardsOnHandPanel = new JPanel();
        cardsOnHandPanel.setBounds(10, 375, 965, 180);
        cardsOnHandPanel.setLayout(grid);
        basePanel.add(cardsOnHandPanel);


        designCards();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void designCards() {
        while (guiGameControl.getCardsOnHand(0).size() > btn_Cards.size()) {
            btn_Cards.add(new CardButton());
        }
        for (int i = 0; i < guiGameControl.getCardsOnHand(0).size(); i++) {
            btn_Cards.get(i).setLinkedCard(guiGameControl.getCardsOnHand(0).get(i));
            btn_Cards.get(i).designButton();
            System.out.print(btn_Cards.get(i).getLinkedCard().getName() + ", ");
            reassignActionListener(i);
            cardsOnHandPanel.add(btn_Cards.get(i));
        }
        System.out.println();
        this.repaint();
    }

    public void reassignActionListener(int index) {
        for (ActionListener a : btn_Cards.get(index).getActionListeners()) {
            btn_Cards.get(index).removeActionListener(a);
        }
        btn_Cards.get(index).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiGameControl.clickCard(btn_Cards.get(index).getLinkedCard(), index);
            }
        });

    }

    private void updateGrid() {
        grid.setColumns(guiGameControl.getCardsOnHand(0).size());
        cardsOnHandPanel.setLayout(grid);
        this.revalidate();
    }

    private void designCOT() {
        cardOnTable.setText(guiGameControl.getCardOnTable().getName());
        cardOnTable.setForeground(Color.white);
        switch (guiGameControl.getCardOnTable().getColorValue()) {
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
            jl_CardsOtherPlayer[i].setText(guiGameControl.getName(i + 1) + ": " +
                    guiGameControl.getCardsOnHand(i + 1).size());
        }
        btn_Next.setVisible(guiGameControl.getActivePlayer() != 0);
        this.repaint();
    }

    public void auswahlFarbe(boolean auswahl) {
        btn_Next.setEnabled(false);
        if (auswahl) openColorFrame();
        else closeColorFrame();
        css.toFront();
    }

    private void openColorFrame() {
        css = new ColorSelectionScreen(guiGameControl);
    }

    private void closeColorFrame(){
        css.dispose();
        btn_Next.setEnabled(true);
    }

    public void updateGui() {
        setVerlauf();
        designCOT();
        updateGrid();
        designCards();
        updateOtherPlayers();
        this.repaint();
    }

    private boolean isGameActive() {
        return guiGameControl.isGameActive();
    }

    private void setVerlauf() {
        StringBuilder hold = new StringBuilder("--------------------Verlauf--------------------" + "\n");
        for (int i = 0; i < guiGameControl.getVerlauf().size(); i++) {
            hold.append(guiGameControl.getVerlauf().get(i));
        }
        verlauf.setText(hold.toString());

    }

    public void setAusgwCardValue(int value) {
        this.ausgCardValue = value;
    }

    public JPanel getCardsOnHand() {
        return cardsOnHandPanel;
    }

    public ArrayList<CardButton> getBtn_Cards() {
        return btn_Cards;
    }

    public void showErrorScreen(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


}


