package UNO;

import UNO.GUI.GUIBotMatch;
import UNO.GUI.GUIStart;
import UNO.Kartenlogik.Deck;

public class GUIStartControl {
    private GUIStart guiGame;
    private Control control;

    private BotMatchControl bmControl;

    public GUIStartControl(Control control, BotMatchControl bmControl) {
        this.guiGame = new GUIStart(this);
        this.control = control;
        this.bmControl = bmControl;
    }

    public void goToBotMatch(){
        control.goToBotMatch();
    }

    public void setDeck(int pAnz) {
        control.setDeck(new Deck(pAnz));
        ;
    }

    public void setAnzSpieler(int pAnz) {
        control.setAnzSpieler(pAnz);
    }

    public void setName(String pName) {
        control.setName(pName);
    }

    public void setDifficulty(int pDifficulty) {
        control.setDifficulty(pDifficulty);
    }

    public void start(){
        control.start();
    }
}
