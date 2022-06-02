package UNO.GUI;

import UNO.BotMatchControl;
import UNO.GUIStartControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIStart extends JFrame implements ActionListener {
    private GUIStartControl guiStartControl;
    private BotMatchControl BMControl;
    private JPanel basePanel;
    private JLabel jl_anzSpieler, jl_anzDeck, jl_name, jl_difficulty;

    private JSlider js_difficulty;
    private JTextField tf_Spieler, tf_Deck;
    private JTextField tf_Name, tf_difficulty;
    private JButton btn_start, btn_BotMatch;

    private JOptionPane dialog;

    private int width = 300;
    private int height = 360;
    private int x = 20;
    private int y = 20;

    public GUIStart(GUIStartControl pControl) {
        guiStartControl = pControl;

        setTitle("UNO - Startscreen");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        basePanel = new JPanel();
        basePanel.setLayout(null);
        cp.add(basePanel, BorderLayout.CENTER);

        jl_anzSpieler = new JLabel("Anzahl Spieler:");
        jl_anzSpieler.setBounds(x, y, 100, 50);
        basePanel.add(jl_anzSpieler);

        tf_Spieler = new JTextField("2");
        tf_Spieler.setBounds(160, 35, 30, 30);
        basePanel.add(tf_Spieler);

        jl_anzDeck = new JLabel("Anzahl der Decks:");
        jl_anzDeck.setBounds(x, 75, 120, 50);
        basePanel.add(jl_anzDeck);

        tf_Deck = new JTextField("1");
        tf_Deck.setBounds(x + 140, 85, 30, 30);
        tf_Deck.addActionListener(this);
        basePanel.add(tf_Deck);

        jl_name = new JLabel("Name:");
        jl_name.setBounds(x, 130, 120, 50);
        basePanel.add(jl_name);

        tf_Name = new JTextField("Fabian");
        tf_Name.setBounds(x + 140, 130, 70, 30);
        basePanel.add(tf_Name);

        jl_difficulty = new JLabel("Schwierigkeit (1-3):");
        jl_difficulty.setBounds(x, 165, 120, 50);
        basePanel.add(jl_difficulty);

        js_difficulty = new JSlider();
        js_difficulty.setBounds(x + 140, 175, 90, 40);
        js_difficulty.setMinimum(1);
        js_difficulty.setMaximum(3);
        js_difficulty.setMajorTickSpacing(1);
        js_difficulty.setValue(2);
        js_difficulty.setSnapToTicks(true);
        js_difficulty.setPaintLabels(true);
        js_difficulty.setPaintTicks(true);
        basePanel.add(js_difficulty);

        btn_start = new JButton("Start");
        btn_start.setBounds(40, 220, 150, 40);
        btn_start.addActionListener(this);
        basePanel.add(btn_start);

        btn_BotMatch = new JButton("Bot Match");
        btn_BotMatch.setBounds(40, 270, 150, 40);
        btn_BotMatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiStartControl.goToBotMatch();
                dispose();
            }
        });
        basePanel.add(btn_BotMatch);

        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
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
}
