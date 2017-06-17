package com.example.hb2009.contacts0617;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.hb2009.contacts0617.MainActivity.MEMBER_ADDRESS;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_EMAIL;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_NAME;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_PASSWORD;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_PHONE;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_PHOTO;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_SEQ;
import static com.example.hb2009.contacts0617.MainActivity.MEMBER_TABLE;

public class MemberList extends AppCompatActivity {
    interface ListService{public  ArrayList<?> perform();}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);

        final Context context=MemberList.this;
       final getList getlist = new getList(context);

        ArrayList<MainActivity.Member> list =(ArrayList<MainActivity.Member>)new ListService() {
            @Override
            public ArrayList<?> perform() {
                return getlist.execute();
            }
        }.perform();

        ListView listView= (ListView) findViewById(R.id.listView);
        listView.setAdapter(new MemberAdapter(context,list));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }

    private abstract class ListQuery extends MainActivity.QueryFactory{
        MainActivity.mySQLiteHelper helper;
        public ListQuery(Context context) {
            super(context);
            helper = new MainActivity.mySQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDB() {
            return helper.getReadableDatabase();
        }
    }

    private class getList extends ListQuery{

        public getList(Context context) {
            super(context);
        }

        @Override
        public SQLiteDatabase getDB() {
            return super.getDB();
        }

        public ArrayList<MainActivity.Member> execute(){
            ArrayList<MainActivity.Member> list = new ArrayList<>();

            Cursor cursor=this.getDB().rawQuery(String.format("SELECT * FROM %s ;",MEMBER_TABLE),null);

            MainActivity.Member m=null;
            if(cursor!=null){
                if (cursor.moveToFirst()){
                    do{
                        m=new MainActivity.Member();
                        m.seq=Integer.parseInt(cursor.getString(cursor.getColumnIndex(MEMBER_SEQ)));
                        m.password=cursor.getString(cursor.getColumnIndex(MEMBER_PASSWORD));
                        m.addr=cursor.getString(cursor.getColumnIndex(MEMBER_ADDRESS));
                        m.email=cursor.getString(cursor.getColumnIndex(MEMBER_EMAIL));
                        m.name=cursor.getString(cursor.getColumnIndex(MEMBER_NAME));
                        m.phone=cursor.getString(cursor.getColumnIndex(MEMBER_PHONE));
                        m.photo=cursor.getString(cursor.getColumnIndex(MEMBER_PHOTO));
                        list.add(m);
                    }while(cursor.moveToNext());

                }
            }else{
                Log.d("등록된 회원이 ","없습니다.");
            }

            Log.d("###### 전체목록 :",list.get(0).toString());
            return list;
        }

    }

    private class MemberAdapter extends BaseAdapter {
        ArrayList<MainActivity.Member>list;
        LayoutInflater inflater;
        public MemberAdapter(Context context, ArrayList<MainActivity.Member> list) {
            this.list=list;
            this.inflater= LayoutInflater.from(context);
        }
        private int[] photos={
                R.drawable.cupcake,
                R.drawable.donut,
                R.drawable.eclair,
                R.drawable.froyo,
                R.drawable.gingerbread,
                R.drawable.honeycomb,
                R.drawable.icecream,
                R.drawable.jellybean,
                R.drawable.kitkat,
                R.drawable.lollipop
        };

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder holder;
            if(v==null){
                v=inflater.inflate(R.layout.member_item,null);
                holder=new ViewHolder();
                holder.profileimg= (ImageView) v.findViewById(R.id.profileimg);
                holder.name= (TextView) v.findViewById(R.id.name);
                holder.phone= (TextView) v.findViewById(R.id.phone);
                v.setTag(holder);
            }else{
                holder= (ViewHolder) v.getTag();
            }
            holder.profileimg.setImageResource(photos[i]);
            holder.name.setText(list.get(i).name);
            holder.phone.setText(list.get(i).phone);
            return v;
        }
    }
    static class ViewHolder{
        ImageView profileimg;
        TextView name;
        TextView phone;
    }


}
