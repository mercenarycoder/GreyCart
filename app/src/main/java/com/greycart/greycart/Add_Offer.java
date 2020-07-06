package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Add_Offer extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText enter_offer_code,enter_offer_name,enter_offer_uses,enter_offermax,enter_offer_amount,enter_offertage;
EditText enter_minsellprice,enter_offer_startdate,enter_offer_enddate,enter_offer_description;
EditText enter_offer_starttime,enter_offer_closetime;
LinearLayout add_offer_selector;
ImageView imagepro_addoffer;
Button offer_saver;
ImageButton back_done_offer;
SpinnerAdapter adapter,adapter1;
Bitmap bitmap;
ArrayList<String> type1,fixed1;
String code,name,uses,max_uses,amount,percentage,min_sell,start_date,end_date,description,type_get="Percentage"
        ,fix_get="Upto",start_time,end_time
        ,convert_image;
Spinner type_offer,fixed_type;
ProgressDialog progressDialog;
    Calendar myCalendar;
public void fillSpinnerList()
{
    type1=new ArrayList<>();
    type1.add("Percentage");
    type1.add("Fixed");
    fixed1=new ArrayList<>();
    fixed1.add("Upto");
    fixed1.add("Percentage");
}
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__offer);
       fillSpinnerList();
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        enter_offer_code=(EditText)findViewById(R.id.enter_offer_code);
       enter_offer_amount=(EditText)findViewById(R.id.enter_offer_amount);
       enter_offer_name=(EditText)findViewById(R.id.enter_offer_name);
       enter_offer_uses=(EditText)findViewById(R.id.enter_offer_uses);
       enter_offermax=(EditText)findViewById(R.id.enter_offermax);
       enter_offertage=(EditText)findViewById(R.id.enter_offertage);
       enter_minsellprice=(EditText)findViewById(R.id.enter_minsellprice);
       enter_offer_startdate=(EditText)findViewById(R.id.enter_offer_startdate);
       enter_offer_enddate=(EditText)findViewById(R.id.enter_offer_closedate);
       enter_offer_description=(EditText)findViewById(R.id.enter_offer_description);
       add_offer_selector=(LinearLayout)findViewById(R.id.add_offer_selector);
       back_done_offer=(ImageButton)findViewById(R.id.back_done_offer);
       back_done_offer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
       imagepro_addoffer=(ImageView)findViewById(R.id.imagepro_addoffer);
       offer_saver=(Button)findViewById(R.id.offer_saver);
       enter_offer_starttime=(EditText)findViewById(R.id.enter_offer_starttime);
       enter_offer_closetime=(EditText)findViewById(R.id.enter_offer_closetime);
       enter_offer_starttime.setInputType(InputType.TYPE_NULL);
       enter_offer_closetime.setInputType(InputType.TYPE_NULL);
       enter_offer_starttime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TimePickerDialog tpd = new TimePickerDialog(Add_Offer.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK
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

                       enter_offer_starttime.setText(hour+":"+min);
                   }
               }, 10, 20, DateFormat.is24HourFormat(Add_Offer.this));

               //You can set a simple text title for TimePickerDialog
               //tpd.setTitle("Title Of Time Picker Dialog");

               /*.........Set a custom title for picker........*/
               TextView tvTitle = new TextView(Add_Offer.this);
               tvTitle.setText("Choose Starting time");
               tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
               tvTitle.setPadding(5, 3, 5, 3);
               tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
               tpd.setCustomTitle(tvTitle);
               tpd.show();
           }
       });
    enter_offer_closetime.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerDialog tpd = new TimePickerDialog(Add_Offer.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK
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

                    enter_offer_closetime.setText(hour+":"+min);
                }
            }, 10, 20, DateFormat.is24HourFormat(Add_Offer.this));

            //You can set a simple text title for TimePickerDialog
            //tpd.setTitle("Title Of Time Picker Dialog");

            /*.........Set a custom title for picker........*/
            TextView tvTitle = new TextView(Add_Offer.this);
            tvTitle.setText("Choose Starting time");
            tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
            tvTitle.setPadding(5, 3, 5, 3);
            tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            tpd.setCustomTitle(tvTitle);
            tpd.show();
        }
    });

    //spinners from here
       type_offer=(Spinner)findViewById(R.id.type_offer);
       fixed_type=(Spinner)findViewById(R.id.fixed_type);
       adapter1=new SpinnerAdapter(Add_Offer.this,type1);
       adapter=new SpinnerAdapter(Add_Offer.this,fixed1);
       type_offer.setAdapter(adapter1);
       fixed_type.setAdapter(adapter);
       type_offer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String get_info=adapter1.getItem(position);
               type_get=get_info;
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
       fixed_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String get_info=adapter.getItem(position);
               fix_get=get_info;
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
       enter_offer_startdate.setInputType(InputType.TYPE_NULL);
       enter_offer_enddate.setInputType(InputType.TYPE_NULL);
     myCalendar= Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //updateLabel();
        }
    };
    enter_offer_startdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "MM/dd/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    enter_offer_startdate.setText(sdf.format(myCalendar.getTime()));
                }
            };
            new DatePickerDialog(Add_Offer.this,date,myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    });
    enter_offer_enddate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "MM/dd/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    enter_offer_enddate.setText(sdf.format(myCalendar.getTime()));
                }
            };
            new DatePickerDialog(Add_Offer.this,date1,myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    });
    offer_saver.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
          code=enter_offer_code.getText().toString();
          name=enter_offer_name.getText().toString();
          uses=enter_offer_uses.getText().toString();
          min_sell=enter_minsellprice.getText().toString();
          max_uses=enter_offermax.getText().toString();
          amount=enter_offer_amount.getText().toString();
          percentage=enter_offertage.getText().toString();
          start_date=enter_offer_startdate.getText().toString();
          start_time=enter_offer_starttime.getText().toString();
          start_date=start_date+" "+start_time;
         // Toast.makeText(Add_Offer.this,start_date,Toast.LENGTH_SHORT).show();
          end_date=enter_offer_enddate.getText().toString();
          end_time=enter_offer_closetime.getText().toString();
          end_date=end_date+" "+end_time;
          description=enter_offer_description.getText().toString();
          if(code.isEmpty()||name.isEmpty()||uses.isEmpty()||min_sell.isEmpty()||max_uses.isEmpty()
          ||amount.isEmpty()||percentage.isEmpty()||start_date.isEmpty()||end_date.isEmpty()||
          description.isEmpty())
          {
   Toast.makeText(Add_Offer.this,"Please enter all mandatory details",Toast.LENGTH_SHORT).show();
          }
          else
          {
         new getallsupport().execute();
          }
           }
       });
       add_offer_selector.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
        openFileChooser();
           }
       });
}
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
          Uri imageuri=data.getData();
            try
            {
                Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(Add_Offer.this.getContentResolver(), imageuri);
                //imagepro.setImageBitmap(bitmap);
                bitmap=bitmap2;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                convert_image= Base64.encodeToString(byteArray,Base64.DEFAULT);
                //System.out.println(convertImage);
                byte [] imageAsBytes=Base64.decode(convert_image.getBytes(),Base64.DEFAULT);
                imagepro_addoffer.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0,imageAsBytes.length));
                //imageView_pic.setImageURI(imageuri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public class getallsupport extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Add_Offer.this);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String...strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/listoffercreate";
            SharedPreferences preferences=getApplicationContext().
                    getSharedPreferences("login_details",
                            getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            JsonParser parser=new JsonParser();
            String responce=parser.add_offer(url,code,name,uses,max_uses,amount,percentage,min_sell,description
            ,type_get,fix_get,start_date,end_date,convert_image,staff_id);
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
                    String object1=String.valueOf(object.get("msg"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(Add_Offer.this);
                    builder.setTitle("Update")
                            .setMessage(String.valueOf(object1))
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                    builder.create();
                    builder.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Add_Offer.this,"Something going wrong",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_Offer.this);
                builder.setTitle("Update")
                        .setMessage("No Internet please turn your intrnet on")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //finish();
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    }
}
