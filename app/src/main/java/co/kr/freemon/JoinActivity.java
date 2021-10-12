package co.kr.freemon;
import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class JoinActivity extends AppCompatActivity {
    FragmentManager fm;
    FragmentTransaction tran;

    //Fragments
    Fragment_join_profile fragProfile;
    Fragment_join_phone_authority fragPhoneAuthority;
    Fragment_join_agreement fragAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        fragProfile = new Fragment_join_profile();
        fragPhoneAuthority = new Fragment_join_phone_authority();
        fragAgree = new Fragment_join_agreement();
        setFrag(0); //프래그먼트 교체
    }

    public void setFrag(int n)
    {//프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        switch (n)
        {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.joing_fragment_frame, fragPhoneAuthority).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.joing_fragment_frame, fragProfile).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.joing_fragment_frame, fragAgree).commit();
                break;
        }
    }

}