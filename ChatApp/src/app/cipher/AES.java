package app.cipher;

/**
 * Created by Azz_B on 16/04/2016.
 */
/*
* contain all static method for AES algorithm
* */
public class AES {

    //--------------------------- key scheduler ---------------------------

    public static int[][] keyScheduler(String key) {
        int[][] keyMatrix = new int[4][44];
        assert(key.length() == 16);
        int[][] m = Manager.strToMatrixArray(key);

        /*
        * copy m to key matrix*/
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                keyMatrix[i][j] = m[i][j];
            }
        }

        int c = 0;
        int current = 3;
        int rconPointer = 0;
        //generate key scheduler
        for (int k = 0; k < 40; k++) {

            int[] col = new int[4];
            for (int j = 0; j < 4; j++) {
                col[j] = keyMatrix[j][current];
            }
            //Manage.showArray(col);

            int modulo = (current+1) % 4;
            if (modulo == 0) {
                col = schedule_core(col, rconPointer);
                rconPointer++;
            }
            current++;
            for (int i = 0; i < 4; i++) {
                keyMatrix[i][current] = keyMatrix[i][c] ^ col[i];
            }

            c++;
        }


        return keyMatrix;
    }

    /*--------------------------------------------------*/

    private static int[] schedule_core(int[] in, int rconpointer) {
        in = leftrotate(in, 1);
        int hex;
        for (int i = 0; i < in.length; i++) {
            hex = in[i];
            in[i] = MTConst.sbox[hex / 16][hex % 16];
        }
        in[0] ^= MTConst.rcon[0][rconpointer];
        return in;
    }

    //--------------------------------- subKey  method---------------
    public static int[][] subKey(int km[][], int begin){
        int[][] arr = new int[4][4];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                arr[i][j] = km[i][4 * begin + j];
            }
        }
        return arr;
    }

    //-----------shift row ------------------

    public static void shiftRows(int[][] arr) {
        for (int i = 1; i < arr.length; i++) {
            arr[i] = leftrotate(arr[i], i);
        }
    }
// ------------------------- leftRotate method ---------------------
    private static int[] leftrotate(int[] arr, int times) {
        assert (arr.length == 4);
        if (times % 4 == 0) {
            return arr;
        }
        while (times > 0) {
            int temp = arr[0];
            for (int i = 0; i < arr.length - 1; i++) {
                arr[i] = arr[i + 1];
            }
            arr[arr.length - 1] = temp;
            --times;
        }
        return arr;
    }

    //inverse shift row
    public static void invShiftRows(int[][] arr) {
        for (int i = 1; i < arr.length; i++) {
            arr[i] = rightrotate(arr[i], i);
        }
    }

    //-------------------- rightRotate method -------------

    private static int[] rightrotate(int[] arr, int times) {
        if (arr.length == 0 || arr.length == 1 || times % 4 == 0) {
            return arr;
        }
        while (times > 0) {
            int temp = arr[arr.length - 1];
            for (int i = arr.length - 1; i > 0; i--) {
                arr[i] = arr[i - 1];
            }
            arr[0] = temp;
            --times;
        }
        return arr;
    }

    //--------------------------------------------------------
    public static void subBytes(int[][] arr) {
        for (int i = 0; i < 4; i++) //Sub-Byte subroutine
        {
            for (int j = 0; j < 4; j++) {
                int hex = arr[j][i];
                arr[j][i] = MTConst.sbox[hex / 16][hex % 16];
            }
        }
    }
//-----------------------------------------------

    public static void invSubBytes(int[][] arr) {
        for (int i = 0; i < arr.length; i++) //Inverse Sub-Byte subroutine
        {
            for (int j = 0; j < arr[0].length; j++) {
                int hex = arr[j][i];
                arr[j][i] = MTConst.invsbox[hex / 16][hex % 16];
            }
        }
    }

    //----------------mix column----------------------------
    public static void mixColumns(int[][] arr) //method for mixColumns
    {
        int[][] tarr = new int[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(arr[i], 0, tarr[i], 0, 4);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arr[i][j] = mcHelper(tarr, MTConst.galois, i, j);
            }
        }
    }

    //helper
    private static int mcHelper(int[][] arr, int[][] g, int i, int j) {
        int mcsum = 0;
        for (int k = 0; k < 4; k++) {
            int a = g[i][k];
            int b = arr[k][j];
            mcsum ^= mcCalc(a, b);
        }
        return mcsum;
    }

    private static int mcCalc(int a, int b) //Helper method for mcHelper
    {
        if (a == 1) {
            return b;
        } else if (a == 2) {
            return MCTables.mc2[b / 16][b % 16];
        } else if (a == 3) {
            return MCTables.mc3[b / 16][b % 16];
        }
        return 0;
    }

    //-------------------------- inverse mix columns ----------------------

    public static void invMixColumns(int[][] arr) {
        int[][] tarr = new int[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(arr[i], 0, tarr[i], 0, 4);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arr[i][j] = invMcHelper(tarr, MTConst.invgalois, i, j);
            }
        }
    }
    //-------------------

    private static int invMcHelper(int[][] arr, int[][] igalois, int i, int j) //Helper method for invMixColumns
    {
        int mcsum = 0;
        for (int k = 0; k < 4; k++) {
            int a = igalois[i][k];
            int b = arr[k][j];
            mcsum ^= invMcCalc(a, b);
        }
        return mcsum;
    }

    //---------------

    private static int invMcCalc(int a, int b) //Helper method for invMcHelper
    {
        if (a == 9) {
            return MCTables.mc9[b / 16][b % 16];
        } else if (a == 0xb) {
            return MCTables.mc11[b / 16][b % 16];
        } else if (a == 0xd) {
            return MCTables.mc13[b / 16][b % 16];
        } else if (a == 0xe) {
            return MCTables.mc14[b / 16][b % 16];
        }
        return 0;
    }

    //-------------------- addRoundKey -----------------------

    public static void addRoundKey(int[][] bytematrix, int[][] keymatrix) {
        for (int i = 0; i < bytematrix.length; i++) {
            for (int j = 0; j < bytematrix[0].length; j++) {
                bytematrix[j][i] ^= keymatrix[j][i];
            }
        }
    }

}
