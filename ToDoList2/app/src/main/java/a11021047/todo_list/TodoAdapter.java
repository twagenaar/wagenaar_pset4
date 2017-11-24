package a11021047.todo_list;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Tessa on 19-11-2017.
 */

public class TodoAdapter extends ResourceCursorAdapter {
    public TodoDatabase db;
    public TodoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_todo, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        db = TodoDatabase.getInstance(context);
        TextView item = view.findViewById(R.id.list_textView);
        String text = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        item.setTag(id);
        item.setText(text);
        CheckBox checkBox = view.findViewById(R.id.list_item);
        checkBox.setTag(id);
        int checked = cursor.getInt(cursor.getColumnIndex("completed"));
        if (checked == 1) {
            checkBox.setChecked(true);
        }
        else {
            checkBox.setChecked(false);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_todo, parent, false);
    }
}
