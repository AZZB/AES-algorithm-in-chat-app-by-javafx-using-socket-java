package app.background;

import app.cipher.Crypt;
import app.cipher.Decrypt;
import app.cipher.Manager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Azz_B on 27/02/2016.
 */
public class TaskServer implements Runnable{

    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private BufferedReader input = null;
    private BufferedOutputStream output = null;

    private MsgGet msgGetCaller;

    public TaskServer(){
        /*try {
            serverSocket = new ServerSocket(9996);
            serverSocket.setSoTimeout(0);

        } catch (IOException e) {


        }*/
    }

    public void setMsgGetCaller(MsgGet caller){
        this.msgGetCaller = caller;
    }



    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(3333);
            System.out.println("starting server at port " + serverSocket.getLocalPort());
            serverSocket.setSoTimeout(0);
            clientSocket  = serverSocket.accept();
            //input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream());
            output = new BufferedOutputStream(clientSocket.getOutputStream());
            String str = "";

            int read;
            byte[] b = new byte[1024];
            while((read = in.read(b)) != -1){
                str = new String(b).substring(0, read - 8);
                System.out.println("len is : " + str.length());
                System.out.println("msg is : " + str);
                if(str.equals("exit")) break;
                String clairTxt = new Decrypt("azzeddine1994pp9").decrypt(str);
                msgGetCaller.getMsgFromTask(clairTxt);

            }

            /*while((str = input.readLine()) != null){

                if(str.equals("exit")) break;
                msgGetCaller.getMsgFromTask(str);☺
            }*/

            System.out.println("client finished and close");


        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("close server ..ok");
        }finally {
            if(clientSocket != null){
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public void writeData(String data){
        try {//ýÏ7«zÿÜ"·
            //data += "\n";
            System.out.println("send: " + data);
            data = Manager.adapteStr(data);
            System.out.println("len is : " + data.length());
            data = new Crypt("azzeddine1994pp9").crypt(data);
            System.out.print("send data taskCrypt: ");
            System.out.println(data);
            System.out.println("len is crypt : " + data.length());
            output.write(data.getBytes());
            //output.write("hi from server".getBytes());
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
           /* if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }

    public void closeServer(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("close server ..ok");
        }
    }



    private void getPrimeNumber(){
        double z =  Math.random() * 1000000 ;
        double x =  Math.random() * z ;
        int y = (int)x;
    }

    //interface for send msg
    public interface MsgGet{
        void getMsgFromTask(String msg);
    }

}
