package app.cipher;

/**
 * Created by Azz_B on 17/04/2016.
 */
/*
* class Crypt for crypt a clear text using key */
public class Crypt {

    private String key;

    public Crypt(String key) {
        this.key = key;

    }

    public String taskCrypt(String str){
        System.out.println("********************** taskCrypt start ******************");

        int [][] keyMatrix = AES.keyScheduler(key);
        int[][] state = Manager.strToMatrixArray(str);
        //show to matrix
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

        System.out.println("cipher text: " + Manager.matIntToString(state));
        System.out.println("********************** taskCrypt end ******************");
        return Manager.matIntToString(state);
    }

    public String crypt(String str){
        str = Manager.adapteStr(str);

        String cipher = "";
        int i = 0;
        while(i < (str.length() / 16)){
            cipher += taskCrypt(str.substring(i * 16, (i * 16) + 16));
            i++;
        }
        return cipher;
    }
}
