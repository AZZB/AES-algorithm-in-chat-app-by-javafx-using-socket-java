package app.cipher;

/**
 * Created by Azz_B on 16/04/2016.
 */

/*
* Manager class , all method responsible for manage data matrix and array and string*/
public class Manager {


// ---------------------   show matrix method -----------------

    public static void showMatrix(int m[][]){
        for(int i = 0; i < m.length; i++){
            System.out.print("\n");
            for(int j = 0; j< m[0].length; j++){
                System.out.print(m[i][j] + ",") ;
            }
        }
        System.out.println("\n*********************************") ;
    }

//------------------------------ show array ------------------
    public static void showArray(int[] arr){
        for(int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + ", ");
        System.out.println("\n");
    }

//-------------------------- convert string to matrix of 4x4  --------------

    public static int[][] strToMatrixArray(String str){
        int[] arr = new int[str.length()];
        int mat[][] = new int[4][4];
        for(int i = 0; i < str.length(); i++){
            arr[i] = str.charAt(i);
        }

        int k = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                mat[i][j] = arr[k];
                k++;
            }
        }
        return mat;
    }
//-------------------------- convert matrix to  array ------------------

    public static int[] matrixToArray(int[][] m){
        int[] a = new int[16];
        int k = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                a[k] = m[i][j];
                k++;
            }
        }
        return a;
    }

    //----------------------- array to String -------------------
    public static String arrayToString(int[] arr){

        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < arr.length; i++){
            buffer.append((char)arr[i]);
        }
        return buffer.toString();
    }

  //----------------------- convert matrix to string value --------------------

    public static String matIntToString(int[][] m){
        int arr[] = matrixToArray(m);
        return arrayToString(arr);
    }

    //-------------- sub string for get a string array of 16 length for each value -------

   /* public static String[] subStr(String str){
        int num = str.length()/16;
        int rest = str.length() % 16;
        System.out.println(num);
        System.out.println(str.length()%16);
        int size = num;
        if((rest) != 0) size++;
        String[] arr = new String[size];
        int c = 0;
        for(int i = 0; i < num; i++){
            arr[i] = str.substring(c, c + 16);
            c += 16;
        }
        if((rest%16) != 0){
            StringBuffer buffer = new StringBuffer();
            buffer.append(str.substring(c, str.length() - 1));
            int i = 0;
            while(i < rest){
                buffer.append("+");
                i++;
            }
            arr[arr.length - 1] = buffer.toString();
        }


        return arr;
    }
*/
    //---------------------------------------------------
    public static String adapteStr(String str){
        int num = str.length()/16;
        //int rest = str.length() % 16;

        if((num * 16) != str.length()) num++;

        //int[] arr = new int[num * 16];


        /*int i = 0;
        while( i < arr.length){
            if(i < str.length())
                arr[i] = str.charAt(i);
            else
                arr[i] = 32;
            i++;
        }*/

        //return arrayToString(arr);

        StringBuilder buffer = new StringBuilder();
        int i = 0;
        while( i < (num * 16)){
            if(i < str.length())
                buffer.append(str.charAt(i));
            else
                buffer.append(" ");
            i++;
        }
        return buffer.toString();
    }







}
