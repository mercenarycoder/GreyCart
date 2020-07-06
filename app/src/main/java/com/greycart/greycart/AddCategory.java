package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddCategory extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ArrayList<String> list;
private SpinnerAdapter adapter;
private Spinner spinner;
private Button submitCategory,submitService;
private TextView category;
    Uri imageuri;
    Bitmap img=null;
LinearLayout service_image_new;
ImageView imagepro_new;
public String state=null;
ProgressDialog progressDialog;
Context context;
Button submit_cat_continue;
String cat_name,convertImage="";
boolean counti=false;
private EditText service_name,service_rate,service_desc;
private ImageButton save_service,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_draw);
        formlist();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context= AddCategory.this;
        category=(TextView)findViewById(R.id.category_name);
        submitCategory=(Button)findViewById(R.id.save_category);
        //
        service_image_new=(LinearLayout)findViewById(R.id.service_image_new);
        imagepro_new=(ImageView)findViewById(R.id.imagepro_new);
      service_image_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        cancel=(ImageButton)findViewById(R.id.back_done_right44);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit_cat_continue=(Button)findViewById(R.id.save_category_continue);
        spinner=(Spinner)findViewById(R.id.spinner_state);
        adapter=new SpinnerAdapter(this,list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            state= (String) parent.getItemAtPosition(position);
           // Toast.makeText(AddCategory.this,state,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submitCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_name=category.getText().toString();
                counti=true;
                new saveCategory().execute();
            }
        });
        submit_cat_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counti=false;
                cat_name=category.getText().toString();
                new saveCategory().execute();
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
                imagepro_new.setImageBitmap(bitmap);
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

    public void formlist()
    {
        list=new ArrayList<>();
        list.add("Active");
        list.add("Deactive");
    }


    public class saveCategory extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Sending information");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/categoryadd";
            String name=cat_name;
            String status=state;
            String pos="0";
            SharedPreferences preferences=getApplicationContext().
                    getSharedPreferences("login_details",getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String result=new JsonParser().saveCategory(url,staff_id,name,status,pos,convertImage);
            return result;
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
                  //  Toast.makeText(context,object1,Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddCategory.this);
                    builder.setTitle("Update")
                            .setMessage(object1)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                             if(counti) {
                                 finish();
                             }
                                }
                            });
                    builder.create();
                    builder.show();
              category.setText("");
              spinner.setSelection(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                Toast.makeText(context,"Data inconsistent check please",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddCategory.this);
                builder.setTitle("Update")
                        .setMessage("Error updating data")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                          finish();
                            }
                        });
                builder.create();
                builder.show();
            }

        }
    }
}
