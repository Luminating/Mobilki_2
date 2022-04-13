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


public class ProductFavoriteAdapter extends RecyclerView.Adapter<ProductFavoriteAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private List<Product> products;
    private int selectedPosition = -1;

    public ProductFavoriteAdapter(Context context, List<Product> states) {
        this.products = states;
        this.inflater = LayoutInflater.from(context);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = new ArrayList<>();
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public ProductFavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductFavoriteAdapter.ViewHolder holder, int position) {
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
            if (selectedPosition == -1){
                selectView.setImageDrawable(selectView.getContext().getDrawable(R.drawable.ic_check_box_outline));
            } else if (selectedPosition == getAdapterPosition()){
                selectView.setImageDrawable(selectView.getContext().getDrawable(R.drawable.ic_check_box));
            } else {
                selectView.setImageDrawable(selectView.getContext().getDrawable(R.drawable.ic_check_box_outline));
            }

            imageView.setImageBitmap(product.getProductBmp());
            descriptionView.setText(product.getDescription());
            manufacturerView.setText(product.getManufacturer());
            priceView.setText(String.valueOf(product.getPrice() / 100) + " руб. " + String.valueOf(product.getPrice() % 100) + " коп. ");


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectView.setImageDrawable(selectView.getContext().getDrawable(R.drawable.ic_check_box));
                    if (selectedPosition != getAdapterPosition()){
                        notifyItemChanged(selectedPosition);
                        selectedPosition = getAdapterPosition();
                        Catalog.getInstance().setSelectedProduct(product);
                    }
                }
            });


        }
    }

    public Product getSelected(){
        if (selectedPosition != -1){
            return products.get(selectedPosition);
        }
        return null;
    }
}
