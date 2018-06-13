package aiims.survey.techmahindra.myapplication.NetworkComponents;

import android.content.Context;

import java.util.ArrayList;

import aiims.survey.techmahindra.myapplication.Database.SurveyDbOpenHelper;
import aiims.survey.techmahindra.myapplication.Database.SurveyTable;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

/**
 * Created by yashjain on 7/4/17.
 */

public class SurveyRequest {

    public static final String KEY_SURVEY_REQUEST="surveyrequest";
    /**
    private String userId;
     TODO: ADD USERID WHENEVER CALLING
     **/
    private String[] sIds;
    private String[] languages;
    private int[] version;


    public SurveyRequest(){
    }


    public SurveyRequest(Context context){
        setData(context);
    }

    //TODO: CALL THIS WHENEVER DATA NEEDS TO BE SET
    public void setData(Context context){
        SurveyTable surveyTable=new SurveyDbOpenHelper(context).getSurveyTable();
        ArrayList<Survey> als = surveyTable.getEverySurveys();
        Survey surveys[] = als.toArray(new Survey[als.size()]);
        int length=surveys.length;
        sIds=new String[length];
        languages=new String[length];
        version=new int[length];
        for(int i=0;i<length;i++){
            sIds[i]=surveys[i].getsId();
            languages[i]=surveys[i].getLanguage();
            version[i]=surveys[i].getVersion();
        }
    }


    public String[] getsIds() {
        return sIds;
    }

    public void setsIds(String[] sIds) {
        this.sIds = sIds;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public int[] getVersion() {
        return version;
    }

    public void setVersion(int[] version) {
        this.version = version;
    }
}
