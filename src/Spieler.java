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
        int n = hand.size();
        Card temp = null;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (hand.get(j - 1).getValue() > hand.get(j).getValue()) {
                    //swap elements
                    temp = hand.get(j - 1);
                    hand.remove(j-1);
                    hand.add(j - 1, hand.get(j));
                    hand.add(j, temp);

                }
            }
        }

    }

}
