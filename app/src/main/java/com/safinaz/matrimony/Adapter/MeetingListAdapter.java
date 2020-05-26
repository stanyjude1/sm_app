
package com.safinaz.matrimony.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.safinaz.matrimony.Model.MeetingBean;
import com.safinaz.matrimony.R;
import com.safinaz.matrimony.Utility.Common;
import com.safinaz.matrimony.Utility.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;


public class MeetingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MeetingBean> arrayList;
    private ItemListener myListener;
    private Context mContext;

    private SessionManager session;
    private int placeHolderId, photoProtectPlaceHolder;

    public MeetingListAdapter(Context mContext, List<MeetingBean> arrayList) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        session = new SessionManager(mContext);
        if (session.getLoginData(SessionManager.KEY_GENDER).equals("Female")) {
            photoProtectPlaceHolder = R.drawable.photopassword_male;
            placeHolderId = R.drawable.male;
        } else if (session.getLoginData(SessionManager.KEY_GENDER).equals("Male")) {
            photoProtectPlaceHolder = R.drawable.photopassword_female;
            placeHolderId = R.drawable.female;
        }
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ViewHolder(inflater.inflate(R.layout.cell_meeting_list, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MeetingBean meetingBean = arrayList.get(position);

        String name = meetingBean.getOtherData().getUsername() + " (" + meetingBean.getOtherData().getMatriId() + ")";
//        String description = Common.getDetailsFromValue(meetingBean.getOtherData().getAge(), meetingBean.getOtherData().getHeight(),
//                meetingBean.getOtherData().getCasteName(), meetingBean.getOtherData().getReligionName(),
//                meetingBean.getOtherData().getCityName(), meetingBean.getOtherData().getCountryName());
//        Date date = Utility.getDateFromDateString(AppConstants.fullDateTimeFormat, leadsBean.getInquiryTime());
        String dateTime = meetingBean.getDate() + " " + meetingBean.getTime();

        ((ViewHolder) holder).lblTitle.setText(name);
       // ((ViewHolder) holder).lblDetail.setText(Html.fromHtml(description));
        ((ViewHolder) holder).lblDateTime.setText(dateTime);

        Spanned sp = Html.fromHtml(String.format(mContext.getString(R.string.lbl_place),meetingBean.getPlace()));
        ((ViewHolder) holder).lblPlace.setText(sp);

        if (meetingBean.getOtherData().getPhoto1() != null) {
            Picasso.get()
                    .load(meetingBean.getOtherData().getPhoto1())
                    .placeholder(placeHolderId)
                    .error(placeHolderId)
                    .fit()
                    // .resize(0, imageWidthHeight)
                    .centerCrop()
                    .into(((ViewHolder) holder).imgProfile);
        } else {
            ((ViewHolder) holder).imgProfile.setImageResource(placeHolderId);
        }

    }

    private String getValueString(String str) {
        String tempStr = mContext.getString(R.string.lbl_not_available);
        if (str != null && str.length() > 0) return str;
        else return tempStr;
    }

    public interface ItemListener {
        void openUserProfile(String otherId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView lblTitle, lblDetail, lblPlace, lblDateTime;
        private ImageView imgProfile;

        public ViewHolder(View view) {
            super(view);

            lblTitle = view.findViewById(R.id.lblTitle);
            lblDetail = view.findViewById(R.id.lblDetail);
            lblPlace = view.findViewById(R.id.lblPlace);
            lblDateTime = view.findViewById(R.id.lblDateTime);
            imgProfile = view.findViewById(R.id.imgProfile);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                MeetingBean meetingBean = arrayList.get(getAdapterPosition());
                myListener.openUserProfile(meetingBean.getOtherData().getId());
            }
        }
    }


}
                                