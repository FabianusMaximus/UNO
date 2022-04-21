package UNO.GUI;

import UNO.BotMatchControl;
import util.SwingCalculation;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIBotMatch extends JFrame {
    private BotMatchControl bMControl;

    private Container cp;
    private JPanel jp_settings, jp_sliders, jp_game, jp_statistics;

    private JLabel jl_Header, jl_anzBots;

    private JLabel[] jl_Bots, jl_statBots;
    private JSlider[] js_difficulty;

    private JSlider js_anzBots;

    private JButton btn_simulate;

    private JTextArea jta_Verlauf;

    private JTextField[] tf_stats;
    private int width, height;

    private Font settingsFont, areaFont;

    public GUIBotMatch(BotMatchControl botMatchControl) {
        this.bMControl = botMatchControl;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) ((int) size.getWidth() - size.getWidth() / 5);
        height = (int) ((int) size.getHeight() - size.getHeight() / 5);
        cp = this.getContentPane();
        cp.setLayout(null);


        jp_settings = new JPanel();
        jp_settings.setSize(width / 3, height);
        jp_settings.setLocation(0, 0);
        jp_settings.setLayout(null);
        jp_settings.setBorder(new LineBorder(Color.green));
        cp.add(jp_settings);

        settingsFont = new Font("Arial", Font.PLAIN, jp_settings.getWidth() / 30);

        jp_game = new JPanel();
        jp_game.setSize(width - width / 3, height);
        jp_game.setLocation(width / 3, 0);
        jp_game.setLayout(null);
        jp_game.setBorder(new LineBorder(Color.blue));
        cp.add(jp_game);

        jl_Header = new JLabel("Uno Bot Match");
        jl_Header.setSize(jp_settings.getWidth() / 2, jp_settings.getHeight() / 20);
        jl_Header.setLocation(jp_settings.getWidth() / 2 - jl_Header.getWidth() / 2, jp_settings.getHeight() / 40);
        jl_Header.setFont(new Font("Arial", Font.PLAIN, (int) (jl_Header.getHeight() / 1.2)));
        jl_Header.setVerticalAlignment(SwingConstants.CENTER);
        jl_Header.setHorizontalAlignment(SwingConstants.CENTER);
        jl_Header.setBorder(new LineBorder(Color.red));
        jp_settings.add(jl_Header);

        jp_sliders = new JPanel();
        jp_sliders.setSize(
                jp_settings.getWidth() - 40,
                (int) (jp_settings.getHeight() * 0.75)
        );
        jp_sliders.setLocation(
                jp_settings.getWidth() / 2 - jp_sliders.getWidth() / 2,
                (int) (jp_settings.getHeight() - jp_settings.getHeight() * 0.85)
        );
        jp_sliders.setLayout(new GridLayout(5, 2, 5, 5));
        jp_sliders.setBorder(new LineBorder(Color.red));
        jp_settings.add(jp_sliders);

        Font settingsFont = new Font("Arial", Font.PLAIN, jp_settings.getWidth() / 30);

        js_difficulty = new JSlider[4];
        jl_Bots = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            js_difficulty[i] = new JSlider();
            js_difficulty[i].setMinimum(1);
            js_difficulty[i].setMaximum(3);
            js_difficulty[i].setMajorTickSpacing(1);
            js_difficulty[i].setValue(2);
            js_difficulty[i].setFont(settingsFont);
            js_difficulty[i].setSnapToTicks(true);
            js_difficulty[i].setPaintLabels(true);
            js_difficulty[i].setPaintTicks(true);
            jl_Bots[i] = new JLabel("Schwierigkeit von Bot " + i + ":");
            jl_Bots[i].setFont(settingsFont);
            jl_Bots[i].setHorizontalAlignment(SwingConstants.CENTER);
            jl_Bots[i].setVerticalAlignment(SwingConstants.CENTER);

            jp_sliders.add(jl_Bots[i]);
            jp_sliders.add(js_difficulty[i]);
        }

        jl_anzBots = new JLabel("Anzahl Bots: ");
        jl_anzBots.setFont(settingsFont);
        jl_anzBots.setHorizontalAlignment(SwingConstants.CENTER);
        jl_anzBots.setVerticalAlignment(SwingConstants.CENTER);
        jp_sliders.add(jl_anzBots);

        js_anzBots = new JSlider();
        js_anzBots = new JSlider();
        js_anzBots.setMinimum(2);
        js_anzBots.setMaximum(4);
        js_anzBots.setMajorTickSpacing(1);
        js_anzBots.setValue(2);
        js_anzBots.setFont(settingsFont);
        js_anzBots.setSnapToTicks(true);
        js_anzBots.setPaintLabels(true);
        js_anzBots.setPaintTicks(true);
        jp_sliders.add(js_anzBots);

        btn_simulate = new JButton("Simulate");
        btn_simulate.setSize((int) (jp_settings.getWidth() * 0.75), 50);
        btn_simulate.setLocation(SwingCalculation.centerX(jp_settings, btn_simulate), (int) (height * 0.91));
        btn_simulate.setFont(settingsFont);
        btn_simulate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botMatchControl.clickSimulate();
            }
        });
        jp_settings.add(btn_simulate);

        areaFont = new Font("Arial", Font.PLAIN, jp_settings.getWidth() / 30);

        jta_Verlauf = new JTextArea("-----------------Verlauf des Spiels-----------------\n");
        jta_Verlauf.setFont(areaFont);
        jta_Verlauf.setEditable(false);
        JScrollPane scroll = new JScrollPane(jta_Verlauf);
        scroll.setSize((int) (jp_game.getWidth() * 0.85), (int) (jp_game.getHeight() * 0.5));
        scroll.setLocation(SwingCalculation.centerX(jp_game, scroll), (int) (jp_game.getHeight() * 0.05));
        jp_game.add(scroll);

        jp_statistics = new JPanel();
        jp_statistics.setSize((int) (jp_game.getWidth() * 0.85), (int) (jp_game.getHeight() * 0.3));
        jp_statistics.setLocation(SwingCalculation.centerX(jp_game, jp_statistics), (int) (jp_game.getHeight() * 0.6));
        jp_statistics.setLayout(new GridLayout(2, 2, 5, 5));
        jp_statistics.setBorder(new LineBorder(Color.red));
        jp_game.add(jp_statistics);

        tf_stats = new JTextField[4];
        jl_statBots = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            tf_stats[i] = new JTextField();
            tf_stats[i].setEditable(false);
            tf_stats[i].setFont(settingsFont);

            jl_statBots[i] = new JLabel("Runden von Bot " + i + " gewonnen:");
            jl_statBots[i].setFont(settingsFont);
            jl_statBots[i].setHorizontalAlignment(SwingConstants.CENTER);
            jl_statBots[i].setVerticalAlignment(SwingConstants.CENTER);

            jp_statistics.add(jl_statBots[i]);
            jp_statistics.add(tf_stats[i]);

        }


        setTitle("BotMatch");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void setButtonEnabled(boolean enabled) {
        btn_simulate.setEnabled(enabled);
    }

    public void appendToVerlauf(String text) {
        jta_Verlauf.append(text + "\n");
    }

    public int getValueSlider(int index) {
        return js_difficulty[index].getValue();
    }

    public int getAnzBots() {
        return js_anzBots.getValue();
    }
}
