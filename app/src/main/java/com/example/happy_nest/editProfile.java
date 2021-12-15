package com.example.happy_nest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mapbox.core.utils.TextUtils;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class editProfile extends AppCompatActivity {

    private ImageView close;
    private TextView save, C_pic,changePic;
    private CircleImageView pro_pic1;
    private MaterialEditText name,username,email,address,contactNo;
    private FirebaseUser cur_user;
    private Uri mImgUri;
    private StorageTask uploadTask;
    private StorageReference stroageRef;
    private Uri imageUri;
    private String downloadUri,image,myUrl;
    private String check="";
    private static final int galleryPic = 1;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);
        auth= FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        close = findViewById(R.id.close);
        save = findViewById(R.id.save);
        pro_pic1 = findViewById(R.id.pro_pic);
        name = findViewById(R.id.namee);
        username = findViewById(R.id.usernamee);
        email = findViewById(R.id.email);
        address = findViewById(R.id.addresss);
        contactNo=findViewById(R.id.cntNoo);
        cur_user = FirebaseAuth.getInstance().getCurrentUser();
        userinfoDisplay(name,email,username,address,contactNo);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    updateProfile();

                // Toast.makeText(editProfile.this, "Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateProfile() {
        HashMap<String,Object> map= new HashMap<>();
        map.put("Name",name.getText().toString());
        map.put("Username",username.getText().toString());
        map.put("Email",email.getText().toString());
        map.put("Address",address.getText().toString());
        map.put("ContactNo",contactNo.getText().toString());
        ///  map.put("ImageUri",downloadUri);

        FirebaseDatabase.getInstance().getReference().child("Users").child(cur_user.getUid()).updateChildren(map);

        startActivity(new Intent(editProfile.this, Profile.class));
        Toast.makeText(editProfile.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();

    }




    private void userinfoDisplay(MaterialEditText name, MaterialEditText email, MaterialEditText username, MaterialEditText address,  MaterialEditText contactNo) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(cur_user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                        String name1= snapshot.child("Name").getValue().toString();
                        String email3= snapshot.child("Email").getValue().toString();
                        String username1= snapshot.child("Username").getValue().toString();
                        //String ContactNo= snapshot.child("ContactNo").getValue().toString();
                        //String Address= snapshot.child("Address").getValue().toString();
                        name.setText(name1);
                        email.setText(email3);
                        username.setText(username1);
                        //address.setText(Address);
                        //contactNo.setText(ContactNo);
                        //   address.setText(address1);
                        // contactNo.setText(contactNo1);
                    }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

