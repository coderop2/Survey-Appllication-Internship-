package aiims.survey.techmahindra.myapplication.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import aiims.survey.techmahindra.myapplication.NetworkComponents.LoginRequest;
import aiims.survey.techmahindra.myapplication.NetworkComponents.SurveySync;
import aiims.survey.techmahindra.myapplication.R;
import aiims.survey.techmahindra.myapplication.SharedPreferences.SharedPrefManager;

import static aiims.survey.techmahindra.myapplication.SharedPreferences.SharedPrefManager.KEY_LANGUAGE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    String[] langs;
    String langMap[];
    private AppCompatEditText userid, password;
    private AppCompatButton login;
    private String currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        login = (AppCompatButton) findViewById(R.id.fllogin);
        userid = (AppCompatEditText) findViewById(R.id.fluserid);
        password = (AppCompatEditText) findViewById(R.id.flpassword);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClick(null);
                    return true;
                }
                return false;
            }
        });
        langs = getResources().getStringArray(R.array.laLanguageOptions);
        langMap = getResources().getStringArray(R.array.laLangMap);
        login.setOnClickListener(this);
        currentLang = SharedPrefManager.getLanguage(this);
        //setLanguageAndView();
        showDialog();
    }


    @Override
    protected void onPause() {
        super.onPause();
        SplashActivity.isOnline = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SplashActivity.isOnline = true;
    }

    private boolean validateLoginCredentials() {
        return true;
    }

    @Override
    public void onClick(View view) {
        //TODO Remove the below line
        loadNextPage();

        if (!validateLoginCredentials()) return;
        SurveySync surveySync = new SurveySync(this);
        LoginRequest mLoginRequest = new LoginRequest();
        mLoginRequest.setUsername(userid.getText().toString());
        mLoginRequest.setPassword(password.getText().toString());
        surveySync.checkLogin(mLoginRequest, new SurveySync.ConnectionCallback() {
            @Override
            public void onSuccess() {
                loadNextPage();
            }

            @Override
            public void onFailure() {
                Toast.makeText(LoginActivity.this, R.string.laLoginFailed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showDialog() {
        currentLang = langMap[0];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.laLanguageOptions, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                currentLang = langMap[i];
                Log.d("CLICKED", currentLang);
            }
        });
        builder.setPositiveButton(R.string.laAlertPositive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPrefManager.setLanguage(LoginActivity.this, currentLang);
                setLanguageAndView();
            }
        });
        builder.show();
    }

    private void setLanguageAndView() {
        Locale locale = new Locale(SharedPrefManager.getLanguage(this));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        updateTexts();
    }

    private void updateTexts() {
        userid.setHint(R.string.userid);
        login.setText(R.string.login);
        password.setHint(R.string.password);
    }

    private void loadNextPage() {
        Intent intent = new Intent(LoginActivity.this, SurveyListActivity.class);
        intent.putExtra(KEY_LANGUAGE, currentLang);
        startActivity(intent);
        LoginActivity.this.finish();
    }

}