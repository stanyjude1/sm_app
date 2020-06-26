package com.safinaz.matrimony.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.safinaz.matrimony.Activities.VendorDetail;
import com.safinaz.matrimony.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetSendMessage extends BottomSheetDialogFragment {
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.message)
    EditText message;
    @BindView(R.id.send_btn)
    Button sendBtn;
    @BindView(R.id.customer_name)
    EditText customerName;
    @BindView(R.id.customer_mobile)
    EditText customerMobile;
    @BindView(R.id.weddingDate)
    EditText weddingDate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottomsheet_send_message, container, false);
        ButterKnife.bind(this, v);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        weddingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formCheck()){
                    if(getActivity() instanceof VendorDetail) {
                        ((VendorDetail) getActivity()).sendMessage(customerName.getText().toString(), email.getText().toString(), customerMobile.getText().toString(), message.getText().toString());
                    }
                    dismiss();
                }
            }
        });
        return v;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        weddingDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog bottomSheetDialog = super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetDialog dialogc = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet =  dialogc.findViewById(R.id.design_bottom_sheet);

            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        return bottomSheetDialog;
    }

    public boolean formCheck(){
        boolean flag = true;
        if(email.getText().toString().isEmpty()){
            email.setError("Required");
            flag = false;
        }

        if(message.getText().toString().isEmpty()){
            message.setError("Required");
            flag = false;
        }

        if(customerName.getText().toString().isEmpty()){
            customerName.setError("Required");
            flag = false;
        }

        if(customerMobile.getText().toString().isEmpty()){
            customerMobile.setError("Required");
            flag = false;
        }

        return flag;
    }
}
