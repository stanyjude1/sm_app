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
import android.widget.ProgressBar;
import android.widget.TextView;


import com.safinaz.matrimony.Adapter.VendorAdapter;
import com.safinaz.matrimony.Model.Vendor;
import com.safinaz.matrimony.R;
import com.safinaz.matrimony.Utility.Constants;
import com.safinaz.matrimony.viewmodel.VendorActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.safinaz.matrimony.Utility.Constants.VENDOR_DATA;
import static com.safinaz.matrimony.Utility.Utils.isOnline;

public class VendorActivity extends AppCompatActivity implements VendorAdapter.VendorAdapterOnClickHandler {
    @BindView(R.id.vendor_rv)
    RecyclerView vendorRv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.empty)
    TextView empty;

    private VendorActivityViewModel vendorActivityViewModel;
    private VendorAdapter vendorAdapter;
    private List<Vendor> vendorList = new ArrayList<>();
    private String id;
    private String CategoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        if(i.hasExtra(Constants.CARTEGORY_PARAM)){
            id = i.getStringExtra(Constants.CARTEGORY_PARAM);
            CategoryName = i.getStringExtra(Constants.CARTEGORY_NAME);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        ButterKnife.bind(this);
        if(CategoryName!=null) {
            toolbar.setTitle(CategoryName + " Vendors");
        } else {
            toolbar.setTitle("Vendors");
        }
        setSupportActionBar(toolbar);
        setUpRecyclerView();
        initViewModel();
    }

    private void initViewModel(){
        vendorActivityViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(VendorActivityViewModel.class);
        vendorActivityViewModel.init();
        vendorActivityViewModel.getVendors(id).observe(this, new Observer<List<Vendor>>() {
            @Override
            public void onChanged(@Nullable List<Vendor> vendors) {
                progressBar.setVisibility(View.INVISIBLE);
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
            //Log.e("Test","I am here");
            //empty.setVisibility(View.VISIBLE);
                        if (!isOnline()) {
                            empty.setVisibility(View.VISIBLE);
                            empty.setText("Oops! Network Error");

                        }
        }


    }

    @Override
    public void onClick(Vendor vendor,View imageView) {
        Intent i = new Intent(this, VendorDetail.class);
        i.putExtra(VENDOR_DATA, vendor);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, (View) imageView, imageView.getResources().getString(R.string.shared_thumbnail));
        startActivity(i, options.toBundle());
    }
}
