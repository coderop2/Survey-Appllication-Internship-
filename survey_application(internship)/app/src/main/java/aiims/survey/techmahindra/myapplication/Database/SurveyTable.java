package aiims.survey.techmahindra.myapplication.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;

import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

/**
 * Created by yashjain on 7/7/17.
 */

public class SurveyTable implements DbConstants{
    private SQLiteDatabase db;

    public SurveyTable(){
    }

    public SurveyTable(SQLiteDatabase db){
        this.db=db;
    }


    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public long addSurvey(Survey survey){
        ContentValues values = new ContentValues();
        values.put(COL_SID, survey.getsId());
        values.put(COL_TITLE, survey.getTitle());
        values.put(COL_DESCRIPTION, survey.getDescription());
        values.put(COL_LANGUAGE, survey.getLanguage());
        values.put(COL_TOTALQ, survey.getTotalQuestions());
        values.put(COL_VERSION, survey.getVersion());
        values.put(COL_TYPE, survey.getType());
        values.put(COL_TOTALP, survey.getTotalPage());
        return db.insertOrThrow(TABLE_SURVEY, null, values);
    }

    public long updateSurvey(Survey survey){
        ContentValues values = new ContentValues();
        values.put(COL_SID,survey.getsId());
        values.put(COL_TITLE,survey.getTitle());
        values.put(COL_DESCRIPTION,survey.getDescription());
        values.put(COL_LANGUAGE,survey.getLanguage());
        values.put(COL_TOTALQ,survey.getTotalQuestions());
        values.put(COL_VERSION,survey.getVersion());
        values.put(COL_TYPE,survey.getType());
        values.put(COL_TOTALP,survey.getTotalPage());
        String selection = COL_LANGUAGE + " = ? AND " + COL_SID + " = ? ";
        return db.update(TABLE_SURVEY,values,selection,new String[]{survey.getLanguage(),survey.getsId()});
    }

    public boolean isPresent(Survey survey){
        String projection[]={COL_SID};
        String selection=COL_LANGUAGE+" = ? AND "+COL_SID+" = ?";
        Cursor cursor=db.query(TABLE_SURVEY,projection,selection,new String[]{survey.getLanguage(),survey.getsId()},null,null,null);
        boolean isPresent=cursor.moveToNext();
        cursor.close();
        return isPresent;
    }

    public long addOrUpdate(Survey survey){
        if(isPresent(survey)){
            return updateSurvey(survey);
        }
        else {
            return addSurvey(survey);
        }
    }

    public int removeSurvey(String language,int sId){
        String whereClause=COL_LANGUAGE+" = ? AND "+COL_SID+" = ?";
        return db.delete(TABLE_SURVEY,whereClause,new String[]{language,String.valueOf(sId)});
    }

    public ArrayList<Survey> getSurvey(String language,int sId){
        String[] projection={COL_SID,COL_TITLE,COL_DESCRIPTION,COL_LANGUAGE,COL_TOTALQ,COL_VERSION,COL_TYPE,COL_TOTALP};
        String selection=COL_LANGUAGE+" = ? AND "+COL_LANGUAGE+" = ?";
        Cursor cursor=db.query(TABLE_SURVEY,projection,selection,new String[]{language,String.valueOf(sId)},null,null,null);
        ArrayList<Survey> surveyList=new ArrayList<>();
        while (cursor.moveToNext()){
            Survey s=new Survey();
            s.setsId(cursor.getString(cursor.getColumnIndexOrThrow(COL_SID)));
            s.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)));
            s.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)));
            s.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(COL_LANGUAGE)));
            s.setTotalQuestions(cursor.getInt(cursor.getColumnIndexOrThrow(COL_TOTALQ)));
            s.setVersion(cursor.getInt(cursor.getColumnIndexOrThrow(COL_VERSION)));
            s.setType(cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)));
            s.setTotalPage(cursor.getInt(cursor.getColumnIndexOrThrow(COL_TOTALP)));
            surveyList.add(s);
        }
        return surveyList;
    }

    public ArrayList<Survey> getAllSurveys(String language){
        String[] projection={COL_SID,COL_TITLE,COL_DESCRIPTION,COL_LANGUAGE,COL_TOTALQ,COL_VERSION,COL_TYPE,COL_TOTALP};
        String selection=COL_LANGUAGE+" = ? ";
        Cursor cursor=db.query(TABLE_SURVEY,projection,selection,new String[]{language},null,null,null);
        ArrayList<Survey> surveyList=new ArrayList<>();
        while (cursor.moveToNext()){
            Survey s=new Survey();
            s.setsId(cursor.getString(cursor.getColumnIndexOrThrow(COL_SID)));
            s.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)));
            s.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)));
            s.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(COL_LANGUAGE)));
            s.setTotalQuestions(cursor.getInt(cursor.getColumnIndexOrThrow(COL_TOTALQ)));
            s.setVersion(cursor.getInt(cursor.getColumnIndexOrThrow(COL_VERSION)));
            s.setType(cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)));
            s.setTotalPage(cursor.getInt(cursor.getColumnIndexOrThrow(COL_TOTALP)));
            surveyList.add(s);
        }
        return surveyList;
    }


    public ArrayList<Survey> getEverySurveys(){
        String[] projection={COL_SID,COL_TITLE,COL_DESCRIPTION,COL_LANGUAGE,COL_TOTALQ,COL_VERSION,COL_TYPE,COL_TOTALP};
        Cursor cursor=db.query(TABLE_SURVEY,projection,null,null,null,null,null);
        ArrayList<Survey> surveyList=new ArrayList<>();
        while (cursor.moveToNext()){
            Survey s=new Survey();
            s.setsId(cursor.getString(cursor.getColumnIndexOrThrow(COL_SID)));
            s.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)));
            s.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)));
            s.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(COL_LANGUAGE)));
            s.setTotalQuestions(cursor.getInt(cursor.getColumnIndexOrThrow(COL_TOTALQ)));
            s.setVersion(cursor.getInt(cursor.getColumnIndexOrThrow(COL_VERSION)));
            s.setType(cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)));
            s.setTotalPage(cursor.getInt(cursor.getColumnIndexOrThrow(COL_TOTALP)));
            surveyList.add(s);
        }
        return surveyList;
    }


}
