package com.safinaz.matrimony.Activities;



import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.safinaz.matrimony.Adapter.VendorAdapter;
import com.safinaz.matrimony.Model.Vendor;
import com.safinaz.matrimony.R;
import com.safinaz.matrimony.viewmodel.VendorActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.safinaz.matrimony.Utility.Constants.VENDOR_DATA;

public class VendorActivity extends AppCompatActivity implements VendorAdapter.VendorAdapterOnClickHandler {
    @BindView(R.id.vendor_rv)
    RecyclerView vendorRv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private VendorActivityViewModel vendorActivityViewModel;
    private VendorAdapter vendorAdapter;
    private List<Vendor> vendorList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        ButterKnife.bind(this);
        toolbar.setTitle("Vendors");
        setSupportActionBar(toolbar);
        setUpRecyclerView();
        initViewModel();
    }

    private void initViewModel(){
        vendorActivityViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(VendorActivityViewModel.class);
        vendorActivityViewModel.init();
        vendorActivityViewModel.getVendors().observe(this, new Observer<List<Vendor>>() {
            @Override
            public void onChanged(@Nullable List<Vendor> vendors) {
                if(vendors!=null){
                    vendorList.addAll(vendors);
                    vendorAdapter.setVendorData(vendorList);
                }
            }
        });
    }

    private void setUpRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        vendorRv.setLayoutManager(gridLayoutManager);
        vendorRv.setHasFixedSize(true);
        vendorAdapter = new VendorAdapter(this);
        vendorRv.setAdapter(vendorAdapter);
        if (vendorAdapter.getItemCount() == 0) {
            Log.e("Test","I am here");
//            errorImage.setVisibility(View.VISIBLE);
//            errorView.setVisibility(View.VISIBLE);
//            if (!isOnline()) {
//                errorView.setText(R.string.oops_network_error);
//            }
        }

    }

    @Override
    public void onClick(Vendor vendor, ImageView imageView) {
        Intent i = new Intent(this, VendorDetail.class);
        i.putExtra(VENDOR_DATA, vendor);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, (View) imageView, imageView.getResources().getString(R.string.shared_thumbnail));
        startActivity(i, options.toBundle());
    }
}
