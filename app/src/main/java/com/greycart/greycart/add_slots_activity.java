package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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


public class add_slots_activity extends AppCompatActivity {
// addslots has been made in the JsonParser class to add a slot
ImageButton back_done_right;
Spinner days_select_here,statys_select2;
SpinnerAdapter2 adapter2,adapter3;
EditText start_time_dalo,end_time_dalo;
EditText max_apps_in_slot;
Button save_slots_final;
//TextView days_selected_slots;
ArrayList<SpinnerClass> list,list2;
String arr[];
int select[];
String state_id,day_id;
String slot_id;
ProgressDialog progressDialog;
updateSlotsto slotsto;
int j=0;
    private String max_appointment;
    private String start_time_getter;
    private String end_time_getter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_slots_activity);
        list=new ArrayList<>();
        list2=new ArrayList<>();
        arr=new String[7];
        select=new int[7];
        for(int i=0;i<7;i++)
        {
            select[i]=0;
        }
        arr[0]="Sunday";
        arr[1]="Monday";
        arr[2]="Tuesday";
        arr[3]="Wednesday";
        arr[4]="Thursday";arr[5]="Friday";arr[6]="Saturday";
        formList();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        back_done_right=(ImageButton)findViewById(R.id.back_done_right);
        back_done_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        start_time_dalo=(EditText)findViewById(R.id.start_time_dalo);
        end_time_dalo=(EditText)findViewById(R.id.end_time_dalo);
        max_apps_in_slot=(EditText)findViewById(R.id.max_apps_in_slot);
        save_slots_final=(Button)findViewById(R.id.save_slots_final);
       // days_selected_slots=(TextView)findViewById(R.id.days_selected_slots);
        days_select_here=(Spinner)findViewById(R.id.days_select_here);
        adapter2=new SpinnerAdapter2(add_slots_activity.this,list);
        days_select_here.setAdapter(adapter2);
        statys_select2=(Spinner)findViewById(R.id.spinner_state2);
        adapter3=new SpinnerAdapter2(add_slots_activity.this,list2);
        statys_select2.setAdapter(adapter3);
        //set to what it was fromhere
         Intent intent=getIntent();
         slot_id=intent.getStringExtra("slot_id");
         String startTime=intent.getStringExtra("start_time");
         if(startTime.endsWith("pm"))
         {
             startTime=startTime.substring(0,startTime.length()-3);
             String arr[]=startTime.split(":");
             int hour=Integer.parseInt(arr[0]);
             if(hour!=12)
             {
                 hour+=12;
             }
             arr[0]=String.valueOf(hour);
             startTime=arr[0]+":"+arr[1];
         }
         if(startTime.endsWith("am"))
         {
             startTime=startTime.substring(0,startTime.length()-3);
         }
         start_time_dalo.setText(startTime);
         String endTime=intent.getStringExtra("end_time");
        if(endTime.endsWith("pm"))
        {
            endTime=endTime.substring(0,endTime.length()-3);
            String arr[]=endTime.split(":");
            int hour=Integer.parseInt(arr[0]);
            if(hour!=12)
            {
                hour+=12;
            }
            arr[0]=String.valueOf(hour);
            endTime=arr[0]+":"+arr[1];
        }
        if(endTime.endsWith("am"))
        {
            endTime=endTime.substring(0,endTime.length()-3);
        }
         end_time_dalo.setText(endTime);
         max_apps_in_slot.setText(intent.getStringExtra("max_miller"));
         String rk=intent.getStringExtra("status");
         if(intent.getStringExtra("status").equals("Active")) {
             statys_select2.setSelection(1);
     //Toast.makeText(add_slots_activity.this,rk,Toast.LENGTH_SHORT).show();
         }
         else
         {
             statys_select2.setSelection(0);
         }
         if(intent.getStringExtra("day_num").length()>=1)
         days_select_here.setSelection(Integer.parseInt(intent.getStringExtra("day_num")));
        //till here
        statys_select2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              SpinnerClass spinnerClass=adapter3.getItem(position);
              state_id=spinnerClass.getId();
    //Toast.makeText(add_slots_activity.this,String.valueOf(state_id),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        days_select_here.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              SpinnerClass spinnerClass=adapter2.getItem(position);
              day_id=spinnerClass.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        start_time_dalo.setInputType(InputType.TYPE_NULL);
        start_time_dalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                TimePickerDialog tpd = new TimePickerDialog(add_slots_activity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK
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
                        start_time_dalo.setText(hour+":"+min);
                    }
                }, 10, 20, DateFormat.is24HourFormat(add_slots_activity.this));

                //You can set a simple text title for TimePickerDialog
                //tpd.setTitle("Title Of Time Picker Dialog");

                /*.........Set a custom title for picker........*/
                TextView tvTitle = new TextView(add_slots_activity.this);
                tvTitle.setText("Choose Starting time");
                tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
                tvTitle.setPadding(5, 3, 5, 3);
                tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
                tpd.setCustomTitle(tvTitle);
                tpd.show();
            }
        });
        end_time_dalo.setInputType(InputType.TYPE_NULL);
        end_time_dalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(add_slots_activity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK
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
                        end_time_dalo.setText(hour+":"+min);
                    }
                }, 10, 20, DateFormat.is24HourFormat(add_slots_activity.this));

                //You can set a simple text title for TimePickerDialog
                //tpd.setTitle("Title Of Time Picker Dialog");

                /*.........Set a custom title for picker........*/
                TextView tvTitle = new TextView(add_slots_activity.this);
                tvTitle.setText("Choose Starting time");
                tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
                tvTitle.setPadding(5, 3, 5, 3);
                tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
                tpd.setCustomTitle(tvTitle);
                tpd.show();

            }
        });
        save_slots_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               max_appointment=max_apps_in_slot.getText().toString();
               start_time_getter = start_time_dalo.getText().toString();
               end_time_getter=end_time_dalo.getText().toString();
              if(!max_appointment.isEmpty()&&!start_time_getter.isEmpty()&&!end_time_getter.isEmpty())
              {
                 String hour1[]=start_time_getter.split(":");
                 String hour2[]=end_time_getter.split(":");
                 if(Integer.parseInt(hour1[0])<Integer.parseInt(hour2[0])) {
                     slotsto = new updateSlotsto();
                     slotsto.execute();
                 }
                 else if(Integer.parseInt(hour1[0])==Integer.parseInt(hour2[0]))
                  {
                      if(Integer.parseInt(hour1[1])<Integer.parseInt(hour2[1]))
                          new updateSlotsto().execute();
                  }
                 else
                 {
   Toast.makeText(add_slots_activity.this,"End time should be greater",Toast.LENGTH_SHORT).show();
                 }
              }
              else
              {
   Toast.makeText(add_slots_activity.this,"Please filll all the details",Toast.LENGTH_SHORT).show();
              }
            }
        });
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    public void formList()
    {
        list.add(new SpinnerClass("0","Sunday"));
        list.add(new SpinnerClass("1","Monday"));
        list.add(new SpinnerClass("2","Tuesday"));
        list.add(new SpinnerClass("3","Wednesday"));
        list.add(new SpinnerClass("4","Thursday"));
        list.add(new SpinnerClass("5","Friday"));
        list.add(new SpinnerClass("6","Saturday"));
        list2.add(new SpinnerClass("0","Deactive"));
        list2.add(new SpinnerClass("1","Active"));
    }
    public  class  updateSlotsto extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(add_slots_activity.this);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/slotupdate";
            String data=new JsonParser().updateSlots(url,slot_id,day_id,start_time_getter,
                    end_time_getter,state_id,max_appointment);
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
                String msg=String.valueOf(object.get("msg"));
                AlertDialog.Builder builder = new AlertDialog.Builder(add_slots_activity.this);
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
                start_time_dalo.setText("");
                end_time_dalo.setText("");
                max_apps_in_slot.setText("");
                statys_select2.setSelection(1);
            } catch (JSONException e) {
                e.printStackTrace();
                AlertDialog.Builder builder = new AlertDialog.Builder(add_slots_activity.this);
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
 Toast.makeText(add_slots_activity.this,"No Internet plz try again later",Toast.LENGTH_SHORT).show();
        }
        }
    }
}
