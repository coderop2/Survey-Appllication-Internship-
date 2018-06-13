package aiims.survey.techmahindra.myapplication.Activities;

import android.content.res.Configuration;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Locale;

import aiims.survey.techmahindra.myapplication.Adapters.SurveyAdapter;
import aiims.survey.techmahindra.myapplication.Database.DbConstants;
import aiims.survey.techmahindra.myapplication.Database.ResponseTable;
import aiims.survey.techmahindra.myapplication.Database.SurveyDbOpenHelper;
import aiims.survey.techmahindra.myapplication.Database.SurveyTable;
import aiims.survey.techmahindra.myapplication.Export.ResponseExport;
import aiims.survey.techmahindra.myapplication.NetworkComponents.SurveySync;
import aiims.survey.techmahindra.myapplication.R;
import aiims.survey.techmahindra.myapplication.SharedPreferences.SharedPrefManager;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

import static android.support.v4.view.MenuItemCompat.getActionView;

public class SurveyListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String surveyLanguage;
    private String[] langMap;
    private SurveyTable surveyTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        langMap = getResources().getStringArray(R.array.laLangMap);
        surveyLanguage = langMap[0];
        setContentView(R.layout.activity_survey_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.surveyListRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        surveyTable = new SurveyDbOpenHelper(this).getSurveyTable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SplashActivity.isOnline = true;
        Locale locale = new Locale(SharedPrefManager.getLanguage(this));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setSurveyListAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.survey_list_menu, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                surveyLanguage = langMap[i];
                setSurveyListAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.laLanguageOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync:
                SurveySync surveySync = new SurveySync(this);
                surveySync.syncSurvey(new SurveySync.ConnectionCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
                surveySync.syncResponse(new SurveySync.ConnectionCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
                return true;
            case R.id.export:
                ResponseExport export = new ResponseExport();
                if (export.isExternalStorageWritable()) {
                    export.exportResults(this);
                    Snackbar.make(findViewById(R.id.aslRR), getString(R.string.slaExportSuccess) + export.getFilePath(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(findViewById(R.id.aslRR), getString(R.string.slaNoSdCard), Snackbar.LENGTH_LONG).show();
                }
                return true;
            case R.id.spinner:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        SplashActivity.isOnline = false;
    }

    private void setSurveyListAdapter() {
        ArrayList<Survey> surveys = surveyTable.getAllSurveys(surveyLanguage);
        mAdapter = new SurveyAdapter(surveys.toArray(new Survey[surveys.size()]), this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
