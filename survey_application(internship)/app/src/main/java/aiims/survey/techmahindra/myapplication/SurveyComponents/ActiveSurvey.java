package aiims.survey.techmahindra.myapplication.SurveyComponents;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import aiims.survey.techmahindra.myapplication.Database.QuestionTable;
import aiims.survey.techmahindra.myapplication.Database.SurveyDbOpenHelper;

/**
 * Created by yashjain on 7/4/17.
 */

public class ActiveSurvey  extends Survey{

    private AnsweredQuestion[] answeredQuestions;
    private ResponderInfo responderInfo;
    @Expose
    private int pageQuestionCount[][];
    @Expose
    private int totalQuestion;


    public ActiveSurvey(Survey survey, Context context) {
        super(survey);
        loadAnsweredQuestions(context);
    }


    public ActiveSurvey() {
    }

    public void loadAnsweredQuestions(Context context) {
        QuestionTable questionTable = new SurveyDbOpenHelper(context).getQuestionTable();
        ArrayList<Question> questions = questionTable.getQuestions(this);
        Log.i("Question ArrayList ", new Gson().toJson(questions));
        Question question[] = questions.toArray(new Question[questions.size()]);
        answeredQuestions = new AnsweredQuestion[question.length];
        for (int i = 0; i < question.length; i++) {
            answeredQuestions[i] = new AnsweredQuestion();
            answeredQuestions[i].setQuestion(question[i]);
        }
        this.pageQuestionCount = questionTable.getTotalQuestions(this);
        this.totalQuestion = 0;
        for (int i = 0; i < pageQuestionCount.length; i++) {
            this.totalQuestion += pageQuestionCount[i][0];
        }
    }

    public int getTotalPages() {
        return pageQuestionCount.length;
    }

    public void saveResponse(){

    }

    public boolean isFinalPage(int pageNo) {
        return pageQuestionCount.length - 1 == pageNo;
    }

    public AnsweredQuestion[] getQuestionsForPage(int pageNo){
        if(pageNo<1&&pageNo>this.totalPage) return null;
        ArrayList<AnsweredQuestion> questionList=new ArrayList<>();
        for(int i=0;i<answeredQuestions.length;i++){
            if(answeredQuestions[i].getPageNo()==pageNo){
                questionList.add(answeredQuestions[i]);
            }
        }
        return questionList.toArray(new AnsweredQuestion[questionList.size()]);
    }


    public AnsweredQuestion[] getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(AnsweredQuestion[] answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public ResponderInfo getResponderInfo() {
        return responderInfo;
    }

    public void setResponderInfo(ResponderInfo responderInfo) {
        this.responderInfo = responderInfo;
    }


    public int[][] getPageQuestionCount() {
        return pageQuestionCount;
    }

    public void setPageQuestionCount(int[][] pageQuestionCount) {
        this.pageQuestionCount = pageQuestionCount;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }
}
