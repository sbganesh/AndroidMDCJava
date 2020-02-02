package com.google.codelabs.mdc.java.shrine.staggeredgridlayout;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.codelabs.mdc.java.shrine.NavigationHost;
import com.google.codelabs.mdc.java.shrine.ProductGridFragment;
import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.network.ImageRequester;
import com.google.codelabs.mdc.java.shrine.network.ProductEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter used to show an asymmetric grid of products, with 2 items in the first column, and 1
 * item in the second column, and so on.
 */
public class StaggeredProductCardRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredProductCardViewHolder> {

    private List<ProductEntry> productList;
    private ImageRequester imageRequester;
    private Context context;

    public StaggeredProductCardRecyclerViewAdapter(List<ProductEntry> productList) {
        this.productList = productList;
        imageRequester = ImageRequester.getInstance();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @NonNull
    @Override
    public StaggeredProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.shr_staggered_product_card_first;
        if (viewType == 1) {
            layoutId = R.layout.shr_staggered_product_card_second;
        } else if (viewType == 2) {
            layoutId = R.layout.shr_staggered_product_card_third;
        }

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        context = layoutView.getContext();
        return new StaggeredProductCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull StaggeredProductCardViewHolder holder, int position) {
        if (productList != null && position < productList.size()) {
            ProductEntry product = productList.get(position);
            holder.productTitle.setText(product.title);
            holder.productPrice.setText(product.price);
            imageRequester.setImageFromUrl(holder.productImage, product.url);
            holder.productImage.setClickable(true);
            holder.productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Inside Product Image click");
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(new ComponentName("com.Appless.ARCoreSamples",
                            "com.unity3d.player.UnityPlayerActivity"));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
