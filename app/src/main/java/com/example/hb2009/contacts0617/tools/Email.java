package com.example.hb2009.contacts0617.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by hb2009 on 2017-06-24.
 */

public class Email {
    private Context context;
    private Activity activity;

    public Email(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void sendEmail(String email){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"+email));
        intent.setType("text/plain");
        intent.putExtra(intent.EXTRA_EMAIL,email);
        intent.putExtra(intent.EXTRA_SUBJECT,"title");
        intent.putExtra(intent.EXTRA_TEXT,"hello");
        context.startActivity(intent.createChooser(intent,"example"));
    }


}
