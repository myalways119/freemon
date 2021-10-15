package common;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DbRequest
{//Use Volley
    public static RequestQueue requestqueue; //한번만 초기화하면 계속 사용 가능

    public static class UrlConst
    {
        final private static String  baseUrl = "https://freemon1.cafe24.com/";
    }

    public class InsertRequest
    {
        //서버 url 설정(php파일 연동)
        final static  private String URL=  UrlConst.baseUrl + "ConnectionTest.php";
    }

    public class UpdateRequest
    {

    }

    public class DeleteRequest
    {

    }
}
