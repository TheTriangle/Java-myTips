package com.example.u.myapplicationtips;

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

import com.example.u.myapplicationtips.R;

import java.util.ArrayList;

public class TipSelectActivity extends Activity implements View.OnClickListener {
    DBHelper dbHelper;
    //LinearLayout linLayout;
    LayoutInflater ltInflater;

    //String[] names;
    //String[] ids;
    //String[] tips;

    ListView lvMain;

    ArrayList<Tip> products = new ArrayList<Tip>();
    TipAdapter boxAdapter;

    Button btnDelete;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        dbHelper = new DBHelper(this);
        fillData();
        boxAdapter = new TipAdapter(this, products);

        // настраиваем список
        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);

        btnDelete = (Button) findViewById(R.id.btnDel);
        btnDelete.setOnClickListener(this);


        //ltInflater = getLayoutInflater();
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast toast;
                toast = Toast.makeText(getApplicationContext(),
                        "Заметка записана!", Toast.LENGTH_SHORT);
                toast.show();
                Tip clickedtip = (Tip)parent.getItemAtPosition(position);
                ContentValues cv = new ContentValues();

                Intent intent = new Intent();
                intent.putExtra("name", clickedtip.name);
                intent.putExtra("tip", clickedtip.tip);
                intent.putExtra("id", clickedtip.id);
                setResult(RESULT_OK, intent);
                finish();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fillData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        //products.add(new Tip(0, "", "", false));
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int tipColIndex = c.getColumnIndex("tip");

            do {
                products.add(new Tip(c.getInt(idColIndex), c.getString(nameColIndex), c.getString(tipColIndex), false, false));
            } while (c.moveToNext());
        } else
            c.close();
    }

    public void onClick(View v) {
        ContentValues cv = new ContentValues();

        Toast toast;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch(v.getId()) {
            case R.id.btnDel:
                toast = Toast.makeText(getApplicationContext(),
                        "Заметки Удалены!", Toast.LENGTH_SHORT);
                toast.show();
                ArrayList<Tip> box = boxAdapter.getBox();
                // удаляем по id
                for (int i = 0; i < box.size(); i++) {
                    int delCount = db.delete("mytable", "id = " + box.get(i).id, null);
                }
                fillData();
                break;
        }
        //if (type == "read") {
        //TODO
        //}*/
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
    /*class DBHelper extends SQLiteOpenHelper {

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
                    + "tip text" + ");");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }//
    }//*/


