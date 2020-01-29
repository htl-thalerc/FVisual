package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.bll.Mitglied;
import com.example.myapplication.database.DatabaseManager;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity implements Serializable {
    DatabaseManager db;
    Button loginButton;
    EditText usernameText;
    EditText passwordText;
    Mitglied currentMitglied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = DatabaseManager.newInstance("http://192.168.193.84:3030");

        loginButton = findViewById(R.id.login);
        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
    }

    public void clickedLogin(View view){
        for (Mitglied mitglied : db.getAllMitglieder()) {
            if (mitglied.getUsername().equals(usernameText.getText().toString()) && mitglied.getPassword().equals(passwordText.getText().toString())) {
                currentMitglied = mitglied;
            }
        }
        Intent send = new Intent(LoginActivity.this,MapsActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("serialzable", currentMitglied);
        send.putExtras(b);
        startActivity(send);
        finish();
    }
}
