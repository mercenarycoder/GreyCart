package com.greycart.greycart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LocationPhonePe extends AppCompatActivity {

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    TextView current_location_final;
    Button submit_location;
    String place_name;
    boolean curry = false;
    Context context;
    ArrayList<baseOptions> list;
    private LocationListener listener;
    private LocationManager locationManager;
    String sessiontoken;
    String TAG = "placeautocomplete";
    double latitude, longitude;
    //    cmp_id,latitude,longitude,place_id,country,google_map_url,business_name,
//    adresss,city_name,postalcode
    String place_id = "", country = "India", google_map_url = "", business_name = "", address = "", city_name = "", postalcode = "";
    ProgressDialog progressDialog;
    private static final int MY_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onResume() {
        if (curry) {
            Toast.makeText(context, "Now Tap The Current Location Button Once More", Toast.LENGTH_SHORT).show();
            try {
                giveCurrent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_phone_pe);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = LocationPhonePe.this;
        Intent intent = getIntent();
        business_name = intent.getStringExtra("name");
        //business_name="akabdjd";
        sessiontoken = intent.getStringExtra("id");
        city_name=intent.getStringExtra("city_id");
        //sessiontoken="1088";
        // Toast.makeText(context,business_name+" "+sessiontoken,Toast.LENGTH_SHORT).show();
       // configure_button();
        Places.initialize(getApplicationContext(), "AIzaSyBlXvcfI6NHCBUlLTNox85g7okQUv6lyDw");
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        }

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                Toast.makeText(context, place.getAddress(), Toast.LENGTH_SHORT).show();
                address = place.getAddress();
                LatLng latLng = place.getLatLng();
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                place_id = place.getId();
                postalcode = "4020160";
                new placeAutoComplete().execute();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(context, "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
        current_location_final = (TextView) findViewById(R.id.current_location_final);
        submit_location = (Button) findViewById(R.id.submit_location);
        //submit button click listener
        submit_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LocationPhonePe.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        current_location_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }


        // this code won'textView execute IF permissions are not allowed, because in the line above there is return statement.

//LocationManager locationManager;
boolean GpsStatus;
    public void CheckGpsStatus(){
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus) {
            try {
                giveCurrent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //  textview.setText("GPS Is Enabled");
        } else {
            Toast.makeText(context,"Enable your Location",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LocationPhonePe.this);
            alertDialog.setTitle("GPS is not Enabled!");
            alertDialog.setMessage("Do you want to turn on GPS?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    curry=true;
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
           // textview.setText("GPS Is Disabled");
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    public void giveCurrent() throws IOException {
        LocationTrack   locationTrack = new LocationTrack(LocationPhonePe.this);
        if (locationTrack.canGetLocation()) {
            double longitude2 = locationTrack.getLongitude();
            double latitude2 = locationTrack.getLatitude();
            latitude=latitude2;
            longitude=longitude2;
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if(addresses.size()>0) {
                String address2 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city2 = addresses.get(0).getLocality();
                String state2 = addresses.get(0).getAdminArea();
                String country2 = addresses.get(0).getCountryName();
                String postalCode2 = addresses.get(0).getPostalCode();
                String knownName2 = addresses.get(0).getFeatureName();
                address=address2;
                postalcode=postalCode2;
                //city_name=city2;
            }
            google_map_url="vbjhfjhvf";
            if(!address.equals(""))
            {
                Toast.makeText(LocationPhonePe.this,"Details Fetched",Toast.LENGTH_SHORT).show();
                new placeAutoComplete().execute();
            }
            else
            {
                Toast.makeText(LocationPhonePe.this,"Please Click Me Again",Toast.LENGTH_SHORT).show();
            }
        } else {
            locationTrack.showSettingsAlert();
        }
    }
    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101&&resultCode==RESULT_OK)
        {
            try {
                giveCurrent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode==100)
        {
            try {
                Toast.makeText(context,"Reached here",Toast.LENGTH_SHORT).show();
                SharedPreferences login_pref = context.getApplicationContext().getSharedPreferences("details",
                        context.getApplicationContext().MODE_PRIVATE);
                String ll1 = login_pref.getString("latitude", "23.1232");
                String lg1 = login_pref.getString("longitude", "23.1232");
                System.out.println(ll1 + "------------------------------" + lg1);
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                latitude= Double.parseDouble(ll1);
                longitude=Double.parseDouble(lg1);
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (addresses.size() > 0) {
                    String address2 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city2 = addresses.get(0).getLocality();
                    String state2 = addresses.get(0).getAdminArea();
                    String country2 = addresses.get(0).getCountryName();
                    String postalCode2 = addresses.get(0).getPostalCode();
                    String knownName2 = addresses.get(0).getFeatureName();
                    address = address2;
                    postalcode = postalCode2;
                   // city_name = city2;
                }
                google_map_url = "vbjhfjhvf";
                if (!address.equals("")) {
                    Toast.makeText(LocationPhonePe.this,address, Toast.LENGTH_SHORT).show();
                    new placeAutoComplete().execute();
                }
            }
            catch (Exception e)
            {

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        giveCurrent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new androidx.appcompat.app.AlertDialog.Builder(LocationPhonePe.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private final static int ALL_PERMISSIONS_RESULT = 101;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
String LOG_TAG="logger";
    public class placeAutoComplete extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        @Override
        protected String doInBackground(String... strings) {
        String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/updategoogle";
        String key="AIzaSyCB3P0ulXoXAZNVIK2-Cm7wcb_1zgtCgLs";
//        sessiontoken="863";
//        business_name="chaichai";
        String data=new JsonParser().placeAuto(url,sessiontoken,place_id,String.valueOf(latitude),String.valueOf(longitude)
        ,country,google_map_url,business_name,address,city_name,postalcode);
        return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        progressDialog.dismiss();
        if(s!=null)
        {
            try {
                JSONObject object = new JSONObject(s);
                String status=String.valueOf(object.get("status"));
                if(status.equals("1"))
                {
                    Toast.makeText(context,String.valueOf(object.get("msg")),Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LocationPhonePe.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(context,String.valueOf(object.get("msg")),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        }
    }
}
