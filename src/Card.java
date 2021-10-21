public class Card {
    private String color;
    private String name;
    private int value;

    public Card(String pColor, int pValue) {
        color = pColor;
        value = pValue;
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

    public String getName() {
        return name;
    }
}
