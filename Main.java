import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int fieldSize = 7;

        String[][] field = new String[fieldSize][fieldSize];
        String[][] playersField = new String[fieldSize][fieldSize];
        ArrayList<Integer> playersList = new ArrayList<Integer>();

        int horizontal = 1;
        int vertical = 2;

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("\033[H\033[J");

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = "□";
            }
        }

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                playersField[i][j] = "□";
            }
        }

        field = putOneSquareShip(fieldSize, field, vertical, horizontal);

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }

        int shotAmounts = 0;
        int hittedSquares = 0;

        while (hittedSquares != 11) {
            System.out.print("Enter your coordinates: ");
            int shotRowCoordinate = scanner.nextInt();
            int shotColumnCoordinate = scanner.nextInt();
            System.out.println("\033[H\033[J");

            if (field[shotRowCoordinate][shotColumnCoordinate] != "■"
                    && field[shotRowCoordinate][shotColumnCoordinate] != "▦"
                    && field[shotRowCoordinate][shotColumnCoordinate] != "▩") {
                playersField[shotRowCoordinate][shotColumnCoordinate] = "⊡";
                for (int i = 0; i < fieldSize; i++) {
                    for (int j = 0; j < fieldSize; j++) {
                        System.out.print(playersField[i][j] + " ");
                    }
                    System.out.println();
                }
                System.out.print("You missed!");
            } else {
                field[shotRowCoordinate][shotColumnCoordinate] = "*";
                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 2; j++) {
                        int rowSurrounding = shotRowCoordinate + i;
                        int columnSurrounding = shotColumnCoordinate + j;

                        if (rowSurrounding >= 0 && rowSurrounding < 7 && columnSurrounding >= 0
                                && columnSurrounding < 7) {
                            if (field[rowSurrounding][columnSurrounding] == "■") {
                                playersField[shotRowCoordinate][shotColumnCoordinate] = "▧";
                            }
                        }
                    }
                }
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int rowSurrounding = shotRowCoordinate + i;
                        int columnSurrounding = shotColumnCoordinate + j;

                        if (rowSurrounding >= 0 && rowSurrounding < 7 && columnSurrounding >= 0
                                && columnSurrounding < 7) {
                            if (field[rowSurrounding][columnSurrounding] == "▦") {
                                playersField[shotRowCoordinate][shotColumnCoordinate] = "▧";
                            }
                        }
                    }
                }
                if (playersField[shotRowCoordinate][shotColumnCoordinate] == "▧") {
                    for (int i = 0; i < fieldSize; i++) {
                        for (int j = 0; j < fieldSize; j++) {
                            System.out.print(playersField[i][j] + " ");
                        }
                        System.out.println();
                    }
                    System.out.print("You hitted the ship!");
                } else {
                    playersField[shotRowCoordinate][shotColumnCoordinate] = "▨";
                    for (int i = 0; i < fieldSize; i++) {
                        for (int j = 0; j < fieldSize; j++) {
                            System.out.print(playersField[i][j] + " ");
                        }
                        System.out.println();
                    }
                    System.out.print("The ship sank!");
                }
                hittedSquares++;
            }

            shotAmounts++;
        }
        /*
         * playersList.add(shotAmounts);
         * System.out.println("You won!");
         * System.out.println("Do you want to start again?(y/n)");
         * 
         * String yes = "y";
         * String no = "n";
         * String playerChoice = scanner.nextLine();
         * 
         * if(playerChoice == yes) {
         * 
         * }
         * else {
         * Arrays.sort(playersList);
         * for(int k = 0; k < playersList.size(); k++) {
         * System.out.println(playersList.get(k));
         * }
         * }
         */
    }

    public static int chooseShipDirection(int vertical, int horizontal) {
        vertical -= horizontal;
        return (int) (Math.random() * ++vertical) + horizontal;
    }

    public static String[][] putThreeSquareShip(int fieldSize, String[][] field, int horizontal, int vertical) {
        Random random = new Random();

        int rowCoordinate = random.nextInt(fieldSize);
        int columnCoordinate = random.nextInt(fieldSize);
        int shipDirection = chooseShipDirection(vertical, horizontal);

        ArrayList<Integer> shipCoordinates = new ArrayList<Integer>();

        int possibleCoordinates = 0;

        field[rowCoordinate][columnCoordinate] = "■";
        if (shipDirection == horizontal) {
            for (int i = -2; i <= 2; i++) {
                int horizontalShipPossibleCoordinates = columnCoordinate + i;

                if (horizontalShipPossibleCoordinates >= 0 && horizontalShipPossibleCoordinates < 7) {
                    if (field[rowCoordinate][horizontalShipPossibleCoordinates] != "■") {
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
            field[rowCoordinate][secondSquare] = "■";
            field[rowCoordinate][thirdSquare] = "■";

            int[] shipsColumns = { columnCoordinate, secondSquare, thirdSquare };
            Arrays.sort(shipsColumns);

            for (int i = -1; i <= 1; i++) {
                for (int j = -2; j <= 2; j++) {
                    int shipRowSurrounding = rowCoordinate + i;
                    int shipColumnSurrounding = shipsColumns[1] + j;

                    if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 && shipColumnSurrounding >= 0
                            && shipColumnSurrounding < 7) {
                        if (field[shipRowSurrounding][shipColumnSurrounding] != "■") {
                            field[shipRowSurrounding][shipColumnSurrounding] = "▢";
                        }
                    }
                }
            }

        } else {
            for (int i = -2; i <= 2; i++) {
                int verticalShipPossibleCoordinates = rowCoordinate + i;

                if (verticalShipPossibleCoordinates >= 0 && verticalShipPossibleCoordinates < 7) {
                    if (field[verticalShipPossibleCoordinates][columnCoordinate] != "■") {
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
            field[secondSquare][columnCoordinate] = "■";
            field[thirdSquare][columnCoordinate] = "■";

            int[] shipsRows = { rowCoordinate, secondSquare, thirdSquare };
            Arrays.sort(shipsRows);

            for (int i = -2; i <= 2; i++) {
                for (int j = -1; j <= 1; j++) {
                    int shipRowSurrounding = shipsRows[1] + i;
                    int shipColumnSurrounding = columnCoordinate + j;

                    if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 && shipColumnSurrounding >= 0
                            && shipColumnSurrounding < 7) {
                        if (field[shipRowSurrounding][shipColumnSurrounding] != "■") {
                            field[shipRowSurrounding][shipColumnSurrounding] = "▢";
                        }
                    }
                }
            }
        }
        String[][] threeSquareShip = new String[fieldSize][fieldSize];
        threeSquareShip = field;
        return threeSquareShip;
    }

    public static String[][] putTwoSquareShip(int fieldSize, int vertical, int horizontal, String[][] field) {
        Random random = new Random();

        String[][] twoSquareShip = putThreeSquareShip(fieldSize, field, horizontal, vertical);

        for (int k = 0; k < 2; k++) {
            int rowCoordinate = random.nextInt(fieldSize);
            int columnCoordinate = random.nextInt(fieldSize);
            while (twoSquareShip[rowCoordinate][columnCoordinate] == "▢"
                    || twoSquareShip[rowCoordinate][columnCoordinate] == "■") {
                rowCoordinate = random.nextInt(fieldSize);
                columnCoordinate = random.nextInt(fieldSize);
            }
            twoSquareShip[rowCoordinate][columnCoordinate] = "▦";

            int shipDirection = chooseShipDirection(vertical, horizontal);

            ArrayList<Integer> shipCoordinates = new ArrayList<Integer>();

            int possibleCoordinates = 0;

            if (shipDirection == horizontal) {
                for (int i = -1; i <= 1; i++) {
                    int horizontalShipPossibleCoordinates = columnCoordinate + i;

                    if (horizontalShipPossibleCoordinates >= 0 && horizontalShipPossibleCoordinates < 7) {
                        if (twoSquareShip[rowCoordinate][horizontalShipPossibleCoordinates] != "▦"
                                && twoSquareShip[rowCoordinate][horizontalShipPossibleCoordinates] != "▢") {
                            shipCoordinates.add(horizontalShipPossibleCoordinates);
                        }
                    }
                }
                while (shipCoordinates.isEmpty()) {
                    rowCoordinate = random.nextInt(fieldSize);
                    columnCoordinate = random.nextInt(fieldSize);
                    while (twoSquareShip[rowCoordinate][columnCoordinate] == "▢"
                            || twoSquareShip[rowCoordinate][columnCoordinate] == "■"
                            || twoSquareShip[rowCoordinate][columnCoordinate] == "▦") {
                        rowCoordinate = random.nextInt(fieldSize);
                        columnCoordinate = random.nextInt(fieldSize);
                    }
                    twoSquareShip[rowCoordinate][columnCoordinate] = "▦";
                    for (int i = -1; i <= 1; i++) {
                        int horizontalShipPossibleCoordinates = columnCoordinate + i;

                        if (horizontalShipPossibleCoordinates >= 0 && horizontalShipPossibleCoordinates < 7) {
                            if (twoSquareShip[rowCoordinate][horizontalShipPossibleCoordinates] != "▦"
                                    && twoSquareShip[rowCoordinate][horizontalShipPossibleCoordinates] != "▢") {
                                shipCoordinates.add(horizontalShipPossibleCoordinates);
                            }
                        }
                    }
                }
                for (int i = 0; i < shipCoordinates.size(); i++) {
                    possibleCoordinates++;
                }
                int secondSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));

                twoSquareShip[rowCoordinate][secondSquare] = "▦";

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int shipRowSurrounding = rowCoordinate + i;
                        int shipColumnSurrounding = columnCoordinate + j;

                        if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 && shipColumnSurrounding >= 0
                                && shipColumnSurrounding < 7) {
                            if (twoSquareShip[shipRowSurrounding][shipColumnSurrounding] != "▦") {
                                twoSquareShip[shipRowSurrounding][shipColumnSurrounding] = "▢";
                            }
                        }
                    }
                }
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int shipRowSurrounding = rowCoordinate + i;
                        int shipColumnSurrounding = secondSquare + j;

                        if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 && shipColumnSurrounding >= 0
                                && shipColumnSurrounding < 7) {
                            if (twoSquareShip[shipRowSurrounding][shipColumnSurrounding] != "▦") {
                                twoSquareShip[shipRowSurrounding][shipColumnSurrounding] = "▢";
                            }
                        }
                    }
                }
            } else {
                for (int i = -1; i <= 1; i++) {
                    int verticalShipPossibleCoordinates = rowCoordinate + i;

                    if (verticalShipPossibleCoordinates >= 0 && verticalShipPossibleCoordinates < 7) {
                        if (twoSquareShip[verticalShipPossibleCoordinates][columnCoordinate] != "▦"
                                && twoSquareShip[verticalShipPossibleCoordinates][columnCoordinate] != "▢") {
                            shipCoordinates.add(verticalShipPossibleCoordinates);
                        }
                    }
                }
                while (shipCoordinates.isEmpty()) {
                    rowCoordinate = random.nextInt(fieldSize);
                    columnCoordinate = random.nextInt(fieldSize);

                    while (twoSquareShip[rowCoordinate][columnCoordinate] == "▢"
                            || twoSquareShip[rowCoordinate][columnCoordinate] == "■"
                            || twoSquareShip[rowCoordinate][columnCoordinate] == "▦") {
                        rowCoordinate = random.nextInt(fieldSize);
                        columnCoordinate = random.nextInt(fieldSize);
                    }
                    for (int i = -1; i <= 1; i++) {
                        int verticalShipPossibleCoordinates = rowCoordinate + i;

                        if (verticalShipPossibleCoordinates >= 0 && verticalShipPossibleCoordinates < 7) {
                            if (twoSquareShip[verticalShipPossibleCoordinates][columnCoordinate] != "▦"
                                    && twoSquareShip[verticalShipPossibleCoordinates][columnCoordinate] != "▢") {
                                shipCoordinates.add(verticalShipPossibleCoordinates);
                            }
                        }
                    }
                }
                for (int i = 0; i < shipCoordinates.size(); i++) {
                    possibleCoordinates++;
                }
                int secondSquare = shipCoordinates.get(random.nextInt(possibleCoordinates));

                twoSquareShip[secondSquare][columnCoordinate] = "▦";

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int shipRowSurrounding = rowCoordinate + i;
                        int shipColumnSurrounding = columnCoordinate + j;

                        if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 && shipColumnSurrounding >= 0
                                && shipColumnSurrounding < 7) {
                            if (twoSquareShip[shipRowSurrounding][shipColumnSurrounding] != "▦") {
                                twoSquareShip[shipRowSurrounding][shipColumnSurrounding] = "▢";
                            }
                        }
                    }
                }
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int shipRowSurrounding = secondSquare + i;
                        int shipColumnSurrounding = columnCoordinate + j;

                        if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 && shipColumnSurrounding >= 0
                                && shipColumnSurrounding < 7) {
                            if (twoSquareShip[shipRowSurrounding][shipColumnSurrounding] != "▦") {
                                twoSquareShip[shipRowSurrounding][shipColumnSurrounding] = "▢";
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < shipCoordinates.size(); i++) {
                shipCoordinates.remove(i);
            }
        }
        return twoSquareShip;
    }

    public static String[][] putOneSquareShip(int fieldSize, String[][] field, int vertical, int horizontal) {
        Random random = new Random();

        String[][] allShips = putTwoSquareShip(fieldSize, vertical, horizontal, field);

        for (int i = 0; i < 4; i++) {
            int rowCoordinate = random.nextInt(fieldSize);
            int columnCoordinate = random.nextInt(fieldSize);

            while (allShips[rowCoordinate][columnCoordinate] == "▢" || allShips[rowCoordinate][columnCoordinate] == "■"
                    || allShips[rowCoordinate][columnCoordinate] == "▦") {
                rowCoordinate = random.nextInt(fieldSize);
                columnCoordinate = random.nextInt(fieldSize);
            }
            allShips[rowCoordinate][columnCoordinate] = "▩";
            for (int k = -1; k <= 1; k++) {
                for (int j = -1; j <= 1; j++) {
                    int shipRowSurrounding = rowCoordinate + k;
                    int shipColumnSurrounding = columnCoordinate + j;

                    if (shipRowSurrounding >= 0 && shipRowSurrounding < 7 && shipColumnSurrounding >= 0
                            && shipColumnSurrounding < 7) {
                        if (allShips[shipRowSurrounding][shipColumnSurrounding] != "▩") {
                            allShips[shipRowSurrounding][shipColumnSurrounding] = "▢";
                        }
                    }
                }
            }
        }
        return allShips;
    }

}
