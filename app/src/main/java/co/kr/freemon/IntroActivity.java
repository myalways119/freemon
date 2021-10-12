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

    private String PrefKeyId ="PrefKeyId";
    private String PrefKeyPw ="PrefKeyPw";

    private static final int PERMISSION_REQUEST_CODE = 0;

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

        if (CheckPermission() == true)
        {//권한 있는 경우
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), AuthorityActivity.class);
            startActivity(intent);
        }
    }

    private void Initialize()
    {
        pref = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
    }

    private boolean DoLogin(String id, String Pw)
    {
        boolean returnValue = false;


        return returnValue;
    }

    private String GetValueFromShared(String key)
    {
        String returnValue = "" ;

        if (key.isEmpty() != true)
        {
            returnValue = pref.getString(key, ""); //key, value(defaults)
        }

        return returnValue;
    }

    private void RestartProgram()
    {
        PackageManager packageManager = getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        startActivity(mainIntent);
        System.exit(0);
    }

    private void ExitProgram() {
        // 종료
        if (Build.VERSION.SDK_INT >= 21) {
            finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
        } else {
            // 액티비티 종료
            finish();
        }
        System.exit(0);
    }

    private boolean CheckPermission()
    {
        boolean returnValue = false;

        if (checkPermissions(this, PERMISSIONS) == false)
        {//권한이 없는 경우
            returnValue = false;
            //Toast.makeText(getApplicationContext(),"앱 사용에 필수적인 권한입니다.\n허용 부탁드립니다." , Toast.LENGTH_LONG).show();
            //ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE); //권한요청
        }
        else
        {/*..권한이 있는경우 실행할 코드....*/
            returnValue = true;
        }

        return returnValue;
    };

    public boolean checkPermissions(Context context, String[] permissionList)
    {
        boolean returnValue = true;
        if (context != null && permissionList != null) {
            for (String permission : permissionList) {
                //if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    returnValue = false;
                }
            }
        }

        return returnValue;
    }

    //권한 요청에 대한 결과 처리

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /*..권한이 있는경우 실행할 코드....*/
                } else {
                    // 하나라도 거부한다면.
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("앱 권한");
                    alertDialog.setMessage("해당 앱의 원할한 기능을 이용하시려면 애플리케이션 정보>권한> 에서 모든 권한을 허용 후 다시 시작 해주십시오");
                    // 권한설정 클릭시 이벤트 발생
                    alertDialog.setPositiveButton("권한설정",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                                    startActivity(intent);
                                    dialog.cancel();
                                }
                            });
                    //취소
                    alertDialog.setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ExitProgram();
                                }
                            });
                    alertDialog.show();

                }
                return;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}