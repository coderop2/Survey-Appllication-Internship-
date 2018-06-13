package aiims.survey.techmahindra.myapplication.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

import aiims.survey.techmahindra.myapplication.R;
import aiims.survey.techmahindra.myapplication.SharedPreferences.SharedPrefManager;
import aiims.survey.techmahindra.myapplication.SurveyComponents.ResponderInfo;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

import static aiims.survey.techmahindra.myapplication.Adapters.SurveyAdapter.KEY_INTENT_RESPONDERINFO;
import static aiims.survey.techmahindra.myapplication.Adapters.SurveyAdapter.KEY_INTENT_SURVEY;

public class ResponderInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatSpinner bloodGroup;
    private RadioGroup gender;
    private AppCompatEditText fName, lName, weight, height, address, age;
    private AppCompatButton submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent callingIntent = getIntent();
        Survey survey = (Survey) callingIntent.getSerializableExtra(KEY_INTENT_SURVEY);
        Locale locale = new Locale(survey.getLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_responder_info);
        bloodGroup = (AppCompatSpinner) findViewById(R.id.riaBloodGroup);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bloodGroup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(adapter);

        gender = (RadioGroup) findViewById(R.id.riaGender);
        fName = (AppCompatEditText) findViewById(R.id.riaFName);
        lName = (AppCompatEditText) findViewById(R.id.riaLName);
        weight = (AppCompatEditText) findViewById(R.id.riaWeight);
        height = (AppCompatEditText) findViewById(R.id.riaHeight);
        address = (AppCompatEditText) findViewById(R.id.riaAddress);
        age = (AppCompatEditText) findViewById(R.id.riaAge);
        submit = (AppCompatButton) findViewById(R.id.riaSubmit);
        submit.setOnClickListener(this);
        age.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClick(null);
                    return true;
                }
                return false;
            }
        });
    }

    private void startIntentForQuestionListActivity() {
        ResponderInfo responderInfo = generateResponderInfo();
        Intent callingIntent = getIntent();
        Intent intent = new Intent(ResponderInfoActivity.this, QuestionListActivity.class);
        intent.putExtra(KEY_INTENT_SURVEY, callingIntent.getSerializableExtra(KEY_INTENT_SURVEY));
        intent.putExtra(KEY_INTENT_RESPONDERINFO, responderInfo);
        startActivity(intent);
        finish();
    }

    private ResponderInfo generateResponderInfo() {
        ResponderInfo responderInfo = new ResponderInfo();
        responderInfo.setfName(lName.getText().toString());
        responderInfo.setfName(fName.getText().toString());
        responderInfo.setBloodGroup(bloodGroup.getSelectedItem().toString());
        responderInfo.setGender(((AppCompatRadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString());
        responderInfo.setWeight(Float.valueOf(weight.getText().toString()));
        responderInfo.setHeight(Float.valueOf(height.getText().toString()));
        responderInfo.setAddress(address.getText().toString());
        responderInfo.setAge(Integer.valueOf(age.getText().toString()));
        return responderInfo;
    }

    private boolean areResultsValid() {
        return !(gender.getCheckedRadioButtonId() == -1 ||
                fName.getText().toString().equals("") ||
                lName.getText().toString().equals("") ||
                weight.getText().toString().equals("") ||
                height.getText().equals("") ||
                address.getText().equals("") ||
                age.getText().equals(""));

    }

    @Override
    protected void onResume() {
        super.onResume();
        SplashActivity.isOnline = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SplashActivity.isOnline = false;
    }

    @Override
    public void onClick(View view) {
        if (areResultsValid()) {
            startIntentForQuestionListActivity();
        } else {
            Snackbar.make(findViewById(R.id.riaLL), getString(R.string.riaEmptyFields), Snackbar.LENGTH_LONG).show();
        }
    }
}
