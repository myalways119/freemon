package co.kr.freemon;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {
    FragmentManager fragManagement;
    FragmentTransaction fragmentTransaction;

    //Fragments
    Fragment_join_profile fragProfile;
    Fragment_join_phone_authority fragPhoneAuthority;
    Fragment_join_agreement fragAgree;

    int currentFragment = 0;
    Button btnNext;
    String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        InitializeView();
        SetListener();
        SetFragment(R.id.join_frag_phoneAuthority);
    }

    private void InitializeView()
    {
        btnNext = findViewById(R.id.joinActivity_btn_next);

        fragAgree = new Fragment_join_agreement();
        fragProfile = new Fragment_join_profile();
        fragPhoneAuthority = new Fragment_join_phone_authority();
        //setFragment(R.id.join_frag_phoneAuthority); //프래그먼트 교체
        phoneNum = GetPhoneNumber();
    }

    public void SetListener()
    {
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                switch (currentFragment)
                {
                    case R.id.join_frag_phoneAuthority:
                        //폰 번호 인증한 경우
                        SetFragment(R.id.join_frag_agree);
                        break;
                    case R.id.join_frag_agree:
                        //동의서 동의 한 경우
                        SetFragment(R.id.join_frag_profile);
                        break;
                    case R.id.join_frag_profile:
                        //프로필 입력한 경우
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };

        btnNext.setOnClickListener(Listener);
    }

    public void SetFragment(int id)
    {//프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        switch (id)
        {
            case R.id.join_frag_phoneAuthority:
                getSupportFragmentManager().beginTransaction().replace(R.id.join_fragment_frame, fragPhoneAuthority).commit();
                break;
            case R.id.join_frag_agree:
                getSupportFragmentManager().beginTransaction().replace(R.id.join_fragment_frame, fragAgree).commit();
                break;
            case R.id.join_frag_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.join_fragment_frame, fragProfile).commit();
                break;
        }

        currentFragment = id;
    }

    private String GetPhoneNumber()
    {
        String returnValue = "";
        TelephonyManager telManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String  PhoneNum = telManager.getLine1Number();

        if(PhoneNum.startsWith("+82")) // 국제번호(+82 10...)로 되어 있을경우 010 으로 변환
        {
            returnValue = PhoneNum.replace("+82", "0");
        }

        return returnValue;
    }


}