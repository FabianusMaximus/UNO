package UNO.Components;

import UNO.Kartenlogik.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CardPanel extends JPanel {
    private Card linkedCard;
    private JLabel topLeft;
    private JLabel bottomRight;
    private JLabel middle;

    private BufferedImage background;
    private JLabel imgLabel;

    private Color color;
    private Font standardFont;
    private Color fontColor;

    public CardPanel(Card card) throws IOException {
        super();
        linkedCard = card;
        color = card.getColorObjekt();
        this.setBackground(color);
        this.setForeground(color);
        this.setLayout(null);
        standardFont = new Font("Arial", Font.PLAIN, 50);
        fontColor = Color.white;
        String value = String.valueOf(card.getValue());

        background = ImageIO.read(new File("src/img/UnoCard.png"));
        imgLabel = new JLabel(new ImageIcon(resizeImage(background,550,870)));
        imgLabel.setBounds(0,0,550,870);
        this.add(imgLabel);
        topLeft = new JLabel(value);
        bottomRight = new JLabel(value);
        middle = new JLabel(value);
    }

    public void designtCard() {
        int width = getWidth();
        int height = getHeight();
        topLeft.setSize(new Dimension(this.getWidth(), this.getWidth()));
        topLeft.setBounds(0, 0, width / 10, width / 10);
        topLeft.setFont(standardFont);
        topLeft.setForeground(fontColor);
        this.add(topLeft);

        this.repaint();
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }


    public static void main(String[] args) throws IOException {
        JFrame mainFrame = new JFrame();
        Container c = mainFrame.getContentPane();
        mainFrame.setSize(550, 870);
        mainFrame.setLayout(null);
        CardPanel hans = new CardPanel(new Card("Blue", 5));
        hans.setBounds(0,0,550,870);

        c.add(hans);

        hans.designtCard();

        mainFrame.setVisible(true);
    }
}


