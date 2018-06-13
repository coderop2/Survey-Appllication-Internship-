package aiims.survey.techmahindra.myapplication.NetworkComponents;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

import aiims.survey.techmahindra.myapplication.Database.QuestionTable;
import aiims.survey.techmahindra.myapplication.Database.SurveyDbOpenHelper;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Question;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

/**
 * Created by yashjain on 7/4/17.
 */

public class QuestionResult {

    private String success;
    private Question[] questions;
    private String sId;
    private String langauge;

    public QuestionResult(){

    }

    public static QuestionResult generateQuestionResult(String result){
        return new Gson().fromJson(result,QuestionResult.class);
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions.toArray(new Question[questions.size()]);
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public void addOrUpdateInDb(Context context){
        QuestionTable questionTable=new SurveyDbOpenHelper(context).getQuestionTable();
        questionTable.deleteAllQuestions(sId,langauge);
        ArrayList<Question> arrayList=new ArrayList<>();
        Collections.addAll(arrayList, questions);
        questionTable.addQuestions(arrayList);
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getLangauge() {
        return langauge;
    }

    public void setLangauge(String langauge) {
        this.langauge = langauge;
    }
}
