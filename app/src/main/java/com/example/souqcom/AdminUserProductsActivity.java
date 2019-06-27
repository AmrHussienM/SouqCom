package com.example.souqcom;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.souqcom.Model.Cart;
import com.example.souqcom.Model.Products;
import com.example.souqcom.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference cartListRef;
    RecyclerView.LayoutManager layoutManager;

    private String userId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);


        recyclerView=findViewById(R.id.products_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        userId=getIntent().getStringExtra("uid");

        cartListRef= FirebaseDatabase.getInstance().getReference()
                .child("cart list").child("admin view").child(userId).child("products");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef,Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {


                holder.txtproductName.setText(model.getpName());
                holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
                holder.txtProductPrice.setText("Price = " + model.getPrice() + " $");


            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_items_layout,viewGroup,false);
                return new CartViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}
