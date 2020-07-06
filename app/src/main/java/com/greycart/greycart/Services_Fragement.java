package com.greycart.greycart;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class Services_Fragement extends Fragment {
    Spinner spinner,state_spinner,gst_spinner;
    private ArrayList<SpinnerClass> list;
    private SpinnerAdapter2 category,city,states_adapter;
    private SpinnerAdapter2 service_at;
    private SpinnerAdapter nature;
    ArrayList<String> nature_list;
    CheckBox change_final,change_final_2;
    ArrayList<SpinnerClass> cities,states;
    ArrayList<SpinnerClass> categories;
    private String state;
    EditText company_name,company_address,company_number,company_tandc,company_email;
    EditText bank_number,bank_ifsc,bank_name,pan_code,license_number;
    ImageView imagepro;
    TextView ek_alert_dkho,area_select,write_it_service;
    LinearLayout company_image,hide_n_seek1,hide_n_seek2,hide_me_from,hide_me_from2;
    Button save_services;
    LinearLayout save_location;
    double lats,longs;
    URL url_image;
    boolean check_getsetting=false,change_kiya=false;
    int call_it_2=0;
    Uri imageuri,doc_uri;
    Bitmap img;
    Switch check_on_off;
    Context context;
    String id_city,id_state;
    getSettingClass refrence;
    String servises[],servises2[],name="",address="",number="",tnc="",bus_cat="",ser_at="",on_off="",
            path_document="",convertImage="",email="",paisa_name="",paisa_number="",paisa_ifsc="",paisa_pan=""
            ,gst_type="",gst_in="",city_name="",store_name="";
    boolean services_id[],servises_id2[],boobs=false;
    int int_ids_bus[];
    boolean check_that=false,first_time=true;
    setServices updater;
    private static final int FILE_SELECT_CODE = 0;
    private int PICK_IMAGE_REQUEST=1;
    ProgressDialog progressDialog;
    private String category_select_id;
    HashMap< String,Integer> city_map,state_map,gst_map;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 100;
    public void formnatureSpinner()
    {
        nature_list=new ArrayList<>();
        nature_list.add("NON GST");
        nature_list.add("GST");
        gst_map=new HashMap<>();
        gst_map.put("GST",1);
        gst_map.put("NON GST",0);
        nature=new SpinnerAdapter(context,nature_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //use getSupportinfo of JSonparser class to get the deatils initially
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.services_fragment, container, false);
        context=this.getActivity();
        //method1();
        //from here ids are given
        check_on_off=(Switch)view.findViewById(R.id.check_on_off);
        check_on_off.setChecked(false);
        area_select=(TextView)view.findViewById(R.id.area_select);
        company_name=(EditText)view.findViewById(R.id.company_name);
        write_it_service=(TextView)view.findViewById(R.id.write_it_service);
        company_address=(EditText)view.findViewById(R.id.company_address);
        company_number=(EditText)view.findViewById(R.id.company_number);
        hide_me_from=(LinearLayout)view.findViewById(R.id.hide_me_from);
        hide_me_from2=(LinearLayout)view.findViewById(R.id.hide_me_from2);
        company_tandc=(EditText)view.findViewById(R.id.company_tandc);
        imagepro=(ImageView)view.findViewById(R.id.imagepro);
        change_final=(CheckBox)view.findViewById(R.id.change_final);
        change_final_2=(CheckBox)view.findViewById(R.id.change_final_2);
        ek_alert_dkho=(TextView)view.findViewById(R.id.ek_alert_dkho);
        ek_alert_dkho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Information")
                        .setMessage(R.string.app_enable)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });
        // imagepro2=(TextView)view.findViewById(R.id.imagepro2);
        company_image=(LinearLayout)view.findViewById(R.id.company_image);
        // company_document=(LinearLayout)view.findViewById(R.id.company_document);
        save_services=(Button)view.findViewById(R.id.save_services);
        hide_n_seek1=(LinearLayout)view.findViewById(R.id.hide_n_seek1);
        hide_n_seek2=(LinearLayout)view.findViewById(R.id.hide_n_seek2);
        //these are new elements and are added 20-01-2020
        state_spinner=(Spinner)view.findViewById(R.id.state_spinner);
        company_email=(EditText)view.findViewById(R.id.company_email69);
        bank_name=(EditText)view.findViewById(R.id.bank_name);
        pan_code=(EditText)view.findViewById(R.id.pan_code);
        bank_number=(EditText)view.findViewById(R.id.bank_number);
        bank_ifsc=(EditText)view.findViewById(R.id.bank_ifsc_code);
        formnatureSpinner();
        gst_spinner=(Spinner) view.findViewById(R.id.gst_spinner);
        gst_spinner.setAdapter(nature);

        //till here
        spinner=(Spinner)view.findViewById(R.id.company_spinner);
        //and initialization ends here
        // new fromInformation2().execute();
        new fromInformation().execute();
        new fromInformation3().execute();
        //new getSetting().execute();
        save_location=(LinearLayout)view.findViewById(R.id.save_location);
        save_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //method1();
                Intent intent=new Intent(context,MapsActivity.class);
                context.startActivity(intent);
                change_kiya=true;
            }
        });
        gst_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String
                gst_type=nature.getItem(position);
                call_it_2++;
                if(gst_type.length()==3&&check_getsetting&&call_it_2>1)
                {
                    final Dialog dialog=new Dialog(context, 0);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.get_gstin);
                    Button submit_gst=(Button)dialog.findViewById(R.id.submit_gst);
                    Button cancel_gst=(Button)dialog.findViewById(R.id.cancel_gst);
                    final EditText dialog_gst=(EditText)dialog.findViewById(R.id.dialog_gst);
                    dialog_gst.setText(gst_in);
                    submit_gst.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(dialog_gst.getText().toString().isEmpty()==false)
                                gst_in=dialog_gst.getText().toString();
                            else
                                gst_spinner.setSelection(0);
                            dialog.dismiss();
                        }
                    });
                    cancel_gst.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gst_spinner.setSelection(0);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                //check_getsetting=true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        company_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        check_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    hide_n_seek2.setEnabled(false);
                    hide_n_seek1.setEnabled(false);
                    hide_n_seek1.setVisibility(View.INVISIBLE);
                    hide_n_seek2.setVisibility(View.INVISIBLE);
                }
                else
                {
                    hide_n_seek2.setEnabled(true);
                    hide_n_seek1.setEnabled(true);
                    hide_n_seek1.setVisibility(View.VISIBLE);
                    hide_n_seek2.setVisibility(View.VISIBLE);
                }
            }
        });

        save_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = company_name.getText().toString();
                address = company_address.getText().toString();
                number = company_number.getText().toString();
                tnc = company_tandc.getText().toString();
                //from here new details are given
                email = company_email.getText().toString();
                paisa_name = bank_name.getText().toString();
                paisa_number = bank_number.getText().toString();
                paisa_ifsc = bank_ifsc.getText().toString();
                paisa_pan = pan_code.getText().toString();

                //and ended here
                for (int i = 0; i < servises_id2.length; i++) {
                    if (servises_id2[i]) {
                        ser_at += String.valueOf(i + 1);
                        if (i + 1 != servises_id2.length) {
                            ser_at += ",";
                        }
                    }
                }
                if(ser_at.endsWith(","))
                {
                    ser_at=ser_at.substring(0,ser_at.length()-1);
                }
                if (check_on_off.isChecked()) {
                    on_off = "1";
                } else {
                    on_off = "0";
                }
                for (int i = 0; i < services_id.length; i++) {
                    if (services_id[i]) {
                        bus_cat += int_ids_bus[i];
                        if (i + 1 != services_id.length) {
                            bus_cat += ",";
                        }
                    }
                }
                if (bus_cat.endsWith(",")) {
                    bus_cat = bus_cat.substring(0, bus_cat.length() - 1);
                }
                  // Toast.makeText(context, ser_at, Toast.LENGTH_SHORT).show();
