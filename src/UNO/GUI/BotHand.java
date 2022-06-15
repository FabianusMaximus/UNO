package UNO.GUI;

import UNO.Control.Control;
import UNO.Kartenlogik.Bot;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BotHand extends JFrame {
    private Control control;

    private Bot linkedBot;
    private GridLayout gridLayout;

    private ArrayList<JPanel> cards = new ArrayList<>();

    public BotHand(Control control, int index) {
        this.control = control;
        try {
            linkedBot = (Bot) control.getSpieler(index + 1);
            this.gridLayout = new GridLayout(1, linkedBot.getAnzCards(), 2, 5);

            updateAnzPanel();
            designCards();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Dieser Spieler ist nicht verbunden");
        }


        setTitle("Hand von Bot " + (index + 1));
        setSize(1000, 180);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(gridLayout);
        setVisible(true);
    }

    public void updateGrid(int anzCards) {
        this.gridLayout.setColumns(anzCards);
    }

    private void soutSize() {
        System.out.println(this.getWidth() + " " + this.getHeight());
    }

    private void updateAnzPanel() {
        while (cards.size() != linkedBot.getAnzCards()) {
            if (cards.size() < linkedBot.getAnzCards()) cards.add(new JPanel());
            else if (cards.size() > linkedBot.getAnzCards()) cards.remove(0);
        }
    }

    private void designCards() {
        for (int i = 0; i < cards.size(); i++) {
            Color c = linkedBot.getHand().get(i).getColorObjekt();
            cards.get(i).setBackground(c);
            cards.get(i).setLayout(new BorderLayout());

            JLabel jl = new JLabel(linkedBot.getHand().get(i).getName());
            if (c != Color.yellow) jl.setForeground(Color.white);
            jl.setHorizontalAlignment(SwingConstants.CENTER);
            jl.setVerticalAlignment(SwingConstants.CENTER);

            cards.get(i).add(jl);
            this.add(cards.get(i));
        }
    }

    public void updateBotHand() {
        updateGrid(linkedBot.getAnzCards());
        designCards();
        this.revalidate();
        this.repaint();
    }
}
