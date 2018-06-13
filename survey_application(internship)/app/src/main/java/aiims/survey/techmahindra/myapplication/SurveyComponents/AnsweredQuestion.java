package aiims.survey.techmahindra.myapplication.SurveyComponents;

import android.util.Log;

import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by yashjain on 7/6/17.
 */

public class AnsweredQuestion extends Question {

    private ArrayList<String> answer;

    @Expose
    private int viewId;

    public AnsweredQuestion() {
        answer = new ArrayList<String>();
        //Required to Set if Question is UnAnswered
    }


    /*Constructor*/

    public AnsweredQuestion(Question question) {
        super(question);
    }

    public boolean isAnswered() {
        return answer.isEmpty();
    }
    /*Getter and Setter*/

    public Question getQuestion() {
        return this;
    }

    public void setQuestion(Question question) {
        this.sId=question.sId;
        this.qNo=question.qNo;
        this.qText=question.qText;
        this.options=question.options;
        this.language=question.language;
        this.multiSelect=question.multiSelect;
        this.pageNo=question.pageNo;
        this.resolver=question.resolver;
    }

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }

    public String getSingleAnswer() {
        return answer.get(0);
    }

    public void addAnswer(String answer) {
        if (isMultiSelect()) {
            this.answer.add(answer);
        } else {
            this.answer.clear();
            this.answer.add(answer);
        }

        Log.i("ANSWER ADDED TO Q " + qText, answer);
    }

    public void removeAnswer(String answer) {
        if (isMultiSelect()) {
            ArrayList<String> newAnswer = new ArrayList<>();
            for (String s : this.answer) {
                if (!answer.equals(s)) {
                    newAnswer.add(s);
                }
            }
            this.answer = newAnswer;
        } else {
            if (answer.equals(this.answer.get(0))) {
                this.answer.clear();
            }
        }
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }
}
