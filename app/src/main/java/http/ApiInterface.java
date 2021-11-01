package http;

import java.util.List;
import item.LargeContent;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface
{
    @GET("common.php")
    Call<List<LargeContent>> selectLargeContent(@Query("type") String type);
}
