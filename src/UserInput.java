import java.util.Scanner;

public class UserInput {
    private Scanner scanner;

    public int inputNrPlayer() {
        System.out.println("Anzahl der Spieler:");
        return verarbeitenEingabe();
    }

    public int inputAnzKarten() {
        System.out.println("Anzahl der Kartensets, die verwendet werden sollen:");
        return verarbeitenEingabe();
    }

    private int verarbeitenEingabe() {
        int eing = 0;
        boolean validInput = false;
        while (!validInput) {
            scanner = new Scanner(System.in);
            try {
                eing = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Das ist leider keine Zahl");
            }
        }
        return eing;
    }


    public int auswahlKarte() {
        System.out.println("Karte auswählen die gelegt werden soll");
        return verarbeitenEingabe() - 1;
    }

    public String auswahlFarbe() {
        System.out.println("Farbe auswählen:");
        String farbe = "";
        boolean validInput = false;
        while (!validInput) {
            scanner = new Scanner(System.in);
            farbe = scanner.nextLine();
            if (farbe.equalsIgnoreCase("Red") || farbe.equalsIgnoreCase("Green") ||
                    farbe.equalsIgnoreCase("Blue") || farbe.equalsIgnoreCase("Yellow")) {
                validInput = true;
            } else {
                System.out.println("Diese Farbe existiert leider nicht");
            }
        }

        return farbe;

    }

    public String auswahlName(){
        System.out.println("Name auswählen");
        String name = "";
        scanner = new Scanner(System.in);
        name = scanner.nextLine();
        return name;
    }

    public boolean eingabeUno(){
        scanner = new Scanner(System.in);
        if (scanner.nextLine().equalsIgnoreCase("uno")){
            return true;
        }else{
            return false;
        }

    }

    public int auswahlDifficulty(){
        System.out.println("Wähle aus wie schwer die Bots sein sollen (1-3)");
        return verarbeitenEingabe()-1;
    }
}
