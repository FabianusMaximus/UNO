package UNO.Kartenlogik;

public class Card {
    private String color;
    private int value;
    private String name;
    private int colorValue;

    public Card(String pColor, int pValue) {
        color = pColor;
        value = pValue;
        if (pColor.equalsIgnoreCase("Rot")) {
            colorValue = 0;
        } else if (pColor.equalsIgnoreCase("Green")) {
            colorValue = 1;
        } else if (pColor.equalsIgnoreCase("Blue")) {
            colorValue = 2;
        } else if (pColor.equalsIgnoreCase("Yellow")) {
            colorValue = 3;
        } else if (pColor.equalsIgnoreCase("Black")) {
            colorValue = 4;
        }
        if (value <= 9) {
            name = color + " " + value;
        } else if (value == 10 && !color.equalsIgnoreCase("Black")) {
            name = color + " " + "+2";
        } else if (value == 11 && !color.equalsIgnoreCase("Black")) {
            name = color + " " + "reverse";
        } else if (value == 12 && !color.equalsIgnoreCase("Black")) {
            name = color + " " + "block";
        } else if (value == 13 && color.equalsIgnoreCase("Black")) {
            name = "color swap";
        } else if (value == 14 && color.equalsIgnoreCase("Black")) {
            name = "color swap + 4";
        }else if(value == 69){
            name = color;
        }

    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public int getColorValue() {
        return colorValue;
    }

    public int compareValueTo(Card pCard) {
        int res = 0;
        if (this.getValue() < pCard.getValue()) {
            res = -1;
        }
        if (this.getValue() > pCard.getValue()) {
            res = 1;
        }
        return res;
    }

    public int compareColorTo(Card pCard) {
        int res = 0;
        if (this.getColorValue() < pCard.getColorValue()) {
            res = -1;
        }
        if (this.getColorValue() > pCard.getColorValue()) {
            res = 1;
        }
        return res;
    }

    public String getName() {
        return name;
    }
}
