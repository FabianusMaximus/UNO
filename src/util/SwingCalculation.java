package util;

import javax.swing.*;
import java.awt.*;

public class SwingCalculation {

    public static int centerX(Component comp1, Component comp2) {
        return comp1.getWidth() / 2 - comp2.getWidth() / 2;
    }

    public static int centerY(Component comp1, Component comp2) {
        return comp1.getHeight() / 2 - comp2.getHeight() / 2;
    }

    public static Point center(Component comp1, Component comp2) {
        return new Point(centerX(comp1, comp2), centerY(comp1, comp2));
    }

    public static Dimension fiftyPercent(Component comp) {
        return new Dimension(comp.getWidth() / 2, comp.getHeight() / 2);
    }

    /**
     * Gibt eine Dimension zurück, die das größte Quadrat einer anderen Oberfläche darstellt
     *
     * @param comp
     * @return
     */
    public static Dimension square(Component comp) {
        if (comp.getWidth() > comp.getHeight()) return new Dimension(comp.getWidth(), comp.getWidth());
        else return new Dimension(comp.getHeight(), comp.getHeight());
    }

    public static Dimension twoThirdsRectangle(Component comp) {
        return new Dimension(comp.getWidth(), comp.getHeight() / 3 * 2);
    }

    public static Dimension getTwoThirdsScreen() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();

        return new Dimension((int) (w * 0.6), (int) (h * 0.6));
    }
}
