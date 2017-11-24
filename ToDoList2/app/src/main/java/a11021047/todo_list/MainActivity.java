package a11021047.todo_list;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
    TodoDatabase db;
    TodoAdapter todoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.todos);
        editText = findViewById(R.id.editText);
        db = TodoDatabase.getInstance(getApplicationContext());
        System.out.println(TodoDatabase.DB_NAME);
        Cursor cursor = db.selectAll();
        System.out.println(cursor);
        todoAdapter = new TodoAdapter(this, cursor);
        listView.setOnItemClickListener(new ListClickListener());
        listView.setOnItemLongClickListener(new ListLongClickListener());
        listView.setAdapter(todoAdapter);
    }

    public void addItem(View view) {
        String text = editText.getText().toString();
        if (!text.equals("")) {
            editText.setText("");
            ToDo todo = new ToDo(text);
            db.addToDo(todo);
            updateData();
        }
    }

    public void updateData() {
        Cursor cursor = db.selectAll();
        todoAdapter.swapCursor(cursor);
    }

    public class ListClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            System.out.println("CLICKED");
            CheckBox checkBox = view.findViewById(R.id.list_item);
            TextView textView = view.findViewById(R.id.list_textView);
            long id = (Integer) textView.getTag();
            int completed = checkBox.isChecked() ? 0 : 1;
            db.update(id, completed);
            updateData();
        }
    }

    public class ListLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            System.out.println("LONGCLICKED");
            TextView textView = view.findViewById(R.id.list_textView);
            long id = (Integer) textView.getTag();
            db.delete(id);
            updateData();
            return true;
        }
    }
}
