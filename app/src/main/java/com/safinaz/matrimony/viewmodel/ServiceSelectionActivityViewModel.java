package com.safinaz.matrimony.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
