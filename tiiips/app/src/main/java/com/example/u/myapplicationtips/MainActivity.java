package com.example.u.myapplicationtips;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.u.myapplicationtips.R;


public class MainActivity extends Activity implements OnClickListener {

    //final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    ImageButton btnRight, btnLeft, btnMenu;
    EditText etName, etTip, etID;

    DBHelper dbHelper;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnAdd.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);
        btnRead.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnClear.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);
        btnUpd.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);
        btnDel.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        btnRight = (ImageButton) findViewById(R.id.btnRight);
        btnRight.setOnClickListener(this);

        btnLeft = (ImageButton) findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(this);

        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etTip = (EditText) findViewById(R.id.etTip);
        etID = (EditText) findViewById(R.id.etID);

        etTip.setText ("hello");
        etName.setText ("myname");
        etID.setText ("0");
        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }

    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String tip = etTip.getText().toString();
        int id = Integer.parseInt(etID.getText().toString());

        Toast toast = Toast.makeText(getApplicationContext(),
                "Пора покормить кота!", Toast.LENGTH_SHORT);
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Intent intent = new Intent(this, TipSelectActivity.class);

        switch (v.getId()) {
            case R.id.btnAdd:
                toast = Toast.makeText(getApplicationContext(),
                        "Загружена Заметка!", Toast.LENGTH_SHORT);
                toast.show();
                cv.put("tip", tip);
                cv.put("name", name);
                cv.put("id", id);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                break;
            case R.id.btnRead:
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                //toast = Toast.makeText(getApplicationContext(),
                //        "Загружена Заметка!", Toast.LENGTH_SHORT);
                //toast.show();
                Cursor c = db.query("mytable", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int tipColIndex = c.getColumnIndex("tip");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        toast = Toast.makeText(getApplicationContext(),
                                c.getString(nameColIndex), Toast.LENGTH_SHORT);
                        toast.show();
                        if (c.getInt(idColIndex) == id) {
                            //toast = Toast.makeText(getApplicationContext(),
                            //        c.getString(tipColIndex), Toast.LENGTH_SHORT);
                            //toast.show();

                            etID.setText(String.valueOf(c.getInt(idColIndex)));
                            etName.setText(c.getString(nameColIndex));
                            etTip.setText(c.getString(tipColIndex));
                            }
                        // а если следующей нет (текущая - последняя), то false -
                        // выходим из цикла
                    } while (c.moveToNext());
                } else
                    c.close();
                break;
            case R.id.btnRight:
                toast = Toast.makeText(getApplicationContext(),
                        "Загружена Заметка!", Toast.LENGTH_SHORT);
                toast.show();
                c = db.query("mytable", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int tipColIndex = c.getColumnIndex("tip");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        if (c.getInt(idColIndex) == id + 1) {
                            etID.setText(String.valueOf(c.getInt(idColIndex)));
                            etName.setText(c.getString(nameColIndex));
                            etTip.setText(c.getString(tipColIndex));
                        }
                    } while (c.moveToNext());
                } else
                    c.close();
                break;
            case R.id.btnLeft:
                toast = Toast.makeText(getApplicationContext(),
                        "Загружена Заметка!", Toast.LENGTH_SHORT);
                toast.show();
                c = db.query("mytable", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int tipColIndex = c.getColumnIndex("tip");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        if (c.getInt(idColIndex) == id - 1) {
                            etID.setText(String.valueOf(c.getInt(idColIndex)));
                            etName.setText(c.getString(nameColIndex));
                            etTip.setText(c.getString(tipColIndex));
                        }
                    } while (c.moveToNext());
                } else
                    c.close();
                break;
            case R.id.btnClear:
                toast = Toast.makeText(getApplicationContext(),
                        "Очищена Заметка!", Toast.LENGTH_SHORT);
                toast.show();
                int clearCount = db.delete("mytable", null, null);
                break;
            case R.id.btnUpd:
                toast = Toast.makeText(getApplicationContext(),
                        "Обновлена Заметка!", Toast.LENGTH_SHORT);
                toast.show();

                cv.put("id", id);
                cv.put("name", name);
                cv.put("tip", tip);
                int updCount = db.update("mytable", cv, "id = ?",
                        new String[] { String.valueOf(id) });
                break;
            case R.id.btnDel:
                toast = Toast.makeText(getApplicationContext(),
                    "Заметка Удалена!", Toast.LENGTH_SHORT);
                toast.show();
                // удаляем по id
                int delCount = db.delete("mytable", "id = " + id, null);
            break;
            case R.id.btnMenu:
                Intent intent = new Intent(this, TipSelectActivity.class);

                //intent.putExtra("name", name);
                //intent.putExtra("id", id);
                //intent.putExtra("type", "add");
                //intent.putExtra("tip", tip);
                startActivityForResult(intent, 1);
                //startActivityForResult(intent, 1);
        }
        dbHelper.close();
    }

    //@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String retname = data.getStringExtra("name");
        String rettip = data.getStringExtra("tip");
        String retid = data.getStringExtra("id");
        etName.setText(retname);
        etTip.setText(rettip);
        etTip.setText(retid);
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
                    + "tip text" + ");");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}