package com.safinaz.matrimony.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.safinaz.matrimony.Model.VendorCategory;
import com.safinaz.matrimony.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.ServiceHolder> {
    private final ServiceAdapterOnClickHandler mCallback;
    List<VendorCategory> vendorCategories = new ArrayList<>();
    private int selectedItem=-1;

    public ServiceCategoryAdapter(ServiceAdapterOnClickHandler mCallback){
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public ServiceCategoryAdapter.ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.vendor_category_item, parent, false);
        return new ServiceHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceCategoryAdapter.ServiceHolder holder, int position) {
        VendorCategory vendorCategory = vendorCategories.get(position);
        Picasso.get().load(vendorCategory.getCategoryImg()).into(holder.categoryImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                //Toast.makeText(holder.itemView.getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        if(vendorCategory.getCategoryName()!=null){
            holder.categoryName.setText(vendorCategory.getCategoryName());
        }

//        if (position == selectedItem) {
//            holder.cardParent.setSelected(true);
//        } else {
//            holder.cardParent.setSelected(false);
//        }
    }

    @Override
    public int getItemCount() {
        if(vendorCategories==null){
            return 0;
        }

        return vendorCategories.size();
    }

    public interface ServiceAdapterOnClickHandler {
        void onClick(VendorCategory vendorCategory);
    }

    public void setVendorData(List<VendorCategory> vendorData) {
        vendorCategories.add(new VendorCategory("101", "https://picsum.photos/seed/picsum/200/300", "Matrimony"));
        vendorCategories.addAll(vendorData);
        notifyDataSetChanged();
    }

    public class ServiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        @BindView(R.id.rl)
//        RelativeLayout cardParent;
        @BindView(R.id.categoryImage)
        ImageView categoryImage;
        @BindView(R.id.categoryName)
        TextView categoryName;
        public ServiceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Save the selected positions to the SparseBooleanArray
            selectedItem = getAdapterPosition();
            notifyDataSetChanged();
            mCallback.onClick(vendorCategories.get(selectedItem));
        }
    }

}
