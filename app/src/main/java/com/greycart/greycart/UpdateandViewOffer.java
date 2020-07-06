package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateandViewOffer extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText enter_offer_code,enter_offer_name,enter_offer_uses,enter_offermax,enter_offer_amount,enter_offertage;
    EditText enter_minsellprice,enter_offer_startdate,enter_offer_enddate,enter_offer_description
            ,update_offer_starttime,update_offer_closetime;
    LinearLayout add_offer_selector;
    ImageView imagepro_addoffer;
    Button offer_saver;
    ImageButton back_done_offer;
    SpinnerAdapter adapter,adapter1,adapter2;
    Bitmap bitmap,img;
    ArrayList<String> type1,fixed1,status1;
    String code,name,uses,max_uses,amount,percentage,min_sell,start_date,end_date,description,type_get="Percentage"
            ,fix_get="Upto",status_get="Active"
            ,convert_image="",offer_id,start_time,end_time;
    Spinner type_offer,fixed_type,status_type;
    ProgressDialog progressDialog;
    Calendar myCalendar;
    HashMap<String,Integer> map_type,map_fixed,map_status;
    public void fillSpinnerList()
    {
        type1=new ArrayList<>();
        type1.add("Percentage");
        type1.add("Fixed");
        fixed1=new ArrayList<>();
        fixed1.add("Upto");
        fixed1.add("Percentage");
        status1=new ArrayList<>();
        status1.add("Active");
        status1.add("Deactive");
        map_type=new HashMap<>();
        map_type.put("Percentage",0);
        map_type.put("Fixed",1);
        map_fixed=new HashMap<>();
        map_fixed.put("0",0);
        map_fixed.put("1",1);
        map_status=new HashMap<>();
        map_status.put("Active",0);
        map_status.put("Deactive",1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateand_view_offer);
        Intent intent=getIntent();
        offer_id=intent.getStringExtra("offer_id");
        setProgress();
        fillSpinnerList();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        back_done_offer=(ImageButton)findViewById(R.id.back_done_offer2);
        back_done_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        enter_offer_code=(EditText)findViewById(R.id.update_offer_code);
        enter_offer_name=(EditText)findViewById(R.id.update_offer_name);
        enter_offer_uses=(EditText)findViewById(R.id.update_offer_uses);
        enter_offermax=(EditText)findViewById(R.id.update_offer_code_max);
        enter_offer_amount=(EditText)findViewById(R.id.update_offer_amount);
        enter_offertage=(EditText)findViewById(R.id.update_offer_percentage);
        enter_minsellprice=(EditText)findViewById(R.id.update_offer_min_sellprice);
        enter_offer_startdate=(EditText)findViewById(R.id.update_offer_startdate);
        enter_offer_enddate=(EditText)findViewById(R.id.update_offer_closedate);
        update_offer_starttime=(EditText)findViewById(R.id.update_offer_starttime);
        update_offer_closetime=(EditText)findViewById(R.id.update_offer_closetime);
        enter_offer_description=(EditText)findViewById(R.id.update_offer_description);
        type_offer=(Spinner)findViewById(R.id.type_offer2);
        fixed_type=(Spinner)findViewById(R.id.fixed_type2);
        status_type=(Spinner)findViewById(R.id.status_type2);
        adapter2=new SpinnerAdapter(UpdateandViewOffer.this,status1);
        adapter1=new SpinnerAdapter(UpdateandViewOffer.this,type1);
        adapter=new SpinnerAdapter(UpdateandViewOffer.this,fixed1);
        type_offer.setAdapter(adapter1);
        fixed_type.setAdapter(adapter);
        status_type.setAdapter(adapter2);
        status_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String get_info=adapter2.getItem(position);
                status_get=get_info;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
               // fix_get=get_info;
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
                new DatePickerDialog(UpdateandViewOffer.this,date,myCalendar.get(Calendar.YEAR),
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
                new DatePickerDialog(UpdateandViewOffer.this,date1,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        add_offer_selector=(LinearLayout)findViewById(R.id.add_offer_selector2);
        add_offer_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        imagepro_addoffer=(ImageView)findViewById(R.id.imagepro_addoffer2);
      offer_saver=(Button)findViewById(R.id.offer_saver2);
      offer_saver.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(convert_image.equals(""))
              {
                try {
                    Bitmap bitmap = ((BitmapDrawable) imagepro_addoffer.getDrawable()).getBitmap();
                    img = bitmap;
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    convert_image = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }
                catch (Exception e)
                {
                    convert_image="noimage";
                }
              }
              code=enter_offer_code.getText().toString();
              name=enter_offer_name.getText().toString();
              uses=enter_offer_uses.getText().toString();
              min_sell=enter_minsellprice.getText().toString();
              max_uses=enter_offermax.getText().toString();
              amount=enter_offer_amount.getText().toString();
              percentage=enter_offertage.getText().toString();
              start_date=enter_offer_startdate.getText().toString();
              // Toast.makeText(Add_Offer.this,start_date,Toast.LENGTH_SHORT).show();
              end_date=enter_offer_enddate.getText().toString();
              description=enter_offer_description.getText().toString();
              if(code.isEmpty()||name.isEmpty()||uses.isEmpty()||min_sell.isEmpty()||max_uses.isEmpty()
                      ||amount.isEmpty()||percentage.isEmpty()||start_date.isEmpty()||end_date.isEmpty()||
                      description.isEmpty())
              {
                  Toast.makeText(UpdateandViewOffer.this,"Please enter all mandatory details",Toast.LENGTH_SHORT).show();
              }
              else
              {
                 new updateOffer().execute();
              }

          }
      });
      update_offer_starttime.setInputType(InputType.TYPE_NULL);
      update_offer_closetime.setInputType(InputType.TYPE_NULL);
        update_offer_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(UpdateandViewOffer.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK
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

                        update_offer_starttime.setText(hour+":"+min);
                    }
                }, 10, 20, DateFormat.is24HourFormat(UpdateandViewOffer.this));

                //You can set a simple text title for TimePickerDialog
                //tpd.setTitle("Title Of Time Picker Dialog");

                /*.........Set a custom title for picker........*/
                TextView tvTitle = new TextView(UpdateandViewOffer.this);
                tvTitle.setText("Choose Starting time");
                tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
                tvTitle.setPadding(5, 3, 5, 3);
                tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
                tpd.setCustomTitle(tvTitle);
                tpd.show();
            }
        });
        update_offer_closetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(UpdateandViewOffer.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK
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

                        update_offer_closetime.setText(hour+":"+min);
                    }
                }, 10, 20, DateFormat.is24HourFormat(UpdateandViewOffer.this));

                //You can set a simple text title for TimePickerDialog
                //tpd.setTitle("Title Of Time Picker Dialog");

                /*.........Set a custom title for picker........*/
                TextView tvTitle = new TextView(UpdateandViewOffer.this);
                tvTitle.setText("Choose Starting time");
                tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
                tvTitle.setPadding(5, 3, 5, 3);
                tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
                tpd.setCustomTitle(tvTitle);
                tpd.show();
            }
        });

        new getOfferView().execute();
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
                Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(UpdateandViewOffer.this.getContentResolver(), imageuri);
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
    public void setProgress()
    {
        progressDialog=new ProgressDialog(UpdateandViewOffer.this);
        progressDialog.setMessage("Data Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    public class updateOffer extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/listofferupdate";
            String responce=new JsonParser().updateOffer(url,code,name,uses,max_uses,amount,
                    percentage,min_sell,description
                    ,type_get,fix_get,start_date,end_date,convert_image,status_get,offer_id);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateandViewOffer.this);
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
                }
                catch (Exception e)
                {

                }
            }
            else
            {

            }
        }
    }

    public class getOfferView extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/listofferview";
            String responce=new JsonParser().viewOffer(url,offer_id);
            return responce;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try {
                    JSONObject object=new JSONObject(s);
                    JSONObject object1=object.getJSONArray("data").getJSONObject(0);
                    String name_js=String.valueOf(object1.get("offer_name"));
                    String code_js=String .valueOf(object1.get("offer_code"));
                    String uses_js=String.valueOf(object1.get("offer_uses"));
                    String max_js=String.valueOf(object1.get("offer_max_uses_user"));
                    String discount_js=String.valueOf(object1.get("offer_discount_amount"));
                    String start_js=String.valueOf(object1.get("offer_start_date"));
                    String arr[]=start_js.split(" ");
                    String end_js=String.valueOf(object1.get("offer_close_date"));
                    String arr2[]=end_js.split(" ");
                    String description_js=String.valueOf(object1.get("offer_description"));
                    String status_js=String.valueOf(object1.get("offer_status"));
                    String img_js=String.valueOf(object1.get("offer_img"));
                    String mis_sell=String.valueOf(object1.get("minimumsellingprice"));
                    //Spinner items from here
                    String dis_js=String.valueOf(object1.get("offer_discount_type"));
                    String type_js=String.valueOf(object1.get("offer_type"));
                    String fixed_js=String.valueOf(object1.get("offer_is_fixed"));
                   enter_offer_code.setText(code_js);
                   enter_offer_name.setText(name_js);
                   enter_offer_uses.setText(uses_js);
                   enter_offermax.setText(max_js);
                   enter_offer_description.setText(description_js);
                   enter_offer_startdate.setText(arr[0]);
                   //here convert it to 24 hour format
                    if(arr[1].endsWith("pm"))
                    {
                        arr[1]=arr[1].substring(0,arr[1].length()-3);
                        String arr_time[]=arr[1].split(":");
                        int hour=Integer.parseInt(arr_time[0]);
                        if(hour!=12)
                        {
                            hour+=12;
                        }
                        arr[1]=String.valueOf(hour);
                        arr[1]=arr_time[0]+":"+arr_time[1];
                    }
                    if(arr[1].endsWith("am"))
                    {
                        arr[1]=arr[1].substring(0,arr[1].length()-3);
                    }

                    update_offer_starttime.setText(arr[1]);
                    if(arr2[1].endsWith("pm"))
                    {
                        arr2[1]=arr2[1].substring(0,arr2[1].length()-3);
                        String arr_time[]=arr2[1].split(":");
                        int hour=Integer.parseInt(arr_time[0]);
                        if(hour!=12)
                        {
                            hour+=12;
                        }
                        arr2[1]=String.valueOf(hour);
                        arr2[1]=arr_time[0]+":"+arr_time[1];
                    }
                    if(arr2[1].endsWith("am"))
                    {
                        arr2[1]=arr2[1].substring(0,arr2[1].length()-3);
                    }

                    update_offer_closetime.setText(arr2[1]);

                   enter_minsellprice.setText(mis_sell);
                   enter_offertage.setText(discount_js);
                   enter_offer_amount.setText(discount_js);
                   enter_offer_enddate.setText(arr2[0]);

                    Picasso.with(UpdateandViewOffer.this)
                            .load(img_js)
                            .placeholder(R.drawable.ic_image_pic)
                            .error(R.drawable.ic_image_pic)
                            .into(imagepro_addoffer);
                    int ij=map_status.get(status_js);
                  //this is for status
                    status_get=status_js;
                    status_type.setSelection(ij);
                    int ij2=map_type.get(dis_js);
                 // this is for type
                    type_get=dis_js;
                    type_offer.setSelection(ij2);
                 // this is for fixed
                    fix_get=fixed1.get(Integer.parseInt(fixed_js));
                  //  fixed_type.setSelection(map_fixed.get(Integer.parseInt(fixed_js)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
Toast.makeText(UpdateandViewOffer.this,"Please try again and open it again",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
