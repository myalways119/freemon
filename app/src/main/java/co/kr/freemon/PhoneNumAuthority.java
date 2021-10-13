package co.kr.freemon;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PlayGamesAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PhoneNumAuthority {

    static String  phoneNum = "+821035524552";

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    public static void testPhoneVerify() {
        // [START auth_test_phone_verify]
        String phoneNum = "+821035524552";
        String testVerificationCode = "123456";

        // Whenever verification is triggered with the whitelisted number,
        // provided it is not set for auto-retrieval, onCodeSent will be triggered.
        FirebaseAuth auth = FirebaseAuth.getInstance();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNum)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        // Save the verification id somewhere
                        // ...
                        // The corresponding whitelisted code above should be used to complete sign-in.
                        Toast.makeText(null,"관리자에게 문의 바랍니다.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        // Sign in with the credential
                        // ...

                        Toast.makeText(null,"관리자에게 문의 바랍니다.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        // ...
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END auth_test_phone_verify]
    }


    public void testPhoneAutoRetrieve() {
        // [START auth_test_phone_auto]
        // The test phone number and code should be whitelisted in the console.
        String phoneNumber = "+16505554567";
        String smsCode = "123456";

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();

        // Configure faking the auto-retrieval with the whitelisted numbers.
        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode);
    }

}