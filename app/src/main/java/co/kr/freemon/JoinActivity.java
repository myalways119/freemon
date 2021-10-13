package co.kr.freemon;
import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JoinActivity extends AppCompatActivity {
    FragmentManager fragManagement;

    //Fragments
    Fragment_join_profile fragProfile;
    Fragment_join_phone_authority fragPhoneAuthority;
    Fragment_join_agreement fragAgree;

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        InitializeView();
        SetListener();
    }

    private void InitializeView()
    {
        btnNext = findViewById(R.id.joinActivity_btn_next);

        fragAgree = new Fragment_join_agreement();
        fragProfile = new Fragment_join_profile();
        fragPhoneAuthority = new Fragment_join_phone_authority();

        setFragment(0); //프래그먼트 교체
    }

    public void SetListener()
    {
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                switch (view.getId()) {
                    case R.id.join_frag_phoneAuthority:
                        //폰 번호 인증한 경우
                        setFragment(R.id.join_frag_agree);
                        break;
                    case R.id.join_frag_agree:
                        //동의서 동의 한 경우
                        setFragment(R.id.join_frag_profile);
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

    public void setFragment(int id)
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
    }

}