package common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import item.UserInfo;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    public static final String KEY_PHONE_NUM = "PHONE_NUM";
    public static final String KEY_ANDROID_ID = "ANDROID_ID";
    private static SharedPrefManager mInstance;
    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context)
    {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void SetValue(String key, String value)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String GetValue(String Key)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key, null);
    }

    /*
    //this method will store the user data in shared preferences
    public void userLogin(UserInfo user)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE_NUM, user.phoneNum);
        editor.putString(ANDROID_ID, user.androidId);

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        boolean returnValue = false;
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.getString(PHONE_NUM, null) != null)
        {
            returnValue = true;
        }

        return returnValue;
    }
*/


    //this method will logout the user
    //public void logout() {
      //  SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.clear();
        //editor.apply();
        //ctx.startActivity(new Intent(ctx, LoginActivity.class));
    //}
}