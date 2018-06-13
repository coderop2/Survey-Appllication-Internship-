package aiims.survey.techmahindra.myapplication.SurveyComponents;

/**
 * Created by yashjain on 7/4/17.
 */

public class Question {

    protected String sId;
    protected int qNo;
    protected String qText;
    protected String[] options;
    protected String language;
    protected boolean multiSelect;
    protected int pageNo;
    protected String mode;//PS: MODE MAY BE "AND" or "OR"
    protected QuestionResolver resolver;


    public Question(){
    }

    public Question(Question question){
        this.sId=question.sId;
        this.qNo=question.qNo;
        this.qText=question.qText;
        this.options=question.options;
        this.language=question.language;
        this.multiSelect=question.multiSelect;
        this.pageNo=question.pageNo;
        this.mode=question.mode;
        this.resolver=question.resolver;
    }


    public boolean isPresentInOptions(String checkOption) {
        if (options == null) return false;
        for (int i = 0; i < options.length; i++) {
            if (checkOption.equals(options[i]))
                return true;
        }
        return false;
    }

    public AnsweredQuestion getAnsweredQuestion() {
        return new AnsweredQuestion(this);
    }

    /*
    * Getters and Setters
    * */

    public String getMode(){
        return mode;
    }

    public void setMode(String mode){
        this.mode=mode;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public int getqNo() {
        return qNo;
    }

    public void setqNo(int qNo) {
        this.qNo = qNo;
    }

    public String getqText() {
        return qText;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public QuestionResolver getResolver(){
        return resolver;
    }

    public void setResolver(QuestionResolver resolver){
        this.resolver=resolver;
    }
}
