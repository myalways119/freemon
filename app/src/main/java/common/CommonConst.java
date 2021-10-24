package common;

public class CommonConst {

    //Preference
    //public static final String PrefKeyId ="PrefKeyId";
    //public static final String PrefKeyPw ="PrefKeyPw";

    //public static final String SharedPreferencesFileName ="SharedPreFile";

    public class Permission
    {
        public static final int REQUEST_CAMERA_CODE = 1;
        public static final int REQUEST_READ_PHONE_STATE_CODE = 2;

        public static final String PERMISSION_PHONE_STATE = android.Manifest.permission.READ_PHONE_STATE;
        public static final String PERMISSION_CAMERA = android.Manifest.permission.CAMERA;
    }

    public static class Url
    {
        private static final String ROOT_URL = "https://freemon1.cafe24.com/myfirstdream/php/";
        public static final String INSERT_TB_USER = ROOT_URL + "user_info.php?apicall=INSERT_TB_USER";
        public static final String SELECT_TB_USER = ROOT_URL + "user_info.php?apicall=SELECT_TB_USER";
        public static final String COMMON_JOIN_AGREE_CONTENT = ROOT_URL + "common.php?apicall=SELECT_LARGE_CONTENT";
    }
}
