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
    private ArrayList<Bot> bot = new ArrayList<>();
    private ArrayList<Spieler> spieler = new ArrayList<>();
    private boolean richtung;
    private int activePlayer;
    private int anzSpieler;
    Card ausgwCard = null;

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
        theGUI.printHand(spieler.get(activePlayer).getName(), pSpieler, spieler);
    }

    private void printCardsOnTable() {
        theGUI.printTabletop(tabletop.getCardOnTable());
    }

    public void layDownCard(Card pCard, int pSpieler) {
        tabletop.layCardOnTable(pCard);
        if (tabletop.getCardOnTable() == null) {
            deck.getDeck().remove(pCard);
        } else {
            spieler.get(pSpieler).getHand().remove(pCard);
        }
    }

    public boolean überpruefenCarte(Card pCard) {
        boolean validMove = false;
        if (pCard.getColorValue() == 4) {
            validMove = true;
        } else if (pCard.getValue() == tabletop.getCardOnTable().getValue() ||
                pCard.getColorValue() == tabletop.getCardOnTable().getColorValue() ||
                tabletop.getCardOnTable().getColorValue() == 4) {
            validMove = true;
        } else {
            if (activePlayer == 0) {
                System.out.println("Diese Karte kann nicht gewählt werden");
            }

        }
        return validMove;
    }

    public void aufnehmenKarte(int pSpieler) {
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
        if (activePlayer == 0){
            layDownCard(new Card(new UserInput().auswahlFarbe(), 69), 0);
        }else{
            layDownCard(new Card (bot.get(activePlayer-1).auswaehlenFarbe(),69),0);
        }

    }

    private void ueberpruefenUno(){
        spieler.get(activePlayer).setUno(new UserInput().eingabeUno());
        if (!spieler.get(activePlayer).hatUnoGesagt()) {
            System.out.println("du hast vergessen uno zu sagen. Strafe: +4 Karten");
            aufnehmenKarte(activePlayer);
            aufnehmenKarte(activePlayer);
            aufnehmenKarte(activePlayer);
            aufnehmenKarte(activePlayer);
            activePlayer = nextPlayer();
        }
    }

    private void performAction(Card pCard) {
        int value = pCard.getValue();

        switch (value) {
            case 10:
                aufnehmenKarte(nextPlayer());
                aufnehmenKarte(nextPlayer());
                break;
            case 11:
                if (anzSpieler == 2) {
                    nextPlayer();
                } else {
                    if (!richtung) richtung = true;
                    else richtung = false;
                    break;
                }

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

    private void sotierenKarten() {
        for (int i = 0; i < spieler.size(); i++) {
            spieler.get(i).sortieren();
        }
    }

    private void gamecycle() {
        richtung = true;

        while (isGameActive()) {
            printCardsOnTable();
            sotierenKarten();
            if (activePlayer == 0) {
                printHand(activePlayer);

                if (spieler.get(activePlayer).getHand().size() == 1 && !spieler.get(activePlayer).hatUnoGesagt()) {
                    ueberpruefenUno();
                } else {
                    int auswahl = new UserInput().auswahlKarte();
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
                        System.out.println("bitte wähle eine der angegebenen Karten");
                    }
                }
            } else {
                bot.get(activePlayer - 1).reaction();
                if (ausgwCard != null){
                    layDownCard(ausgwCard,activePlayer);
                    if (isActionCard(ausgwCard)){
                        performAction(ausgwCard);
                    }
                }else{
                    aufnehmenKarte(activePlayer);
                }

                activePlayer = nextPlayer();
            }


        }
        System.out.println("---------Spieler " + activePlayer + " hat gewonnen---------");

    }

    private void austeilen(int pAnzSpieler) {
        spieler.add(new Spieler(new UserInput().auswahlName()));
        for (int i = 1; i < pAnzSpieler; i++) {
            spieler.add(new Spieler(i));
            bot.add(new Bot(spieler.get(i).getName(), spieler.get(i), this));
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < pAnzSpieler; j++) {
                spieler.get(j).addCardToHand(deck.getDeck().get(i + j));
                deck.getDeck().remove(i + j);
            }
        }
        layDownCard(deck.getDeck().get(0), 0);
    }

    public void setAusgwCard(Card pCard){
        ausgwCard = pCard;
    }

    public void start() {
        deck.shuffle();
        austeilen(anzSpieler);
        gamecycle();

    }
}
