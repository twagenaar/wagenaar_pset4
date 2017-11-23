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
//        ToDo todo1 = new ToDo("test1");
//        ToDo todo2 = new ToDo("test2");
//        ToDo todo3 = new ToDo("test3");
        db = TodoDatabase.getInstance(getApplicationContext());
        System.out.println(TodoDatabase.DB_NAME);
//        use the TodoDatabase to get all records from the database, make a new TodoAdapter and link the ListView to the adapter.
        Cursor cursor = db.selectAll();
        System.out.println(cursor);
        todoAdapter = new TodoAdapter(this, cursor);
        listView.setOnItemClickListener(new ListClickListener());
        listView.setOnItemLongClickListener(new ListLongClickListener());
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                System.out.println("CLICKED UGH");
//            }
//        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                System.out.println("ONCLICK!!!!!!!!!!!!!!!!!!!!!!");
//            }
//        }
//        );
//        listView.setOnItemLongClickListener(new MyOnItemLongClickListener());
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

    protected void onListItemClick(ListView l, View v, int position, long id) {
        System.out.println("TEST");
    }

    public class ListClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            System.out.println("CLICKED");
            CheckBox checkBox = view.findViewById(R.id.list_item);
            TextView textView = view.findViewById(R.id.list_textView);
            long id = (Integer) textView.getTag();
            int completed = checkBox.isChecked()? 0 : 1;
            db.update(id, completed);
            updateData();
            System.out.println(id);
//            checkItem();



        }
    }

    public class ListLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            System.out.println("LONGCLICKED");
//            CheckBox checkBox = view.findViewById(R.id.list_item);
            TextView textView = view.findViewById(R.id.list_textView);
            long id = (Integer) textView.getTag();
//            int completed = checkBox.isChecked()? 0 : 1;
            db.delete(id);
            updateData();
            System.out.println(id);
            return true;
        }
    }

//    public void checkItem(View view) {
////        TextView textView = view.findViewById(R.id.list_textView);
////        int id = textView.getTag();
//    }

//    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
////        lv.setOnItemClickListener(new OnItemClickListener() {
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            System.out.println("ONCLICK!!!!!!!!!!!!!!!!!!!!!!");
//            // selected item
//            int rowid = (Integer) view.findViewById(R.id.list_textView).getTag();
//            int completed = ((CheckBox) view.findViewById(R.id.list_item)).isChecked()? 1 : 0;
//            db.update(rowid, completed);
//
////            Toast toast = Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT);
////            toast.show();
//        }
//    }
}

//    private class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener {
//        @Override
//        public void onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//        }
//
//    }
