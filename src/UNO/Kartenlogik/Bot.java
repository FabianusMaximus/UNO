package UNO.Kartenlogik;

import UNO.Control;
import UNO.Kartenlogik.Card;
import UNO.Kartenlogik.Spieler;

import java.util.ArrayList;


public class Bot {
    private String name;
    private Spieler spieler;
    private Control control;
    private boolean spielen;
    private int difficulty;
    private int bestColor;

    public Bot(String pName, Spieler pSpieler, Control pControl, int pDifficulty) {
        name = pName;
        spieler = pSpieler;
        control = pControl;
        difficulty = pDifficulty;
    }

    private void berechnenSpielzugEasy() {
        ArrayList<Card> holdCards;
        holdCards = spieler.getHand();
        for (int i = 0; i < holdCards.size(); i++) {
            if (control.überpruefenCarte(holdCards.get(i))) {
                control.setAusgwCard(holdCards.get(i));
                spielen = true;
            } else {
                spielen = false;
            }
        }
    }

    /**
     * Wöhlt aus den Karten die er legen kann zufällig aus.
     * Schaut bei einem Farbwechsel, welche Farbe er am meisten besietzt
     */
    private void berechnenSpielzugMedium() {
        ArrayList<Card> holdCards;
        holdCards = spieler.getHand();
        ArrayList<Card> validCards = new ArrayList<Card>();
        int[] anzColor = new int[4];
        int compColors = 0;
        for (int i = 0; i < holdCards.size(); i++) {
            switch (holdCards.get(i).getColorValue()) {
                case 0 -> anzColor[0]++;
                case 1 -> anzColor[1]++;
                case 2 -> anzColor[2]++;
                case 3 -> anzColor[3]++;
            }
            if (control.überpruefenCarte(holdCards.get(i))) {
                validCards.add(holdCards.get(i));
            }
        }
        if (validCards.size() != 0) {
            int random = ((int) (Math.random() * validCards.size()));
            control.setAusgwCard(validCards.get(random));
            spielen = true;
        } else {
            spielen = false;
        }


        for (int i = 0; i < anzColor.length; i++) {
            if (compColors < anzColor[i]) {
                compColors = anzColor[i];
                bestColor = i;
            }
        }
    }

    private void berechnenSpielzugHard() {
        //TODO Wenn bot 4 oder weniger Kraten hat, kein color swap
        //TODO wenn er mehr als 4 von einer Farbe hat -> eher color swap
    }

    public boolean kannSpielen() {
        return spielen;
    }

    public String auswaehlenFarbe() {
        String farbe[] = {"red", "green", "blue", "yellow"};
        String ausgfarbe = "";
        return switch (difficulty) {
            case 0 -> ausgfarbe = farbe[((int) (Math.random() * 4))];
            case 1, 2 -> ausgfarbe = farbe[bestColor];
            default -> ausgfarbe;
        };


    }

    public void reaction() {
        switch (difficulty) {
            case 0 -> berechnenSpielzugEasy();
            case 1 -> berechnenSpielzugMedium();
            case 2 -> berechnenSpielzugHard();
        }

    }


}
