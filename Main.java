import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int fieldSize = 7;

        int[][] field = new int[fieldSize][fieldSize];

        int horizontal = 1;
        int vertical = 2;

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("\033[H\033[J");

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = 8;
            }
        }

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        putThreeSquaresShip(fieldSize, field, horizontal, vertical);
        System.out.println();
        /*
         * for (int i = 0; i < 2; i++) {
         * putTwoSquareShip(fieldSize, vertical, horizontal, field);
         * System.out.println();
         * }
         * System.out.println();
         * 
         * for (int i = 0; i <= 4; i++) {
         * putOneSquareShip(fieldSize, field);
         * System.out.println();
         * }
         */
    }

    public static int chooseShipDirection(int vertical, int horizontal) {
        vertical -= horizontal;
        return (int) (Math.random() * ++vertical) + horizontal;
    }

    public static void putThreeSquaresShip(int fieldSize, int[][] field, int horizontal, int vertical) {
        Random random = new Random();

        int rowCoordinate = random.nextInt(fieldSize);
        int columnCoordinate = random.nextInt(fieldSize);
        int shipDirection = chooseShipDirection(vertical, horizontal);

        ArrayList<Integer> shipCoordinates = new ArrayList<Integer>();

        int possibleCoordinates = 0;

        field[rowCoordinate][columnCoordinate] = 1;
        if (shipDirection == horizontal) {
            for (int i = -2; i <= 2; i++) {
                int horizontalShipPossibleCoordinates = columnCoordinate + i;

                if (horizontalShipPossibleCoordinates >= 0 && horizontalShipPossibleCoordinates < 7) {
                    if (field[rowCoordinate][horizontalShipPossibleCoordinates] != 1) {
                        shipCoordinates.add(horizontalShipPossibleCoordinates);
                    }
                }
            }
            for (int i = 0; i < shipCoordinates.size(); i++) {
                possibleCoordinates++;
            }
            int secondSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));
            int thirdSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));
            while (secondSquare == thirdSquare || (secondSquare - thirdSquare > 2 || secondSquare - thirdSquare < -2)) {
                secondSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));
                thirdSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));
            }
            field[rowCoordinate][secondSquare] = 1;
            field[rowCoordinate][thirdSquare] = 1;

            int[] shipsColumns = { columnCoordinate, secondSquare, thirdSquare };
            Arrays.sort(shipsColumns);

            for (int i = -1; i <= 1; i++) {
                for (int j = -2; j <= 2; j++) {
                    int shipRowSurrounding = rowCoordinate + i;
                    int shipColumnSurrounding = shipsColumns[1] + j;

                    if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 && shipColumnSurrounding >= 0
                            && shipColumnSurrounding < 7) {
                        if (field[shipRowSurrounding][shipColumnSurrounding] != 1) {
                            field[shipRowSurrounding][shipColumnSurrounding] = 0;
                        }
                    }
                }
            }

        } else {
            for (int i = -2; i <= 2; i++) {
                int verticalShipPossibleCoordinates = rowCoordinate + i;

                if (verticalShipPossibleCoordinates >= 0 && verticalShipPossibleCoordinates < 7) {
                    if (field[verticalShipPossibleCoordinates][columnCoordinate] != 1) {
                        shipCoordinates.add(verticalShipPossibleCoordinates);
                    }
                }
            }
            for (int i = 0; i < shipCoordinates.size(); i++) {
                possibleCoordinates++;
            }
            int secondSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));
            int thirdSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));
            while (secondSquare == thirdSquare || (secondSquare - thirdSquare > 2 || secondSquare - thirdSquare < -2)) {
                secondSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));
                thirdSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));
            }
            field[secondSquare][columnCoordinate] = 1;
            field[thirdSquare][columnCoordinate] = 1;

            int[] shipsRows = { rowCoordinate, secondSquare, thirdSquare };
            Arrays.sort(shipsRows);

            for (int i = -2; i <= 2; i++) {
                for (int j = -1; j <= 1; j++) {
                    int shipRowSurrounding = shipsRows[1] + i;
                    int shipColumnSurrounding = columnCoordinate + j;

                    if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 && shipColumnSurrounding >= 0
                            && shipColumnSurrounding < 7) {
                        if (field[shipRowSurrounding][shipColumnSurrounding] != 1) {
                            field[shipRowSurrounding][shipColumnSurrounding] = 0;
                        }
                    }
                }
            }

        }
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }
    /*
     * public static void putTwoSquareShip(int fieldSize, int vertical, int
     * horizontal, int[][] field) {
     * Random random = new Random();
     * 
     * int rowCoordinate = random.nextInt(fieldSize);
     * int columnCoordinate = random.nextInt(fieldSize);
     * while (field[rowCoordinate][columnCoordinate] == 0) {
     * rowCoordinate = random.nextInt(fieldSize);
     * columnCoordinate = random.nextInt(fieldSize);
     * }
     * int shipDirection = chooseShipDirection(vertical, horizontal);
     * 
     * ArrayList<Integer> shipCoordinates = new ArrayList<Integer>();
     * 
     * int possibleCoordinates = 0;
     * 
     * field[rowCoordinate][columnCoordinate] = 1;
     * if (shipDirection == horizontal) {
     * for (int i = -1; i <= 1; i++) {
     * int horizontalShipPossibleCoordinates = columnCoordinate + i;
     * 
     * if (horizontalShipPossibleCoordinates >= 0 &&
     * horizontalShipPossibleCoordinates < 7) {
     * if (field[rowCoordinate][horizontalShipPossibleCoordinates] != 1) {
     * shipCoordinates.add(horizontalShipPossibleCoordinates);
     * }
     * }
     * }
     * for (int i = 0; i < shipCoordinates.size(); i++) {
     * possibleCoordinates++;
     * }
     * int secondSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));
     * 
     * field[rowCoordinate][secondSquare] = 1;
     * 
     * for (int i = -1; i <= 1; i++) {
     * for (int j = -1; j <= 1; j++) {
     * int shipRowSurrounding = rowCoordinate + i;
     * int shipColumnSurrounding = columnCoordinate + j;
     * 
     * if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 &&
     * shipColumnSurrounding >= 0
     * && shipColumnSurrounding < 7) {
     * if (field[shipRowSurrounding][shipColumnSurrounding] != 1) {
     * field[shipRowSurrounding][shipColumnSurrounding] = 0;
     * }
     * }
     * }
     * }
     * for (int i = -1; i <= 1; i++) {
     * for (int j = -1; j <= 1; j++) {
     * int shipRowSurrounding = rowCoordinate + i;
     * int shipColumnSurrounding = secondSquare + j;
     * 
     * if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 &&
     * shipColumnSurrounding >= 0
     * && shipColumnSurrounding < 7) {
     * if (field[shipRowSurrounding][shipColumnSurrounding] != 1) {
     * field[shipRowSurrounding][shipColumnSurrounding] = 0;
     * }
     * }
     * }
     * }
     * 
     * } else {
     * for (int i = -1; i <= 1; i++) {
     * int verticalShipPossibleCoordinates = rowCoordinate + i;
     * 
     * if (verticalShipPossibleCoordinates >= 0 && verticalShipPossibleCoordinates <
     * 7) {
     * if (field[verticalShipPossibleCoordinates][columnCoordinate] != 1) {
     * shipCoordinates.add(verticalShipPossibleCoordinates);
     * }
     * }
     * }
     * for (int i = 0; i < shipCoordinates.size(); i++) {
     * possibleCoordinates++;
     * }
     * int secondSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));
     * 
     * field[secondSquare][columnCoordinate] = 1;
     * 
     * for (int i = -1; i <= 1; i++) {
     * for (int j = -1; j <= 1; j++) {
     * int shipRowSurrounding = rowCoordinate + i;
     * int shipColumnSurrounding = columnCoordinate + j;
     * 
     * if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 &&
     * shipColumnSurrounding >= 0
     * && shipColumnSurrounding < 7) {
     * if (field[shipRowSurrounding][shipColumnSurrounding] != 1) {
     * field[shipRowSurrounding][shipColumnSurrounding] = 0;
     * }
     * }
     * }
     * }
     * for (int i = -1; i <= 1; i++) {
     * for (int j = -1; j <= 1; j++) {
     * int shipRowSurrounding = secondSquare + i;
     * int shipColumnSurrounding = columnCoordinate + j;
     * 
     * if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 &&
     * shipColumnSurrounding >= 0
     * && shipColumnSurrounding < 7) {
     * if (field[shipRowSurrounding][shipColumnSurrounding] != 1) {
     * field[shipRowSurrounding][shipColumnSurrounding] = 0;
     * }
     * }
     * }
     * }
     * 
     * }
     * for (int i = 0; i < fieldSize; i++) {
     * for (int j = 0; j < fieldSize; j++) {
     * System.out.print(field[i][j] + " ");
     * }
     * System.out.println();
     * }
     * }
     * 
     * public static void putOneSquareShip(int fieldSize, int[][] field) {
     * Random random = new Random();
     * 
     * int rowCoordinate = random.nextInt(fieldSize);
     * int columnCoordinate = random.nextInt(fieldSize);
     * 
     * field[rowCoordinate][columnCoordinate] = 1;
     * while (field[rowCoordinate][columnCoordinate] == 0) {
     * rowCoordinate = random.nextInt(fieldSize);
     * columnCoordinate = random.nextInt(fieldSize);
     * }
     * for (int i = -1; i <= 1; i++) {
     * for (int j = -1; j <= 1; j++) {
     * int shipRowSurrounding = rowCoordinate + i;
     * int shipColumnSurrounding = columnCoordinate + j;
     * 
     * if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 &&
     * shipColumnSurrounding >= 0
     * && shipColumnSurrounding < 7) {
     * if (field[shipRowSurrounding][shipColumnSurrounding] != 1) {
     * field[shipRowSurrounding][shipColumnSurrounding] = 0;
     * }
     * }
     * }
     * }
     * for (int i = 0; i < fieldSize; i++) {
     * for (int j = 0; j < fieldSize; j++) {
     * System.out.print(field[i][j] + " ");
     * }
     * System.out.println();
     * }
     * }
     */
}
