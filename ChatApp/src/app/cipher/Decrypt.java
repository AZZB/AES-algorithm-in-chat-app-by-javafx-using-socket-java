package app.cipher;



/**
 * Created by Azz_B on 17/04/2016.
 */

/*
* class Decrypt for decrypt cipher text using key*/
public class Decrypt {

    private String key;

    public Decrypt(String key){
        this.key = key;
    }

    public String taskDecrypt(String str){
        System.out.println("********************** taskDecrypt start ******************");
        int [][] keyMatrix = AES.keyScheduler(key);
        int[][] state = Manager.strToMatrixArray(str);

        //show to matrix
        Manager.showMatrix(keyMatrix);
        Manager.showMatrix(state);

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

        System.out.println("clair text: " + Manager.matIntToString(state));
        System.out.println("********************** taskDecrypt end ******************");

        return Manager.matIntToString(state);
    }

    // --------------------  taskDecrypt a string long of 16 --------------
    public String decrypt(String str){
        //str = Manager.adapteStr(str);

        String cipher = "";
        int i = 0;
        while(i < (str.length() / 16) ){
            cipher += taskDecrypt(str.substring(i * 16, (i * 16) + 16));
            i++;
        }
        return cipher;
    }

}
