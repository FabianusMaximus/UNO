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

    public CardPanel(Card card, int width, int height) throws IOException {
        super();
        linkedCard = card;
        color = card.getColorObjekt();
        this.setBackground(color);
        this.setForeground(color);
        this.setLayout(null);
        standardFont = new Font("Arial", Font.PLAIN, 40);
        fontColor = Color.white;
        String value = String.valueOf(card.getValue());

        background = ImageIO.read(new File("src/img/UnoCard.png"));
        imgLabel = new JLabel(resizeImage(new ImageIcon("src/img/UnoCard.png"),
                width, height));
        imgLabel.setBounds(0, 0, width, height);
        this.add(imgLabel);
        topLeft = new JLabel(value);
        bottomRight = new JLabel(value);
        middle = new JLabel(value);
        designtCard();
    }

    public void designtCard() {
        int width = getWidth();
        int height = getHeight();
        topLeft.setSize(new Dimension(this.getWidth(), this.getWidth()));
        topLeft.setBounds(20, 20, width / 10, width / 10);
        topLeft.setFont(standardFont);
        topLeft.setForeground(fontColor);
        imgLabel.add(topLeft);

        middle.setSize(width / 2, height / 2);
        middle.setLocation(width / 2 - middle.getWidth() / 2, height / 2 - middle.getHeight() / 2);
        middle.setFont(standardFont);
        middle.setForeground(Color.blue);
        imgLabel.add(middle);

        bottomRight.setSize(new Dimension(this.getWidth(), this.getWidth()));
        bottomRight.setBounds(width - 20, height - 20, width / 10, height / 10);
        bottomRight.setFont(standardFont);
        bottomRight.setForeground(fontColor);
        imgLabel.add(bottomRight);

        this.repaint();
    }

    private ImageIcon resizeImage(ImageIcon originalImage, int targetWidth, int targetHeight){
        return new ImageIcon(originalImage.getImage()
                .getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT));
    }


    public static void main(String[] args) throws IOException {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container c = mainFrame.getContentPane();
        int width = 300;
        int height = 500;
        mainFrame.setSize(width, height);
        mainFrame.setLayout(null);
        CardPanel hans = new CardPanel(new Card("Blue", 5), width, height);
        hans.setBounds(0, 0, width, height);

        c.add(hans);

        mainFrame.setVisible(true);
    }
}


