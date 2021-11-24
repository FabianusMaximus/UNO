package UNO.GUI;

import UNO.Control;
import UNO.Kartenlogik.Card;
import UNO.Kartenlogik.Spieler;

import java.util.ArrayList;

public class GUI {
    private Control control;

    public GUI(Control pControl) {
        control = pControl;

    }

    public void printDeck(ArrayList<Card> pDeck) {
        System.out.println("--------Deck--------");
        for (int i = 0; i < pDeck.size(); i++) {
            System.out.println(pDeck.get(i).getName());
        }
    }

    public void printHand(String pName, int spielerNr, ArrayList<Spieler> pSpieler) {
        System.out.println("--------" + pName + " --------");
        for (int i = 0; i < pSpieler.get(spielerNr).getHand().size(); i++) {
            System.out.println((i + 1) + ": " + pSpieler.get(spielerNr).getHand().get(i).getName());
        }
        System.out.println("99: Karte aufnehmen");
    }

    public void printTabletop(Card pTabletop) {
        System.out.println("-------Card on Table-------");
        System.out.println(pTabletop.getName());

    }

    public void printCardsOtherPlayers(ArrayList<Spieler> pSpieler) {
        System.out.println("-------Cards other Players-------");
        for (int i = 1; i < pSpieler.size(); i++) {
            System.out.println(pSpieler.get(i).getName() + ": " + pSpieler.get(i).getNrOfCards());
        }
    }
}
