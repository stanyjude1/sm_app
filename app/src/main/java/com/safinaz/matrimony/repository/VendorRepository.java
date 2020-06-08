package com.safinaz.matrimony.repository;

import android.arch.lifecycle.MutableLiveData;

import com.safinaz.matrimony.Model.Vendor;
import com.safinaz.matrimony.Model.VendorCategory;
import com.safinaz.matrimony.Model.VendorCategoryResponse;

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
        vendorList.add(new Vendor("1","https://sm.iugale.tech/assets/wedding-planner/0050313d8c119dfe30d84d5d3b90367a.png", "50", "150", "India", "Bangalore", "category 1", "planner 1", "500", "ttbhrf", "grg@rgre.rfgre", "+91-222225522222"));
        vendorList.add(new Vendor("2", "https://sm.iugale.tech/assets/wedding-planner/7da4c61beeead60fe960abcb1d53ce0a.png", "2", "12", "India", "Bangalore", "category 1", "planner 1", "0", "dsjdhsjd jshd", "smm5@tempmails.io", "+91-664646464646"));
        vendorMutableLiveData.setValue(vendorList);

        return vendorMutableLiveData;
    }

    public MutableLiveData<List<VendorCategory>> getCategories(){
        List<VendorCategory> vendorCategories = new ArrayList<>();
        final MutableLiveData<List<VendorCategory>> vendorCategoryMutableLiveData = new MutableLiveData<>();
        vendorCategories.add(new VendorCategory("101", "https://picsum.photos/id/1031/300", "Matrimony"));
        vendorCategories.add(new VendorCategory("132", "https://sm.iugale.tech/assets/wedding-planner/e27adcebe8363797b39ed1182b6a73f4.png", "category_1"));
        vendorCategories.add(new VendorCategory("138", "https://sm.iugale.tech/assets/wedding-planner/7da4c61beeead60fe960abcb1d53ce0a.png", "category_4"));
        vendorCategories.add(new VendorCategory("104", "https://picsum.photos/id/1031/300", "Wedding Venue"));
        vendorCategoryMutableLiveData.setValue(vendorCategories);

        return vendorCategoryMutableLiveData;
    }
}
