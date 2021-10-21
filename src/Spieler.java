import java.util.ArrayList;

public class Spieler {
    private ArrayList<Card> hand = new ArrayList<>();

    public Spieler(){

    }
    public ArrayList<Card> getHand(){
        return hand;
    }
    public void addCardToHand(Card pCard){
        hand.add(pCard);
    }
    public Card selectCard(int pNr){
        return hand.get(pNr);
    }
}
