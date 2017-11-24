package a11021047.to_dolist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
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
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CLICKED");
                CheckBox checkBox = view.findViewById(R.id.list_item);
                TextView textView = view.findViewById(R.id.list_textView);
                long id = (Integer) textView.getTag();
                int completed = checkBox.isChecked()? 0 : 1;
                db.update(id, completed);
                checkBox.setChecked(completed == 1);
                System.out.println(id);

            }
        });
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_todo, parent, false);
    }
}
