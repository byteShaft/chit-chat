package com.bytesahft.chitchat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button signUp;
    private EditText name;
    private EditText number;
    private EditText password;
    private boolean userExist = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUp = (Button) findViewById(R.id.signUp);
        name = (EditText) findViewById(R.id.edit_text_name);
        number = (EditText) findViewById(R.id.edit_text_phone_number);
        password = (EditText) findViewById(R.id.edit_text_password);
        signUp.setOnClickListener(this);
        number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    QBUsers.getUserByLogin(number.getText().toString(), new QBEntityCallback<QBUser>() {
                        @Override
                        public void onSuccess(QBUser qbUser, Bundle bundle) {
                            userExist = true;
                            Toast.makeText(getApplicationContext(), "user already exist with this number",
                                    Toast.LENGTH_SHORT).show();
                            number.setError("user already exist");

                        }

                        @Override
                        public void onError(QBResponseException e) {
                            userExist = false;
                            Drawable drawable = getResources().getDrawable(R.drawable.tick);
                            drawable.setBounds(0, 0, 20, 20);
                            number.setCompoundDrawables(null, null, drawable, null);

                        }
                    });
                }
            }
        });
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                number.setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        QBAuth.createSession(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession session, Bundle params) {
                // success
                System.out.println(session.toString());
                System.out.println(params.toString());
            }

            @Override
            public void onError(QBResponseException error) {
                // errors
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(SignUp.this, ContactActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp:
                if (!name.getText().toString().trim().isEmpty() &&
                        !password.getText().toString().trim().isEmpty() &&
                        !number.getText().toString().trim().isEmpty()) {
                    signUp(number.getText().toString(), password.getText().toString(),
                            name.getText().toString());
                }
                break;
        }

    }

    private void signUp(String username, String password, String name) {
        final QBUser user = new QBUser(username, password);
        user.setFullName(name);
        StringifyArrayList<String> tags = new StringifyArrayList<>();
        tags.add("test");
        tags.add("man");
        user.setTags(tags);

        QBUsers.signUp(user, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                System.out.println(user.getEmail());
                System.out.println(user.getFullName());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }

            @Override
            public void onError(QBResponseException errors) {

            }
        });
    }
}
