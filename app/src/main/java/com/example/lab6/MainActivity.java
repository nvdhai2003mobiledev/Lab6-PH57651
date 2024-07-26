package com.example.lab6;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab6.delete.InterfaceDelete;
import com.example.lab6.select.InterfaceSelect;
import com.example.lab6.select.SvrResponseSelect;
import com.example.lab6.update.InterfaceUpdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText txt1, txt2, txt3;
    TextView tvKq;
    Button btn, btnUpdate, btnDelete;

    @SuppressLint("MissingInflatedId")
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

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        tvKq = findViewById(R.id.tvKq);
        btn = findViewById(R.id.btn);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        btn.setOnClickListener(v -> {
            selectData(tvKq);
        });
        btnUpdate.setOnClickListener(view -> {
            updateData(txt1, txt2, txt3, tvKq);
        });
        btnDelete.setOnClickListener(view -> {
            deleteData(txt1, tvKq);
        });
    }

    private void insetData(EditText txt1, EditText txt2, EditText txt3, TextView tvKq) {
        SanPham s = new SanPham(txt1.getText().toString(),
                txt2.getText().toString(),
                txt3.getText().toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.24.4.150/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceInsertSanPham insertSanPham = retrofit.create(InterfaceInsertSanPham.class);
        Call<SvrResponseSanPham> call = insertSanPham.insertSanPham(s.getMaSP(), s.getTenSP(), s.getMoTa());
        call.enqueue(new Callback<SvrResponseSanPham>() {
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res = response.body();
                tvKq.setText(res.getMessage());
            }

            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKq.setText("Lỗi: " + t.getMessage());
            }
        });
    }

    String strKQ = "";
    List<SanPham> ls;

    private void selectData(TextView tvKq) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.29/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceSelect interfaceSelect = retrofit.create(InterfaceSelect.class);
        Call<SvrResponseSelect> call = interfaceSelect.selectSanPham();
        call.enqueue(new Callback<SvrResponseSelect>() {
            @Override
            public void onResponse(Call<SvrResponseSelect> call, Response<SvrResponseSelect> response) {
                SvrResponseSelect res = response.body();
                ls = new ArrayList<>(Arrays.asList(res.getProducts()));
                for (SanPham p : ls) {
                    strKQ += "MaSP: " + p.getMaSP() + "; TenSP: " + p.getTenSP() + "; MoTa: " + p.getMoTa() + "\n";
                }
                tvKq.setText(strKQ);
            }

            @Override
            public void onFailure(Call<SvrResponseSelect> call, Throwable t) {
                tvKq.setText("Lỗi: " + t.getMessage());
            }
        });
    }

    private void deleteData(EditText txt1, TextView tvKq) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.29/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceDelete deleteSanPham = retrofit.create(InterfaceDelete.class);
        Call<SvrResponseSanPham> call = deleteSanPham.deleteSanPham(txt1.getText().toString());
        call.enqueue(new Callback<SvrResponseSanPham>() {
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res = response.body();
                tvKq.setText(res.getMessage());
            }

            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKq.setText("Lỗi: " + t.getMessage());
            }
        });
    }

    private void updateData(EditText txt1, EditText txt2, EditText txt3, TextView tvKq) {
        SanPham s = new SanPham(txt1.getText().toString(),
                txt2.getText().toString(),
                txt3.getText().toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.29/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceUpdate interfaceUpdate = retrofit.create(InterfaceUpdate.class);
        Call<SvrResponseSanPham> call = interfaceUpdate.updateSanPham(s.getMaSP(), s.getTenSP(), s.getMoTa());
        call.enqueue(new Callback<SvrResponseSanPham>() {
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res = response.body();
                tvKq.setText(res.getMessage());
            }

            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKq.setText("Lỗi: " + t.getMessage());
            }
        });
    }
}