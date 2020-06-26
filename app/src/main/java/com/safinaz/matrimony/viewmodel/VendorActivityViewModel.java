package com.safinaz.matrimony.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    public LiveData<List<Vendor>> getVendors(String id){
        mutableLiveData = vendorRepository.getVendors(id);
        return mutableLiveData;
    }
}
