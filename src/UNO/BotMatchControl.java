package UNO;

import UNO.GUI.GUIBotMatch;
import UNO.Kartenlogik.Deck;
import util.Tracking;

public class BotMatchControl {
    private Control control;
    private GUIBotMatch guiBotMatch;

    public BotMatchControl(Control control) {
        this.control = control;
        guiBotMatch = new GUIBotMatch(this);
    }

    public void clickSimulate() {
        guiBotMatch.setButtonEnabled(false);
        for (int i = 0; i < guiBotMatch.getAnzIterationen(); i++) {
            control.setAnzSpieler(guiBotMatch.getAnzBots());
            control.startBotMatch();
        }
        guiBotMatch.setButtonEnabled(true);
    }

    public int getDifficulty(int index) {
        return guiBotMatch.getValueSlider(index);
    }

    public void appendToVerlauf(String text) {
        guiBotMatch.appendToVerlauf(text);
    }

    public void updateGewonnen(int bot, int gewonnen) {
        guiBotMatch.updateGewonnen(bot, gewonnen);
    }

    public void trackStats(int farbwechselFarbeMax, int farbwechselFarbeMin, int nextPlayerAvoidance,
                           int plusTwoWhen, int farbwechselWhen, int gewonnenBot0, int gewonnenBot1) {
        Tracking theTracking = new Tracking();
        theTracking.trackStats(farbwechselFarbeMax, farbwechselFarbeMin, nextPlayerAvoidance,
                plusTwoWhen, farbwechselWhen, gewonnenBot0, gewonnenBot1);
    }
}
