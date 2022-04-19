package UNO;

import UNO.GUI.GUIGame;
import UNO.GUI.WinScreen;
import UNO.Kartenlogik.*;

import java.util.ArrayList;

public class Control {
    private Deck deck;
    private GUIGame theGameGUI;
    private GUIGameControl guiGameControl;
    private GUIStartControl guiStartControl;
    private WinScreen theWinScreen;
    private Tabletop tabletop;
    private ArrayList<Bot> bot = new ArrayList<>();
    private ArrayList<Spieler> spieler = new ArrayList<>();
    private boolean richtung;
    private int activePlayer;
    private int anzSpieler;
    private String name;
    private int difficulty;
    private Card ausgwCard = null;
    private Spieler gewinner;
    private ArrayList<String> verlauf = new ArrayList<>();

    public Control() {
        guiStartControl = new GUIStartControl(this);
        tabletop = new Tabletop();
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setAnzSpieler(int pAnz) {
        anzSpieler = pAnz;
    }

    public int getAnzSpieler() {
        return anzSpieler;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setDifficulty(int pDifficulty) {
        difficulty = pDifficulty;
    }

    /**
     * Setzt das Attribut der Klasse control auf den nächsten Spieler, damit der nächste Zug gemacht werden kann
     */
    public void continueToNextPlayer() {
        activePlayer = nextPlayer();
    }

    public ArrayList<Card> getCardsOnHand(int index) {
        return spieler.get(index).getHand();
    }

    public Card getCardOnTable() {
        return tabletop.getCardOnTable();
    }

    public String getName(int index) {
        return spieler.get(index).getName();
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public Spieler getGewinner() {
        return gewinner;
    }

    public void layDownCard(Card pCard, int pSpieler) {
        tabletop.layCardOnTable(pCard);
        addToVerlauf(pCard, pSpieler);
        if (pCard.isActionCard()) {
            performAction(pCard);
        }
        if (tabletop.getCardOnTable() == null) {
            deck.getDeck().remove(pCard);
        } else {
            spieler.get(pSpieler).getHand().remove(pCard);
        }
    }

    public void addToVerlauf(Card pCard, int pSpieler) {
        verlauf.add(spieler.get(pSpieler).getName() + ": " + pCard.getName() + "\n");
        if (verlauf.size() > 5) {
            verlauf.remove(0);
        }
    }

    public ArrayList<String> getVerlauf() {
        return verlauf;
    }

    public boolean ueberpruefenKarte(Card pCard) {
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
        sortierenKarten();
        if (deck.getDeck().isEmpty()) {
            deck = new Deck(1);
            deck.shuffle();
        }
    }

    private int nextPlayer() {
        int i = activePlayer;
        if (richtung) i++;
        else i--;
        if (i >= spieler.size() && i > 0) i = 0;
        else if (i < 0) i = spieler.size() - 1;

        return i;
    }

    private void auswaehlenFarbe() {
        if (activePlayer == 0 && theGameGUI != null) {
            theGameGUI.auswahlFarbe(true);
        } else if (theGameGUI == null) {
            layDownCard(new Card("black", 69), 0);
        } else {
            layDownCard(new Card(bot.get(activePlayer - 1).auswaehlenFarbe(), 69), activePlayer);
        }
    }


    private void performAction(Card pCard) {
        int value = pCard.getValue();

        switch (value) {
            case 10:
                aufnehmenKarte(nextPlayer());
                aufnehmenKarte(nextPlayer());
                activePlayer = nextPlayer();
                break;
            case 11:
                if (anzSpieler == 2) {
                    activePlayer = nextPlayer();
                } else {
                    richtung = !richtung;
                }
                break;

            case 12:
                activePlayer = nextPlayer();
                break;

            case 13:
                auswaehlenFarbe();
                break;
            case 14:
                auswaehlenFarbe();
                aufnehmenKarte(nextPlayer());
                aufnehmenKarte(nextPlayer());
                aufnehmenKarte(nextPlayer());
                aufnehmenKarte(nextPlayer());
                break;
        }
    }

    private void sortierenKarten() {
        for (Spieler value : spieler) {
            value.sortieren();
        }
    }

    public void reactionBot() {
        bot.get(activePlayer - 1).reaction();
        if (bot.get(activePlayer - 1).kannSpielen()) {
            layDownCard(ausgwCard, activePlayer);
        } else {
            aufnehmenKarte(activePlayer);
        }

        activePlayer = nextPlayer();
    }

    public boolean isGameActive() {
        boolean active = true;
        for (int i = 0; i < spieler.size(); i++) {
            if (spieler.get(i).getHand().isEmpty()) {
                gewinner = spieler.get(i);
                active = false;
                break;
            }
        }
        return active;
    }


    private void austeilen(int pAnzSpieler) {
        spieler.add(new Spieler(name));
        for (int i = 1; i < pAnzSpieler; i++) {
            spieler.add(new Spieler(i));
            bot.add(new Bot(spieler.get(i).getName(), spieler.get(i), this, difficulty));
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < pAnzSpieler; j++) {
                spieler.get(j).addCardToHand(deck.getDeck().get(i + j));
                deck.getDeck().remove(i + j);
            }
        }
        layDownCard(deck.getDeck().get(0), 0);
        verlauf.remove(0);
    }

    public void setAusgwCard(Card pCard) {
        ausgwCard = pCard;
    }

    public void setTheGameGUI(GUIGame guiGame) {
        this.theGameGUI = guiGame;
    }

    public void setWinScreen() {
        if (theWinScreen == null) {
            theWinScreen = new WinScreen(this);
        }
    }

    public void start() {
        deck.shuffle();
        austeilen(anzSpieler);
        sortierenKarten();
        guiGameControl = new GUIGameControl(this);

        //gamecycle();


    }
}
