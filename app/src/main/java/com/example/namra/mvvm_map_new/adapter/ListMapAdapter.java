package com.example.namra.mvvm_map_new.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.namra.mvvm_map_new.R;
import com.example.namra.mvvm_map_new.model.Example;
public class ListMapAdapter extends RecyclerView.Adapter<ListMapAdapter.ViewHolder> {

    private Context mCtx;
    private Example mapList;
   TextView place,vicinity;
   View view;
   CardView card;

    public void setMapList(Example mapList){
        this.mapList = mapList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       LayoutInflater inflater = LayoutInflater.from(parent.getContext());
       mCtx = parent.getContext();
        View view = inflater.inflate(R.layout.item_list, parent,false);
        card=(CardView)view.findViewById(R.id.card);
        return new ViewHolder(view);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }



    @Override
    public int getItemCount() {
        if (mapList == null)
        {
            return 0;
        }
        else
            return mapList.getResults().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView place,vicinity;

        public ViewHolder(View itemView) {
            super(itemView);
          //  card = itemView.findViewById(R.id.card);
          place = itemView.findViewById(R.id.place);
          vicinity = itemView.findViewById(R.id.vicinity);

        }

          }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.place.setText(mapList.getResults().get(position).getName());
       holder.vicinity.setText(mapList.getResults().get(position).getVicinity());
    }
}