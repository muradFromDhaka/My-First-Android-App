package com.abc.myfirstchatapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.abc.myfirstchatapp.R;
import com.abc.myfirstchatapp.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ListView listUsers;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // Initialize views
        listUsers = findViewById(R.id.listUsers);
        btnLogout = findViewById(R.id.btnLogout);

        auth = FirebaseAuth.getInstance();

        // 🔐 Check if user is logged in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            openLogin();
            return;
        }

        // Setup logout
        btnLogout.setOnClickListener(v -> logout());

        // Setup adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>());
        listUsers.setAdapter(adapter);

        // Load users from Firebase
        loadUsers(currentUser.getUid());

        // Click user to open chat
        listUsers.setOnItemClickListener((parent, view, position, id) -> {
            User selectedUser = users.get(position);
            Intent in = new Intent(UserListActivity.this, ChatActivity.class);
            in.putExtra("uid", selectedUser.uid);
            in.putExtra("email", selectedUser.email);
            in.putExtra("name", selectedUser.name); // optional if you want to show in chat
            startActivity(in);
        });
    }

    private void loadUsers(String currentUid) {
        FirebaseDatabase.getInstance().getReference("users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users.clear();
                        adapter.clear();

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            User u = ds.getValue(User.class);
                            if (u != null && !u.uid.equals(currentUid)) {
                                users.add(u);
                                adapter.add(u.name != null ? u.name : u.email); // show name if available
                            }
                        }
                        adapter.notifyDataSetChanged();

                        if (users.isEmpty()) {
                            Toast.makeText(UserListActivity.this, "No other users found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UserListActivity.this, "Failed to load users: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void logout() {
        auth.signOut();
        openLogin();
    }

    private void openLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
