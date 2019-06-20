package com.example.souqcom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.souqcom.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText name,phone,address,city;
    private Button confirmOrder;
    private ProgressDialog loadingBar;

    private String totalAmount="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount=getIntent().getStringExtra("total price");
        Toast.makeText(this, "Total price = "+ totalAmount, Toast.LENGTH_SHORT).show();

        identification();

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                check();

            }
        });
    }

    private void check()
    {
        if (TextUtils.isEmpty(name.getText().toString()))
        {
            Toast.makeText(this, "please provide your full name.", Toast.LENGTH_SHORT).show();
        }


        else if (TextUtils.isEmpty(phone.getText().toString()))
        {
            Toast.makeText(this, "please provide your phone.", Toast.LENGTH_SHORT).show();
        }


        else if (TextUtils.isEmpty(address.getText().toString()))
        {
            Toast.makeText(this, "please provide your address.", Toast.LENGTH_SHORT).show();
        }


        else if (TextUtils.isEmpty(city.getText().toString()))
        {
            Toast.makeText(this, "please provide your city.", Toast.LENGTH_SHORT).show();
        }
        else
            {
                loadingBar.setTitle("Order");
                loadingBar.setMessage("please waiting for confirming order..");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();

                confirmOrdering();
            }
    }

    private void confirmOrdering()
    {
        String saveCurrentTime,saveCurrentDate;

        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());


        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());

        final DatabaseReference orderRef= FirebaseDatabase.getInstance().getReference()
                .child("orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String,Object> orderMaps=new HashMap<>();
        orderMaps.put("totalAmount",totalAmount);
        orderMaps.put("name",name.getText().toString());
        orderMaps.put("phone",phone.getText().toString());
        orderMaps.put("address",address.getText().toString());
        orderMaps.put("city",city.getText().toString());
        orderMaps.put("date",saveCurrentDate);
        orderMaps.put("time",saveCurrentTime);
        orderMaps.put("state","not shipped");

        orderRef.updateChildren(orderMaps).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("cart list")
                            .child("user view")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(ConfirmFinalOrderActivity.this, "your final order has been placed successfully.", Toast.LENGTH_SHORT).show();

                                        Intent intent=new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });
                }

            }
        });


    }

    private void identification()
    {
        name=findViewById(R.id.shippment_name);
        phone=findViewById(R.id.shippment_phone);
        address=findViewById(R.id.shippment_address);
        city=findViewById(R.id.shippment_city);
        confirmOrder=findViewById(R.id.confirm_order_button);
        loadingBar=new ProgressDialog(this);
    }
}
