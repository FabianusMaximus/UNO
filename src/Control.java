import java.util.ArrayList;

public class Control {
    private Card card;
    private Deck deck;
    private GUI theGUI;
    private ArrayList<Spieler> spieler = new ArrayList<>();

    private int anzSpieler;

    public Control(){
        theGUI = new GUI(this);
        deck = new Deck(2);
    }

    private void printDeck(){
        theGUI.printDeck(deck.getDeck());
    }

    private void printHand(int pSpieler){
        theGUI.printHand(pSpieler,spieler);
    }

    private void austeilen(int pAnzSpieler){
        for (int i = 0; i < pAnzSpieler; i++) {
            spieler.add(new Spieler());
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < pAnzSpieler; j++) {
                spieler.get(j).addCardToHand(deck.getDeck().get(i + j));
                deck.getDeck().remove(i + j);
            }
        }

    }

    public void start(){
        anzSpieler = 2;
        printDeck();
        deck.shuffle();
        printDeck();
        austeilen(anzSpieler);
        printHand(0);
        printHand(1);
    }
}
