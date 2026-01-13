package com.abc.myfirstandroidapp.layout;

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

import com.abc.myfirstandroidapp.R;
import com.abc.myfirstandroidapp.adapters.AddressAdapter;
import com.abc.myfirstandroidapp.db.AddressDao;
import com.abc.myfirstandroidapp.entity.Address;

import java.util.List;

public class AddressListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AddressAdapter addressAdapter;
    AddressDao addressDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_address_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressDao = new AddressDao(this);

        loadAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAddress();
    }

    private void loadAddress(){
        List<Address> addressList = addressDao.getAllAddress();

        addressAdapter = new AddressAdapter(this,addressList, address -> {
            String[] options = {"update", "delete"};
            new AlertDialog.Builder(this)
                    .setTitle("Select section")
                    .setItems(options,(dialog, v)->{
                        if(v==0){
                            Intent intent = new Intent(this, AddressFormActivity.class);
                            intent.putExtra("id", address.getId());
                            startActivity(intent);
                        }else if(v == 1){
                            new AlertDialog.Builder(this)
                                    .setTitle("Delete Address")
                                    .setMessage("Are you sure")
                                    .setPositiveButton("Yes",(d, w) ->{
                                        addressDao.deleteAddress(address.getId());
                                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                                        loadAddress();
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                    })
                    .show();
        });
        recyclerView.setAdapter(addressAdapter);
    }
}