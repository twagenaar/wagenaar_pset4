package a11021047.todo_list;

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
