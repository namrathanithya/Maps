package com.example.namra.mvvm_map_new.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

//import com.example.namra.mvvm_map_new.FloatingAdapter;
import com.example.namra.mvvm_map_new.R;
import com.example.namra.mvvm_map_new.adapter.FloatingAdapter;
import com.example.namra.mvvm_map_new.model.Example;
import com.example.namra.mvvm_map_new.viewmodel.GeoViewModel;

public class RecyclerActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FloatingAdapter floatingAdapter;
    GeoViewModel geoViewModel;
    Context context;
    Bundle bundle1 = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
    setContentView(R.layout.floating_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.floating_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        floatingAdapter = new FloatingAdapter();
        mRecyclerView.setAdapter(floatingAdapter);

        geoViewModel = ViewModelProviders.of(this).get(GeoViewModel.class);
        bundle1 = getIntent().getExtras().getParcelable("bundle1");
       Example example = bundle1.getParcelable("Data");
       floatingAdapter.setMapList(example);
       floatingAdapter.notifyDataSetChanged();


        Bundle bundle=new Bundle();
       // bundle.putParcelable("Recycler", );
      //  sampleFragment.setArguments(bundle);
        geoViewModel.getDataVal().observe(this, new Observer<Example>() {
            @Override
            public void onChanged(@Nullable Example example) {
                floatingAdapter.setMapList(example);
                floatingAdapter.notifyDataSetChanged();
            }
        });


}}
