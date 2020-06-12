package com.safinaz.matrimony.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.safinaz.matrimony.Fragments.BottomSheetSendMessage;
import com.safinaz.matrimony.Model.Vendor;
import com.safinaz.matrimony.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.safinaz.matrimony.Utility.Constants.VENDOR_DATA;

public class VendorDetail extends AppCompatActivity {
    @BindView(R.id.btnSubmit)
    Button submitBtn;
    @BindView(R.id.vendor_backdrop)
    ImageView vendorBackdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vendor_photo)
    ImageView photo;
    @BindView(R.id.vendor_rating)
    TextView vendorRating;
    @BindView(R.id.vendor_about)
    TextView vendorAbout;
    @BindView(R.id.vendor_location)
    TextView vendorLocation;
    @BindView(R.id.vendor_email)
    TextView vendorEmail;
    @BindView(R.id.vendor_phone)
    TextView vendorPhone;
    MaterialRatingBar ratingBar;
    private Vendor vendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent catchIntent = getIntent();
        if(catchIntent.hasExtra(VENDOR_DATA)){
            vendor = (Vendor) getIntent().getSerializableExtra(VENDOR_DATA);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_detail);
        ButterKnife.bind(this);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetSendMessage sendCreateBottomSheet = new BottomSheetSendMessage();
                sendCreateBottomSheet.show(getSupportFragmentManager(), "sendcreatebottomsheet");
            }
        });
        if(vendor.getVendorPlannerName()!=null) {
            toolbar.setTitle(vendor.getVendorPlannerName());
        }
        setSupportActionBar(toolbar);
        ratingBar = findViewById(R.id.rating_bar);
        initViews();
    }

    public void initViews(){
//        if(vendor.getAbout()!=null){
//            vendorAbout.setText(vendor.getAbout());
//        }
        Picasso.get().load(vendor.getVendorImg()).into(vendorBackdrop, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }
        });

        Picasso.get().load(vendor.getVendorImg()).into(photo, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }
        });

        if(vendor.getVendorAddress()!=null){
            vendorLocation.setText(vendor.getVendorAddress());
        }
        if(vendor.getVendorMobile()!=null){
            vendorPhone.setText(vendor.getVendorMobile());
        }
        if(vendor.getVendorEmail()!=null){
            vendorEmail.setText(vendor.getVendorEmail());
        }

//        if(vendor.getRating()!=null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                ratingBar.setRating(vendor.getRating());
//            } else {
//                ratingBar.setRating(vendor.getRating());
//            }
//            vendorRating.setText(String.format("Rating: %s / 5", vendor.getRating()));
//
//        }
        ratingBar.setVisibility(View.GONE);
    }
}
