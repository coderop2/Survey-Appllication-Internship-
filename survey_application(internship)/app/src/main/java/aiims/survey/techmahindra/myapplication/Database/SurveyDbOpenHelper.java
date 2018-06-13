package aiims.survey.techmahindra.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yashjain on 7/7/17.
 */

public class SurveyDbOpenHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private SurveyTable surveyTable;
    private QuestionTable questionTable;
    private ResponseTable responseTable;


    public SurveyDbOpenHelper(Context context) {
        super(context, DbConstants.DB_NAME, null, DbConstants.DB_VERSION);
        setUpDb();
    }


    //CAll this on a thread
    public void setUpDb() {
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConstants.CREATE_SURVEY_TABLE);
        db.execSQL(DbConstants.CREATE_QUESTION_TABLE);
        db.execSQL(DbConstants.CREATE_RESPONSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SurveyTable getSurveyTable() {
        if(surveyTable==null)
            surveyTable=new SurveyTable(db);
        return surveyTable;
    }


    public QuestionTable getQuestionTable() {
        if(questionTable==null)
            questionTable=new QuestionTable(db);
        return questionTable;
    }


    public ResponseTable getResponseTable() {
        if(responseTable==null)
            responseTable=new ResponseTable(db);
        return responseTable;
    }

}
