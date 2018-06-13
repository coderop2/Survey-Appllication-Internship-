package aiims.survey.techmahindra.myapplication.NetworkComponents;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

import aiims.survey.techmahindra.myapplication.Database.SurveyDbOpenHelper;
import aiims.survey.techmahindra.myapplication.Database.SurveyTable;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

/**
 * Created by yashjain on 7/4/17.
 */

public class SurveyResult {

    private String success;
    private Survey[] surveys;

    public SurveyResult(){

    }

    public Survey[] getSurveys() {
        return surveys;
    }

    public void setSurveys(Survey[] surveys) {
        this.surveys = surveys;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public static SurveyResult generateSurveyResult(String result){
        return new Gson().fromJson(result,SurveyResult.class);
    }

    public ArrayList<Survey> addOrUpdateInDb(Context context){
        SurveyTable surveyTable=new SurveyDbOpenHelper(context).getSurveyTable();
        ArrayList<Survey> updatedSurvey=new ArrayList<>();
        for (int i = 0; i <surveys.length ; i++) {
            if(surveyTable.isPresent(surveys[i])) {
                surveyTable.updateSurvey(surveys[i]);
                updatedSurvey.add(surveys[i]);
            }
            else {
                surveyTable.addSurvey(surveys[i]);
            }
        }
        return updatedSurvey;
    }

}
