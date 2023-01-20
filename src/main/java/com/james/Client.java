package com.james;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    
    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost",12345)) {
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            DataInputStream is = new DataInputStream(socket.getInputStream());

            boolean isEnd=false;

            //System.out.println(is.readUTF());

            while(!isEnd){

                String received = is.readUTF();
                if(received.contains("draw") || received.contains("winner")){
                    System.out.println(received);
                    isEnd=true;
                    break;
                }
                System.out.println(received);
                String input = new Scanner(System.in).next();
                os.writeUTF(input);
                os.flush();


            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    


}
