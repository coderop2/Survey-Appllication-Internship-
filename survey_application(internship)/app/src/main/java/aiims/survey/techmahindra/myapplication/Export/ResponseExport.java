package aiims.survey.techmahindra.myapplication.Export;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import aiims.survey.techmahindra.myapplication.Database.DbConstants;
import aiims.survey.techmahindra.myapplication.Database.ResponseTable;
import aiims.survey.techmahindra.myapplication.Database.SurveyDbOpenHelper;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Response;

/**
 * Created by yashjain on 7/18/17.
 */

public class ResponseExport {


    public static final String FILE_SURVEY_RESPONSE = "SurveyResponse.json";

    public ResponseExport() {

    }

    public void exportResults(Context context) {
        if (isExternalStorageWritable()) {
            try {
                Log.d("FILE PATH:  ", getFilePath());

                ArrayList<Response> responses = new ArrayList<>();
                ResponseTable responseTable = new SurveyDbOpenHelper(context).getResponseTable();
                responses.addAll(responseTable.getResponse(DbConstants.OFFLINE));

                int rid[] = new int[responses.size()];
                for (int i = 0; i < rid.length; i++) {
                    rid[i] = responses.get(i).getrId();
                }
                responses.addAll(responseTable.getResponse(DbConstants.EXPORTED));
                String fileInfo = new Gson().toJson(responses);
                Log.d("FILE INFO", fileInfo);

                File externalpath = Environment.getExternalStorageDirectory();
                File newfile = new File(externalpath, FILE_SURVEY_RESPONSE);
                if (!newfile.exists()) {
                    newfile.createNewFile();
                }
                FileOutputStream fileoutputstream = new FileOutputStream(newfile);
                BufferedWriter bufferedwrite = new BufferedWriter(
                        new OutputStreamWriter(fileoutputstream));

                //you can write however way you want to write in the file
                bufferedwrite.write(fileInfo);
                bufferedwrite.close();
                fileoutputstream.close();
            } catch (Exception e) {
                Log.d("EXPORT EXCEPTION", e.getMessage());
            }
        }
    }


    public String getFilePath() {
        return Environment.getExternalStorageDirectory().getPath() + "/" + FILE_SURVEY_RESPONSE;
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

}
