package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Real_Addslots extends AppCompatActivity {
TextView days_selected;
EditText start_time_dalo2,end_time_dalo2,max_appointment;
Button save_and_see;
ImageButton back_done_right33;
String days[];
boolean selected[];
ArrayList<String> list,list2;
ProgressDialog progressDialog;
Spinner spinner_state;
RecyclerView days_select;
ArrayList<medicineBaseClass2> list3;
Context context;
dashboardmainadapter3 adapter3;
SpinnerAdapter adapter;
String state_getter,days_get;
String start_time_getter,end_time_getter,max_apps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real__addslots);
        list=new ArrayList<>();
        formlist();
        context=Real_Addslots.this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        days_selected=(TextView)findViewById(R.id.days_of_week);
        start_time_dalo2=(EditText)findViewById(R.id.start_time_dalo2);
        end_time_dalo2=(EditText)findViewById(R.id.end_time_dalo2);
        max_appointment=(EditText)findViewById(R.id.max_miller);
        days_select=(RecyclerView)findViewById(R.id.days_select);
        back_done_right33=(ImageButton)findViewById(R.id.back_done_right33);
        adapter=new SpinnerAdapter(Real_Addslots.this,list2);
        spinner_state=(Spinner)findViewById(R.id.spinner_state2);
        spinner_state.setAdapter(adapter);
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           state_getter=adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        back_done_right33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save_and_see=(Button)findViewById(R.id.save_it_and2);
        dataLoader();
        save_and_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          days_get="";
          for(int i=0;i<selected.length;i++)
          {
              if(selected[i])
              {
                  days_get+=i+",";
              }
          }
          if(days_get.endsWith(","))
          {
              days_get=days_get.substring(0,days_get.length()-1);
          }
          start_time_getter=start_time_dalo2.getText().toString();
          end_time_getter=end_time_dalo2.getText().toString();
          max_apps=max_appointment.getText().toString();
          if(start_time_getter.isEmpty()||end_time_getter.isEmpty()||max_apps.isEmpty())
          {
              Toast.makeText(Real_Addslots.this,
                      "please enter some details missing",Toast.LENGTH_SHORT).show();
          }
          else
          {
              String hour1[]=start_time_getter.split(":");
              String hour2[]=end_time_getter.split(":");
              if(Integer.parseInt(hour1[0])<Integer.parseInt(hour2[0]))
              new slotsAdder().execute();
              if(Integer.parseInt(hour1[0])==Integer.parseInt(hour2[0]))
              {
                  if(Integer.parseInt(hour1[1])<Integer.parseInt(hour2[1]))
                      new slotsAdder().execute();
              }
              else
     Toast.makeText(Real_Addslots.this,"End Time should be Greater",Toast.LENGTH_SHORT).show();

          }
            }
        });
        days_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(Real_Addslots.this);
                mbuilder.setTitle("Select days");
                mbuilder.setMultiChoiceItems(days, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                });
                mbuilder.setCancelable(false);
                final Context finalContext = Real_Addslots.this;
                mbuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                        list3=new ArrayList<>();
                        for(int i=0;i<selected.length;i++)
                        {
                            if(selected[i])
                            {
                                list3.add(new medicineBaseClass2(String.valueOf(selected[i]),days[i]));
                            }
                        }
                        adapter3=new dashboardmainadapter3(list3, finalContext);
                        days_select=(RecyclerView)findViewById(R.id.days_select);
                        days_select.setLayoutManager(new LinearLayoutManager(finalContext,LinearLayoutManager.HORIZONTAL,false));
                        days_select.setHasFixedSize(true);
                        days_select.setAdapter(adapter3);
                    }
                });
                AlertDialog alertDialog = mbuilder.create();
                alertDialog.show();
            }
        });
        start_time_dalo2.setInputType(InputType.TYPE_NULL);
        start_time_dalo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(Real_Addslots.this,
                        AlertDialog.THEME_DEVICE_DEFAULT_DARK
                        , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour=String.valueOf(hourOfDay);
                        if(hourOfDay<10)
                        {
                            hour="0"+hour;
                        }
                        String min=String.valueOf(minute);
                        if(minute<10)
                        {
                           min="0"+min;
                        }
                        start_time_dalo2.setText(hour+":"+min);
                    }
                }, 10, 20, DateFormat.is24HourFormat(Real_Addslots.this));

                //You can set a simple text title for TimePickerDialog
                //tpd.setTitle("Title Of Time Picker Dialog");

                /*.........Set a custom title for picker........*/
                TextView tvTitle = new TextView(Real_Addslots.this);
                tvTitle.setText("Choose Starting time");
                tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
                tvTitle.setPadding(5, 3, 5, 3);
                tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
                tpd.setCustomTitle(tvTitle);
                tpd.show();
            }
        });
        end_time_dalo2.setInputType(InputType.TYPE_NULL);
        end_time_dalo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(Real_Addslots.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK
                        , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour=String.valueOf(hourOfDay);
                        if(hourOfDay<10)
                        {
                            hour="0"+hour;
                        }
                        String min=String.valueOf(minute);
                        if(minute<10)
                        {
                            min="0"+min;
                        }
                        end_time_dalo2.setText(hour+":"+min);
                    }
                }, 10, 20, DateFormat.is24HourFormat(Real_Addslots.this));

                //You can set a simple text title for TimePickerDialog
                //tpd.setTitle("Title Of Time Picker Dialog");

                /*.........Set a custom title for picker........*/
                TextView tvTitle = new TextView(Real_Addslots.this);
                tvTitle.setText("Choose Ending time");
                tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
                tvTitle.setPadding(5, 3, 5, 3);
                tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
                tpd.setCustomTitle(tvTitle);
                tpd.show();
            }
        });
    }
    public void formlist()
    {
        list2=new ArrayList<>();
        list2.add("Active");
        list2.add("Deactive");
    }


    public void dataLoader()
    {
        days= new String[7];
        days[0]="Sunday";
        days[1]="Monday";
        days[2]="Tuesday";
        days[3]="Wednesday";days[4]="Thursday";days[5]="Friday";days[6]="Saturday";
        selected=new boolean[7];
        for(int i=0;i<selected.length;i++)
        {
            selected[i]=false;
        }
    }
    public class slotsAdder extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute()
        {
            progressDialog=new ProgressDialog(Real_Addslots.this);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
           String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/slotadd";
            SharedPreferences preferences=getApplicationContext().
                    getSharedPreferences("login_details",getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");

            String data=new JsonParser().addSlots(url,staff_id,days_get,start_time_getter,end_time_getter
            ,max_apps);
            return data;
        }
        @Override
        protected void onPostExecute(String s)
        {
            progressDialog.dismiss();
            super.onPostExecute(s);
            if(s!=null)
            {
                try {
                    JSONObject object=new JSONObject(s);
                    String msg=String.valueOf(object.get("msg"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(Real_Addslots.this);
                    builder.setTitle("Update")
                            .setMessage(msg)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                    builder.create();
                    builder.show();
                    start_time_dalo2.setText("");
                    end_time_dalo2.setText("");
                    max_appointment.setText("");
                    for(int i=0;i<selected.length;i++)
                    {
                        selected[i]=false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Real_Addslots.this);
                    builder.setTitle("Warning")
                            .setMessage("Some exception please try again!!")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create();
                    builder.show();

                }
            }
            else
            {
            Toast.makeText(Real_Addslots.this,"No Internet connection",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
