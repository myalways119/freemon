package co.kr.freemon;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class IntroActivity extends AppCompatActivity {
    private SharedPreferences pref;

    private boolean hasPermission = false;
    private boolean hasLoginInfo = false;

    //private static final int PERMISSION_REQUEST_CODE = 0;

    //요청할 권한들 배열로 선언
    private String[] PERMISSIONS =
    {
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Initialize();

        Intent intent= GetNextActivityIntent();

        if (intent != null)
        {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this.getApplicationContext(),"관리자에게 문의 바랍니다.", Toast.LENGTH_LONG).show();
        }
    }

    private void Initialize()
    {
        pref = getSharedPreferences(CommonConst.SharedPreferencesFileName, Activity.MODE_PRIVATE);
    }

    private Intent GetNextActivityIntent ()
    {
        Intent returnIntent;

        if (Common.CheckPermission(this, CommonConst.Permission.PERMISSION_CAMERA) == true
                || Common.CheckPermission(this, CommonConst.Permission.PERMISSION_PHONE_STATE) == true)
        {//권한 있는 경우
            Boolean isLogin = false;
            String id = Common.GetValueFromShared(pref, CommonConst.PrefKeyId);
            String pw = Common.GetValueFromShared(pref, CommonConst.PrefKeyPw);

            //DB로 접속해서 ID,PW가 유효한지 확인하기.

            if(isLogin == true)
            {
                returnIntent = new Intent(getApplicationContext(), MainActivity.class); //로그인 화면
            }
            else
            {
                returnIntent = new Intent(getApplicationContext(), LoginActivity.class); //로그인 화면
            }
        }
        else
        {//권한 없는 경우
            returnIntent = new Intent(getApplicationContext(), AuthorityActivity.class); //권한 설정화면
        }

        return returnIntent;
    }

/*
    private String GetValueFromShared(String key)
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