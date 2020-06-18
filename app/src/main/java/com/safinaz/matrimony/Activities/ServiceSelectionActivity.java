package com.safinaz.matrimony.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.safinaz.matrimony.Adapter.ServiceCategoryAdapter;
import com.safinaz.matrimony.Model.VendorCategory;
import com.safinaz.matrimony.R;
import com.safinaz.matrimony.Utility.Common;
import com.safinaz.matrimony.Utility.Constants;
import com.safinaz.matrimony.Utility.Utils;
import com.safinaz.matrimony.viewmodel.ServiceSelectionActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceSelectionActivity extends AppCompatActivity implements View.OnClickListener, ServiceCategoryAdapter.ServiceAdapterOnClickHandler {

    @BindView(R.id.rl2)
    RecyclerView serviceRv;
    boolean doubleBackToExitPressedOnce = false;
    private boolean isVendor = false;
    ServiceCategoryAdapter adapter;
    ServiceSelectionActivityViewModel serviceSelectionActivityViewModel;
    private List<VendorCategory> categoryList = new ArrayList<>();
    private String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_service_selection);
        ButterKnife.bind(this);
        initialize();
        initViewModel();
    }

//    private void clearAllSelection(){
//        btnMatrimony.setSelected(false);
//        btnFlowerDecorators.setSelected(false);
//        btnBridalMakeup.setSelected(false);
//        btnWeddingVenues.setSelected(false);
//    }

    private void initViewModel() {
        serviceSelectionActivityViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ServiceSelectionActivityViewModel.class);
        serviceSelectionActivityViewModel.init();
        serviceSelectionActivityViewModel.getCategories().observe(this, new Observer<List<VendorCategory>>() {
            @Override
            public void onChanged(@Nullable List<VendorCategory> vendors) {
                if (vendors != null) {
                    categoryList.addAll(vendors);
                    adapter.setVendorData(categoryList);
                }
            }
        });
    }

    private void initialize() {
//        clearAllSelection();
//        btnMatrimony.setOnClickListener(this);
//        btnWeddingVenues.setOnClickListener(this);
//        btnBridalMakeup.setOnClickListener(this);
//        btnFlowerDecorators.setOnClickListener(this);
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, Utils.calculateNoOfColumns(this, 180), RecyclerView.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        serviceRv.setLayoutManager(gridLayoutManager);
        serviceRv.setHasFixedSize(true);
        adapter= new ServiceCategoryAdapter(this);
        serviceRv.setAdapter(adapter);
        if (adapter.getItemCount() == 0) {
            Log.e("Test","I am here");
//            errorImage.setVisibility(View.VISIBLE);
//            errorView.setVisibility(View.VISIBLE);
//            if (!isOnline()) {
//                errorView.setText(R.string.oops_network_error);
//            }
        }

    }

//    private Boolean isServiceSelected() {
//        if(btnWeddingVenues.isSelected() || btnMatrimony.isSelected() || btnBridalMakeup.isSelected() || btnFlowerDecorators.isSelected()){
//            return true;
//        } else {
//            return false;
//        }
//    }

//    private void clearUserSelected(){
//        if(isServiceSelected()){
//            clearAllSelection();
//        }
//    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getApplicationContext(), R.string.press_back_to_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {

//        switch (v.getId()) {
//            case R.id.btnMatrimony: {
//                clearUserSelected();
//                btnMatrimony.setSelected(true);
//                isVendor = false;
//            }
//            break;
//            case R.id.btnWeddingVenues:{
//                clearUserSelected();
//                btnWeddingVenues.setSelected(true);
//                isVendor = true;
//            }
//            break;
//            case R.id.btnBridalMakeup: {
//                clearUserSelected();
//                btnBridalMakeup.setSelected(true);
//                isVendor = true;
//            }
//            break;
//            case R.id.btnFlowerDecorators:{
//                clearUserSelected();
//                btnFlowerDecorators.setSelected(true);
//                isVendor = true;
//            }
//            break;
//            case R.id.btnSubmit:{
//                if(btnMatrimony.isSelected()){
//                    redirectToMatrimony();
//                } else if(isVendor) {
//                    //Check kind of vendor & pass in Intent
//                    if(btnWeddingVenues.isSelected()){
//                        redirectToVendor();
//                    }
//                    if(btnBridalMakeup.isSelected()){
//                        redirectToVendor();
//                    }
//                    if(btnFlowerDecorators.isSelected()){
//                        redirectToVendor();
//                    }
//                } else {
//                    Common.showToast(this,"Please select service type");
//                }
//            }
//            break;
//        }
    }

    public void redirectToMatrimony(){
        Intent i = new Intent(ServiceSelectionActivity.this, FirstActivity.class);
        startActivity(i);
    }

    public void redirectToVendor(String id, String CategoryName){
        Intent i = new Intent(ServiceSelectionActivity.this, VendorActivity.class);
        i.putExtra(Constants.CARTEGORY_PARAM, id);
        i.putExtra(Constants.CARTEGORY_NAME, CategoryName);
        Log.e("Test", id);
        startActivity(i);
    }

    @Override
    public void onClick(VendorCategory vendorCategory) {
       if(vendorCategory.getCategoryId().equals("101")){
           redirectToMatrimony();
       } else {
           redirectToVendor(vendorCategory.getCategoryId(), vendorCategory.getCategoryName());
       }
    }
}
