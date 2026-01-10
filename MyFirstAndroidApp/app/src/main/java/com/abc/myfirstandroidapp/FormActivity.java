package com.abc.myfirstandroidapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form);

        EditText rName = findViewById(R.id.rName);
        EditText rPass = findViewById(R.id.rPass);
        EditText rEmail = findViewById(R.id.rEmail);
        EditText rPhone = findViewById(R.id.rPhone);
        RadioGroup rGender = findViewById(R.id.rGender);
        DatePicker rDate = findViewById(R.id.rDate);
        Spinner rCountry = findViewById(R.id.rCountry);
        CheckBox rJava = findViewById(R.id.rJava);
        CheckBox rAndroid = findViewById(R.id.rAndroid);
        CheckBox rFlutter = findViewById(R.id.rFlutter);
        CheckBox rAngular = findViewById(R.id.rAngular);
        CheckBox rTerm = findViewById(R.id.rTerm);
        Button rButton = findViewById(R.id.rButton);
        TextView rLogin = findViewById(R.id.rLogin);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                Arrays.asList("Bangladesh","Pakisthan","USA","UK"));
        rCountry.setAdapter(adapter);

        rButton.setOnClickListener(v -> {
            String name = rName.getText().toString();
            String email = rEmail.getText().toString();
            String pass = rPass.getText().toString();
            String phone = rPhone.getText().toString();

            int selectedGenderId = rGender.getCheckedRadioButtonId();
            String gender = selectedGenderId == -1 ?
                    "Not selected" : ((RadioButton) findViewById(selectedGenderId)).getText().toString();

            String dob = rDate.getDayOfMonth()+"/" + rDate.getMonth()+1+ "/" + rDate.getYear();

            List<String> skills = new ArrayList<>();
            if(rJava.isChecked()) skills.add("Java");
            if(rAndroid.isChecked()) skills.add("Android");
            if(rFlutter.isChecked()) skills.add("Flutter");
            if(rAngular.isChecked()) skills.add("Angular");

            boolean term = rTerm.isChecked();

            String value = "Name:" + name +"\n"
                    + "Email: " +email+ "\n"
                    + "Password: " +pass+ "\n"
                    + "Phone: " + phone+ "\n"
                    + "gender: " + gender+ "\n"
                    + "skills: " + skills + "\n"
                    + "Terms accepted: " + (term ? "Yes" : "No");

            //Show AlertDialog;
            new AlertDialog.Builder(this)
                    .setTitle("Form Data")
                    .setMessage(value)
                    .setPositiveButton("OK", null)
                    .show();

        });

        rLogin.setOnClickListener(v ->{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });


    }
}