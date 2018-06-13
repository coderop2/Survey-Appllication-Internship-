package aiims.survey.techmahindra.myapplication.Database;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

import aiims.survey.techmahindra.myapplication.SurveyComponents.ResponderInfo;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Response;

/**
 * Created by yashjain on 7/7/17.
 */

public class ResponseTable implements DbConstants{

    private SQLiteDatabase db;

    public ResponseTable(){
    }

    public ResponseTable(SQLiteDatabase db){
        this.db=db;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public long addResponse(Response response){
        ResponderInfo responderInfo=response.getResponderInfo();
        ContentValues values=new ContentValues();
        values.put(COL_SID,response.getsId());
        values.put(COL_USERID,response.getUserId());
        values.put(COL_LANGUAGE,response.getLanguage());
        values.put(COL_VERSION,response.getVersion());
        values.put(COL_LATITUDE,response.getLatitude());
        values.put(COL_LONGITUDE,response.getLongitude());
        values.put(COL_ALTITUDE,response.getAltitude());
        values.put(COL_SYNCED,response.getSynced());
        values.put(COL_RESULT,response.getResult().toString());
        values.put(COL_FNAME,responderInfo.getfName());
        values.put(COL_LNAME,responderInfo.getlName());
        values.put(COL_GENDER,responderInfo.getGender());
        values.put(COL_BLOODG,responderInfo.getBloodGroup());
        values.put(COL_WEIGHT,responderInfo.getWeight());
        values.put(COL_HEIGHT,responderInfo.getHeight());
        values.put(COL_ADDRESS,responderInfo.getAddress());
        values.put(COL_AGE,responderInfo.getAge());
        return db.insert(TABLE_RESPONSE, null, values);
    }

    public int removeResponse(String synced){
        String whereClause= COL_SYNCED+" = ? ";
        return db.delete(TABLE_RESPONSE,whereClause,new String[]{synced});
    }

    public int removeResponse(int rId){
        String whereClause= COL_RID+" = ? ";
        return db.delete(TABLE_RESPONSE,whereClause,new String[]{String.valueOf(rId)});
    }

    public int getCount() {
        String query = "SELECT COUNT(" + COL_RID + ") FROM " + TABLE_RESPONSE +
                " WHERE " + COL_RID + " = '" + EXPORTED + "' OR " + COL_RID + " = '" + OFFLINE + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        return cursor.getInt(0);
    }

    public int updateResponse(Response response){
        ResponderInfo responderInfo=response.getResponderInfo();
        ContentValues values=new ContentValues();
        values.put(COL_SID,response.getsId());
        values.put(COL_USERID,response.getUserId());
        values.put(COL_LANGUAGE,response.getLanguage());
        values.put(COL_VERSION,response.getVersion());
        values.put(COL_LATITUDE,response.getLatitude());
        values.put(COL_LONGITUDE,response.getLongitude());
        values.put(COL_ALTITUDE,response.getAltitude());
        values.put(COL_SYNCED,response.getSynced());
        values.put(COL_RESULT,response.getResult().toString());
        values.put(COL_FNAME,responderInfo.getfName());
        values.put(COL_LNAME,responderInfo.getlName());
        values.put(COL_GENDER,responderInfo.getGender());
        values.put(COL_BLOODG,responderInfo.getBloodGroup());
        values.put(COL_WEIGHT,responderInfo.getWeight());
        values.put(COL_HEIGHT,responderInfo.getHeight());
        values.put(COL_ADDRESS,responderInfo.getAddress());
        values.put(COL_AGE,responderInfo.getAge());
        int rId=response.getrId();
        String selection=COL_RID+" = ?";
        return db.update(TABLE_RESPONSE,values,selection,new String[]{String.valueOf(rId)});
    }

    public int updateSync(int rId,String synced){
        ContentValues values=new ContentValues();
        values.put(COL_SYNCED,synced);
        String selection=COL_RID+" = ?";
        return db.update(TABLE_RESPONSE,values,selection,new String[]{String.valueOf(rId)});
    }


    public void updateSync(int[] rId,String synced){
       for(int i=0;i<rId.length;i++){
           updateSync(rId[i],synced);
       }
    }



    public ArrayList<Response> getResponse(String synced){
        String[] projection={COL_RID,COL_SID,COL_USERID,COL_LANGUAGE,COL_VERSION,COL_LATITUDE,COL_LONGITUDE,COL_ALTITUDE,COL_SYNCED,COL_RESULT,COL_FNAME,COL_LNAME,COL_GENDER,COL_BLOODG,COL_WEIGHT,COL_HEIGHT,COL_ADDRESS,COL_AGE};
        String selection=COL_SYNCED+" = ?";
        ArrayList<Response> responseList=new ArrayList<>();
        Log.d("SYNC", synced);
        if (db == null) Log.d("Db is ", "NULL");
        Cursor cursor = db.query(TABLE_RESPONSE, projection, selection, new String[]{synced}, null, null, null);
        if (cursor == null) return responseList;
        Gson gson=new Gson();
        while(cursor.moveToNext()){
            Response response=new Response();
            ResponderInfo responderInfo=new ResponderInfo();
            response.setrId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_RID)));
            response.setsId(cursor.getString(cursor.getColumnIndexOrThrow(COL_SID)));
            response.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(COL_LANGUAGE)));
            response.setUserId(cursor.getString(cursor.getColumnIndexOrThrow(COL_USERID)));
            response.setVersion(cursor.getInt(cursor.getColumnIndexOrThrow(COL_VERSION)));
            response.setLatitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COL_LATITUDE)));
            response.setLongitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COL_LONGITUDE)));
            response.setAltitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COL_ALTITUDE)));
            response.setSynced(cursor.getString(cursor.getColumnIndexOrThrow(COL_SYNCED)));
            response.setResult(cursor.getString(cursor.getColumnIndexOrThrow(COL_RESULT)));
            responderInfo.setfName(cursor.getString(cursor.getColumnIndexOrThrow(COL_FNAME)));
            responderInfo.setlName(cursor.getString(cursor.getColumnIndexOrThrow(COL_LNAME)));
            responderInfo.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COL_GENDER)));
            responderInfo.setWeight(cursor.getFloat(cursor.getColumnIndexOrThrow(COL_WEIGHT)));
            responderInfo.setHeight(cursor.getFloat(cursor.getColumnIndexOrThrow(COL_HEIGHT)));
            responderInfo.setBloodGroup(cursor.getString(cursor.getColumnIndexOrThrow(COL_BLOODG)));
            responderInfo.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS)));
            responderInfo.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(COL_AGE)));
            response.setResponderInfo(responderInfo);
            responseList.add(response);
        }
        return responseList;
    }

}

