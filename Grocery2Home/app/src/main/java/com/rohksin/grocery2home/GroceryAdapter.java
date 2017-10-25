package com.rohksin.grocery2home;

import android.content.Context;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Illuminati on 10/25/2017.
 */

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {


    private Context context;
    private List<Grocery> groceries;

    public GroceryAdapter(Context context, List<Grocery> groceries)
    {
        this.context = context;
        this.groceries = groceries;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.grocery_item_card,parent,false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, int position) {

        Grocery grocery = groceries.get(position);

        holder.groceryImage.setImageResource(android.R.drawable.alert_dark_frame);
        holder.groceryName.setText(grocery.getName());

    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder{


        public ImageView groceryImage;
        public TextView groceryName;

        public GroceryViewHolder(View itemView) {
            super(itemView);

            groceryImage = (ImageView)itemView.findViewById(R.id.groceryImage);
            groceryName = (TextView)itemView.findViewById(R.id.groceryName);



        }
    }
}
