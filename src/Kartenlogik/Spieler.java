package Kartenlogik;
import java.util.ArrayList;

public class Spieler {
    private ArrayList<Card> hand = new ArrayList<>();

    public Spieler() {

    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addCardToHand(Card pCard) {
        hand.add(pCard);
    }

    public Card selectCard(int pNr) {
        return hand.get(pNr);
    }


    public void sortieren() {
        Card temp;
        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < hand.size() - 1; i++) {
                if (hand.get(i).compareValueTo(hand.get(i + 1)) > 0) {
                    temp = hand.get(i);
                    hand.set(i, hand.get(i + 1));
                    hand.set(i + 1, temp);
                    sorted = false;
                }
            }
        }
        sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < hand.size() - 1; i++) {
                if (hand.get(i).compareColorTo(hand.get(i + 1)) > 0) {
                    temp = hand.get(i);
                    hand.set(i, hand.get(i + 1));
                    hand.set(i + 1, temp);
                    sorted = false;
                }
            }
        }

    }

}
