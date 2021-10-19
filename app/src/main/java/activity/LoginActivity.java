package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.kr.freemon.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnJoin;
    Button btnFindPrevPhoneNumAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.InitializeView();
        this.SetListener();
    }

    public void InitializeView()
    {
        btnLogin = (Button) findViewById(R.id.login_btnLogin);
        btnJoin = (Button) findViewById(R.id.login_btnJoin);
        btnFindPrevPhoneNumAccount = (Button) findViewById(R.id.login_btnFindPrevPhoneNum);
    }

    public void SetListener()
    {
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                switch (view.getId()) {
                    case R.id.login_btnLogin:
                        Intent intent = new Intent(getApplicationContext(), JoinAgreeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.login_btnJoin:
                        Intent joinIntent = new Intent(getApplicationContext(), JoinAgreeActivity.class);
                        startActivity(joinIntent);
                        break;
                    case R.id.login_btnFindPrevPhoneNum:
                        //Check Login Information from DB
                        break;
                }
            }
        };

        btnLogin.setOnClickListener(Listener);
        btnJoin.setOnClickListener(Listener);
        btnFindPrevPhoneNumAccount.setOnClickListener(Listener);
    }
}