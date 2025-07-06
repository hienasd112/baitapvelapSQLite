package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    // Thong tin co ban cua database
    public static final String DB_NAME = "Demo6";
    public static final int DB_VERSION = 1;

    // Constructor
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Tao bang khi DB duoc tao lan dau tien
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS nhanvien (" +
                "id TEXT PRIMARY KEY," +
                "name TEXT," +
                "salary REAL)";
        db.execSQL(createTable);
    }

    // Goi khi version DB thay doi: xoa bang cu va tao lai
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS nhanvien");
        onCreate(db);
    }
}
