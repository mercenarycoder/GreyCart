package com.greycart.greycart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import static android.Manifest.permission;

public class Create_Appointment  extends Fragment {
   TextView name_of_owner,plan_name,expiry_date;
   Button upgrade_plan,call_the_boss;
   ProgressDialog progressDialog;
   Context context;
   String number="7389438159",upgrade_number;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       context=this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.create_appointment, container, false);
    name_of_owner=(TextView)view.findViewById(R.id.name_of_owner);
    plan_name=(TextView)view.findViewById(R.id.plan_name);
    expiry_date=(TextView)view.findViewById(R.id.expiry_date);
    //upgrade_plan=(Button)view.findViewById(R.id.upgrade_plan);
    call_the_boss=(Button)view.findViewById(R.id.call_the_boss);

    call_the_boss.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(context,permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                    return;
                }
                else
                {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{permission.CALL_PHONE}, 1);
                }
            }
            else
            {
                context.startActivity(callIntent);
            }
        }});
    new getallsupport().execute();
    return view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent callIntent=new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));
                startActivity(callIntent);
            } else {
                Toast.makeText(context, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class getallsupport extends AsyncTask<String,String,String>
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
        protected String doInBackground(String...strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/onlinelalajivendor/upgradeplan";
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().
                            getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            JsonParser parser=new JsonParser();
            String responce=parser.getSupportInfo(url,staff_id);
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
                    JSONObject object1=object.getJSONObject("data");
                   name_of_owner.setText(String.valueOf(object1.get("name")));
                   plan_name.setText(String.valueOf(object1.get("plan_name")));
                   expiry_date.setText(String.valueOf(object1.get("expiry_date")));
                   number=String.valueOf(object1.get("support_call_number"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Something going wrong",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(context,"No internet",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
