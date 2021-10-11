package co.kr.freemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AuthorityActivity extends AppCompatActivity {
    private boolean hasPermission = false;
    private boolean hasLoginInfo = false;

    private static final int REQUEST_C_CODE = 1;
    private static final int REQUEST_P_CODE = 2;

    private static String C_PERMISSION_PHONE_NUMBER = android.Manifest.permission.READ_PHONE_STATE;
    private static String C_PERMISSION_CAMERA = android.Manifest.permission.CAMERA;

    Button btnAllowCamera;
    Button btnAllowPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority);

        getPhoneNumber();
        InitializeView();
        SetListener();

    }

    private void getPhoneNumber()
    {
        TelephonyManager telManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") String  PhoneNum = telManager.getLine1Number();
        Toast.makeText(this, PhoneNum, Toast.LENGTH_SHORT).show();
        if(PhoneNum.startsWith("+82")) // 국제번호(+82 10...)로 되어 있을경우 010 으로 변환

        {

            PhoneNum = PhoneNum.replace("+82", "0");

        }

        Toast.makeText(this, PhoneNum, Toast.LENGTH_SHORT).show();
    }

    public void InitializeView()
    {
        btnAllowCamera = (Button) findViewById(R.id.btnAllowCamera);
        btnAllowPhoneNum = (Button) findViewById(R.id.btnAllowPhoneNumber);
        btnAllowCamera.setEnabled(true);
        btnAllowPhoneNum.setEnabled(true);

        SetButtonEnable(C_PERMISSION_CAMERA);
        SetButtonEnable(C_PERMISSION_PHONE_NUMBER);
    }

    private void SetButtonEnable(String permission)
    {
        boolean hasPermission = false;
        if(permission.isEmpty() == true) return;

        if (CheckPermission(this,permission) == true)
        {
            hasPermission = true;
        }

        if (permission == C_PERMISSION_CAMERA) {
            btnAllowPhoneNum.setEnabled(hasPermission);
        }
        else if (permission == C_PERMISSION_PHONE_NUMBER)
        {
            btnAllowPhoneNum.setEnabled(hasPermission);
        }
    }

    public void SetListener()
    {
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                switch (view.getId()) {
                    case R.id.btnAllowCamera:
                        //ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_C_CODE); //권한요청
                        ActivityCompat.requestPermissions(AuthorityActivity.this, new String[]{C_PERMISSION_CAMERA}, REQUEST_C_CODE);
                        SetButtonEnable(C_PERMISSION_CAMERA);//Set Button enable
                        break;
                    case R.id.btnAllowPhoneNumber:
                        ActivityCompat.requestPermissions(AuthorityActivity.this, new String[]{C_PERMISSION_PHONE_NUMBER}, REQUEST_P_CODE);
                        SetButtonEnable(C_PERMISSION_PHONE_NUMBER);//Set Button enable
                        break;
                }
            }
        };

        btnAllowCamera.setOnClickListener(Listener);
        btnAllowPhoneNum.setOnClickListener(Listener);
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

    /*
    private boolean CheckPermission(String permission)
    {
        boolean returnValue = false;

        if (checkPermissions(this, permission) == false)
        {//권한이 없는 경우
            returnValue = false;
            //Toast.makeText(getApplicationContext(),"앱 사용에 필수적인 권한입니다.\n허용 부탁드립니다." , Toast.LENGTH_LONG).show();
            //ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE); //권한요청
        }
        else
        {
            returnValue = true;
        }

        return returnValue;
    };
*/

    public boolean CheckPermission(Context context, String permission)
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

/*
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
*/

    //권한 요청에 대한 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_P_CODE: //Request Authority of Phone Number
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {/*..권한이 있는경우 실행할 코드....*/
                    btnAllowPhoneNum.setEnabled(false);
                }
                else
                {/*..권한이 없는 경우....*/
                    btnAllowPhoneNum.setEnabled(true);
                }
/*
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

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
*/
                return;
            case REQUEST_C_CODE: //Request Authority of Camera
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {/*..권한이 있는경우 실행할 코드....*/
                    btnAllowCamera.setEnabled(false);
                }
                else
                {/*..권한이 없는 경우....*/
                    btnAllowCamera.setEnabled(true);
                }
                return;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
