package activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import co.kr.freemon.R;

public class JoinProfileActivity extends AppCompatActivity {
    ImageView imgView;
    ImageView imgViewIcon;
    Button btnComplete;

    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_profile);

        InitializeView();
        SetListener();
    }

    public void InitializeView()
    {
        imgView = findViewById(R.id.join_profile_imageView);
        imgViewIcon = findViewById(R.id.join_profile_imageViewIcon);
        btnComplete = findViewById(R.id.join_profile_btnComplete);

        //IMAGEVIEW.background = getResources().getDrawable(R.drawable.imageview_cornerround, null)
        imgView.setClipToOutline(true);
    }

    public void SetListener()
    {

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>()
        {
            @Override
            public void onActivityResult(ActivityResult result)
            {
                if (result.getResultCode() == RESULT_OK)
                {
                    Intent intent = result.getData();
                    //Log.e(TAG, "intent : " + intent);
                    Uri uri = intent.getData();
                    //Log.e(TAG, "uri : " + uri);
                    imgView.setImageURI(uri);
                    Glide.with(getApplicationContext()).load(uri).into(imgView);
                }
            }
        });

        View.OnClickListener Listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switch (view.getId())
                {
                    case R.id.join_profile_imageView:
                    case R.id.join_profile_imageViewIcon:
                        //갤러리 or 사진 앱 실행하여 사진을 선택하도록..
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        launcher.launch(intent);

                        break;
                    case R.id.join_profile_btnComplete:
                        //Validation
                        //mandatory check/ 아이디 중복 체크(전번)

                        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent1);
                        break;
                }
            }
        };

        imgView.setOnClickListener(Listener);
        imgViewIcon.setOnClickListener(Listener);
        btnComplete.setOnClickListener(Listener);
    }

}