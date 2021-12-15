package com.example.happy_nest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.happy_nest.Adapter.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import Model.HomeInFeedModel;

public class Requestrent extends AppCompatActivity {
    TextView rname, cno, raddress, homename, hpub;
    DatabaseReference hhref, dbbref;
ImageButton imageButton,call;
    String homepublisher, hpubname, homenamee, uname,cn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_requestrent);
        rname = findViewById(R.id.rname);
        homename = findViewById(R.id.homename);
        raddress = findViewById(R.id.raddress);
        imageButton=findViewById(R.id.imageButton);
        call=findViewById(R.id.call);
        cno = findViewById(R.id.cno);
         cn=getIntent().getStringExtra("Rcontactno").toString().trim();
        String homeid = getIntent().getStringExtra("Homeid");
        rname.setText(getIntent().getStringExtra("Rname"));
        raddress.setText(getIntent().getStringExtra("RAddress"));
        cno.setText(getIntent().getStringExtra("Rcontactno"));
        hhref = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
        hhref.child(homeid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HomeInFeedModel home = snapshot.getValue(HomeInFeedModel.class);
                    //homepublisher = home.getPublisher();
                    homename.setText(home.getHomeName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
         imageButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i7=new Intent(Requestrent.this, mail.class);
                 i7.putExtra("oemail",getIntent().getStringExtra("RAddress"));
                 startActivity(i7);
             }
         });
         call.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View vv) {
                 Intent i8=new Intent(Intent.ACTION_CALL);
                 i8.setData(Uri.parse("tel:"+cn));
                 startActivity(i8);
             }
         });

    }
}

