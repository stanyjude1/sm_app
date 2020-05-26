package com.safinaz.matrimony.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.safinaz.matrimony.R;
import com.safinaz.matrimony.Utility.Common;

public class UserTypeSelectionActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnOnlineMember, btnExclusiveMember;
    private Button btnSubmit;
    private String userType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_selection);

        initialize();
    }

    private void initialize() {
        btnOnlineMember = findViewById(R.id.btnOnlineMember);
        btnExclusiveMember = findViewById(R.id.btnExclusiveMember);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnOnlineMember.setSelected(false);
        btnExclusiveMember.setSelected(false);

        btnOnlineMember.setOnClickListener(this);
        btnExclusiveMember.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOnlineMember:
                btnOnlineMember.setSelected(true);
                btnExclusiveMember.setSelected(false);
                userType = "o";
                break;
            case R.id.btnExclusiveMember:
                btnOnlineMember.setSelected(false);
                btnExclusiveMember.setSelected(true);
                userType = "e";
                break;
            case R.id.btnSubmit:
                if(btnOnlineMember.isSelected() || btnExclusiveMember.isSelected()) {
                    Intent intent = new Intent(this, RegisterFirstActivity.class);
                    intent.putExtra("user_type", userType);
                    startActivity(intent);
                }else{
                    Common.showToast(this,"Please select user type");
                }
                break;
        }
    }
}
