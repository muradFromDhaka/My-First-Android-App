package com.abc.myemployeeapp.layout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.abc.myemployeeapp.R;
import com.abc.myemployeeapp.db.EmployeeDao;
import com.abc.myemployeeapp.entity.Employee;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EmployeeFormActivity extends AppCompatActivity {

    EditText etName, etEmail, etPhone, etAge, etSalary;
    CheckBox etJava, etFlutter, etAndroid, etAngular;
    Spinner etDepartment, etActivity;
    Button btnSave, btnUploadImage;
    ImageView ivProfile;

    EmployeeDao employeeDao;
    long employeeId = -1;

    String selectedImagePath = null;

    ActivityResultLauncher<Intent> cameraLauncher;
    ActivityResultLauncher<Intent> galleryLauncher;
    ActivityResultLauncher<String> cameraPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_form);

        initViews();
        initSpinners();
        initLaunchers();

        employeeDao = new EmployeeDao(this);

        // 🔹 Add this: check storage/gallery permission
        checkStoragePermission();



        // 🔹 Update mode check
        if (getIntent() != null && getIntent().hasExtra("id")) {
            employeeId = getIntent().getLongExtra("id", -1);
            loadEmployee(employeeId);
            btnSave.setText("Update Employee");
        }

        btnUploadImage.setOnClickListener(v -> showImageChooser());
        btnSave.setOnClickListener(v -> saveEmployee());
    }

    // ================= INIT =================
    private void initViews() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAge = findViewById(R.id.etAge);
        etSalary = findViewById(R.id.etSalary);

        etJava = findViewById(R.id.etJava);
        etFlutter = findViewById(R.id.etFlutter);
        etAndroid = findViewById(R.id.etAndroid);
        etAngular = findViewById(R.id.etAngular);

        etDepartment = findViewById(R.id.etDepartment);
        etActivity = findViewById(R.id.etActivity);

        btnSave = findViewById(R.id.btnSave);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        ivProfile = findViewById(R.id.ivProfile);
    }

    private void initSpinners() {
        etDepartment.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                Arrays.asList("HR", "Finance", "Marketing", "IT", "R&D")
        ));

        etActivity.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                Arrays.asList("Active", "Inactive")
        ));
    }

    // ================= SAVE / UPDATE =================
    private void saveEmployee() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Name & Email required", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = etAge.getText().toString().isEmpty() ? 0 :
                Integer.parseInt(etAge.getText().toString());

        double salary = etSalary.getText().toString().isEmpty() ? 0 :
                Double.parseDouble(etSalary.getText().toString());

        List<String> skills = new ArrayList<>();
        if (etJava.isChecked()) skills.add("Java");
        if (etAngular.isChecked()) skills.add("Angular");
        if (etFlutter.isChecked()) skills.add("Flutter");
        if (etAndroid.isChecked()) skills.add("Android");

        String skillString = skills.isEmpty() ? "" : String.join(",", skills);

        long joiningDate = employeeId == -1
                ? System.currentTimeMillis()
                : employeeDao.getEmployeeById(employeeId).getJoiningDate();

        Employee emp = new Employee(
                name,
                email,
                etPhone.getText().toString(),
                age,
                salary,
                etActivity.getSelectedItem().toString(),
                joiningDate,
                etDepartment.getSelectedItem().toString(),
                skillString
        );

        emp.setImagePath(selectedImagePath);

        if (employeeId == -1) {
            employeeDao.insertEmployee(emp);
            Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
        } else {
            emp.setId(employeeId);
            employeeDao.updateEmployee(emp);
            Toast.makeText(this, "Employee Updated", Toast.LENGTH_SHORT).show();
        }

        startActivity(new Intent(this, EmployeeListActivity.class));
        finish();
    }

    // ================= LOAD EMPLOYEE =================
    private void loadEmployee(long id) {
        Employee e = employeeDao.getEmployeeById(id);
        if (e == null) return;

        etName.setText(e.getName());
        etEmail.setText(e.getEmail());
        etPhone.setText(e.getPhone());
        etAge.setText(String.valueOf(e.getAge()));
        etSalary.setText(String.valueOf(e.getSalary()));

        setSpinner(etDepartment, e.getDepartment());
        setSpinner(etActivity, e.getActive());

        if (e.getSkills() != null) {
            etJava.setChecked(e.getSkills().contains("Java"));
            etAngular.setChecked(e.getSkills().contains("Angular"));
            etFlutter.setChecked(e.getSkills().contains("Flutter"));
            etAndroid.setChecked(e.getSkills().contains("Android"));
        }

        selectedImagePath = e.getImagePath();
        if (selectedImagePath != null) {
            ivProfile.setImageURI(Uri.parse(selectedImagePath));
        }
    }

    private void setSpinner(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        spinner.setSelection(adapter.getPosition(value));
    }

    // ================= IMAGE =================
    private void showImageChooser() {
        String[] options = {"Camera", "Gallery", "Remove"};
        new AlertDialog.Builder(this)
                .setItems(options, (d, w) -> {
                    if (w == 0) requestCamera();
                    else if (w == 1) openGallery();
                    else removeImage();
                }).show();
    }

    private void openGallery() {
        galleryLauncher.launch(
                new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        );
    }

    private void requestCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void openCamera() {
        cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    private void removeImage() {
        selectedImagePath = null;
        ivProfile.setImageResource(R.drawable.image_24);
    }

    private void initLaunchers() {

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                r -> {
                    if (r.getResultCode() == RESULT_OK && r.getData() != null) {
                        Bitmap bmp = (Bitmap) Objects.requireNonNull(r.getData().getExtras()).get("data");
                        ivProfile.setImageBitmap(bmp);
                        selectedImagePath = bitmapToUri(bmp).toString();
                    }
                }
        );

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                r -> {
                    if (r.getResultCode() == RESULT_OK && r.getData() != null) {
                        Uri uri = r.getData().getData();
                        selectedImagePath = uri.toString();
                        ivProfile.setImageURI(uri);
                    }
                }
        );

        cameraPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {
                    if (granted) openCamera();
                    else Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
        );
    }

    private Uri bitmapToUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        String path = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "emp_" + System.currentTimeMillis(),
                null
        );
        return Uri.parse(path);
    }


    private void checkStoragePermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 101);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
            }
        }
    }



}
