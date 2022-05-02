package util;

import UNO.Kartenlogik.Bot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Tracking {

    private FileWriter statWriter, weightWriter;
    private BufferedReader weightReader;

    public Tracking() {
        try {
            statWriter = new FileWriter("src/stattrack.txt", true);
            weightWriter = new FileWriter("src/weights.txt", true);
            weightReader = new BufferedReader(new FileReader("src/weights.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void trackStats(Bot[] bots) {


        double verhaeltnis = (double) bots[0].getRundenGewonnen() / bots[1].getRundenGewonnen();


        String text = "-----------------------------------------------------------------------------------------------" +
                "---------------------------------------------------------------------------------\n" +
                "farbwechselFarbeMax : " + bots[1].getFarbwechselFarbeMax() + ", " +
                "farbwechselFarbeMin: " + bots[1].getFarbwechselFarbeMin() + ", " +
                "nextPlayerAvoidance: " + bots[1].getNextPlayerAvoidance() + ", " +
                "plusTwoWhen: " + bots[1].getPlusTwoWhen() + ", " +
                "farbwechselWhen: " + bots[1].getFarbwechselWhen() + ", " +
                "gewonnenBot0: " + bots[0].getRundenGewonnen() + ", " +
                "gewonnenBot1: " + bots[1].getRundenGewonnen() + ", " +
                "Verhältnis: " + verhaeltnis + "\n";

        try {
            statWriter.append(text);
            statWriter.flush();
            System.out.println("Successfully wrote to file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeWriter() {
        try {
            statWriter.close();
        } catch (IOException e) {
            System.out.println("Writer konnte nicht geschlossen werden");
        }
    }

    public void setWeights(int farbwechselFarbeMax, int farbwechselFarbeMin, int nextPlayerAvoidance, int plusTwoWhen,
                           int farbwechselWhen) {
        try {
            weightWriter.write(farbwechselFarbeMax + "," + farbwechselFarbeMin + "," + nextPlayerAvoidance + "," +
                    plusTwoWhen + "," + farbwechselWhen);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] getWeights() {
        int[] weights = new int[5];
        try {
            StringBuilder sb = new StringBuilder();
            String line = weightReader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = weightReader.readLine();
            }
            String everything = sb.toString();
            String[] split = sb.toString().split(",");
            int i = 0;
            for (String string : split) {
                if (!string.equals(",")) {
                    weights[i] = Integer.parseInt(string);
                    i++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return weights;
    }
}
