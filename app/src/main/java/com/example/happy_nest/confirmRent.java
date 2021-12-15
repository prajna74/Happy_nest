package com.example.happy_nest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class confirmRent extends AppCompatActivity {
    private EditText cName,cAddress,cNo;
    private ImageButton confirm,imageButton,c;
    String saveCurrentDate;
    String saveCurrentTime;
    FirebaseAuth mAuth;
    String homeid,oemail,ocno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_confirm_rent);
        FirebaseUser cur_user= FirebaseAuth.getInstance().getCurrentUser();
        mAuth=FirebaseAuth.getInstance();
        cName=findViewById(R.id.con_Name);
        cAddress= findViewById(R.id.con_address);
        cNo= findViewById(R.id.con_phnNo);
        confirm= findViewById(R.id.confirm);
        imageButton=findViewById(R.id.imageButton);
        c=findViewById(R.id.c);
        ocno=getIntent().getStringExtra("ocno").toString().trim();
         homeid=getIntent().getStringExtra("homeid");
         oemail=getIntent().getStringExtra("oemail");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checking();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i6=new Intent(confirmRent.this, mail.class);
                i6.putExtra("oemail",oemail);
                startActivity(i6);
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                Intent i9=new Intent(Intent.ACTION_CALL);
                i9.setData(Uri.parse("tel:"+ocno));
                startActivity(i9);
            }
        });

    }

    private void checking() {
        if (TextUtils.isEmpty(cName.getText().toString()))
        {
            Toast.makeText(this, "Please type your name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cAddress.getText().toString()))
        {
            Toast.makeText(this, "Please provide your address", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(cNo.getText().toString()))
        {
            Toast.makeText(this, "Please provide your contact no", Toast.LENGTH_SHORT).show();
        }
        else{
            confirmation();
        }

    }

    private void confirmation() {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate= currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH: mm: ss a");
        saveCurrentTime= currentTime.format(calendar.getTime());
        DatabaseReference rentRef= FirebaseDatabase.getInstance().getReference().child("ConfirmRent");

        HashMap<String,Object> map= new HashMap<>();
        map.put("Name",cName.getText().toString());
        map.put("ContactNo",cNo.getText().toString());
        map.put("Address",cAddress.getText().toString());
        map.put("State","Not Rented yet");
        map.put("HomeId",homeid);
        rentRef.child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(confirmRent.this, "You've successfully done the rent request", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(confirmRent.this, HomePage.class));
                }
            }
        });
    }
}