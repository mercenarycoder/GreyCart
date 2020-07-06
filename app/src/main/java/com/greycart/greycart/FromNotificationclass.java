package com.greycart.greycart;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.greycart.greycart.App.CHANNEL_1_ID;

public class FromNotificationclass extends BroadcastReceiver
{
    Context context;
    NotificationManagerCompat notificationManager;
    int r=0;
    public void formality()
    {
        notificationManager= NotificationManagerCompat.from(context);
        new setIt().execute();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        formality();
    }

    public class setIt extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/notification";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=context.getApplicationContext().
                    getSharedPreferences("login_details",context.getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.notifications(url,staff_id);

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        if(s!=null) {
            try {
                boolean counter=true;
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                ArrayList name = new ArrayList<String>();
                // list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String cus_name = String.valueOf(object.get("customer_name"));
                    String order_no = String.valueOf(object.get("order_no"));
                    String book_date = String.valueOf(object.get("appointment_date"));
                    String time = String.valueOf(object.get("start_time")) + " to " +
                            String.valueOf(object.get("end_time"));
                    String book_time = String.valueOf(object.get("day"));
                    System.out.println(cus_name);
                    Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    Intent action = new Intent(context, Dashboard.class);
                    //action.putExtra("appointment_id", appointment_id);
                    //action.putExtra("buttons", "allfine2");
                    //Context context;
                    PendingIntent pendingIntent = PendingIntent.getActivity(context,
                            0, action, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Notification notification = new NotificationCompat.Builder(context,
                                order_no)
                                .setSmallIcon(R.drawable.ic_notification)
                                .setContentTitle(cus_name)
                                .addAction(R.drawable.add_icon, "Order", pendingIntent)
                                .setContentInfo("Order Number:" + order_no)
                                .setContentText("Time:" + time)
                                .setAutoCancel(true)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();
                         // notification.flags|=Notification.FLAG_AUTO_CANCEL;
                        NotificationChannel channel1 = new NotificationChannel(
                                order_no,
                                cus_name,
                                NotificationManager.IMPORTANCE_HIGH
                        );
                        channel1.setDescription(time+"     Order Number"+order_no);
                        channel1.enableLights(true);
                        channel1.setShowBadge(true);
                        channel1.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                        NotificationManager manager =context.getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel1);
                        manager.notify(r,notification);
                        r++;
                        v.vibrate(VibrationEffect.createOneShot(1000,
                                VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    else {
                        Notification notification = new NotificationCompat.Builder(context,
                                CHANNEL_1_ID)
                                .setSmallIcon(R.drawable.vendorlogo2)
                                .setContentTitle(cus_name)
                                .addAction(R.drawable.add_icon, "Order", pendingIntent)
                                .setContentInfo("Order Number:" + order_no)
                                .setContentText("Time:" + time)
                                .setAutoCancel(true)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();
                        // a small code for making phone vibrate on this code when api is made
                        notificationManager.notify(r, notification);
                        r++;
                        v.vibrate(1000);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
            }
        }
    }

