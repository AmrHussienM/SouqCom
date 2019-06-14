package com.example.souqcom.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.souqcom.Interface.ItemClickListner;
import com.example.souqcom.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName,txtProductDescription,txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName=itemView.findViewById(R.id.product_name);
        txtProductDescription=itemView.findViewById(R.id.product_description);
        imageView=itemView.findViewById(R.id.product_image);
        txtProductPrice=itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner=listner;
    }

    @Override
    public void onClick(View v)
    {
        listner.onClick(v,getAdapterPosition(),false);

    }
}
