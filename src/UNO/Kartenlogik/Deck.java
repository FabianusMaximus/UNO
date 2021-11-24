package UNO.Kartenlogik;
import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();

    public Deck(int pGroeße) {
        for (int j = pGroeße; j > 0; j--) {
            //Von jeder Farbe 20 Karten
            for (int n = 0; n < 2; n++) {
                for (int i = 0; i < 10; i++) {
                    deck.add(new Card("Red", i));
                    deck.add(new Card("Green", i));
                    deck.add(new Card("Blue", i));
                    deck.add(new Card("Yellow", i));

                }
            }
            //Von jeder basic Actionskarte 8
            for (int n = 0; n < 2; n++) {
                for (int i = 10; i < 13; i++) {
                    deck.add(new Card("Red", i));
                    deck.add(new Card("Green", i));
                    deck.add(new Card("Blue", i));
                    deck.add(new Card("Yellow", i));

                }
            }
            //von jeder schwarzen Karte 4
            for (int i = 0; i < 4; i++) {
                deck.add(new Card("Black", 13));
                deck.add(new Card("Black", 14));
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
