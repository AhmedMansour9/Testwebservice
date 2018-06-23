package gbstracking.friends;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by HP on 19/04/2018.
 */

public class RetrofitClint {
    private static Retrofit retrofit=null;

    public static Retrofit RetrofitClint (String path){
     if(retrofit==null){

         retrofit =new Retrofit.Builder()
                 .baseUrl(path)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .build();
     }
return retrofit;
    }
}
