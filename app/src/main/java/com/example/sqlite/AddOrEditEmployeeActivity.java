package com.example.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import model.Employee;
import sqlite.EmployeeDao;

public class AddOrEditEmployeeActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmployeeId, etName, etSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_or_edit_employee);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEmployeeId = findViewById(R.id.etEmployeeId);
        etName = findViewById(R.id.etName);
        etSalary = findViewById(R.id.etSalary);

        findViewById(R.id.btSave).setOnClickListener(this);
        findViewById(R.id.btListEmployee).setOnClickListener(this);

        readEmployee();
    }

    private void readEmployee() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("employee_id")) {
            String empId = intent.getStringExtra("employee_id");
            if (empId != null && !empId.isEmpty()) {
                EmployeeDao dao = new EmployeeDao(this);
                Employee employee = dao.getById(empId);
                if (employee != null) {
                    etEmployeeId.setText(employee.getId());
                    etEmployeeId.setEnabled(false); // khóa không cho sửa ID khi chỉnh sửa
                    etName.setText(employee.getName());
                    etSalary.setText(String.valueOf(employee.getSalary()));
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btSave) {
            saveEmployee();
        } else if (id == R.id.btListEmployee) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void saveEmployee() {
        String empId = etEmployeeId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String salaryStr = etSalary.getText().toString().trim();

        if (empId.isEmpty() || name.isEmpty() || salaryStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        float salary;
        try {
            salary = Float.parseFloat(salaryStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid salary!", Toast.LENGTH_SHORT).show();
            return;
        }

        EmployeeDao dao = new EmployeeDao(this);
        Employee emp = new Employee(empId, name, salary);

        if (etEmployeeId.isEnabled()) {
            // Thêm mới
            long result = dao.insert(emp);
            if (result > 0) {
                Toast.makeText(this, "New employee has been saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Insert failed!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Cập nhật
            int result = dao.update(emp);
            if (result > 0) {
                Toast.makeText(this, "Employee has been updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
            }
        }

        finish(); // quay về màn hình danh sách
    }
}
