package com.lpzahd.lpzahd.activity.account;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lpzahd.lpzahd.R;
import com.lpzahd.lpzahd.util.PreferencesUtil;
import com.lpzahd.lpzahd.util.ResponseUtil;
import com.lpzahd.lpzahd.util.ToastUtil;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    // UI references.
    private AutoCompleteTextView mNickView;
    private EditText mPasswordView;
    private View mLoginFormView;

    // attemp login fail count
    private int loginFailCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mNickView = (AutoCompleteTextView) findViewById(R.id.nick);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if(ResponseUtil.checkOver()) {
                        attemptLogin();
                    }
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
        addNicksToAutoComplete(PreferencesUtil.getUserNicks());
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mNickView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mNickView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String nick = mNickView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(nick)) {
            mNickView.setError(getString(R.string.error_field_required));
            focusView = mNickView;
            cancel = true;
        } else if (!isNickValid(nick)) {
            mNickView.setError(getString(R.string.error_invalid_nick));
            focusView = mNickView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            loginFailCount++ ;
            if(!TextUtils.equals(PreferencesUtil.getUserPassword(nick), password)) {
                showEgg();
            } else {

            }
        }
    }

    private boolean isNickValid(String nick) {
        return nick.length() < 32;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void addNicksToAutoComplete(List<String> nicks) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, nicks);

        mNickView.setAdapter(adapter);
    }

    private void showEgg() {
        if(loginFailCount < 6) {
            ToastUtil.showToast("剩余尝试次数 : " + (6 - loginFailCount));
        } else if(loginFailCount >= 6 && loginFailCount < 10) {
            ToastUtil.showToast("用户即将被锁定", Color.MAGENTA);
        } else if(loginFailCount >= 10 && loginFailCount < 20) {

        } else if(loginFailCount >= 20 && loginFailCount < 30) {
            ToastUtil.showToast("你一定不是什么正经人!", Color.WHITE);
        } else if(loginFailCount >= 30 && loginFailCount < 35) {
            ToastUtil.showToast("我要报警了!", Color.MAGENTA);
        } else if(loginFailCount >= 35 && loginFailCount < 45) {
            ToastUtil.showToast("看我，看我，看我，你还不看我，我死给你看", Color.MAGENTA);
        } else if(loginFailCount >= 45 && loginFailCount < 55) {
            ToastUtil.showToast("少年你被绿的了吧~", Color.YELLOW);
        } else if(loginFailCount == 100) {
            ToastUtil.showToast("少年，看在你这么诚心诚意的份上，我服你了，让你进吧");
        }
    }

}

