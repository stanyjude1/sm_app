package com.safinaz.matrimony.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.safinaz.matrimony.Model.Vendor;
import com.safinaz.matrimony.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.VendorHolder> {
    private final VendorAdapterOnClickHandler mCallback;
    List<Vendor> vendorList = new ArrayList<>();

    public VendorAdapter(VendorAdapterOnClickHandler mCallback){
        this.mCallback = mCallback;
    }
    @NonNull
    @Override
    public VendorAdapter.VendorHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.vendor_item, parent, false);
        return new VendorHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorAdapter.VendorHolder holder, int position) {
        Vendor vendor = vendorList.get(position);
        Picasso.get().load(vendor.getPhotoUrl()).into(holder.vendorPhoto, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(holder.itemView.getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        if(vendor.getVendorName()!=null){
            holder.vendorName.setText(vendor.getVendorName());
        }
        if(vendor.getAbout()!=null){
            holder.vendorDetail.setText(vendor.getAbout());
        }
    }

    @Override
    public int getItemCount() {
        if(vendorList==null){
            return 0;
        }
        return vendorList.size();
    }

    public void setVendorData(List<Vendor> vendorData) {
        Log.e("Test",vendorData.toString());
        Collections.sort(vendorData, new Comparator<Vendor>() {
            @Override
            public int compare(Vendor lhs, Vendor rhs) {
                return (Double.compare(rhs.getRating(), lhs.getRating()));
            }
        });
        vendorList = vendorData;
        notifyDataSetChanged();
    }

    public interface VendorAdapterOnClickHandler {
        void onClick(Vendor vendor, ImageView vendorImage);
    }

    public class VendorHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.vendor_photo)
        ImageView vendorPhoto;
        @BindView(R.id.vendor_name)
        TextView vendorName;
        @BindView(R.id.vendor_detail)
        TextView vendorDetail;
        public VendorHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCallback.onClick(vendorList.get(adapterPosition), vendorPhoto);
        }
    }
}
