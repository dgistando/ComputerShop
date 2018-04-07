package com.computer.shop.computershop;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        final Product product = productList.get(position);

        holder.tvTitle.setText(product.getTitle());
        holder.tvDesc.setText(product.getDesc());
        holder.tvRating.setText(String.valueOf(product.getRating()));
        holder.tvPrice.setText(String.valueOf(product.getPrice()));

        //getDrawable(int) deprecated API level 21.
        //Used getDrawable(int,Theme) but no theme here
        //holder.imageView.setImageDrawable(mContext.getResources().getDrawable(product.getImage(), null));
        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher_background));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "clicked item: "+ product.getTitle(), Toast.LENGTH_LONG).show();

                //Intent intent = ((HomeActivity) mContext).getIntent();

                Intent intent = new Intent(mContext, ProductActivity.class);

                intent.putExtra("Title", product.getTitle());
                intent.putExtra("Desc", product.getDesc());
                intent.putExtra("Rating", product.getRating());
                intent.putExtra("Price", product.getPrice());
                intent.putExtra("Image", product.getImage());

                //((HomeActivity) mContext).setResult(((HomeActivity) mContext).RESULT_OK);
                //((HomeActivity)mContext).finish();

                ((HomeActivity) mContext).startActivity(intent);
            }
        });
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
        public RelativeLayout relativeLayout;

        public ProductViewHolder(View view){
            super(view);

            imageView = view.findViewById(R.id.imageView);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDesc = view.findViewById(R.id.tvDesc);
            tvRating = view.findViewById(R.id.tvRating);
            tvPrice = view.findViewById(R.id.tvPrice);
            relativeLayout = (RelativeLayout)view.findViewById(R.id.cardViewRL);
        }
    }


}
