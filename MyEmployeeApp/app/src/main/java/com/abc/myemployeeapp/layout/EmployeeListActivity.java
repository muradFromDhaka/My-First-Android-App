package com.abc.myemployeeapp.layout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.myemployeeapp.R;
import com.abc.myemployeeapp.adapter.EmployeeAdapter;
import com.abc.myemployeeapp.db.EmployeeDao;
import com.abc.myemployeeapp.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    EmployeeDao employeeDao;

    List<Employee> employeeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        employeeDao = new EmployeeDao(this);

        // 🔹 Init adapter ONCE
        adapter = new EmployeeAdapter(this, employeeList, this::showActionDialog);
        recyclerView.setAdapter(adapter);

        loadEmployees();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEmployees(); // refresh when coming back
    }

    // 🔹 Reload data only
    private void loadEmployees() {
        employeeList.clear();
        employeeList.addAll(employeeDao.getAllEmployees());
        adapter.notifyDataSetChanged();
    }

    // 🔹 Update / Delete dialog
    private void showActionDialog(Employee employee) {

        String[] options = {"Update", "Delete"};

        new AlertDialog.Builder(this)
                .setTitle("Select Action")
                .setItems(options, (dialog, which) -> {

                    if (which == 0) {
                        // ✏️ Update
                        Intent intent =
                                new Intent(this, EmployeeFormActivity.class);
                        intent.putExtra("id", employee.getId());
                        startActivity(intent);

                    } else {
                        // 🗑 Delete
                        new AlertDialog.Builder(this)
                                .setTitle("Delete Employee")
                                .setMessage("Are you sure you want to delete?")
                                .setPositiveButton("Yes", (d, w) -> {
                                    employeeDao.deleteEmployee(employee.getId());
                                    Toast.makeText(
                                            this,
                                            "Employee Deleted",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                    loadEmployees();
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                })
                .show();
    }
}
