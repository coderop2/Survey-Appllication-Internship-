package aiims.survey.techmahindra.myapplication.SurveyComponents;

/**
 * Created by yashjain on 7/4/17.
 */

public class User {

    private String userId;
    private String name;
    private String[] languages;

    public static void login(String username,String password){
    }

    public static void logout(){
    }

    public User(){
    }

    public void validateUser(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }
}
