package com.greycart.greycart;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {
    LinearLayout forget_1,forget_2,forget_3;
    EditText phone_forget,password_forget,confirm_forget,verify_otp2;
    TextView forgot_otp2;
    Button register_forget,final_forget,verify_it2;
    ProgressDialog progressDialog;
    ImageButton back_forgot;
    String phone="",password="",password_confirm="",otp="",otp_final="",id="";
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context= ForgotPassword.this;
        //for phone number
        forget_1=(LinearLayout)findViewById(R.id.forget_1);
        //for OTP Verifications
        forget_2=(LinearLayout)findViewById(R.id.forget_2);
        //for making new password
        back_forgot=(ImageButton)findViewById(R.id.back_forgot);
        back_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        forget_3=(LinearLayout)findViewById(R.id.forget_3);
        forget_2.setVisibility(View.INVISIBLE);
        forget_3.setVisibility(View.INVISIBLE);
        forgot_otp2=(TextView)findViewById(R.id.forgot_otp2);
        forgot_otp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AdminLogin().execute();
            }
        });
        //for initially getting phone number dude
        register_forget=(Button)findViewById(R.id.register_forget);
        //this one for setting changed password
        final_forget=(Button)findViewById(R.id.final_forget);
        //for verifying otp with forget_2
        verify_it2=(Button)findViewById(R.id.verify_it2);
        phone_forget=(EditText)findViewById(R.id.phone_forget);
        password_forget=(EditText)findViewById(R.id.password_forget);
        confirm_forget=(EditText)findViewById(R.id.confirm_forget);
        verify_otp2=(EditText)findViewById(R.id.verify_otp2);
        register_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here get the number of the user initially
                phone=phone_forget.getText().toString();
                if(phone.length()>10||phone.length()<10)
                {
                    Toast.makeText(context,"Please Enter a Valid Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    new AdminLogin().execute();
                }
            }

        });
        final_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password=password_forget.getText().toString();
                password_confirm=confirm_forget.getText().toString();
                if(password.length()<6)
                {
                    Toast.makeText(context,"Lenght Of Password Must be Greater Then 6",Toast.LENGTH_SHORT).show();
                    password_forget.setError("Password Length very Short");
                }
                else if(password_confirm.equals(password))
                {
                    password_forget.setError("");
                    confirm_forget.setError("");
                    new finalChange().execute();
                }
                else
                {
                    confirm_forget.setError("Passwords Do Not Match");
                    Toast.makeText(context,"Both Passowrds Do Not Match",Toast.LENGTH_SHORT).show();
                }

            }
        });
        verify_it2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_final=verify_otp2.getText().toString();
                if(otp_final.equals(otp))
                {
                    new verifyOTP().execute();
                }
                else if(otp_final.length()==0)
                {
                    Toast.makeText(context,"Please Enter OTP First",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context,"Please Enter Correct OTP",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private class finalChange extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/step3forgotpassword";
            String data=new JsonParser().updatePassword2(password,password_confirm,url,id);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject object = new JSONObject(s);
                    boolean check=false;
                    if(String.valueOf(object.get("status")).equals("1"))
                    {
                        check=true;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final boolean finalCheck = check;
                    builder.setTitle("Alert")
                            .setMessage(String.valueOf(object.get("msg")))
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @SuppressLint("RestrictedApi")
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(finalCheck)
                                    {
                                        SharedPreferences login_pref=getApplicationContext().
                                                getSharedPreferences("login_details", getApplicationContext().
                                                        MODE_PRIVATE);
                                        SharedPreferences.Editor editor=login_pref.edit();
                                        //Toast.makeText(MainActivity.this,jsonObject1.getString("user_name"), Toast.LENGTH_SHORT).show();
                                        editor.putString("user_pass","");
                                        editor.apply();
                                        editor.commit();
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(context,"Correct error as shown in message and try again",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.create();
                    builder.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class verifyOTP extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/step2verifyotp";
            String data=new JsonParser().verifyOTP(id,otp,url,phone);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject object = new JSONObject(s);
                    String msg=String.valueOf(object.get("status"));
                    if(msg.equals("1"))
                    {
                        forget_3.setVisibility(View.VISIBLE);
                        forget_2.setVisibility(View.INVISIBLE);
                        //JSONObject object1=object.getJSONObject("data");
                       // id=String.valueOf(object1.get("id"));
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Alert")
                                .setMessage(String.valueOf(object.get("msg")))
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @SuppressLint("RestrictedApi")
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(context,"Please your Interenet connectection",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class AdminLogin extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context;
            progressDialog = new ProgressDialog(ForgotPassword.this);
            progressDialog.setMessage("Please wait");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();
            String url = "https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/step1sendotpmobile";
            String json = jsonParser.sendOTPFirst(url,phone);
            //        Log.d("return",json);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (s != null) {
                JSONObject object = null;
                try {
                    object = new JSONObject(s);
                    String object1 =String.valueOf( object.get("data"));
                    String msg=String.valueOf(object.get("msg"));
                    if(msg.equals("OTP sent successfully"))
                    {
                        otp=object1;
                        forget_1.setVisibility(View.INVISIBLE);
                        forget_2.setVisibility(View.VISIBLE);
                        id=String.valueOf(object.get("vendor_id"));
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Alert")
                                .setMessage(String.valueOf(msg))
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @SuppressLint("RestrictedApi")
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                    //toDetails(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Please Press Confirm button once more",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(context,"Internet Issues",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
