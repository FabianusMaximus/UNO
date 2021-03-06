package UNO.Kartenlogik;

import UNO.Control.Control;

import java.awt.*;
import java.util.ArrayList;


public class Bot extends Spieler {
    private Control control;
    private boolean spielen;
    private int difficulty, bestColor, rundenGewonnen;

    public Bot(int nummer, Control pControl, int pDifficulty) {
        super(nummer);
        control = pControl;
        difficulty = pDifficulty;
    }

    /**
     * wählt die erste Krate aus, die er legen kann
     */
    private void berechnenSpielzugEasy() {
        ArrayList<Card> holdCards = this.getHand();
        ArrayList<Card> validCards = selectValidCards(holdCards);
        if (validCards.size() != 0) {
            int random = ((int) (Math.random() * validCards.size()));
            control.setAusgwCard(validCards.get(random));
            spielen = true;
        } else spielen = false;
    }

    /**
     * Wählt aus den Karten, die er legen kann zufällig aus.
     * Schaut bei einem Farbwechsel, welche Farbe er am meisten besitzt
     */
    private void berechnenSpielzugMedium() {
        ArrayList<Card> holdCards = this.getHand();
        ArrayList<Card> validCards = selectValidCards(holdCards);
        int[] anzColor = countAnzColors(holdCards);
        if (validCards.size() != 0) {
            control.setAusgwCard(validCards.get(0));
            spielen = true;
        } else spielen = false;

        bestColor = getIndexHighestNumber(anzColor);
    }

    private void berechnenSpielzugHard() {
        //TODO Farbe wichtiger als Zahl
        //TODO verhindern dass der Gegner mehr Karten einer Farbe legen kann, eher Farbe wechseln

        ArrayList<Card> holdCards = this.getHand();
        ArrayList<Card> validCards = selectValidCards(holdCards);
        int[] anzColor = countAnzColors(holdCards);
        int[] anzActionCards = countAnzActionCards(holdCards);
        boolean hasSwap = hasCard(holdCards, 13);
        boolean hasPlusFour = hasCard(holdCards, 14);
        boolean hasPlusTwo = hasCard(validCards, 10);
        boolean hasAvoidingCard = hasCard(validCards, 11) || hasCard(validCards, 12);
        boolean hasActionCards = hasActionCards(validCards);

        if (validCards.size() != 0) {
            //wenn ihm nichts Besseres einfällt, macht er den erst besten Zug
            control.setAusgwCard(validCards.get(0));
            //Schaut, ob er die Farbe hat, die schon auf dem Tisch liegt
            if (hasColor(validCards, control.getCardOnTable().getColorValue())) {
                control.setAusgwCard(selectCard(validCards, control.getCardOnTable().getColorObjekt()));
            }
            //Legt keine Farbwechselkarte, wenn er weniger als 4 aber mehr als eine Karte auf der Hand hat (Bitch-Finish)
            if (holdCards.size() <= 4 && holdCards.size() > 1) {
                removeCardFromList(validCards, 13);
                removeCardFromList(validCards, 14);
                hasSwap = false;
                hasPlusFour = false;
            }
            //Wenn der nachfolgende Spieler nur noch eine Karte wird versucht dessen Zug zu vermeiden
            if (hasAvoidingCard && control.getNextPlayer().getAnzCards() == 1) {
                if (hasCard(validCards, 11)) control.setAusgwCard(selectCard(validCards, 11));
                else control.setAusgwCard(selectCard(validCards, 12));
            }
            //Schaut, ob der nächste Spieler weniger als 3 Karten hat und ob er eine +2 legen kann
            if (hasPlusTwo && control.getNextPlayer().getAnzCards() >= 3) {
                control.setAusgwCard(selectCard(validCards, 10));
            }
            //Schaut, ob er mehr als 4 Karten einer Farbe hat und überlegt sich dann, ob er wechseln soll
            if (hasMoreThan4OfOneColor(anzColor)) {
                if (hasPlusFour && control.getNextPlayer().getAnzCards() >= 4) {
                    control.setAusgwCard(selectCard(validCards, 14));
                } else if (hasSwap) {
                    control.setAusgwCard(selectCard(validCards, 13));
                }
            }
            //Legt eher Aktionskarten ab
            if (hasActionCards) control.setAusgwCard(selectComboCard(validCards));
            //Schaut, ob er Karten hat die sich zusammen ablegen lassen
            if (hasComboCards(validCards)) control.setAusgwCard(selectComboCard(validCards));

            spielen = true;
        } else spielen = false;

        bestColor = getIndexHighestNumber(anzColor);
    }

