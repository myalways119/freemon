package activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;

import common.Common;
import common.CommonConst;
import co.kr.freemon.R;
import common.DbRequest;


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
        {//로그인, 권한체크, 메인 화면 중에 하나 이동.
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

        //한번만 초기화 하면 계속 사용 가능(volley HTTP통신을 위함)
        if(DbRequest.requestqueue == null)
            DbRequest.requestqueue = Volley.newRequestQueue(getApplicationContext());
    }

    private Intent GetNextActivityIntent ()
    {
        Intent returnIntent;


        //Q. 010-----기존 계정 정보가 존재합니다. 기존 정보로 로그인 하시겠습니까? (Dialog 창 띄우기)
        //Yes, 비밀번호 찾는 질문 던지기.
          //  No, 새로 가입가능하게 하고 (기존 정보는 BACKUP으로 넘기기)

        //새로운 전화번호로 로그인 한 경우
        //기존 계정 불러오기(전화번호 입력받고, 질문 던지기)

        //기기 ID정보도 같이 저장하기.



        if (Common.CheckPermission(this, CommonConst.Permission.PERMISSION_CAMERA) == true
                || Common.CheckPermission(this, CommonConst.Permission.PERMISSION_PHONE_STATE) == true)
        {//권한 있는 경우
            Boolean isLogin = false;
            String id = Common.GetValueFromShared(pref, CommonConst.PrefKeyId);
            String pw = Common.GetValueFromShared(pref, CommonConst.PrefKeyPw);

            //DB로 접속해서 ID,PW가 유효한지 확인하기.

            if(isLogin == true)
            {
                returnIntent = new Intent(getApplicationContext(), MainActivity.class); //자동 로그인 후 메인 화면 이동
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