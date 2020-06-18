package com.safinaz.matrimony.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.safinaz.matrimony.Model.ContactUsData;
import com.safinaz.matrimony.Model.ContactUsResponse;
import com.safinaz.matrimony.Model.Vendor;
import com.safinaz.matrimony.Model.VendorCategory;
import com.safinaz.matrimony.Model.VendorCategoryResponse;
import com.safinaz.matrimony.Model.VendorResponse;
import com.safinaz.matrimony.retrofit.AppApiService;
import com.safinaz.matrimony.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorRepository {
    public static VendorRepository vendorRepository;
    private AppApiService appApiService;


    public VendorRepository(){
        appApiService = RetrofitClient.createService(AppApiService.class);
    }

    public static VendorRepository getInstance(){
        if(vendorRepository==null){
            vendorRepository = new VendorRepository();
        }
        return vendorRepository;
    }

    public MutableLiveData<List<Vendor>> getVendors(String id){
        List<Vendor> vendorList = new ArrayList<>();
        final MutableLiveData<List<Vendor>> vendorMutableLiveData = new MutableLiveData<>();
        appApiService.getVendorCategories(id).enqueue(new Callback<VendorResponse>() {
            @Override
            public void onResponse(Call<VendorResponse> call, Response<VendorResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body().getErrorCode()==0) {
                    if(response.body().getErrorCode()!=1) {
                        vendorMutableLiveData.setValue(response.body().getSuccess());
                    } else {
                        vendorMutableLiveData.setValue(null);
                    }
                } else {
                    vendorMutableLiveData.setValue(null);
                    Log.d("API Request", "Request failed with error" + response.code() + " " + response.message());
                }
            }
            @Override
            public void onFailure(Call<VendorResponse> call, Throwable t) {
                Log.e("Test", t.toString());
                vendorMutableLiveData.setValue(null);
            }
        });
//        vendorList.add(new Vendor("1","https://sm.iugale.tech/assets/wedding-planner/0050313d8c119dfe30d84d5d3b90367a.png", "50", "150", "India", "Bangalore", "category 1", "planner 1", "500", "ttbhrf", "grg@rgre.rfgre", "+91-222225522222"));
//        vendorList.add(new Vendor("2", "https://sm.iugale.tech/assets/wedding-planner/7da4c61beeead60fe960abcb1d53ce0a.png", "2", "12", "India", "Bangalore", "category 1", "planner 1", "0", "dsjdhsjd jshd", "smm5@tempmails.io", "+91-664646464646"));
//        vendorMutableLiveData.setValue(vendorList);

        return vendorMutableLiveData;
    }

    public MutableLiveData<List<VendorCategory>> getCategories(){
        final MutableLiveData<List<VendorCategory>> vendorCategoryMutableLiveData = new MutableLiveData<>();
        appApiService.getVendorCategories().enqueue(new Callback<VendorCategoryResponse>() {
            @Override
            public void onResponse(Call<VendorCategoryResponse> call, Response<VendorCategoryResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body().getErrorCode()==0) {
                    if(response.body().getErrorCode()!=1) {
                        vendorCategoryMutableLiveData.setValue(response.body().getSuccess());
                    } else {
                        vendorCategoryMutableLiveData.setValue(null);
                    }
                } else {
                    vendorCategoryMutableLiveData.setValue(null);
                    Log.d("API Request", "Request failed with error" + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<VendorCategoryResponse> call, Throwable t) {
                Log.e("Test", t.toString());
                vendorCategoryMutableLiveData.setValue(null);
            }
        });


        return vendorCategoryMutableLiveData;
    }

    public MutableLiveData<ContactUsResponse> sendResponse(String vendorId, String vendorName, String customerName, String customerMail, String customerNumber, String customerMessage){
        final MutableLiveData<ContactUsResponse> vendorCategoryMutableLiveData = new MutableLiveData<>();
        Map<String, String> map = new HashMap<>();
        map.put("vendor_id", vendorId);
        map.put("vendor_mail", vendorName);
        map.put("customer_name", customerName);
        map.put("customer_mail", customerMail);
        map.put("customer_number", customerNumber);
        map.put("customer_message", customerMessage);
        appApiService.sendForm(map).enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
//                    if(response.body().getErrorCode()!=1) {
                        vendorCategoryMutableLiveData.setValue(response.body());
//                    } else {
//                        vendorCategoryMutableLiveData.setValue(null);
//                    }
                } else {
                    vendorCategoryMutableLiveData.setValue(null);
                    Log.d("APIRequest", "Request failed with error" + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                Log.e("Test", t.toString());
                vendorCategoryMutableLiveData.setValue(null);
            }
        });


        return vendorCategoryMutableLiveData;
    }
}
