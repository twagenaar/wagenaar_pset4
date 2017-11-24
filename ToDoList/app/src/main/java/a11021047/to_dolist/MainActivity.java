package a11021047.to_dolist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
//        use the TodoDatabase to get all records from the database, make a new TodoAdapter and link the ListView to the adapter.
        Cursor cursor = db.selectAll();
        System.out.println(cursor);
        todoAdapter = new TodoAdapter(this, cursor);
        listView.setOnItemClickListener(new ListClickListener());
        listView.setOnItemLongClickListener(new ListLongClickListener());
        listView.setAdapter(todoAdapter);
    }

    public void addItem(View view) {
        String text = editText.getText().toString();
        editText.setText("");
        ToDo todo = new ToDo(text);
        db.addToDo(todo);
        updateData();
    }

    public void updateData() {
        Cursor cursor = db.selectAll();
        todoAdapter.swapCursor(cursor);
    }

//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        System.out.println("TEST");
//    }

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
