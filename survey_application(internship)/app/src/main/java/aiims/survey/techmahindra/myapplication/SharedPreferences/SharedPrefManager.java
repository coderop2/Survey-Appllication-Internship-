package aiims.survey.techmahindra.myapplication.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import aiims.survey.techmahindra.myapplication.R;

/**
 * Created by yashjain on 7/8/17.
 */

public class SharedPrefManager {

    public static final String SHARED_PREF_NAME = "sharedpreferences";
    public static final String KEY_USERNAME = "userid";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_LANGUAGE = "language";
    public static String[] langMap;

    private static SharedPrefManager mInstance;
    private static Context mCtx;


    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public static String getLanguage(Context context) {
        mCtx = context;
        if (langMap == null) {
            langMap = mCtx.getResources().getStringArray(R.array.laLangMap);
        }
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LANGUAGE, langMap[0]);
    }

    public static void setLanguage(Context context, String language) {
        mCtx = context;
        if (langMap == null) {
            langMap = mCtx.getResources().getStringArray(R.array.laLangMap);
        }
        if (isPresent(context, language)) {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_LANGUAGE, language);
            editor.commit();
        }
    }

    public static boolean isPresent(Context context, String language) {
        mCtx = context;
        if (langMap == null) {
            langMap = mCtx.getResources().getStringArray(R.array.laLangMap);
        }
        for (int i = 0; i < langMap.length; i++) {
            if (langMap[i].equals(language))
                return true;
        }
        return false;
    }

    public boolean userLogin(String username){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

}
