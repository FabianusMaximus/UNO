package util;

import java.io.FileWriter;
import java.io.IOException;

public class Tracking {

    private FileWriter myWriter;

    public Tracking() {
        try {
            myWriter = new FileWriter("src/stattrack.txt", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void trackStats(int farbwechselFarbeMax, int farbwechselFarbeMin, int nextPlayerAvoidance,
                           int plusTwoWhen, int farbwechselWhen, int gewonnenBot0, int gewonnenBot1) {

        double verhaeltnis = (double) gewonnenBot0 / gewonnenBot1;
        String text = "-----------------------------------------------------------------------------------------------" +
                "---------------------------------------------------------------------------------\n" +
                "farbwechselFarbeMax : " + farbwechselFarbeMax + ", " +
                "farbwechselFarbeMin: " + farbwechselFarbeMin + ", " +
                "nextPlayerAvoidance: " + nextPlayerAvoidance + ", " +
                "plusTwoWhen: " + plusTwoWhen + ", " +
                "farbwechselWhen: " + farbwechselWhen + ", " +
                "gewonnenBot0: " + gewonnenBot0 + ", " +
                "gewonnenBot1: " + gewonnenBot1 + ", " +
                "Verhältnis: " + verhaeltnis + "\n";

        try {
            myWriter.append(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeWriter(){
        try {
            myWriter.flush();
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Tracking cock = new Tracking();
        for (int i = 0; i < 100; i++) {
            cock.trackStats(1, 2, 3, 4, 5,
                    6, 8);
        }
        cock.closeWriter();
    }
}
