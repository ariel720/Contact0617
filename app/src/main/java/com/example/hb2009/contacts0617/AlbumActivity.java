package com.example.hb2009.contacts0617;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        final Context context = AlbumActivity.this;
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new PhotoAdapter(context,photos()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(context,"test"+i,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String[] photos(){
        int count = 5;
        String[] arr = new String[count];
        for(int i = 0; i<arr.length;i++){
            arr[i] = "mov0"+i;
        }

        return arr;
    }

    private class PhotoAdapter extends BaseAdapter {
        private Context context;
        private String[]photos;

        public PhotoAdapter(Context context, String[] photos) {
            this.context = context;
            this.photos = photos;
        }

        @Override
        public int getCount() {
            return photos.length;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View gridview;
            ViewHolder holder;
            if(v==null){
                gridview = new GridView(context);
                gridview = inflater.inflate(R.layout.photo_item,null);
                holder=new ViewHolder();
             //   holder.iv_photo= (ImageView) v.findViewById(R.id.iv_photo);
                ImageView imageview = (ImageView) gridview.findViewById(R.id.iv_photo);
                String photo = photos[i];
                switch (photo){
                    case "mov01": imageview.setImageResource(R.drawable.mov01); break;
                    case "mov02": imageview.setImageResource(R.drawable.mov02);break;
                    case "mov03": imageview.setImageResource(R.drawable.mov03);break;
                    case "mov04": imageview.setImageResource(R.drawable.mov04);break;
                    case "mov05": imageview.setImageResource(R.drawable.mov05);break;
               /*     case "mov06": imageview.setImageResource(R.drawable.mov06);break;
                    case "mov07": imageview.setImageResource(R.drawable.mov07);break;
                    case "mov08": imageview.setImageResource(R.drawable.mov08);break;
                    case "mov09": imageview.setImageResource(R.drawable.mov09);break;*/
                }
            }else{
                gridview = v;
            }

            return gridview;
        }
    }

    static class ViewHolder{
        ImageView iv_photo;
    }


}
