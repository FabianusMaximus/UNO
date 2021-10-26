import Kartenlogik.Card;
import Kartenlogik.Deck;
import Kartenlogik.Spieler;

import java.util.ArrayList;

public class Control {
    private Card card;
    private Deck deck;
    private GUI theGUI;
    //private GUIGame theGameGUI;
    //private GUIStart theStartGUI;
    private Tabletop tabletop;
    private ArrayList<Spieler> spieler = new ArrayList<>();
    private boolean richtung;
    private int activePlayer;
    private int anzSpieler;

    public Control() {
        theGUI = new GUI(this);
        //theStartGUI = new GUIStart(this);
        tabletop = new Tabletop();

        anzSpieler = new UserInput().inputNrPlayer();
        deck = new Deck(new UserInput().inputAnzKarten());
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

    private void layDownCard(Card pCard, int pSpieler) {
        tabletop.layCardOnTable(pCard);
        if (tabletop.getCardOnTable() == null) {
            deck.getDeck().remove(pCard);
        } else {
            spieler.get(pSpieler).getHand().remove(pCard);
        }
    }

    private boolean überpruefenCarte(Card pCard) {
        boolean validMove = false;
        if (pCard.getColorValue() == 4) {
            validMove = true;
        } else if (pCard.getValue() == tabletop.getCardOnTable().getValue() ||
                pCard.getColorValue() == tabletop.getCardOnTable().getColorValue() ||
                tabletop.getCardOnTable().getColorValue() == 4) {
            validMove = true;
        } else {
            System.out.println("Diese Karte kann nicht gewählt werden");
        }
        return validMove;
    }

    private void aufnehmenKarte(int pSpieler) {
        spieler.get(pSpieler).addCardToHand(deck.getDeck().get(0));
        deck.getDeck().remove(0);
        if (deck.getDeck().isEmpty()) {
            deck = new Deck(1);
            deck.shuffle();
        }
    }

    private boolean isActionCard(Card pCard) {
        boolean actionCard = false;
        if (pCard.getValue() >= 9) {
            actionCard = true;
        }
        return actionCard;
    }

    private int nextPlayer() {
        int i = activePlayer;
        if (richtung) {
            i++;
        } else {
            i--;
        }
        if (i >= spieler.size() && i > 0) {
            i = 0;
        } else if (i < 0) {
            i = spieler.size() - 1;

        }
        return i;
    }

    private void farbwechsel() {
        layDownCard(new Card(new UserInput().auswahlFarbe(), 69), 0);
    }

    private void performAction(Card pCard) {
        int value = pCard.getValue();

        switch (value) {
            case 10:
                aufnehmenKarte(nextPlayer());
                aufnehmenKarte(nextPlayer());
                break;
            case 11:
                if (!richtung) richtung = true;
                else richtung = false;
                break;
            case 12:
                activePlayer = nextPlayer();
                break;
            case 13:
                farbwechsel();

                break;
            case 14:
                farbwechsel();
                aufnehmenKarte(nextPlayer());
                aufnehmenKarte(nextPlayer());
                aufnehmenKarte(nextPlayer());
                aufnehmenKarte(nextPlayer());
                break;
        }
    }

    private boolean isGameActive() {
        boolean activ = true;
        for (int i = 0; i < spieler.size(); i++) {
            if (spieler.get(i).getHand().isEmpty()) {
                activ = false;
                break;
            }
        }
        return activ;
    }

    private void sotierenKarten(){
        for (int i = 0; i < spieler.size(); i++) {
            spieler.get(i).sortieren();
        }
    }

    private void gamecycle() {
        richtung = true;

        while (isGameActive()) {
            printCardsOnTable();
            sotierenKarten();
            printHand(activePlayer);
            int auswahl = new UserInput().auswahlKarte();
            Card ausgwCard = null;
            if (auswahl == 98) {
                aufnehmenKarte(activePlayer);
                activePlayer = nextPlayer();
            } else if (auswahl < spieler.get(activePlayer).getHand().size()) {
                ausgwCard = spieler.get(activePlayer).getHand().get(auswahl);
                if (überpruefenCarte(ausgwCard)) {
                    layDownCard(ausgwCard, activePlayer);
                    if (isActionCard(ausgwCard)) {
                        performAction(ausgwCard);
                    }
                    activePlayer = nextPlayer();
                }
            } else {
                System.out.println("bitte wähle eine der angegenen Karten");
            }


        }
        System.out.println("---------Spdieler " + activePlayer + " hat gewonnen---------");

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
        layDownCard(deck.getDeck().get(0), 0);
    }

    public void start() {
        deck.shuffle();
        austeilen(anzSpieler);
        gamecycle();

    }
}
