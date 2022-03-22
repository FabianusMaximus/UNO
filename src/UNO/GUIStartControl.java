package UNO;

import UNO.GUI.GUIStart;
import UNO.Kartenlogik.Deck;

public class GUIStartControl {
    private GUIStart guiGame;
    private Control control;

    public GUIStartControl(Control control) {
        this.guiGame = new GUIStart(this);
        this.control = control;
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
