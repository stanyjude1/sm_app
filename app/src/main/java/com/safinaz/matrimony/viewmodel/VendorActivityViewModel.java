package com.safinaz.matrimony.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.safinaz.matrimony.Model.Vendor;
import com.safinaz.matrimony.repository.VendorRepository;

import java.util.List;

public class VendorActivityViewModel extends ViewModel {
    private MutableLiveData<List<Vendor>> mutableLiveData;
    private VendorRepository vendorRepository;
    public VendorActivityViewModel() {
    }
    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        vendorRepository = vendorRepository.getInstance();
    }

    public LiveData<List<Vendor>> getVendors(){
        mutableLiveData = vendorRepository.getVendors();
        return mutableLiveData;
    }
}
