package com.safinaz.matrimony.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.safinaz.matrimony.Model.Vendor;
import com.safinaz.matrimony.Model.VendorCategory;
import com.safinaz.matrimony.repository.VendorRepository;

import java.util.List;

public class ServiceSelectionActivityViewModel extends ViewModel {
    private MutableLiveData<List<VendorCategory>> mutableLiveData;
    private VendorRepository vendorRepository;
    public ServiceSelectionActivityViewModel() {
    }
    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        vendorRepository = vendorRepository.getInstance();
    }

    public LiveData<List<VendorCategory>> getCategories(){
        mutableLiveData = vendorRepository.getCategories();
        return mutableLiveData;
    }
}
