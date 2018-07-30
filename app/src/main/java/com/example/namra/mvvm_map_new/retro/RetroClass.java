package com.example.namra.mvvm_map_new.retro;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.namra.mvvm_map_new.model.Example;
import com.google.android.gms.maps.GoogleMap;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClass extends Application {
    public static RetrofitMaps retrofitMaps;
    GoogleMap googleMap;
    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 8000;
    public static final String url = "https://maps.googleapis.com/maps/";
    MutableLiveData<Example> liveData;
    static RetroClass app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = RetroClass.this;
    }

    public RetrofitMaps getRetrofit() {

        if (retrofitMaps == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).readTimeout(10000, TimeUnit.SECONDS).connectTimeout(15000, TimeUnit.SECONDS).addInterceptor(interceptor).build();

            Retrofit retrofit= new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            retrofitMaps = retrofit.create(RetrofitMaps.class);
            return retrofitMaps;}

        else {
            return retrofitMaps;
        }
    }

    public  LiveData<Example> setMapLiveData(String string, String str , int radius)
    {

        final MutableLiveData<Example> pojomutable = new MutableLiveData<>();
        RetrofitMaps retrofitMaps = getRetrofit();
       //Call<Example> call = getRetrofit().getNearbyPlaces(string,str,PROXIMITY_RADIUS);

        retrofitMaps.getNearbyPlaces(string,str,radius).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                try{
                    Example example=response.body();
                    Log.e("response",example.getResults().get(0).getGeometry().getLocation().getLat().toString());
                    pojomutable.postValue(example);


                } catch (NullPointerException e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                Log.d("onFailure", t.toString());
                t.printStackTrace();
                PROXIMITY_RADIUS += 10000;

            }
        });

        return pojomutable;

    }

    private static GsonConverterFactory getApiConvertorFactory() {
        return GsonConverterFactory.create();
    }

    public static RetroClass getApp() {
        return app;
    }

}




