import Kartenlogik.Card;
import Kartenlogik.Spieler;

import java.util.ArrayList;

public class Bot {
    private String name;
    private Spieler spieler;
    private Control control;

    public Bot(String pName, Spieler pSpieler, Control pControl) {
        name = pName;
        spieler = pSpieler;
        control = pControl;
    }

    private void berechnenSpielzug() {
        ArrayList<Card> holdCards = new ArrayList<Card>();
        holdCards = spieler.getHand();
        int bestCard = 16;
        int comp = 0;
        int index = 99;
        for (int i = 0; i < holdCards.size(); i++) {
            if (control.überpruefenCarte(holdCards.get(i))) {
                control.setAusgwCard(holdCards.get(i));
            }
        }
    }

    public String auswaehlenFarbe() {
        String farbe[] = {"red", "green", "blue", "yellow"};
        return farbe[((int) (Math.random() * 4))];
    }

    public void reaction() {
        berechnenSpielzug();
    }


}
