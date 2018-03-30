package com.example.u.mytipsmail;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class tipselectActivity extends Activity implements View.OnClickListener {
    String name;
    String id;
    String type;
    String tip;
    DBHelper dbHelper;
    LinearLayout linLayout;
    LayoutInflater ltInflater;

    String[] names;
    String[] ids;
    String[] tips;

    ListView lvMain;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");
        tip = intent.getStringExtra("tip");

        setContentView(R.layout.activity_select);
        dbHelper = new DBHelper(this);
        int[] colors = new int[2];

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ltInflater = getLayoutInflater();

        Cursor c = db.query("mytable", null, null, null, null, null, null);

        int i = 1;
        names[0] = "new Tip";
        ids[0] = "";
        tips[0] = "";
        if (c.moveToFirst()) {
            do {
                int idColIndex = c.getColumnIndex("id");
                int nameColIndex = c.getColumnIndex("name");
                int emailColIndex = c.getColumnIndex("email");
                names[i] = c.getString(nameColIndex);
                ids[i] = c.getString(idColIndex);
                tips[i] = c.getString(emailColIndex);
                i++;
            } while (c.moveToNext());
        } else
            //Log.d(LOG_TAG, "0 rows");
            c.close();

        lvMain = (ListView) findViewById(R.id.lvMain);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.activity_select, names);
         lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                //        + id);
            }
        });

        lvMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long idg) {
                TextView v = (TextView) view.findViewById(R.id.textView1);//TODO hallelujah

                Toast toast;
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                if (type == "add") {
                    if (v.getText() == "new Tip") {
                        toast = Toast.makeText(getApplicationContext(),
                                "Добавлена Заметка!", Toast.LENGTH_SHORT);
                        toast.show();

                        cv.put("id", id);
                        cv.put("name", name);
                        cv.put("email", tip);
                        long rowID = db.insert("mytable", null, cv);
                    } else {
                        toast = Toast.makeText(getApplicationContext(),
                                "Обновлена Заметка!", Toast.LENGTH_SHORT);
                        toast.show();

                        cv.put("id", id);
                        cv.put("name", name);
                        cv.put("email", tip);

                        Cursor c = db.query("mytable", null, null, null, null, null, null);

                        if (c.moveToFirst()) {

                            String nowid = c.getString(c.getColumnIndex("id"));
                            String nowname = c.getString(c.getColumnIndex("name"));

                            do {
                                if (nowname == name) {
                                    int updCount = db.update("mytable", cv, "id = ?",
                                            new String[]{nowid});
                                }

                            } while (c.moveToNext());
                            int updCount = db.update("mytable", cv, "id = ?",
                                    new String[]{v.getText().toString()});
                        }
                    }
                }
                if (type == "read") {
                    String returningid;
                    String returningname;
                    String returningtip;

                    String pressedid;

                    toast = Toast.makeText(getApplicationContext(),
                                "Загружена Заметка!", Toast.LENGTH_SHORT);
                    toast.show();
                    Cursor c = db.query("mytable", null, null, null, null, null, null);

                    if (c.moveToFirst()) {

                        returningid = c.getString(c.getColumnIndex("id"));
                        returningname = c.getString(c.getColumnIndex("name"));
                        returningtip = c.getString(c.getColumnIndex("email"));

                        do {
                            if (returningname == v.getText()) {
                                Intent intent = new Intent();
                                intent.putExtra("name", returningname);
                                intent.putExtra("id", returningid);
                                intent.putExtra("tip", returningtip);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        } while (c.moveToNext());
                    } else
                        c.close();
                    //TODO break;
                }
                    //Log.d(LOG_TAG, "itemSelect: position = " + position + ", id = "
                    //        + id);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onClick(View v) {
        ContentValues cv = new ContentValues();

        Toast toast;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (type == "add") {
            toast = Toast.makeText(getApplicationContext(),
                    "Добавлена Заметка!", Toast.LENGTH_SHORT);
            toast.show();

            cv.put("id", id);
            cv.put("name", name);
            cv.put("email", tip);
            long rowID = db.insert("mytable", null, cv);
        }
        //if (type == "read") {
            //TODO
        //}
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            //Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "email text" + ");");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

