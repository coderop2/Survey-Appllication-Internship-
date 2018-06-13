package aiims.survey.techmahindra.myapplication.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.util.ArrayList;

import aiims.survey.techmahindra.myapplication.SurveyComponents.Question;
import aiims.survey.techmahindra.myapplication.SurveyComponents.QuestionResolver;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

/**
 * Created by yashjain on 7/7/17.
 */

public class QuestionTable implements DbConstants{
    private SQLiteDatabase db;

    public QuestionTable(){
    }

    public QuestionTable(SQLiteDatabase db){
        this.db=db;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public long addQuestion(Question question){
        Gson gson=new Gson();
        ContentValues values=new ContentValues();
        values.put(COL_SID,question.getsId());
        values.put(COL_QNO,question.getqNo());
        values.put(COL_QTEXT,question.getqText());
        values.put(COL_MULTISELECT,question.isMultiSelect());
        values.put(COL_LANGUAGE,question.getLanguage());
        values.put(COL_OPTIONS,gson.toJson(question.getOptions()).toString());
        values.put(COL_PAGE,question.getPageNo());
        values.put(COL_RESOLVER,gson.toJson(question.getResolver()).toString());
        return db.insertOrThrow(TABLE_QUESTION,null,values);
    }


    public boolean isPresent(Question question){
        String projection[]={COL_SID};
        String selection=COL_SID+" = ? AND "+COL_PAGE+" = ? AND "+COL_LANGUAGE+" = ? AND "+COL_QNO+" = ?";
        Cursor cursor=db.query(TABLE_QUESTION,projection,selection,new String[]{question.getsId(),String.valueOf(question.getPageNo()),question.getLanguage(),String.valueOf(question.getqNo())},null,null,null);
        boolean isPresent=cursor.moveToNext();
        cursor.close();
        return isPresent;
    }

    public void addQuestions(ArrayList<Question> questionList){
        for(Question q:questionList)
            addQuestion(q);
    }

    public int deleteQuestion(Question question){
        String whereClause=COL_SID+" = ? AND "+COL_LANGUAGE+" = ? AND "+COL_QNO+" = ?";
        return db.delete(TABLE_QUESTION,whereClause,new String[]{question.getsId(),question.getLanguage(),String.valueOf(question.getqNo())});
    }

    public int deleteAllQuestions(Survey survey){
        String whereClause=COL_SID+" = ? AND "+COL_LANGUAGE+" = ?";
        return db.delete(TABLE_QUESTION,whereClause,new String[]{survey.getsId(),survey.getLanguage()});
    }

    public int deleteAllQuestions(String sId,String language){
        String whereClause=COL_SID+" = ? AND "+COL_LANGUAGE+" = ?";
        return db.delete(TABLE_QUESTION,whereClause,new String[]{sId,language});
    }

    public ArrayList<Question> getQuestions(Survey survey){
        String projection[]={COL_SID,COL_QNO,COL_QTEXT,COL_MULTISELECT,COL_LANGUAGE,COL_OPTIONS,COL_PAGE,COL_RESOLVER};
        String selection=COL_SID+"= ? AND "+COL_LANGUAGE+"= ?";
        String sortOrder = COL_PAGE + " ASC " + " , " + COL_QNO + " ASC ";
        Cursor cursor=db.query(TABLE_QUESTION,projection,selection,new String[]{String.valueOf(survey.getsId()),survey.getLanguage()},null,null,sortOrder);
        ArrayList<Question> questionList=new ArrayList<>();
        Gson gson=new Gson();
        while (cursor.moveToNext()){
            Question q=new Question();
            q.setsId(cursor.getString(cursor.getColumnIndexOrThrow(COL_SID)));
            q.setqNo(cursor.getInt(cursor.getColumnIndexOrThrow(COL_QNO)));
            q.setqText(cursor.getString(cursor.getColumnIndexOrThrow(COL_QTEXT)));
            q.setMultiSelect(cursor.getInt(cursor.getColumnIndexOrThrow(COL_MULTISELECT)) > 0);
            q.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(COL_LANGUAGE)));
            q.setOptions(gson.fromJson(cursor.getString(cursor.getColumnIndexOrThrow(COL_OPTIONS)),String[].class));
            q.setPageNo(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PAGE)));
            q.setResolver(gson.fromJson(cursor.getString(cursor.getColumnIndexOrThrow(COL_RESOLVER)), QuestionResolver.class));
            questionList.add(q);
        }
        return questionList;
    }

    public ArrayList<Question> getQuestions(Survey survey,int pageNo){
        String projection[]={COL_SID,COL_QNO,COL_QTEXT,COL_MULTISELECT,COL_LANGUAGE,COL_OPTIONS,COL_PAGE,COL_RESOLVER};
        String selection=COL_SID+"= ? AND "+COL_LANGUAGE+"= ? AND "+COL_PAGE+" = ?";
        String sortOrder=COL_QNO+" ASC ";
        Cursor cursor=db.query(TABLE_QUESTION,projection,selection,new String[]{String.valueOf(survey.getsId()),survey.getLanguage(),String.valueOf(pageNo)},null,null,sortOrder);
        ArrayList<Question> questionList=new ArrayList<>();
        Gson gson=new Gson();
        while (cursor.moveToNext()){
            Question q=new Question();
            q.setsId(cursor.getString(cursor.getColumnIndexOrThrow(COL_SID)));
            q.setqNo(cursor.getInt(cursor.getColumnIndexOrThrow(COL_QNO)));
            q.setqText(cursor.getString(cursor.getColumnIndexOrThrow(COL_QTEXT)));
            q.setMultiSelect(cursor.getInt(cursor.getColumnIndexOrThrow(COL_MULTISELECT)) > 0);
            q.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(COL_LANGUAGE)));
            q.setOptions(gson.fromJson(cursor.getString(cursor.getColumnIndexOrThrow(COL_OPTIONS)),String[].class));
            q.setPageNo(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PAGE)));
            q.setResolver(gson.fromJson(cursor.getString(cursor.getColumnIndexOrThrow(COL_RESOLVER)), QuestionResolver.class));
            questionList.add(q);
        }
        return questionList;
    }

    public int[][] getTotalQuestions(Survey survey) {
        String query = " SELECT " + COL_PAGE + "  , COUNT(" + COL_PAGE + ") " +
                " FROM " + TABLE_QUESTION +
                " WHERE " + COL_SID + " = '" + survey.getsId() + "' AND " + COL_LANGUAGE + " = '" + survey.getLanguage() + "' "
                + " GROUP BY " + COL_PAGE
                + " ORDER BY " + COL_PAGE + " ASC";
        Cursor cursor = db.rawQuery(query, null);
        int[][] totalPageNo = new int[cursor.getCount()][1];
        int i = 0;
        while (cursor.moveToNext()) {
            totalPageNo[i][0] = cursor.getInt(1);
        }
        return totalPageNo;
    }

}
