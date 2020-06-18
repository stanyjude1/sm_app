package com.safinaz.matrimony.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.safinaz.matrimony.Model.ContactUsData;
import com.safinaz.matrimony.Model.ContactUsResponse;
import com.safinaz.matrimony.repository.VendorRepository;

public class VendorDetailViewModel extends ViewModel {
    private MutableLiveData<ContactUsResponse> mutableLiveData;
    private VendorRepository vendorRepository;
    public VendorDetailViewModel() {
    }
    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        vendorRepository = vendorRepository.getInstance();
    }

    public LiveData<ContactUsResponse> sendContactUsDetail(String vendorId, String vendorName, String customerName, String customerMail, String customerNumber, String customerMessage){
        mutableLiveData = vendorRepository.sendResponse(vendorId, vendorName, customerName, customerMail, customerNumber, customerMessage);
        return mutableLiveData;
    }
}
