package com.greycart.greycart;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Support_fragment extends Fragment {
    TextView email, number;
    ProgressDialog progressDialog;
    Context context;
    ArrayList<Notification_baseclass> list;
    Notification_Adapter adapter;
    RecyclerView notification_recycler;
    ImageButton open_whatsapp, open_facebook;
    public static String FACEBOOK_URL = "https://www.facebook.com/YourPageName";
    public static String FACEBOOK_PAGE_ID = "YourPageName";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support, container, false);
        email = (TextView) view.findViewById(R.id.support_email123);
        number = (TextView) view.findViewById(R.id.support_mobile123);
        open_whatsapp = (ImageButton) view.findViewById(R.id.open_watsapp);
        open_facebook = (ImageButton) view.findViewById(R.id.open_facebook);
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number_dial = number.getText().toString();
                Intent call_intent = new Intent(Intent.ACTION_CALL);
                call_intent.setData(Uri.parse("tel:" + number_dial));
                call_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_GRANTED) {
                       context.startActivity(call_intent);
                        return;
                    }
                    else
                    {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                }
                else
                {
                    context.startActivity(call_intent);
                }
                //context.startActivity(call_intent);
        }
    });
    open_whatsapp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String masg_at="+91"+number.getText().toString();
            String url = "https://api.whatsapp.com/send?phone="+masg_at;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        }
    });
    notification_recycler=(RecyclerView)view.findViewById(R.id.notification_recycler);
    open_facebook.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = "https://www.facebook.com/greycart.co.in/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
            //startActivity(facebookIntent);
        }
    });
    context=this.getActivity();
    new getallsupport().execute();
    return view;
    }
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana",
                    0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent callIntent=new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number.getText().toString()));
                startActivity(callIntent);
            } else {
                Toast.makeText(context, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class getallsupport extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String...strings) {
          String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/getsupport";
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().
                            getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            JsonParser parser=new JsonParser();
            String responce=parser.getSupportInfo(url,staff_id);
            return responce;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            if(s!=null)
            {
                try {
                    JSONObject object = new JSONObject(s);
                    JSONObject object1=object.getJSONObject("data");
                    email.setText(String.valueOf(object1.get("email")));
                    number.setText(String.valueOf(object1.get("mobile_no")));
                    list=new ArrayList<>();
                    JSONArray array=object1.getJSONArray("annoucement");
                    for(int i=0;i<array.length();i++)
                    {
                       JSONObject object2=array.getJSONObject(i);
                       String id=String.valueOf(object2.get("id"));
                       String msg=String.valueOf(object2.get("message"));
                       String created=String.valueOf(object2.get("created_at"));
                       String arr[]=created.split(" ");
                       String date=arr[0];
                       String time=arr[1];
                       list.add(new Notification_baseclass(msg,date,time,id));
                    }
                    adapter=new Notification_Adapter(list,context);
                    notification_recycler.setLayoutManager(new LinearLayoutManager(context));
                    notification_recycler.setHasFixedSize(true);
                    notification_recycler.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Something going wrong",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {

            }
        }
    }
}
