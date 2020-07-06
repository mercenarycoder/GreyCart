package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class update_Service extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    String cat_id=null;
    ImageView imageView_pic;
    LinearLayout takepic;
    Uri imageuri;
    EditText service_name,service_rate,service_description,service_rate_out2,barcode2;
    String name=null,rate=null,description=null;
    Bitmap img=null;
    Button savencontinue,goback;
    ProgressDialog progressDialog;
    boolean conti=false;
    private SpinnerAdapter2 adapter2;
    ArrayList<SpinnerClass> list;
    Spinner spi_gst2;
    Context context;
    TextView selected_category;
    String cat_name="";
    ImageButton back_done_right99;
    String prod_id,status_pro,hsn_id,unit_id,convertImage="",old_mrp="",pp_final="",barcode_final="";
    boolean check_that=false;

    Spinner hsn_spinner;
    SpinnerAdapter2 adapter3;
    ArrayList<SpinnerClass> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__service);
        context= update_Service.this;

        Intent intent=getIntent();
        prod_id=intent.getStringExtra("prod_id");
        System.out.println("------------------------------------"+prod_id);
        cat_name=intent.getStringExtra("cat_name");
       // Toast.makeText(update_Service.this,String.valueOf(prod_id),Toast.LENGTH_SHORT).show();
        spi_gst2=(Spinner)findViewById(R.id.spi_gst2);
        new gethsn().execute();
        new gethsn2().execute();
        new getting().execute();
        hsn_spinner=(Spinner)findViewById(R.id.hsn_spinner2);
        barcode2=(EditText)findViewById(R.id.barcode_2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        selected_category=(TextView)findViewById(R.id.selected_category2);
        selected_category.setText("Product :"+cat_name);
        imageView_pic=(ImageView)findViewById(R.id.imagepro22);
        takepic=(LinearLayout)findViewById(R.id.service_image22);
        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        service_name=(EditText)findViewById(R.id.service_name2);
        service_rate=(EditText)findViewById(R.id.service_rate2);
        service_rate_out2=(EditText)findViewById(R.id.service_rate_out2);
        //service_rate_out2.setText("0");
        service_description=(EditText)findViewById(R.id.service_description2);
        savencontinue=(Button)findViewById(R.id.save_service2);
        savencontinue.setText("Update");
        //goback=(Button)findViewById(R.id.back_service);

        back_done_right99=(ImageButton)findViewById(R.id.back_done_right992);
        back_done_right99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        savencontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=service_name.getText().toString();
                rate=service_rate.getText().toString();
                description=service_description.getText().toString();
                old_mrp=barcode2.getText().toString();
                pp_final=service_rate_out2.getText().toString();
                if(!name.isEmpty()&&!rate.isEmpty())
                {
                    if(convertImage.equals("")&&check_that)
                    {
                        Bitmap bitmap=((BitmapDrawable)imageView_pic.getDrawable()).getBitmap();
                        img=bitmap;
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        img.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        convertImage= Base64.encodeToString(byteArray,Base64.DEFAULT);
                    }
                    conti=true;
                    new Adding().execute();
                }
                else
                {
                    if(name.isEmpty()) {
                        service_name.setError("please enter service name");
                    }
                    else {
                        service_rate.setError("please enter rate first");
                    }
                }
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
            imageuri=data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                imageView_pic.setImageBitmap(bitmap);
                img=bitmap;
                //imageView_pic.setImageURI(imageuri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                convertImage= Base64.encodeToString(byteArray,Base64.DEFAULT);
                Log.d("image ", "doInBackground: "+convertImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private class gethsn extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
           try{
            URL url=new URL( "https://express.accountantlalaji.com/newapp/onlinelalaji/greycartvendor/listofunits");
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                //System.out.print(current);
            }
            JSONObject object=new JSONObject(current);
            list=new ArrayList<>();
            JSONArray jsonArray=object.getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject object1 = jsonArray.getJSONObject(i);
                String data2=String.valueOf(object1.get("name"));
                String id2=String.valueOf(object1.get("id"));
                list.add(new SpinnerClass(id2,data2));
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
            super.onPostExecute(s);
            adapter2=new SpinnerAdapter2(context,list);
            spi_gst2.setAdapter(adapter2);
            spi_gst2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SpinnerClass obj=list.get(position);
                    unit_id=obj.getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
    private class gethsn2 extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/listofhsn";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getApplicationContext().
                    getSharedPreferences("login_details",getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.getCategories(url,staff_id);

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                try {
                    JSONObject obj = new JSONObject(s);
                    JSONArray array=obj.getJSONArray("data");
                    list2=new ArrayList<>();
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject item=array.getJSONObject(i);
                        String hsn_id=String.valueOf(item.get("id"));
                        String hsn_code=String.valueOf(item.get("hsn_code"));
                        String hsn_rate=String.valueOf(item.get("hsn_rate"));
                        String hsn_des=String.valueOf(item.get("hsn_description"));
                        list2.add(new SpinnerClass(hsn_id,hsn_code));
                    }
                    adapter3 = new SpinnerAdapter2(context, list2);
                    hsn_spinner.setAdapter(adapter3);

                    hsn_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            SpinnerClass obj = list2.get(position);
                            hsn_id=obj.getId();
                            // name_hst = obj.getName();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                catch (Exception e)
                {
                    Toast.makeText(context,"Plz Open page again",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
            }
        }
    }
    private class getting extends AsyncTask<String,String,String>
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
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/servicesview";
            String data=new JsonParser().getProduct(url,prod_id);
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
                    JSONObject object1=object.getJSONObject("data");
                    String service_name_final=String.valueOf(object1.get("product_name"));
                    String service_rate_final=String.valueOf(object1.get("rate"));
                    String service_id_final=String.valueOf(object1.get("category_id"));
                    String service_description_final=String.valueOf(object1.get("description"));
                    String service_status_final=String.valueOf(object1.get("status"));
                    String hsn_id_final=String.valueOf(object1.get("hsn_id"));
                    String unit_id_final=String.valueOf(object1.get("unit_id"));
                    String url_img=String.valueOf(object1.get("img"));
                    String old_mrp=String.valueOf(object1.get("old_mrp"));
                    String barcode=String.valueOf(object1.get("barcode"));
                    String pp=String.valueOf(object1.get("pp"));
                    pp_final=pp;
                    barcode_final=barcode;
                    if(old_mrp.equals(""))
                    {
                        old_mrp="0";
                    }

                    service_rate_out2.setText(pp);
                    hsn_id=hsn_id_final;
                    unit_id=unit_id_final;
                    System.out.println("----------------------"+pp_final+"-------------------------------------"+unit_id);
                    status_pro=service_status_final;
                    cat_id=service_id_final;
                    service_name.setText(service_name_final);
                    service_description.setText(service_description_final);
                    barcode2.setText(old_mrp);
                    service_rate.setText(service_rate_final);
                    Picasso.with(update_Service.this)
                            .load(url_img)
                            .into(imageView_pic, new Callback() {
                                @Override
                                public void onSuccess() {
                                    check_that=true;
                                }

                                @Override
                                public void onError() {
                                   check_that=false;
                                }
                            });
                    for(int i=0;i<list2.size();i++)
                    {
                        SpinnerClass obj=list2.get(i);
                        if(obj.getId().equals(hsn_id))
                        {
                            hsn_spinner.setSelection(i);
                            break;
                        }
                    }
                    for(int i=0;i<list.size();i++)
                    {
                        SpinnerClass obj2=list.get(i);
                        if(obj2.getId().equals(unit_id_final))
                        {
                            spi_gst2.setSelection(i);
                            break;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(update_Service.this,"Service Not Found",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
Toast.makeText(update_Service.this,"NO internet connection",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }
            return null;
            } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private class Adding extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Updating data it will take some time");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/servicesupdate";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getApplicationContext().
                    getSharedPreferences("login_details",getApplicationContext().MODE_PRIVATE);
            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.updateServices(url,prod_id,name,rate,description,convertImage,hsn_id,
                    cat_id,status_pro,unit_id,pp_final,old_mrp,barcode_final);
            System.out.println("----------------------"+pp_final+"-------------------------------------"+unit_id);
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
                    //Toast.makeText(context,object1,Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(update_Service.this);
                    builder.setTitle("Update")
                            .setMessage(object1)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(conti) {
                                        finish();
                                    }
                                }
                            });
                    builder.create();
                    builder.show();
                    service_name.setText("");
                    service_rate.setText("");
                    service_description.setText("");
                    //make a default image and make it paste here
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e);
                    Toast.makeText(update_Service.this,"Some exception",Toast.LENGTH_SHORT).show();
                }
            }
            else if(isInternetAvailable()==false)
            {
                Toast.makeText(update_Service.this,"Internet not available",Toast.LENGTH_SHORT).show();
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(update_Service.this);
                builder.setTitle("Update")
                        .setMessage("please check entered details.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();
            }
        }
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
