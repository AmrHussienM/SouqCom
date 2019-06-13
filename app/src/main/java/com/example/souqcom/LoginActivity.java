package com.example.souqcom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.souqcom.Model.Users;
import com.example.souqcom.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText inputNumber,inputPassword;
    private Button loginButton;
    private ProgressDialog loadingBar;
    private String parentDbName= "Users";
    private CheckBox checkBox;
    private TextView adminLink,notAdminLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        identification();
        Paper.init(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login Admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                parentDbName="Admins";
            }
        });

        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginButton.setText("Login");
                adminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);
                parentDbName="Users";

            }
        });
    }

    private void loginUser()
    {
        String phone=inputNumber.getText().toString();
        String password=inputPassword.getText().toString();

        if (phone.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "missing Field..", Toast.LENGTH_SHORT).show();
        }
        else
            {
                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("please waiting for login..");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();

                AllowAccessToAccount(phone,password);
            }
    }

    private void AllowAccessToAccount(final String phone, final String password)
    {

        if (checkBox.isChecked())
        {
            Paper.book().write(Prevalent.userPhoneKey,phone);
            Paper.book().write(Prevalent.userPasswordKey,password);

        }

        final DatabaseReference rootRef;
        rootRef= FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users userData=dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone))
                    {
                        if (userData.getPassword().equals(password))
                        {

                            if (parentDbName.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this, "welcome admin, logged in succsessfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                goToAdminActvity();

                            }
                            else if (parentDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, "logged in succsessfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                goToHomeActvity();
                                Prevalent.currentOnlineUser=userData;
                            }

                        }
                        else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "password incorrect..", Toast.LENGTH_SHORT).show();
                            }
                    }

                }
                else
                    {
                        Toast.makeText(LoginActivity.this, "Account with this"+ phone + " Number does not exist", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "you need to create new account", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void goToAdminActvity()
    {
        Intent intent=new Intent(LoginActivity.this,adminCategoryActivity.class);
        startActivity(intent);
    }

    private void goToHomeActvity()
    {
        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    private void identification()
    {
        inputNumber=findViewById(R.id.login_phone_number_input);
        inputPassword=findViewById(R.id.login_password_input);
        loginButton=findViewById(R.id.login_btn);
        loadingBar=new ProgressDialog(this);
        checkBox=findViewById(R.id.rememberMe);
        adminLink=findViewById(R.id.admin_panel_link);
        notAdminLink=findViewById(R.id.not_admin_panel_link);
    }
}
