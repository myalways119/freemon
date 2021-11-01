package common;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

public class Common {


    public String loginId;
    public String loginPw;

    public static boolean CheckPermission(Context context, String permission) {
        boolean returnValue = true;
        if (context != null && permission != null) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                returnValue = false;
            }
        }

        return returnValue;
    }

    public static String getPhoneNumber(Context context){

        String phoneNumber = "";

        TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {

            String tmpPhoneNumber = mgr.getLine1Number();
            phoneNumber = tmpPhoneNumber.replace("+82", "0");

        } catch (Exception e) {
            phoneNumber = "";
        }

        return phoneNumber;
    }
/*
    public static String GetValueFromShared(SharedPreferences pref, String key)
    {
        String returnValue = "" ;

        if (key.isEmpty() != true)
        {
            returnValue = pref.getString(key, ""); //key, value(defaults)
        }

        return returnValue;
    }

 */
}
