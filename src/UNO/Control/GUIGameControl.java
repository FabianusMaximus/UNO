package UNO.Control;

import UNO.GUI.BotHand;
import UNO.GUI.GUIGame;
import UNO.GUI.WinScreen;
import UNO.Kartenlogik.Card;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
            guiGame.showUnoButton(control.getSpieler(0).getAnzCards() == 1);
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
        if (mussUnoSagen() && !control.getSpieler(0).hatUnoGesagt()) {
            for (int i = 0; i < 4; i++) {
                control.aufnehmenKarte(0);
            }
            guiGame.showErrorScreen("Du hast vergessen UNO zu sagen");
        }
        control.getSpieler(0).setUno(false);
        control.reactionBot();
        guiGame.updateGui();
        updateBotHands();
        if (!control.isGameActive()) goToWinScreen();
    }

    public void clickColor(int index) {
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

    /**
     * Öffnet die Website von Uno, auf der die Spielregeln erklärt werden
     */
    public void clickDescription() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/FabianusMaximus/UNO"));
            } catch (IOException | URISyntaxException e) {
                System.out.println("URL konnte nicht aufgerufen werden");
            }
        }
    }

    public void updateBotHands() {
        botHands.forEach(BotHand::updateBotHand);
    }

    private void goToWinScreen() {
        guiGame.dispose();
        winScreen = new WinScreen(control);
    }

    public void sayUno() {
        control.getSpieler(0).setUno(true);
    }

    private boolean mussUnoSagen() {
        return control.getSpieler(0).getAnzCards() == 1;
    }
}
