package com.example.mobilki_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    //private final List<Product> products;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> states) {
        this.products = states;
        this.inflater = LayoutInflater.from(context);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = new ArrayList<>();
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // static class?
    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView, selectView;
        final TextView descriptionView, manufacturerView, priceView;


        ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.product_image);
            descriptionView = view.findViewById(R.id.description);
            manufacturerView = view.findViewById(R.id.manufacturer);
            priceView = view.findViewById(R.id.price);
            selectView = view.findViewById(R.id.imageViewSelect);
        }

        void bind(final Product product){
            if (product.isFavorite()){
                selectView.setImageDrawable(selectView.getContext().getDrawable(R.drawable.ic_star));
            } else {
                selectView.setImageDrawable(selectView.getContext().getDrawable(R.drawable.ic_star_border));
            }

            imageView.setImageBitmap(product.getProductBmp());
            descriptionView.setText(product.getDescription());
            manufacturerView.setText(product.getManufacturer());
            priceView.setText(String.valueOf(product.getPrice() / 100) + " руб. " + String.valueOf(product.getPrice() % 100) + " коп. ");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (product.isFavorite()){
                        selectView.setImageDrawable(selectView.getContext().getDrawable(R.drawable.ic_star_border));
                        product.setFavorite(false);
                    } else {
                        selectView.setImageDrawable(selectView.getContext().getDrawable(R.drawable.ic_star));
                        product.setFavorite(true);
                    }
                }
            });

        }
    }
}

// https://www.youtube.com/watch?v=MejWtAwg5Tg