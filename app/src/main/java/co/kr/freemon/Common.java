package co.kr.freemon;

import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class Common {
    public String loginId;
    public String loginPw;

    public static boolean CheckPermission(Context context, String permission)
    {
        boolean returnValue = true;
        if (context != null && permission != null) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
            {
                returnValue = false;
            }
        }

        return returnValue;
    }

    public static String GetValueFromShared(SharedPreferences pref, String key)
    {
        String returnValue = "" ;

        if (key.isEmpty() != true)
        {
            returnValue = pref.getString(key, ""); //key, value(defaults)
        }

        return returnValue;
    }
}
