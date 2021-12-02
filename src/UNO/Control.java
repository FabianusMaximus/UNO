package UNO;

import UNO.GUI.GUI;
import UNO.GUI.GUIGame;
import UNO.GUI.GUIStart;
import UNO.Kartenlogik.*;
import UNO.util.UserInput;

import java.util.ArrayList;

public class Control {
    private Deck deck;
    private GUI theGUI;
    private GUIGame theGameGUI;
    private GUIStart theStartGUI;
    private Tabletop tabletop;
    private ArrayList<Bot> bot = new ArrayList<>();
    private ArrayList<Spieler> spieler = new ArrayList<>();
    private boolean richtung;
    private int activePlayer;
    private int anzSpieler;
    private String name;
    private int difficulty;
    private Card ausgwCard = null;
    private String gewinner;

    public Control() {
        theGUI = new GUI(this);
        theStartGUI = new GUIStart(this);
        tabletop = new Tabletop();

        //setAnzSpieler(new UserInput().inputNrPlayer());
        //setDeck(new UserInput().inputAnzKarten());

    }

    public void setDeck(int pAnz){
        deck = new Deck(pAnz);
    }

    public void setAnzSpieler(int pAnz){
        anzSpieler = pAnz;
    }

    public int getAnzSpieler(){
       return anzSpieler;
    }

    public void setName(String pName){
        name = pName;
    }

    public void setDifficulty(int pDifficulty) {
        difficulty = pDifficulty;
    }

    public void setActivePlayer(){
        activePlayer = nextPlayer();
    }

    public ArrayList<Card> getCardsOnHand(int index){
        return spieler.get(index).getHand();
    }

    public Card getCardOnTable(){
        return tabletop.getCardOnTable();
    }

    public String getName(int index){
        return spieler.get(index).getName();
    }

    public int getActivePlayer(){
        return activePlayer;
    }

    private void printHand(int pSpieler) {
        theGUI.printHand(spieler.get(activePlayer).getName(), pSpieler, spieler);
    }

    private void printCardsOnTable() {
        theGUI.printTabletop(tabletop.getCardOnTable());
    }

    private void printCardsOtherPlayers() {
        theGUI.printCardsOtherPlayers(spieler);
    }

    public void layDownCard(Card pCard, int pSpieler) {
        tabletop.layCardOnTable(pCard);
        if (pCard.isActionCard()){
            performAction(pCard);
        }
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
        sotierenKarten();
        if (deck.getDeck().isEmpty()) {
            deck = new Deck(1);
            deck.shuffle();
        }
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

    private void farbwechsel(Card pCard) {
        if (activePlayer == 0) {
            layDownCard(new Card(new UserInput().auswahlFarbe(), 69), 0);
        } else {
            layDownCard(new Card(bot.get(activePlayer - 1).auswaehlenFarbe(),
                    69), 0);
        }

    }

    private void ueberpruefenUno() {
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
                activePlayer = nextPlayer();
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
                farbwechsel(pCard);

                break;
            case 14:
                farbwechsel(pCard);
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
                gewinner = spieler.get(i).getName();
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

    public void reactionBot(){
        bot.get(activePlayer-1).reaction();
        if (bot.get(activePlayer - 1).kannSpielen()) {
            layDownCard(ausgwCard, activePlayer);
            if (ausgwCard.isActionCard()) {
                performAction(ausgwCard);
            }
        } else {
            aufnehmenKarte(activePlayer);
        }

        activePlayer = nextPlayer();
    }

    private void gamecycle() {
        richtung = true;

        while (isGameActive()) {
            printCardsOnTable();
            sotierenKarten();
            if (activePlayer == 0) {
                printCardsOtherPlayers();
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
                            if (ausgwCard.isActionCard()) {
                                performAction(ausgwCard);
                            }
                            activePlayer = nextPlayer();
                        }
                    } else {
                        System.out.println("bitte wähle eine der angegebenen Karten");
                    }
                }
            }


        }
        System.out.println("---------Spieler " + gewinner + " hat gewonnen---------");

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
    }

    public void setAusgwCard(Card pCard) {
        ausgwCard = pCard;
    }

    public void start() {
        deck.shuffle();
        austeilen(anzSpieler);
        sotierenKarten();
        theGameGUI = new GUIGame(this);
        //gamecycle();


    }
}
