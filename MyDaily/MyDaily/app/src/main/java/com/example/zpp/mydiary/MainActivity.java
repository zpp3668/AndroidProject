package com.example.zpp.mydiary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private ListView lv_dailylist;
    private SimpleCursorAdapter mAdapter;
    private String []title;
    private int []id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        lv_dailylist = (ListView) findViewById(R.id.lv_dailylist);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        mDatabase = new DBHelper(this).getWritableDatabase();
        queryTitle();
        initEvent();
    }

    public void initEvent(){
        final MyBaseAdapter myBaseAdapter=new MyBaseAdapter();
        lv_dailylist.setAdapter(myBaseAdapter);
        lv_dailylist.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Bundle bundle = new Bundle();
                bundle.putInt("id", position);
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);   //startActivity方法
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void queryTitle() {
        Cursor cursor1= mDatabase.rawQuery("select count(2) from "+DBHelper.TABLE_NAME,null);
        cursor1.moveToFirst();
        long count = cursor1.getLong(0);
        int num=(int) count;
        title=new String[num];
        id=new int[num];
        cursor1.close();
        Cursor cursor;
        cursor = mDatabase.query(DBHelper.TABLE_NAME,DBHelper.TABLE_COLUMNS,null,null,null,null,null);
        int i=0;
        while (cursor != null && cursor.moveToNext()) {
            id[i]=cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
            title[i]=cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE));
            i+=1;
        }
        cursor.close();
    }

    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            Cursor cursor= mDatabase.rawQuery("select count(2) from "+DBHelper.TABLE_NAME,null);
            cursor.moveToFirst();
            long count = cursor.getLong(0);
            cursor.close();
            int num=(int)count;
            return num;
        }

        @Override
        public Object getItem(int position) { return title[position]; }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertview, ViewGroup parent) {
            View view=View.inflate(MainActivity.this,R.layout.list_item,null);
            TextView tv_title=(TextView)view.findViewById(R.id.tv_title);
            tv_title.setText(title[position]);
            return view;
        }
    }

}
