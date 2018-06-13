package aiims.survey.techmahindra.myapplication.SurveyComponents;

import java.io.Serializable;

/**
 * Created by yashjain on 7/4/17.
 */

public class Survey implements Serializable {

    public final static String TYPE_NATIONAL="national";
    public final static String TYPE_INTERNATIONAL="international";
    public final static String TYPE_LOCAL="local";

    protected String sId;
    protected String type;
    protected int version;
    protected String title;
    protected String description;
    protected int totalQuestions;
    protected String language;
    protected int totalPage;

    public Survey(){
    }

    public Survey(Survey survey){
        this.sId=survey.sId;
        this.type=survey.type;
        this.version=survey.version;
        this.title=survey.title;
        this.description=survey.description;
        this.totalQuestions=survey.totalQuestions;
        this.language=survey.language;
        this.totalPage=survey.totalPage;
    }

    /*
    * Getters and Setters
    * */

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalPage(){
        return totalPage;
    }

    public void setTotalPage(int totalPage){
        this.totalPage=totalPage;
    }
}
