package com.cjj.mousepaint.utils;

import android.graphics.Color;

public class HexUtils {
    public static String byte2HexStr(byte[] paramArrayOfByte) {
        StringBuilder localStringBuilder = new StringBuilder("");
        int i = 0;
        if (i < paramArrayOfByte.length) {
            String str1 = Integer.toHexString(0xFF & paramArrayOfByte[i]);
            if (str1.length() == 1) ;
            for (String str2 = "0" + str1; ; str2 = str1) {
                localStringBuilder.append(str2);
                localStringBuilder.append(" ");
                i++;
                break;
            }
        }
        return localStringBuilder.toString().toUpperCase().trim();
    }

    public static int getHexColor(String paramString) {
        int i = Long.decode("0xFF8080FF").intValue();
        try {
            int j = Color.parseColor(paramString);
            return j;
        } catch (Exception localException) {
        }
        return i;
    }

    public static byte[] hexStr2Bytes(String paramString) {
        int i = paramString.length() / 2;
        System.out.println(i);
        byte[] arrayOfByte = new byte[i];
        for (int j = 0; j < i; j++) {
            int k = 1 + j * 2;
            int m = k + 1;
            arrayOfByte[j] = Byte.decode("0x" + paramString.substring(j * 2, k) + paramString.substring(k, m)).byteValue();
        }
        return arrayOfByte;
    }

    public static String strToUnicode(String paramString)
            throws Exception {
        StringBuilder localStringBuilder = new StringBuilder();
        int i = 0;
        if (i < paramString.length()) {
            int j = paramString.charAt(i);
            String str = Integer.toHexString(j);
            if (j > 128)
                localStringBuilder.append("\\u" + str);
            else {
                i++;
                localStringBuilder.append("\\u00" + str);
            }
        }
        return localStringBuilder.toString();
    }


    public static String unicodeToString(String paramString) {
        int i = paramString.length() / 6;
        StringBuilder localStringBuilder = new StringBuilder();
        for (int j = 0; j < i; j++) {
            String str1 = paramString.substring(j * 6, 6 * (j + 1));
            String str2 = str1.substring(2, 4) + "00";
            String str3 = str1.substring(4);
            localStringBuilder.append(new String(Character.toChars(Integer.valueOf(str2, 16).intValue() + Integer.valueOf(str3, 16).intValue())));
        }
        return localStringBuilder.toString();
    }
}