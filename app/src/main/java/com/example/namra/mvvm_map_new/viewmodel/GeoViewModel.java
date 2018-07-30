package com.example.namra.mvvm_map_new.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.namra.mvvm_map_new.model.Example;
import com.example.namra.mvvm_map_new.retro.RetroClass;
import com.example.namra.mvvm_map_new.retro.RetrofitMaps;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeoViewModel extends AndroidViewModel {

    private Example example;
    private GoogleMap mMap;
    String string;

    private int PROXIMITY_RADIUS = 10000;

    private RetroClass retroClass ;
    private LiveData<Example> liveData;
    public LiveData<Example> getDataVal() {
        //  if(example == null)
        // {
        //  example = retroClass.setMapLiveData(stringBuffer);
        // }
        return liveData;
    }
    public GeoViewModel(@NonNull Application application) {

        super(application);
        retroClass = new RetroClass();
        liveData = new MutableLiveData<>();
    }

   public void getMapLiveData(String s,String locatn, int rad) {


    // this.string=string;
      //  if (stringBuffer == null) {
       //    stringBuffer.insert(0, "restaurant");
           liveData = retroClass.setMapLiveData(s,locatn,rad);
       }
   // }




    //


   // public LiveData<Example> getMapLiveData()
  //  {
  //      return liveData;
    }
