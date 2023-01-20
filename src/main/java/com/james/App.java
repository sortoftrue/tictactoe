package com.james;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public final class App {
    public static void main(String[] args) {
        
        //Game.main();

        try (ServerSocket ss = new ServerSocket(12345)) {

            while(true){
                System.out.println("Waiting new conn.");
                Socket socket = ss.accept();
                System.out.println("Connected. Start new thread");

                Thread instance = new Thread(new GameServer(socket));
                instance.start();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
