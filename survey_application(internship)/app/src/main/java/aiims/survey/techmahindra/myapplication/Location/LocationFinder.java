package aiims.survey.techmahindra.myapplication.Location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by yashjain on 7/12/17.
 */

public class LocationFinder {


    private static final long LOCATION_UPDATE_INTERVAL = 10000;
    private static final long LOCATION_UPDATE_FASTEST = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static FusedLocationProviderClient mFusedLocationProviderClient;
    private static LocationCallback mLocationCallback;
    private static Activity mActivity;
    private static LocationRequest mLocationRequest;
    private static LocationSettingsRequest mLocationSettingsRequest;
    private static SettingsClient mSettingsClient;
    private static boolean mRequestingLocationUpdates;


    /**
     * This function needs to be called once in the Splash Activity
     */
    public static void init(@NonNull Activity activity, @NonNull LocationCallback locationCallback) {
        mActivity = activity;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mActivity);
        mSettingsClient = LocationServices.getSettingsClient(mActivity);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setFastestInterval(LOCATION_UPDATE_FASTEST);
        mLocationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
        mLocationCallback = locationCallback;
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }


    /**
     * Returns if GPS is enabled iff Init has been called
     */
    public static boolean isGpsEnabled() {
        if (mActivity == null) return false;
        return ((LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Show a Dialog before calling this function that GPS is necessary With an option of "Enable GPS"
     * Call this whenever Location is Desired
     * And Call this in OnResume
     */
    public static void startLocationUpdates() {
        mRequestingLocationUpdates = true;
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(mActivity, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                    }
                })
                .addOnFailureListener(mActivity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(mActivity, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                        }
                    }
                });
    }


    /**
     * Call this in OnPause
     * Or whenever Location is not Required
     */
    public static void stopLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            return;
        }
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRequestingLocationUpdates = false;
                    }
                });
    }


    public void checkLocationEnabled(Activity activity) {
        if (!isGpsEnabled()) {
            activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }
}
