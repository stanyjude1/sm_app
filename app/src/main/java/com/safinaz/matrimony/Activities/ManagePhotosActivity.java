package com.safinaz.matrimony.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.safinaz.matrimony.Fragments.MyPhotoFragment;
import com.safinaz.matrimony.Fragments.PhotoPasswordFragment;
import com.safinaz.matrimony.R;
import com.safinaz.matrimony.Utility.Common;
import com.safinaz.matrimony.Utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ManagePhotosActivity extends AppCompatActivity {

    Common common;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SessionManager session;
    private String userType = "o";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_photos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage Photos");

        common = new Common(this);
        session = new SessionManager(this);

        if (session.getLoginData(SessionManager.USER_TYPE) != null
                && session.getLoginData(SessionManager.USER_TYPE).length() > 0
                && session.getLoginData(SessionManager.USER_TYPE).equalsIgnoreCase("e")) {
            userType = session.getLoginData(SessionManager.USER_TYPE);
        }

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (userType.equalsIgnoreCase("e")) {
            tabLayout.setVisibility(View.GONE);
        }


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyPhotoFragment(), "My Photos");
        if (userType.equalsIgnoreCase("o")) {
            adapter.addFragment(new PhotoPasswordFragment(), "Photo Requests");
        }
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", "reload");
            returnIntent.putExtra("tabid", "my");
            setResult(RESULT_OK, returnIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "reload");
        returnIntent.putExtra("tabid", "my");
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
