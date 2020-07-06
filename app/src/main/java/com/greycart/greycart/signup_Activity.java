package com.greycart.greycart;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class signup_Activity extends AppCompatActivity {
private Spinner spn_category;
private SpinnerAdapter2 category;
//ArrayList<SpinnerClass> cities;
ArrayList<SpinnerClass> categories;
EditText name,email,phone;
HashMap<String,String> cate_multi;
String categories2[];
boolean ids[];
int ids2[];
String name_get,email_get,phone_get;
private TextView go_back;
private Button sign_up;
String ids_category=null,name_category=null;
String ids_city=null,name_city=null;
TextView version_name2,sign_up_city;
RecyclerView set_categories;
dashboardmainadapter3 adapter3;
Context context;
ArrayList<medicineBaseClass2> list;
String city_select_id,category_select_id;
ProgressDialog dialog;
    private SpinnerAdapter2 states_adapter;
    ArrayList<SpinnerClass> states;
    HashMap< String,Integer> state_map;
    Spinner state_spinner;
    String id_state;
    boolean boobs=false;
    //Button
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
        state_spinner=(Spinner)findViewById(R.id.sign_up_states);
        new fromInformation3().execute();
        new fromInformation().execute();
        context=signup_Activity.this;
       // new fromInformation2().execute();
        list=new ArrayList<>();
        adapter3=new dashboardmainadapter3(list,context);
        set_categories=(RecyclerView)findViewById(R.id.set_categories);
        set_categories.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        set_categories.setHasFixedSize(true);
        set_categories.setAdapter(adapter3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        name=(EditText)findViewById(R.id.sign_up_name);
        email=(EditText)findViewById(R.id.sign_up_email);
        phone=(EditText)findViewById(R.id.sign_up_number);
        sign_up=(Button) findViewById(R.id.sign_up_button);
        go_back=(TextView) findViewById(R.id.sign_up_go_back);
        version_name2=(TextView)findViewById(R.id.version_name2);
        try {
            PackageInfo info= signup_Activity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version_name2.setText("Beta Version "+info.versionName+"\n Powered By : AccountantLalaji");
            //here link will be thier
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version_name2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://accountantlalaji.com"));
                startActivity(browserIntent);
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        name_get=name.getText().toString();
        email_get=email.getText().toString();
        phone_get=phone.getText().toString();
        String str="";
        for(int i=0;i<ids.length;i++)
        {
            if(ids[i])
            {
                str+=ids2[i]+",";
            }
        }
        if(str.endsWith(","))
        {
            str=str.substring(0,str.length()-1);
        }

        category_select_id=str;
      //System.out.println("Categories are :---------------------------"+category_select_id);
        //Toast.makeText(signup_Activity.this,category_select_id,Toast.LENGTH_SHORT).show();
 Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
 Pattern VALID_PHONE_REGEX = Pattern.compile("\\d{10}", Pattern.CASE_INSENSITIVE);
Matcher matcher_phone = VALID_PHONE_REGEX.matcher(phone_get);
Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email_get);
        if(!matcher.find())
        {
            Toast.makeText(signup_Activity.this,"please enter valid email address",Toast.LENGTH_SHORT).show();
        }
        else if(phone_get.startsWith("+"))
        {
            Toast.makeText(signup_Activity.this,"please do not add country code give plain number",Toast.LENGTH_SHORT).show();
        }
        else if(!matcher_phone.find())
        {
            Toast.makeText(signup_Activity.this,"please enter valid Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(!name_get.isEmpty()&&!email_get.isEmpty()&&!phone_get.isEmpty())
        {
        new AdminLogin().execute();
        }
        else
        {
            Toast.makeText(signup_Activity.this,"please enter data in all fields",Toast.LENGTH_SHORT).show();
        }
            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(signup_Activity.this, MainActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
    private class fromInformation extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
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
                    System.out.print(current);
                }
                JSONObject object=new JSONObject(current);

                JSONArray jsonArray=object.getJSONArray("data");
                categories2=new String[jsonArray.length()];
                ids=new boolean[jsonArray.length()];
                ids2=new int[jsonArray.length()];
                cate_multi=new HashMap<>();
                for(int i=0;i<jsonArray.length();i++)
                {
                 JSONObject object1 = jsonArray.getJSONObject(i);
                 String data2=String.valueOf(object1.get("name"));
                 String id2=String.valueOf(object1.get("id"));
                 categories2[i]=data2;
                 ids[i]=false;
                 ids2[i]=Integer.parseInt(id2);
                 cate_multi.put(data2,id2);
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
            sign_up_city=(TextView)findViewById(R.id.sign_up_city);
            sign_up_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mbuilder = new AlertDialog.Builder(signup_Activity.this);
                    mbuilder.setTitle("Select days");
                    mbuilder.setMultiChoiceItems(categories2, ids, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        }
                    });
                    mbuilder.setCancelable(false);
                    mbuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            list=new ArrayList<>();
                            for(int i=0;i<ids.length;i++)
                            {
                                if(ids[i])
                                {
                                    list.add(new medicineBaseClass2(String.valueOf(ids[i]),categories2[i]));
                                }
                            }
                            adapter3=new dashboardmainadapter3(list,context);
                            set_categories=(RecyclerView)findViewById(R.id.set_categories);
                            set_categories.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                            set_categories.setHasFixedSize(true);
                            set_categories.setAdapter(adapter3);
                        }
                    });
                    AlertDialog alertDialog = mbuilder.create();
                    alertDialog.show();
                }
            });
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
                    System.out.print(current);
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
                    boobs=true;
                    new fromInformation2().execute();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

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
            dialog=new ProgressDialog(signup_Activity.this);
            dialog.setMessage("Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            if(boobs)
            {
               try {
                   String url = "https://express.accountantlalaji.com/newapp/onlinelalaji/greycartvendor/getcitybystate";
                   String data = new JsonParser().getcityState(url, id_state);
                   JSONObject object = new JSONObject(data);
                   categories = new ArrayList<>();
                   JSONArray jsonArray = object.getJSONArray("data");
                   // categories.add(new SpinnerClass("krishna","City"));
                   for (int i = 0; i < jsonArray.length(); i++) {
                       JSONObject object1 = jsonArray.getJSONObject(i);
                       categories.add(new SpinnerClass(String.valueOf(object1.get("id")),
                               String.valueOf(object1.get("name"))));
                   }
               }
               catch (Exception e)
               {

               }
                return null;
            }
            else {
                HttpURLConnection httpURLConnection = null;
                try {
                    URL url2 = new URL("https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/city");
                    httpURLConnection = (HttpURLConnection) url2.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    String current = "";
                    InputStream ir = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(ir);
                    int data = inputStreamReader.read();
                    while (data != -1) {
                        current += (char) data;
                        data = inputStreamReader.read();
                        System.out.print(current);
                    }
                    JSONObject object = new JSONObject(current);
                    categories = new ArrayList<>();
                    JSONArray jsonArray = object.getJSONArray("data");
                    // categories.add(new SpinnerClass("krishna","City"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object1 = jsonArray.getJSONObject(i);
                        categories.add(new SpinnerClass(String.valueOf(object1.get("id")),
                                String.valueOf(object1.get("name"))));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }

                return null;
            }
            }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            //spn_city=(Spinner)findViewById(R.id.sign_up_city);
            //city=new SpinnerAdapter2(signup_Activity.this,cities);
            //spn_city.setAdapter(city);
             spn_category=(Spinner)findViewById(R.id.sign_up_category2);
            category=new SpinnerAdapter2(signup_Activity.this,categories);
            spn_category.setAdapter(category);
            spn_category.setPrompt("City");
            spn_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   SpinnerClass spinnerClass=categories.get(position);
                   city_select_id=spinnerClass.getId();
                 // Toast.makeText(signup_Activity.this,city_select_id,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
    private class AdminLogin extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context;
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            JsonParser jsonParser=new JsonParser();
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/businessSignup";
            String json=jsonParser.signingIn(url,name_get,email_get,phone_get,city_select_id,id_state,category_select_id);
            //        Log.d("return",json);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            //  Log.d("some",result);

            if(null!=result)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(result);

                    final String responce = String.valueOf(jsonObject.get("status"));
                    final String responce2=String.valueOf(jsonObject.get("msg"));
                    JSONObject obj=jsonObject.getJSONObject("data");
                    final String id=String.valueOf(obj.get("cmp_id"));
                    Object value;
                   // JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    //toDetails(jsonObject1);
                    //Toast.makeText(signup_Activity.this,jsonObject1.getString("user_name"),Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(signup_Activity.this);
                    builder.setTitle("Update")
                            .setMessage(responce2)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                      String standard="Your account has been successfully created";
                      if(responce.equals("1")){
                          SharedPreferences login_pref=getApplicationContext().getSharedPreferences("login_details",
                                  getApplicationContext().MODE_PRIVATE);
                          SharedPreferences.Editor editor=login_pref.edit();
                          //Toast.makeText(MainActivity.this,jsonObject1.getString("user_name"), Toast.LENGTH_SHORT).show();
                          editor.putString("user_pass",phone_get);
                          editor.putString("user_id",email_get);
                          editor.putString("user_id2",id);
                          editor.putString("bus_name",name_get);
                          editor.putString("from_where","register");
                          editor.apply();
                          editor.commit();

                          Intent intent=new Intent(signup_Activity.this, LocationPhonePe.class);
                          intent.putExtra("name",name_get);
                          intent.putExtra("id",id);
                          intent.putExtra("city_id",city_select_id);
                      startActivity(intent);
                      finish();
                      }
                      else
                      {

                      }
                                }
                            });
                    builder.create();
                    builder.show();

                }
                catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        final String responce2=String.valueOf(jsonObject.get("msg"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(signup_Activity.this);
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                    catch (Exception r)
                    {

                    }
                }
            }
            //Toast.makeText(signup_Activity.this, "something missing", Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(signup_Activity.this,"please check details and try again",Toast.LENGTH_SHORT).show();
            }
        }
        public void toDetails(JSONObject jsonObject1) throws JSONException {
            SharedPreferences login_pref=getApplicationContext().getSharedPreferences("login_details",
                    getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor=login_pref.edit();
            //Toast.makeText(signup_Activity.this,jsonObject1.getString("user_name"), Toast.LENGTH_SHORT).show();
            //editor.putString("user_pass",password_getter);
            editor.putString("user_id",email_get);
            editor.putString("user_id",email.getText().toString());
            //editor.putString("user_pass",password.getText().toString());
            editor.putString("auth_key", String.valueOf(jsonObject1.get("auth_key")));
            editor.putString("user_name", String.valueOf(jsonObject1.get("user_name")));
            editor.putString("user_mobile",String.valueOf(jsonObject1.get("user_mobile")));
            editor.putString("user_state", String.valueOf(jsonObject1.get("user_state")));
            editor.putString("user_address", String.valueOf(jsonObject1.get("user_address")));
            editor.putString("user_role", String.valueOf(jsonObject1.get("user_role")));
            editor.putString("user_city", String.valueOf(jsonObject1.get("user_city")));
            editor.putString("user_id2", String.valueOf(jsonObject1.get("id")));
            editor.putString("user_lang", String.valueOf(jsonObject1.get("user_lang")));
            editor.apply();
        }
    }

}
