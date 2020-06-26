package com.safinaz.matrimony.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.safinaz.matrimony.Application.MyApplication;
import com.safinaz.matrimony.Custom.TouchImageView;
import com.safinaz.matrimony.Model.DashboardItem;
import com.safinaz.matrimony.R;
import com.safinaz.matrimony.Utility.AppDebugLog;
import com.safinaz.matrimony.Utility.Common;
import com.safinaz.matrimony.Utility.SessionManager;
import com.safinaz.matrimony.Utility.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LikeProfileActivity extends AppCompatActivity {

    private ListView lv_like;
    List<DashboardItem> list = new ArrayList<>();
    Common common;
    SessionManager session;
    ProgressDialog pd;
    boolean continue_request;
    TextView tv_no_data;
    LikeAdapter adapter;
    int page = 0;
    private int placeHolder,photoProtectPlaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Likes Profiles");

        common = new Common(this);
        session = new SessionManager(this);

        if (session.getLoginData(SessionManager.KEY_GENDER).equals("Female")) {
            photoProtectPlaceHolder = R.drawable.photopassword_male;
            placeHolder = R.drawable.male;
        } else if (session.getLoginData(SessionManager.KEY_GENDER).equals("Male")) {
            photoProtectPlaceHolder = R.drawable.photopassword_female;
            placeHolder = R.drawable.female;
        }


        lv_like = (ListView) findViewById(R.id.lv_like);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);

        page = page + 1;
        getListData(page);

        initializeAdapter();

        lv_like.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentFirstVisibleItem;
            private int totalItem;

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && scrollState == SCROLL_STATE_IDLE) {
                    if (continue_request) {
                        if (pd != null)
                            pd.dismiss();
                        page = page + 1;
                        getListData(page);
                    }
                    //if (pd!=null)
                    //    pd.dismiss();
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                this.currentFirstVisibleItem = firstVisibleItemm;
                this.currentVisibleItemCount = visibleItemCountt;
                this.totalItem = totalItemCountt;
            }
        });

    }

    private void initializeAdapter() {
        adapter = new LikeAdapter(this, list);
        lv_like.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        MyApplication.getInstance().cancelPendingRequests("req");
        super.onBackPressed();
    }

    private void getListData(int page) {
        pd = new ProgressDialog(LikeProfileActivity.this);
        pd.setIndeterminate(true);
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.show();

        HashMap<String, String> param = new HashMap<>();
        param.put("matri_id", session.getLoginData(SessionManager.KEY_matri_id));
        param.put("member_id", session.getLoginData(SessionManager.KEY_USER_ID));
        param.put("action", "like");

        common.makePostRequestTime(Utils.like_profile_list + page, param, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                Log.d("resp", response);
                try {
                    JSONObject object = new JSONObject(response);
                    int total_count = object.getInt("total_count");
                    if (total_count != 0) {
                        tv_no_data.setVisibility(View.GONE);
                        lv_like.setVisibility(View.VISIBLE);
                        continue_request = object.getBoolean("continue_request");
                        if (list.size() != total_count) {
                            JSONArray data = object.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject obj = data.getJSONObject(i);

                                DashboardItem item = new DashboardItem();
                                item.setId(obj.getString("id"));
                                item.setMatri_id(obj.getString("matri_id"));
                                item.setName(obj.getString("username"));
//                                String about = common.getAge(obj.getString("birthdate")) + " Year, " +
//                                        checkData(obj.getString("height")) +
//                                        checkData(obj.getString("caste_name")) + checkData(obj.getString("religion_name")) +
//                                        checkData(obj.getString("city_name")) + checkData(obj.getString("country_name"));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                String description = Common.getDetailsFromValue(common.getAge(obj.getString("birthdate"), sdf) + " Years, ", obj.getString("height"),
                                        obj.getString("caste_name"), obj.getString("religion_name"),
                                        obj.getString("city_name"), obj.getString("country_name"));
                                item.setAbout(description);
                                item.setImage_approval(obj.getString("photo1_approve"));
                                item.setImage(obj.getString("photo1"));
                                item.setUser_id(obj.getString("user_id"));
                                item.setPhoto_view_status(obj.getString("photo_view_status"));
                                item.setPhoto_view_count(obj.getString("photo_view_count"));

                                list.add(item);
                            }
                            adapter.notifyDataSetChanged();
                        }

                    } else {
                        tv_no_data.setVisibility(View.VISIBLE);
                        lv_like.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    common.showToast(getString(R.string.err_msg_try_again_later));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (pd != null)
                    pd.dismiss();
                if(error.networkResponse!=null) {
             common.showToast(Common.getErrorMessageFromErrorCode(error.networkResponse.statusCode));
}
            }
        });

    }

    private String checkData(String text) {
        if (!text.equals("") && !text.equals("null")) {
            return text + " , ";
        }
        return "";
    }

    public class LikeAdapter extends ArrayAdapter<DashboardItem> {
        Context context;
        List<DashboardItem> list;

        public LikeAdapter(Context context, List<DashboardItem> list) {
            super(context, R.layout.like_item, list);
            this.context = context;
            this.list = list;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.like_item, null, true);

            TextView tv_name = (TextView) rowView.findViewById(R.id.tv_name);
            TextView tv_detail = (TextView) rowView.findViewById(R.id.tv_detail);
            ImageView img_profile = (ImageView) rowView.findViewById(R.id.img_profile);
            LikeButton btn_like = (LikeButton) rowView.findViewById(R.id.btn_like);

            final DashboardItem item = list.get(position);
            tv_name.setText(item.getMatri_id().toUpperCase());
            //  String about = item.getAbout() + "...<font color='#ff041a'>Read More</font>";
            tv_detail.setText(Html.fromHtml(item.getAbout()));
            if (item.getPhoto_view_status().equals("0")) {
                if (session.getLoginData(SessionManager.KEY_GENDER).equals("Female")) {

                    img_profile.setImageResource(R.drawable.photopassword_male);
                } else if (session.getLoginData(SessionManager.KEY_GENDER).equals("Male")) {
                    img_profile.setImageResource(R.drawable.photopassword_female);
                }
            } else if (item.getPhoto_view_status().equals("2") && MyApplication.getPlan()) {
                if (item.getImage_approval().equals("UNAPPROVED")) {
                    if (session.getLoginData(SessionManager.KEY_GENDER).equals("Female")) {

                        img_profile.setImageResource(R.drawable.male);
                    } else if (session.getLoginData(SessionManager.KEY_GENDER).equals("Male")) {

                        img_profile.setImageResource(R.drawable.female);
                    }
                } else {

                    Picasso.get().load(item.getImage()).into(img_profile);
                }
            } else if (item.getPhoto_view_status().equals("2") && !MyApplication.getPlan()) {
                if (session.getLoginData(SessionManager.KEY_GENDER).equals("Female")) {

                    img_profile.setImageResource(R.drawable.male);
                } else if (session.getLoginData(SessionManager.KEY_GENDER).equals("Male")) {
                    img_profile.setImageResource(R.drawable.female);
                }
            } else if (item.getPhoto_view_status().equals("1")) {
                if (item.getImage_approval().equals("UNAPPROVED")) {
                    if (session.getLoginData(SessionManager.KEY_GENDER).equals("Female")) {
                        img_profile.setImageResource(R.drawable.male);

                    } else if (session.getLoginData(SessionManager.KEY_GENDER).equals("Male")) {
                        img_profile.setImageResource(R.drawable.female);

                    }
                } else {

                    Picasso.get().load(item.getImage()).into(img_profile);
                }
            } else {
                if (item.getImage_approval().equals("UNAPPROVED")) {
                    if (session.getLoginData(SessionManager.KEY_GENDER).equals("Female")) {

                        img_profile.setImageResource(R.drawable.male);
                    } else if (session.getLoginData(SessionManager.KEY_GENDER).equals("Male")) {

                        img_profile.setImageResource(R.drawable.female);
                    }
                } else {

                    Picasso.get().load(item.getImage()).into(img_profile);
                }

            }

            img_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getPhoto_view_status().equals("0") && item.getPhoto_view_count().equals("0")) {
                        //common.validImage(item.getImage(),item.getImage_approval(),item.getPhoto_protect(),
                        //                            item.getPhoto_view_status()).equals("male_password") ||
                        //                            common.validImage(item.getImage(),item.getImage_approval(),item.getPhoto_protect(),
                        //                                    item.getPhoto_view_status()).equals("female_password")
                        alertPhotoPassword(item.getMatri_id());
                    } else if (item.getPhoto_view_status().equals("0") && item.getPhoto_view_count().equals("1")) {
                        final Dialog dialog = new Dialog(context);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.show_image_alert);
                        TouchImageView img_url = (TouchImageView) dialog.findViewById(R.id.img_url);
                        Picasso.get().load(item.getImage()).into(img_url);
                        dialog.show();
                    } else {
                        if (MyApplication.getPlan()) {
                            Intent i = new Intent(context, OtherUserProfileActivity.class);
                            i.putExtra("other_id", item.getUser_id());
                            context.startActivity(i);
                        } else {
                            common.showToast("Please upgrade your membership to view this profile.");
                            context.startActivity(new Intent(context, PlanListActivity.class));
                        }
                    }
                }
            });
            /*img_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MyApplication.getPlan()){
                        Intent i=new Intent(context,OtherUserProfileActivity.class);
                        i.putExtra("other_id",item.getUser_id());
                        context.startActivity(i);
                    }else{
                        common.showToast("Please upgrade your membership to chat with this member.");
                        context.startActivity(new Intent(context,PlanListActivity.class));
                    }
                }
            });*/
            tv_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MyApplication.getPlan()) {
                        Intent i = new Intent(context, OtherUserProfileActivity.class);
                        i.putExtra("other_id", item.getUser_id());
                        context.startActivity(i);
                    } else {
                        common.showToast("Please upgrade your membership to chat with this member.");
                        context.startActivity(new Intent(context, PlanListActivity.class));
                    }
                }
            });
            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MyApplication.getPlan()) {
                        Intent i = new Intent(context, OtherUserProfileActivity.class);
                        i.putExtra("other_id", item.getUser_id());
                        context.startActivity(i);
                    } else {
                        common.showToast("Please upgrade your membership to chat with this member.");
                        context.startActivity(new Intent(context, PlanListActivity.class));
                    }
                }
            });
            btn_like.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {

                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    removeLike(position, item.getMatri_id());
                }
            });

            return rowView;

        }

        ;
    }

    private void alertPhotoPassword(final String matri_id) {
        final String[] arr = new String[]{"We found your profile to be a good match. Please send me Photo password to proceed further.",
                "I am interested in your profile. I would like to view photo now, send me password."};
        final String[] selected = {"We found your profile to be a good match. Please send me Photo password to proceed further."};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(LikeProfileActivity.this);

        alt_bld.setTitle("Photos View Request");
        alt_bld.setSingleChoiceItems(arr, 0, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                //dialog.dismiss();// dismiss the alertbox after chose option
                selected[0] = arr[item];
            }
        });
        alt_bld.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendRequest(selected[0], matri_id);
            }
        });
        alt_bld.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //alertpassword(password,url);
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();

    }

    private void sendRequest(String int_msg, String matri_id) {
        pd = new ProgressDialog(LikeProfileActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();

        HashMap<String, String> param = new HashMap<>();
        param.put("interest_message", int_msg);
        param.put("receiver_id", matri_id);
        param.put("requester_id", session.getLoginData(SessionManager.KEY_matri_id));

        common.makePostRequestTime(Utils.photo_password_request, param, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), object.getString("errmessage"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    common.showToast(getString(R.string.err_msg_try_again_later));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (pd != null)
                    pd.dismiss();
                if(error.networkResponse!=null) {
             common.showToast(Common.getErrorMessageFromErrorCode(error.networkResponse.statusCode));
}
            }
        });

    }

    private void removeLike(final int position, String id) {
        pd = new ProgressDialog(LikeProfileActivity.this);
        pd.setIndeterminate(true);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        HashMap<String, String> param = new HashMap<>();
        param.put("matri_id", session.getLoginData(SessionManager.KEY_matri_id));
        param.put("like_status", "No");
        param.put("other_id", id);

        common.makePostRequestTime(Utils.like_profile, param, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                AppDebugLog.print("response in removeLike : " + response);
                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getString("status").equals("success")) {
                        common.showAlert("Unlike", object.getString("errmessage"), R.drawable.heart_gray_fill);
                        list.remove(position);
                        if (list.size() == 0) {
                            tv_no_data.setVisibility(View.VISIBLE);
                            lv_like.setVisibility(View.GONE);
                        }
//                        AppDebugLog.print("list size after unlike : " + list.size());
                        initializeAdapter();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    common.showToast(getString(R.string.err_msg_try_again_later));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (pd != null)
                    pd.dismiss();
                if(error.networkResponse!=null) {
             common.showToast(Common.getErrorMessageFromErrorCode(error.networkResponse.statusCode));
}
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
