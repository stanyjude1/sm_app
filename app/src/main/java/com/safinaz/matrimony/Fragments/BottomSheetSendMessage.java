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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottomsheet_send_message, container, false);
        ButterKnife.bind(this, v);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formCheck()){
                    Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_LONG).show();
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

        return flag;
    }
}
