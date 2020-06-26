package com.safinaz.matrimony.Activities;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.safinaz.matrimony.Fragments.BottomSheetSendMessage;
import com.safinaz.matrimony.Model.ContactUsResponse;
import com.safinaz.matrimony.Model.Vendor;
import com.safinaz.matrimony.R;
import com.safinaz.matrimony.viewmodel.VendorDetailViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.stfalcon.imageviewer.StfalconImageViewer;
import com.stfalcon.imageviewer.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    TextView vendorRange;
    @BindView(R.id.vendor_about)
    TextView vendorAbout;
    @BindView(R.id.vendor_location)
    TextView vendorLocation;
    @BindView(R.id.vendor_email)
    TextView vendorEmail;
    @BindView(R.id.vendor_phone)
    TextView vendorPhone;
    @BindView(R.id.vendor_capacity)
    TextView vendorCapacity;
    @BindView(R.id.vendor_adderess)
    TextView vendorAddress;
    private Vendor vendor;

    private VendorDetailViewModel vendorDetailViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent catchIntent = getIntent();
        if(catchIntent.hasExtra(VENDOR_DATA)){
            vendor = (Vendor) getIntent().getSerializableExtra(VENDOR_DATA);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_detail);
        ButterKnife.bind(this);
//        Zoomy.Builder builder = new Zoomy.Builder(this).target(photo);
//        builder.register();
        if(vendor.getVendorPlannerName()!=null) {
            toolbar.setTitle(vendor.getVendorPlannerName());
        }
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViewModel();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetSendMessage sendCreateBottomSheet = new BottomSheetSendMessage();
                sendCreateBottomSheet.show(getSupportFragmentManager(), "sendcreatebottomsheet");
            }
        });
        initViews();
    }

    private void initViewModel(){
        vendorDetailViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(VendorDetailViewModel.class);
        vendorDetailViewModel.init();
    }

    public void initViews(){
        Picasso.get().load(vendor.getVendorImg()).into(vendorBackdrop, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }
        });

        ArrayList<String> images = new ArrayList<>();
        images.add(vendor.getVendorImg());
        StfalconImageViewer.Builder stfalconImageViewer = new StfalconImageViewer.Builder<String>(this, images, new ImageLoader<String>() {
            @Override
            public void loadImage(ImageView imageView, String image) {
                Picasso.get().load(image).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stfalconImageViewer.show();
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
            vendorAddress.setText(vendor.getVendorAddress());
        }
        if(vendor.getVendorMobile()!=null){
            vendorPhone.setText(vendor.getVendorMobile());
        }
        if(vendor.getVendorEmail()!=null){
            vendorEmail.setText(vendor.getVendorEmail());
        }
        if(vendor.getVendorStartRateRange()!=null && vendor.getVendorEndRateRange()!=null){
            vendorRange.setText(String.format("%s - %s", vendor.getVendorStartRateRange(), vendor.getVendorEndRateRange()));
        }
        if(vendor.getVendorCapacity()!=null){
            vendorCapacity.setText(vendor.getVendorCapacity());
        }

        if(vendor.getVendorCityName()!=null && vendor.getVendorCountryName()!=null){
            vendorLocation.setText(String.format("%s, %s", vendor.getVendorCityName(), vendor.getVendorCountryName()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendMessage(String customerName, String customerMail, String customerNumber, String customerMessage){
//        Log.e("TEst", vendor.getVendorId()+" "+vendor.getVendorPlannerName()+" "+customerName+" "+customerMail+" "+customerNumber+" "+customerMessage);
//       ContactUsData contactUsData = new ContactUsData();
        vendorDetailViewModel.sendContactUsDetail(vendor.getVendorId(), vendor.getVendorPlannerName(), customerName,customerMail, customerNumber, customerMessage).observe(this, new Observer<ContactUsResponse>() {
            @Override
            public void onChanged(@Nullable ContactUsResponse contactUsResponse) {
                if(contactUsResponse!=null){
                    Toast.makeText(VendorDetail.this, "Message Sent", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(VendorDetail.this, "ERROR!! Message Not Sent", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
