package com.example.souqcom;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.souqcom.Model.AdminOrders;
import com.example.souqcom.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference orderRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_order);

        orderRef= FirebaseDatabase.getInstance().getReference().child("orders");
        recyclerView=findViewById(R.id.order_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options=
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(orderRef,AdminOrders.class)
                .build();

        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter=
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model) {

                        holder.userName.setText("Name: " + model.getName());
                        holder.phoneNumber.setText("Phone: " + model.getPhone());
                        holder.userTotalPrice.setText("Total Amount= $" + model.getTotalAmount());
                        holder.userDateTime.setText("Order at: " + model.getDate() + " " + model.getTime());
                        holder.userShippingAddress.setText("Shipping Address: " + model.getAddress() + ", " + model.getCity());

                        holder.showOrderButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String uID=getRef(position).getKey();


                                Intent intent=new Intent(AdminNewOrderActivity.this,AdminUserProductsActivity.class);
                                intent.putExtra("uid",uID);
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CharSequence options[]=new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };


                                AlertDialog.Builder builder=new AlertDialog.Builder(AdminNewOrderActivity.this);
                                builder.setTitle("Have you shipped this order products");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        if (which==0)
                                        {
                                            String uID=getRef(position).getKey();
                                            removeOrder(uID);

                                        }
                                        else
                                            {
                                                finish();
                                            }
                                    }
                                });

                                builder.show();
                            }
                        });
                    }



                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orders_layout,viewGroup,false);
                        return new AdminOrdersViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    private void removeOrder(String uID)
    {
        orderRef.child(uID).removeValue();

    }

    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder {

        public TextView userName,phoneNumber,userTotalPrice,userDateTime,userShippingAddress;
        public Button showOrderButton;


        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.order_user_name);
            phoneNumber=itemView.findViewById(R.id.order_phone_number);
            userTotalPrice=itemView.findViewById(R.id.order_total_price);
            userDateTime=itemView.findViewById(R.id.order_date_time);
            userShippingAddress=itemView.findViewById(R.id.order_address_city);
            showOrderButton=itemView.findViewById(R.id.show_product_orders_btn);
        }


    }
}
