package com.safinaz.matrimony.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.safinaz.matrimony.R;
import com.safinaz.matrimony.Utility.Common;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceSelectionActivity extends AppCompatActivity implements View.OnClickListener {
    //TODO: Replace these individual view with RecyclerView during Revamp (This will provide flexibility in adding more services)

    @BindView(R.id.btnMatrimony)
    ImageView btnMatrimony;
    @BindView(R.id.btnWeddingVenues)
    ImageView btnWeddingVenues;
    @BindView(R.id.btnBridalMakeup)
    ImageView btnBridalMakeup;
    @BindView(R.id.btnFlowerDecorators)
    ImageView btnFlowerDecorators;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    boolean doubleBackToExitPressedOnce = false;
    private boolean isVendor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_service_selection);
        ButterKnife.bind(this);
        initialize();
    }

    private void clearAllSelection(){
        btnMatrimony.setSelected(false);
        btnFlowerDecorators.setSelected(false);
        btnBridalMakeup.setSelected(false);
        btnWeddingVenues.setSelected(false);
    }

    private void initialize() {
        clearAllSelection();
        btnMatrimony.setOnClickListener(this);
        btnWeddingVenues.setOnClickListener(this);
        btnBridalMakeup.setOnClickListener(this);
        btnFlowerDecorators.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private Boolean isServiceSelected() {
        if(btnWeddingVenues.isSelected() || btnMatrimony.isSelected() || btnBridalMakeup.isSelected() || btnFlowerDecorators.isSelected()){
            return true;
        } else {
            return false;
        }
    }

    private void clearUserSelected(){
        if(isServiceSelected()){
            clearAllSelection();
        }
    }

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
        switch (v.getId()) {
            case R.id.btnMatrimony: {
                clearUserSelected();
                btnMatrimony.setSelected(true);
                isVendor = false;
            }
            break;
            case R.id.btnWeddingVenues:{
                clearUserSelected();
                btnWeddingVenues.setSelected(true);
                isVendor = true;
            }
            break;
            case R.id.btnBridalMakeup: {
                clearUserSelected();
                btnBridalMakeup.setSelected(true);
                isVendor = true;
            }
            break;
            case R.id.btnFlowerDecorators:{
                clearUserSelected();
                btnFlowerDecorators.setSelected(true);
                isVendor = true;
            }
            break;
            case R.id.btnSubmit:{
                if(btnMatrimony.isSelected()){
                    redirectToMatrimony();
                } else if(isVendor) {
                    //Check kind of vendor & pass in Intent
                    if(btnWeddingVenues.isSelected()){
                        redirectToVendor();
                    }
                    if(btnBridalMakeup.isSelected()){
                        redirectToVendor();
                    }
                    if(btnFlowerDecorators.isSelected()){
                        redirectToVendor();
                    }
                } else {
                    Common.showToast(this,"Please select service type");
                }
            }
            break;
        }
    }

    public void redirectToMatrimony(){
        Intent i = new Intent(ServiceSelectionActivity.this, FirstActivity.class);
        startActivity(i);
    }

    public void redirectToVendor(){
        Intent i = new Intent(ServiceSelectionActivity.this, VendorActivity.class);
        startActivity(i);
    }
}
