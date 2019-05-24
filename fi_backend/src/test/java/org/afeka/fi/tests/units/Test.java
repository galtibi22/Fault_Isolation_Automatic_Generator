package org.afeka.fi.tests.units;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;

public class Test {


    @org.junit.Test
    public void test() {
        String a="721 dad";
        String [] split=a.split(" ");
        if (isCotendo(split[0])|| isCotendo(split[1]))
            System.out.println("success");

    }


    public static boolean akamai(String str1,String str2){
        char array1[] = str1.toCharArray();
        char array2[] = str2.toCharArray();
        if (array1.length ==array2.length){
            for (int i=0;i<array1.length;i++){
                if (array1[i]!=array2[i])
                    return false;
            }
            return true;
        }else
            return false;
    }

    public static Boolean isCotendo(String inputString) {
        StringBuilder sb = new StringBuilder(inputString);
        if (sb.reverse().toString().equals(inputString)) {
            for (char ch : inputString.toCharArray()) {
                if (ch == 'c' || ch == 'o' || ch == 't' || ch == 'e' || ch == 'n' || ch == 'd' || ch == '1') {
                    return true;
                }
            }
        }return false;
    }

    public static boolean duplicates(String [] split){
        for (int i=0;i<split.length;i++){
            if (checkDuplicates(split,split[i]))
                return true;
        }
        return false;
    }

    static boolean checkDuplicates(String arr[], String k)
    {
        for (int i=0; i<arr.length; i++)
        {
            if (arr[i].equals(k))
                return true;
        }
        return false;
    }

}
