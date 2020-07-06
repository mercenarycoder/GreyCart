package com.greycart.greycart;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Update_category extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    String cat_id,name,position,status;
int position2=1;
ArrayList<String> list;
EditText category_name2;
    Uri imageuri;
    Bitmap img=null;
    String convertImage="";
    ImageView imageView_pic;
    Spinner spinner_state_update;
Button update_it;
SpinnerAdapter adapter;
ProgressDialog progressDialog;
ImageButton back_done_right45;
String img_url="";
LinearLayout service_image_new;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Intent intent=getIntent();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        imageView_pic=(ImageView)findViewById(R.id.imagepro_new);
        service_image_new=(LinearLayout)findViewById(R.id.service_image_new);
        cat_id=intent.getStringExtra("cat_id");
        name=intent.getStringExtra("name");
        position=intent.getStringExtra("position");
        status=intent.getStringExtra("status");
        img_url=intent.getStringExtra("img");
        if(status.equals("active"))
        {
            position2=0;
        }
        else
        {
            position2=1;
        }
        formlist();
        adapter=new SpinnerAdapter(Update_category.this,list);
        category_name2=(EditText)findViewById(R.id.category_name2);
        category_name2.setText(name);
        spinner_state_update=(Spinner)findViewById(R.id.spinner_state_update);
        spinner_state_update.setAdapter(adapter);
        spinner_state_update.setSelection(position2);
        if(!img_url.equals("zmpty")&&img_url.endsWith(".jpg")&&exists(img_url))
        {
            Picasso.with(Update_category.this).load(img_url).into(imageView_pic);
            Bitmap bitmap=((BitmapDrawable)imageView_pic.getDrawable()).getBitmap();
            img=bitmap;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            convertImage= Base64.encodeToString(byteArray,Base64.DEFAULT);
        }
        service_image_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        spinner_state_update.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status=list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        back_done_right45=(ImageButton)findViewById(R.id.back_done_right45);
        back_done_right45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        update_it=(Button)findViewById(R.id.update_category);
        update_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=category_name2.getText().toString();
                if(name.isEmpty())
                {
                    Toast.makeText(Update_category.this,"Please enter updating name",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
            new updateCategory().execute();
                }
            }
        });
    }
    public static boolean exists(String URLName){
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
    public void formlist()
    {
        list=new ArrayList<>();
        list.add("active");
        list.add("deactive");
    }
    public class updateCategory extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Update_category.this);
            progressDialog.setMessage("Sending information");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/categoryupdate";
            String result=new JsonParser().updateCategory(url,cat_id,name,position,status,convertImage);
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_category.this);
                    builder.setTitle("Update")
                            .setMessage(object1)
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
                    Toast.makeText(Update_category.this,"Data inconsistent check please",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Update_category.this);
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
