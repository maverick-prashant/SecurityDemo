package com.mavericklabs.securitydemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by Prashant Kashetti on 19/9/17.
 */

public class AppValidator {
    //we store the hash of the signture for a little more protection
    private static final String APP_SIGNATURE = "9B52B8E1881F78CF60E7FB5372E1DAF5CE2532C9";
    private Context context;

    public AppValidator(Context context) {
        this.context = context;
    }

    public boolean isValidApp() {
        if (isHacked())
            return false;
        if (isDebuggable())
            return false;
        try {
            if (!validateAppSignature()) {
                return false;
            }
        } catch (PackageManager.NameNotFoundException | NoSuchProviderException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }

    private boolean isHacked() {
        //Renamed?
        if (context.getPackageName().compareTo("com.mavericklabs.securitydemo") != 0) {
            return true; // BOOM!
        }

        //Relocated?
        String installer = context.getPackageManager().getInstallerPackageName("com.mavericklabs.securitydemo");

        // BOOM!
        return installer == null || installer.compareTo("com.android.vending") != 0;
    }

    private boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    /**
     * Query the signature for this application to detect whether it matches the
     * signature of the real developer. If it doesn't the app must have been
     * resigned, which indicates it may been tampered with.
     *
     * @return true if the app's signature matches the expected signature.
     * @throws PackageManager.NameNotFoundException, NoSuchProviderException, NoSuchAlgorithmException
     */
    private boolean validateAppSignature() throws PackageManager.NameNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {

        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                context.getPackageName(), PackageManager.GET_SIGNATURES);
        //note sample just checks the first signature
        Signature[] signatures = packageInfo.signatures;
        for (Signature signature : signatures) {
            // SHA1 the signature
            String sha1 = getSHA1(signature.toByteArray());
            // check is matches hardcoded value
            return APP_SIGNATURE.equals(sha1);
        }

        return false;
    }

    //computed the sha1 hash of the signature
    private String getSHA1(byte[] sig) throws NoSuchProviderException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA1", "BC");
        digest.update(sig);
        byte[] hashtext = digest.digest();
        return bytesToHex(hashtext);
    }

    //util method to convert byte array to hex string
    private String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
