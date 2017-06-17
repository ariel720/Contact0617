package com.example.hb2009.contacts0617;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import static com.example.hb2009.contacts0617.MainActivity.MEMBER_ADDRESS;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_EMAIL;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_NAME;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_PASSWORD;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_PHONE;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_PHOTO;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_SEQ;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_TABLE;

public class MemberUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_update);
        final Context context = MemberUpdate.this;

        Intent intent = this.getIntent();
        final String spec = intent.getExtras().getString("spec");
        String []arr = spec.split("/");
        ImageView iv_photo = (ImageView) findViewById(R.id.iv_photo);
        EditText et_name = (EditText) findViewById(R.id.et_name);
        final EditText et_email = (EditText) findViewById(R.id.et_email);
        EditText et_phone = (EditText) findViewById(R.id.et_phone);
        EditText et_addr = (EditText) findViewById(R.id.et_addr);

        final String seq = arr[0];
        final String email = arr[1];
        final String name = arr[2];
        final String addr = arr[5];
        final String phone = arr[3];


        et_email.setHint(email);
        et_email.setText(email);
        et_name.setHint(name);
        et_phone.setHint(phone);
        et_addr.setHint(addr);

        int profile = getResources().getIdentifier(this.getPackageName()+":drawable/"+arr[4],null,null);
        iv_photo.setImageDrawable(getResources().getDrawable(profile,context.getTheme()));

        findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final doUpdate update = new doUpdate(context);

                email =(et_email.getText().toString().equals(""))? email : et_email.getText().toString();
                //모바일에서는 삼항연산자쓰기.

                new MainActivity.UpdateService() {
                   @Override
                   public void perform() {
                       update.execute(name,email,phone,addr,seq);
                   }
               };
            }
        });
    }

    private abstract class UpdateQuery extends MainActivity.QueryFactory{
        MainActivity.mySQLiteHelper helper;
        public UpdateQuery(Context context) {
            super(context);
            helper = new MainActivity.mySQLiteHelper(context);
        }
        @Override
        public SQLiteDatabase getDB() {
            return helper.getWritableDatabase();
        }
    }

    private class doUpdate extends UpdateQuery {
        public doUpdate(Context context) {
            super(context);
        }

        @Override
        public SQLiteDatabase getDB() {
            return super.getDB();
        }
        public void execute(String name,String email,String phone, String addr, String seq){
            this.getDB().execSQL(
                    String.format(
                            "UPDATE %s SET  %s='%s', %s='%s',%s='%s',%s='%s',%s='%s',%s='%s' WHERE %s = '%s';",
                            MEMBER_TABLE,MEMBER_NAME,name,MEMBER_EMAIL,email,MEMBER_PHONE,phone,MEMBER_ADDRESS,addr,MEMBER_SEQ,seq));
        }
    }





}
