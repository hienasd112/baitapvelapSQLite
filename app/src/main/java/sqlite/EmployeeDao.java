package sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Employee;

public class EmployeeDao {
    private final SQLiteDatabase db;

    public EmployeeDao(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Truy van voi tham so tuy chon
    @SuppressLint("Range")
    public List<Employee> get(String sql, String... selecArgs) {
        List<Employee> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selecArgs);
        while (cursor.moveToNext()) {
            Employee emp = new Employee();
            emp.setId(cursor.getString(cursor.getColumnIndex("id")));
            emp.setName(cursor.getString(cursor.getColumnIndex("name")));
            emp.setSalary(cursor.getFloat(cursor.getColumnIndex("salary")));
            list.add(emp);
        }
        cursor.close(); // Dong cursor sau khi su dung
        return list;
    }

    // Lay tat ca nhan vien
    public List<Employee> getAll() {
        String sql = "SELECT * FROM nhanvien";
        return get(sql);
    }

    // Lay theo id
    public Employee getById(String id) {
        String sql = "SELECT * FROM nhanvien WHERE id =?";
        List<Employee> list = get(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    // Them nhan vien moi
    public long insert(Employee emp) {
        ContentValues values = new ContentValues();
        values.put("id", emp.getId());
        values.put("name", emp.getName());
        values.put("salary", emp.getSalary());
        return db.insert("nhanvien", null, values);
    }

    // Cap nhat nhan vien
    public int update(Employee emp) {
        ContentValues values = new ContentValues();
        values.put("name", emp.getName());
        values.put("salary", emp.getSalary());
        return db.update("nhanvien", values, "id=?", new String[]{emp.getId()});
    }

    // Xoa theo id
    public int delete(String id) {
        return db.delete("nhanvien", "id=?", new String[]{id});
    }
}
