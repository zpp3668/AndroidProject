package com.example.zpp.mydiary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_back,btn_delete;
    private TextView tv_title,tv_createtime,tv_content;
    private SQLiteDatabase mDatabase;
    private int []idlist;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDatabase = new DBHelper(this).getWritableDatabase();
        queryTitle();
        initView();
        initEvent();
    }

    public void initView(){
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_createtime = (TextView) findViewById(R.id.tv_createtime);
        tv_content = (TextView) findViewById(R.id.tv_content);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(DetailActivity.this);
        btn_delete = (Button) findViewById(R.id.btn_delete);
    }

    public void initEvent(){
        Bundle b=getIntent().getExtras();
        //获取Bundle的信息
        int pos=b.getInt("id");
        id=idlist[pos];
        System.out.println("id:"+id);
        Cursor cursor= mDatabase.query(DBHelper.TABLE_NAME,DBHelper.TABLE_COLUMNS,"id=?",new String[]{id+""},null,null,null);
        while (cursor != null && cursor.moveToNext()) {
            tv_title.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE)));
            tv_createtime.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CREATETIME)));
            tv_content.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CONTENT)));
        }
        cursor.close();
        btn_delete.setOnClickListener(DetailActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        if (view.getId() == R.id.btn_delete) {
            deleteData(id);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    private void queryTitle() {
        Cursor cursor1= mDatabase.rawQuery("select count(2) from "+DBHelper.TABLE_NAME,null);
        cursor1.moveToFirst();
        long count = cursor1.getLong(0);
        int num=(int) count;
        idlist=new int[num];
        cursor1.close();
        Cursor cursor;
        cursor = mDatabase.query(DBHelper.TABLE_NAME,DBHelper.TABLE_COLUMNS,null,null,null,null,null);
        int i=0;
        while (cursor != null && cursor.moveToNext()) {
            idlist[i]=cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
            i+=1;
        }
        cursor.close();
    }

    private void deleteData(int id) {
        mDatabase.delete(DBHelper.TABLE_NAME,"id = ?",new String[]{id+""});
    }

}
