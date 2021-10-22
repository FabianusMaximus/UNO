import java.util.ArrayList;

public class Tabletop {
    private ArrayList<Card> cardsOnTable;

    public Tabletop(){

    }

    public void layCardOnTable(Card pCard){
        cardsOnTable.add(pCard);
    }

    public ArrayList<Card> getCardsOnTable(){
        return cardsOnTable;
    }
}
