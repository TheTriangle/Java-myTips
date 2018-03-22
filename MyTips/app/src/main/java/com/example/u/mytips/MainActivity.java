package com.example.u.mytips;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.u.mytips.R;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final String LOG_TAG = "myLogs";

    DBHelper dbHelper;

    private EditText TipText;
    private EditText NameText;


    Button ButtonSave = (Button) findViewById(R.id.buttonSave);

    Button ButtonLoad = (Button) findViewById(R.id.buttonLoad);

    Button ButtonSaveAs = (Button) findViewById(R.id.buttonSaveAs);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView myTextView = (TextView)findViewById(R.id.textTip);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        ButtonSave.setBackgroundColor(getResources().getColor(R.color.colorSave));

        ButtonLoad.setBackgroundColor(getResources().getColor(R.color.colorLoad));

        ButtonSaveAs.setBackgroundColor(getResources().getColor((R.color.colorSave)));

        TipText = (EditText) findViewById(R.id.textTip);
        NameText = (EditText) findViewById(R.id.Name);

        //TipText.setText(R.string.SavedTip);

        ButtonSave.setOnClickListener(this);
        ButtonLoad.setOnClickListener(this);
        ButtonSaveAs.setOnClickListener(this);
    }


    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = NameText.getText().toString();
        String tip = TipText.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Toast toast = Toast.makeText(getApplicationContext(),
                "заметка сохранена!", Toast.LENGTH_SHORT);

        switch (v.getId()) {
            case R.id.buttonSaveAs:
                // подготовим данные для вставки в виде пар: наименование столбца -
                // значение
                cv.put("name", name);
                cv.put("tip", tip);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);

                toast = Toast.makeText(getApplicationContext(),
                        "Заметка сохранена!", Toast.LENGTH_SHORT);
                toast.show();

                break;
            case R.id.buttonLoad:
                Cursor c = db.query("mytable", null, null, null, null, null, null);

                if (c.moveToFirst()) {
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int tipColIndex = c.getColumnIndex("tip");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) + ", name = "
                                        + c.getString(nameColIndex) + ", tip = "
                                        + c.getString(tipColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false -
                        // выходим из цикла
                        if (c.getString(nameColIndex) == name) {
                            TipText.setText(c.getString(tipColIndex));
                        }
                    } while (c.moveToNext());

                    toast = Toast.makeText(getApplicationContext(),
                            "Загружена заметка!", Toast.LENGTH_SHORT);
                    toast.show();

                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                break;
            case R.id.buttonSave:
                if (name.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(LOG_TAG, "--- Update mytable: ---");
                // подготовим значения для обновления
                cv.put("name", name);
                cv.put("tip", tip);
                // обновляем по id
                int updCount = db.update("mytable", cv, "id = ?",
                        new String[] { name });
                Log.d(LOG_TAG, "updated rows count = " + updCount);
                toast = Toast.makeText(getApplicationContext(),
                        "Заметка сохранена!", Toast.LENGTH_SHORT);
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }



    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "tip text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

