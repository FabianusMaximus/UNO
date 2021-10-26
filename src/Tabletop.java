import Kartenlogik.Card;

public class Tabletop {
    private Card cardOnTable;

    public Tabletop(){
    }

    public void layCardOnTable(Card pCard){
        cardOnTable= pCard;
    }

    public Card getCardOnTable(){
        return cardOnTable;
    }
}
