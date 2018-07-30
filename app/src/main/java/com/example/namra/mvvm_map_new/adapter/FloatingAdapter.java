package com.example.namra.mvvm_map_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.namra.mvvm_map_new.R;
import com.example.namra.mvvm_map_new.model.Example;

public class FloatingAdapter extends RecyclerView.Adapter<FloatingAdapter.ViewHolder> {

    private Context context;
    private Example mapList;
    TextView place,vicinity;
    View view;

    public void setMapList(Example mapList) {
        this.mapList = mapList;
    }

    @Override
    public FloatingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.floating_adapter, parent,false);
        return new FloatingAdapter.ViewHolder(view);
    }

    @Override
    public void onViewRecycled(FloatingAdapter.ViewHolder holder) {
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
        TextView place2,vicinity2,icon,scope;
        ImageView coverImage;

        public ViewHolder(View itemView) {
            super(itemView);

            //card = itemView.findViewById(R.id.card);
            place2 = itemView.findViewById(R.id.textView1);
            vicinity2 = itemView.findViewById(R.id.textView2);
            icon = itemView.findViewById(R.id.textView3);
            scope = itemView.findViewById(R.id.textView4);
            coverImage = itemView.findViewById(R.id.imageView1);

        }

    }
    @Override
    public void onBindViewHolder(FloatingAdapter.ViewHolder holder, int position) {
        holder.place2.setText(mapList.getResults().get(position).getName());
        holder.vicinity2.setText(mapList.getResults().get(position).getVicinity());
        holder.icon.setText(mapList.getResults().get(position).getIcon());
        holder.scope.setText(mapList.getResults().get(position).getScope());
        holder.coverImage.setImageResource(R.mipmap.ic_launcher);


    }

}
