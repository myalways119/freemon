package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import common.Common;
import common.CommonConst;
import co.kr.freemon.R;
import common.*;

public class AuthorityActivity extends AppCompatActivity {
    private boolean hasPermission = false;
    private boolean hasLoginInfo = false;

    private RecyclerView recyclerview;
    Button btnAllow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority);

        InitializeView();
        SetListener();
    }

    public void InitializeView()
    {
        btnAllow = (Button) findViewById(R.id.auth_btnAllow);

        //Recycle View(권한 신청 화면)
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<ExpandableListAdapter.Item> data = new ArrayList<>();  // 데이터를 담을 List

        ExpandableListAdapter.Item item = new ExpandableListAdapter.Item(ExpandableListAdapter.Icon.phone, ExpandableListAdapter.HEADER, "전화");
        item.invisibleChildren = new ArrayList<>();
        item.invisibleChildren.add(new ExpandableListAdapter.Item(null, ExpandableListAdapter.CHILD, "회원 가입시 인증을 위해서 사용됩니다."));

        ExpandableListAdapter.Item item2 = new ExpandableListAdapter.Item(ExpandableListAdapter.Icon.camera, ExpandableListAdapter.HEADER, "카메라");
        item2.invisibleChildren = new ArrayList<>();
        item2.invisibleChildren.add(new ExpandableListAdapter.Item(null, ExpandableListAdapter.CHILD, "사진 업로드를 위해서 사용 됩니다."));

        data.add(item);
        data.add(item2);

        recyclerview.setAdapter(new ExpandableListAdapter(data));
        // End
    }

    public void SetListener()
    {
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                switch (view.getId())
                {
                    case R.id.auth_btnAllow:
                        boolean hasCameraPermission = Common.CheckPermission(getApplicationContext(), CommonConst.Permission.PERMISSION_CAMERA);
                        boolean hasPhoneStatePermission = Common.CheckPermission(getApplicationContext(), CommonConst.Permission.PERMISSION_PHONE_STATE);

                        if (hasCameraPermission == false)
                        {//카메라 권한 요청
                            ActivityCompat.requestPermissions(AuthorityActivity.this, new String[]{CommonConst.Permission.PERMISSION_CAMERA}, CommonConst.Permission.REQUEST_CAMERA_CODE);
                        }

                        if (hasPhoneStatePermission == false)
                        {//폰 번호 권한 요청
                            ActivityCompat.requestPermissions(AuthorityActivity.this, new String[]{CommonConst.Permission.PERMISSION_PHONE_STATE}, CommonConst.Permission.REQUEST_READ_PHONE_STATE_CODE);
                        }

                        GoNextActivity();

                        break;
                }
            }
        };

        //btnAllowCamera.setOnClickListener(Listener);
        //btnAllowPhoneNum.setOnClickListener(Listener);
        btnAllow.setOnClickListener(Listener);
    }

    private void GoNextActivity()
    {
        //권한 요청후 한번 더 확인
        boolean hasCameraPermission = Common.CheckPermission(getApplicationContext(), CommonConst.Permission.PERMISSION_CAMERA);
        boolean hasPhoneStatePermission = Common.CheckPermission(getApplicationContext(), CommonConst.Permission.PERMISSION_PHONE_STATE);

        if (hasCameraPermission == true && hasPhoneStatePermission == true)
        {
            //자동 로그인 정보가 있는 경우, Main 화면으로 이동
            //아니면 Login화면
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class); //로그인 화면
            startActivity(intent);
        }
    }

    private void ExitProgram() {
        // 종료
        if (Build.VERSION.SDK_INT >= 21) {
            finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
        } else {
            // 액티비티 종료
            finish();
        }
        System.exit(0);
    }

    //권한 요청에 대한 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case CommonConst.Permission.REQUEST_READ_PHONE_STATE_CODE: //Request Authority of Phone Number
                GoNextActivity();
                return;
            case CommonConst.Permission.REQUEST_CAMERA_CODE: //Request Authority of Camera
                GoNextActivity();
                return;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

