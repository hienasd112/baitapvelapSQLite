package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import adapter.EmployeeAdapter;
import model.Employee;
import sqlite.DBHelper;
import sqlite.EmployeeDao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Employee> employeeList;
    private Employee selectedEmployee = null;
    private EmployeeAdapter adapter;
    private String employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khoi tao DB
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.getReadableDatabase().close();

        // Load danh sach
        ListView lvEmployees = findViewById(R.id.lvEmployees);
        EmployeeDao dao = new EmployeeDao(this);
        employeeList = dao.getAll();

        adapter = new EmployeeAdapter(this, employeeList);
        lvEmployees.setAdapter(adapter);

        // Su kien chon item trong ListView
        lvEmployees.setOnItemClickListener((adapterView, view, position, id) -> {
            selectedEmployee = employeeList.get(position);
            Toast.makeText(this, "Selected: " + selectedEmployee.getName(), Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnEdit).setOnClickListener(this);
        findViewById(R.id.btnInsert).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
         // Nút Thêm mới
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btnEdit) {
            if (selectedEmployee != null) {
                Intent intent = new Intent(this, AddOrEditEmployeeActivity.class);
                intent.putExtra("employee_id", selectedEmployee.getId());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select an employee to edit!", Toast.LENGTH_SHORT).show();
            }
        } else if (viewId == R.id.btnDelete) {
            if (selectedEmployee != null) {
                EmployeeDao dao = new EmployeeDao(this);
                dao.delete(selectedEmployee.getId());
                Toast.makeText(this, "Employee deleted", Toast.LENGTH_SHORT).show();

                // Reload list
                employeeList.clear();
                employeeList.addAll(dao.getAll());
                adapter.notifyDataSetChanged();
                selectedEmployee = null;
            } else {
                Toast.makeText(this, "Please select an employee to delete!", Toast.LENGTH_SHORT).show();
            }
        } else if (viewId == R.id.btnInsert) {
            // Chuyển sang màn hình thêm mới
            Intent intent = new Intent(this, AddOrEditEmployeeActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Tự động refresh khi quay lại
        EmployeeDao dao = new EmployeeDao(this);
        employeeList.clear();
        employeeList.addAll(dao.getAll());
        adapter.notifyDataSetChanged();
        selectedEmployee = null;
    }
}
