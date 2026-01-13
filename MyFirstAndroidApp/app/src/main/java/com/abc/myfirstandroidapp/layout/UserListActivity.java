package com.abc.myfirstandroidapp.layout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abc.myfirstandroidapp.R;
import com.abc.myfirstandroidapp.adapters.UserAdapter;
import com.abc.myfirstandroidapp.db.UserDao;
import com.abc.myfirstandroidapp.entity.User;

import java.util.List;

public class UserListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    UserAdapter adapter;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userDao = new UserDao(this);

        loadUsers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUsers(); // This refreshes when returning from FormActivity
    }
    private void loadUsers() {
        List<User> userList = userDao.getAllUser();

        adapter = new UserAdapter(this, userList, user -> {
            String[] options = {"Update", "Delete"};
            new android.app.AlertDialog.Builder(UserListActivity.this)
                    .setTitle("Select Action")
                    .setItems(options, (dialog,which) -> {
                        if (which == 0) {
                            // Update - just startActivity normally
                            Intent intent = new Intent(UserListActivity.this, FormActivity.class);
                            intent.putExtra("userId", user.getId());
                            startActivity(intent); // Regular startActivity
                        } else if (which == 1) {
                            // Delete
                            new AlertDialog.Builder(UserListActivity.this)
                                    .setTitle("Delete User")
                                    .setMessage("Are you sure?")
                                    .setPositiveButton("Yes", (d, w) -> {
                                        userDao.deleteUser(user.getId());
                                        Toast.makeText(UserListActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                        loadUsers(); // Refresh after delete
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                    }).show();
        });

        recyclerView.setAdapter(adapter);
    }


}
