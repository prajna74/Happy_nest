package com.example.happy_nest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAHome extends AppCompatActivity {


        FirebaseAuth mAuth;
    Toolbar toolbar;
        private ImageButton homeImg;
        private EditText description;
        private StorageReference picOfPostHome;
        private static final int galleryPic = 1;
        private Uri ImageUri;


        String nameHome, contactNo, beds, price, address,localArea;
        String saveCurrentDate, saveCurrentTime, descrip;
        private String randomKey;
        NavigationView sidenav;
        ActionBarDrawerToggle toggle;
        DrawerLayout drawerLayout;
        private String downloadUri, iUri;

        private Button homePic, details;
        private ImageButton postBtn, tracMap;
        private TextView text;
        private EditText homeName, homeaddress, rent;
        private EditText phoNo, room;
        private ProgressDialog pd;

        private DatabaseReference postDataRef;
        CircleImageView SNpropic;

        private FirebaseAuth auth;
        private DatabaseReference databaseReference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
            getSupportActionBar().hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_post_a_home);
            mAuth = FirebaseAuth.getInstance();

            auth = FirebaseAuth.getInstance();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

            retrivePicture();

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar2);
            sidenav = (NavigationView) findViewById(R.id.sidenavmenu);
            SNpropic = (CircleImageView) sidenav.getHeaderView(0).findViewById(R.id.profile_pic_SN);
            drawerLayout = (DrawerLayout) findViewById(R.id.draw);
            toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                    R.string.open,
                    R.string.close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            sidenav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.profileSN:
                            //Toast.makeText(getApplicationContext(), "Profile will Open", Toast.LENGTH_LONG).show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            Intent intent = new Intent(PostAHome.this, Profile.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                            break;
                        case R.id.mypostsSN:
                            //Toast.makeText(getApplicationContext(), "Myposts will Open", Toast.LENGTH_LONG).show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            Intent intent1 = new Intent(PostAHome.this, MyPosts.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);

                            break;
                        case R.id.notificationSN:
                            //Toast.makeText(getApplicationContext(), "Notifications will Open", Toast.LENGTH_LONG).show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            Intent intent2 = new Intent(PostAHome.this, Notifications.class);
                            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent2);

                            break;
                        case R.id.settingsSN:
                            //Toast.makeText(getApplicationContext(), "Settings will Open", Toast.LENGTH_LONG).show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            Intent intent3 = new Intent(PostAHome.this, Settings.class);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent3);

                            break;
                        case R.id.exitSN:
                            Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_LONG).show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            FirebaseAuth.getInstance().signOut();

                            Intent intent7 = new Intent(PostAHome.this, MainActivity.class);
                            startActivity(intent7);
                            break;
                        case R.id.logoutSN:
                            Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            FirebaseAuth.getInstance().signOut();

                            Intent intent5 = new Intent(PostAHome.this, Login.class);
                            startActivity(intent5);
                            break;
                        case R.id.aboutusSN:
                            //  Toast.makeText(getApplicationContext(), "About Us will Open", Toast.LENGTH_LONG).show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            Intent intent4 = new Intent(PostAHome.this, AboutUs.class);
                            intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent4);

                            break;
                    }
                    return true;
                }
            });


            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.postAHome);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.homePage:
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.search:
                            startActivity(new Intent(getApplicationContext(), search.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.postAHome:
                            return true;

                    }
                    return false;
                }

            });




            picOfPostHome = FirebaseStorage.getInstance().getReference().child("home_pictures");

            postDataRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
            homeImg = findViewById(R.id.homeImage);

            description = findViewById(R.id.des);

            postBtn = findViewById(R.id.button_post);

            homeName = findViewById(R.id.homeName);
            rent = findViewById(R.id.rentRange);
            phoNo = findViewById(R.id.phnNo);
            room = findViewById(R.id.room);
            pd = new ProgressDialog(this);

           homeaddress=findViewById(R.id.address);

            homeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    final int ACTIVITY_SELECT_IMAGE = 1234;
                    startActivityForResult(i, galleryPic);
                }
            });
            postBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collectData();
                }
            });


        }

        private void retrivePicture() {
            databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.hasChild("image")) {
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(SNpropic);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == galleryPic && resultCode == RESULT_OK && data != null) {
                ImageUri = data.getData();
                homeImg.setImageURI(ImageUri);
            }

        }


        private void collectData() {
            nameHome = homeName.getText().toString();
            contactNo = phoNo.getText().toString();
            beds = room.getText().toString();
            price = rent.getText().toString();
            localArea=homeaddress.getText().toString();

            // localArea= subArea.getText().toString();
            descrip = description.getText().toString();
            // area=SelectDistrict.toString();

            if (ImageUri == null) {
                Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
           } else
                if (TextUtils.isEmpty(contactNo)) {
                Toast.makeText(this, "Please give your Contact No, its mandatory", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(beds)) {
                Toast.makeText(this, "Please provide all the information", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(price)) {
                Toast.makeText(this, "Please provide all the information", Toast.LENGTH_SHORT).show();
            }
            else {
                storeData();
            }
        }

        private void storeData() {
            pd.setMessage("Posting");
            pd.show();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH: mm: ss a");
            saveCurrentTime = currentTime.format(calendar.getTime());

            randomKey = saveCurrentDate + saveCurrentTime;

            final StorageReference file = picOfPostHome.child(ImageUri.getLastPathSegment() + randomKey + ".jpg");

            final UploadTask uploadTask = file.putFile(ImageUri);


            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                    Toast.makeText(PostAHome.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(PostAHome.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            downloadUri = file.getDownloadUrl().toString();
                            return file.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadUri = task.getResult().toString();
                                Toast.makeText(PostAHome.this, "Done", Toast.LENGTH_SHORT).show();
                                UpdateDatabase();
                            }
                        }
                    });
                }
            });

        }

        private void UpdateDatabase() {
            HashMap<String, Object> map = new HashMap<>();
            map.put("pId", randomKey);
            map.put("date", saveCurrentDate);
            map.put("time", saveCurrentTime);
            map.put("image", downloadUri);
            map.put("homeName", nameHome);
            map.put("contactNo", contactNo);
            map.put("room", beds);
            map.put("rentCost", price);
            map.put("description", descrip);
            map.put("localArea",localArea);
            map.put("Publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
            map.put("Email",FirebaseAuth.getInstance().getCurrentUser().getEmail());


            postDataRef.child(randomKey).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        pd.dismiss();
                        Toast.makeText(PostAHome.this, "Posted", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        String msg = task.getException().toString();
                        Toast.makeText(PostAHome.this, "Error" + msg, Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
