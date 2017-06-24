package com.example.hb2009.contacts0617.tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

/**
 * Created by hb2009 on 2017-06-24.
 */

public class Phone {
    private Context context;
    private Activity activity;

    public Phone(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void dial(String phoneNo){
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNo));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void call(String phoneNo){

        /*마시멜로 버젼
        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNo));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/

        //누가 버젼
        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNo));
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[]{},2);
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
