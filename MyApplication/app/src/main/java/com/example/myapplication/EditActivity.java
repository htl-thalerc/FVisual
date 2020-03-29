package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.bll.Mitglied;
import com.example.myapplication.database.DatabaseManager;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class EditActivity extends AppCompatActivity implements Serializable {
    DatabaseManager db;
    Mitglied currentMitglied;
    EditText usernameText;
    EditText passwordText;
    Button generatePassword;
    Button save;
    Button cancel;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        currentMitglied = (Mitglied) getIntent().getSerializableExtra("serialzable");
        db = DatabaseManager.newInstance();
        usernameText = findViewById(R.id.txtusername);
        passwordText = findViewById(R.id.txtPassword);
        generatePassword = findViewById(R.id.buttonGenerate);
        save = findViewById(R.id.btnSave);
        cancel = findViewById(R.id.btnCancel);
        checkBox = findViewById(R.id.checkbox);
        usernameText.setText(currentMitglied.getUsername());
        passwordText.setText(currentMitglied.getPassword());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void clickedGenerate(View view) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        Random rand = new Random();

        for(int i = 0; i < 8; i++){
            char c = chars[rand.nextInt(chars.length)];
            stringBuilder.append(c);
        }

        passwordText.setText(stringBuilder.toString());
    }

    public void cancel(View view) {
        Intent send = new Intent(EditActivity.this, MapsActivity.class);
        startActivity(send);
        finish();
    }

    public void save(View view) throws ExecutionException, InterruptedException {
        currentMitglied.setUsername(usernameText.getText().toString());
        currentMitglied.setPassword(passwordText.getText().toString());

        db.UpdateMitglied(currentMitglied);
        System.out.println(currentMitglied);
        Intent send = new Intent(EditActivity.this, MapsActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("serialzable", currentMitglied);
        send.putExtras(b);
        startActivity(send);
        finish();
    }


}
