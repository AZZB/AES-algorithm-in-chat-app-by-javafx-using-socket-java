package app.background;

import app.cipher.Crypt;
import app.cipher.Decrypt;
import app.cipher.Manager;

import java.io.*;
import java.net.Socket;

/**
 * Created by Azz_B on 17/04/2016.
 */
public class TaskClient  implements Runnable {


    private MsgGet msgGetCaller;

    private Socket socket = null;
    //private BufferedReader input = null;
    private BufferedOutputStream output = null;


    public void setMsgGetCaller(MsgGet caller){
        this.msgGetCaller = caller;
    }

    @Override
    public void run() {


        try {
            socket = new Socket("127.0.0.1", 3333);
            System.out.println("connection to server at port " + socket.getPort());
            //input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
            String str = "";
            int read;
            byte[] b = new byte[1024];
            while((read = in.read(b)) != -1){

                str = new String(b).substring(0, read);
                System.out.println("len is : " + str.length());
                System.out.println("msg is : " + str);
                if(str.equals("exit")) break;
                String clairTxt = new Decrypt("azzeddine1994pp9").decrypt(str);
                msgGetCaller.getMsgFromTask(clairTxt);

            }


            /*while((str = input.readLine()) != null){

                if(str.equals("exit")) break;
                msgGetCaller.getMsgFromTask(str);
            }*/

            System.out.println("server finished and close");

        } catch (IOException e) {
            System.out.println("problem connection to server");
            e.printStackTrace();
        }finally {
            /*if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }*/
        }
    }

    public void writeData(String data){
        try {
            System.out.println("send: " + data);
            data = Manager.adapteStr(data);
            System.out.println("len is : " + data.length());

            data = new Crypt("azzeddine1994pp9").crypt(data);
            System.out.print("send data taskCrypt: ");
            System.out.println(data);
            System.out.println("len crypt is : " + data.length());
            output.write(data.getBytes());
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




    //interface for send msg
    public interface MsgGet{
        void getMsgFromTask(String msg);
    }
}
