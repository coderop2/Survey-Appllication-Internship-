package aiims.survey.techmahindra.myapplication.NetworkComponents;

/**
 * Created by yashjain on 7/4/17.
 */

public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
