package com.abc.myfirstandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abc.myfirstandroidapp.layout.LoginActivity;
import com.abc.myfirstandroidapp.layout.UserListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button hLogin = findViewById(R.id.mLogin);
        Button mList = findViewById(R.id.mList);
         hLogin.setOnClickListener(v ->{
             Intent intent = new Intent(this, LoginActivity.class);
             startActivity(intent);
         });


        mList.setOnClickListener(v ->{
            Intent intent = new Intent(this, UserListActivity.class);
            startActivity(intent);
        });
    }
}