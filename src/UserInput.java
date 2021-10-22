import java.util.Scanner;

public class UserInput{
    private Scanner scanner;

    public int inputNrPlayer(){
        System.out.println("Anzahl der Spieler:");
        int anz = 0;
        scanner = new Scanner(System.in);
        anz = Integer.parseInt(scanner.nextLine());
        return anz;
    }

    public int inputAnzKarten(){
        System.out.println("Anzahl der Karten, die verwendet werden sollen:");
        int anz = 0;
        scanner = new Scanner(System.in);
        anz = Integer.parseInt(scanner.nextLine());
        return anz;
    }

    public int auswahlKarte(){
        System.out.println("Karte auswählen die gelegt werden soll");
        int nr = 0;
        scanner = new Scanner(System.in);
        nr = Integer.parseInt(scanner.nextLine());
        return nr - 1;
    }
}
