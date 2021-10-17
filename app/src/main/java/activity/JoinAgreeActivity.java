package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.kr.freemon.R;
import common.Common;
import common.CommonConst;
import common.ExpandableListAdapter;

public class JoinAgreeActivity extends AppCompatActivity {

    CheckBox chkAllowAll;
    CheckBox chkAllowService;
    CheckBox chkAllowProfile;
    Button btnAllowAndNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_agree);

        InitializeView();
        SetListener();
    }

    public void InitializeView()
    {

        chkAllowAll = findViewById(R.id.join_agree_chkAllowAll);
        chkAllowService = findViewById(R.id.join_agree_chkAllowService);
        chkAllowProfile = findViewById(R.id.join_agree_chkAllowProfile);
        btnAllowAndNext = findViewById(R.id.join_agree_btnAllowAndNext);
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
                }
            }
        };

        btnAllowAndNext.setOnClickListener(Listener);
        chkAllowAll.setOnClickListener(Listener);
        chkAllowService.setOnClickListener(Listener);
        chkAllowProfile.setOnClickListener(Listener);
    }
}