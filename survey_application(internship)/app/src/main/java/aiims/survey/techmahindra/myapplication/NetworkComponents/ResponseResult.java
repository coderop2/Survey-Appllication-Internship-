package aiims.survey.techmahindra.myapplication.NetworkComponents;

import android.content.Context;

import aiims.survey.techmahindra.myapplication.Database.DbConstants;
import aiims.survey.techmahindra.myapplication.Database.ResponseTable;
import aiims.survey.techmahindra.myapplication.Database.SurveyDbOpenHelper;

/**
 * Created by yashjain on 7/4/17.
 */

public class ResponseResult {

    private String success;
    private int[] rId;

    public void removeResponse(Context context){
        ResponseTable responseTable=new SurveyDbOpenHelper(context).getResponseTable();
        for(int i=0;i<rId.length;i++){
            responseTable.removeResponse(rId[i]);
        }
    }


    public void setSync(Context context){
        ResponseTable responseTable=new SurveyDbOpenHelper(context).getResponseTable();
        for(int i=0;i<rId.length;i++){
            responseTable.updateSync(rId[i], DbConstants.SYNCED_TO_SERVER);
        }
    }

    public ResponseResult(){

    }

    public int[] getrId() {
        return rId;
    }

    public void setrId(int[] rId) {
        this.rId = rId;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
