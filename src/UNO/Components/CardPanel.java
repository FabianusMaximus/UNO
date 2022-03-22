package UNO.Components;

import UNO.Kartenlogik.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;

public class CardPanel extends JPanel {
    private Card linkedCard;
    private JLabel topLeft, bottomRight, middle, imgLabel;
    private Color color;
    private Font standardFont;
    private Color fontColor;

    public CardPanel(Card card) {
        super();
        linkedCard = card;
        color = card.getColorObjekt();
        this.setBackground(color);
        this.setForeground(color);
        this.setLayout(null);
        standardFont = new Font("Arial", Font.PLAIN, 40);
        fontColor = Color.white;
    }

    public void designtCard() {
        String value = String.valueOf(linkedCard.getValue());

        topLeft = new JLabel(value);
        topLeft.setSize(this.getWidth() / 10, this.getHeight() / 10);
        topLeft.setLocation(20, 20);
        topLeft.setFont(standardFont);
        topLeft.setForeground(fontColor);
        this.add(topLeft);

        middle = new JLabel(value);
        middle.setSize(this.getWidth() / 2, this.getHeight() / 2);
        middle.setLocation(this.getWidth() / 2 - middle.getWidth() / 2, this.getHeight() / 2 - middle.getHeight() / 2);
        middle.setFont(new Font("Arial", Font.PLAIN, 100));
        middle.setVerticalAlignment(SwingConstants.CENTER);
        middle.setHorizontalAlignment(SwingConstants.CENTER);
        middle.setForeground(linkedCard.getColorObjekt());
        if (imgLabel != null) imgLabel.add(middle);

        bottomRight = new JLabel(value);
        bottomRight.setSize(this.getWidth() / 10, this.getHeight() / 10);
        bottomRight.setLocation(this.getWidth() - bottomRight.getWidth() - 20, this.getHeight() - bottomRight.getHeight() - 20);
        bottomRight.setFont(standardFont);
        bottomRight.setForeground(fontColor);
        this.add(bottomRight);
    }

    private void addContentToPanel() {
        try {
            imgLabel = new JLabel(resizeImage(new ImageIcon("src/img/UnoCard.png"),
                    this.getWidth(), this.getHeight()));
            imgLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
            this.add(imgLabel);
        } catch (IllegalArgumentException e) {
            System.out.println("bild kann nicht resized werden");
        }
        designtCard();
        this.revalidate();
        this.repaint();
    }

    public void updateGui() {
        for (Component component : this.getComponents()) {
            System.out.println("Penis");
        }
    }

    private ImageIcon resizeImage(ImageIcon originalImage, int targetWidth, int targetHeight) {
        return new ImageIcon(originalImage.getImage()
                .getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT));
    }


    public static void main(String[] args) throws IOException {
        int width = 1920;
        int height = 1080;

        JFrame mainFrame = new JFrame();
        mainFrame.setSize(width, height);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(1, 5));
        ArrayList<CardPanel> cardPanels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cardPanels.add(new CardPanel(new Card("Blue", 5)));
            mainFrame.add(cardPanels.get(i));
        }



        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (CardPanel cardPanel : cardPanels) {
                    cardPanel.addContentToPanel();
                }
            }
        });
        mainFrame.setVisible(true);


    }
}


