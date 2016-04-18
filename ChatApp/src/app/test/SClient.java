package app.test;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by Azz_B on 17/04/2016.
 */
public class SClient {

    public static void main(String[] args){

        Socket socket = null;
        try {

            socket = new Socket("localhost", 3333);
            System.out.println("connection to server at port " + socket.getPort());
            //BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream output = new BufferedOutputStream(socket.getOutputStream());
            String msg = "from azz";
            output.write(msg.getBytes());
            output.flush();

            String str = "";
            byte[] b = new byte[8];
            while(in.read(b) != -1){

                System.out.println("msg is : " + new String(b, StandardCharsets.UTF_8));
                if(str.equals("exit")) break;

            }



        } catch (IOException e) {
            System.out.println("problem connection");
            e.printStackTrace();
        }finally {
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
