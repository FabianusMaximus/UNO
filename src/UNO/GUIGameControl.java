package UNO;

import UNO.GUI.GUIGame;
import UNO.Kartenlogik.Card;

import java.util.ArrayList;

public class GUIGameControl {
    private Control control;
    private GUIGame guiGame;

    public GUIGameControl(Control control) {
        this.control = control;
        this.guiGame = new GUIGame(this);
    }

    public int getAnzSpieler() {
        return control.getAnzSpieler();
    }

    public Card getCardOnTable() {
        return control.getCardOnTable();
    }

    public String getName(int index) {
        return control.getName(index);
    }

    public ArrayList<Card> getCardsOnHand(int index) {
        return control.getCardsOnHand(index);
    }

    public int getActivePlayer() {
        return control.getActivePlayer();
    }

    public boolean isGameActive() {
        return control.isGameActive();
    }

    public ArrayList<String> getVerlauf() {
        return control.getVerlauf();
    }

    public void clickCard(Card card, int index) {
        if (control.getActivePlayer() == 0) {
            if (control.ueberpruefenKarte(card)) {
                guiGame.setAusgwCardValue(card.getColorValue());
                control.layDownCard(card, 0);
                guiGame.getCardsOnHand().remove(guiGame.getBtn_Cards().get(index));
                guiGame.getBtn_Cards().remove(index);
                guiGame.setZug(true);
            } else {
                guiGame.showErrorScreen("Diese Karte kann nicht gelegt werden");
            }
            guiGame.updateGui();
        }
    }

    public void clickStapel() {
        control.aufnehmenKarte(0);
        guiGame.setZug(true);
        guiGame.setAusgwCardValue(0);

        guiGame.updateGui();
    }

    public void clickNext() {
        control.reactionBot();
        guiGame.updateGui();
    }

    public void clickFarbe(int index) {
        String farbe = switch (index) {
            case 0 -> "Red";
            case 1 -> "Green";
            case 2 -> "Blue";
            case 3 -> "Yellow";
            default -> "undefiniert";
        };
        guiGame.setAusgwCardValue(0);
        control.layDownCard(new Card(farbe, 69), 0);
        guiGame.setZug(true);
        guiGame.auswahlFarbe(false);
        guiGame.updateGui();
    }
}
