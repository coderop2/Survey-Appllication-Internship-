package aiims.survey.techmahindra.myapplication.SurveyComponents;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by yashjain on 7/4/17.
 */

public class Response {

    private int rId;
    private String sId;
    private String userId;
    private String language;
    private int version;
    private float latitude;
    private float longitude;
    private float altitude;
    private String synced;
    private JSONArray result;
    private ResponderInfo responderInfo;


    public Response(){

    }

    public void addResponse(AnsweredQuestion answeredQuestion){

    }

    /*Setters And Getters*/

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public JSONArray getResult() {
        return result;
    }

    public void setResult(JSONArray result) {
        this.result = result;
    }

    public void setResult(String result) {
        try {
            this.result = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ResponderInfo getResponderInfo() {
        return responderInfo;
    }

    public void setResponderInfo(ResponderInfo responderInfo) {
        this.responderInfo = responderInfo;
    }
}
