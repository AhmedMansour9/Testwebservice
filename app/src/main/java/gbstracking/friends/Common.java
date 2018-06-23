package gbstracking.friends;

/**
 * Created by HP on 19/04/2018.
 */

public class Common {
    public static final String baseURL="https://maps.googleapis.com";
    public static IGoogleApi iGoogleApi(){
        return RetrofitClint.RetrofitClint(baseURL).create(IGoogleApi.class);
    }
}
