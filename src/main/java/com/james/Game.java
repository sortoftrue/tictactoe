package com.james;

import java.util.Scanner;

public class Game {

    public static void main() {

        char[][] grid = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = ' ';
            }
        }
        printGrid(grid);
        char currPlay = 'X';
        int turnCount = 0;

        while (!checkWin(grid) && turnCount != 9) {

            switch (currPlay) {
                case 'X': {
                    printGrid(grid);
                    makeMove(grid, 'X');
                    currPlay = 'O';
                    break;
                }
                case 'O': {
                    printGrid(grid);
                    makeMove(grid, 'O');
                    currPlay = 'X';
                    break;
                }
                default: {
                    break;
                }
            }
            turnCount++;

        }

        if (turnCount == 9) {
            System.out.println("It's a draw!");
        } else if (currPlay == 'X') {
            System.out.println('O' + " is the winner!");
        } else {
            System.out.println('X' + " is the winner!");
        }

        printGrid(grid);

    }

    public static boolean checkWin(char[][] grid) {

        boolean checkWin = false;

        if (grid[0][0] == grid[0][1] && grid[0][1] == grid[0][2] && grid[0][0] != ' ') {
            checkWin = true;
        } else if (grid[1][0] == grid[1][1] && grid[1][1] == grid[1][2] && grid[1][0] != ' ') {
            checkWin = true;
        } else if (grid[2][0] == grid[2][1] && grid[2][1] == grid[2][2] && grid[2][0] != ' ') {
            checkWin = true;
        } else if (grid[0][0] == grid[1][0] && grid[1][0] == grid[2][0] && grid[0][0] != ' ') {
            checkWin = true;
        } else if (grid[0][1] == grid[1][1] && grid[1][1] == grid[2][1] && grid[0][1] != ' ') {
            checkWin = true;
        } else if (grid[0][2] == grid[1][2] && grid[1][2] == grid[2][2] && grid[0][2] != ' ') {
            checkWin = true;
        } else if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[0][0] != ' ') {
            checkWin = true;
        } else if (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0] && grid[0][2] != ' ') {
            checkWin = true;
        }

        return checkWin;
    }

    public static void printGrid(char[][] grid) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("[" + grid[i][j] + "]");
            }
            System.out.println();
        }

    }

    public static void makeMove(char[][] grid, char player) {

        System.out.println(player + "'s' turn");
        boolean valid = false;
        Integer choice = 0;

        while (!valid) {
            try {
                choice = Integer.parseInt(new Scanner(System.in).next());
                valid = true;

                choice -= 1;
                Integer gridPosX = choice % 3;
                Integer gridPosY = (choice - gridPosX) / 3;

                if (choice > 8 || choice < 0) {
                    System.out.println("Enter 1-9");
                    valid = false;
                } else if (grid[gridPosY][gridPosX] != ' ') {
                    System.out.println("Square Taken");
                    valid = false;
                } else {
                    grid[gridPosY][gridPosX] = player;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid!");
            }
        }

    }
}
