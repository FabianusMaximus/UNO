import java.util.ArrayList;

public class Tabletop {
    private Card cardsOnTable;

    public Tabletop(){
    }

    public void layCardOnTable(Card pCard){
        cardsOnTable= pCard;
    }

    public Card getCardOnTable(){
        return cardsOnTable;
    }
}
