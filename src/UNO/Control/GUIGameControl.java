package UNO.Control;

import UNO.GUI.BotHand;
import UNO.GUI.GUIGame;
import UNO.GUI.WinScreen;
import UNO.Kartenlogik.Card;

import java.util.ArrayList;

public class GUIGameControl {
    private Control control;
    private GUIGame guiGame;

    private WinScreen winScreen;
    private ArrayList<BotHand> botHands = new ArrayList<>();

    public GUIGameControl(Control control) {
        this.control = control;
        this.guiGame = new GUIGame(this);
        control.setTheGameGUI(guiGame);

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
        System.out.println("Ausgewählte Karte: " + card.getName());
        if (control.getActivePlayer() == 0) {
            if (control.ueberpruefenKarte(card)) {
                guiGame.setAusgwCardValue(card.getColorValue());
                control.layDownCard(card, 0);
                guiGame.getCardsOnHand().remove(guiGame.getBtn_Cards().get(index));
                guiGame.getBtn_Cards().remove(index);
                control.continueToNextPlayer();
            } else {
                guiGame.showErrorScreen("Diese Karte kann nicht gelegt werden");
            }
            guiGame.updateGui();
        }
    }

    public void clickStapel() {
        if (control.getActivePlayer() == 0) {
            control.aufnehmenKarte(0);
            control.continueToNextPlayer();
        }
        guiGame.updateGui();
    }

    public void clickNext() {
        control.reactionBot();
        guiGame.updateGui();
        updateBotHands();
        if (!control.isGameActive())goToWinScreen();
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
        guiGame.auswahlFarbe(false);
        guiGame.updateGui();
    }

    public void clickMenuItem(int index) {
        botHands.add(new BotHand(control, index));
    }

    public void updateBotHands() {
        botHands.forEach(BotHand::updateBotHand);
    }

    private void goToWinScreen(){
        guiGame.dispose();
        winScreen = new WinScreen(control);
    }
}