//             if(on_off.isEmpty()||name.isEmpty()||address.isEmpty()||number.isEmpty()
//                ||email.isEmpty()||tnc.isEmpty()||bus_cat.isEmpty())
//             {
//                 Toast.makeText(context,"Please fill details something missing",Toast.LENGTH_SHORT).show();
//             }

                if (convertImage.equals("") && check_that) {
                    Bitmap bitmap = ((BitmapDrawable) imagepro.getDrawable()).getBitmap();
                    img = bitmap;
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    convertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }
                updater = new setServices();
                updater.execute();

            }
        });
//       company_document.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//               intent.setType("*/*");
//               intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//               try {
//                   startActivityForResult(
//                           Intent.createChooser(intent, "Select a File to Upload"),
//                           FILE_SELECT_CODE);
//               } catch (android.content.ActivityNotFoundException ex) {
//                   // Potentially direct the user to the Market with a Dialog
//                   Toast.makeText(context, "Please install a File Manager.",
//                           Toast.LENGTH_SHORT).show();
//               }
//           }
//       });
        return  view;
    }

    LocationManager locationManager;
    private static final int PERMISSION_ACCESS_COARSE_LOCATION =1 ;
    boolean GpsStatus;
    public void CheckGpsStatus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                        PERMISSION_ACCESS_COARSE_LOCATION);
            }
            else
            {
                try {
                    giveCurrent();
                    save_services.performClick();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            giveCurrent();
        }
    }

    public void method1()
    {
        CheckGpsStatus();
    }
    LocationTrack locationTrack;
    double latitude_main=0.0;
    double longitude_main=0.0;
    public void giveCurrent()
    {
        locationTrack = new LocationTrack(context);
        if (locationTrack.canGetLocation()) {
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();
            latitude_main=latitude;
            longitude_main=longitude;
            if(latitude_main==0.0||latitude_main==0.0) {
                Toast.makeText(context,"Please Tap Me Again", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context,"Location Fetched",Toast.LENGTH_SHORT).show();
            }
        } else {
            locationTrack.showSettingsAlert();
        }
    }
    @Override
    public void onPause() {

        super.onPause();
    }
