package gbstracking.friends;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by HP on 19/04/2018.
 */

public interface IGoogleApi {
    @GET
    Call<String> getPath(@Url String url );
}
