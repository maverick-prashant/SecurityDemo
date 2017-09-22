package com.mavericklabs.securitydemo;

import android.content.Context;


/**
 * Created by Prashant Kashetti on 31/5/16.
 * utility class to use encryption/decryption
 */
public class SecurityUtils {
    private static SecurityUtils securityUtils;

    private SecurityUtils(Context context) {

    }

    public static SecurityUtils getInstance(Context context) {
        if (securityUtils == null) {
            securityUtils = new SecurityUtils(context.getApplicationContext());
        }
        return securityUtils;
    }


    public String encrypt(String plainText) {
        try {
            byte[] data = plainText.getBytes("UTF-8");
            data = Cryptor.crypt(data, 0);
            return Cryptor.bytes2HexStr(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String cipherText) {
        try {
            byte[] data = Cryptor.hexStr2Bytes(cipherText);
            data = Cryptor.crypt(data, 1);
            return new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
