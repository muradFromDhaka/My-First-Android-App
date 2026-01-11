package com.abc.myfirstandroidapp.layout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.abc.myfirstandroidapp.R;
import com.abc.myfirstandroidapp.db.UserDao;
import com.abc.myfirstandroidapp.entity.User;
import com.google.android.material.snackbar.Snackbar;

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
                Arrays.asList("Bangladesh", "Pakisthan", "USA", "UK"));
        rCountry.setAdapter(adapter);


        //NEW-------------
        UserDao userDao = new UserDao(this);
        long userId = getIntent().getLongExtra("userId", -1);
        User editingUser = null;

        // ---------------- PREFILL IF UPDATE ----------------
        if (userId != -1) {
            editingUser = userDao.getUserById(userId);

            rName.setText(editingUser.getName());
            rEmail.setText(editingUser.getEmail());
            rPass.setText(editingUser.getPassword());
            rPhone.setText(editingUser.getPhone());

            // Gender
            for (int i = 0; i < rGender.getChildCount(); i++) {
                RadioButton rb = (RadioButton) rGender.getChildAt(i);
                if (rb.getText().toString().equals(editingUser.getGender())) {
                    rb.setChecked(true);
                    break;
                }
            }

            // DOB
            String[] dobParts = editingUser.getDob().split("/");
            rDate.updateDate(Integer.parseInt(dobParts[2]), Integer.parseInt(dobParts[1]) - 1, Integer.parseInt(dobParts[0]));

            // Country
            int pos = adapter.getPosition(editingUser.getCountry());
            rCountry.setSelection(pos);

            // Skills
            String[] skills = editingUser.getSkills().split(",");
            rJava.setChecked(Arrays.asList(skills).contains("Java"));
            rAndroid.setChecked(Arrays.asList(skills).contains("Android"));
            rFlutter.setChecked(Arrays.asList(skills).contains("Flutter"));

            // Terms
            rTerm.setChecked(editingUser.isTermsAccepted());

            rButton.setText("Update");
        }


        //NEW-------------
        rButton.setOnClickListener(view -> {

            String name = rName.getText().toString();
            String email = rEmail.getText().toString();
            String password = rPass.getText().toString();
            String phone = rPhone.getText().toString();

            int selectedGenderId = rGender.getCheckedRadioButtonId();
            String gender = selectedGenderId == -1 ? "Not Selected" : ((RadioButton) findViewById(selectedGenderId)).getText().toString();

            String dob = rDate.getDayOfMonth() + "/" + (rDate.getMonth() + 1) + "/" + rDate.getYear();

            String country = rCountry.getSelectedItem().toString();

            List<String> skills = new ArrayList<>();
            if (rJava.isChecked()) {
                skills.add("Java");
            }
            if (rAndroid.isChecked()) skills.add("Android");
            if (rFlutter.isChecked()) skills.add("Flutter");
            if (rAngular.isChecked()) skills.add("Angular");
            boolean acceptedTerms = rTerm.isChecked();

            String skillString = skills.isEmpty() ? "" : String.join(",", skills);

            User user = new User(
                    name,
                    email,
                    password,
                    phone,
                    gender,
                    dob,
                    country,
                    skillString,
                    acceptedTerms);

            if (userId != -1) {
                user.setId(userId);
                boolean updated = userDao.updateUser(userId, user) != 0;
                if (updated) {
                    Toast.makeText(this, "User Updated!", Toast.LENGTH_SHORT).show();
//                    finish();
                    startActivity(new Intent(this, UserListActivity.class));

                } else {
                    Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            } else {
                long id = userDao.insertUser(user);
                Toast.makeText(this, "Saved! ID: " + id, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, UserListActivity.class));
            }
        });

    }

    // 1️⃣ Toast
    public void showToast(View view) {
        Toast.makeText(this, "This is Toast", Toast.LENGTH_SHORT).show();
    }

    // 2️⃣ Snackbar
    public void showSnackbar(View view) {
        Snackbar snackbar = Snackbar.make(view, "This is Snackbar", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", v -> Toast.makeText(this, "Undo Clicked", Toast.LENGTH_SHORT).show());
        snackbar.setBackgroundTint(Color.BLACK);
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    // 3️⃣ AlertDialog (Modal)
    public void showAlert(View view) {
        new AlertDialog.Builder(this).setTitle("Delete Item").setMessage("Are you sure?").setPositiveButton("Yes", (d, w) -> Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()).setNegativeButton("No", null).show();
    }

    // 4️⃣ Custom Dialog
//    public void showCustomDialog(View view) {
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_custom);
//        dialog.setCancelable(false);
//
//        Button close = dialog.findViewById(R.id.btnClose);
//        close.setOnClickListener(v -> dialog.dismiss());
//
//        dialog.show();
//    }

    // 5️⃣ Bottom Sheet
//    public void showBottomSheet(View view) {
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
//        View sheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, null);
//        bottomSheetDialog.setContentView(sheetView);
//        bottomSheetDialog.show();
//    }


//            Toast.makeText(this, value, Toast.LENGTH_LONG).show();


//            new AlertDialog.Builder(this)
//                    .setTitle("Form Data")
//                    .setMessage(value)
//                    .setPositiveButton("OK", null)
//                    .show();

//            new AlertDialog.Builder(this)
//                    .setTitle("Delete Item")
//                    .setMessage("Are you sure?")
//                    .setPositiveButton("Yes", (d, w) -> {
//                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
//                    }).setNegativeButton("No", (d, w) -> {
//                        Toast.makeText(this, "Not Deleted", Toast.LENGTH_SHORT).show();
//                    }).setNeutralButton("X",(d, w) -> {
//                        Toast.makeText(this, "Neutral", Toast.LENGTH_SHORT).show();
//                    }).show();


//            Snackbar snackbar = Snackbar.make(view, value, Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", v -> Toast.makeText(this, "Undo Clicked", Toast.LENGTH_SHORT).show());
//            snackbar.setBackgroundTint(Color.BLACK);
//            snackbar.setTextColor(Color.WHITE);
//            snackbar.show();

//    }


}