import java.util.ArrayList;

public class Control {
    private Card card;
    private Deck deck;
    private GUI theGUI;
    private Tabletop tabletop;
    private ArrayList<Spieler> spieler = new ArrayList<>();
    private boolean richtung;
    private boolean gameActive;

    private int anzSpieler;

    public Control() {
        anzSpieler = new UserInput().inputNrPlayer();
        theGUI = new GUI(this);
        deck = new Deck(new UserInput().inputAnzKarten());
        tabletop = new Tabletop();
    }

    private void printDeck() {
        theGUI.printDeck(deck.getDeck());
    }

    private void printHand(int pSpieler) {
        theGUI.printHand(pSpieler, spieler);
    }

    private void printCardsOnTable() {
        theGUI.printTabletop(tabletop.getCardOnTable());
    }

    private void layDownCard(Card pCard) {
        tabletop.layCardOnTable(pCard);
        deck.getDeck().remove(pCard);
    }

    private boolean überpruefenCarte(Card pCard) {
        boolean validMove = false;
        if (pCard.getColorValue() == 4) {
            validMove = true;
        } else if (pCard.getValue() == tabletop.getCardOnTable().getValue() ||
                pCard.getColorValue() == tabletop.getCardOnTable().getColorValue()) {
            validMove = true;
        }else {
            System.out.println("Diese Karte kann nicht gewählt werden");
        }
        return validMove;
    }

    private void gamecycle() {
        int activePlayer = 0;
        richtung = true;
        gameActive = true;

        while (gameActive) {
            printCardsOnTable();
            for (int i = 0; i < spieler.size(); i++) {
                spieler.get(i).sortieren();
                printHand(i);
            }
            Card ausgwCard = spieler.get(activePlayer).getHand().get(new UserInput().auswahlKarte());
            if (überpruefenCarte(ausgwCard)){
                layDownCard(ausgwCard);
            }
            if (activePlayer >= spieler.size()) {
                activePlayer = 0;
            } else {
                if (richtung) {
                    activePlayer++;
                } else {
                    activePlayer--;
                }
            }

        }

    }

    private void austeilen(int pAnzSpieler) {
        for (int i = 0; i < pAnzSpieler; i++) {
            spieler.add(new Spieler());
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < pAnzSpieler; j++) {
                spieler.get(j).addCardToHand(deck.getDeck().get(i + j));
                deck.getDeck().remove(i + j);
            }
        }
        layDownCard(deck.getDeck().get(0));
    }

    public void start() {
        deck.shuffle();
        austeilen(anzSpieler);
        gamecycle();

    }
}
