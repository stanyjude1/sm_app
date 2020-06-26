package com.safinaz.matrimony.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.safinaz.matrimony.Adapter.MenuListAdapter;
import com.safinaz.matrimony.Application.MyApplication;
import com.safinaz.matrimony.Fragments.ExclusiveDashboardFragment;
import com.safinaz.matrimony.Model.MenuChildBean;
import com.safinaz.matrimony.Model.MenuGroupBean;
import com.safinaz.matrimony.R;
import com.safinaz.matrimony.Utility.AppDebugLog;
import com.safinaz.matrimony.Utility.Common;
import com.safinaz.matrimony.Utility.SessionManager;
import com.safinaz.matrimony.Utility.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExclusiveDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tv_edit_profile, tv_name, tv_matri_id;
    Common common;
    SessionManager session;
    private Button btn_member;
    boolean doubleBackToExitPressedOnce = false;
    ProgressDialog pd;
    public ImageView imgProfile;
    private int placeHolder, photoProtectPlaceHolder;
    private boolean isRequiredCurrentPlanData = true;

    private ExclusiveDashboardFragment exclusiveDashboardFragment;

    private ExpandableListView drawerExpandableListView;
    private List<MenuGroupBean> drawerListData;
    private List<MenuGroupBean> menuList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        setContentView(R.layout.activity_exclusive_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        common = new Common(this);

        if (session.getLoginData(SessionManager.KEY_GENDER).equals("Male")) {
            photoProtectPlaceHolder = R.drawable.photopassword_male;
            placeHolder = R.drawable.male;
        } else if (session.getLoginData(SessionManager.KEY_GENDER).equals("Female")) {
            photoProtectPlaceHolder = R.drawable.photopassword_female;
            placeHolder = R.drawable.female;
        }

        // Create and set Android Fragment as default.
        exclusiveDashboardFragment = new ExclusiveDashboardFragment();
        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.frame_layout, exclusiveDashboardFragment);
        // Commit the Fragment replace action.
        fragmentTransaction.commit();

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        tv_edit_profile = headerView.findViewById(R.id.tv_edit_profile);
        tv_name = headerView.findViewById(R.id.tv_name);
        tv_matri_id = headerView.findViewById(R.id.tv_matri_id);
        imgProfile = headerView.findViewById(R.id.imgProfile);
        //lay_header=(LinearLayout) headerView.findViewById(R.id.lay_header);
        common.setDrawableLeftTextView(R.drawable.eye_white, tv_edit_profile);

        btn_member = findViewById(R.id.btn_member);

        initMenu();

        btn_member.setOnClickListener(view -> {
            startActivity(new Intent(ExclusiveDashboardActivity.this, CurrentPlanActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        });

        tv_edit_profile.setVisibility(View.GONE);

        getMyprofile();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("dashresume", "onResume");
    }

    private void getCurrentPlan() {
        HashMap<String, String> param = new HashMap<>();
        param.put("matri_id", session.getLoginData(SessionManager.KEY_matri_id));
        common.makePostRequest(Utils.check_plan, param, response -> {
            if (pd != null)
                pd.dismiss();
            try {
                JSONObject object = new JSONObject(response);
                MyApplication.setPlan(object.getBoolean("is_show"));

            } catch (JSONException e) {
                if (pd != null)
                    pd.dismiss();
                e.printStackTrace();
            }
        }, error -> {
            if (pd != null)
                pd.dismiss();
        });
    }

    public void getMyprofile() {
        pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        HashMap<String, String> param = new HashMap<>();
        param.put("member_id", session.getLoginData(SessionManager.KEY_USER_ID));
        common.makePostRequest(Utils.get_my_profile, param, response -> {
            if (pd != null)
                pd.dismiss();
            try {
                JSONObject object = new JSONObject(response);
                JSONObject data = object.getJSONObject("data");

                exclusiveDashboardFragment.setUserProfile(data);

                tv_name.setText(data.getString("username"));
                tv_matri_id.setText(data.getString("matri_id"));

                if (!data.getString("photo1").equals(""))
                    Picasso.get().load(data.getString("photo1")).placeholder(placeHolder).error(placeHolder).into(imgProfile);
                else {
                    imgProfile.setImageResource(placeHolder);
                }

                if (isRequiredCurrentPlanData) {
                    isRequiredCurrentPlanData = false;
                    getCurrentPlan();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                common.showToast(getString(R.string.err_msg_try_again_later));
            }
        }, error -> {
            if (pd != null)
                pd.dismiss();
            if (error.networkResponse != null) {
                common.showToast(Common.getErrorMessageFromErrorCode(error.networkResponse.statusCode));
            }
        });
    }

    private void initMenu() {
        drawerExpandableListView = findViewById(R.id.list_menu);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Gson gson = new GsonBuilder().setDateFormat(Utils.GSONDateTimeFormat).create();
        menuList.clear();
        String jsonData = session.getDrawerMenuArrayObject();
        if (jsonData != null) {
            JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
            menuList = gson.fromJson(jsonObject.getAsJsonArray("exclusive_member_menu"), new TypeToken<List<MenuGroupBean>>() {
            }.getType());

            AppDebugLog.print("exclusive_member_menu : " + menuList.size());
        } else {
            JsonObject jsonObject = new JsonParser().parse(Utils.DRAWER_MENU_DATA).getAsJsonObject();
            menuList = gson.fromJson(jsonObject.getAsJsonArray("exclusive_member_menu"), new TypeToken<List<MenuGroupBean>>() {
            }.getType());
        }

        drawerListData = menuList;
        AppDebugLog.print("drawerListData size : " + drawerListData.size());

        MenuListAdapter adapter = new MenuListAdapter(this, menuList);
        drawerExpandableListView.setAdapter(adapter);

        drawerExpandableListView.setOnChildClickListener((expandableListView, view, groupPosition, childPosition, l) -> {
            MenuChildBean menuChildBean = menuList.get(groupPosition).getDrawerChildList().get(childPosition);
            try {
                AppDebugLog.print("Group Menu Action : " + (getPackageName() + ".Activities." + menuChildBean.getSubMenuAction()));
                Class<?> c = Class.forName(getPackageName() + ".Activities." + menuChildBean.getSubMenuAction());
                Intent intent = new Intent(this, c);
                if (menuChildBean.getSubmenuTag() != null && menuChildBean.getSubmenuTag().length() > 0) {
                    intent.putExtra(Utils.KEY_INTENT, menuChildBean.getSubmenuTag());
                }
                startActivity(intent);
            } catch (ClassNotFoundException ignored) {
                ignored.printStackTrace();
                AppDebugLog.print("Activity not found");
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        drawerExpandableListView.setOnGroupClickListener((expandableListView, view, groupPosition, l) -> {
            MenuGroupBean groupBean = menuList.get(groupPosition);
            if (groupBean.getMenuAction() != null && groupBean.getMenuAction().length() > 0) {
                if (groupBean.getMenuAction().equalsIgnoreCase("Logout")) {
                    conformLogout();
                } else {
                    try {
                        AppDebugLog.print("Group Menu Action : " + (getPackageName() + ".Activities." + groupBean.getMenuAction()));
                        Class<?> c = Class.forName(getPackageName() + ".Activities." + groupBean.getMenuAction());
                        Intent intent = new Intent(this, c);
                        startActivity(intent);
                    } catch (ClassNotFoundException ignored) {
                        ignored.printStackTrace();
                        AppDebugLog.print("Activity not found");
                    }
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            } else {
                return false;
            }
        });
    }

    private void conformLogout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ExclusiveDashboardActivity.this);
        alert.setMessage("Are you sure you want logout from this app?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                session.logoutUser();
            }
        });
        alert.setNegativeButton("No", null);
        alert.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                startActivity(intent);
                finish();
                System.exit(0);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(getApplicationContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
