package activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import common.Common;
import common.CommonConst;
import co.kr.freemon.R;
import common.DbRequest;
import common.SharedPrefManager;
import common.VolleySingleton;
import item.UserInfo;

public class IntroActivity extends AppCompatActivity {
    private SharedPreferences pref;

    private boolean hasPermission = false;
    private boolean hasLoginInfo = false;

    UserInfo userInfoFromDb = null;

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

        Intent intent= GetNextActivityIntent();

        if (intent != null)
        {//로그인, 권한체크, 메인 화면 중에 하나로 이동.
            finish();
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this.getApplicationContext(),"관리자에게 문의 바랍니다.", Toast.LENGTH_LONG).show();
        }
    }

    private Intent GetNextActivityIntent()
    {
        //GET VARIABLES
        Intent returnIntent;
        hasPermission = false;
        String phoneNum = SharedPrefManager.getInstance(getApplicationContext()).GetValue(SharedPrefManager.KEY_PHONE_NUM);//내부 저장소에서 저장된 폰번호(SharedPref)
        String devicePhoneNum = "";
        String deviceAndroidId = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);

        if(Common.CheckPermission(this, CommonConst.Permission.PERMISSION_CAMERA) == true
           && Common.CheckPermission(this, CommonConst.Permission.PERMISSION_PHONE_STATE))
        {
            hasPermission = true;
            devicePhoneNum = Common.getPhoneNumber(this.getApplicationContext()); //권한 반드시 있어야 해당 값을 가져올 수 있음. READ_PHONE_STATE
        }

        if(devicePhoneNum.isEmpty() == false)
        {
            GetUserInfo(devicePhoneNum);//Get User Info From DB.
        }
        //--END

        //START LOGIC
        if(hasPermission == false)
        {//권한 없는 경우
            returnIntent = new Intent(getApplicationContext(), AuthorityActivity.class); //권한 설정화면
        }
        else if(hasPermission == true
                && userInfoFromDb != null
                && userInfoFromDb.androidId == deviceAndroidId
                && userInfoFromDb.phoneNum == devicePhoneNum)
        {//권한 있고, 자동 로그인 정보도 있는 경우
            returnIntent = new Intent(getApplicationContext(), MainActivity.class); //자동 로그인 후 메인 화면 이동
        }
        else
        {
            returnIntent = new Intent(getApplicationContext(), LoginActivity.class); //로그인 화면
        }

        return returnIntent;

        //storing the user in shared preferences
        ///if(userInfo != null && userInfo.phoneNum.isEmpty() == false)
        //{
        //  SharedPrefManager.getInstance(getApplicationContext()).SetValue(SharedPrefManager.KEY_PHONE_NUM, userInfo.phoneNum);
        //}

        //starting the profile activity
        //finish();
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));


        //Q. 010-----기존 계정 정보가 존재합니다. 기존 정보로 로그인 하시겠습니까? (Dialog 창 띄우기)
        //Yes, 비밀번호 찾는 질문 던지기.
          //  No, 새로 가입가능하게 하고 (기존 정보는 BACKUP으로 넘기기)

        //새로운 전화번호로 로그인 한 경우
        //기존 계정 불러오기(전화번호 입력받고, 질문 던지기)

        //기기 ID정보도 같이 저장하기.
        /*
        if (Common.CheckPermission(this, CommonConst.Permission.PERMISSION_CAMERA) == true
                || Common.CheckPermission(this, CommonConst.Permission.PERMISSION_PHONE_STATE) == true)
        {//권한 있는 경우
            String devicePhoneNum = Common.getPhoneNumber(this.getApplicationContext()); //권한 반드시 있어야됨. READ_PHONE_STATE
            String phoneNum = SharedPrefManager.getInstance(getApplicationContext()).GetValue(SharedPrefManager.KEY_PHONE_NUM);

            if(SharedPrefManager.getInstance(this).isLoggedIn() == true)
            {
                //DB로 접속해서 ID,PW가 유효한지 확인하기.
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

         */

        //if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1)
        //{ returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); }
        //else { returnIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); }
    }

    private void GetUserInfo(String phoneNum)
    {
        ProgressBar progressBar;
        UserInfo returnInfo;

        if (phoneNum.isEmpty() == true)
        {
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbRequest.UrlConst.SELECT_TB_USER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error"))
                            {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                UserInfo returnInfo = new UserInfo();
                                returnInfo.phoneNum = userJson.getString("phone_num");
                                returnInfo.name = userJson.getString("name");
                                returnInfo.gender = userJson.getString("gender");
                                returnInfo.berth = userJson.getString("berth");
                                returnInfo.profilePic = userJson.getString("profile_pic");
                                returnInfo.targetCity = userJson.getString("target_city");
                                returnInfo.recQuestion = userJson.getString("rec_question");
                                returnInfo.recAnswer = userJson.getString("rec_answer");

                                userInfoFromDb = returnInfo; //Set User Info From DB
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("PHONE_NUM", phoneNum);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}