    public boolean kannSpielen() {
        return spielen;
    }

    /**
     * Wählt, je nach Schwierigkeit der Bots, die Farbe aus zu der gewechselt werden soll
     *
     * @return Sting der Farbe zu der gewechselt werden soll
     */
    public String auswaehlenFarbe() {
        String[] farbe = {"red", "green", "blue", "yellow"};
        String ausgfarbe = "";
        return switch (difficulty) {
            case 0 -> farbe[((int) (Math.random() * 4))];
            case 1, 2 -> farbe[bestColor];
            default -> ausgfarbe;
        };
    }

    /**
     * Gibt den Index der höchsten Zahl des Arrays zurück
     *
     * @param numbers Zahlen die verglichen werden sollen
     * @return index höchste Zahl
     */
    private int getIndexHighestNumber(int[] numbers) {
        int comp = 0;
        int index = 0;
        for (int number : numbers) {
            if (number > comp) {
                comp = number;
            }
        }
        for (int i = 0; i < numbers.length; i++) {
            if (comp == numbers[i]) {
                return i;
            }
        }
        return index;
    }

    /**
     * Zählt, wie viele Karten von jeder Farbe der Bot besitzt
     *
     * @param cards Liste der Karten die gezählt werden sollen
     * @return Array mit der Anzahl der Karten pro Farbe.
     * 0 = Rot, 1 = Grün, 2 = Blau, 3 = Gelb, 4 = Schwarz
     */
    private int[] countAnzColors(ArrayList<Card> cards) {
        int[] anzColor = new int[4];
        for (Card card : cards) {
            switch (card.getColorValue()) {
                case 0 -> anzColor[0]++;
                case 1 -> anzColor[1]++;
                case 2 -> anzColor[2]++;
                case 3 -> anzColor[3]++;
            }
        }
        return anzColor;
    }

    /**
     * Zählt, wie viele Aktionskarten man von welcher Sorte hat
     *
     * @param cards Liste der Karten die gezählt werden sollen
     * @return Array mit der Anzahl der Karten pro Actionskarte.
     * 0 = +2, 1 = reverse, 2 = block, 3 = swap, 4 = +4.
     */
    private int[] countAnzActionCards(ArrayList<Card> cards) {
        int[] actionCards = new int[5];
        for (Card card : cards) {
            switch (card.getValue()) {
                case 10 -> actionCards[0]++;
                case 11 -> actionCards[1]++;
                case 12 -> actionCards[2]++;
                case 13 -> actionCards[3]++;
                case 14 -> actionCards[4]++;

            }
        }
        return actionCards;
    }

    /**
     * Gibt alle Karten zurück die gelegt werden können
     *
     * @param cards Liste der Karten die überprüft werden sollen
     * @return Liste der Karten die gelegt werden können
     */
    private ArrayList<Card> selectValidCards(ArrayList<Card> cards) {
        ArrayList<Card> validCards = new ArrayList<>();
        for (Card card : cards) {
            if (control.ueberpruefenKarte(card)) validCards.add(card);
        }
        return validCards;
    }

