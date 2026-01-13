package com.abc.myfirstandroidapp.layout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abc.myfirstandroidapp.R;
import com.abc.myfirstandroidapp.db.AddressDao;
import com.abc.myfirstandroidapp.entity.Address;

public class AddressFormActivity extends AppCompatActivity {

    EditText tvName, tvCity, tvPo;
    Button btnSubmit;

    AddressDao addressDao;
    long addressId = -1; // নতুন না update বুঝতে

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_address_form);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvName = findViewById(R.id.tvName);
        tvCity = findViewById(R.id.tvCity);
        tvPo = findViewById(R.id.tvPo);
        btnSubmit = findViewById(R.id.btnSubmit);

        addressDao = new AddressDao(this);

        // 🔹 update এর জন্য id আসছে কিনা চেক
        if (getIntent().hasExtra("id")) {
            addressId = getIntent().getLongExtra("id", -1);
            Address address = addressDao.getAddressId(addressId);

            if (address != null) {
                tvName.setText(address.getName());
                tvCity.setText(address.getCity());
                tvPo.setText(address.getPo());
                btnSubmit.setText("Update");
            }
        }

        btnSubmit.setOnClickListener(v -> saveAddress());
    }

    private void saveAddress() {
        String name = tvName.getText().toString().trim();
        String city = tvCity.getText().toString().trim();
        String po = tvPo.getText().toString().trim();

        if (name.isEmpty() || city.isEmpty() || po.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Address address = new Address(name, city, po);

        if (addressId == -1) {
            // ➕ নতুন Address save
            addressDao.insertAddress(address);
            Toast.makeText(this, "Address Saved", Toast.LENGTH_SHORT).show();
        } else {
            // ✏️ Address update
            address.setId(addressId);
            addressDao.updateAddress(addressId, address);
            Toast.makeText(this, "Address Updated", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, AddressListActivity.class);
        startActivity(intent);

        finish(); // AddressListActivity তে ফিরে যাবে
    }
}
