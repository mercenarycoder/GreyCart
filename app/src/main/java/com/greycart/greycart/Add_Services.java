package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.greycart.greycart.R.drawable.add_icon;

public class Add_Services extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    String cat_id=null;
    ImageView imageView_pic;
    LinearLayout takepic;
    Uri imageuri;
    EditText service_name,service_rate,service_description,service_rate_out3,barcode_3;
    String name=null,rate=null,description=null,old_mrp=null,pp=null;
    Bitmap img=null;
    Button savencontinue,goback;
    Spinner spi_gst,hsn_spinner;
    SpinnerAdapter2 adapter2,adapter3;
    ArrayList<SpinnerClass> list,list2;
    String name_hst="",id_hst="",gst_slot="";
    ProgressDialog progressDialog;
    boolean conti=false;
    Context context;
    TextView selected_category;
    String cat_name="";
    Button rotate_issue;
    ImageButton back_done_right99;
    String convertImage="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__services);
        context= Add_Services.this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        cat_id=getIntent().getStringExtra("cat_id");
        cat_name=getIntent().getStringExtra("cat_name");
        spi_gst=(Spinner)findViewById(R.id.spi_gst);
        new gethsn().execute();
        hsn_spinner=(Spinner)findViewById(R.id.hsn_spinner);
        new gethsn2().execute();
        rotate_issue=(Button)findViewById(R.id.rotate_issue);
        rotate_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExifInterface exif = null;
                try {
                File file=new File(Add_Services.this.getCacheDir(),"sathi");
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.JPEG,55,byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    file.createNewFile();
                    FileOutputStream fos=new FileOutputStream(file);
                    fos.write(byteArray);
                    fos.flush();
                    fos.close();
                exif = new ExifInterface(convertImage);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                if(orientation==ExifInterface.ORIENTATION_ROTATE_90||1==1)
                {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                  Bitmap   bitmap = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(),
                          matrix, true);
                    imageView_pic.setImageBitmap(bitmap);
                    img=bitmap;
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.JPEG,55,byteArrayOutputStream2);
                    byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
                    convertImage= Base64.encodeToString(byteArray2,Base64.DEFAULT);
                }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        selected_category=(TextView)findViewById(R.id.selected_category);
        selected_category.setText("Category : "+cat_name);
        imageView_pic=(ImageView)findViewById(R.id.imagepro2);
        takepic=(LinearLayout)findViewById(R.id.service_image2);
        barcode_3=(EditText)findViewById(R.id.barcode_3);
        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        service_name=(EditText)findViewById(R.id.service_name);
        service_rate=(EditText)findViewById(R.id.service_rate);
        service_description=(EditText)findViewById(R.id.service_description);
        savencontinue=(Button)findViewById(R.id.save_service);
        service_rate_out3=(EditText)findViewById(R.id.service_rate_out3);
        service_rate_out3.setText("0");
        goback=(Button)findViewById(R.id.back_service);
        back_done_right99=(ImageButton)findViewById(R.id.back_done_right99);
        back_done_right99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=service_name.getText().toString();
                rate=service_rate.getText().toString();
                description=service_description.getText().toString();
                old_mrp=barcode_3.getText().toString();
                pp=service_rate_out3.getText().toString();
                if(!name.isEmpty()&&!rate.isEmpty())
                {
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
        savencontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=service_name.getText().toString();
                rate=service_rate.getText().toString();
                description=service_description.getText().toString();
                pp=service_rate_out3.getText().toString();
                old_mrp=barcode_3.getText().toString();
                if(!name.isEmpty()&&!rate.isEmpty())
                {
                    conti=false;
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
                    System.out.print(current);
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
            spi_gst.setAdapter(adapter2);
            SpinnerClass cc=list.get(0);
            id_hst=cc.getId();
            spi_gst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SpinnerClass obj=list.get(position);
                    id_hst=obj.getId();
                    name_hst=obj.getName();
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
            SharedPreferences preferences=context.getApplicationContext().
                    getSharedPreferences("login_details",context.getApplicationContext().MODE_PRIVATE);

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
                SpinnerClass cc=list2.get(0);
                gst_slot=cc.getId();
                hsn_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SpinnerClass obj = list2.get(position);
                        gst_slot = obj.getId();
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
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG,55,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                convertImage= Base64.encodeToString(byteArray,Base64.DEFAULT);
                 //imageView_pic.setImageURI(imageuri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private class Adding extends AsyncTask<String,String,String>
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
/*url:https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/business_category*/
        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/servicesadd";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getApplicationContext().
                    getSharedPreferences("login_details",getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.addServices(url,name,rate,cat_id,convertImage,description,staff_id,gst_slot,id_hst,pp,old_mrp);

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
                    Toast.makeText(context,object1,Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(Add_Services.this);
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
                    barcode_3.setText("");
                    service_rate_out3.setText("");
                    convertImage="";
                    imageView_pic.setImageResource(R.drawable.ic_image_pic);
                    //make a default image and make it paste here
                } catch (JSONException e) {
                    e.printStackTrace();
                 System.out.println(e);
            Toast.makeText(Add_Services.this,"Some exception",Toast.LENGTH_SHORT).show();
                }
            }
            else if(isInternetAvailable()==false)
            {
                Toast.makeText(Add_Services.this,"Internet not available",Toast.LENGTH_SHORT).show();
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_Services.this);
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
