package app.test;

import app.cipher.Crypt;
import app.cipher.Decrypt;
import app.cipher.Manager;

/**
 * Created by Azz_B on 17/04/2016.
 */
public class Test2 {

    public static void main(String[] args){
        System.out.println("hello");
        String k = "azzeddine1994pp9";
        String m = /*"hello how are you";/*/"let me show you how to build best app in the word";
        String str = Manager.adapteStr(m);
        System.out.println(str.length());
        String cipher = new Crypt(k).crypt(m);
        System.out.println(cipher);
        System.out.println(new Decrypt(k).decrypt(cipher));

    }
}
