package com.safinaz.matrimony.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.JsonObject;
import com.safinaz.matrimony.Application.MyApplication;
import com.safinaz.matrimony.R;
import com.safinaz.matrimony.Utility.AppDebugLog;
import com.safinaz.matrimony.Utility.Common;
import com.safinaz.matrimony.Utility.SessionManager;
import com.safinaz.matrimony.Utility.Utils;
import com.safinaz.matrimony.retrofit.AppApiService;
import com.safinaz.matrimony.retrofit.RetrofitClient;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashScreenActivity extends AppCompatActivity {
    private AVLoadingIndicatorView progressBar;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar);
        session = new SessionManager(this);

        session.saveDrawerMenuArrayObject(Utils.DRAWER_MENU_DATA);
    }

    @Override
    protected void onResume() {
        super.onResume();

        generateToken();
    }

    private void generateToken() {
        progressBar.setVisibility(View.VISIBLE);
        //volleyRequest.getResponseInJsonStringUsingVolley(GET_TOKEN_TAG,AppConstants.BASE_URL+AppConstants.GET_TOKEN,null);

        HashMap<String, String> param = new HashMap<>();
        param.put("appversion", Common.getAppVersionName(this));
        param.put("device_type", "android");
        //param.put("user-agent", "NI-AAPP");

        AppDebugLog.print("Params in getToken : " + param.toString());

        Retrofit retrofit = RetrofitClient.getClient();
        AppApiService service = retrofit.create(AppApiService.class);
        Call<JsonObject> call = service.getToken(param);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressBar.setVisibility(View.GONE);
                AppDebugLog.print("response in generateToken : " + response.body());
                JsonObject data = response.body();
                if (data != null) {
                    if (data.has("tocken"))
                        session.setUserData(SessionManager.TOKEN, data.get("tocken").getAsString());

                    if (data.has("is_force_update") && data.get("is_force_update").getAsBoolean()) {
                        openForceUpdateDialog();
                        return;
                    }

                    if (data.has("status") && data.get("status").getAsString().equalsIgnoreCase("success")) {
                        session.saveDrawerMenuArrayObject(data.toString());
                    }

                    startTimer();
                } else {
                    startTimer();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                AppDebugLog.print("error in generateToken : " + t.getMessage());
                //Utility.showToast(SplashAvtivity.this, getString(R.string.err_msg_try_again_later));
                progressBar.setVisibility(View.GONE);
                startTimer();
            }
        });
    }

    private void openForceUpdateDialog() {
        String updateUrl = "https://play.google.com/store/apps/details?id=" + getPackageName();
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available!!!")
                .setCancelable(false)
                .setMessage("Please update application for better use.")
                .setPositiveButton("Update", (dialog1, which) -> redirectStore(updateUrl))
//                .setNegativeButton("No, thanks", (dialog12, which) -> {
//                    finish();
//                })
                .create();
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void closeScreen() {
        Intent intent = null;
        SessionManager session = new SessionManager(this);
        if (session.isLoggedIn()) {
            if (session.getLoginData(SessionManager.USER_TYPE).equalsIgnoreCase("o")) {
                intent = new Intent(this, DashboardActivity.class);
            } else {
                intent = new Intent(this, ExclusiveDashboardActivity.class);
            }
            intent.putExtra("isFromSplash", true);
        } else {
            intent = new Intent(MyApplication.getInstance(), ServiceSelectionActivity.class);
        }
        startActivity(intent);
        finish();
    }

    private void startTimer() {
        CountDownTimer lTimer = new CountDownTimer(300, 300) {
            public void onFinish() {
                closeScreen();
            }

            @Override
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }
}
