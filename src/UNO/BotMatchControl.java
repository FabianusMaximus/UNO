package UNO;

import UNO.GUI.GUIBotMatch;
import UNO.Kartenlogik.Deck;

public class BotMatchControl {
    private Control control;
    private GUIBotMatch guiBotMatch;

    public BotMatchControl(Control control) {
        this.control = control;
        guiBotMatch = new GUIBotMatch(this);
    }

    public void clickSimulate() {
        guiBotMatch.setButtonEnabled(false);
        control.setAnzSpieler(guiBotMatch.getAnzBots());

    }
}
