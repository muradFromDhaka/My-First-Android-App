package com.abc.myfirstandroidapp.layout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abc.myfirstandroidapp.R;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText lName = findViewById(R.id.lName);
        EditText lPass = findViewById(R.id.lPass);
        Button lLogin = findViewById(R.id.lLogin);
        TextView lAccount = findViewById(R.id.lAccount);



        lLogin.setOnClickListener(v -> {
             String name = lName.getText().toString().trim();
             String pass = lPass.getText().toString().trim();
            String value = "Name: " + name + ", Password: " + pass;

            if(name.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Please fill up all filed", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
            }

        });

        lAccount.setOnClickListener(v->{
            Intent intent = new Intent(this, FormActivity.class);
            startActivity(intent);
        });


    }
}