package com.rohksin.grocery2home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Illuminati on 10/25/2017.
 */

public class ProductListActivity extends AppCompatActivity {


    private RecyclerView productRecyclerView;
    private List<Grocery> groceryList;
    private GroceryAdapter groceryAdapter;


    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference Groceriesreference = reference.child("Groceries");


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);

        productRecyclerView = (RecyclerView)findViewById(R.id.productRecyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(this);

        productRecyclerView.setLayoutManager(llm);
        setUpGroceryList();
        productRecyclerView.setAdapter(groceryAdapter);


        Groceriesreference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Added","Child Added");

                Grocery grocery = dataSnapshot.getValue(Grocery.class);

                //GroceryAdapter groceryAdapter = new GroceryAdapter(ProductListActivity.this, groceryList);
                //productRecyclerView.setAdapter(groceryAdapter);
               // groceryAdapter.notifyDataSetChanged();

                addNewGrocery(grocery);



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void setUpGroceryList()
    {
        groceryList = new ArrayList<Grocery>();
        groceryAdapter = new GroceryAdapter(ProductListActivity.this, groceryList);
        productRecyclerView.setAdapter(groceryAdapter);

    }


    public void addNewGrocery(Grocery grocery)
    {
        Log.d("Inside Add method","Added from add method");
        groceryList.add(0,grocery);
        groceryAdapter.notifyItemChanged(0);
    }

}


































///// Junk Code

        /*
        Groceriesreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //List<Grocery> groceryList = dataSnapshot.getValue(List<Grocery>.class);

                 //groceryList = new ArrayList<Grocery>();

                int count =0;
                for( DataSnapshot children : dataSnapshot.getChildren())
                {
                    Grocery grocery = children.getValue(Grocery.class);
                    groceryList.add(grocery);

                    Log.d("Added",++count+"");
                }

                //GroceryAdapter groceryAdapter = new GroceryAdapter(ProductListActivity.this, groceryList);

               // productRecyclerView.setAdapter(groceryAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        */

//GroceryAdapter groceryAdapter = new GroceryAdapter(this, groceries);

//productRecyclerView.setAdapter(groceryAdapter);

