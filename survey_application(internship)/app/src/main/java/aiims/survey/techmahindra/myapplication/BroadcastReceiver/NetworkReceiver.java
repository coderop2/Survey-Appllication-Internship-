package aiims.survey.techmahindra.myapplication.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import aiims.survey.techmahindra.myapplication.NetworkComponents.SurveySync;

public class NetworkReceiver extends BroadcastReceiver {
    //TODO: Check Manifest and use REGISTERRECEIVER for higher android version

    Context applicationContext;

    @Override
    public void onReceive(Context applicationContext, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        this.applicationContext=applicationContext;
        if(isConnected()){
            SurveySync surveySync=new SurveySync(applicationContext.getApplicationContext());
            surveySync.syncResponse(new SurveySync.ConnectionCallback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onFailure() {

                }
            });
            surveySync.syncSurvey(new SurveySync.ConnectionCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure() {

                }
            });
        }

    }


    boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo==null)return false;
        return (netInfo.isConnected());
    }

}
