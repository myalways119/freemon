package co.kr.freemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.InitializeView();
        this.SetListener();
    }

    public void InitializeView()
    {
        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_join = (Button) findViewById(R.id.btnJoin);

    }

    public void SetListener()
    {
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                switch (view.getId()) {
                    case R.id.btnLogin:

                        break;
                    case R.id.btnJoin:
                        Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };

        btn_login.setOnClickListener(Listener);
        btn_join.setOnClickListener(Listener);
    }
}