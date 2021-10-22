import java.util.ArrayList;

public class Control {
    private Card card;
    private Deck deck;
    private GUI theGUI;
    private Tabletop tabletop;
    private UserInput userInput;
    private ArrayList<Spieler> spieler = new ArrayList<>();

    private int anzSpieler;

    public Control(){
        theGUI = new GUI(this);
        deck = new Deck(2);
        tabletop = new Tabletop();
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
        anzSpieler = new UserInput().inputNrPlayer();
        /**
         *
         */
        printDeck();
        deck.shuffle();
        printDeck();
        austeilen(anzSpieler);
        printHand(0);
        printHand(1);
        spieler.get(1).sortieren();
        printHand(1);
    }
}
