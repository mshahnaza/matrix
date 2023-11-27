import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] field = new int[7][7];

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("\033[H\033[J");

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                field[i][j] = 0;
            }
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }
}
