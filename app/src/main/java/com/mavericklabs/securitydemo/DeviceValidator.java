package com.mavericklabs.securitydemo;

import android.content.Context;
import android.os.Build;

/**
 * Created by Prashant Kashetti on 19/9/17.
 */

public class DeviceValidator {
    private Context context;

    public DeviceValidator(Context context) {
        this.context = context;
    }

    public void isValidDevice() {
        if (!isEmulator()) {
            CheckRoot checkRoot = new CheckRoot(context);
            checkRoot.run();
        }
    }

    private boolean isEmulator() {
        return (Build.PRODUCT.equals("sdk"))
                || (Build.PRODUCT.equals("google_sdk"))
                || (Build.PRODUCT.equals("sdk_x86"))
                || (Build.PRODUCT.equals("vbox86p"))
                || (Build.MANUFACTURER.equals("unknown"))
                || (Build.MANUFACTURER.equals("Genymotion"))
                || (Build.BRAND.equals("generic"))
                || (Build.BRAND.equals("generic_x86"))
                || (Build.DEVICE.equals("generic"))
                || (Build.DEVICE.equals("generic_x86"))
                || (Build.DEVICE.equals("vbox86p"))
                || (Build.MODEL.equals("sdk"))
                || (Build.MODEL.equals("google_sdk"))
                || (Build.MODEL.equals("Android SDK built for x86"))
                || (Build.HARDWARE.equals("goldfish"))
                || (Build.HARDWARE.equals("vbox86"))
                || (Build.FINGERPRINT.contains("generic/sdk/generic"))
                || (Build.FINGERPRINT.contains("generic_x86/sdk_x86/generic_x86"))
                || (Build.FINGERPRINT.contains("generic/google_sdk/generic"))
                || (Build.FINGERPRINT.contains("generic/vbox86p/vbox86p"));
    }
}
