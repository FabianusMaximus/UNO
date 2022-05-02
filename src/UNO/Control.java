package UNO;

import UNO.GUI.GUIGame;
import UNO.GUI.WinScreen;
import UNO.Kartenlogik.*;
import util.Tracking;

import java.util.ArrayList;

public class Control {
    private Deck deck;
    private GUIGame theGameGUI;
    private GUIGameControl guiGameControl;
    private GUIStartControl guiStartControl;
    private BotMatchControl bmControl;
    private WinScreen theWinScreen;
    private Tabletop tabletop;
    private ArrayList<Spieler> spieler = new ArrayList<>();
    private boolean richtung;
    private int activePlayer;
    private int anzSpieler;
    private String name;
    private int difficulty;
    private Card ausgwCard = null;
    private Spieler gewinner;
    private ArrayList<String> verlauf = new ArrayList<>();

    //TODO wenn der Bot 2 mal dran ist, stuckt er
    public Control() {
        guiStartControl = new GUIStartControl(this, bmControl);
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

    public Spieler getPlayer(int index) {
        return spieler.get(index);
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

    private void layDownCardBM(Card pCard, int pSpieler) {
        tabletop.layCardOnTable(pCard);
        bmControl.appendToVerlauf(spieler.get(pSpieler).getName() + ": " + pCard.getColor() + " " + pCard.getName());
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
            if (!(spieler.get(activePlayer) instanceof Bot)) {
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

    public Spieler getNextPlayer() {
        return spieler.get(nextPlayer());
    }

    private void auswaehlenFarbe() {
        if (!(spieler.get(activePlayer) instanceof Bot) && theGameGUI != null) {
            theGameGUI.auswahlFarbe(true);
        } else if (theGameGUI == null) {
            layDownCard(new Card("black", 69), 0);
        } else {
            layDownCard(new Card(((Bot) (spieler.get(activePlayer))).auswaehlenFarbe(), 69), activePlayer);
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
        ((Bot) spieler.get(activePlayer)).reaction();
        if (((Bot) spieler.get(activePlayer)).kannSpielen()) {
            layDownCard(ausgwCard, activePlayer);
        } else {
            aufnehmenKarte(activePlayer);
        }

        activePlayer = nextPlayer();
    }

    private void reactionBotBM() {
        ((Bot) spieler.get(activePlayer)).reaction();
        if (((Bot) spieler.get(activePlayer)).kannSpielen()) {
            layDownCardBM(ausgwCard, activePlayer);
        } else {
            aufnehmenKarte(activePlayer);
        }
        activePlayer = nextPlayer();
    }

    public boolean isGameActive() {
        for (Spieler value : spieler) {
            if (value.getHand().isEmpty()) {
                gewinner = value;
                return false;
            }
        }
        return true;
    }

    private void erstellenSpieler() {
        spieler.add(new Spieler(name));
        for (int i = 1; i < anzSpieler; i++) {
            spieler.add(new Bot(i, this, difficulty));
        }
    }

    private void erstellenBot(int number, int difficulty) {
        spieler.add(new Bot(number, this, difficulty));

    }

    private void updateConfig(int index, int difficulty) {
        //TODO machen, dass man noch mal simulieren kann und alle Einstellungen anpassen kann
        ((Bot) spieler.get(index)).setDifficulty(difficulty);
    }


    private void austeilen() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < anzSpieler; j++) {
                spieler.get(j).addCardToHand(deck.getDeck().get(i + j));
                deck.getDeck().remove(i + j);
            }
        }
        layDownCard(deck.getDeck().get(0), 0);
        verlauf.remove(0);
    }

    public void goToBotMatch() {
        bmControl = new BotMatchControl(this);
    }

    public void setAusgwCard(Card pCard) {
        ausgwCard = pCard;
    }

    public void setTheGameGUI(GUIGame guiGame) {
        this.theGameGUI = guiGame;
    }

    public void start() {
        deck.shuffle();
        erstellenSpieler();
        austeilen();
        sortierenKarten();
        guiGameControl = new GUIGameControl(this);
    }

    public void startBotMatch() {
        deck = new Deck(1);
        deck.shuffle();
        if (spieler.isEmpty()) {
            for (int i = 0; i < anzSpieler; i++) {
                erstellenBot(i, bmControl.getDifficulty(i) - 1);
            }
        } else {
            for (int i = 0; i < anzSpieler; i++) {
                updateConfig(i, bmControl.getDifficulty(i) - 1);
            }
        }
        austeilen();
        sortierenKarten();
        botGamecycle();
    }

    public void botGamecycle() {
        while (checkHasCards()) {
            reactionBotBM();
        }
        bmControl.appendToVerlauf("----------Spiel Vorbei----------");
        Bot holdBot = (Bot) spieler.get(getIndexWinner());
        bmControl.appendToVerlauf("Bot " + getIndexWinner() + " hat gewonnen!");
        holdBot.erhoehenRundenGewonnen();
        bmControl.updateGewonnen(getIndexWinner(), holdBot.getRundenGewonnen());
    }

    private boolean checkHasCards() {
        for (Spieler spieler : spieler) {
            if (spieler.getHand().isEmpty()) return false;
        }
        return true;
    }

    public int getIndexWinner() {
        for (int i = 0; i < spieler.size(); i++) {
            if (spieler.get(i).getAnzCards() == 0) return i;
        }
        return 14;
    }
}
