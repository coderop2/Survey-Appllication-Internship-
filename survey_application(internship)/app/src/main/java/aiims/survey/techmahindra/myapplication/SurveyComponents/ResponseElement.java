package aiims.survey.techmahindra.myapplication.SurveyComponents;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by yashjain on 7/17/17.
 */

public class ResponseElement {

    private int pageNo;
    private int qNo;
    private ArrayList<String> optionSelected;

    public ResponseElement() {

    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getqNo() {
        return qNo;
    }

    public void setqNo(int qNo) {
        this.qNo = qNo;
    }

    public ArrayList<String> getOptionSelected() {
        return optionSelected;
    }

    public void setOptionSelected(ArrayList<String> optionSelected) {
        this.optionSelected = optionSelected;
    }
}
