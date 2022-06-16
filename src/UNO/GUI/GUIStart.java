package UNO.GUI;

import UNO.Control.GUIStartControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIStart extends JFrame implements ActionListener {
    private GUIStartControl guiStartControl;

    private Container cp;
    private JPanel firstPanel, secondPanel;
    private JLabel jl_anzSpieler, jl_anzDeck, jl_name, jl_difficulty;

    private JSlider js_difficulty;
    private JTextField tf_Spieler, tf_Deck;
    private JTextField tf_Name;
    private JButton btn_PlayerMatch, btn_BotMatch, btn_Start;

    private JOptionPane dialog;

    private int width = 300;
    private int height = 360;
    private int x = 20;
    private int y = 20;

    public GUIStart(GUIStartControl pControl) {
        guiStartControl = pControl;

        setTitle("UNO - Startscreen");
        cp = getContentPane();
        cp.setLayout(new BorderLayout());

        firstPanel = new JPanel();
        firstPanel.setLayout(null);
        cp.add(firstPanel);

        JLabel imgLabel = new JLabel(resizeImage(new ImageIcon("src/img/UNO_Logo.png"), 160, 112));
        imgLabel.setBounds(width / 2 - 160 / 2, 40, 160, 112);
        firstPanel.add(imgLabel);

        btn_PlayerMatch = new JButton("Player Match");
        btn_PlayerMatch.setSize(150, 40);
        btn_PlayerMatch.setLocation(width / 2 - btn_PlayerMatch.getWidth() / 2, 220);
        btn_PlayerMatch.addActionListener(e -> openSecondPanel());
        firstPanel.add(btn_PlayerMatch);

        btn_BotMatch = new JButton("Bot Match");
        btn_BotMatch.setSize(150, 40);
        btn_BotMatch.setLocation(width / 2 - btn_BotMatch.getWidth() / 2, 270);
        btn_BotMatch.addActionListener(e -> {
            guiStartControl.goToBotMatch();
            dispose();
        });
        firstPanel.add(btn_BotMatch);

        secondPanel = new JPanel();
        secondPanel.setLayout(null);

        jl_anzSpieler = new JLabel("Anzahl Spieler:");
        jl_anzSpieler.setBounds(x, y, 100, 50);
        secondPanel.add(jl_anzSpieler);

        tf_Spieler = new JTextField("2");
        tf_Spieler.setBounds(160, 35, 30, 30);
        secondPanel.add(tf_Spieler);

        jl_anzDeck = new JLabel("Anzahl der Decks:");
        jl_anzDeck.setBounds(x, 75, 120, 50);
        secondPanel.add(jl_anzDeck);

        tf_Deck = new JTextField("1");
        tf_Deck.setBounds(x + 140, 85, 30, 30);
        tf_Deck.addActionListener(this);
        secondPanel.add(tf_Deck);

        jl_name = new JLabel("Name:");
        jl_name.setBounds(x, 130, 120, 50);
        secondPanel.add(jl_name);

        tf_Name = new JTextField("Fabian");
        tf_Name.setBounds(x + 140, 130, 70, 30);
        secondPanel.add(tf_Name);

        jl_difficulty = new JLabel("Schwierigkeit (1-3):");
        jl_difficulty.setBounds(x, 165, 120, 50);
        secondPanel.add(jl_difficulty);

        js_difficulty = new JSlider();
        js_difficulty.setBounds(x + 140, 175, 90, 40);
        js_difficulty.setMinimum(1);
        js_difficulty.setMaximum(3);
        js_difficulty.setMajorTickSpacing(1);
        js_difficulty.setValue(2);
        js_difficulty.setSnapToTicks(true);
        js_difficulty.setPaintLabels(true);
        js_difficulty.setPaintTicks(true);
        secondPanel.add(js_difficulty);

        btn_Start = new JButton("Start");
        btn_Start.setSize(150, 40);
        btn_Start.setLocation(width/2 - 150/2,250);
        btn_Start.addActionListener(this);
        secondPanel.add(btn_Start);

        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            guiStartControl.setAnzSpieler(Integer.parseInt(tf_Spieler.getText()));
            guiStartControl.setDeck(Integer.parseInt(tf_Deck.getText()));
            guiStartControl.setName(tf_Name.getText());
            guiStartControl.setDifficulty(js_difficulty.getValue() - 1);
            dispose();
            guiStartControl.start();
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Alle Felder müssen korrekt ausgefüllt sein");
        }
    }

    private void openSecondPanel() {
        cp.remove(firstPanel);
        cp.add(secondPanel);
        cp.revalidate();
        cp.repaint();
    }

    private ImageIcon resizeImage(ImageIcon originalImage, int targetWidth, int targetHeight) {
        return new ImageIcon(originalImage.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT));
    }
}
