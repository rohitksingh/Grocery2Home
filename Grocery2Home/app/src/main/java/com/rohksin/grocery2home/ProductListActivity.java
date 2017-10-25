package com.rohksin.grocery2home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Illuminati on 10/25/2017.
 */

public class ProductListActivity extends AppCompatActivity {


    private RecyclerView productRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);

        productRecyclerView = (RecyclerView)findViewById(R.id.productRecyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(this);

        productRecyclerView.setLayoutManager(llm);

        List<Grocery> groceries = new ArrayList<Grocery>();

        for(int i=0;i<10;i++)
        {
            Grocery grocery = new Grocery("ParleG"+i);
            groceries.add(grocery);
        }

        GroceryAdapter groceryAdapter = new GroceryAdapter(this, groceries);

        productRecyclerView.setAdapter(groceryAdapter);


    }

}
