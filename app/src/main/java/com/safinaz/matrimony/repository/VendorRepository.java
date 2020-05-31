package com.safinaz.matrimony.repository;

import android.arch.lifecycle.MutableLiveData;

import com.safinaz.matrimony.Model.Vendor;

import java.util.ArrayList;
import java.util.List;

public class VendorRepository {
    public static VendorRepository vendorRepository;

    public VendorRepository(){

    }

    public static VendorRepository getInstance(){
        if(vendorRepository==null){
            vendorRepository = new VendorRepository();
        }
        return vendorRepository;
    }

    public MutableLiveData<List<Vendor>> getVendors(){
        List<Vendor> vendorList = new ArrayList<>();
        final MutableLiveData<List<Vendor>> vendorMutableLiveData = new MutableLiveData<>();
        vendorList.add(new Vendor("New Delhi", 2000, "$200-$300", "https://picsum.photos/id/1031/300", 3.2f, "This is a sample about for vendor1", "test@test.com", "Test", "+91 0000000000"));
        vendorList.add(new Vendor("Chennai", 2100, "$100-$200", "https://picsum.photos/id/1078/300", 4.2f, "This is a sample about for vendor 2", "test1@test.com", "Example", "+91 0000000001"));
        vendorList.add(new Vendor("Dehradun", 500, "$50-$80", "https://picsum.photos/id/111/300", 4.8f, "This is a sample about for vendor 3", "test2@test.com", "Example Vendor", "+91 0000000002"));
        vendorMutableLiveData.setValue(vendorList);

        return vendorMutableLiveData;
    }
}
