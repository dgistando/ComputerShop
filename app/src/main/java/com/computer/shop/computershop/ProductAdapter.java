package com.computer.shop.computershop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private Context mContext;
    private List<Product> productList;

    public ProductAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_home, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.tvTitle.setText(product.getTitle());
        holder.tvDesc.setText(product.getDesc());
        holder.tvRating.setText(String.valueOf(product.getRating()));
        holder.tvPrice.setText(String.valueOf(product.getPrice()));

        //getDrawable(int) deprecated API level 21.
        //Used getDrawable(int,Theme) but no theme here
        //holder.imageView.setImageDrawable(mContext.getResources().getDrawable(product.getImage(), null));
        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher_background));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvTitle,
                 tvDesc,
                 tvRating,
                 tvPrice;

        public ProductViewHolder(View view){
            super(view);

            imageView = view.findViewById(R.id.imageView);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDesc = view.findViewById(R.id.tvDesc);
            tvRating = view.findViewById(R.id.tvRating);
            tvPrice = view.findViewById(R.id.tvPrice);
        }
    }
}
