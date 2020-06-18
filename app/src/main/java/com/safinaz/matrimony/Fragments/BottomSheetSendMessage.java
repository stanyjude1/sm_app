package com.safinaz.matrimony.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.safinaz.matrimony.Activities.VendorDetail;
import com.safinaz.matrimony.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetSendMessage extends BottomSheetDialogFragment {
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottomsheet_send_message, container, false);
        ButterKnife.bind(this, v);

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
