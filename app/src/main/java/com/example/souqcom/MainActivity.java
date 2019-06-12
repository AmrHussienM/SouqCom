package com.example.souqcom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.souqcom.Model.Users;
import com.example.souqcom.Prevalent.Prevalent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button joinBtn,loginBtn;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        Paper.init(this);

        joinBtn=findViewById(R.id.main_join_btn);
        loginBtn=findViewById(R.id.main_login_btn);
        loadingBar=new ProgressDialog(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLoginActivity();
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegisterActivity();
            }
        });

        String userPhoneKey=Paper.book().read(Prevalent.userPhoneKey);
        String userPasswordKey=Paper.book().read(Prevalent.userPasswordKey);
        if (userPhoneKey != "" && userPasswordKey != "")
        {
            if (!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey))
            {
                AllowAccess(userPhoneKey,userPasswordKey);


                loadingBar.setTitle("Already logged in");
                loadingBar.setMessage("please wait.....");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();

            }
        }


    }

    private void AllowAccess(final String phone, final String password)
    {

        final DatabaseReference rootRef;
        rootRef= FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users userData=dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone))
                    {
                        if (userData.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "logged in succsessfully...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            goToHomeActvity();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "password incorrect..", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account with this"+ phone + " Number does not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "you need to create new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void goToHomeActvity()
    {
        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
    }


    private void gotoRegisterActivity()
    {
        Intent registerIntent=new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(registerIntent);

    }

    private void gotoLoginActivity()
    {
        Intent loginIntent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);

    }

}
