package app.test;

import app.cipher.AES;
import app.cipher.Manager;

/**
 * Created by Azz_B on 17/04/2016.
 */
public class Test {

    public static void main(String[] args){
//³X¼ üûFØÎ¾»         
        String k = "azzeddine1994pp9";
        String m = "hello azzeddine8";

        int [][] keyMatrix = AES.keyScheduler(k);
        int[][] state = Manager.strToMatrixArray(m);
        Manager.showMatrix(keyMatrix);
        Manager.showMatrix(state);

        AES.addRoundKey(state, AES.subKey(keyMatrix, 0));

        for (int i = 1; i < 9; i++) {
            AES.subBytes(state); //implements the Sub-Bytes subroutine.
            AES.shiftRows(state); //implements Shift-Rows subroutine.
            AES.mixColumns(state);
            AES.addRoundKey(state, AES.subKey(keyMatrix, i));
        }

        AES.subBytes(state); //implements the Sub-Bytes subroutine.
        AES.shiftRows(state); //implements Shift-Rows subroutine.
        AES.addRoundKey(state, AES.subKey(keyMatrix, 9));

        Manager.showMatrix(state);
        System.out.println(Manager.matIntToString(state));

        //---------------------- decryption ---------------------
        AES.addRoundKey(state, AES.subKey(keyMatrix, 9));

        for (int i = 9 - 1; i > 0; i--) {
            AES.invShiftRows(state);
            AES.invSubBytes(state);
            AES.addRoundKey(state, AES.subKey(keyMatrix, i));
            AES.invMixColumns(state);
        }

        AES.invShiftRows(state);
        AES.invSubBytes(state);
        AES.addRoundKey(state, AES.subKey(keyMatrix, 0));

        Manager.showMatrix(state);
        System.out.println(Manager.matIntToString(state));

    }
}
