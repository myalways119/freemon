package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.kr.freemon.R;
import common.CommonConst;

public class JoinAgreeActivity extends AppCompatActivity {

    CheckBox chkAllowAll;
    CheckBox chkAllowService;
    CheckBox chkAllowProfile;
    Button btnAllowAndNext;
    Button btnSeeService;
    Button btnSeeProfile;

    Dialog textViewDialog;

    String agreeProfileContent;
    String agreeServiceContent;

    Map<String, String> agreeContextList = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_agree);

        InitializeView();
        SetListener();
        SetAgreementContent();
    }

    public void InitializeView()
    {
        textViewDialog = new Dialog(this);
        textViewDialog.setContentView(R.layout.dialog_content);

        chkAllowAll = findViewById(R.id.join_agree_chkAllowAll);
        chkAllowService = findViewById(R.id.join_agree_chkAllowService);
        chkAllowProfile = findViewById(R.id.join_agree_chkAllowProfile);
        btnAllowAndNext = findViewById(R.id.join_agree_btnAllowAndNext);
        btnSeeService = findViewById(R.id.join_agree_btnseeServiceContent);
        btnSeeProfile = findViewById(R.id.join_agree_btnseeProfileContent);
    }

    public void SetListener()
    {
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                switch (view.getId())
                {
                    case R.id.join_agree_chkAllowAll:
                        boolean isChecked = ((CheckBox)view).isChecked();

                        if(isChecked == true)
                        {
                            chkAllowService.setChecked(true);
                            chkAllowProfile.setChecked(true);
                        }
                        else
                        {//리스트로 나중에 변경하기.
                            chkAllowService.setChecked(false);
                            chkAllowProfile.setChecked(false);
                        }
                        break;
                    case R.id.join_agree_btnAllowAndNext:
                        if(chkAllowService.isChecked() == false || chkAllowProfile.isChecked() == false)
                        {
                            Toast.makeText(getApplicationContext(),"서비스와 개인정보 동의는 필수 동의사항입니다.", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Intent intent = new Intent(getApplicationContext(), JoinPhoneAuthActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.join_agree_btnseeProfileContent:
                        ShowTextViewDialog(view.getId());
                        break;
                    case R.id.join_agree_btnseeServiceContent:
                        ShowTextViewDialog(view.getId());
                        break;
                }
            }
        };

        btnAllowAndNext.setOnClickListener(Listener);
        chkAllowAll.setOnClickListener(Listener);
        chkAllowService.setOnClickListener(Listener);
        chkAllowProfile.setOnClickListener(Listener);

        btnSeeService.setOnClickListener((Listener));
        btnSeeProfile.setOnClickListener((Listener));
    }

    public void ShowTextViewDialog(int id)
    {
        textViewDialog.show(); // 다이얼로그 띄우기

        Button btnOk = textViewDialog.findViewById(R.id.dialog_content_btnOk);
        TextView txtMainView = textViewDialog.findViewById(R.id.dialog_content_mainTxtView);
        TextView txtViewDialogTitle = textViewDialog.findViewById(R.id.dialog_content_title);

        txtMainView.setText("");

        if(id == R.id.join_agree_btnseeProfileContent)
        {
            txtViewDialogTitle.setText(R.string.join_agree_profile);

            txtMainView.setText(agreeContextList.get("JOIN_AGREE_SERVICE_CONTENT"));
        }
        else if(id == R.id.join_agree_btnseeServiceContent)
        {
            txtViewDialogTitle.setText(R.string.join_agree_service);
            txtMainView.setText(agreeContextList.get("JOIN_AGREE_PROFILE_CONTENT"));
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                textViewDialog.dismiss(); // 다이얼로그 닫기
            }
        });

    }

    public void SetAgreementContent()
    {
        //서버의 loadDBtoJson.php파일에 접속하여 (DB데이터들)결과 받기
        //Volley+ 라이브러리 사용

        //서버주소
        //결과를 JsonArray 받을 것이므로..
        //StringRequest가 아니라..JsonArrayRequest를 이용할 것임
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, CommonConst.Url.COMMON_JOIN_AGREE_CONTENT, null, new Response.Listener<JSONArray>()
        {
            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용
            @Override
            public void onResponse(JSONArray response)
            {
                try
                {
                    for(int i=0;i<response.length();i++)
                    {
                        JSONObject jsonObject= response.getJSONObject(i);

                        String id = jsonObject.getString("ID");
                        String content = jsonObject.getString("CONTENT");

                        agreeContextList.put(id, content);
                    }
                } catch (JSONException e) {e.printStackTrace();}

            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JoinAgreeActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("TYPE", "AGREE_CONTENT");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }


}