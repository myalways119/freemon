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

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

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

    //Firebase Phone Number Authority
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

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

        //PhoneNumAuthority.testPhoneVerify();
        //PhoneNumAuthority.testPhoneAutoRetrieve();
        //PhoneAuthProvider.getInstance().verifyPhoneNumber( "+821035524552", 60, TimeUnit.SECONDS, this, null );
        testPhoneAutoRetrieve();
    }


    public void testPhoneAutoRetrieve() {
        // [START auth_test_phone_auto]
        // The test phone number and code should be whitelisted in the console.
        String phoneNumber = "+821035524552";
        String smsCode = "123456";

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();

        Toast.makeText(this.getApplicationContext(),"전화번호 인증1", Toast.LENGTH_SHORT).show();

        // Configure faking the auto-retrieval with the whitelisted numbers.
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        // Instant verification is applied and a credential is directly returned.
                        // ...
                        Toast.makeText(null,"인증 완료2", Toast.LENGTH_SHORT).show();
                    }

                    // [START_EXCLUDE]
                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(null,"인증 완료3", Toast.LENGTH_SHORT).show();
                    }
                    // [END_EXCLUDE]
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        // [END auth_test_phone_auto]
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

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.setLanguageCode("kr");
        // [END start_phone_auth]
    }





}