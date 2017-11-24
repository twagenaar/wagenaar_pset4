package a11021047.todo_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tessa on 18-11-2017.
 */

public class TodoDatabase extends SQLiteOpenHelper {
    public static String DB_NAME = "todoDB";
    private static String TABLE_TODO = "todos";
    private static int VERSION_NUMBER = 1;
    private static TodoDatabase instance;
    private TodoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public static TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new TodoDatabase(context, DB_NAME, null, VERSION_NUMBER, null);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_TODO + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT NOT NULL, completed INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_TODO);
        ContentValues values = new ContentValues();
        values.put("completed", 0);
        values.put("title", "test1");
        sqLiteDatabase.insert(TABLE_TODO, null, values);
        ContentValues values2 = new ContentValues();
        values2.put("completed", 0);
        values2.put("title", "test2");
        sqLiteDatabase.insert(TABLE_TODO, null, values2);
        ContentValues values3 = new ContentValues();
        values3.put("completed", 1);
        values3.put("title", "test3");
        sqLiteDatabase.insert(TABLE_TODO, null, values3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        VERSION_NUMBER = i1;
        onCreate(sqLiteDatabase);
    }

    public void addToDo (ToDo toDo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("completed", toDo.completed);
        values.put("title", toDo.title);
        db.insert(TABLE_TODO, null, values);
    }

    public void update (long id, int completed) {
        System.out.println("UPDATE DB");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("completed", completed);
        db.update(TABLE_TODO, values, "_id=?", new String[] {Long.toString(id)});
    }

    public void delete(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, "_id=?", new String[] {Long.toString(id)});
    }

    public Cursor selectAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT rowid _id,* FROM " + TABLE_TODO, null);
    }
}