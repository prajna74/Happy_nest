package com.example.happy_nest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import HomesInFeed.HomesInFeed;
import Model.HomeInFeedModel;
import Model.Renters;

public class Notifications extends AppCompatActivity {
    private RecyclerView rentList;

    RecyclerView.LayoutManager layoutManager;
    StorageReference storageReference;
    DatabaseReference mDatabase;
    private FirebaseUser cUser;
    notificationAdapter adapter;
    FirebaseStorage storage;
    List<Renters> mData;

    DatabaseReference rentRef= FirebaseDatabase.getInstance().getReference().child("ConfirmRent");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notifications);
        rentList= findViewById(R.id.rent_recyler);
        // rentList.setLayoutManager(new LinearLayoutManager(this));
        rentList.setHasFixedSize(true);
        Log.d("checked","i am here");
        mData = new ArrayList<>();
        layoutManager = new LinearLayoutManager( this);
        rentList.setLayoutManager(layoutManager);
        cUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // rentRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts").child(cUser.getUid());

        // adapter.startListening();
        data();

    }

    void data(){

        rentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren())
                {
                    Renters data = ds.getValue(Renters.class);
                    Log.d("checked",data.getAddress());
                    mData.add(data);

                }
                Log.d("checked", String.valueOf(mData));
                adapter = new notificationAdapter(mData,rentList.getContext());
                rentList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("checked","failed to retrieve");
            }
        });
    }
}