package aiims.survey.techmahindra.myapplication.Database;

/**
 * Created by yashjain on 7/4/17.
 */

public interface DbConstants {

    //DB CONFIG DETAILS
    String DB_NAME="surveydb";
    int DB_VERSION=1;

    //TABLE NAMES
    String TABLE_SURVEY="survey";
    String TABLE_QUESTION="question";
    String TABLE_RESPONSE="response";

    //COLUMN NAMES
        /*SURVEY TABLE*/
    String COL_SID="sid";
    String COL_TITLE="title";
    String COL_DESCRIPTION="description";
    String COL_LANGUAGE="language";
    String COL_TOTALQ="totalq";
    String COL_VERSION="version";
    String COL_TYPE="type";
    String COL_TOTALP="totalp";
    /*QUESTION TABLE*/
    String COL_QNO="qno";
    String COL_QTEXT="qtext";
    String COL_MULTISELECT="multiselect";
    String COL_OPTIONS="otext";
    String COL_PAGE="page";
    String COL_RESOLVER="resolver";
    /*RESPONSE TABLE*/
    String COL_USERID="userid";
    String COL_LATITUDE="latitude";
    String COL_LONGITUDE="longitude";
    String COL_ALTITUDE="altitude";
    String COL_SYNCED="synced";
    String COL_RESULT="result";
    String COL_FNAME="fname";
    String COL_LNAME="lname";
    String COL_ADDRESS="address";
    String COL_AGE="age";
    String COL_RID="rid";
    String COL_GENDER="gender";
    String COL_BLOODG="bloodg";
    String COL_WEIGHT="weight";
    String COL_HEIGHT="height";

    //MODES FOR QUESTION
    String AND="and";
    String OR="or";

    //BLOOD GROUPS
    String APOS="A+";
    String ANEG="A-";
    String BPOS="B+";
    String BNEG="B-";
    String ABPOS="AB+";
    String ABNEG="AB-";
    String OPOS="O+";
    String ONEG="O-";

    //GENDER TYPES
    String MALE="male";
    String FEMALE="female";
    String OTHER="other";

    //DATA TYPES
    String TEXT=" TEXT ";
    String INTEGER=" INTEGER ";
    String BOOLEAN=" BOOLEAN ";
    String REAL=" REAL ";
    String INTEGER_PRIMARY_AUTO= " INTEGER PRIMARY KEY AUTOINCREMENT ";

    //VALUES FOR SYNCED
    String SYNCED_TO_SERVER="syncedtoserver";
    String EXPORTED="exported";
    String OFFLINE="offline";

    //VALUES FOR TYPE
    String TYPE_NATIONAL="national";
    String TYPE_INTERNATIONAL="international";
    String TYPE_LOCAL="local";


    //TABLE CREATE STATEMENTS
    String CREATE_SURVEY_TABLE=
            " CREATE TABLE IF NOT EXISTS "+TABLE_SURVEY+" ("+
                    COL_SID+TEXT+","+
                    COL_TITLE+TEXT+","+
                    COL_DESCRIPTION+TEXT+","+
                    COL_LANGUAGE+TEXT+","+
                    COL_TOTALQ+INTEGER+","+
                    COL_VERSION+INTEGER+","+
                    COL_TYPE+TEXT+","+
                    COL_TOTALP+INTEGER+")";
    String CREATE_QUESTION_TABLE=
            " CREATE TABLE IF NOT EXISTS "+TABLE_QUESTION+" ("+
                    COL_SID+TEXT+","+
                    COL_QNO+INTEGER+","+
                    COL_QTEXT+TEXT+","+
                    COL_MULTISELECT+BOOLEAN+","+ //BOOLEANS ARE STORED AS 0 AND 1
                    COL_LANGUAGE+TEXT+","+
                    COL_OPTIONS+TEXT+","+
                    COL_PAGE+INTEGER+","+
                    COL_RESOLVER+TEXT+")";
    String CREATE_RESPONSE_TABLE=
            " CREATE TABLE IF NOT EXISTS "+TABLE_RESPONSE+" ("+
                    COL_RID+INTEGER_PRIMARY_AUTO+","+
                    COL_SID+TEXT+","+
                    COL_USERID+TEXT+","+
                    COL_LANGUAGE+TEXT+","+
                    COL_VERSION+INTEGER+","+
                    COL_LATITUDE+REAL+","+
                    COL_LONGITUDE+REAL+","+
                    COL_ALTITUDE+REAL+","+
                    COL_SYNCED+TEXT+","+
                    COL_RESULT+TEXT+","+
                    COL_FNAME+TEXT+","+
                    COL_LNAME+TEXT+","+
                    COL_GENDER+TEXT+","+
                    COL_BLOODG+TEXT+","+
                    COL_WEIGHT+REAL+","+
                    COL_HEIGHT+REAL+","+
                    COL_ADDRESS+TEXT+","+
                    COL_AGE+INTEGER+")";

}
