import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();

    public Deck(int pGroeße) {
        for (int j = pGroeße; j > 0; j--) {
            for (int i = 0; i < 10; i++) {
                deck.add(new Card("Blue", i));
                deck.add(new Card("Green", i));
                deck.add(new Card("Yellow", i));
                deck.add(new Card("Red", i));
            }
            for (int i = 0; i < 4; i++) {
                deck.add(new Card("Black", 10));
                deck.add(new Card("Black", 11));
            }
        }
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void shuffle() {
        ArrayList<Card> shuffledDeck = new ArrayList<>();
        while (deck.size() != 0) {
            int randy = ((int) (Math.random() * deck.size()));
            shuffledDeck.add(deck.get(randy));
            deck.remove(randy);
        }
        deck.addAll(shuffledDeck);
    }
}
