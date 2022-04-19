package UNO.Components;

import UNO.Kartenlogik.Card;

import javax.swing.*;
import java.awt.*;

public class CardButton extends JButton {
    private Card linkedCard;

    public CardButton() {

    }

    public CardButton(Card card) {
        this.linkedCard = card;

        this.setText(linkedCard.getName());
        this.setForeground(Color.white);

        switch (linkedCard.getColorValue()) {
            case 0 -> setBackground(Color.red);
            case 1 -> setBackground(Color.green);
            case 2 -> setBackground(Color.blue);
            case 3 -> {
                setBackground(Color.yellow);
                setForeground(Color.black);
            }
            case 4 -> setBackground(Color.black);
        }
    }

    public Card getLinkedCard() {
        return linkedCard;
    }

    public void setLinkedCard(Card linkedCard) {
        this.linkedCard = linkedCard;
    }

    public void designButton() {
        this.setText(linkedCard.getName());
        this.setForeground(Color.white);

        switch (linkedCard.getColorValue()) {
            case 0 -> setBackground(Color.red);
            case 1 -> setBackground(Color.green);
            case 2 -> setBackground(Color.blue);
            case 3 -> {
                setBackground(Color.yellow);
                setForeground(Color.black);
            }
            case 4 -> setBackground(Color.black);
        }
    }
}
