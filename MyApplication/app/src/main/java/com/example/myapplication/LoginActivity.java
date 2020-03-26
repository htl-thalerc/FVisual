package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import java.util.List;

public class LoginActivity extends AppCompatActivity implements Serializable {
    DatabaseManager db;
    Button loginButton;
    EditText usernameText;
    EditText passwordText;
    Mitglied currentMitglied;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = DatabaseManager.newInstance();

        loginButton = findViewById(R.id.login);
        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkbox);

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




    public void clickedLogin(View view) {
        List<Mitglied> mitgliedList;
        mitgliedList = db.getAllMitglieder();
        if (mitgliedList.size() == 0) {
            Toast.makeText(getApplicationContext(), "Connection to Server failed!", Toast.LENGTH_SHORT).show();
        } else {
            for (Mitglied mitglied : mitgliedList) {
                if (mitglied.getUsername().equals(usernameText.getText().toString()) && mitglied.getPassword().equals(passwordText.getText().toString())) {
                    currentMitglied = mitglied;
                }
            }
            if (currentMitglied == null) {
                Toast.makeText(getApplicationContext(), "Login not successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                Intent send = new Intent(LoginActivity.this, MapsActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("serialzable", currentMitglied);
                send.putExtras(b);
                startActivity(send);
                finish();
            }
        }
    }
}
