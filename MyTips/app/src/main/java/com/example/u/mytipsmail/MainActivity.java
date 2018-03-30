package com.example.u.mytipsmail;

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
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    //final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
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

        etName = (EditText) findViewById(R.id.etName);
        etTip = (EditText) findViewById(R.id.etTip);
        etID = (EditText) findViewById(R.id.etID);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }

    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String Tip = etTip.getText().toString();
        String id = etID.getText().toString();

        Toast toast = Toast.makeText(getApplicationContext(),
                "Пора покормить кота!", Toast.LENGTH_SHORT);
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Intent intent = new Intent(this, tipselectActivity.class);

        switch (v.getId()) {
            case R.id.btnAdd:

                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.putExtra("Type", "add");
                intent.putExtra("tip", Tip);

                startActivityForResult(intent, 1);

                break;
            case R.id.btnRead:
                //toast = Toast.makeText(getApplicationContext(),
                //        "Загружена Заметка!", Toast.LENGTH_SHORT);
                //toast.show();
                //Cursor c = db.query("mytable", null, null, null, null, null, null);

                //if (c.moveToFirst()) {

                    //int idColIndex = c.getColumnIndex("id");
                    //int nameColIndex = c.getColumnIndex("name");
                    //int TipColIndex = c.getColumnIndex("Tip");

                    //do {
                    //    if (idColIndex == Integer.parseInt(id)) {
                    //        etName.setText(c.getString(nameColIndex));
                    //       etTip.setText(c.getString(TipColIndex));

                            //intent.putExtra("name", name);
                            //intent.putExtra("id", id);
                            //intent.putExtra("Type", "read");
                            //intent.putExtra("tip", Tip);

                            //startActivity(intent);
                    //    }
                    //} while (c.moveToNext());
                //} else
                //    c.close();
                break;
            case R.id.btnClear:
                toast = Toast.makeText(getApplicationContext(),
                        "Очищена Заметка!", Toast.LENGTH_SHORT);
                toast.show();
                int clearCount = db.delete("mytable", null, null);
                break;
            case R.id.btnUpd:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                toast = Toast.makeText(getApplicationContext(),
                        "Обновлена Заметка!", Toast.LENGTH_SHORT);
                toast.show();

                cv.put("id", id);
                cv.put("name", name);
                cv.put("Tip", Tip);
                int updCount = db.update("mytable", cv, "id = ?",
                        new String[] { id });
                break;
            case R.id.btnDel:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                toast = Toast.makeText(getApplicationContext(),
                        "Заметка Удалена!", Toast.LENGTH_SHORT);
                toast.show();
                // удаляем по id
                int delCount = db.delete("mytable", "id = " + id, null);
                break;
        }
        dbHelper.close();
    }

    @Override
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
                    + "Tip text" + ");");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}