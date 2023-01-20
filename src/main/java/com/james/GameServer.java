package com.james;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class GameServer implements Runnable {

    Socket clientSocket;

    GameServer(Socket socket) {
        clientSocket = socket;
    }

    public void run() {

        try (DataInputStream is = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()))) {
            DataOutputStream os = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

            char[][] grid = new char[3][3];

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    grid[i][j] = ' ';
                }
            }

            //os.writeUTF(printGrid(grid));
            //os.flush();

            char currPlay = 'X';
            int turnCount = 0;

            while (!checkWin(grid) && turnCount != 9) {

                switch (currPlay) {
                    case 'X': {
                        os.writeUTF(printGrid(grid) + "X's turn!");
                        os.flush();
                        makeMove(grid, 'X', is, os);
                        currPlay = 'O';
                        break;
                    }
                    case 'O': {
                        // os.writeUTF(printGrid(grid)+"O's turn!");
                        // os.flush();
                        compMove(grid, 'O');
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
                os.writeUTF("It's a draw!\n"+printGrid(grid));
                os.flush();
            } else if (currPlay == 'X') {
                os.writeUTF('O' + " is the winner!\n"+printGrid(grid));
                os.flush();
            } else {
                os.writeUTF('X' + " is the winner!\n"+printGrid(grid));
                os.flush();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

    public static String printGrid(char[][] grid) {

        String output = "";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                output += ("[" + grid[i][j] + "]");
            }
            output += "\n";
        }

        return output;

    }

    public static void compMove(char[][] grid, char player) {

        boolean valid = false;
        Integer choice = 0;

        while (!valid) {
            System.out.println("Computer's turn!");
            Random random = new Random();
            choice = random.nextInt(9) + 1;

            choice -= 1;
            Integer gridPosX = choice % 3;
            Integer gridPosY = (choice - gridPosX) / 3;

            if (choice > 8 || choice < 0) {
                valid = false;
            } else if (grid[gridPosY][gridPosX] != ' ') {
                valid = false;
            } else {
                grid[gridPosY][gridPosX] = player;
                valid = true;
            }

        }

    }

    public static void makeMove(char[][] grid, char player, DataInputStream is, DataOutputStream os) {

        // System.out.println(player + "'s' turn");
        boolean valid = false;
        Integer choice = 0;

        while (!valid) {
            try {
                System.out.println("Waiting for input");
                choice = Integer.parseInt(is.readUTF());
                valid = true;

                choice -= 1;
                Integer gridPosX = choice % 3;
                Integer gridPosY = (choice - gridPosX) / 3;

                if (choice > 8 || choice < 0) {
                    os.writeUTF("Enter 1-9");
                    os.flush();
                    valid = false;
                } else if (grid[gridPosY][gridPosX] != ' ') {
                    os.writeUTF("Square Taken");
                    os.flush();
                    valid = false;
                } else {
                    grid[gridPosY][gridPosX] = player;
                }

            } catch (NumberFormatException | IOException e) {
                try {
                    os.writeUTF("Invalid!");
                    os.flush();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }

    }
}
