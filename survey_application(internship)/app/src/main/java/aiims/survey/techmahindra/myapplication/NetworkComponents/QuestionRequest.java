package aiims.survey.techmahindra.myapplication.NetworkComponents;

import java.util.ArrayList;

import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

/**
 * Created by yashjain on 7/4/17.
 */

public class QuestionRequest {

    /*
    * TODO: ADD USERID WHEN CALLING
    * */


    public static final String KEY_QUESTION_REQUEST="questionrequest";
    private String[] sId;
    private String[] language;

    public QuestionRequest(){
    }

    public void setData(Survey[] surveys){
        int length=surveys.length;
        sId=new String[length];
        language=new String[length];
        for(int i=0;i<length;i++){
            sId[i]=surveys[i].getsId();
            language[i]=surveys[i].getLanguage();
        }
    }

    public void setData(ArrayList<Survey> surveys){
        setData(surveys.toArray(new Survey[surveys.size()]));
    }

    public String[] getsId() {
        return sId;
    }

    public void setsId(String[] sId) {
        this.sId = sId;
    }

    public String[] getLanguage() {
        return language;
    }

    public void setLanguage(String[] language) {
        this.language = language;
    }
}
