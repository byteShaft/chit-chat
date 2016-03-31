package com.bytesahft.chitchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextPhoneNumber;
    private EditText editTextPassword;
    private Button loginButton;
    private Button signUp;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        editTextPassword = (EditText) findViewById(R.id.et_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signUp = (Button) findViewById(R.id.btn_sign_up);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar_Horizontal);
        signUp.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up:
                startActivity(new Intent(LoginActivity.this, SignUp.class));
                break;
            case R.id.btn_login:
                String phoneNumber = editTextPhoneNumber.getText().toString();
                String password = editTextPassword.getText().toString();
                if (!phoneNumber.isEmpty() && !password.isEmpty()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    QBUser user = new QBUser(phoneNumber, password);

                    QBUsers.signIn(user, new QBEntityCallback<QBUser>() {
                        @Override
                        public void onSuccess(QBUser user, Bundle params) {
                            mProgressBar.setVisibility(View.GONE);
                            System.out.println(user);
                            Toast.makeText(getApplicationContext(), "Login success",
                                    Toast.LENGTH_SHORT).show();
                            Helpers.userLogin(true);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }

                        @Override
                        public void onError(QBResponseException errors) {
                            mProgressBar.setVisibility(View.GONE);

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All fields must be filled",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }



    }
}
