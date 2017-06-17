package com.example.hb2009.contacts0617;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.hb2009.contacts0617.MainActivity.MEMBER_ADDRESS;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_EMAIL;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_NAME;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_PASSWORD;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_PHONE;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_PHOTO;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_SEQ;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_TABLE;


public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        Context context = MemberDetail.this;

        ImageView iv_photo = (ImageView) findViewById(R.id.iv_photo);

        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_email = (TextView) findViewById(R.id.tv_email);
        TextView tv_addr = (TextView) findViewById(R.id.tv_addr);
        TextView tv_phone = (TextView) findViewById(R.id.tv_phone);


        Intent intent = this.getIntent();
        final String seq = intent.getExtras().getString("seq");

       Log.d("넘어온값",seq);

        final getDetail detail = new getDetail(context);
      MainActivity.Member m = new MainActivity.DeleteService() {
          @Override
          public MainActivity.Member perform() {
              return detail.execute(seq);
          }
      }.perform();

        tv_name.setText(m.name);
        tv_email.setText(m.email);
        tv_phone.setText(m.phone);
        tv_addr.setText(m.addr);
        
        int profile = getResources().getIdentifier(this.getPackageName()+":drawable/"+m.photo,null,null);
        iv_photo.setImageDrawable(getResources().getDrawable(profile,context.getTheme()));

        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MemberDetail.this,"mail1",Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_dial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_movie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private abstract class DetailQuery extends MainActivity.QueryFactory{
        MainActivity.mySQLiteHelper helper;
        public DetailQuery(Context context) {
            super(context);
            helper = new MainActivity.mySQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDB() {
            return helper.getReadableDatabase();
        }
    }

    private class getDetail extends DetailQuery {

        public getDetail(Context context) {
            super(context);
        }

        @Override
        public SQLiteDatabase getDB() {
            return super.getDB();
        }
        public MainActivity.Member execute(String seq){
            Cursor cursor=this.getDB().rawQuery(String.format("SELECT * FROM %s WHERE %s = '%s';",MEMBER_TABLE,MEMBER_SEQ,seq),null);

            MainActivity.Member m = new MainActivity.Member();
            if(cursor.moveToNext()) {
                m.seq=Integer.parseInt(cursor.getString(cursor.getColumnIndex(MEMBER_SEQ)));
                m.password=cursor.getString(cursor.getColumnIndex(MEMBER_PASSWORD));
                m.addr=cursor.getString(cursor.getColumnIndex(MEMBER_ADDRESS));
                m.email=cursor.getString(cursor.getColumnIndex(MEMBER_EMAIL));
                m.name=cursor.getString(cursor.getColumnIndex(MEMBER_NAME));
                m.phone=cursor.getString(cursor.getColumnIndex(MEMBER_PHONE));
                m.photo=cursor.getString(cursor.getColumnIndex(MEMBER_PHOTO));
            }
            return m;
        }
    }






}
