package aiims.survey.techmahindra.myapplication.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import aiims.survey.techmahindra.myapplication.BroadcastReceiver.NetworkReceiver;
import aiims.survey.techmahindra.myapplication.Database.DbConstants;
import aiims.survey.techmahindra.myapplication.Database.QuestionTable;
import aiims.survey.techmahindra.myapplication.Database.SurveyDbOpenHelper;
import aiims.survey.techmahindra.myapplication.Database.SurveyTable;
import aiims.survey.techmahindra.myapplication.Location.LocationFinder;
import aiims.survey.techmahindra.myapplication.R;
import aiims.survey.techmahindra.myapplication.SharedPreferences.SharedPrefManager;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Question;
import aiims.survey.techmahindra.myapplication.SurveyComponents.QuestionResolver;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

public class SplashActivity extends AppCompatActivity {


    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final String[] permissionRequired =
            {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};
    public static boolean isOnline;
    private ImageView splashImage;
    private Animation fadeIn, fadeOut;
    private ProgressBar progressBar;
    private List<String> pList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setLanguage();
        splashImage = (ImageView) findViewById(R.id.splashImage);
        progressBar = (ProgressBar) findViewById(R.id.splashProgressBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("Inside", "If case");
            pList = new ArrayList<String>();
            for (String s : permissionRequired) {
                if (!checkPermission(s)) {
                    pList.add(s);
                    Log.d("Permission ", s + "  added");
                }
            }
            if (pList.isEmpty())
                startBackgroundTasks();
            else
                setPermissions();
        } else {
            startBackgroundTasks();
        }
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.splash_in);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashImage.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.splash_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashImage.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashImage.startAnimation(fadeIn);

    }


    protected void setPermissions() {

        Log.d("Inside setPermissions", " hers");
        if (!pList.isEmpty()) {
            Log.d("Displaying Dialog", " hers");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.saAlertTitle);
            builder.setMessage(R.string.saAlertMessage);
            builder.setPositiveButton(R.string.saPositiveButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(SplashActivity.this, pList.toArray
                            (new String[pList.size()]), PERMISSIONS_REQUEST_CODE);
                }
            });
            builder.setCancelable(false);
            builder.setNeutralButton(R.string.saNeutralButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            builder.show();
        }
    }

    private void setLanguage() {
        Locale locale = new Locale(SharedPrefManager.getLanguage(this));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }


    protected boolean checkPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this,
                permission) == PackageManager.PERMISSION_GRANTED;
    }


    private void startBackgroundTasks() {
        AppInitializer appInitializer = new AppInitializer();
        appInitializer.execute();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSIONS_REQUEST_CODE) {
            return;
        }
        boolean isGranted = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                Log.d("GrantResult" + i, grantResults[i] == PackageManager.PERMISSION_DENIED ? "Permission Denied" : "Permission Granted");
                isGranted = false;
                break;
            }
        }
        if (isGranted) {
            LocationFinder.isGpsEnabled();
            Toast.makeText(this, R.string.saPermissionGranted, Toast.LENGTH_LONG).show();
            startBackgroundTasks();
        } else {
            Toast.makeText(this, R.string.saPermissionNotGranted, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnline = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnline = false;
    }

    private class AppInitializer extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        //TODO Do starting work of initializing here
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                BroadcastReceiver br = new NetworkReceiver();
                IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
                getApplicationContext().registerReceiver(br, filter);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Remove Below in Actual Line App
            loadQuestionOnce();
            return null;
        }


        private void loadQuestionOnce() {
            SharedPreferences sharedPreferences = getSharedPreferences("NAME", 1);
            if (sharedPreferences.getBoolean("loadedQ", false))
                return;

            SurveyTable surveyTable = new SurveyDbOpenHelper(getApplicationContext()).getSurveyTable();

            for (int i = 0; i < 5; i++) {
                Survey s = new Survey();
                s.setLanguage("en");
                s.setsId("sidEn" + String.valueOf(i));
                s.setTitle("Title En" + String.valueOf(i));
                s.setDescription("Description En" + String.valueOf(i));
                if (i % 3 == 0)
                    s.setType(DbConstants.TYPE_NATIONAL);
                else if (i % 3 == 1)
                    s.setType(DbConstants.TYPE_INTERNATIONAL);
                else
                    s.setType(DbConstants.TYPE_LOCAL);
                s.setTotalPage(2);
                s.setTotalQuestions(10);
                surveyTable.addOrUpdate(s);
                addQuestions(s);
            }

            for (int i = 0; i < 5; i++) {
                Survey s = new Survey();
                s.setLanguage("hi");
                s.setsId("आईडी हिंदी" + String.valueOf(i));
                s.setTitle("शीर्षक हिंदी" + String.valueOf(i));
                s.setDescription("विवरण हिंदी" + String.valueOf(i));
                if (i % 3 == 0)
                    s.setType(DbConstants.TYPE_NATIONAL);
                else if (i % 3 == 1)
                    s.setType(DbConstants.TYPE_INTERNATIONAL);
                else
                    s.setType(DbConstants.TYPE_LOCAL);
                s.setTotalPage(2);
                s.setTotalQuestions(10);
                surveyTable.addOrUpdate(s);
                addQuestions(s);
            }
            sharedPreferences.edit().putBoolean("loadedQ", true).commit();
        }

        private void addQuestions(Survey s) {
            ArrayList<Question> questions = new ArrayList<>();
            QuestionTable questionTable = new SurveyDbOpenHelper(getApplicationContext()).getQuestionTable();
            if (s.getLanguage().equals("en")) {
                for (int i = 0; i < 10; i++) {
                    Question question = new Question();
                    question.setResolver(new QuestionResolver());
                    question.setsId(s.getsId());
                    question.setqNo(i + 1);
                    question.setqText("This is Question " + String.valueOf(i + 1));
                    question.setMultiSelect(i % 2 == 0);
                    question.setMode(i % 3 == 0 ? DbConstants.OR : DbConstants.AND);
                    question.setPageNo(i / 5 < 1 ? 0 : 1);
                    question.setLanguage("en");
                    question.setOptions(new String[]{"Option Text 1", "Option Text 2", "Option Text 3", "Option Text 4"});
                    questions.add(question);
                }
            } else {
                for (int i = 0; i < 10; i++) {
                    Question question = new Question();
                    question.setResolver(new QuestionResolver());
                    question.setsId(s.getsId());
                    question.setqNo(i + 1);
                    question.setLanguage("hi");
                    question.setqText("यह सवाल है " + String.valueOf(i + 1));
                    question.setMultiSelect(i % 2 == 0);
                    question.setMode(i % 3 == 0 ? DbConstants.OR : DbConstants.AND);
                    question.setPageNo(i / 5 < 1 ? 0 : 1);
                    question.setOptions(new String[]{"विकल्प टेक्स्ट 1", "विकल्प टेक्स्ट 2", "विकल्प टेक्स्ट 3", "विकल्प टेक्स्ट 4"});
                    questions.add(question);
                }

            }
            questionTable.addQuestions(questions);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.INVISIBLE);
            finish();
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
    }
}
