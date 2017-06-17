package com.example.hb2009.contacts0617;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
//1.DTO만들기
    static class Member{int seq;String name,password,email,phone,addr,photo;}
    //2. 쿼리공장 : sqliteDB가져옴.
    static abstract class QueryFactory{
        Context context;
        public QueryFactory(Context context) {this.context = context;}
        public abstract SQLiteDatabase getDB();
    }

    public final static String DB_NAME = "ariel.db";
    public final static String MEMBER_TABLE = "Members";
    public final static String MEMBER_SEQ = "seq";
    public final static String MEMBER_NAME = "name";
    public final static String MEMBER_PASSWORD = "password";
    public final static String MEMBER_PHONE = "phone";
    public final static String MEMBER_EMAIL = "email";
    public final static String MEMBER_ADDRESS = "address";
    public final static String MEMBER_PHOTO = "photo";

    //람다변환위해 필요
    interface LoginService {public void login();}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context context=MainActivity.this;

        //6.로그인 쿼리가 있는 클래스 일단 가져옴.
        final MemberLogin mLogin = new MemberLogin(context);

        //7.db만드는클래스 호출
        mySQLiteHelper helper = new mySQLiteHelper(context); //DB만들기

        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText inputID= (EditText) findViewById(R.id.inputID);
                EditText inputPW= (EditText) findViewById(R.id.inputPW);
                final String sID=inputID.getText().toString();
                final String sPW=inputPW.getText().toString();

                Log.d("넘어갈id",sID);
                Log.d("넘어갈pw",sPW);

                new LoginService(){
                    @Override
                    public void login() {
//8.로그인 쿼리 실행.
                        if(mLogin.execute(sID,sPW)){
                            Toast.makeText(context,"로그인 성공",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context,MemberList.class));
                        }else{
                            Toast.makeText(context,"로그인 실패",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context,MainActivity.class));
                        }

                    }
                }.login();

            }
        });
        findViewById(R.id.joinBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //인터페이스에 메소드 하나만.
    /*interface : 추상메소드만 있음.
    abstract : concrete 메소드와 추상메소드 함께 있음.
    */

   // public static interface ListService {public ArrayList<?> list();}
//3.테이블만들고 가라 데이터 집어넣고
    public static class mySQLiteHelper extends SQLiteOpenHelper{

        public mySQLiteHelper(Context context) {
            super(context, DB_NAME, null, 1);
            this.getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s" +
                            "(%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT," +
                            "%s TEXT,%s TEXT,%s TEXT,%s TEXT);",
                    MEMBER_TABLE,MEMBER_SEQ,MEMBER_NAME,MEMBER_PASSWORD,MEMBER_EMAIL,MEMBER_PHONE,MEMBER_ADDRESS,MEMBER_PHOTO));

            for(int i=1;i<10;i++){
                db.execSQL(
                        String.format(
                                "INSERT INTO %s(%s,%s,%s,%s,%s,%s)" +
                                        "VALUES('%s','%s','%s','%s','%s','%s');",
                                MEMBER_TABLE,MEMBER_NAME,MEMBER_PASSWORD,MEMBER_EMAIL,MEMBER_PHONE,MEMBER_ADDRESS,MEMBER_PHOTO,
                                "홍길동"+i,"1","hong"+i+"@test.com","010-1234-567"+i,"서울"+i,"profileimg"));
            }
            Log.d("########## 실행한 지점","onCreate");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS"+MEMBER_TABLE );
            onCreate(db);
        }
    }

//4.만들어진 db중에 읽을수 있는 db를 가져오게 하는 함수
    private abstract class LoginQuery extends  MainActivity.QueryFactory{
        SQLiteOpenHelper helper;
        public LoginQuery(Context context) {
            super(context);
            helper = new MainActivity.mySQLiteHelper(context);
        }

        //alt+insert : overid Methods
        @Override
        public SQLiteDatabase getDB() {
            return helper.getReadableDatabase();
        }
    }
//5.위의 함수를 상속받고, id,pw를 받아서 로그인 쿼리를 실행.
    private class MemberLogin extends LoginQuery{

        public MemberLogin(Context context) {
            super(context);
        }
        public boolean execute(String seq,String password){
            return super
                .getDB()
                .rawQuery(String.format("SELECT password FROM %s WHERE %s = '%s' AND %s = '%s'",
                        MEMBER_TABLE,MEMBER_SEQ,seq,MEMBER_PASSWORD,password),null)
                .moveToNext();
        }
    }


}
