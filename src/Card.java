public class Card {
    private String color;
    private int value;
    private String name;
    private int colorValue;

    public Card(String pColor, int pValue) {
        color = pColor;
        value = pValue;
        if (pColor.equals("Rot")) {
            colorValue = 0;
        } else if (pColor.equals("Green")) {
            colorValue = 1;
        } else if (pColor.equals("Blue")) {
            colorValue = 2;
        } else if (pColor.equals("Yellow")) {
            colorValue = 3;
        } else if (pColor.equals("Black")) {
            colorValue = 4;
        }
        if (value <= 9) {
            name = color + " " + value;
        } else if (value == 10 && !color.equals("Black")) {
            name = color + " " + "+2";
        } else if (value == 10 && color.equals("Black")) {
            name = "Farbwechselkarte";
        } else if (value == 11 && color.equals("Black")) {
            name = "Farbwechsel + 4";
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
