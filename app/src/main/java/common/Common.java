package common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import com.android.volley.RequestQueue;

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
