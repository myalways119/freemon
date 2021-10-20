package common;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import item.UserInfo;

public class DbRequest
{//Use Volley

    public static class UrlConst
    {
        private static final String ROOT_URL = "https://freemon1.cafe24.com/myfirstdream/php/";
        public static final String INSERT_TB_USER = ROOT_URL + "user_info.php?apicall=INSERT_TB_USER";
        public static final String SELECT_TB_USER = ROOT_URL + "user_info.php?apicall=SELECT_TB_USER";
    }

    public class UpdateRequest
    {

    }

    public class DeleteRequest
    {

    }
}
