package NetworkClient;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    public static String BASE_URL = "https://api.yelp.com";
    public static String API_KEY = "SoMdI1hozERcSsJ_Uz8KzN6iRP_JC2-1FbF6ZRZja1A0UQrlZQWvwz0bpitn5feP1FNf03vxG_zkoP4pT85IiPFv2JlhO04Oicr_HPZTvIHgDJpklykamtQ-cobHXXYx";
    private static  int timeOut = 30;
    private static NetworkClient mInstance;

    private OkHttpClient getHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("Authorization"," Bearer "+API_KEY).build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }
    public static synchronized NetworkClient getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkClient();
        }
        return mInstance;
    }
    public ApiService getClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
                .build()
                .create(ApiService.class);
    }
}