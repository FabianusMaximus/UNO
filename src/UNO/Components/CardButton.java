package UNO.Components;

import UNO.Kartenlogik.Card;

import javax.swing.*;
import java.awt.*;

public class CardButton extends JButton {
    private Card linkedCard;


    public CardButton(Card card) {
        this.linkedCard = card;

        this.setText(linkedCard.getName());
        this.setForeground(Color.white);
    }

    public Card getLinkedCard() {
        return linkedCard;
    }

    public void setLinkedCard(Card linkedCard) {
        this.linkedCard = linkedCard;
    }
}
