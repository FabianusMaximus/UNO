package util;

import java.awt.*;

public class SwingCalculation {

    public static int centerX(Component comp1, Component comp2) {
        return comp1.getWidth() / 2 - comp2.getWidth() / 2;
    }

    public static int centerY(Component comp1, Component comp2) {
        return comp1.getHeight() / 2 - comp2.getHeight() / 2;
    }
}