String postalcode,google_map_url;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            imageuri=data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageuri);
                //imagepro.setImageBitmap(bitmap);
                img=bitmap;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                convertImage= Base64.encodeToString(byteArray,Base64.DEFAULT);
                System.out.println(convertImage);
                byte [] imageAsBytes=Base64.decode(convertImage.getBytes(),Base64.DEFAULT);
                imagepro.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0,imageAsBytes.length));
                //imageView_pic.setImageURI(imageuri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode==FILE_SELECT_CODE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            doc_uri=data.getData();
            try {
                path_document=getPath(context,doc_uri);
                //  imagepro2.setText(path_document);
                //Some more code will be added here
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(requestCode==101&&resultCode==RESULT_OK)
        {

                try {
                    Toast.makeText(context, "Reached here", Toast.LENGTH_SHORT).show();
                    SharedPreferences login_pref = context.getApplicationContext().getSharedPreferences("details",
                            context.getApplicationContext().MODE_PRIVATE);
                    String ll1 = login_pref.getString("latitude", "23.1232");
                    String lg1 = login_pref.getString("longitude", "23.1232");
                    System.out.println(ll1 + "------------------------------" + lg1);
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(context, Locale.getDefault());
                    latitude_main = Double.parseDouble(ll1);
                    longitude_main = Double.parseDouble(lg1);
                    addresses = geocoder.getFromLocation(latitude_main, longitude_main, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
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
                        Toast.makeText(context, address, Toast.LENGTH_SHORT).show();
                        new placeAutoComplete().execute();
                    }
                }
                catch (Exception e)
                {

                }
        }
    }
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection,
                        null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    public class getSetting extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/getsetting";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.getSupportInfo(url,staff_id);

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try {
                    JSONObject object=new JSONObject(s);
                    JSONObject dataObj=object.getJSONObject("data");
                    String cmp_name=String.valueOf(dataObj.get("cmp_name"));
                    company_name.setText(cmp_name);
                    String cmp_address=String.valueOf(dataObj.get("address"));
                    company_address.setText(cmp_address);
                    String cmp_no=String.valueOf(dataObj.get("contact_no"));
                    company_number.setText(cmp_no);
                    String cmp_email=String. valueOf(dataObj.get("email"));
                    company_email.setText(cmp_email);
                    String ecomerce_status=String.valueOf(dataObj.get("ecomerce_status"));
                    if(ecomerce_status.equals("0"))
                    {
                        hide_me_from.setVisibility(View.INVISIBLE);
                        hide_me_from.setEnabled(false);
                        hide_me_from2.setVisibility(View.INVISIBLE);
                        hide_me_from2.setEnabled(false);
                    }
                    else
                    {
                        hide_me_from.setVisibility(View.VISIBLE);
                        hide_me_from.setEnabled(true);
                        hide_me_from2.setVisibility(View.VISIBLE);
                        hide_me_from2.setEnabled(true);
                    }
                    String acc_name=String.valueOf(dataObj.get("account_name"));
                    String acc_no=String.valueOf(dataObj.get("account_number"));
                    String bank_code2=String.valueOf(dataObj.get("bank_ifsc_code"));
                    String panner=String.valueOf(dataObj.get("pan"));
                    String nature_geter=String.valueOf(dataObj.get("nature_of_business"));
                    String gstin2=String.valueOf(dataObj.get("gstin"));
                    gst_in=gstin2;
                    String state_id22=String.valueOf(dataObj.get("state"));
                    gst_type=nature_geter;
                    id_city=String.valueOf(dataObj.get("city_name"));
                    city_name=String.valueOf(dataObj.get("city_name"));
                    if(state_map.containsKey(state_id22))
                    {
                        int state_pos=state_map.get(state_id22);
                        id_state=state_id22;
                        state_spinner.setSelection(state_pos);
                        //               boobs=true;
                        //             new fromInformation2().execute();
                    }
                    else
                    {
                        state_spinner.setSelection(5);
                    }
                    bank_name.setText(acc_name);
                    bank_ifsc.setText(bank_code2);
                    bank_number.setText(acc_no);
                    pan_code.setText(panner);
                    if(nature_geter.equals("GST"))
                    {
                        gst_spinner.setSelection(1);
                    }
                    else
                    {
                        gst_spinner.setSelection(0);
                    }

                    String tnc=String.valueOf(dataObj.get("term_condition"));
                    company_tandc.setText(tnc);
                    String url=String.valueOf(dataObj.get("logo"));
                    //use this url to prove your point
                    // url=""https://wallpapercave.com/wp/H1rDn26.jpg"
                    Picasso.with(context)
                            .load(url)
                            .placeholder(R.drawable.ic_image_pic)
                            .error(R.drawable.ic_image_pic)
                            .into(imagepro, new Callback() {
                                @Override
                                public void onSuccess() {
                                    check_that=true;
                                }

                                @Override
                                public void onError() {
                                    check_that=false;
                                }
                            });
                    String bussniess_cat=String.valueOf(dataObj.get("business_category"));
                    String service_at_cat=String.valueOf(dataObj.get("service_at"));
                    String bussniess_status=String.valueOf(dataObj.get("business_status"));
                    if(bussniess_status.equals("1")&&ecomerce_status.equals("1"))
                    {
                        check_on_off.setChecked(true);
                    }
                    else
                    {
                        check_on_off.setChecked(false);
                    }

                    if(check_on_off.isChecked())
                    {
                        hide_n_seek1.setVisibility(View.VISIBLE);
                        hide_n_seek2.setVisibility(View.VISIBLE);
                        hide_n_seek2.setEnabled(true);
                        hide_n_seek1.setEnabled(true);
                    }
                    else
                    {
                        hide_n_seek1.setVisibility(View.INVISIBLE);
                        hide_n_seek2.setVisibility(View.INVISIBLE);
                        hide_n_seek2.setEnabled(false);
                        hide_n_seek1.setEnabled(false);
                    }
                    //new fromInformation2().execute();

                    String bankname=String.valueOf(dataObj.get("account_name"));
                    bank_name.setText(bankname);
                    String account_number=String.valueOf(dataObj.get("account_number"));
                    bank_number.setText(account_number);
                    String bank_ifsc_code=String.valueOf(dataObj.get("bank_ifsc_code"));
                    bank_ifsc.setText(bank_ifsc_code);
                    state=String.valueOf(dataObj.get("state"));
                    String id=String.valueOf(dataObj.get("cmp_id"));
                    refrence=new getSettingClass(id,cmp_name,city_name,cmp_address,tnc,
                            cmp_no,url,bussniess_status,bussniess_cat,service_at_cat);
                    JSONArray array=dataObj.getJSONArray("we_provide_service_at");

                    servises2=new String[array.length()];
                    servises_id2=new boolean[array.length()];

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject rk=array.getJSONObject(i);
                        String ser=String.valueOf(rk.get("name"));
                        String id_2=String.valueOf(rk.get("id"));
                        servises2[i]=ser;
                        servises_id2[i]=false;
                    }
                    store_name="";
                    String arr[]=service_at_cat.split(",");
                    for(int i=0;i<arr.length;i++)
                    {
                        if(!arr[i].equals("0")&&!arr[i].equals("")&&Integer.parseInt(arr[i])>0) {
                            servises_id2[Integer.parseInt(arr[i]) - 1] = true;
                            store_name+=" "+servises2[i]+" ,";
                            if(arr[i].equals("2"))
                            {
                                change_final.setChecked(true);
                            }
                            if(arr[i].equals("1"))
                            {
                                change_final_2.setChecked(true);
                            }
                        }
                        else
                        {

                        }
                    }
                    change_final.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(change_final.isChecked())
                                servises_id2[1]=true;
                            else
                                servises_id2[1]=false;
                        }
                    });
                    change_final_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(change_final_2.isChecked())
                                servises_id2[0]=true;
                            else
                                servises_id2[0]=false;
                        }
                    });
                    if(store_name.endsWith(","))
                    {
                        store_name=store_name.substring(0,store_name.length()-1);
                    }
                    write_it_service.setText("Service At :");
                    String arr2[]=bussniess_cat.split(",");
                    for(int i=0;i<arr2.length;i++)
                    {
                        for(int j=0;j<int_ids_bus.length;j++)
                        {
                            if(!arr2[i].equals("null")&&!arr2[i].equals("")&&int_ids_bus[j]==Integer.parseInt(arr2[i]))
                            {
                                services_id[j]=true;
                            }
                        }
                    }
                    check_getsetting=true;
                /*
                    hide_n_seek2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder mbuilder = new AlertDialog.Builder(context);
                            mbuilder.setTitle("Selected Type of Services");
                            mbuilder.setMultiChoiceItems(servises2, servises_id2, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                                }
                            });
                            mbuilder.setCancelable(false);
                            mbuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    store_name="";
                                    for(int i=0;i<servises_id2.length;i++)
                                    {
                                        if(servises_id2[i])
                                        {
                                            store_name+=" "+servises2[i]+" ,";
                                        }
                                    }
                                    if(store_name.endsWith(","))
                                    {
                                        store_name=store_name.substring(0,store_name.length()-1);
                                    }
                                    //  Toast.makeText(context,ser_at,Toast.LENGTH_SHORT).show();
                                    write_it_service.setText("Service At :"+store_name);
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = mbuilder.create();
                            alertDialog.show();
                        }
                    });

                 */
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
            else
            {
                Toast.makeText(context,"No internet connection",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class placeAutoComplete extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/updategoogle";
            String key="AIzaSyCB3P0ulXoXAZNVIK2-Cm7wcb_1zgtCgLs";
//        sessiontoken="863";
//        business_name="chaichai";
            System.out.println("Reaching here -0----------------------------------------------------------");
            String data=new JsonParser().placeAuto(url,refrence.getCmp_id(),"",String.valueOf(latitude_main),String.valueOf(longitude_main)
                    ,"India",google_map_url,name,address,city_name,postalcode);
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
    private class fromInformation extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            try
            {
                URL url2 = new URL("https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/business_category");
                httpURLConnection=(HttpURLConnection)url2.openConnection();
                httpURLConnection.setRequestMethod("GET");
                String current="";
                InputStream ir=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(ir);
                int data = inputStreamReader.read();
                while (data != -1) {
                    current += (char) data;
                    data = inputStreamReader.read();
                    //      System.out.print(current);
                }
                JSONObject object=new JSONObject(current);
                JSONArray jsonArray=object.getJSONArray("data");
                servises=new String[jsonArray.length()];
                int_ids_bus=new int[jsonArray.length()];
                services_id=new boolean[jsonArray.length()];
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    //this is new generation
                    servises[i]=String.valueOf(object1.get("name"));
                    services_id[i]=false;
                    int_ids_bus[i]=Integer.parseInt(String.valueOf(object1.get("id")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(httpURLConnection!=null)
                {
                    httpURLConnection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            hide_n_seek1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mbuilder = new AlertDialog.Builder(context);
                    mbuilder.setTitle("Select Categories");
                    mbuilder.setMultiChoiceItems(servises,services_id, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        }
                    });
                    mbuilder.setCancelable(false);
                    mbuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                          /*
                            bus_cat="";
                            for(int i=0;i<services_id.length;i++)
                            {
                                if(services_id[i])
                                {
                                    bus_cat+=String.valueOf(int_ids_bus[i])+",";
                                }
                            }
                            if(bus_cat.endsWith(","))
                            {
                                bus_cat=bus_cat.substring(0,bus_cat.length()-1);
                            }
                          //  Toast.makeText(context, bus_cat, Toast.LENGTH_SHORT).show();

                           */
                        }
                    });
                    AlertDialog alertDialog = mbuilder.create();
                    alertDialog.show();
                }
            });
            super.onPostExecute(s);
        }
    }
    private class fromInformation2 extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {

            try {
                String url = "https://express.accountantlalaji.com/newapp/onlinelalaji/greycartvendor/getcitybystate";
                String data = new JsonParser().getcityState(url, id_state);
                JSONObject object = new JSONObject(data);
                categories = new ArrayList<>();
                city_map=new HashMap<>();
                JSONArray jsonArray = object.getJSONArray("data");
                // categories.add(new SpinnerClass("krishna","City"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    categories.add(new SpinnerClass(String.valueOf(object1.get("id")),
                            String.valueOf(object1.get("name"))));
                    city_map.put(String.valueOf(object1.get("id")),i);
                }
            }
            catch (Exception e)
            {

            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //spn_city=(Spinner)findViewById(R.id.sign_up_city);
            //city=new SpinnerAdapter2(signup_Activity.this,cities);
            //spn_city.setAdapter(city);
            //spinner=(Spinner)findViewById(R.id.sign_up_category2);
            category=new SpinnerAdapter2(context,categories);
            spinner.setAdapter(category);
            spinner.setPrompt("City");
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SpinnerClass spinnerClass=categories.get(position);
                    id_city=spinnerClass.getId();
                    //Toast.makeText(context,id_city,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            if(first_time)
            {
                if(!city_map.isEmpty()&&city_map.containsKey(city_name))
                {
                    spinner.setSelection(city_map.get(city_name));
                    id_city= String.valueOf(city_map.get(city_name));
                    // new getAreas().execute();
                    first_time=false;
                }
            }
        }
    }
    String city_area[];
    String area_id[];
    boolean selected_ares[];
    private class getAreas extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/listofarea";

            String data=new JsonParser().loadAreas(id_city,url);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null) {
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray array = object.getJSONArray("data");
                    city_area=new String[array.length()];
                    area_id=new String[array.length()];
                    selected_ares=new boolean[array.length()];
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        city_area[i]=String.valueOf(obj.get("name"));
                        area_id[i]=String.valueOf(obj.get("id"));
                        selected_ares[i]=false;
                    }
                    area_select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder mbuilder = new AlertDialog.Builder(context);
                            mbuilder.setTitle("Select Areas");
                            mbuilder.setMultiChoiceItems(city_area,selected_ares, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                                }
                            });
                            mbuilder.setCancelable(false);
                            mbuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = mbuilder.create();
                            alertDialog.show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(s);
        }
    }
    private class fromInformation3 extends AsyncTask<String,String,String>
    {

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            try
            {
                URL url2 = new URL("https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/state");
                httpURLConnection=(HttpURLConnection)url2.openConnection();
                httpURLConnection.setRequestMethod("GET");
                String current="";
                InputStream ir=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(ir);
                int data = inputStreamReader.read();
                while (data != -1) {
                    current += (char) data;
                    data = inputStreamReader.read();
                   // System.out.print(current);
                }
                JSONObject object=new JSONObject(current);
                states=new ArrayList<>();
                JSONArray jsonArray=object.getJSONArray("data");
                //categories.add(new SpinnerClass("krishna","City"));
                state_map=new HashMap<>();
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    states.add(new SpinnerClass(String.valueOf(object1.get("id")),
                            String.valueOf(object1.get("name"))));
                    state_map.put(String.valueOf(object1.get("id")),i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(httpURLConnection!=null)
                {
                    httpURLConnection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //spn_city=(Spinner)findViewById(R.id.sign_up_city);
            //city=new SpinnerAdapter2(signup_Activity.this,cities);
            //spn_city.setAdapter(city);
            states_adapter=new SpinnerAdapter2(context,states);
            state_spinner.setAdapter(states_adapter);
            // spn_category.setPrompt("City");
            state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SpinnerClass spinnerClass=states.get(position);
                    id_state=spinnerClass.getId();
                   // Toast.makeText(context,id_state,Toast.LENGTH_SHORT).show();
                    if(!boobs) {
                        //new fromInformation2().execute();
                        new getSetting().execute();
                    }
                    else{
                        new fromInformation2().execute();
                    }
                    boobs=true;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            super.onPostExecute(s);
        }
    }
    public class setServices extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }
        String ll1,lg1;
        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/getsettingupdate";
            String areas="all places";
//        for(int i=0;i<selected_ares.length;i++)
//        {
//            if(selected_ares[i])
//                areas+=area_id[i]+",";
//        }
//        if(areas.endsWith(","))
//        {
//            areas=areas.substring(0,areas.length()-1);
//        }
            if(change_kiya)
            {
                SharedPreferences login_pref=context.getApplicationContext().getSharedPreferences("details",
                        context.getApplicationContext().MODE_PRIVATE);
                ll1=login_pref.getString("latitude","23.1232");
                lg1=login_pref.getString("longitude","23.1232");
                System.out.println(ll1+"------------------------------"+lg1);
                //Toast.makeText(context,ll1+"---------"+lg1,Toast.LENGTH_SHORT).show();
            }
            else
            {
                ll1="empty";
                lg1="empty";
            }
            String data=new JsonParser().updateSettings(url,refrence.getCmp_id(),name,id_city,email,gst_in,gst_type,paisa_pan
                    ,id_state,paisa_name,paisa_number,paisa_ifsc,address,
                    number,tnc,bus_cat,on_off,ser_at,convertImage,path_document,ll1,
                    lg1,areas);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try {
                    JSONObject object=new JSONObject(s);
                    String object1=String.valueOf(object.get("msg"));
                    // Toast.makeText(context,object1,Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage(object1)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Dashboard_Fragment()).commit();
                                   TextView menu=(TextView)getActivity().findViewById(R.id.menu);
                                    menu.setText("Dashboard");
                                    new Dashboard().count=0;

                                }
                            });
                    builder.create();
                    builder.show();
                    bus_cat="";
                    ser_at="";
                    on_off="";

                    //make a default image and make it paste here
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e);
                    Toast.makeText(context,"Some exception",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(context,"Services are not updated",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
