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

    Button btnAllowCamera;
    Button btnAllowPhoneNum;
    Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority);

        InitializeView();
        SetListener();

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<ExpandableListAdapter.Item> data = new ArrayList<>();  // 데이터를 담을 List

//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "전화"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "회원 가입시 인증을 위해서 사용됩니다."));

        //data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "카메라"));
        //data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "사진 업로드를 위해서 사용 됩니다."));

        ExpandableListAdapter.Item item = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "전화");
        item.invisibleChildren = new ArrayList<>();
        item.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "회원 가입시 인증을 위해서 사용됩니다."));

        ExpandableListAdapter.Item item2 = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "카메라");
        item.invisibleChildren = new ArrayList<>();
        item.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "사진 업로드를 위해서 사용 됩니다."));

        data.add(item);
        data.add(item2);

        //recyclerview.setAdapter(new ExpandableListAdapter(data));

        recyclerview.setAdapter(new ExpandableListAdapter(data));
    }


    public void InitializeView()
    {
        btnAllowCamera = (Button) findViewById(R.id.btnAllowCamera);
        btnAllowPhoneNum = (Button) findViewById(R.id.btnAllowPhoneNumber);
        btnComplete = (Button) findViewById(R.id.btnAuthorityComplete);
        btnAllowCamera.setEnabled(true);
        btnAllowPhoneNum.setEnabled(true);

        SetButtonEnable(btnAllowCamera, CommonConst.Permission.PERMISSION_CAMERA);
        SetButtonEnable(btnAllowPhoneNum, CommonConst.Permission.PERMISSION_PHONE_STATE);
    }

    public void SetListener()
    {
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                switch (view.getId()) {
                    case R.id.btnAllowCamera:
                        ActivityCompat.requestPermissions(AuthorityActivity.this, new String[]{CommonConst.Permission.PERMISSION_CAMERA}, CommonConst.Permission.REQUEST_CAMERA_CODE);
                        break;
                    case R.id.btnAllowPhoneNumber:
                        ActivityCompat.requestPermissions(AuthorityActivity.this, new String[]{CommonConst.Permission.PERMISSION_PHONE_STATE}, CommonConst.Permission.REQUEST_READ_PHONE_STATE_CODE);
                        break;
                    case R.id.btnAuthorityComplete:

                        if (Common.CheckPermission(getApplicationContext(), CommonConst.Permission.PERMISSION_CAMERA) == true
                            && Common.CheckPermission(getApplicationContext(), CommonConst.Permission.PERMISSION_PHONE_STATE) == true)
                        {//권한 있는 경우
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class); //로그인 화면
                            startActivity(intent);
                        }
                        else
                        {//권한 없는 경우
                            Toast.makeText(getApplicationContext(),"앱 사용에 해당 권한들은 필수입니다.", Toast.LENGTH_LONG).show();
                        }

                        break;
                }
            }
        };

        btnAllowCamera.setOnClickListener(Listener);
        btnAllowPhoneNum.setOnClickListener(Listener);
        btnComplete.setOnClickListener(Listener);
    }

    private void SetButtonEnable(Button btn, String permission)
    {
        boolean hasPermission = false;
        String askPermision = "권한요청";
        String hasPermision = "설정완료";

        if(permission.isEmpty() == true || btn == null) return;

        if (Common.CheckPermission(this, permission) == true)
        {
            hasPermission = true;
        }

        if (hasPermission == true)
        {
            btn.setEnabled(false);
            btn.setText(hasPermision);
        }
        else
        {
            btn.setEnabled(true);
            btn.setText(askPermision);
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
                SetButtonEnable(btnAllowPhoneNum, CommonConst.Permission.PERMISSION_PHONE_STATE);//Set Button enable
                return;
            case CommonConst.Permission.REQUEST_CAMERA_CODE: //Request Authority of Camera
                SetButtonEnable(btnAllowCamera, CommonConst.Permission.PERMISSION_CAMERA);//Set Button enable
                return;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

