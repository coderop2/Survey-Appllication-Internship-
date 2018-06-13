package aiims.survey.techmahindra.myapplication.Activities;

import android.content.Intent;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.internal.b;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import aiims.survey.techmahindra.myapplication.Adapters.QuestionAdapter;
import aiims.survey.techmahindra.myapplication.Adapters.SurveyAdapter;
import aiims.survey.techmahindra.myapplication.Database.DbConstants;
import aiims.survey.techmahindra.myapplication.Database.QuestionTable;
import aiims.survey.techmahindra.myapplication.Database.ResponseTable;
import aiims.survey.techmahindra.myapplication.Database.SurveyDbOpenHelper;
import aiims.survey.techmahindra.myapplication.Location.LocationFinder;
import aiims.survey.techmahindra.myapplication.NetworkComponents.SurveySync;
import aiims.survey.techmahindra.myapplication.R;
import aiims.survey.techmahindra.myapplication.SharedPreferences.SharedPrefManager;
import aiims.survey.techmahindra.myapplication.SurveyComponents.ActiveSurvey;
import aiims.survey.techmahindra.myapplication.SurveyComponents.AnsweredQuestion;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Question;
import aiims.survey.techmahindra.myapplication.SurveyComponents.QuestionResolver;
import aiims.survey.techmahindra.myapplication.SurveyComponents.ResponderInfo;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Response;
import aiims.survey.techmahindra.myapplication.SurveyComponents.ResponseElement;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

import static aiims.survey.techmahindra.myapplication.Adapters.SurveyAdapter.KEY_INTENT_RESPONDERINFO;
import static aiims.survey.techmahindra.myapplication.Adapters.SurveyAdapter.KEY_INTENT_SURVEY;

public class QuestionListActivity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView mRecyclerView;
    private QuestionAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //private AnsweredQuestion[] qDataSet;
    private AppCompatButton nextSubmit;
    private AppCompatTextView pageNo;
    private ActiveSurvey mActiveSurvey;
    private int currentPageNo;
    private ArrayList<ResponseElement> responseElements;
    private Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        Bundle intentInfo = getIntent().getExtras();
        Survey survey = (Survey) intentInfo.getSerializable(KEY_INTENT_SURVEY);
        Log.i("SURVEY SID", survey.getsId());
        Log.i("SURVEY LANGUAGE", survey.getLanguage());
        ResponderInfo responderInfo = (ResponderInfo) intentInfo.getSerializable(KEY_INTENT_RESPONDERINFO);
        Log.i("Responder Info", responderInfo.getfName());
        response = new Response();
        response.setsId(survey.getsId());
        response.setSynced(DbConstants.OFFLINE);
        response.setLanguage(survey.getLanguage());
        response.setVersion(survey.getVersion());
        response.setUserId(SharedPrefManager.getInstance(this).getUsername());
        response.setResponderInfo(responderInfo);
        mActiveSurvey = new ActiveSurvey(survey, this);
        mActiveSurvey.setResponderInfo(responderInfo);
        nextSubmit = (AppCompatButton) findViewById(R.id.aqlNextSubmit);
        nextSubmit.setOnClickListener(this);
        pageNo = (AppCompatTextView) findViewById(R.id.aqlPageNo);
        mRecyclerView = (RecyclerView) findViewById(R.id.questionListRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        responseElements = new ArrayList<>();
        LocationFinder.init(this, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //FOR MORE ACCURACY USE DOUBLE ONLY!!!
                Location location = locationResult.getLastLocation();
                response.setLatitude(Float.valueOf(String.valueOf(location.getLatitude())));
                response.setLongitude(Float.valueOf(String.valueOf(location.getLongitude())));
                response.setAltitude(Float.valueOf(String.valueOf(location.getAltitude())));
            }
        });
        currentPageNo = -1;
        setNextPage(mActiveSurvey.isFinalPage(0) ? getString(R.string.aqlSubmit) : getString(R.string.aqlNext));


        /*
        qDataSet=new AnsweredQuestion[8];
        QuestionTable questionTable=new SurveyDbOpenHelper(this).getQuestionTable();
        for(int i=0;i<8;i++){
            qDataSet[i]=new AnsweredQuestion();
            qDataSet[i].setsId("sid");
            qDataSet[i].setPageNo(10);
            qDataSet[i].setOptions(new String[]{"Option 1","Option 2","Option 3"});
            qDataSet[i].setLanguage("lang");
            qDataSet[i].setqText(String.valueOf("This is Question "+String.valueOf(i)));
            qDataSet[i].setqNo(i);
            qDataSet[i].setMultiSelect(i%2==0);
            qDataSet[i].setResolver(new QuestionResolver());
            qDataSet[i].setMode(String.valueOf(i));
            //questionTable.addQuestion(qDataSet[i]);
        }

        Survey s=new Survey();
        s.setsId("sid");
        s.setLanguage("lang");
        ArrayList<Question> a=questionTable.getQuestions(s);
        AnsweredQuestion[] b=new AnsweredQuestion[a.size()];
        for(int i=0;i<a.size();i++){
            b[i]=a.get(i).getAnsweredQuestion();
        }
        mAdapter = new QuestionAdapter(mActiveSurvey.getQuestionsForPage(10),this);
        mRecyclerView.setAdapter(mAdapter);
        */
    }


    public void setNextPage(String buttonText) {
        currentPageNo++;
        Log.d("Button String", buttonText);
        nextSubmit.setText(buttonText);
        pageNo.setText(getString(R.string.aqlpageNoText) + String.valueOf(currentPageNo + 1) + "/" + String.valueOf(mActiveSurvey.getTotalPages()));
        mAdapter = new QuestionAdapter(mActiveSurvey.getQuestionsForPage(currentPageNo), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_in));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aqlNextSubmit:
                if (mActiveSurvey.isFinalPage(currentPageNo)) {
                    saveStatus();
                    generateResponse();
                    finish();
                } else if (mActiveSurvey.isFinalPage(currentPageNo + 1)) {
                    saveStatus();
                    setNextPage(getString(R.string.aqlSubmit));
                } else {
                    saveStatus();
                    setNextPage(getString(R.string.aqlNext));
                }
        }
    }

    private void saveStatus() {
        responseElements.addAll(mAdapter.getResponse());
    }

    @Override
    protected void onPause() {
        super.onPause();
        SplashActivity.isOnline = false;
        LocationFinder.stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationFinder.startLocationUpdates();
        SplashActivity.isOnline = true;
    }

    private void generateResponse() {

        try {
            response.setResult(new JSONArray(new Gson().toJson(responseElements)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ResponseTable responseTable = new SurveyDbOpenHelper(this).getResponseTable();
        responseTable.addResponse(response);
        Log.d(" RESPONSE", new Gson().toJson(response));
        SurveySync surveySync = new SurveySync(this);
        surveySync.syncResponse(new SurveySync.ConnectionCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(QuestionListActivity.this, "Response Submitted", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(QuestionListActivity.this, "Response Saved Offline", Toast.LENGTH_LONG).show();
            }
        });
    }
}
