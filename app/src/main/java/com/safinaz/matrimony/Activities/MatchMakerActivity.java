package com.safinaz.matrimony.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.safinaz.matrimony.Fragments.MatchMakerFragment;
import com.safinaz.matrimony.R;
import com.safinaz.matrimony.Utility.AppDebugLog;
import com.safinaz.matrimony.Utility.SessionManager;
import com.safinaz.matrimony.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class MatchMakerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    public MatchMakerFragment acceptedMatchMakerFragment;
    public MatchMakerFragment rejectedMatchMakerFragment;
    public MatchMakerFragment pendingMatchMakerFragment;

    public boolean isFirstTimeApiCallFromInitialize = true;

    private boolean isFromAppLinking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_maker);
        initialize();

        SessionManager session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            Intent intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
            finish();
        } else {
            handleIntent(getIntent());
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        AppDebugLog.print("in handleIntent :" + intent.getData());
        Uri appLinkData = intent.getData();
        if (appLinkData != null) {
            isFromAppLinking = true;
            AppDebugLog.print("appLinkData in handleIntent : " + appLinkData.getPath());
        }
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }

    private void backPressed() {
        if (isFromAppLinking) {
            SessionManager session = new SessionManager(this);
            if (session.getLoginData(SessionManager.USER_TYPE).equalsIgnoreCase("o")) {
                startActivity(new Intent(this, DashboardActivity.class));
            } else {
                startActivity(new Intent(this, ExclusiveDashboardActivity.class));
            }
            finish();
        }else{
            finish();
        }
    }


    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Matches received from Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(view -> {
            backPressed();
        });

    }

    private void initialize() {
        setToolbar();

       // ApplicationData.displayWidth = Utility.getDisplayWidth(this);

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        tabLayout = findViewById(R.id.tabs_photo);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(mViewPager));
    }

    private void setupViewPager(ViewPager viewPager) {
        acceptedMatchMakerFragment = MatchMakerFragment.newInstance(Utils.KEY_ACCEPT);
        rejectedMatchMakerFragment = MatchMakerFragment.newInstance(Utils.KEY_REJECT);
        pendingMatchMakerFragment = MatchMakerFragment.newInstance(Utils.KEY_PENDING);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(acceptedMatchMakerFragment, "Accepted");
        adapter.addFragment(rejectedMatchMakerFragment, "Rejected");
        adapter.addFragment(pendingMatchMakerFragment, "Pending");
        viewPager.setAdapter(adapter);

        mViewPager.setCurrentItem(0);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
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

    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager viewPager) {

        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        acceptedMatchMakerFragment.tabChanged();
                        break;
                    case 1:
                        rejectedMatchMakerFragment.tabChanged();
                        break;
                    case 2:
                        pendingMatchMakerFragment.tabChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

}
