package aiims.survey.techmahindra.myapplication.SurveyComponents;

import java.io.Serializable;

/**
 * Created by yashjain on 7/4/17.
 */

public class ResponderInfo implements Serializable {

    private String fName;
    private String lName;
    private String gender;
    private float weight;
    private float height;
    private String bloodGroup;
    private String address;
    private int age;

    /*Constructor*/

    public ResponderInfo(){

    }

    /*Setters And Getters*/



    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