    /**
     * Entfernt alle Karten von einem bestimmten Wert aus der Liste
     *
     * @param cards Liste der Karten aus der entfernt werden soll
     * @param value Wert der Karten die entfernt werden sollen
     */
    private void removeCardFromList(ArrayList<Card> cards, int value) {
        cards.removeIf(card -> card.getValue() == value);
    }

    /**
     * Gibt zurück, ob er mindestens eine Karte mit einem bestimmten Wert besitzt
     *
     * @param cards Karten die überprüft werden sollen
     * @param value Wert der Karte
     * @return ob er mindestens eine Karte hat
     */
    private boolean hasCard(ArrayList<Card> cards, int value) {
        for (Card card : cards) {
            if (card.getValue() == value) return true;
        }
        return false;
    }

    private boolean hasMoreThan4OfOneColor(int[] anzColors) {
        ArrayList<Integer> colorsWithMoreThan4Cards = new ArrayList<>();
        for (int anzColor : anzColors) {
            if (anzColor >= 4) return true;
        }
        return false;
    }

    private Card selectCard(ArrayList<Card> cards, int value) {
        for (Card card : cards) {
            if (card.getValue() == value) return card;
        }
        System.out.println("habe Karte zwar gesucht, aber nicht gefunden: 1 " + "value: " + value);
        return null;
    }

    private Card selectCard(ArrayList<Card> cards, Color color) {
        for (Card card : cards) {
            if (card.getColorObjekt() == color) return card;
        }
        System.out.println("habe Karte zwar gesucht, aber nicht gefunden: 2");
        throw new RuntimeException();
    }

    /**
     * Gibt karte mit den ausgewählten Parametern zurück
     *
     * @param cards Karten aus denen ausgewählt werden soll
     * @param value Wert der Karte
     * @param color Farbe der Karte. 0 = Rot, 1 = Grün, 2 = Blau, 3 = Gelb, 4 = Schwarz
     * @return Carte die die Parameter erfüllt
     */
    private Card selectCard(ArrayList<Card> cards, int value, int color) {
        for (Card card : cards) {
            if (card.getValue() == value && card.getColorValue() == color) return card;
        }
        System.out.println("habe Karte zwar gesucht, aber nicht gefunden: 3" + value + " " + color);
        return null;
    }

    /**
     * Es gibt karten die sich in einer 1 gegen 1 Situation zusammen ablegen lassen
     *
     * @return Ob er Karten hat, die man zusammen ablegen kann
     */
    private boolean hasComboCards(ArrayList<Card> validCards) {
        int anzPlusTwo = 0;
        int anzReverse = 0;
        int anzBlock = 0;
        if (control.getAnzSpieler() == 2) {
            for (Card card : validCards) {
                switch (card.getValue()) {
                    case 10 -> anzPlusTwo++;
                    case 11 -> anzReverse++;
                    case 12 -> anzBlock++;
                }
            }
        }
        return anzPlusTwo > 1 || anzReverse > 1 || anzBlock > 1;
    }

    private boolean hasActionCards(ArrayList<Card> validCards) {
        int anz = 0;
        for (Card card : validCards) {
            switch (card.getValue()) {
                case 10, 11, 12 -> anz++;
            }
        }
        return anz > 0;
    }

    private Card selectComboCard(ArrayList<Card> validCards) {
        for (Card card : validCards) {
            if (card.getValue() >= 10 && card.getValue() <= 12) return card;
        }
        System.out.println("Kombo-karte konnte nicht gefunden werden");
        return null;

    }

    private boolean hasColor(ArrayList<Card> cards, int color) {
        for (Card card : cards) {
            if (card.getColorValue() == color) return true;
        }
        return false;
    }

    public void reaction() {
        switch (difficulty) {
            case 0 -> berechnenSpielzugEasy();
            case 1 -> berechnenSpielzugMedium();
            case 2 -> berechnenSpielzugHard();
        }
    }

    public void erhoehenRundenGewonnen() {
        rundenGewonnen++;
    }

    public int getRundenGewonnen() {
        return rundenGewonnen;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
