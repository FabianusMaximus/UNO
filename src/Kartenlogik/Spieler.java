package Kartenlogik;

import java.util.ArrayList;

public class Spieler {
    private String name;
    private ArrayList<Card> hand = new ArrayList<>();
    private  int nr;
    private boolean uno = false;

    public Spieler(int pNr) {
        name = "Bot " + nr;
        nr = pNr;
    }

    public Spieler(String pName) {
        name = pName;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addCardToHand(Card pCard) {
        hand.add(pCard);
    }

    public Card selectCard(int pNr) {
        return hand.get(pNr);
    }

    public String getName() {
        return name;
    }

    public int getNr(){
        return nr;
    }

    public void setUno(boolean pUno){
        uno = pUno;
    }

    public boolean hatUnoGesagt(){
        return uno;
    }


    public void sortieren() {
        Card temp;
        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < hand.size() - 1; i++) {
                if (hand.get(i).compareValueTo(hand.get(i + 1)) > 0) {
                    temp = hand.get(i);
                    hand.set(i, hand.get(i + 1));
                    hand.set(i + 1, temp);
                    sorted = false;
                }
            }
        }
        sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < hand.size() - 1; i++) {
                if (hand.get(i).compareColorTo(hand.get(i + 1)) > 0) {
                    temp = hand.get(i);
                    hand.set(i, hand.get(i + 1));
                    hand.set(i + 1, temp);
                    sorted = false;
                }
            }
        }

    }

}
