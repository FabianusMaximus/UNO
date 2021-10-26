import Kartenlogik.Card;
import Kartenlogik.Spieler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI  {
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

    public void printHand(int spielerNr, ArrayList<Spieler> pSpieler) {
        System.out.println("--------Hand Spieler " + spielerNr + "--------");
        for (int i = 0; i < pSpieler.get(spielerNr).getHand().size(); i++) {
            System.out.println((i + 1) + ": " + pSpieler.get(spielerNr).getHand().get(i).getName());
        }
        System.out.println("99: Karte aufnehmen");
    }

    public void printTabletop(Card pTabletop) {
        System.out.println("-------Card on Table-------");
            System.out.println(pTabletop.getName());

    }
}
