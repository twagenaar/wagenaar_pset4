package a11021047.to_dolist;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tessa on 19-11-2017.
 */

public class ToDo {
    public String title;
    public int completed;

    public ToDo(String name) {
        title = name;
        completed = 0;
    }

    public void setCompleted() {
        completed = 1;
    }

}
