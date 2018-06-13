package aiims.survey.techmahindra.myapplication.NetworkComponents;

import android.content.Context;

import java.util.ArrayList;

import aiims.survey.techmahindra.myapplication.Database.DbConstants;
import aiims.survey.techmahindra.myapplication.Database.ResponseTable;
import aiims.survey.techmahindra.myapplication.Database.SurveyDbOpenHelper;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Response;

/**
 * Created by yashjain on 7/4/17.
 */

public class ResponseRequest {


    /*Add UserId*/

    public static final String KEY_RESPONE_REQUEST="responserequest";
    private ArrayList<Response> responses;

    public ResponseRequest(){
    }

    public void setData(Context context){
        ResponseTable responseTable=new SurveyDbOpenHelper(context).getResponseTable();
        responses=responseTable.getResponse(DbConstants.EXPORTED);
        responses.addAll(responseTable.getResponse(DbConstants.OFFLINE));
    }

    public int getTotalResponses(){
        if(responses==null) return -1;
        return responses.size();
    }

}
