package com.example.u.mytips;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.u.mytips.R;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView myTextView = (TextView)findViewById(R.id.textTip);
        setContentView(R.layout.activity_main);

        Button ButtonSave = (Button) findViewById(R.id.buttonSave);
        Button ButtonLoad = (Button) findViewById(R.id.buttonLoad);
        final EditText TipText = (EditText) findViewById(R.id.textTip);
        final EditText NameText = (EditText) findViewById(R.id.Name);

        //TipText.setText(R.string.SavedTip);

        System.out.println(TipText.getText());

        View.OnClickListener oclBtnSaveLoad = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.buttonSave) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Заметка сохранена (нет)", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Загружена заметка (нет)", Toast.LENGTH_SHORT);
                    toast.show();
                    TipText.setText(R.string.SavedTip);
                }
                getResources().getString(R.string.tvBottomText);
            }
        };

        ButtonSave.setOnClickListener(oclBtnSaveLoad);
        ButtonLoad.setOnClickListener(oclBtnSaveLoad);
    }
}

