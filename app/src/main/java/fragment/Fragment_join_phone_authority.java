package fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

import co.kr.freemon.R;

public class Fragment_join_phone_authority extends Fragment
{
    View view;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    Button btnClick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_join_phone_authority, container, false);


        return view;
    }

    public void Sent() {
        String phoneNumber = "+821035524552";
        String smsCode = "123456";

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();

        // Configure faking the auto-retrieval with the whitelisted numbers.
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(null)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {
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

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
                        Toast.makeText(null,"인증 완료5", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        // [END auth_test_phone_auto]
    }

}
